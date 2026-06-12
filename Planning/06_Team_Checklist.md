# ✅ Team Checklist — CSD201 Undo/Redo Engine
**Dùng để theo dõi tiến độ chung mỗi tuần**

---

## 📊 TRẠNG THÁI TỔNG QUAN

> Cập nhật cột Status khi hoàn thành: ⬜ Chưa làm | 🔄 Đang làm | ✅ Xong

| # | Hạng mục | Owner | Status |
|---|---|---|---|
| 1 | `ActionType.java` | Member 2 | ⬜ |
| 2 | `Action.java` | Member 2 | ⬜ |
| 3 | `UndoRedoEngine.java` | Member 1 | ⬜ |
| 4 | `TextEditor.java` | Member 3 | ⬜ |
| 5 | `Main.java` | Member 3 | ⬜ |
| 6 | Stack State Diagram | Member 2 | ⬜ |
| 7 | Flowchart | Member 1 | ⬜ |
| 8 | Class Diagram | Member 4 | ⬜ |
| 9 | Sequence Diagram | Member 3 | ⬜ |
| 10 | Report: Introduction | Member 4 | ⬜ |
| 11 | Report: CT Analysis | Member 2 | ⬜ |
| 12 | Report: Data Structure Design | Member 2 | ⬜ |
| 13 | Report: Algorithm Explanation | Member 1 | ⬜ |
| 14 | Report: Implementation | Member 1 + 3 | ⬜ |
| 15 | Report: Testing | Member 4 | ⬜ |
| 16 | Report: Conclusion | Member 4 | ⬜ |
| 17 | Final code review | Member 1 | ⬜ |
| 18 | Demo script | All | ⬜ |

---

## 📅 WEEK 1 CHECKPOINT

**Mục tiêu:** Design + Skeleton code

### Phải xong trước cuối Week 1:

- [ ] `ActionType.java` — Member 2 ✔ (dễ, làm đầu tiên)
- [ ] `Action.java` — Member 2 ✔ (các member khác cần file này)
- [ ] Skeleton `UndoRedoEngine.java` — Member 1 (chưa cần implement đầy đủ, chỉ cần có method signatures)
- [ ] Skeleton `TextEditor.java` — Member 3 (phần buffer, chưa cần connect Engine)
- [ ] Class diagram bản đầu — Member 4
- [ ] Flowchart bản đầu — Member 1
- [ ] Stack State Diagram bản đầu — Member 2
- [ ] Report outline — Member 4 + Member 1

### Milestone M1:
```
✅ Action.java + ActionType.java compile được
✅ All 5 Java files tồn tại và compile (dù chưa đầy đủ)
✅ Diagrams bản nháp
✅ Demo sequence đã thống nhất
```

---

## 📅 WEEK 2 CHECKPOINT

**Mục tiêu:** Core implementation + Testing

### Phải xong trước cuối Week 2:

- [ ] `UndoRedoEngine.java` đầy đủ — Member 1
  - [ ] `performAction()` với `redoStack.clear()`
  - [ ] `undo()` hoạt động đúng
  - [ ] `redo()` hoạt động đúng
- [ ] `TextEditor.java` đầy đủ — Member 3
  - [ ] `insertAt()` với `recordAction` flag
  - [ ] `deleteAt()` với `recordAction` flag
  - [ ] Connect với Engine
- [ ] `Main.java` demo sequence chạy được — Member 3
- [ ] Test cases Expected Result được điền — Member 4
- [ ] LIFO verify test — Member 2

### Demo Sequence phải chạy đúng:
```
Type H → Type e → Type l → Type l → Type o
Undo → Undo → Redo → Type !

Kết quả: Hello → Hell → Hel → Hell → Hell!
```

### Milestone M2:
```
✅ Demo chính chạy đúng hoàn toàn
✅ Undo INSERT/DELETE hoạt động
✅ redoStack clear đúng sau action mới
✅ Edge cases không crash
✅ Test cases Expected Result đã điền
```

---

## 📅 WEEK 3 CHECKLIST

**Mục tiêu:** Polish + Report + Submission

### Code:
- [ ] Console output dễ đọc — Member 3
- [ ] Code cleanup (remove test prints, add comments) — Member 1
- [ ] Final integration test — All

