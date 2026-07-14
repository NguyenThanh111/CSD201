package runtime;

import java.util.Scanner;
import java.util.InputMismatchException;
import data.TextEditor;

public class Program {

    public static void main(String[] args) {
        //Menu
        System.out.println("=====Text Editor=====");
        System.out.println("1. Insert");
        System.out.println("2. Delete");
        System.out.println("3. Undo");
        System.out.println("4. Redo");
        System.out.println("5. Exit");
        System.out.println("=====================");

        //Text input
        Scanner scanner = new Scanner(System.in);
        TextEditor textEditor = new TextEditor();
        while (true) {
            try {
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        System.out.print("Enter position to insert: ");
                        int insertPosition = scanner.nextInt();
                        scanner.nextLine(); // Consume newline
                        System.out.print("Enter text to insert: ");
                        String insertText = scanner.nextLine();
                        textEditor.insert(insertPosition, insertText);
                        System.out.println("Current Content: " + textEditor.getContent());
                        break;
                    case 2:
                        System.out.print("Enter position to delete: ");
                        int deletePosition = scanner.nextInt();
                        System.out.print("Enter length to delete: ");
                        int deleteLength = scanner.nextInt();
                        scanner.nextLine(); // Consume newline
                        textEditor.delete(deletePosition, deleteLength);
                        System.out.println("Current Content: " + textEditor.getContent());
                        break;
                    case 3:
                        textEditor.undo();
                        System.out.println("Current Content: " + textEditor.getContent());
                        break;
                    case 4:
                        textEditor.redo();
                        System.out.println("Current Content: " + textEditor.getContent());
                        break;
                    case 5:
                        System.out.println("Exiting...");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Please enter a valid integer.");
                scanner.nextLine(); // Clear the invalid input from scanner buffer
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
        }
    }
    
}
