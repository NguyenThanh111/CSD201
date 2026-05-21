package model;

public class TextDocument {

    private final StringBuilder content;

    public TextDocument() {
        content = new StringBuilder();
    }

    public void insert(int position, String text) {
        if (!isPositionValidForInsert(position)) {
            throw new IllegalArgumentException("Vi tri chen khong hop le.");
        }
        content.insert(position, text);
    }

    public String delete(int position, int length) {
        validateRange(position, length);

        String removedText = content.substring(position, position + length);
        content.delete(position, position + length);
        return removedText;
    }

    public String replace(int position, int length, String newText) {
        validateRange(position, length);

        String oldText = content.substring(position, position + length);
        content.replace(position, position + length, newText);
        return oldText;
    }

    public void clear() {
        content.setLength(0);
    }

    public int length() {
        return content.length();
    }

    public String getContent() {
        return content.toString();
    }

    private boolean isPositionValidForInsert(int position) {
        return position >= 0 && position <= content.length();
    }

    private void validateRange(int position, int length) {
        if (position < 0) {
            throw new IllegalArgumentException("Vi tri khong duoc am.");
        }
        if (length < 0) {
            throw new IllegalArgumentException("Do dai khong duoc am.");
        }
        if (position + length > content.length()) {
            throw new IllegalArgumentException("Vi tri va do dai vuot qua noi dung hien tai.");
        }
    }
}