### Report:
- [ ] Introduction hoàn chỉnh — Member 4
- [ ] CT Analysis hoàn chỉnh — Member 2
- [ ] Data Structure Design hoàn chỉnh — Member 2
- [ ] Algorithm Explanation hoàn chỉnh — Member 1
- [ ] Implementation section hoàn chỉnh — Member 1 + 3
- [ ] Testing section (với Actual Result) — Member 4
- [ ] Conclusion hoàn chỉnh — Member 4
- [ ] References — Member 4
- [ ] Format nhất quán toàn bộ report — Member 4

### Diagrams:
- [ ] Tất cả Mermaid render đúng trong Obsidian — Member 4 verify

### Submission:
- [ ] Tất cả Java files compile không có warning — Member 1
- [ ] Demo chạy lại lần cuối — Member 3
- [ ] File nộp được đóng gói — Member 1

### Milestone M3:
```
✅ Working code
✅ Report hoàn chỉnh
✅ Diagrams đầy đủ
✅ Test case table hoàn chỉnh
✅ Demo script
✅ File nộp đã kiểm tra
```

---

## 🚨 BUG CHECKLIST — Kiểm tra trước khi nộp

Đây là 7 lỗi phổ biến nhất. Chạy qua từng cái trước khi nộp:

| # | Bug | Cách kiểm tra | Fixed? |
|---|---|---|---|
| 1 | `redoStack.clear()` bị thiếu trong `performAction()` | Type → Undo → Type mới → Redo phải in "Nothing to redo" | ⬜ |
| 2 | `recordAction = false` bị thiếu trong undo/redo | Undo rồi check: undoStack không được thêm action mới | ⬜ |
| 3 | Không check `isEmpty()` trước `pop()` | Undo/Redo khi stack rỗng phải không crash | ⬜ |
| 4 | Action không lưu `content` | Undo DELETE phải khôi phục đúng ký tự | ⬜ |
| 5 | Action không lưu `position` | Undo INSERT phải xóa đúng vị trí | ⬜ |
| 6 | Delete không validate position | Delete ở pos 999 khi text chỉ có 5 ký tự phải in lỗi | ⬜ |
| 7 | Report chỉ mô tả code, không giải thích thuật toán | Đọc lại report: có giải thích "Tại sao Stack?" chưa? | ⬜ |

---

## 🎤 DEMO SCRIPT (3–5 phút)

> Cả nhóm cùng thống nhất ai nói phần nào

### Slide / Flow gợi ý:

**[Member 4 hoặc Member 1 — ~30s]**
> "Our project simulates the Undo/Redo mechanism in text editors. We use two stacks: undoStack for performed actions and redoStack for undone actions."

**[Member 2 — ~1 phút]**
> "We chose Stack because Undo always reverses the most recent action first — this is exactly the Last In, First Out behavior of Stack. Each action stores type, content, and position so we can reverse it precisely."

**[Member 3 — chạy demo — ~2 phút]**
> Chạy `java Main`, giải thích output theo từng dòng:
> - "Here we type Hello, you can see undoStack growing..."
> - "After two undos, text becomes Hel and redoStack has 2 actions..."
> - "After redo, l is restored..."
> - "After typing !, redoStack is cleared — this is the key behavior."

**[Member 1 — ~30s]**
> "If anyone has questions about the engine logic or the algorithm design, I can explain."

---

## 💬 Q&A — Câu hỏi thường gặp từ giảng viên

| Câu hỏi | Câu trả lời tốt |
|---|---|
| "Tại sao dùng Stack?" | "Because Undo reverses the most recent action first — LIFO behavior." |
| "Tại sao cần 2 Stack?" | "undoStack tracks what to undo; redoStack enables redo after undo." |
| "Redo mất khi nào?" | "When a new action is performed after undo — we call redoStack.clear()." |
| "Action lưu gì?" | "Type (INSERT/DELETE), content (what was typed), position (where)." |
| "recordAction = false là gì?" | "It prevents undo/redo from recording itself as a new action in history." |

---

*Files này được tạo từ file planning gốc: CSD201_Project_UndoRedo_Planning.md*
*Thư mục: CSD201/ | Dùng trong Obsidian hoặc bất kỳ Markdown viewer nào*
