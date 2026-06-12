# 🖥️ Member 3 — UI / Simulation Layer
**CSD201 — Undo/Redo Engine | Ước tính: ~10 giờ**

---

## 🎯 Trách nhiệm chính

Bạn là **cầu nối giữa người dùng và engine**. Code của bạn làm cho project "sống" — người xem demo sẽ thấy kết quả qua console output của bạn. Bạn cũng implement `TextEditor.java` — class kết nối text buffer với Engine.

---

## 📁 Files bạn sở hữu

| File | Trách nhiệm |
|---|---|
| `TextEditor.java` | **Bạn implement chính** |
| `Main.java` | **Bạn implement chính** |
| `ConsoleMenu.java` | Optional — nếu nhóm muốn menu tương tác |

---

## ✅ Coding Checklist

### TextEditor.java

- [ ] Field `buffer` — kiểu `StringBuilder`
- [ ] Field `engine` — kiểu `UndoRedoEngine`
- [ ] Constructor nhận `int maxHistory`
- [ ] Implement `typeText(String text)` — append vào cuối buffer
- [ ] Implement `insertAt(int position, String content, boolean recordAction)`:
  - [ ] Validate position hợp lệ
  - [ ] `buffer.insert(position, content)`
  - [ ] Nếu `recordAction == true` → tạo `Action` và gọi `engine.performAction()`
- [ ] Implement `deleteText(int position, int length)` — gọi `deleteAt(..., true)`
- [ ] Implement `deleteAt(int position, int length, boolean recordAction)`:
  - [ ] Validate range hợp lệ
  - [ ] Lưu `deletedContent = buffer.substring(position, position + length)`
  - [ ] `buffer.delete(position, position + length)`
  - [ ] Nếu `recordAction == true` → tạo `Action` DELETE và gọi `engine.performAction()`
- [ ] Implement `undo()` — gọi `engine.undo(this)`
- [ ] Implement `redo()` — gọi `engine.redo(this)`
- [ ] Implement `getText()` — return `buffer.toString()`
- [ ] Implement `printState(String label)` — hiển thị text + stacks

---

## 📌 Code Skeleton — TextEditor.java

```java
public class TextEditor {
    private StringBuilder buffer;
    private UndoRedoEngine engine;

    public TextEditor(int maxHistory) {
        this.buffer = new StringBuilder();
        this.engine = new UndoRedoEngine(maxHistory);
    }

    // Gõ text vào cuối — dùng trong demo chính
    public void typeText(String text) {
        int position = buffer.length();
        insertAt(position, text, true);
    }

    // Insert với flag recordAction
    public void insertAt(int position, String content, boolean recordAction) {
        if (position < 0 || position > buffer.length()) {
            System.out.println("Invalid insert position: " + position);
            return;
        }
        buffer.insert(position, content);

        if (recordAction) {
            Action action = new Action(ActionType.INSERT, content, position);
            engine.performAction(action);
        }
    }

    // Delete với validation
    public void deleteText(int position, int length) {
        deleteAt(position, length, true);
    }

    public void deleteAt(int position, int length, boolean recordAction) {
        if (position < 0 || length <= 0 || position + length > buffer.length()) {
            System.out.println("Invalid delete range: position=" + position + ", length=" + length);
            return;
        }
        String deletedContent = buffer.substring(position, position + length);
        buffer.delete(position, position + length);

        if (recordAction) {
            Action action = new Action(ActionType.DELETE, deletedContent, position);
            engine.performAction(action);
        }
    }

    public void undo() { engine.undo(this); }
    public void redo() { engine.redo(this); }

    public String getText() { return buffer.toString(); }

    public void printState(String label) {
        System.out.println("\n=== " + label + " ===");
        System.out.println("Current Text: \"" + getText() + "\"");
        engine.printStacks();
    }
}
```

---

## 📌 Code Skeleton — Main.java

