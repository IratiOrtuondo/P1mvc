/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MVC;

/**
 *
 * @author ortuu
 */
import java.util.Observable;

class Value extends Observable {
	private int value; // Almacena el valor actual
	public int max;   // Almacena el valor máximo permitido

	// Constructor
	Value() {
    	value = 0; // Inicializa el valor en 0
	}

	// Método para incrementar el valor
	void inc() {
    	setChanged(); // Marca el estado como cambiado
    	if (value < max) { // Verifica si el valor actual es menor que el máximo
        	value++;
        	notifyObservers(new ConsoleBar.Command(ConsoleBar.Opcode.INC)); // Notifica a los observadores que se ha incrementado el valor
    	} else {
        	notifyObservers(new ConsoleBar.Command(ConsoleBar.Opcode.BELL)); // Notifica si se intenta exceder el valor máximo
    	}
	}

	// Método para decrementar el valor
	void dec() {
    	setChanged(); // Marca el estado como cambiado
    	if (value > 0) { // Verifica si el valor actual es mayor que 0
        	value--;
        	notifyObservers(new ConsoleBar.Command(ConsoleBar.Opcode.DEC)); // Notifica a los observadores que se ha decrementado el valor
    	} else {
        	notifyObservers(new ConsoleBar.Command(ConsoleBar.Opcode.BELL)); // Notifica si se intenta ir por debajo de 0
    	}
	}

	// Método para obtener el valor actual
	int get() {
    	return value; // Retorna el valor actual
	}

	// Método para establecer el valor máximo
	void setMax(int max) {
    	this.max = max; // Establece el valor máximo
	}
}
