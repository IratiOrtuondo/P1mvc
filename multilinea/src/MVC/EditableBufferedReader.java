/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MVC;

/**
 *
 * @author ortuu
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class EditableBufferedReader extends BufferedReader {
    private MultiLine model;
    private Console view;

    public EditableBufferedReader(Reader in) {
        super(in);
        this.model = new MultiLine();
        this.view = new Console();
    }

    public void setRaw() {
        try {
            Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", "stty -echo raw </dev/tty"}).waitFor();
        } catch (IOException | InterruptedException e) {
            System.err.println("Error al establecer modo raw: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void unsetRaw() {
        try {
            Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", "stty echo cooked </dev/tty"}).waitFor();
        } catch (IOException | InterruptedException e) {
            System.err.println("Error al regresar al modo normal: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public String readLine() throws IOException {
        setRaw();
        try {
            run();
        } finally {
            unsetRaw();
        }
        return model.getText(); // Devolver todas las líneas
    }

    public void run() throws IOException {
        int key;
        while ((key = readKey()) != 13) { // Mientras no se presione Enter
            switch (key) {
                case -102: // Flecha derecha
                    model.moveCursorRight();
                    break;

                case -103: // Flecha izquierda
                    model.moveCursorLeft();
                    break;

                case -104: // Home
                    model.moveCursorHome();
                    break;

                case -105: // End
                    model.moveCursorEnd();
                    break;

                case -106: // Backspace
                    model.deleteCharBeforeCursor();
                    break;

                case -107: // Delete
                    model.deleteCharAtCursor();
                    break;

                case -108: // Insert
                    model.toggleInsertMode();
                    view.showInsertMode(model.isInsertMode());
                    break;


                default: // Cualquier otro carácter
                    if (model.isInsertMode()) {
                        model.insertChar((char) key);
                    } else {
                        model.overwriteChar((char) key);
                    }
                    break;
            }

            view.displayText(model.getText(), model.getCursorPos());
        }
    }

    public int readKey() throws IOException {
        int c = super.read();
        if (c == 27) { // Si se detecta ESC
            super.read(); // Lee y descarta '['
            int arrowKey = super.read();
            switch (arrowKey) {
                case 'C': return -102; // Flecha derecha
                case 'D': return -103; // Flecha izquierda
                case 'H': return -104; // Home
                case 'F': return -105; // End
                case '2':
                    int tilde = super.read();
                    if (tilde == '~') return -108; // Insert
                    break;
                case '3':
                    int tilde2 = super.read();
                    if (tilde2 == '~') return -107; // Delete
                    break;
            }
        } else if (c == 127) {
            return -106; // Backspace
        }
        return c; // Retorna el carácter normal si no es una tecla especial
    }
}
