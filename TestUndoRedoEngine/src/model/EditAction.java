package model;

public class EditAction {

    private final ActionType type;
    private final int position;
    private final String oldText;
    private final String newText;

    public EditAction(ActionType type, int position, String oldText, String newText) {
        this.type = type;
        this.position = position;
        this.oldText = oldText == null ? "" : oldText;
        this.newText = newText == null ? "" : newText;
    }

    public ActionType getType() {
        return type;
    }

    public int getPosition() {
        return position;
    }

    public String getOldText() {
        return oldText;
    }

    public String getNewText() {
        return newText;
    }

    @Override
    public String toString() {
        return type + "(pos=" + position
                + ", old=\"" + oldText + "\""
                + ", new=\"" + newText + "\")";
    }
}
