# 📋 Project Overview & Đánh Giá Planning
**CSD201 — Undo/Redo Engine for Text Editor**

---

## ✅ ĐÁNH GIÁ FILE PLANNING GỐC

> Nhận xét từ góc độ Senior Developer + Lecturer

### Điểm mạnh

| Hạng mục | Đánh giá |
|---|---|
| Cấu trúc tổng thể | ⭐⭐⭐⭐⭐ — Rất rõ ràng, đầy đủ 8 bước |
| Computational Thinking | ⭐⭐⭐⭐⭐ — Trình bày 4 trụ cột CT chuẩn, phù hợp DSA |
| Data Structure Design | ⭐⭐⭐⭐⭐ — Two-Stack Model được mô tả chính xác |
| Code Skeleton | ⭐⭐⭐⭐⭐ — Starter code Java chạy được, đúng hướng |
| Diagram | ⭐⭐⭐⭐ — Mermaid đầy đủ, cần render để kiểm tra |
| Task Assignment | ⭐⭐⭐⭐ — Cân bằng, nhưng cần điều chỉnh cho member bận |
| Tips & Pitfalls | ⭐⭐⭐⭐⭐ — Rất thực tế, đúng lỗi sinh viên hay gặp |

### Điểm cần điều chỉnh

1. **Phân công Member 4** — Hiện tại 12h, khá nhiều. Cần cắt bớt CT Analysis (phần phức tạp) và giao cho member khác rảnh hơn.
2. **File quá dài cho nhóm** — 1917 dòng, khó theo dõi khi cả nhóm cùng đọc. Nên tách thành file riêng cho từng người.
3. **Không có deadline cụ thể** — Roadmap có Week 1/2/3 nhưng chưa có ngày thật. Nhóm nên điền vào.

---

## 🗂️ CẤU TRÚC FILE ĐÃ TÁCH

Planning gốc đã được tách thành **6 file** phù hợp cho từng thành viên:

```
CSD201/
├── 00_AI_Agent_Prompt.md        ← Prompt dùng khi hỏi AI
├── 01_Project_Overview.md       ← File này — Tổng quan + đánh giá
├── 02_Member1_CoreEngine.md     ← Hướng dẫn Member 1 (Leader)
├── 03_Member2_DataStructure.md  ← Hướng dẫn Member 2
├── 04_Member3_UILayer.md        ← Hướng dẫn Member 3
├── 05_Member4_Report_Lite.md    ← Hướng dẫn Member 4 (bận — phần nhẹ)
└── 06_Team_Checklist.md         ← Checklist tích hợp cuối tuần
```

---

## 👥 PHÂN CÔNG ĐÃ ĐIỀU CHỈNH

> Lưu ý: Member 4 đang đi làm nên được giảm tải so với planning gốc.

| Member | Vai trò | Giờ ước tính | File hướng dẫn |
|---|---|---:|---|
| Member 1 (Leader) | Core Engine + Integration | ~12h | `02_Member1_CoreEngine.md` |
| Member 2 | Data Structure Specialist | ~10h | `03_Member2_DataStructure.md` |
| Member 3 | UI / Simulation Layer | ~10h | `04_Member3_UILayer.md` |
| Member 4 ⚠️ | Report (phần nhẹ) + Testing | ~6–7h | `05_Member4_Report_Lite.md` |

**Phần CT Analysis** (vốn của Member 4) được chuyển sang **Member 2** thực hiện vì liên quan chặt đến Data Structure.

---

## 🏗️ KIẾN TRÚC TỔNG THỂ

```
Main.java
   │
   └─► TextEditor.java          (Member 3)
           │
           ├─► StringBuilder     (text buffer)
           │
           └─► UndoRedoEngine.java    (Member 1)
                   │
                   ├─► undoStack: Deque<Action>
                   ├─► redoStack: Deque<Action>
                   │
                   └─► Action.java         (Member 2)
                           ├── ActionType (enum)
                           ├── content (String)
                           └── position (int)
```

---

## 📅 TIMELINE (điền ngày thật vào)

| Tuần | Mục tiêu | Milestone |
|---|---|---|
| Week 1 | Design + Skeleton code | M1: Class diagram + code compile được |
| Week 2 | Core implement + Testing | M2: Undo/Redo chạy đúng demo chính |
| Week 3 | Polish + Report + Nộp | M3: Final deliverable ready |

---

## ⚠️ QUY TẮC NHÓM (bắt buộc thực hiện)

1. **Không code engine trước khi thống nhất `Action` object** — Member 2 phải define xong `Action.java` trước.
2. **Member 3 không connect với Engine cho đến khi Member 1 xong `performAction()`**
3. **Họp checkpoint cuối mỗi tuần** — dù ngắn, dù online, phải có.
4. **Member 4** chỉ cần deliver đúng phần được giao, không cần làm thêm.

---

## 🔑 CÂU TRẢ LỜI MÀU NHÓ KHI THẦY CÔ HỎI

> Cả nhóm nên thuộc câu này trước khi demo:

**"Tại sao dùng Stack cho Undo/Redo?"**

```
Because Undo always reverses the most recent action first,
which matches the Last In, First Out behavior of Stack.
We use two stacks: undoStack stores performed actions,
and redoStack stores undone actions for potential redo.
```

---

*Được tạo từ file planning gốc: CSD201_Project_UndoRedo_Planning.md*
