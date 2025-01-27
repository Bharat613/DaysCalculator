package PROJECTS;

import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

// Custom JPanel with a linear gradient background
class GradientPanel extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Define gradient colors
        Color startColor = new Color(135, 206, 235); // Light blue
        Color endColor = new Color(70, 130, 180);   // Steel blue

        // Create a linear gradient from top to bottom
        int width = getWidth();
        int height = getHeight();
        GradientPaint gradientPaint = new GradientPaint(0, 0, startColor, 0, height, endColor);

        // Apply the gradient paint
        g2d.setPaint(gradientPaint);
        g2d.fillRect(0, 0, width, height);
    }
}

public class DaysCalculator extends JFrame {
    private JLabel dobLabel, resultLabel;
    private JDateChooser dobField;
    private JButton calculateButton;

    public DaysCalculator() {
        setTitle("Days Calculator");

        // Full-screen setup
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        setSize(screenSize.width, screenSize.height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);

        // Set gradient background
        GradientPanel gradientPanel = new GradientPanel();
        setContentPane(gradientPanel);
        gradientPanel.setLayout(new GridBagLayout());

        // Layout constraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Components
        
        
        dobLabel = new JLabel("Select Date:");
        dobField = new JDateChooser(); // Using JDateChooser instead of JTextField
        dobField.setPreferredSize(new Dimension(250, 40)); // Increase size of date picker

        calculateButton = new JButton("Calculate Difference");
        
        
        calculateButton.setPreferredSize(new Dimension(250, 40)); // Increase size of button

        resultLabel = new JLabel("Result: ", SwingConstants.CENTER);

        // Styling components
        dobLabel.setFont(new Font("Arial", Font.BOLD, 16));
        dobField.setFont(new Font("Arial", Font.PLAIN, 16));
        resultLabel.setFont(new Font("Arial", Font.BOLD, 18));
        resultLabel.setForeground(Color.BLUE);
        calculateButton.setBackground(Color.CYAN);
        calculateButton.setFont(new Font("Arial", Font.BOLD, 16));

        // Hover animation for the button
        calculateButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                calculateButton.setBackground(Color.GREEN);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                calculateButton.setBackground(Color.CYAN);
            }
        });
        JButton calendarButton = dobField.getCalendarButton();
        calendarButton.setFont(new Font("Arial", Font.PLAIN, 18)); // Increase font size of the calendar icon
        calendarButton.setPreferredSize(new Dimension(40, 40));

        // Add components to the layout
        gbc.gridx = 0;
        gbc.gridy = 0;
        gradientPanel.add(dobLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gradientPanel.add(dobField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gradientPanel.add(calculateButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gradientPanel.add(resultLabel, gbc);

        // Action listener for button
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateDifference();
            }
        });

        setVisible(true);
    }

    private void calculateDifference() {
        try {
            // Get the selected date from the JDateChooser
            Date date = dobField.getDate();
            if (date == null) {
                resultLabel.setText("Please select a valid date!");
                resultLabel.setForeground(Color.RED);
                return;
            }

            // Convert the selected date to LocalDate
            LocalDate dob = new java.sql.Date(date.getTime()).toLocalDate();
            LocalDate currentDate = LocalDate.now();

            // Validate that the DOB is not in the future
            if (dob.isAfter(currentDate)) {
                resultLabel.setText("Date of birth cannot be in the future!");
                resultLabel.setForeground(Color.RED);
                return;
            }

            // Calculate the difference
            Period period = Period.between(dob, currentDate);

            // Display the result
            resultLabel.setText("Years: " + period.getYears() +
                    ", Months: " + period.getMonths() +
                    ", Days: " + period.getDays());
            resultLabel.setForeground(Color.BLUE);
        } catch (Exception ex) {
            resultLabel.setText("Invalid Date! Try again.");
            resultLabel.setForeground(Color.RED);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(DaysCalculator::new);
    }
}
