package data;

public class ActionHistory {
    private ActionNode current;

    public ActionHistory(String initialSnapshot) {
        this.current = new ActionNode(initialSnapshot);
    }

    public void record(String snapshot) {
        if (current.getSnapshot().equals(snapshot)) {
            return;
        }
        ActionNode newNode = new ActionNode(snapshot);
        newNode.prev = current;
        current.next = null;
        current.next = newNode;
        current = newNode;
    }
    

    public String undo() {
        if (current.getPrev() != null) {
            current = current.getPrev();
            return current.getSnapshot();
        }
        return null; // No more actions to undo
    }

    public String redo() {
        if (current.getNext() != null) {
            current = current.getNext();
            return current.getSnapshot();
        }
        return null; // No more actions to redo
    }

    public String getCurrentSnapshot() {
        return current.getSnapshot();
    }

    public boolean canUndo() {
        return current.getPrev() != null;
    }

    public boolean canRedo() {
        return current.getNext() != null;
    }
}
