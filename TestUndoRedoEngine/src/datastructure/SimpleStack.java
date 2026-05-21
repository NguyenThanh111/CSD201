package datastructure;

public class SimpleStack<T> {

    private Object[] elements;
    private int top;

    public SimpleStack() {
        elements = new Object[10];
        top = -1;
    }

    public void push(T value) {
        if (size() == elements.length) {
            expandCapacity();
        }
        top++;
        elements[top] = value;
    }

    @SuppressWarnings("unchecked")
    public T pop() {
        if (isEmpty()) {
            return null;
        }

        T value = (T) elements[top];
        elements[top] = null;
        top--;
        return value;
    }

    @SuppressWarnings("unchecked")
    public T peek() {
        if (isEmpty()) {
            return null;
        }
        return (T) elements[top];
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public int size() {
        return top + 1;
    }

    public void clear() {
        for (int i = 0; i <= top; i++) {
            elements[i] = null;
        }
        top = -1;
    }

    public String display() {
        if (isEmpty()) {
            return "[empty]";
        }

        StringBuilder builder = new StringBuilder();
        builder.append("Top -> ");
        for (int i = top; i >= 0; i--) {
            builder.append(elements[i]);
            if (i > 0) {
                builder.append(" | ");
            }
        }
        builder.append(" <- Bottom");
        return builder.toString();
    }

    private void expandCapacity() {
        Object[] newElements = new Object[elements.length * 2];
        for (int i = 0; i < elements.length; i++) {
            newElements[i] = elements[i];
        }
        elements = newElements;
    }
}
