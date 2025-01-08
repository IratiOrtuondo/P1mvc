/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package multiline;

/**
 *
 * @author victor
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Console {
    public void displayText(String text, int cursorPos, int r0, int c0, int rf, int cf, int startRow) throws IOException {
        clearLines(startRow, rf + 1);

        
        System.out.print("\033[" + (startRow + 1) + ";1H");
        System.out.print(text);

       
        moveCursor(startRow + r0, c0);
    }

    private void moveCursor(int row, int col) {
        System.out.print("\033[" + (row + 1) + ";" + (col + 1) + "H");
    }

    public static void clearLines(int startRow, int numLines) {
        for (int i = 0; i < numLines; i++) {
            System.out.print("\033[" + (startRow + i + 1) + ";1H"); 
            System.out.print(" ".repeat(80)); 
        }
    }

    public static int calculateLines(String text, int terminalWidth) {
        if (terminalWidth <= 0) {
            return -1;
        }
        return (text.length() + terminalWidth - 1) / terminalWidth;
    }

    public static int getTerminalWidth() throws IOException {
        int width = 80;
        Process process = Runtime.getRuntime().exec(new String[]{"sh", "-c", "tput cols 2> /dev/tty"});
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line = reader.readLine();
            if (line != null && !line.trim().isEmpty()) {
                width = Integer.parseInt(line.trim());
            }
        } catch (NumberFormatException e) {
            System.err.println("Error al obtener ancho del terminal, usando valor por defecto: " + e.getMessage());
        }
        return width;
    }

    public static int getCursorRow() throws IOException {
        System.out.print("\033[6n"); 
        System.out.flush();

        // Leer la respuesta del terminal
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder response = new StringBuilder();
        int c;
        while ((c = reader.read()) != 'R') { 
            response.append((char) c);
        }

        
        String[] parts = response.toString().split("\\[|;");
        if (parts.length >= 2) {
            return Integer.parseInt(parts[1]) - 1; // Devuelve la fila actual (base 1 a base 0)
        }
        return 0; // Valor por defecto si falla
    }
}
