package MVC;
/**
 *
 * @author victor
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class EditableBufferedReader extends BufferedReader {
    private boolean insertMode; // Modo inserción o sobreescritura
    
    // Constantes para teclas especiales
    private static final int ARROW_RIGHT = -102;
    private static final int ARROW_LEFT = -103;
    private static final int HOME = -104;
    private static final int END = -105;
    private static final int BACKSPACE = -106;
    private static final int DELETE = -107;
    private static final int INSERT = -108;

    public EditableBufferedReader(InputStreamReader in) {
        super(in);
        this.insertMode = true; // Inicialmente en modo inserción
    }

    // Método que pasa la consola a modo raw (sin buffer de línea)
    public void setRaw() {
        try {
            Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", "stty -echo raw </dev/tty"}).waitFor();
        } catch (IOException | InterruptedException e) {
            System.err.println("Error al establecer modo raw: " + e.getMessage());
            e.printStackTrace(); // Opción para depuración
        }
    }

    // Método que regresa la consola al modo normal (cooked)
    public void unsetRaw() {
        try {
            Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", "stty echo cooked </dev/tty"}).waitFor();
        } catch (IOException | InterruptedException e) {
            System.err.println("Error al regresar al modo normal: " + e.getMessage());
            e.printStackTrace(); // Opción para depuración
        }
    }

    // Método read para leer un carácter o tecla especial
    @Override
    public int read() throws IOException {
        int c = super.read(); // Lee el primer carácter

        if (c == 27) { // Si se detecta ESC
            super.read(); // Lee y descarta '['
            int arrowKey = super.read();
            switch (arrowKey) {
                case 'C': return ARROW_RIGHT; // Flecha derecha (Right Arrow)
                case 'D': return ARROW_LEFT; // Flecha izquierda (Left Arrow)
                case 'H': return HOME; // Home (Inicio de línea)
                case 'F': return END; // End (Final de línea)
                case '2': // Podría ser la tecla Insert
                    int tilde = super.read();
                    if (tilde == '~') {
                        return INSERT; // Insert (cambia entre modo inserción/sobreescritura)
                    }
                    break;
                case '3': // Podría ser la tecla Delete
                    int tilde2 = super.read();
                    if (tilde2 == '~') {
                        return -DELETE; // Delete (Borrar el carácter actual)
                    }
                    break;
            }
        } else if (c == 127) {
            return BACKSPACE; // Backspace (Borrar el carácter a la izquierda)
        }
        return c; // Retorna el carácter normal si no es especial
    }

    // Método para leer una línea de texto editable
    @Override
    public String readLine() throws IOException {
        StringBuilder buffer = new StringBuilder();
        int cursorPos = 0;

        while (true) {
            // Imprimir el contenido actual
            System.out.print("\r" + buffer.toString() + " "); // Imprimir buffer
            // Mover el cursor a la posición correcta
            System.out.print("\r" + buffer.toString());
            // Esto mueve el cursor al final del texto actual
            moveCursor(cursorPos);

            int key = this.read();

            if (key == 13) { // Enter (finaliza la entrada)
                break;
            }

            switch (key) {
                case ARROW_RIGHT: // Flecha derecha
                    if (cursorPos < buffer.length()) {
                        cursorPos++; // Mueve el cursor a la derecha
                    }
                    break;

                case ARROW_LEFT: // Flecha izquierda
                    if (cursorPos > 0) {
                        cursorPos--; // Mueve el cursor a la izquierda
                    }
                    break;

                case HOME: // Home
                    cursorPos = 0; // Mueve el cursor al inicio de la línea
                    break;

                case END: // End
                    cursorPos = buffer.length(); // Mueve el cursor al final de la línea
                    break;

                case BACKSPACE: // Backspace
                    if (cursorPos > 0) {
                        buffer.deleteCharAt(cursorPos - 1); // Borra el carácter a la izquierda
                        cursorPos--;
                    }
                    break;

                case DELETE: // Delete
                    if (cursorPos < buffer.length()) {
                        buffer.deleteCharAt(cursorPos); // Borra el carácter actual
                    }
                    break;

                case INSERT: // Insert (cambiar entre modo inserción/sobreescritura)
                    toggleInsertMode(); // Alterna el modo
                    System.out.println("\nModo de inserción: " + (insertMode ? "Desactivado" : "Activado"));
                    break;

                default: // Cualquier otro carácter normal
                    if (insertMode) { // Modo inserción
                        buffer.insert(cursorPos, (char) key);
                    } else { // Modo sobreescritura
                        if (cursorPos < buffer.length()) {
                            buffer.setCharAt(cursorPos, (char) key);
                        } else {
                            buffer.insert(cursorPos, (char) key);
                        }
                    }
                    cursorPos++;
                    break;
            }
        }

        // Al final de la entrada, imprimimos el buffer final
        System.out.print("\r" + buffer.toString() + "\n");
        return buffer.toString();
    }

    // Método para mover el cursor a una posición específica
    private void moveCursor(int position) {
        // Esto mueve el cursor hacia la derecha usando escape sequences
        System.out.print("\033[" + (position + 1) + "G"); // Mueve el cursor a la posición dada
    }

    // Método para alternar entre el modo inserción y sobreescritura
    public void toggleInsertMode() {
        this.insertMode = !this.insertMode;
    }
}
