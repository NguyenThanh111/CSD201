package runtime;

import engine.UndoRedoEngine;
import java.util.Scanner;

public class Main {

    private static final Scanner SCANNER = new Scanner(System.in);
    private static final UndoRedoEngine ENGINE = new UndoRedoEngine();

    public static void main(String[] args) {
        int choice;

        do {
            showMenu();
            choice = readInteger("Nhap lua chon: ");
            handleChoice(choice);
        } while (choice != 0);

        ENGINE.clearAll();
        System.out.println("Cam on ban da su dung chuong trinh!");
        SCANNER.close();
    }

    private static void showMenu() {
        System.out.println();
        System.out.println("==================================================");
        System.out.println("       REDO / UNDO ENGINE OF TEXT EDITOR");
        System.out.println("==================================================");
        System.out.println("Document length: " + ENGINE.getDocumentLength());
        System.out.println("Current document:");
        System.out.println("\"" + ENGINE.getDocumentContent() + "\"");
        System.out.println("--------------------------------------------------");
        System.out.println("1. Insert text");
        System.out.println("2. Delete text");
        System.out.println("3. Replace text");
        System.out.println("4. Undo");
        System.out.println("5. Redo");
        System.out.println("6. Show Undo / Redo stacks");
        System.out.println("7. Clear document and stacks");
        System.out.println("0. Exit");
        System.out.println("==================================================");
    }

    private static void handleChoice(int choice) {
        switch (choice) {
            case 1:
                handleInsert();
                break;
            case 2:
                handleDelete();
                break;
            case 3:
                handleReplace();
                break;
            case 4:
                System.out.println(ENGINE.undo());
                break;
            case 5:
                System.out.println(ENGINE.redo());
                break;
            case 6:
                showStacks();
                break;
            case 7:
                ENGINE.clearAll();
                System.out.println("Da xoa document, undoStack va redoStack.");
                break;
            case 0:
                break;
            default:
                System.out.println("Lua chon khong hop le. Vui long thu lai.");
        }
    }

    private static void handleInsert() {
        int position = readInteger("Nhap vi tri can chen: ");
        String text = readText("Nhap text can chen: ");
        System.out.println(ENGINE.insert(position, text));
    }

    private static void handleDelete() {
        int position = readInteger("Nhap vi tri bat dau xoa: ");
        int length = readInteger("Nhap so ky tu can xoa: ");
        System.out.println(ENGINE.delete(position, length));
    }

    private static void handleReplace() {
        int position = readInteger("Nhap vi tri bat dau replace: ");
        int length = readInteger("Nhap so ky tu can replace: ");
        String newText = readText("Nhap text moi: ");
        System.out.println(ENGINE.replace(position, length, newText));
    }

    private static void showStacks() {
        System.out.println();
        System.out.println("---------------- STACK STATUS ----------------");
        System.out.println("undoStack: " + ENGINE.getUndoStackDisplay());
        System.out.println("redoStack: " + ENGINE.getRedoStackDisplay());
        System.out.println("----------------------------------------------");
    }

    private static int readInteger(String message) {
        while (true) {
            System.out.print(message);
            String input = SCANNER.nextLine();

            try {
                return Integer.parseInt(input.trim());
            } catch (NumberFormatException ex) {
                System.out.println("Gia tri khong hop le. Vui long nhap so nguyen.");
            }
        }
    }

    private static String readText(String message) {
        System.out.print(message);
        return SCANNER.nextLine();
    }
}
