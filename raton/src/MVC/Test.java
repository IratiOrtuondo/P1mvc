/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MVC;

import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author victor
 */
public class Test {
    public static void main(String[] args) throws IOException {
        EditableBufferedReader reader = new EditableBufferedReader(new InputStreamReader(System.in));
        
        System.out.println("Editor de línea de texto interactivo");
        System.out.println("Instrucciones:");
        System.out.println("- Usa las flechas para mover el cursor");
        System.out.println("- Usa 'Insert' para alternar entre modo inserción y sobrescritura");
        System.out.println("- Usa 'Backspace' para borrar el carácter antes del cursor");
        System.out.println("- Usa 'Delete' para borrar el carácter en la posición del cursor");
        System.out.println("- Usa 'Home' para mover el cursor al inicio de la línea");
        System.out.println("- Usa 'End' para mover el cursor al final de la línea");
        System.out.println("- Usa el ratón para mover el cursor haciendo clic en una posición de la línea");
        System.out.println("Presiona 'Enter' para finalizar la edición y mostrar el texto ingresado.\n");

      
      
        reader.setRaw();
        
        try {
            System.out.println("Escribe una línea (puedes usar las teclas Home, End, flechas, y Insert para alternar inserción):" + "\r" );
            String input = reader.readLine();
            System.out.println("\nEntrada final: " + input);
        } finally {
            reader.unsetRaw(); // Asegura que el terminal regrese al modo cooked
        }
    
}
}



