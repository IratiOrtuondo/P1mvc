/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MVC;
import java.io.*;
import static java.lang.System.in;


/**
 *
 * @author ortuu
 */
public class SlideBar {

    // Función para obtener el ancho del terminal
   private static int getTerminalWidth() {
    int width = 20; // Valor por defecto
    try {
        Process process = Runtime.getRuntime().exec(new String[] { "sh", "-c", "tput cols" });
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = reader.readLine();
        if (line != null && !line.trim().isEmpty()) {
            width = Integer.parseInt(line.trim()); // Limpia los espacios en blanco y saltos de línea
        }
    } catch (Exception e) {
        System.err.println("Error al obtener el tamaño del terminal, usando valor por defecto: " + e.getMessage());
    }
    return width-11;
}


    // Configura el terminal en modo "raw"
    private static void setRaw() {
        try {
            Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", "stty -echo raw </dev/tty"}).waitFor();
        } catch (IOException | InterruptedException e) {
            System.err.println("Error al establecer modo raw: " + e.getMessage());
            e.printStackTrace(); // Opción para depuración
        }
    }

    // Restaura el terminal a modo "cooked"
    private static void unsetRaw() {
        try {
            Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", "stty echo cooked </dev/tty"}).waitFor();
        } catch (IOException | InterruptedException e) {
            System.err.println("Error al regresar al modo normal: " + e.getMessage());
            e.printStackTrace(); // Opción para depuración
        }
    }

    static final int RIGHT = 0, LEFT = 1;

    // Método para leer las teclas de flecha
    public static int readArrow() throws IOException {
        int ch;
        ch = in.read();
        if (ch == 27) { // Si se detecta ESC
            in.read(); // Lee y descarta '['
            ch = in.read();
            switch (ch) {
                case 'C' -> {
                    return RIGHT; // Flecha derecha
                }
                case 'D' -> {
                    return LEFT; // Flecha izquierda
                }
            }
        }
        return ch;
    }

    // Método main
    public static void main(String[] args) throws IOException {
        int arrow;
        ConsoleBar con = null;
        Value value = null;

        try {
            value = new Value();
            con = new ConsoleBar(value);

            setRaw(); // Establece el modo "raw" para la entrada del terminal

            int terminalWidth = getTerminalWidth(); // Obtiene el ancho del terminal dinámicamente
            value.setMax(terminalWidth); // Establecer el máximo de columnas en función del tamaño del terminal
            value.addObserver(con); // Agrega ConsoleBar como observador del valor

            // Loop para leer flechas y ajustar el valor
            while ((arrow = readArrow()) != '\r') {
                if (arrow == RIGHT)
                    value.inc(); // Incrementa el valor al presionar flecha derecha
                else if (arrow == LEFT)
                    value.dec(); // Decrementa el valor al presionar flecha izquierda
            }
        } finally {
            unsetRaw(); // Restaura el modo normal del terminal
        }
    }
}
