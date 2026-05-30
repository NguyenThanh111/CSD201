# 📝 Member A — Nội dung Report 1
> **Chủ đề:** Undo/Redo Engine for Text Editors  
> **Môn:** CSD201 — Cấu trúc Dữ liệu & Giải thuật  
> **Thành viên:** Member A · Thành  
> **Nhiệm vụ:** Section 1.1 (Problem Statement) + Section 1.2.1 (Core Data Structures Table)

---

## SECTION 1.1 — Case Study Overview

### 🔷 Problem Statement

In modern text editing software such as Microsoft Word, Visual Studio Code, or Google Docs, users frequently need to **reverse or re-apply their editing actions** — a feature commonly known as Undo (Ctrl+Z) and Redo (Ctrl+Y). Without this mechanism, a single accidental keystroke or deletion could result in irreversible data loss, directly impacting developers, writers, students, and any user who works with text-based content on a daily basis.

The core challenge lies in **designing a data structure that can efficiently track the history of user actions** in a sequential, reversible manner. Specifically, the system must remember what action was performed, in what order, and be able to both reverse that action (Undo) and reapply it (Redo) — all within constant or near-constant time complexity.

From a data structure perspective, this problem requires a mechanism that respects **LIFO (Last In, First Out)** ordering, since the most recently performed action must always be the first one to be undone. This makes the **Stack** the natural and optimal data structure to model both the Undo and Redo histories of a text editor.

---

## SECTION 1.2 — Decomposition

### 🔷 1.2.1 Core Data Structures Table

Bảng dưới đây phân tích các module chính của hệ thống Undo/Redo Engine, cấu trúc dữ liệu tương ứng, và lý do lựa chọn từng cấu trúc đó:

| Module | Data Structure | Justification |
|---|---|---|
| **Undo Stack** | Stack (LIFO) | Mỗi hành động của người dùng được push vào Undo Stack. Khi Undo được gọi, hành động gần nhất (top of stack) sẽ được pop ra và đảo ngược. LIFO đảm bảo đúng thứ tự hoàn tác từ mới → cũ. |
| **Redo Stack** | Stack (LIFO) | Khi một hành động bị Undo, nó được chuyển sang Redo Stack. Nếu người dùng nhấn Redo (Ctrl+Y), hành động đó sẽ được pop từ Redo Stack và thực thi lại. Stack giúp duy trì đúng thứ tự làm lại. |
| **Command Object** | Object / Class | Mỗi hành động (gõ phím, xóa, paste...) được đóng gói thành một Command Object chứa đủ thông tin để thực thi lại (`execute()`) và đảo ngược (`undo()`). Đây là cốt lõi của **Command Pattern**. |
| **Editor State** | String / Buffer | Lưu trữ nội dung văn bản hiện tại của editor. Được đọc và cập nhật mỗi khi một Command được execute hoặc undo. Cấu trúc đơn giản (String/StringBuilder) giúp truy cập và sửa đổi nhanh. |
| **History Manager** | Two-Stack Structure | Lớp điều phối trung tâm quản lý cả hai stack. Khi hành động mới xảy ra, Redo Stack bị xóa (vì lịch sử redo không còn hợp lệ). Đảm bảo tính nhất quán giữa hai stack tại mọi thời điểm. |

---

## 💡 Ghi chú cho Member A

- **Problem Statement** nên được đặt ở đầu Section 1.1 trong file Word report, trình bày dạng văn xuôi (3–5 câu như trên).
- **Bảng Core Data Structures** đặt ở Section 1.2.1, format dạng bảng với 3 cột: Module | Data Structure | Justification.
- Khi ghép vào file Word chung, Member A là người tổng hợp → nên làm phần mình trước rồi tạo file Word nháp để các thành viên khác điền vào.
- Phần `Command Object` và `History Manager` cũng là nền tảng để nhóm viết code sau — giữ consistent với phần code nha!

---

*Được soạn theo yêu cầu Report 1 — CSD201 · SU2026 · Group 3*
