package engine;

import datastructure.SimpleStack;
import model.ActionType;
import model.EditAction;
import model.TextDocument;

public class UndoRedoEngine {

    private final TextDocument document;
    private final SimpleStack<EditAction> undoStack;
    private final SimpleStack<EditAction> redoStack;

    public UndoRedoEngine() {
        document = new TextDocument();
        undoStack = new SimpleStack<EditAction>();
        redoStack = new SimpleStack<EditAction>();
    }

    public String insert(int position, String text) {
        try {
            document.insert(position, text);
            EditAction action = new EditAction(ActionType.INSERT, position, "", text);
            saveNewAction(action);
            return "Da insert text thanh cong.";
        } catch (IllegalArgumentException ex) {
            return "Loi: " + ex.getMessage();
        }
    }

    public String delete(int position, int length) {
        try {
            String removedText = document.delete(position, length);
            EditAction action = new EditAction(ActionType.DELETE, position, removedText, "");
            saveNewAction(action);
            return "Da delete text thanh cong.";
        } catch (IllegalArgumentException ex) {
            return "Loi: " + ex.getMessage();
        }
    }

    public String replace(int position, int length, String newText) {
        try {
            String oldText = document.replace(position, length, newText);
            EditAction action = new EditAction(ActionType.REPLACE, position, oldText, newText);
            saveNewAction(action);
            return "Da replace text thanh cong.";
        } catch (IllegalArgumentException ex) {
            return "Loi: " + ex.getMessage();
        }
    }

    public String undo() {
        if (undoStack.isEmpty()) {
            return "Khong co thao tac nao de Undo.";
        }

        EditAction action = undoStack.pop();
        reverseAction(action);
        redoStack.push(action);
        return "Da Undo: " + action;
    }

    public String redo() {
        if (redoStack.isEmpty()) {
            return "Khong co thao tac nao de Redo.";
        }

        EditAction action = redoStack.pop();
        applyAction(action);
        undoStack.push(action);
        return "Da Redo: " + action;
    }

    public void clearAll() {
        document.clear();
        undoStack.clear();
        redoStack.clear();
    }

    public String getDocumentContent() {
        return document.getContent();
    }

    public int getDocumentLength() {
        return document.length();
    }

    public String getUndoStackDisplay() {
        return undoStack.display();
    }

    public String getRedoStackDisplay() {
        return redoStack.display();
    }

    private void saveNewAction(EditAction action) {
        undoStack.push(action);
        redoStack.clear();
    }

    private void applyAction(EditAction action) {
        if (action.getType() == ActionType.INSERT) {
            document.insert(action.getPosition(), action.getNewText());
        } else if (action.getType() == ActionType.DELETE) {
            document.delete(action.getPosition(), action.getOldText().length());
        } else if (action.getType() == ActionType.REPLACE) {
            document.replace(action.getPosition(), action.getOldText().length(), action.getNewText());
        }
    }

    private void reverseAction(EditAction action) {
        if (action.getType() == ActionType.INSERT) {
            document.delete(action.getPosition(), action.getNewText().length());
        } else if (action.getType() == ActionType.DELETE) {
            document.insert(action.getPosition(), action.getOldText());
        } else if (action.getType() == ActionType.REPLACE) {
            document.replace(action.getPosition(), action.getNewText().length(), action.getOldText());
        }
    }
}