```java
public class Main {
    public static void main(String[] args) {
        TextEditor editor = new TextEditor(100);

        editor.printState("Initial State");

        // Demo sequence bắt buộc
        editor.typeText("H");   editor.printState("After typing H");
        editor.typeText("e");   editor.printState("After typing e");
        editor.typeText("l");   editor.printState("After typing l");
        editor.typeText("l");   editor.printState("After typing l (2nd)");
        editor.typeText("o");   editor.printState("After typing o");

        editor.undo();          editor.printState("After undo 1");
        editor.undo();          editor.printState("After undo 2");
        editor.redo();          editor.printState("After redo 1");

        editor.typeText("!");   editor.printState("After typing !");

        // Demo delete
        System.out.println("\n\n--- DELETE DEMO ---");
        editor.deleteText(0, 1); editor.printState("After delete at pos 0");
        editor.undo();           editor.printState("After undo delete");

        // Edge case demo
        System.out.println("\n\n--- EDGE CASE DEMO ---");
        editor.undo(); editor.undo(); editor.undo();
        editor.undo(); editor.undo(); editor.undo(); // "Nothing to undo" phải hiện
        editor.redo(); editor.redo(); editor.redo();
        editor.redo(); editor.redo(); editor.redo(); // "Nothing to redo" phải hiện
    }
}
```

---

## 🎯 Expected Output — Demo chính

```
=== Initial State ===
Current Text: ""
Undo Stack top -> []
Redo Stack top -> []

=== After typing H ===
Current Text: "H"
Undo Stack top -> [INSERT("H", pos=0, time=...)]
Redo Stack top -> []

... (tiếp tục tương tự) ...

=== After undo 1 ===
Current Text: "Hell"
Undo Stack top -> [INSERT("l", pos=3, time=...), ...]
Redo Stack top -> [INSERT("o", pos=4, time=...)]

=== After undo 2 ===
Current Text: "Hel"
Redo Stack top -> [INSERT("l", pos=3, ...), INSERT("o", pos=4, ...)]

=== After redo 1 ===
Current Text: "Hell"
Redo Stack top -> [INSERT("o", pos=4, ...)]

=== After typing ! ===
Current Text: "Hell!"
Redo Stack top -> []     ← redoStack đã bị clear!
```

---

## ⚠️ Điều QUAN TRỌNG về `recordAction`

> Đây là điểm hay gây nhầm lẫn nhất:

```java
// Khi USER gõ → recordAction = true → ghi vào history
public void typeText(String text) {
    insertAt(position, text, true); // ← true
}

// Khi ENGINE undo/redo → recordAction = false → KHÔNG ghi lại
// (vì undo/redo không phải là action mới của user)
editor.insertAt(action.getPosition(), action.getContent(), false); // ← false
```

**Nếu quên `false`:** undo sẽ tự ghi thêm action mới → lịch sử bị sai → bug khó debug.

---

## 📝 Report sections của bạn

- [ ] **Demo Scenario** — mô tả sequence demo chính, giải thích từng bước output
- [ ] **User Guide** — hướng dẫn cách chạy chương trình (compile + run lệnh)
- [ ] **Implementation: TextEditor và Main** — mô tả hai class này

### Gợi ý User Guide:
```
Compile:
  javac *.java

Run:
  java Main

Requirements: Java 8+
```

---

## 🗓️ Timeline của bạn

| Tuần | Việc cần làm | Output |
|---|---|---|
| Week 1 | Tạo skeleton `TextEditor.java` (chưa cần Engine) | File compile, buffer hoạt động |
| Week 2 | Connect với Engine (sau khi Member 1 xong) | Demo chạy được |
| Week 2 | Improve console output, thêm delete demo | Output dễ đọc |
| Week 3 | Viết User Guide + Demo section cho report | Report sections |

---

## 🤝 Dependency với team

| Bạn cần từ | Việc cần |
|---|---|
| Member 2 | `Action.java` + `ActionType.java` để dùng trong `TextEditor` |
| Member 1 | `UndoRedoEngine.java` để connect `engine.undo(this)` và `engine.redo(this)` |

**Tip:** Week 1 bạn có thể code phần buffer (`insertAt`, `deleteAt`, `getText`, `printState`) mà không cần Engine — chỉ cần comment out phần `engine.performAction()` tạm thời.

---

*Xem thêm context đầy đủ tại: `01_Project_Overview.md`*
