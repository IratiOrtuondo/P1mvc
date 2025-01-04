/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package calcu;

import java.util.Scanner;

/**
 *
 * @author ortuo
 */
public class Form {
    private Field[] fields;
    private Label resultLabel;
    private Scanner scanner;

    public Form(Field[] fields, Label resultLabel) {
        this.fields = fields;
        this.resultLabel = resultLabel;
        this.scanner = new Scanner(System.in);
    }

    public boolean read() {
        try {
            for (Field field : fields) {
                System.out.print(field.getLabel());
                field.setValue(scanner.nextLine());
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Field getField(String name) {
        for (Field field : fields) {
            if (field.getName().equals(name)) {
                return field;
            }
        }
        return null;
    }

    public Label getResultLabel() {
        return resultLabel;
    }

    public void close() {
        scanner.close();
    } 
}
