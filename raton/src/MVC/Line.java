/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MVC;

/**
 *
 * @author victor
 */
// Clase que maneja la línea de texto y el cursor
public class Line {
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
    }

    public void insertChar(char c) {
        text.insert(cursorPos, c);
        cursorPos++;
    }

    public void overwriteChar(char c) {
        if (cursorPos < text.length()) {
            text.setCharAt(cursorPos, c);
        } else {
            text.insert(cursorPos, c);
        }
        cursorPos++;
    }

    public void deleteCharBeforeCursor() {
        if (cursorPos > 0) {
            text.deleteCharAt(cursorPos - 1);
            cursorPos--;
        }
    }

    public void deleteCharAtCursor() {
        if (cursorPos < text.length()) {
            text.deleteCharAt(cursorPos);
        }
    }

    public void moveCursorRight() {
        if (cursorPos < text.length()) {
            cursorPos++;
        }
    }

    public void moveCursorLeft() {
        if (cursorPos > 0) {
            cursorPos--;
        }
    }

    public void moveCursorHome() {
        cursorPos = 0;
    }

    public void moveCursorEnd() {
        cursorPos = text.length();
    }

    public void setCursorPos(int pos) {
        this.cursorPos = Math.max(0, Math.min(pos, text.length())); // Asegura que la posición sea válida
    }
    public void moveCursorTo(int position) {
    if (position < 0) {
        this.cursorPos = 0; // Fija el cursor al inicio si la posición es negativa
    } else if (position > text.length()) {
        this.cursorPos = text.length(); // Fija el cursor al final si la posición excede la longitud del texto
    } else {
        this.cursorPos = position; // Coloca el cursor en la posición solicitada
    }
}

}
