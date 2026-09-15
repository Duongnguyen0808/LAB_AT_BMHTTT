# LAB 1 - Bắt gói tin Telnet - SSH

## 1. Thông tin sinh viên

- Họ và tên: Đặng Dương Nguyên
- MSSV: 1150080150
- Lớp: 11CNPM2
- Môn học: An toàn hệ thống thông tin
- Tên bài Lab: Lab 1 - Bắt gói tin Telnet - SSH

## 2. Mục tiêu bài Lab

Bài thực hành nhằm thiết lập môi trường Client/Server sử dụng Telnet và SSH, đồng thời sử dụng Wireshark để bắt và phân tích lưu lượng mạng.

Các mục tiêu chính:

- Thiết lập kết nối Telnet và SSH giữa Client và Server.
- Sử dụng Wireshark để bắt gói tin.
- Quan sát dữ liệu truyền qua Telnet.
- Quan sát lưu lượng SSH sau khi mã hóa.
- So sánh mức độ an toàn giữa Telnet và SSH.
- Thực nghiệm SSH public-key authentication.

## 3. Môi trường thực hành

### Client

- Hệ điều hành: Windows 11
- PuTTY
- Wireshark 4.6.8

### Server

- Ubuntu Server 26.04.1 LTS
- Chạy trên Oracle VirtualBox
- Telnet Server: `inetutils-telnetd`
- SSH Server: `openssh-server`

### Mạng

- Adapter 1: NAT
- Adapter 2: Host-Only Adapter
- IP Windows Host-Only: `192.168.56.1`
- IP Ubuntu Server: `192.168.56.101`

## 4. Nội dung đã thực hiện

### 4.1. Thiết lập Ubuntu Server

- Tạo máy ảo Ubuntu Server trên VirtualBox.
- Cấu hình NAT và Host-Only Adapter.
- Kiểm tra kết nối giữa Windows Client và Ubuntu Server bằng `ping`.

### 4.2. Thực hành Telnet

Tạo tài khoản thử nghiệm:

```bash
sudo adduser uitlab
```

Cài đặt Telnet Server:

```bash
sudo apt update
sudo apt install inetutils-telnetd inetutils-inetd update-inetd -y
```

Kiểm tra TCP port 23:

```bash
ss -ltn | grep ':23'
```

Trên Windows Client:

- Mở Wireshark.
- Chọn interface Host-Only.
- Sử dụng display filter:

```text
tcp.port == 23
```

Kết nối bằng PuTTY:

```text
Host Name: 192.168.56.101
Port: 23
Connection type: Telnet
```

Sau khi đăng nhập bằng user `uitlab`, thực hiện một số lệnh cơ bản như:

```bash
pwd
ls
mkdir test
ls
```

Sau đó sử dụng Wireshark:

```text
Follow -> TCP Stream
```

để phân tích nội dung phiên Telnet.

### 4.3. Thực nghiệm Telnet với mật khẩu mạnh

Thay đổi mật khẩu của user:

```bash
sudo passwd uitlab
```

Sau đó thực hiện lại quá trình Telnet và bắt gói.

Kết quả cho thấy việc sử dụng mật khẩu mạnh không bổ sung mã hóa cho Telnet. Nếu lưu lượng được bắt đúng vị trí, dữ liệu trao đổi vẫn có thể bị quan sát.

### 4.4. Thực hành SSH

Cài OpenSSH Server:

```bash
sudo apt install openssh-server -y
sudo systemctl enable --now ssh
```

Kiểm tra trạng thái:

```bash
systemctl status ssh
```

Trên Wireshark sử dụng filter:

```text
tcp.port == 22
```

Kết nối SSH bằng PuTTY:

```text
Host Name: 192.168.56.101
Port: 22
Connection type: SSH
```

Sau khi đăng nhập bằng user `uitlab`, thực hiện:

```bash
pwd
ls
mkdir ssh_test
ls
```

Kết quả Wireshark cho thấy có thể quan sát IP, port, kích thước và thời gian gói tin nhưng không thể đọc username, password hoặc nội dung lệnh dưới dạng plaintext như Telnet.

### 4.5. SSH Public-Key Authentication

Tạo cặp khóa trên Windows:

```powershell
ssh-keygen -t ed25519
```

Public key được thêm vào file:

```text
~/.ssh/authorized_keys
```

trên Ubuntu Server.

Kiểm tra đăng nhập:

```powershell
ssh uitlab@192.168.56.101
```

Kết quả: Client có thể xác thực bằng SSH key thay vì chỉ dựa vào password.

## 5. Kết quả thực hiện

Bài Lab đã hoàn thành các nội dung:

- [x] Ubuntu Server hoạt động trên VirtualBox.
- [x] Windows Client kết nối được tới Ubuntu Server.
- [x] Telnet Server hoạt động trên TCP/23.
- [x] Kết nối Telnet bằng PuTTY thành công.
- [x] Bắt và phân tích lưu lượng Telnet bằng Wireshark.
- [x] Thực nghiệm Telnet với mật khẩu phức tạp.
- [x] OpenSSH Server hoạt động trên TCP/22.
- [x] Kết nối SSH bằng PuTTY thành công.
- [x] Bắt và phân tích lưu lượng SSH bằng Wireshark.
- [x] Thực nghiệm SSH public-key authentication.
- [x] So sánh được sự khác biệt bảo mật giữa Telnet và SSH.

## 6. Nhận xét

Telnet và SSH đều cho phép quản trị hệ thống từ xa, tuy nhiên mức độ bảo mật khác nhau rõ rệt.

Telnet không mã hóa nội dung phiên nên dữ liệu có thể bị quan sát khi lưu lượng được bắt đúng vị trí.

SSH mã hóa payload của phiên, vì vậy Wireshark không thể đọc plaintext của username, password và nội dung lệnh. Tuy nhiên một số metadata như IP nguồn, IP đích, port, thời gian và kích thước gói vẫn có thể được quan sát.

SSH public-key authentication giúp giảm sự phụ thuộc vào mật khẩu và phù hợp hơn cho các hệ thống quản trị thực tế.

## 7. Cấu trúc thư mục

```text
LAB_AT_BMHTTT/
└── LAB1/
    ├── README.md
    ├── Lab1_Lop_MSSV_TenSV.docx
    ├── images/
    ├── telnet.pcapng
    └── ssh.pcapng
```

## 8. Hướng dẫn kiểm tra

Giảng viên có thể kiểm tra bài Lab theo thứ tự:

1. Mở báo cáo Word trong thư mục `LAB1`.
2. Kiểm tra các ảnh thực nghiệm Telnet và SSH.
3. Mở file `.pcapng` bằng Wireshark nếu có.
4. Với Telnet, dùng filter:

```text
tcp.port == 23
```

5. Với SSH, dùng filter:

```text
tcp.port == 22
```

6. Kiểm tra phần `Follow TCP Stream` của phiên Telnet.
7. Đối chiếu với phiên SSH để thấy payload SSH đã được mã hóa.
8. Kiểm tra phần demo SSH public-key authentication trong báo cáo/video.

## 9. Video thực hành

Link video YouTube:

https://youtu.be/1sjzteZLrh4

## 10. Lưu ý

- Toàn bộ ảnh trong báo cáo là ảnh từ quá trình thực hành thực tế.
- Telnet chỉ được sử dụng trong mạng Lab nội bộ.
- Không mở TCP/23 ra Internet.
- Các file `.pcapng`, ảnh kết quả và báo cáo cần được lưu đúng trong thư mục `LAB1`.
