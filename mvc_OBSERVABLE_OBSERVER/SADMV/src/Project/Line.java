/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Project;

/**
 *
 * @author victor
 */
// Clase que maneja la línea de texto y el cursor
import java.util.Observable;

public class Line extends Observable {
    private StringBuilder text;
    private int cursorPos;
    private boolean insertMode;

    public Line() {
        this.text = new StringBuilder();
        this.cursorPos = 0;
        this.insertMode = true;  // Modo inserción por defecto
    }

    public String getText() {
        return text.toString();
    }

    public int getCursorPos() {
        return cursorPos;
    }

    public boolean isInsertMode() {
        return insertMode;
    }

    public void toggleInsertMode() {
        insertMode = !insertMode;
        notifyChange();
    }

    public void insertChar(char c) {
        text.insert(cursorPos, c);
        cursorPos++;
        notifyChange();
    }

    public void overwriteChar(char c) {
        if (cursorPos < text.length()) {
            text.setCharAt(cursorPos, c);
        } else {
            text.insert(cursorPos, c);
        }
        cursorPos++;
        notifyChange();
    }

    public void deleteCharBeforeCursor() {
        if (cursorPos > 0) {
            text.deleteCharAt(cursorPos - 1);
            cursorPos--;
            notifyChange();
        }
    }

    public void deleteCharAtCursor() {
        if (cursorPos < text.length()) {
            text.deleteCharAt(cursorPos);
            notifyChange();
        }
    }

    public void moveCursorRight() {
        if (cursorPos < text.length()) {
            cursorPos++;
            notifyChange();
        }
    }

    public void moveCursorLeft() {
        if (cursorPos > 0) {
            cursorPos--;
            notifyChange();
        }
    }

    public void moveCursorHome() {
        cursorPos = 0;
        notifyChange();
    }

    public void moveCursorEnd() {
        cursorPos = text.length();
        notifyChange();
    }

    public void moveCursorTo(int position) {
        if (position < 0) {
            this.cursorPos = 0;
        } else if (position > text.length()) {
            this.cursorPos = text.length();
        } else {
            this.cursorPos = position;
        }
        notifyChange();
    }

    private void notifyChange() {
        setChanged();  // Marca que hubo un cambio
        notifyObservers();  // Notifica a los observadores
    }
}