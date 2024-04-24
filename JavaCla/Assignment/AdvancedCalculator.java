import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class AdvancedCalculator extends JFrame {
    private JTextField numField1, numField2, resultField;
    private JButton addButton, subtractButton, multiplyButton, divideButton, powerButton, sqrtButton, absButton, modButton;
    private JLabel statusLabel;

    public AdvancedCalculator() {
        setTitle("Advanced Calculator");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create text fields
        numField1 = createInputField();
        numField2 = createInputField();
        resultField = createInputField();
        resultField.setEditable(false);

        // Create buttons for basic operations
        addButton = createButton("+", '+');
        subtractButton = createButton("-", '-');
        multiplyButton = createButton("*", '*');
        divideButton = createButton("/", '/');
        powerButton = createButton("x^y", '^');
        sqrtButton = createButton("sqrt", '√');
        absButton = createButton("abs", '|');
        modButton = createButton("%", '%');

        // Status label for input validation feedback
        statusLabel = new JLabel("", JLabel.CENTER);
        statusLabel.setForeground(Color.RED);

        // Layout using panels
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        inputPanel.add(new JLabel("Number 1:"));
        inputPanel.add(numField1);
        inputPanel.add(new JLabel("Number 2:"));
        inputPanel.add(numField2);
        inputPanel.add(new JLabel("Result:"));
        inputPanel.add(resultField);

        JPanel buttonPanel = new JPanel(new GridLayout(4, 4, 5, 5));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        buttonPanel.add(addButton);
        buttonPanel.add(subtractButton);
        buttonPanel.add(multiplyButton);
        buttonPanel.add(divideButton);
        buttonPanel.add(modButton);
        buttonPanel.add(powerButton);
        buttonPanel.add(sqrtButton);
        buttonPanel.add(absButton);

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(statusLabel, BorderLayout.SOUTH);

        add(mainPanel);

        // Listen for Enter key events on text field
        numField1.addKeyListener(new EnterKeyListener());
        numField2.addKeyListener(new EnterKeyListener());
    }

    private JTextField createInputField() {
        JTextField field = new JTextField(10);
        field.setHorizontalAlignment(JTextField.RIGHT);
        return field;
    }

    private JButton createButton(String label, char operator) {
        JButton button = new JButton(label);
        button.setFont(new Font("Arial", Font.PLAIN, 18));
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                calculate(operator);
            }
        });
        return button;
    }

    private void calculate(char operator) {
        String num1Text = numField1.getText();
        String num2Text = numField2.getText();

        if (num1Text.isEmpty() || num2Text.isEmpty()) {
            statusLabel.setText("Please enter numbers in both fields.");
            resultField.setText("");
            return;
        }

        try {
            double num1 = Double.parseDouble(num1Text);
            double num2 = Double.parseDouble(num2Text);
            double result = 0;

            switch (operator) {
                case '+':
                    result = num1 + num2;
                    break;
                case '-':
                    result = num1 - num2;
                    break;
                case '*':
                    result = num1 * num2;
                    break;
                case '/':
                    if (num2 == 0) {
                        statusLabel.setText("Cannot divide by zero!");
                        resultField.setText("");
                        return;
                    }
                    result = num1 / num2;
                    break;
                case '%':
                    result = num1 % num2;
                    break;
                case '^':
                    result = Math.pow(num1, num2);
                    break;
                case '√':
                    if (num1 < 0) {
                        statusLabel.setText("Cannot calculate square root of a negative number.");
                        resultField.setText("");
                        return;
                    }
                    result = Math.sqrt(num1);
                    break;
                case '|':
                    result = Math.abs(num1);
                    break;
            }

            resultField.setText(String.valueOf(result));
            statusLabel.setText("");
        } catch (NumberFormatException ex) {
            statusLabel.setText("Invalid input. Please enter valid numbers.");
            resultField.setText("");
        }
    }

    private class EnterKeyListener implements KeyListener {
        public void keyTyped(KeyEvent e) {}

        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                calculate('+'); // Use addition as default operator when Enter is pressed
            }
        }

        public void keyReleased(KeyEvent e) {}
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AdvancedCalculator().setVisible(true);
        });
    }
}
