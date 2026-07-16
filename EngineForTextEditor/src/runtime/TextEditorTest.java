package runtime;

import data.TextEditor;

public class TextEditorTest {
    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {
        System.out.println("===== EngineForTextEditor Test Result =====");

        testInsert();
        testInsertUndo();
        testInsertUndoRedo();
        testMultipleUndo();
        testRedoClearedAfterNewEdit();
        testUndoWhenEmpty();
        testRedoWhenEmpty();
        testDeleteUndoRedo();
        testInvalidInsertPosition();
        testInvalidDeletePosition();
        testEmptyInsertDoesNotCreateHistory();
        testDeleteZeroLengthDoesNotCreateHistory();

        System.out.println("==========================================");
        System.out.println("Passed: " + passed);
        System.out.println("Failed: " + failed);

        if (failed == 0) {
            System.out.println("Final Result: ALL TESTS PASSED");
        } else {
            System.out.println("Final Result: SOME TESTS FAILED");
        }
    }

    private static void testInsert() {
        TextEditor editor = new TextEditor();
        editor.insert(0, "Hello");

        check("Insert text", "Hello", editor.getContent());
    }

    private static void testInsertUndo() {
        TextEditor editor = new TextEditor();
        editor.insert(0, "Hello");
        editor.undo();

        check("Insert then Undo", "", editor.getContent());
    }

    private static void testInsertUndoRedo() {
        TextEditor editor = new TextEditor();
        editor.insert(0, "Hello");
        editor.undo();
        editor.redo();

        check("Insert, Undo, then Redo", "Hello", editor.getContent());
    }

    private static void testMultipleUndo() {
        TextEditor editor = new TextEditor();
        editor.insert(0, "A");
        editor.insert(1, "B");
        editor.insert(2, "C");
        editor.undo();
        editor.undo();

        check("Several edits then Undo several times", "A", editor.getContent());
    }

    private static void testRedoClearedAfterNewEdit() {
        TextEditor editor = new TextEditor();
        editor.insert(0, "Hello");
        editor.insert(5, " World");
        editor.undo();
        editor.insert(5, " Sweet");
        editor.redo();

        check("Undo then new edit clears old Redo", "Hello Sweet", editor.getContent());
    }

    private static void testUndoWhenEmpty() {
        TextEditor editor = new TextEditor();
        editor.undo();

        check("Undo when no history exists", "", editor.getContent());
    }

    private static void testRedoWhenEmpty() {
        TextEditor editor = new TextEditor();
        editor.redo();

        check("Redo when no history exists", "", editor.getContent());
    }

    private static void testDeleteUndoRedo() {
        TextEditor editor = new TextEditor();
        editor.insert(0, "Hello");
        editor.delete(1, 3);
        check("Delete text", "Ho", editor.getContent());

        editor.undo();
        check("Delete then Undo", "Hello", editor.getContent());

        editor.redo();
        check("Delete then Redo", "Ho", editor.getContent());
    }

    private static void testInvalidInsertPosition() {
        TextEditor editor = new TextEditor();
        boolean errorFound = false;

        try {
            editor.insert(1, "A");
        } catch (IllegalArgumentException e) {
            errorFound = true;
        }

        check("Invalid insert position is rejected", true, errorFound);
        check("Invalid insert keeps content unchanged", "", editor.getContent());
    }

    private static void testInvalidDeletePosition() {
        TextEditor editor = new TextEditor();
        editor.insert(0, "Hello");
        boolean errorFound = false;

        try {
            editor.delete(4, 5);
        } catch (IllegalArgumentException e) {
            errorFound = true;
        }

        check("Invalid delete position is rejected", true, errorFound);
        check("Invalid delete keeps content unchanged", "Hello", editor.getContent());
    }

    private static void testEmptyInsertDoesNotCreateHistory() {
        TextEditor editor = new TextEditor();
        editor.insert(0, "");
        editor.undo();

        check("Empty insert does not create Undo history", "", editor.getContent());
    }

    private static void testDeleteZeroLengthDoesNotCreateHistory() {
        TextEditor editor = new TextEditor();
        editor.insert(0, "Hello");
        editor.delete(2, 0);
        editor.undo();

        check("Delete zero length does not create extra history", "", editor.getContent());
    }

    private static void check(String testName, String expected, String actual) {
        if (expected.equals(actual)) {
            passed++;
            System.out.println("[PASS] " + testName + " | Expected: \"" + expected + "\" | Actual: \"" + actual + "\"");
        } else {
            failed++;
            System.out.println("[FAIL] " + testName + " | Expected: \"" + expected + "\" | Actual: \"" + actual + "\"");
        }
    }

    private static void check(String testName, boolean expected, boolean actual) {
        if (expected == actual) {
            passed++;
            System.out.println("[PASS] " + testName + " | Expected: " + expected + " | Actual: " + actual);
        } else {
            failed++;
            System.out.println("[FAIL] " + testName + " | Expected: " + expected + " | Actual: " + actual);
        }
    }
}
