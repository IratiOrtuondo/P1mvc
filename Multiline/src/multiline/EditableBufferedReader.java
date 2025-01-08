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
import java.io.Reader;

public class EditableBufferedReader extends BufferedReader {
    private static final int ARROW_RIGHT = -102;
    private static final int ARROW_LEFT = -103;
    private static final int HOME = -104;
    private static final int END = -105;
    private static final int BACKSPACE = -106;
    private static final int DELETE = -107;
    private static final int INSERT = -108;

    private int r0 = 0; // Fila actual del cursor
    private int c0 = 0; // Columna actual del cursor
    private int rf = 0; // Última fila con texto
    private int cf = 80; // Número de columnas en el terminal--> despues calcularemos este valor con la funcion getTerminalWidth()
    private int startRow = 0; // Fila inicial del cursor

    public EditableBufferedReader(Reader in) {
        super(in);
    }

    public void setRaw() {
        try {
            Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", "stty -echo raw </dev/tty"}).waitFor();
        } catch (IOException | InterruptedException e) {
            System.err.println("Error al establecer modo raw: " + e.getMessage());
        }
    }

    public void unsetRaw() {
        try {
            Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", "stty echo cooked </dev/tty"}).waitFor();
        } catch (IOException | InterruptedException e) {
            System.err.println("Error al regresar al modo normal: " + e.getMessage());
        }
    }

    @Override
    public String readLine() throws IOException {
        MultiLine model = new MultiLine();
        Console view = new Console();
        setRaw();
        try {
            cf = Console.getTerminalWidth(); 
            startRow = Console.getCursorRow(); 
            int key;
            while ((key = read()) != 13) { 
                switch (key) {
                    case ARROW_RIGHT:
                        model.moveCursorRight();
                        updateCursorPosition(model.getCursorPos());
                        break;
                    case ARROW_LEFT:
                        model.moveCursorLeft();
                        updateCursorPosition(model.getCursorPos());
                        break;
                    case HOME:
                        model.moveCursorHome();
                        updateCursorPosition(0);
                        break;
                    case END:
                        model.moveCursorEnd();
                        updateCursorPosition(model.getText().length());
                        break;
                    case BACKSPACE:
                        model.deleteCharBeforeCursor();
                        updateCursorPosition(model.getCursorPos());
                        break;
                    case DELETE:
                        model.deleteCharAtCursor();
                        break;
                    case INSERT:
                        model.toggleInsertMode();
                        break;
                    default:
                        if (model.isInsertMode()) {
                            model.insertChar((char) key);
                        } else {
                            model.overwriteChar((char) key);
                        }
                        updateCursorPosition(model.getCursorPos());
                        break;
                }

                rf = Console.calculateLines(model.getText(), cf) - 1; // Calcular última fila ocupada
                view.displayText(model.getText(), model.getCursorPos(), r0, c0, rf, cf, startRow);
            }
        } finally {
            unsetRaw();
        }
        return model.getText();
    }

    @Override
    public int read() throws IOException {
        int c = super.read();
        if (c == 27) { // Si se detecta ESC
            super.read();
            int arrowKey = super.read();
            switch (arrowKey) {
                case 'C': return ARROW_RIGHT;
                case 'D': return ARROW_LEFT;
                case 'H': return HOME;
                case 'F': return END;
                case '2':
                    if (super.read() == '~') return INSERT;
                    break;
                case '3':
                    if (super.read() == '~') return DELETE;
                    break;
            }
        } else if (c == 127) {
            return BACKSPACE;
        }
        return c;
    }

    private void updateCursorPosition(int cursorPos) {
        r0 = cursorPos / cf; // Calcula fila actual
        c0 = cursorPos % cf; // Calcula columna actual
    }
}





