/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Project;

import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author victor
 */
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {
        
        System.out.println("Edita el texto y presiona Enter para finalizar:");
        
        // Crear una instancia de EditableBufferedReader
        EditableBufferedReader reader = new EditableBufferedReader(new InputStreamReader(System.in));
        
        String userInput = "";
        try {
            // Leer la línea con soporte de edición
            userInput = reader.readLine();
        } catch (Exception e) {
            System.err.println("Ocurrió un error al leer la línea: " + e.getMessage());
            e.printStackTrace();
        }
        
        // Mostrar el texto final ingresado
        System.out.println( "\r"+"Texto ingresado: " + userInput);
    }
}




