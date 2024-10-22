/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MVC;

import java.util.ArrayList;
import java.util.List;

class MultiLine {
    private List<StringBuilder> lines;
    private int cursorLine;
    private int cursorPos;
    private boolean insertMode;

    public MultiLine() {
        this.lines = new ArrayList<>();
        this.lines.add(new StringBuilder()); // Añadir la primera línea
        this.cursorLine = 0;
        this.cursorPos = 0;
        this.insertMode = true;  // Modo inserción por defecto
    }

    public String getText() {
        StringBuilder text = new StringBuilder();
        for (StringBuilder line : lines) {
            text.append(line);
        }
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
        lines.get(cursorLine).insert(cursorPos, c);
        cursorPos++;
    }

    public void overwriteChar(char c) {
        if (cursorPos < lines.get(cursorLine).length()) {
            lines.get(cursorLine).setCharAt(cursorPos, c);
        } else {
            lines.get(cursorLine).insert(cursorPos, c);
        }
        cursorPos++;
    }

    public void deleteCharBeforeCursor() {
        if (cursorPos > 0) {
            lines.get(cursorLine).deleteCharAt(cursorPos - 1);
            cursorPos--;
        }
    }

    public void deleteCharAtCursor() {
        if (cursorPos < lines.get(cursorLine).length()) {
            lines.get(cursorLine).deleteCharAt(cursorPos);
        }
    }

    public void moveCursorRight() {
        if (cursorPos < lines.get(cursorLine).length()) {
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
        cursorPos = lines.get(cursorLine).length();
    }

 
}
