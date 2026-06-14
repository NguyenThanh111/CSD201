package data;

public class ActionNode {
    String snapshot;
    ActionNode next;
    ActionNode prev;

    public ActionNode(String snapshot) {
        this.snapshot = snapshot;
        this.next = null;
        this.prev = null;
    }

    public String getSnapshot() {
        return snapshot;
    }
    public ActionNode getNext() {
        return next;
    }
    public ActionNode getPrev() {
        return prev;
    }
}
