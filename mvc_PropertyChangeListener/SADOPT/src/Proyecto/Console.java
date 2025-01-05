/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Proyecto;

/**
 *
 * @author victor
 */

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class Console implements PropertyChangeListener {
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("text".equals(evt.getPropertyName()) || "cursorPos".equals(evt.getPropertyName())) {
            Line line = (Line) evt.getSource();
            displayText(line.getText(), line.getCursorPos());
        }
    }

    public void displayText(String text, int cursorPos) {
        // Limpiar la consola y mostrar el texto
        System.out.print("\r" + text + " ");
        // Mover el cursor a la posición correcta
        System.out.print("\r" + text);
        moveCursor(cursorPos);
    }

    private void moveCursor(int position) {
        // Esto mueve el cursor hacia la derecha usando escape sequences
        System.out.print("\033[" + (position + 1) + "G");
    }
}


