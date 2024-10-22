/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MVC;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 *
 * @author ortuu
 */

class Console {
    
    private MultiLine model;

    public void displayText(String text, int cursorPos) throws IOException {

        // Limpiar la consola y mostrar el texto
       
       int ancho= Console.getTerminalWidth();
       int lineas = Console.calculateLines(text, ancho);
       Console.clearLines(lineas);
        
        // Mover el cursor a la posición correcta
        System.out.print("\r" + text + " ");
        moveCursor(cursorPos);
    }

    public void showInsertMode(boolean isInsertMode) {
        System.out.println("\nModo de inserción: " + (isInsertMode ? "Desactivado" : "Activado"));
    }

    private void moveCursor(int position) {
        // Esto mueve el cursor hacia la derecha usando escape sequences
        System.out.print("\033[" + (position + 1) + "G"); // Mueve el cursor a la posición dada
    }
    
        // Función que ejecuta el comando 'stty size' para obtener el tamaño de la terminal
    public static int getTerminalWidth() throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder("sh", "-c", "stty size < /dev/tty");
        Process process = processBuilder.start();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String output = reader.readLine();

        if (output != null) {
            String[] parts = output.split(" ");
            return Integer.parseInt(parts[1]); // El segundo valor es el ancho
        }

        return -1; // Si falla la obtención del ancho
    }

    // Función que calcula cuántas líneas ocupa el texto dado el ancho de la terminal
    public static int calculateLines(String text, int terminalWidth) {
        if (terminalWidth <= 0) {
            return -1; // Valor no válido
        }
        int textLength = text.length();
        return (textLength + terminalWidth - 1) / terminalWidth; // Calcular líneas ocupadas
    }

    // Función que limpia las líneas en la terminal
public static void clearLines(int numLines) {
  
    // Borrar desde la última línea hasta la primera
    for (int i = 0; i < numLines; i++) {
        System.out.print("\r" + " "); // Sobrescribir la línea actual con espacios
        System.out.print("\r"); // Retornar al principio de la línea

        // Si no es la última línea, mover el cursor hacia arriba para borrar la siguiente
        if (i < numLines - 1) {
            System.out.print("\033[1A"); // Mover el cursor una línea arriba
        }
    }
    
    
}



}




