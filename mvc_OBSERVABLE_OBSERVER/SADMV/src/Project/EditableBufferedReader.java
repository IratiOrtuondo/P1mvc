/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Project;

/**
 *
 * @author victor
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public class EditableBufferedReader extends BufferedReader {
    private Line model;
    private Console view;

    public EditableBufferedReader(Reader in) {
        super(in);
    }

   
    
    public void setRaw() {
        try {
            Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", "stty -echo raw </dev/tty"}).waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void unsetRaw() {
        try {
            Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", "stty echo cooked </dev/tty"}).waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String readLine() throws IOException {
        
        setRaw();
        this.model = new Line();
        this.view = new Console();

        // Conectar el modelo y la vista
        model.addObserver(view);

        try {
            int key;
            while ((key = read()) != 13) { // Mientras no se presione Enter
                switch (key) {
                    case -102: model.moveCursorRight(); break;
                    case -103: model.moveCursorLeft(); break;
                    case -104: model.moveCursorHome(); break;
                    case -105: model.moveCursorEnd(); break;
                    case -106: model.deleteCharBeforeCursor(); break;
                    case -107: model.deleteCharAtCursor(); break;
                    case -108: model.toggleInsertMode(); break;
                    default:
                        if (model.isInsertMode()) {
                            model.insertChar((char) key);
                        } else {
                            model.overwriteChar((char) key);
                        }
                        break;
                }
            }
        } finally {
            unsetRaw();
            
        }

        return model.getText();
    }

    @Override
    public int read() throws IOException {
        int c = super.read();

        if (c == 27) { // Si detectamos ESC
            int nextChar = super.read();
            if (nextChar == '[') {
                int followingChar = super.read();
                 
                    switch (followingChar) {
                        case 'C': return -102; // Flecha derecha
                        case 'D': return -103; // Flecha izquierda
                        case 'H': return -104; // Inicio
                        case 'F': return -105; // Fin
                        case '2':
                            if (super.read() == '~') return -108; // Insertar
                            break;
                        case '3':
                            if (super.read() == '~') return -107; // Suprimir
                            break;
                    }
                
            }
        } else if (c == 127) {
            return -106; // Retroceso
        }

        return c;
    }
}
