# CSD201 - Data Structures and Algorithms Workspace Summary

Tài liệu này tóm tắt toàn bộ bối cảnh dự án, cấu trúc thư mục và cơ chế hoạt động của mã nguồn hiện tại trong thư mục `D:\CSD\CSD201` để các AI Agent hoặc lập trình viên khác có thể tiếp cận nhanh nhất.

---

## 📌 Tổng quan Workspace (Workspace Overview)
Workspace này phục vụ cho môn học **CSD201 (Cấu trúc dữ liệu và Giải thuật)** học kỳ **SU26**.
Trọng tâm dự án thực hành là xây dựng **Undo/Redo Engine cho Text Editor** viết bằng Java, bên cạnh các tài liệu lập kế hoạch nghiên cứu giải thuật cho các bài toán thực tế khác.

---

## 🗂️ Cấu trúc thư mục (Directory Structure)

```text
D:\CSD\CSD201\
├── CSD201/                      # Đề xuất & Lập kế hoạch nghiên cứu các đề tài
│   ├── CSD201_Research_EVRentalStation_Planning.md       --> Bài toán xe điện (Dijkstra + Heap)
│   ├── CSD201_Research_LibrarySeatManagement_Planning.md --> Bài toán ghế thư viện (Priority Queue)
│   └── Project Planning.md                               --> Định hướng dự án Undo/Redo
│
├── Planning/                    # Phân rã công việc & Hướng dẫn cho nhóm 4 thành viên (Undo/Redo)
│   ├── 01_Project_Overview.md   --> Tổng quan dự án, phân chia vai trò & timeline
│   ├── 02_Member1_CoreEngine.md --> Hướng dẫn code phần Engine chính
│   ├── 03_Member2_DataStructure.md --> Hướng dẫn thiết kế Action Node & phân tích CT
│   ├── 04_Member3_UILayer.md    --> Hướng dẫn viết giao diện Text Editor
│   └── 05_Member4_Report_Lite.md --> Hướng dẫn kiểm thử & viết báo cáo gọn nhẹ
│
├── EngineForTextEditor/         # Dự án Java chứa mã nguồn thực tế của Text Editor
│   ├── src/
│   │   ├── data/                # Chứa các lớp logic nghiệp vụ & Cấu trúc dữ liệu
│   │   │   ├── ActionNode.java
│   │   │   ├── ActionHistory.java
│   │   │   └── TextEditor.java
│   │   └── runtime/             # Chứa giao diện tương tác dòng lệnh CLI
│   │       └── Program.java
│   └── build.xml
│
├── TestUndoRedoEngine/          # Thư mục chứa các bản dựng thử nghiệm
└── SU26_CSD201_Additional.xlsx  # Bảng theo dõi tiến độ/điểm số của lớp học
```

---

## ⚙️ Cơ chế hoạt động của Undo/Redo Engine thực tế

Mã nguồn Java hiện tại trong `EngineForTextEditor/` cài đặt cơ chế Undo/Redo sử dụng **Danh sách liên kết kép (Doubly Linked List) chứa Snapshot trạng thái**, thay vì mô hình Two-Stack truyền thống.

### 1. Luồng dữ liệu (Data Flow)
*   **Trạng thái ban đầu:** Hệ thống tạo một node gốc lưu chuỗi rỗng `""`. Con trỏ `current` trỏ vào node này.
*   **Khi ghi lại hành động (`record`):** Mỗi khi người dùng chèn hoặc xóa chữ, hệ thống lấy bản sao chuỗi mới nhất, đóng gói vào một `ActionNode` mới, liên kết `prev` của nút mới với `current`, gán `current.next` trỏ đến nút mới, và chuyển con trỏ `current` sang nút mới này.
*   **Khi Undo:** Di chuyển con trỏ `current` lùi lại phía trước qua con trỏ `prev` (`current = current.prev`).
*   **Khi Redo:** Di chuyển con trỏ `current` tiến lên phía sau qua con trỏ `next` (`current = current.next`).

### 2. Mô hình liên kết các nút (ASCII Art)
```text
[ Trạng thái ban đầu ] <===> [ Nhập "Hello" ] <===> [ Nhập "Hello World" ] (current)
```
*   Nếu **Undo**:
    ```text
    [ Trạng thái ban đầu ] <===> [ Nhập "Hello" ] (current) <===> [ Nhập "Hello World" ]
    ```
*   Nếu **Redo**: con trỏ `current` di chuyển tiến lên trở lại.
*   Nếu **Nhập mới sau khi Undo** (ví dụ nhập `"Hello Java"`): Nhánh cũ `"Hello World"` bị đứt liên kết và bị giải phóng bộ nhớ (Garbage Collector).
    ```text
    [ Trạng thái ban đầu ] <===> [ Nhập "Hello" ] <===> [ Nhập "Hello Java" ] (current)
                                                             └─x─> [ Nhập "Hello World" ] (GC thu hồi)
    ```

### ⚠️ Lưu ý sự sai lệch (Planning vs Implementation)
*   **Trong tài liệu Planning (`01_Project_Overview.md`):** Dự kiến thiết kế theo mô hình **Two-Stack (undoStack & redoStack)** lưu trữ các hành động chênh lệch (Delta Actions) để tiết kiệm bộ nhớ.
*   **Trong mã nguồn thực tế:** Triển khai bằng **Doubly Linked List lưu Snapshot**. Phương án này lập trình đơn giản hơn nhưng tiêu tốn bộ nhớ hơn khi dung lượng văn bản tăng cao.

---

## 🚀 Hướng dẫn chạy thử nghiệm (Execution & Compile)
*   **Entrypoint:** [Program.java](file:///D:/CSD/CSD201/EngineForTextEditor/src/runtime/Program.java)
*   *Lưu ý khi biên dịch:* Tệp [Program.java](file:///D:/CSD/CSD201/EngineForTextEditor/src/runtime/Program.java) (thuộc package `runtime`) sử dụng `TextEditor` (thuộc package `data`). Cần đảm bảo có dòng `import data.TextEditor;` ở đầu tệp `Program.java` để tránh lỗi biên dịch.
