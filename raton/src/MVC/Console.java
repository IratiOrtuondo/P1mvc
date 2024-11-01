/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MVC;

/**
 *
 * @author victor
 */

// Clase para manejar la salida en la consola
public class Console {
        String lastText = "";

       public void displayText(String text, int cursorPos) {
    
        // Solo actualiza si el texto ha cambiado
        if (!text.equals(lastText)) {
            // Imprime los caracteres que han cambiado
            int minLength = Math.min(text.length(), lastText.length());

            for (int i = 0; i < minLength; i++) {
                if (text.charAt(i) != lastText.charAt(i)) {
                    // Mueve el cursor a la posición correcta y reemplaza el carácter
                    moveCursor(i);
                    System.out.print(text.charAt(i));
                }
            }

            // Si el nuevo texto es más largo, imprime el resto
            if (text.length() > lastText.length()) {
                System.out.print(text.substring(minLength));
            }

            // Si el nuevo texto es más corto, limpia el sobrante
            else if (text.length() < lastText.length()) {
                System.out.print(" ".repeat(lastText.length() - text.length()));
            }

            // Actualiza el texto guardado
            lastText = text; 
        }
        
    	moveCursor(cursorPos);
    }

    public void showInsertMode(boolean isInsertMode) {
        System.out.println("\nModo de inserción: " + (isInsertMode ? "Activado" : "Desactivado"));
    }

    private void moveCursor(int position) {
        // Esto mueve el cursor hacia la derecha usando escape sequences
        System.out.print("\033[" + (position + 1) + "G"); // Mueve el cursor a la posición dada
    }
}


