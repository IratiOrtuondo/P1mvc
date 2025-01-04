/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package calcu;

/**
 *
 * @author ortuo
 */
public class CalculatorForm {
   public static void main(String[] args) {
        // Define widgets
        Field[] fields = new Field[] {
            new Field("first", "First operand: "),
            new Field("second", "Second operand: "),
            new Field("operator", "Operator (+, -, *, /): ")
        };
        Label resultLabel = new Label("result", "Result: ");
        Form form = new Form(fields, resultLabel);

        // Event loop
        while (form.read()) {
            try {
                // Read input
                int first = Integer.parseInt(form.getField("first").getValue());
                int second = Integer.parseInt(form.getField("second").getValue());
                String operator = form.getField("operator").getValue();

                // Evaluate result
                int result = calculate(operator, first, second);
                resultLabel.setLabel("Result: " + result);

            } catch (NumberFormatException e) {
                resultLabel.setLabel("Error: Invalid number format.");
            } catch (ArithmeticException e) {
                resultLabel.setLabel("Error: " + e.getMessage());
            } catch (Exception e) {
                resultLabel.setLabel("Error: Invalid input.");
            }

            // Write result
            resultLabel.display();
        }

        // Close form
        form.close();
    }

    private static int calculate(String operator, int first, int second) {
        switch (operator) {
            case "+":
                return first + second;
            case "-":
                return first - second;
            case "*":
                return first * second;
            case "/":
                if (second == 0) {
                    throw new ArithmeticException("Division by zero is not allowed.");
                }
                return first / second;
            default:
                throw new IllegalArgumentException("Invalid operator: " + operator);
        }
    }  
}
