/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MVC;

/**
 * @author ortuu
 *ConsoleBar
 */
import java.util.*;

class ConsoleBar implements Observer {
	static enum Opcode {
    	INC, DEC, BELL
	}

	static class Command {
    	Opcode op;

    	Command(Opcode op) {
        	this.op = op;
    	}
	}

    
	Value model;
	static final char BLOCK = '#'; // Character to represent the filled part of the bar

	ConsoleBar(Value value) {
    	model = value;

    	// get max columns (you can set a default value, e.g., 20)
     	// Or you can use a method to calculate it dynamically
    	display();
	}

	@Override
	public void update(Observable o, Object arg) {
    	Command comm = (Command) arg;
    	switch (comm.op) {
        	case INC:
            	display(); // Display when incremented
            	break;
        	case DEC:
            	display(); // Display when decremented
            	break;
        	case BELL:
            	System.out.print('\007'); // Sound a bell
            	break;
    	}
	}

	// Method to display the current value as a bar
	
    // Método para mostrar la barra en la consola
    private void display() {
        int value = model.get();  // Obtiene el valor actual
        int filledBlocks = value; // Calcula los bloques llenos

        // Usa StringBuilder para construir la barra
        StringBuilder bar = new StringBuilder("\r[");
        for (int i = 0; i < filledBlocks; i++) {
            bar.append(BLOCK);  // Llena la barra
        }
        for (int i = filledBlocks; i < model.max; i++) {
            bar.append(' ');  // Espacios vacíos
        }
        bar.append("] ").append(value).append("/").append(model.max);

        // Sobreescribe la línea en la consola
        System.out.print(bar.toString()+ " ");
        System.out.flush();  // Asegura que se muestre inmediatamente
    }
}


