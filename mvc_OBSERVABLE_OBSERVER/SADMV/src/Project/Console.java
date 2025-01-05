/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Project;

/**
 *
 * @author victor
 */

// Clase para manejar la salida en la consola


import java.util.Observer;
import java.util.Observable;

public class Console implements Observer {
    @Override
    public void update(Observable observable, Object arg) {
        if (observable instanceof Line) {
            Line line = (Line) observable;
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
        System.out.print("\033[" + (position + 1) + "G"); // Mueve el cursor a la posición dada
    }
}



