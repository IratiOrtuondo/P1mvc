/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MVC1;

/**
 *
 * @author ortuu
 */
public class Console {
    public void displayText(String text, int cursorPos) {
        // Limpiar la consola y mostrar el texto
        System.out.print("\r" + text + " ");
        
        // Mover el cursor a la posición correcta
        System.out.print("\r" + text);
        moveCursor(cursorPos);
    }

    public void showInsertMode(boolean isInsertMode) {
        System.out.println("\nModo de inserción: " + (isInsertMode ? "Desactivado" : "Activado"));
    }

    private void moveCursor(int position) {
        // Esto mueve el cursor hacia la derecha usando escape sequences
        System.out.print("\033[" + (position + 1) + "G"); // Mueve el cursor a la posición dada
    }
    
    
}

