package data;

public class TextEditor {
    private StringBuilder content;
    private ActionHistory actionHistory;

    public TextEditor() {
        this.content = new StringBuilder();
        this.actionHistory = new ActionHistory(content.toString());
    }

    public void insert(int position, String text) {
        if (text == null) {
            throw new IllegalArgumentException("Text to insert cannot be null");
        }
        if (position < 0 || position > content.length()) {
            throw new IllegalArgumentException("Position out of bounds");
        }
        content.insert(position, text);
        actionHistory.record(content.toString());
    }

    public void delete(int position, int length) {
        if (position < 0 || position > content.length() || length < 0 || position + length > content.length()) {
            throw new IllegalArgumentException("Position or length out of bounds");
        }
        content.delete(position, position + length);
        actionHistory.record(content.toString());
    }

    public void undo() {
        String previousSnapshot = actionHistory.undo();
        if (previousSnapshot != null) {
            content = new StringBuilder(previousSnapshot);
        }
    }

    public void redo() {
        String nextSnapshot = actionHistory.redo();
        if (nextSnapshot != null) {
            content = new StringBuilder(nextSnapshot);
        }
    }

    public String getContent() {
        return content.toString();
    }
}
