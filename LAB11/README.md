# LAB 2 - MÃ HÓA ĐỐI XỨNG HIỆN ĐẠI

## 1. Thông tin sinh viên
- **Họ và tên**: Đặng Dương Nguyên
- **Mã số sinh viên (MSSV)**: 1150080150
- **Lớp**: CNPM2 (11_DH_CNPM2)
- **Học phần**: An toàn và Bảo mật Hệ thống Thông tin
- **Tên bài thực hành**: Lab 2 – Mã hóa đối xứng hiện đại (DES, TripleDES, AES, RC4, Twofish)
- **Repository GitHub**: [https://github.com/Duongnguyen0808/LAB_AT_BMHTTT](https://github.com/Duongnguyen0808/LAB_AT_BMHTTT) (Thư mục: `LAB11`)

---

## 2. Tóm tắt & Mục tiêu bài thực hành

### 2.1. Tóm tắt
Bài thực hành giúp sinh viên tìm hiểu và xây dựng chương trình mã hóa, giải mã dữ liệu bằng các thuật toán mã hóa đối xứng hiện đại bao gồm **DES, TripleDES, AES (GCM, CTR, CBC), RC4 và Twofish**. Sinh viên sử dụng **Java Swing, Python và Microsoft SQL Server** để thiết kế giao diện, xử lý dữ liệu nhị phân, quản lý tài khoản người dùng bằng kỹ thuật băm an toàn và lưu vết lịch sử mã hóa.

### 2.2. Mục tiêu
- Hiểu rõ nguyên lý hoạt động của các thuật toán mã khối (Block Cipher) và mã dòng (Stream Cipher).
- Nắm vững các chế độ mã hóa: **CBC** (kết hợp Vector khởi tạo IV ngẫu nhiên), **CTR** (Counter Mode biến mã khối thành mã dòng không cần đệm), và **GCM** (Galois/Counter Mode - mã hóa kèm xác thực toàn vẹn bằng tag 128-bit).
- Xây dựng chương trình mã hóa và giải mã bằng Java (Swing) và Python (Tkinter).
- Thiết kế giao diện đồ họa, kết nối Microsoft SQL Server qua JDBC, lưu thông tin tài khoản (áp dụng PBKDF2-HMAC-SHA256 với salt 16-byte ngẫu nhiên) và lưu vết lịch sử thao tác.
- Kiểm thử toàn diện các trường hợp kiểm thử bắt buộc, phát hiện khóa sai và phát hiện dữ liệu bị sửa đổi.
- Quản lý mã nguồn chuyên nghiệp và nộp bài qua GitHub.

---

## 3. Môi trường & Công cụ thực hiện

| Phần mềm / Thư viện | Phiên bản khuyến nghị / Đã kiểm thử | Ghi chú |
| :--- | :--- | :--- |
| **JDK** | OpenJDK 17, 21 hoặc 25 | Trình biên dịch và máy ảo Java |
| **Apache NetBeans / VS Code** | NetBeans 20+ / VS Code | Môi trường phát triển Java |
| **Microsoft SQL Server** | SQL Server 2025 / 2022 Express | Cổng TCP/IP: 14333 (hoặc 1433) |
| **SSMS** | SQL Server Management Studio 20+ | Quản trị CSDL và chạy script |
| **Python** | Python 3.10+ | Chạy ứng dụng Tkinter |
| **Thư viện Java (Maven)** | `mssql-jdbc` 12.8.1.jre11, `bcprov-jdk18on` 1.78.1 | JDBC Driver và BouncyCastle |
| **Thư viện Python** | `cryptography >= 41.0.0` | Thư viện mật mã học chuyên dụng |

---

## 4. Cấu trúc thư mục nộp bài

```text
LAB11/
├── 03_SQL_SERVER/
│   ├── 01_Tao_CSDL.sql              # Script tạo CSDL LAB2_MaHoaHienDai và các bảng
│   ├── 02_Tao_Tai_Khoan_SQL.sql     # Script tạo Login lab2_user (pass: LabUser@2026)
│   └── 03_Kiem_Tra_Du_Lieu.sql      # Script kiểm tra dữ liệu sau khi mã hóa
├── images/                          # Thư mục hình ảnh minh chứng kiểm thử
├── LAB2_MaHoaHienDai/               # Dự án Java Maven NetBeans
│   ├── pom.xml                      # Cấu hình dependency Maven (MSSQL JDBC & BouncyCastle)
│   ├── run.bat                      # File thực thi chạy nhanh ứng dụng
│   ├── .gitignore                   # Cấu hình bỏ qua thư mục build target/
│   └── src/main/java/week_02/       # Giao diện Swing, file .form NetBeans và các lớp thuật toán
│       ├── CipherUtil.java          # Tiện ích chuyển đổi, Base64, Hex, PBKDF2
│       ├── FileTextIO.java          # Hộp thoại Open / Save file UTF-8
│       ├── DbConfig.java            # Cấu hình kết nối SQL Server
│       ├── HistoryDao.java          # Ghi/đọc lịch sử bảng dbo.crypto_history
│       ├── UserDao.java             # Đăng ký / Đăng nhập băm PBKDF2 + salt
│       ├── DESCipher.java           # Thuật toán DES (CBC + PKCS5 + IV)
│       ├── TripleDESCipher.java     # Thuật toán TripleDES (24-byte key)
│       ├── AESCipher.java           # Thuật toán AES-256 GCM (xác thực tag)
│       ├── RC4Cipher.java           # Thuật toán mã dòng RC4 (ra HEX)
│       ├── TwofishCipher.java       # Thuật toán Twofish (BouncyCastle)
│       ├── AESCTRCipher.java        # Thuật toán AES CTR (Counter Mode)
│       ├── SwingUiHelper.java       # Tiện ích dựng giao diện Swing nhất quán
│       ├── frm_*.java & frm_*.form  # Các màn hình JFrame và tệp cấu hình NetBeans GUI Builder
│       └── frm_Menu.java            # Cửa sổ chính điều hướng toàn bộ bài Lab
├── LAB2_Python/                     # Ứng dụng Python
│   ├── aes_cbc_gui.py               # Ứng dụng Tkinter AES-CBC & --self-test
│   └── requirements.txt             # Danh sách thư viện phụ thuộc (cryptography)
├── LAB11_JAVA_DH_CNPM2_1150080150_DangDuongNguyen.docx  # Báo cáo Word chi tiết từng bước
├── LAB2_HuongDan.docx               # Tài liệu hướng dẫn thực hành gốc của giảng viên
└── README.md                        # Hướng dẫn chi tiết chạy chương trình
```

---

## 5. Hướng dẫn thiết lập Cơ sở dữ liệu SQL Server

### Bước 5.1: Tạo CSDL và các bảng
Mở **SSMS**, kết nối `localhost\SQLEXPRESS` bằng quyền Windows Authentication, mở và chạy file `03_SQL_SERVER/01_Tao_CSDL.sql`.
- Tạo cơ sở dữ liệu `LAB2_MaHoaHienDai`.
- Tạo bảng `dbo.app_users`: lưu `username`, `salt` (16 byte), `password_hash` (32 byte dẫn xuất từ PBKDF2 120,000 vòng).
- Tạo bảng `dbo.crypto_history`: lưu vết `algorithm`, `operation`, `app_username`, `input_length`, `output_length` (tuyệt đối không lưu bản rõ hay khóa).

### Bước 5.2: Tạo tài khoản kết nối cho ứng dụng
Mở và chạy file `03_SQL_SERVER/02_Tao_Tai_Khoan_SQL.sql`:
- Tạo Login: `lab2_user` với mật khẩu `LabUser@2026`.
- Cấp quyền đọc/ghi (`db_datareader`, `db_datawriter`) trên CSDL `LAB2_MaHoaHienDai`.

### Bước 5.3: Kiểm tra cấu hình cổng TCP/IP
Đảm bảo cổng TCP/IP của SQL Server Express là `14333` (hoặc `1433` tùy máy). Kiểm tra bằng lệnh:
```powershell
Get-NetTCPConnection -State Listen | Where-Object { $_.LocalPort -in 1433, 14333 }
```

---

## 6. Hướng dẫn biên dịch và chạy chương trình Java

### 6.1. Chạy nhanh bằng `run.bat` (Khuyến nghị)
Mở Terminal tại thư mục `LAB11` rồi chạy từng form hoặc toàn bộ Menu chính:
```powershell
# Khởi chạy cửa sổ Menu chính frm_Menu
.\LAB2_MaHoaHienDai\run.bat frm_Menu

# Hoặc chạy kiểm thử riêng từng bài (từ Bước 5 đến Bước 12):
.\LAB2_MaHoaHienDai\run.bat frm_KetNoiSQL
.\LAB2_MaHoaHienDai\run.bat frm_DES
.\LAB2_MaHoaHienDai\run.bat frm_TripleDES
.\LAB2_MaHoaHienDai\run.bat frm_AES
.\LAB2_MaHoaHienDai\run.bat frm_RC4
.\LAB2_MaHoaHienDai\run.bat frm_Twofish
.\LAB2_MaHoaHienDai\run.bat frm_AES_CTR
```

### 6.2. Chạy bằng NetBeans hoặc Maven
1. Mở NetBeans -> **File -> Open Project** -> Chọn thư mục `LAB2_MaHoaHienDai`.
2. Nhấp chuột phải vào Project -> Chọn **Clean and Build** (`Shift+F11`). Maven sẽ tự động tải các gói `mssql-jdbc` và `bcprov-jdk18on`.
3. Nhấn **F6** (`Run Project`) để khởi chạy Menu chính (`frm_Menu`), hoặc `Shift+F6` (`Run File`) cho từng form.

---

## 7. Hướng dẫn chạy chương trình Python (AES-CBC)

### Bước 7.1: Cài đặt thư viện
```powershell
cd LAB2_Python
py -m pip install -r requirements.txt
```

### Bước 7.2: Chạy chế độ tự kiểm thử (Self-Test)
```powershell
py aes_cbc_gui.py --self-test
```
*Kết quả xuất hiện*:
```text
SELF-TEST OK: 4 thông điệp mã hóa/giải mã đúng, sai khóa bị phát hiện.
```

### Bước 7.3: Mở giao diện đồ họa Tkinter
```powershell
py aes_cbc_gui.py
```
- Bấm **Generate Key** để sinh khóa AES-256 ngẫu nhiên 32 byte (Base64).
- Bấm **Generate IV** để sinh Vector khởi tạo ngẫu nhiên 16 byte (Base64).
- Nhập văn bản vào ô Plaintext -> bấm **Encrypt** -> bản mã hiện ra trong ô Ciphertext.
- Bấm **Decrypt** -> khôi phục văn bản bản rõ ban đầu.
- Bấm **Save File** / **Open File** để lưu trữ và mở tệp có cấu trúc `IV=...\n<ciphertext>`.

---

## 8. Bảng kết quả kiểm thử bắt buộc (Bảng 2.1)

| Bài thực hành | Dữ liệu kiểm thử nhập vào | Kết quả quan sát được và đánh giá |
| :--- | :--- | :--- |
| **2.1 DES** | Plaintext: `Xin chào Lab 2 - Mã hóa hiện đại!`, Key: `12345678` | `Decrypt(Encrypt(x)) = x`. Mỗi lần Encrypt sinh ra chuỗi Base64 khác nhau do IV 8-byte ngẫu nhiên. Nhập Key 4 ký tự báo lỗi ngay. |
| **2.2 TripleDES** | Key: `123456789012345678901234` (24 ký tự ASCII) | `Decrypt(Encrypt(x)) = x`. Khi xóa 1 ký tự còn 23 ký tự -> ứng dụng chặn và thông báo: *'Khóa DESede phải đúng 24 byte'*. |
| **2.3 AES-GCM** | Đăng ký user `sinhvien01`, pass `MatKhau@2026`. Mã hóa với Key `MatKhauAES@2026` | Đăng nhập thành công, cấp Ticket 32 HEX; ghi nhận lịch sử vào SQL Server; khi sửa 1 bit bản mã, GCM lập tức quăng lỗi phát hiện giả mạo. |
| **2.4 RC4** | Plaintext: `Plaintext`, Key: `Key` | Bản mã HEX sinh ra chính xác: **`BBF316E8D940AF0AD3`** (khớp chuẩn NIST); giải mã ra đúng `Plaintext`. |
| **2.5 Twofish** | Key: `Twofish@Lab2`, IV sinh từ nút **Generate IV** (16 byte = 32 ký tự HEX) | Giải mã chính xác; tính năng Save/Open tệp lưu đúng cấu trúc 2 dòng gồm `IV=...` và bản mã; sai IV giải mã sai/báo lỗi. |
| **2.6 AES-CTR (Java)** | Key 32 byte Base64 và Nonce 12 byte Base64 ngẫu nhiên | Giải mã đúng văn bản dài; độ dài bản mã đúng bằng độ dài bản rõ (không cần đệm). |
| **2.6 AES-CBC (Python)** | Văn bản tiếng Việt dài > 1000 ký tự | Chạy `--self-test` in ra `SELF-TEST OK`; Giao diện Tkinter mã hóa và giải mã chính xác 100%. |

---

## 9. Bảng tổng hợp lỗi thường gặp và cách xử lý (Bảng 2.12)

1. **`cannot find symbol variable txt_key`**: Do tên điều khiển trên giao diện NetBeans chưa được đổi tên (`Change Variable Name...`). Cần đặt lại đúng tên biến như bảng hướng dẫn.
2. **`cannot find symbol class JOptionPane`**: Thiếu khai báo `import javax.swing.JOptionPane;` ở đầu file.
3. **`BadPaddingException / IllegalBlockSizeException`**: Xảy ra khi giải mã bằng sai khóa hoặc dữ liệu đệm bị hỏng. Cần nhập đúng khóa đã dùng để mã hóa.
4. **`AEADBadTagException: Tag mismatch`**: Xảy ra ở AES-GCM khi bản mã bị sửa đổi dù chỉ 1 bit hoặc sai khóa. Đây là cơ chế toàn vẹn dữ liệu của chế độ GCM.
5. **`TCP/IP connection failed to host localhost, port 14333`**: Dịch vụ SQL Server chưa bật giao thức TCP/IP hoặc cổng 14333 chưa mở. Mở *SQL Server Configuration Manager*, bật TCP/IP và cấu hình IPAll Port thành 14333, sau đó Restart dịch vụ MSSQL$SQLEXPRESS.
6. **`Login failed for user 'lab2_user'`**: Sai mật khẩu hoặc tài khoản SQL chưa tạo. Cần chạy lại file `02_Tao_Tai_Khoan_SQL.sql` với mật khẩu `LabUser@2026`.

---

## 10. Hướng dẫn nộp bài / cập nhật lên GitHub

Sau khi hoàn thành dán code, quay video kiểm thử và kiểm tra chạy ổn định:
```bash
# Di chuyển về thư mục gốc repository LAB_AT_BMHTTT
cd c:\Users\duong\Hoc\LAB_AT_BMHTTT

# Kiểm tra trạng thái và thêm toàn bộ mã nguồn đã hoàn thiện
git status
git add .

# Ghi lại commit bài nộp
git commit -m "Complete LAB11/LAB2 source code after video verification"

# Đẩy toàn bộ mã nguồn lên nhánh main của Duongnguyen0808/LAB_AT_BMHTTT
git push origin main
```
*Link thư mục bài nộp trên GitHub: [https://github.com/Duongnguyen0808/LAB_AT_BMHTTT/tree/main/LAB11](https://github.com/Duongnguyen0808/LAB_AT_BMHTTT/tree/main/LAB11).*
