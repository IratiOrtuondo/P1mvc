/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MVC;

/**
 *
 * @author victor
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class EditableBufferedReader extends BufferedReader {
	private Line model;
	private Console view;
    
	// Constantes para teclas especiales
	private static final int ARROW_RIGHT = -102;
	private static final int ARROW_LEFT = -103;
	private static final int HOME = -104;
	private static final int END = -105;
	private static final int BACKSPACE = -106;
	private static final int DELETE = -107;
	private static final int INSERT = -108;
	private static final int MOUSE_EVENT = -109;

	public EditableBufferedReader(Reader in) {
    	super(in);
	}

	// Método para activar la detección de eventos de ratón
	public void enableMouse() {
    	try {
        	//c=Integer.parseInt(System.getenv("COLUMNS");
        	Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", "printf \"\\e[?1000;1006h\" > /dev/tty 2> /dev/null"}).waitFor();
    	} catch (IOException | InterruptedException e) {
        	System.err.println("Error al habilitar la detección del ratón: " + e.getMessage());
        	e.printStackTrace();
    	}
	}

	// Método para desactivar la detección de eventos de ratón
	public void disableMouse() {
    	try {
        	Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", "printf \"\\e[?1000;1006l\" > /dev/tty 2> /dev/null"}).waitFor();
    	} catch (IOException | InterruptedException e) {
        	System.err.println("Error al deshabilitar la detección del ratón: " + e.getMessage());
        	e.printStackTrace();
    	}
	}

	// Método para pasar la consola a modo raw (sin buffer de línea)
	public void setRaw() {
    	try {
        	Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", "stty -echo raw </dev/tty"}).waitFor();
    	} catch (IOException | InterruptedException e) {
        	System.err.println("Error al establecer modo raw: " + e.getMessage());
        	e.printStackTrace();
    	}
	}

	// Método para regresar la consola al modo normal (cooked)
	public void unsetRaw() {
    	try {
        	Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", "stty echo cooked </dev/tty"}).waitFor();
    	} catch (IOException | InterruptedException e) {
        	System.err.println("Error al regresar al modo normal: " + e.getMessage());
        	e.printStackTrace();
    	}
	}

	// Sobreescribimos el método readLine() para proporcionar la funcionalidad personalizada
	@Override
	public String readLine() throws IOException {
    	enableMouse(); // Activar la detección del ratón
    	setRaw(); // Pasar la consola a modo raw
    	this.model = new Line();
    	this.view = new Console();

    	try {
        	int key;
        	while ((key = read()) != 13) { // Mientras no se presione Enter
            	switch (key) {
                	case ARROW_RIGHT:
                    	model.moveCursorRight();
                    	break;
                	case ARROW_LEFT:
                    	model.moveCursorLeft();
                    	break;
                	case HOME:
                    	model.moveCursorHome();
                    	break;
                	case END:
                    	model.moveCursorEnd();
                    	break;
                	case BACKSPACE:
                    	model.deleteCharBeforeCursor();
                    	break;
                	case DELETE:
                    	model.deleteCharAtCursor();
                    	break;
                	case INSERT:
                    	model.toggleInsertMode();
                    	view.showInsertMode(model.isInsertMode());
                    	break;
                	case MOUSE_EVENT:
                    	// Ya manejado en handleMouseSequence()
                     	handleMouseSequence();
                    	break;
                	default:
                    	if (model.isInsertMode()) {
                        	model.insertChar((char) key);
                    	} else {
                        	model.overwriteChar((char) key);
                    	}
                    	break;
            	}

            	// Actualizar la vista con el texto actual y la posición del cursor
            	view.displayText(model.getText(), model.getCursorPos());
        	}
    	} finally {
        	unsetRaw(); // Regresar la consola al modo normal
        	disableMouse(); // Desactivar la detección del ratón
    	}

    	return model.getText(); // Devolver la línea editada
	}

	// Método que lee una tecla del usuario y detecta secuencias de ratón
	@Override
	public int read() throws IOException {
    	int c = super.read();

    	if (c == 27) { // Si detectamos ESC
        	int nextChar = super.read();
        	if (nextChar == '[') {
            	int followingChar = super.read();
            	if (followingChar == '<') {
                	// Evento de ratón en formato ESC [ <
              	 
                	return MOUSE_EVENT; // Retorna un código de evento de ratón
            	} else {
                	// Otros eventos de teclado (Home, End, flechas, etc.)
                	switch (followingChar) {
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
            	}
        	}
    	} else if (c == 127) {
        	return BACKSPACE;
    	}

    	return c;
	}

	// Método para manejar la secuencia ESC [ < de eventos de ratón
	private void handleMouseSequence() throws IOException {
    	StringBuilder sb = new StringBuilder();
    	int ch;

    	// Leer caracteres hasta que llegue la 'M' o 'm' al final de la secuencia
    	while ((ch = super.read()) != 'M' && ch != 'm') {
        	sb.append((char) ch);
    	}

    	// Dividir la secuencia en partes separadas por ';'
    	String[] parts = sb.toString().split(";");
    	if (parts.length >= 3) {
        	int eventType = Integer.parseInt(parts[0]);
        	int mouseX = Integer.parseInt(parts[1]);
        	int mouseY = Integer.parseInt(parts[2]);

        	// Aquí se manejan los eventos de ratón
        	if (eventType == 0) { // Asumimos 0 como clic izquierdo
            	model.moveCursorTo(mouseX - 1); // Ajusta el cursor según la posición X
            	view.displayText(model.getText(), model.getCursorPos());
        	}
    	}
	}
}






