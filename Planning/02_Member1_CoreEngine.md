# 🛠️ Member 1 — Leader / Core Engine Developer
**CSD201 — Undo/Redo Engine | Ước tính: ~12 giờ**

---

## 🎯 Trách nhiệm chính

Bạn là **Project Manager + Core Engine Developer**. Vai trò của bạn vừa là code phần khó nhất (Engine), vừa đảm bảo cả nhóm ráp code được với nhau.

---

## 📁 Files bạn sở hữu

| File | Trạng thái |
|---|---|
| `UndoRedoEngine.java` | **Bạn implement chính** |
| Integration giữa `TextEditor` ↔ `UndoRedoEngine` | **Bạn review và fix** |
| Final merge trước khi nộp | **Bạn chịu trách nhiệm** |

---

## ✅ Coding Checklist

### UndoRedoEngine.java

- [ ] Khai báo `undoStack` dùng `Deque<Action>` (ArrayDeque)
- [ ] Khai báo `redoStack` dùng `Deque<Action>` (ArrayDeque)
- [ ] Implement `performAction(Action action)`:
  - [ ] `undoStack.push(action)`
  - [ ] `redoStack.clear()` ← **đừng quên cái này**
  - [ ] Gọi `trimHistoryIfNeeded()` nếu có maxHistory
- [ ] Implement `undo(TextEditor editor)`:
  - [ ] Check `undoStack.isEmpty()` → in "Nothing to undo"
  - [ ] `action = undoStack.pop()`
  - [ ] Nếu `INSERT` → gọi `editor.deleteAt(..., false)`
  - [ ] Nếu `DELETE` → gọi `editor.insertAt(..., false)`
  - [ ] `redoStack.push(action)`
- [ ] Implement `redo(TextEditor editor)`:
  - [ ] Check `redoStack.isEmpty()` → in "Nothing to redo"
  - [ ] `action = redoStack.pop()`
  - [ ] Nếu `INSERT` → gọi `editor.insertAt(..., false)`
  - [ ] Nếu `DELETE` → gọi `editor.deleteAt(..., false)`
  - [ ] `undoStack.push(action)`
- [ ] Implement `printStacks()` để debug
- [ ] Implement `getUndoHistory()` và `getRedoHistory()`

---

## 📌 Code Skeleton — UndoRedoEngine.java

```java
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class UndoRedoEngine {
    private Deque<Action> undoStack;
    private Deque<Action> redoStack;
    private int maxHistory;

    public UndoRedoEngine(int maxHistory) {
        this.undoStack = new ArrayDeque<>();
        this.redoStack = new ArrayDeque<>();
        this.maxHistory = maxHistory;
    }

    public void performAction(Action action) {
        undoStack.push(action);
        redoStack.clear();          // ← CRITICAL: luôn clear redo khi có action mới
        trimHistoryIfNeeded();
    }

    public void undo(TextEditor editor) {
        if (undoStack.isEmpty()) {
            System.out.println("Nothing to undo.");
            return;
        }
        Action action = undoStack.pop();

        if (action.getType() == ActionType.INSERT) {
            editor.deleteAt(action.getPosition(), action.getContent().length(), false);
        } else if (action.getType() == ActionType.DELETE) {
            editor.insertAt(action.getPosition(), action.getContent(), false);
        }

        redoStack.push(action);
    }

    public void redo(TextEditor editor) {
        if (redoStack.isEmpty()) {
            System.out.println("Nothing to redo.");
            return;
        }
        Action action = redoStack.pop();

        if (action.getType() == ActionType.INSERT) {
            editor.insertAt(action.getPosition(), action.getContent(), false);
        } else if (action.getType() == ActionType.DELETE) {
            editor.deleteAt(action.getPosition(), action.getContent().length(), false);
        }

        undoStack.push(action);
    }

    private void trimHistoryIfNeeded() {
        if (maxHistory <= 0) return;
        while (undoStack.size() > maxHistory) {
            undoStack.removeLast(); // xóa action cũ nhất
        }
    }

    public List<Action> getUndoHistory() { return new ArrayList<>(undoStack); }
    public List<Action> getRedoHistory() { return new ArrayList<>(redoStack); }

    public void printStacks() {
        System.out.println("Undo Stack top -> " + undoStack);
        System.out.println("Redo Stack top -> " + redoStack);
    }
}
```

---

## ⚠️ 3 điều KHÔNG được quên

> Đây là nguồn gốc của 90% bugs trong project này:

**1. Phải `redoStack.clear()` trong `performAction()`**
```java
// SAI — thiếu clear
public void performAction(Action action) {
    undoStack.push(action);
    // THIẾU: redoStack.clear();
}

// ĐÚNG
public void performAction(Action action) {
    undoStack.push(action);
    redoStack.clear(); // ← bắt buộc
}
```

**2. Dùng `recordAction = false` khi undo/redo gọi buffer**
```java
// Trong undo():
editor.deleteAt(action.getPosition(), action.getContent().length(), false);
//                                                                  ^^^^^ false = không ghi action mới
```

**3. Kiểm tra `isEmpty()` trước `pop()`**
```java
if (undoStack.isEmpty()) {
    System.out.println("Nothing to undo.");
    return; // ← return ngay, không pop
}
```

---

## 📝 Report sections của bạn

Bạn chịu trách nhiệm viết các phần sau trong báo cáo:

- [ ] **Project Overview** — giới thiệu bài toán, tại sao chọn Stack
- [ ] **Algorithm Explanation** — giải thích `performAction()`, `undo()`, `redo()` bằng ngôn ngữ tự nhiên + pseudocode
- [ ] **Implementation: Core Engine** — mô tả `UndoRedoEngine.java`
- [ ] **Integration Summary** — mô tả cách các class kết nối với nhau

### Gợi ý viết Algorithm Explanation:

```
performAction(action):
    1. Push action vào undoStack
    2. Clear redoStack (action mới vô hiệu hóa lịch sử redo)

undo():
    1. Nếu undoStack rỗng → báo "Nothing to undo"
    2. Pop action mới nhất từ undoStack
    3. Áp dụng thao tác ngược lại lên text buffer
    4. Push action sang redoStack

redo():
    1. Nếu redoStack rỗng → báo "Nothing to redo"
    2. Pop action mới nhất từ redoStack
    3. Thực hiện lại action gốc lên text buffer
    4. Push action trở về undoStack
```

---

## 🗓️ Timeline của bạn

| Tuần | Việc cần làm | Output |
|---|---|---|
| Week 1 | Thiết kế engine logic + review Action của M2 | Flowchart, skeleton compile |
| Week 2 | Implement đầy đủ engine + integration test | Engine chạy đúng demo |
| Week 3 | Code review toàn nhóm + hỗ trợ report | Final merge |

---

## 🤝 Dependency với team

| Bạn cần từ | Việc cần |
|---|---|
| Member 2 | `Action.java` và `ActionType.java` phải xong **trước** khi bạn implement engine |
| Member 3 | `TextEditor.java` phải có `insertAt()` và `deleteAt()` với tham số `boolean recordAction` |

**Giao tiếp:** Nhắn Member 2 confirm interface `Action` trước cuối Week 1.

---

*Xem thêm context đầy đủ tại: `01_Project_Overview.md`*
