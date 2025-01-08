package Scanner;
import java.io.IOException;
import java.util.Scanner;

public class EditableBufferedReader {
    private StringBuilder text;  
    private int cursorPos;       
    private boolean insertMode;  
    private Scanner scanner;     

    public EditableBufferedReader() {
        this.text = new StringBuilder();
        this.cursorPos = 0;
        this.insertMode = true; 
         scanner = new Scanner(System.in); 
    }

    public void setRaw() throws IOException, InterruptedException {
       
        Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", "stty -echo raw </dev/tty"}).waitFor();
    }

    public void unsetRaw() throws IOException, InterruptedException {
        
        Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", "stty echo cooked </dev/tty"}).waitFor();
    }

    public int read() throws IOException {
        scanner.useDelimiter(""); 
        if (scanner.hasNext()) {
            String input = scanner.next(); 

            if (input.equals("\u001B")) { // ESC
                if (scanner.hasNext() && scanner.next().equals("[")) { 
                    String sequence = scanner.next(); 
                    if (sequence.equals("3")) { 
                        if (scanner.hasNext() && scanner.next().equals("~")) {
                            return -107; // Suprimir
                        }
                    } else if (sequence.equals("2")) { 
                        if (scanner.hasNext() && scanner.next().equals("~")) {
                            return -108; 
                        }
                    }
                    return switch (sequence) {
                        case "C" -> -102; 
                        case "D" -> -103; 
                        case "H" -> -104; 
                        case "F" -> -105;
                        default -> -1;   
                    };
                }
            } else if (input.equals("\u007F")) { 
                return -106;
            } else if (input.equals("\r")) { 
                return 13;
            } else if (input.length() == 1) { 
                return input.charAt(0);
            }
        }

        return -1; // Comando desconocido
    }

    public String readLine() throws IOException {
        try {
            
            System.out.println(" Editor de Texto ");
          
            

            setRaw(); 

           
            displayText(); 

            while (true) {
                int key = read(); 
                if (key == 13) {
                    break;
                }

                switch (key) {
                    case -102 -> moveCursorRight(); 
                    case -103 -> moveCursorLeft(); 
                    case -104 -> moveCursorHome();  
                    case -105 -> moveCursorEnd();   
                    case -106 -> deleteCharBeforeCursor(); 
                    case -107 -> deleteCharAtCursor(); 
                    case -108 -> toggleInsertMode(); 
                    default -> {
                        if (key >= 32 && key <= 126) { 
                            if (insertMode) {
                                insertChar((char) key);
                            } else {
                                overwriteChar((char) key);
                            }
                        }
                    }
                }

                displayText(); 
            }

            return text.toString();
        } catch (InterruptedException e) {
            throw new IOException("Error al cambiar el modo de terminal", e);
        } finally {
            try {
                unsetRaw(); 
            } catch (InterruptedException e) {
                System.err.println("Error al restaurar el modo normal del terminal: " + e.getMessage());
            }
        }
    }

    private void toggleInsertMode() {
        insertMode = !insertMode; 
    }

    private void displayText() {
       
        System.out.print("\r" + text + " "); 
        moveCursor(cursorPos); 
    }

    private void moveCursor(int position) {
        System.out.print("\r");             
        System.out.print("\033[" + (position + 1) + "G");
    }

    private void insertChar(char c) {
        text.insert(cursorPos, c);
        cursorPos++;
    }

    private void overwriteChar(char c) {
        if (cursorPos < text.length()) {
            text.setCharAt(cursorPos, c);
        } else {
            text.append(c);
        }
        cursorPos++;
    }

    private void deleteCharBeforeCursor() {
        if (cursorPos > 0) {
            text.deleteCharAt(cursorPos - 1);
            cursorPos--;
        }
    }

    private void deleteCharAtCursor() {
        if (cursorPos < text.length()) {
            text.deleteCharAt(cursorPos);
        }
    }

    private void moveCursorRight() {
        if (cursorPos < text.length()) {
            cursorPos++;
        }
    }

    private void moveCursorLeft() {
        if (cursorPos > 0) {
            cursorPos--;
        }
    }

    private void moveCursorHome() {
        cursorPos = 0;
    }

    private void moveCursorEnd() {
        cursorPos = text.length();
    }

    public static void main(String[] args) {
        EditableBufferedReader editor = new EditableBufferedReader();
        try {
            String result = editor.readLine();
            System.out.println("\nTexto final:");
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
