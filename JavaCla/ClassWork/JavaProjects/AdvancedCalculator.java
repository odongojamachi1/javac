package JavaProjects;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdvancedCalculator extends JFrame {
    private JTextField displayField;
    private JPanel buttonPanel;

    public AdvancedCalculator() {
        setTitle("Advanced Calculator");
        setSize(300, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create display field
        displayField = new JTextField();
        displayField.setEditable(false);
        displayField.setHorizontalAlignment(SwingConstants.RIGHT);
        displayField.setFont(new Font("Arial", Font.PLAIN, 24));

        // Create button panel
        buttonPanel = new JPanel(new GridLayout(5, 4, 5, 5));
        addButton("7");
        addButton("8");
        addButton("9");
        addButton("/");
        addButton("4");
        addButton("5");
        addButton("6");
        addButton("*");
        addButton("1");
        addButton("2");
        addButton("3");
        addButton("-");
        addButton("0");
        addButton(".");
        addButton("+/-");
        addButton("+");
        addButton("C");
        addButton("sqrt");  
        addButton("=");
        
        // Layout
        setLayout(new BorderLayout());
        add(displayField, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
    }

    private void addButton(String label) {
        JButton button = new JButton(label);
        button.setFont(new Font("Arial", Font.PLAIN, 18));
        button.addActionListener(new ButtonClickListener());
        buttonPanel.add(button);
    }

    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            switch (command) {
                case "C":
                    displayField.setText("");
                    break;
                case "=":
                    evaluateExpression();
                    break;
                case "sqrt":
                    calculateSquareRoot();
                    break;
             
                case "+/-":
                    changeSign();
                    break;
                default:
                    displayField.setText(displayField.getText() + command);
            }
        }

        private void evaluateExpression() {
            String expression = displayField.getText();
            try {
                double result = CalculatorEngine.evaluate(expression);
                displayField.setText(String.valueOf(result));
            } catch (IllegalArgumentException ex) {
                displayField.setText("Error");
            }
        }

        private void calculateSquareRoot() {
            double operand = Double.parseDouble(displayField.getText());
            if (operand < 0) {
                displayField.setText("Error");
            } else {
                double result = Math.sqrt(operand);
                displayField.setText(String.valueOf(result));
            }
        }

  

        private void changeSign() {
            double value = Double.parseDouble(displayField.getText());
            displayField.setText(String.valueOf(-value));
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdvancedCalculator().setVisible(true));
    }
}

class CalculatorEngine {
    static double evaluate(String expression) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < expression.length()) ? expression.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < expression.length()) throw new IllegalArgumentException("Unexpected: " + (char) ch);
                return x;
            }

            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if (eat('+')) x += parseTerm();
                    else if (eat('-')) x -= parseTerm();
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if (eat('*')) x *= parseFactor();
                    else if (eat('/')) x /= parseFactor();
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor();
                if (eat('-')) return -parseFactor();

                double x;
                int startPos = this.pos;
                if (eat('(')) {
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') {
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(expression.substring(startPos, this.pos));
                } else {
                    throw new IllegalArgumentException("Unexpected: " + (char) ch);
                }

                if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation
                if (eat('√')) x = Math.sqrt(x); // square root
                return x;
            }
        }.parse();
    }
}
