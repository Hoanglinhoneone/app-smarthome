# Smart Home: Điều Khiển Hệ Thống Đèn Thông Minh

Dự án **Smart Home** cho phép người dùng điều khiển hệ thống đèn thông minh trong ngôi nhà của mình thông qua một ứng dụng Android. Người dùng có thể bật/tắt đèn, điều chỉnh màu sắc của đèn và sử dụng các phương thức điều khiển khác nhau như thủ công, tự động và qua giọng nói.

## Công Nghệ Sử Dụng

- **Công cụ phát triển**: Android Studio
- **Logging**: Timber, Log
- **Database**:
  - Room Database
  - Firebase
- **Reactive Programming**: Kotlin, Flow, StateFlow, Coroutine
- **Navigation**: Navigation Drawer
- **UI**: Jetpack Compose, Material Design 3
- **Speech to Text**: Giọng nói để điều khiển đèn
- **Custom View**: Canvas (để tạo giao diện biểu đồ thống kê tùy chỉnh)
- **Dependency Injection**: Hilt

## Chức Năng Ứng Dụng:
### Trang chủ: 
- **Bật/Tắt Đèn Thủ Công**: Người dùng có thể bật/tắt đèn theo cách thủ công thông qua giao diện ứng dụng.
- **Bật/Tắt Đèn Tự Động**: Hệ thống có thể tự động bật/tắt đèn dựa trên các điều kiện môi trường.
- **Điều Khiển Qua Giọng Nói**: Ứng dụng hỗ trợ nhận dạng giọng nói để điều khiển bật/tắt đèn và thay đổi màu sắc.
- **Điều Khiển Màu Đèn**: Người dùng có thể thay đổi màu sắc của đèn thông qua giao diện ứng dụng hoặc qua giọng nói.
### Thống kê:
- **Thống kê dữ liệu**: Người dùng có thể theo dõi dữ liệu ánh sáng thông qua biểu đồ
### Lịch sử:
- **Hiển thị lịch sử dữ liệu ánh sáng**: Người dùng có thể theo dõi dữ liệu ánh sáng thông qua biểu đồ
- **Hiển thị lịch sử hành động của hệ thống**: Người dùng có thể theo dõi các hành động trước đó của ứng dụng: bật/tắt đèn, chế độ auto, đổi màu đèn...
### Cài Đặt
- **Thay đổi giới hạn bật/tắt đèn của chế độ auto**: Người dùng có thể tùy chỉnh giá trị giới hạn bật/tắt đèn cho ứng dụng
- **Bật chế độ Dynamic cho ứng dụng**: Người dùng có thể bật tính năng màu động (dynamic) cho ứng dụng
- **Xóa dữ liệu ánh sáng / hành động**: Người dùng có thể xóa dữ liệu của ứng dụng
### Clone Dự Án
```bash
git clone https://github.com/your-username/smart-home-app.git
cd smart-home-app
