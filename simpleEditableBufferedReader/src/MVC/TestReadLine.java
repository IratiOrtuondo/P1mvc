/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MVC;
/**
 *
 * @author victor
 */
import java.io.*;
import java.io.IOException;
import java.io.InputStreamReader;

class TestReadLine {
   public static void main(String[] args) throws IOException {
        EditableBufferedReader reader = new EditableBufferedReader(new InputStreamReader(System.in));
        reader.setRaw();
        
        try {
            System.out.println("Escribe una línea (puedes usar las teclas Home, End, flechas, y Insert para alternar inserción):");
            String input = reader.readLine();
            System.out.println("\nEntrada final: " + input);
        } finally {
            reader.unsetRaw(); // Asegura que el terminal regrese al modo cooked
        }
    }
}


