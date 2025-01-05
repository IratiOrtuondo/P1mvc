/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Proyecto;

/**
 *
 * @author victor
 */
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {
      
        System.out.println("Edita el texto y presiona Enter para finalizar:");

        EditableBufferedReader reader = new EditableBufferedReader(new InputStreamReader(System.in));

        String userInput = "";
        try {
            userInput = reader.readLine();
        } catch (Exception e) {
            System.err.println("Error al leer la línea: " + e.getMessage());
        }

        System.out.println("\nTexto ingresado: " + userInput);
    }
}

