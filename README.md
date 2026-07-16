# Text Editor Undo/Redo Engine (EngineForTextEditor)

Dự án này triển khai một trình soạn thảo văn bản mô phỏng chạy trên Console với cơ chế **Undo (Hoàn tác)** và **Redo (Làm lại)** sử dụng ngôn ngữ Java.

---

## 📁 Cấu trúc thư mục mã nguồn

Mã nguồn được đặt trong thư mục `src/` và chia làm hai gói (package) chính:

```text
EngineForTextEditor/
├── src/
│   ├── data/                 # Chứa Logic nghiệp vụ và Cấu trúc dữ liệu
│   │   ├── ActionNode.java   # Nút lưu trữ ảnh chụp trạng thái văn bản (Snapshot)
│   │   ├── ActionHistory.java# Danh sách liên kết kép quản lý lịch sử trạng thái
│   │   └── TextEditor.java   # Lớp lõi xử lý soạn thảo (StringBuilder buffer)
│   │
│   └── runtime/              # Giao diện tương tác người dùng
│       └── Program.java      # Giao diện CLI Console & điều phối nhập liệu
│
└── build/                    # Thư mục chứa mã biên dịch .class
```

---

## ⚙️ Cơ chế hoạt động của Engine

Trình soạn thảo này sử dụng cấu trúc **Danh sách liên kết kép (Doubly Linked List)** lưu trữ **Snapshot (Ảnh chụp toàn bộ trạng thái)** của văn bản ở mỗi bước thay đổi, thay vì lưu các hành động chênh lệch (Delta Actions) truyền thống.

### 1. Mô hình liên kết các nút (ASCII Art)
```text
[ Trạng thái ban đầu: "" ] <===> [ Nhập "Hello" ] <===> [ Nhập "Hello World" ] (current)
```

*   **Khi nhập mới (`record`):** Tạo node mới, liên kết `prev` của nó với node hiện tại, trỏ `next` của node hiện tại vào node mới, rồi chuyển con trỏ `current` sang node mới.
*   **Khi Undo:** Di chuyển con trỏ `current` lùi lại phía trước qua con trỏ `prev` (`current = current.prev`). Văn bản hiển thị được cập nhật theo snapshot tại nút đó.
*   **Khi Redo:** Di chuyển con trỏ `current` tiến lên qua con trỏ `next` (`current = current.next`).
*   **Khi ghi đè lịch sử:** Nếu đang ở trạng thái đã Undo và người dùng gõ nội dung mới, nhánh lịch sử cũ phía trước (nhánh Redo cũ) sẽ bị đứt liên kết và được giải phóng khỏi bộ nhớ bởi Java Garbage Collector.
    ```text
    [ Trạng thái ban đầu ] <===> [ Nhập "Hello" ] <===> [ Nhập "Hello Java" ] (current)
                                                             └─x─> [ Nhập "Hello World" ] (Bị xóa)
    ```

---

## 🛠️ Hướng dẫn Biên dịch và Chạy ứng dụng

Bạn có thể biên dịch và khởi chạy trực tiếp dự án từ dòng lệnh bằng cách sử dụng Java JDK được cài đặt sẵn trên máy:

### 1. Lệnh biên dịch (Compile)
Mở terminal tại thư mục `D:\CSD\CSD201\EngineForTextEditor` và chạy lệnh sau để biên dịch toàn bộ các lớp vào thư mục `build`:
```bash
& "C:\Program Files\Java\jdk1.8.0_202\bin\javac.exe" -d build src/data/ActionNode.java src/data/ActionHistory.java src/data/TextEditor.java src/runtime/Program.java
```

### 2. Lệnh khởi chạy (Run)
Sau khi biên dịch thành công, chạy lệnh dưới đây để khởi động chương trình CLI:
```bash
& "C:\Program Files\Java\jdk1.8.0_202\bin\java.exe" -cp build runtime.Program
```

---

## 🛡️ Các cải tiến về Tính an toàn (Robustness & Safety)
*   **Null Safety:** Ngăn chặn việc chèn giá trị `null` vào văn bản (tránh chèn chuỗi ký tự `"null"` mặc định của StringBuilder).
*   **Sửa lỗi điều kiện biên xóa:** Cho phép xóa 0 ký tự ở cuối chuỗi hoặc trên chuỗi rỗng bình thường mà không gây lỗi crash.
*   **Chống crash đầu vào:** Bổ sung khối lệnh `try-catch` bắt lỗi `InputMismatchException` (nhập sai kiểu dữ liệu chữ thay vì số) và `IllegalArgumentException` (nhập vị trí vượt biên) giúp chương trình chạy liên tục, báo lỗi rõ ràng thay vì đột ngột dừng hoạt động.
