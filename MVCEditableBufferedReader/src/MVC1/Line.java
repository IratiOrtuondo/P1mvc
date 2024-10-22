/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MVC1;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 *
 * @author ortuu
 */
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
    
}

