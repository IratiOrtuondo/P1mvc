/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Proyecto;

/**
 *
 * @author victor
 */
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Line {
    private StringBuilder text;
    private int cursorPos;
    private boolean insertMode;
    private final PropertyChangeSupport support;

    public Line() {
        this.text = new StringBuilder();
        this.cursorPos = 0;
        this.insertMode = true; // Modo inserción por defecto
        this.support = new PropertyChangeSupport(this);
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
        boolean oldMode = this.insertMode;
        this.insertMode = !this.insertMode;
        support.firePropertyChange("insertMode", oldMode, this.insertMode);
    }

    public void insertChar(char c) {
        String oldText = text.toString();
        text.insert(cursorPos, c);
        cursorPos++;
        support.firePropertyChange("text", oldText, text.toString());
    }

    public void overwriteChar(char c) {
        String oldText = text.toString();
        if (cursorPos < text.length()) {
            text.setCharAt(cursorPos, c);
        } else {
            text.insert(cursorPos, c);
        }
        cursorPos++;
        support.firePropertyChange("text", oldText, text.toString());
    }

    public void deleteCharBeforeCursor() {
        if (cursorPos > 0) {
            String oldText = text.toString();
            text.deleteCharAt(cursorPos - 1);
            cursorPos--;
            support.firePropertyChange("text", oldText, text.toString());
        }
    }

    public void deleteCharAtCursor() {
        if (cursorPos < text.length()) {
            String oldText = text.toString();
            text.deleteCharAt(cursorPos);
            support.firePropertyChange("text", oldText, text.toString());
        }
    }

    public void moveCursorRight() {
        if (cursorPos < text.length()) {
            int oldPos = cursorPos;
            cursorPos++;
            support.firePropertyChange("cursorPos", oldPos, cursorPos);
        }
    }

    public void moveCursorLeft() {
        if (cursorPos > 0) {
            int oldPos = cursorPos;
            cursorPos--;
            support.firePropertyChange("cursorPos", oldPos, cursorPos);
        }
    }

    public void moveCursorHome() {
        int oldPos = cursorPos;
        cursorPos = 0;
        support.firePropertyChange("cursorPos", oldPos, cursorPos);
    }

    public void moveCursorEnd() {
        int oldPos = cursorPos;
        cursorPos = text.length();
        support.firePropertyChange("cursorPos", oldPos, cursorPos);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }
}
