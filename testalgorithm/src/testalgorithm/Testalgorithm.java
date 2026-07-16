package testalgorithm; // Thêm dòng khai báo package này

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Random;

// Đổi tên class thành Testalgorithm để khớp với cấu trúc project của bạn
public class Testalgorithm {

    // ==========================================
    // THUẬT TOÁN A: DOUBLY LINKED LIST (DLL)
    // ==========================================
    static class Node {
        String value;
        Node prev;
        Node next;

        Node(String value) {
            this.value = value;
        }
    }

    static class DLLHistory {
        Node head;
        Node current;

        DLLHistory() {
            head = new Node("START");
            current = head;
        }

        void addAction(String value) {
            Node newNode = new Node(value);
            current.next = newNode;
            newNode.prev = current;
            current = newNode;
        }

        void undo() {
            if (current.prev != null) {
                current = current.prev;
            }
        }

        void redo() {
            if (current.next != null) {
                current = current.next;
            }
        }
    }

    // ==========================================
    // THUẬT TOÁN B: TWO STACKS (2-Stack)
    // ==========================================
    static class TwoStackHistory {
        Deque<String> undoStack = new ArrayDeque<>();
        Deque<String> redoStack = new ArrayDeque<>();

        void addAction(String value) {
            undoStack.push(value);
            redoStack.clear(); 
        }

        void undo() {
            if (!undoStack.isEmpty()) {
                redoStack.push(undoStack.pop());
            }
        }

        void redo() {
            if (!redoStack.isEmpty()) {
                undoStack.push(redoStack.pop());
            }
        }
    }

    static class Operation {
        String type; 
        String value;

        Operation(String type, String value) {
            this.type = type;
            this.value = value;
        }
    }

    // ==========================================
    // HÀM KIỂM TRA HIỆU NĂNG CHÍNH
    // ==========================================
    public static void main(String[] args) {
        int[] inputSizes = {100, 500, 1000, 5000, 10000};
        Random random = new Random();

        System.out.printf("%-15s | %-25s | %-25s%n", "Input Size (n)", "Algorithm A: DLL (µs)", "Algorithm B: 2-Stack (µs)");
        System.out.println("-------------------------------------------------------------------------");

        for (int n : inputSizes) {
            List<Operation> operations = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                double rand = random.nextDouble();
                if (rand < 0.7) { 
                    operations.add(new Operation("add", "Action " + i));
                } else if (rand < 0.85) { 
                    operations.add(new Operation("undo", null));
                } else { 
                    operations.add(new Operation("redo", null));
                }
            }

            DLLHistory dllHist = new DLLHistory();
            long startDLL = System.nanoTime();
            for (Operation op : operations) {
                if (op.type.equals("add")) dllHist.addAction(op.value);
                else if (op.type.equals("undo")) dllHist.undo();
                else if (op.type.equals("redo")) dllHist.redo();
            }
            long endDLL = System.nanoTime();

            TwoStackHistory stackHist = new TwoStackHistory();
            long startStack = System.nanoTime();
            for (Operation op : operations) {
                if (op.type.equals("add")) stackHist.addAction(op.value);
                else if (op.type.equals("undo")) stackHist.undo();
                else if (op.type.equals("redo")) stackHist.redo();
            }
            long endStack = System.nanoTime();

            double timeDllUs = (endDLL - startDLL) / 1000.0;
            double timeStackUs = (endStack - startStack) / 1000.0;

            System.out.printf("%-15d | %-25.2f | %-25.2f%n", n, timeDllUs, timeStackUs);
        }
    }
}