# LAB 4 – THỰC HÀNH NMAP

README hướng dẫn nhanh, đi kèm file Word hướng dẫn từng bước. Thực hiện trong mạng **Host-Only của chính bạn**, không quét mạng công cộng hoặc máy ngoài phạm vi lab.

## 1. Môi trường

| Thiết bị | Vai trò | IP đã quan sát |
|---|---|---|
| Windows 11 (máy thật) | Chạy VirtualBox; có thể chạy Nmap/Zenmap nếu bài yêu cầu | Kiểm tra bằng `ipconfig` (sơ đồ minh họa: `192.168.56.1`) |
| Kali Linux | Máy quét Nmap 7.99 | `192.168.56.102/24` |
| Metasploitable 2 | Máy đích cố ý có lỗ hổng | `192.168.56.103/24` |

**Quan trọng:** Đây là các IP từ ảnh cấu hình trước đó; kiểm tra lại mỗi khi khởi động máy. Chỉ kết nối Metasploitable 2 với **Host-Only**, không dùng Bridged/NAT. File `nmap-7.99-1.x86_64.rpm` không dùng để cài trên Windows hoặc Kali; Kali đã có Nmap.

## 2. Chuẩn bị

- [ ] Bật Kali và Metasploitable 2 trong VirtualBox; kiểm tra cả hai dùng cùng mạng Host-Only.
- [ ] Tạo snapshot `Before-LAB4` cho mỗi VM khi máy đã tắt (nếu chưa tạo).
- [ ] Trên Windows, chạy `ipconfig` và tìm adapter VirtualBox Host-Only; ghi IP thật.
- [ ] Trên Kali, chạy `nmap --version` và `ip -br addr`.
- [ ] Trên Metasploitable 2, đăng nhập `msfadmin` / `msfadmin`, chạy `ifconfig`.
- [ ] Trên Kali, chạy `ping -c 4 192.168.56.103` để kiểm tra kết nối.

## 3. Thực hành theo thứ tự

**Tất cả lệnh dưới đây chạy trên Terminal Kali** (trừ khi có ghi khác). Nếu IP máy đích thay đổi, thay `192.168.56.103` bằng IP thực tế.

### A. Phát hiện host

```bash
nmap -sn 192.168.56.0/24
```

Ghi host UP, IP, MAC/vendor (nếu có) và đối chiếu với các máy ảo và adapter Windows.

### B. Quét TCP và so sánh

```bash
nmap -sT 192.168.56.103
sudo nmap -sS 192.168.56.103
sudo nmap -sF 192.168.56.103
sudo nmap -sX 192.168.56.103
sudo nmap -sN 192.168.56.103
sudo nmap -sA 192.168.56.103
```

Chạy **từng lệnh**, chờ hoàn tất và ghi trạng thái cổng, thời gian quét. `open|filtered` không đồng nghĩa với `open`; ACK scan không dùng để xác nhận cổng mở.

### C. Quét UDP, nhận diện dịch vụ và hệ điều hành

```bash
sudo nmap -sU --top-ports 20 192.168.56.103
nmap -sV 192.168.56.103
sudo nmap -O 192.168.56.103
sudo nmap -A 192.168.56.103
```

Ghi phiên bản dịch vụ thực tế, độ tin cậy của OS detection và sự khác biệt về thời gian/lưu lượng.

### D. NSE: kiểm tra SMB

```bash
sudo nmap -p 445 --script smb-os-discovery 192.168.56.103
sudo nmap -p 445 --script smb-vuln-ms17-010 192.168.56.103
```

Chỉ kết luận có dấu hiệu dễ bị ảnh hưởng khi output thực sự báo `VULNERABLE`. Timeout, lỗi hoặc không xác định **không** chứng minh máy đã vá.

### E. Xuất kết quả

```bash
mkdir -p ~/LAB4_Nmap
cd ~/LAB4_Nmap
nmap -sV 192.168.56.103 -oN services.txt
nmap -sV 192.168.56.103 -oX services.xml
nmap -p 445 192.168.56.103 -oG smb.txt
grep '445/open' smb.txt
nmap -sV 192.168.56.103 -oA lab4
ls -lh
```

Nếu máy đã có `xsltproc`, có thể tạo HTML bằng `xsltproc services.xml -o services.html`.

### F. Trước và sau hardening

Dùng **Windows VM của bạn hoặc dịch vụ thử nghiệm do giảng viên cấp** trên mạng Host-Only. Chọn một dịch vụ/cổng thử nghiệm, quét trước, tắt dịch vụ hoặc đổi rule firewall, rồi quét lại với cùng tùy chọn:

```bash
nmap -sV -p PORT IP_WINDOWS_VM -oN before.txt
# Thực hiện thay đổi phòng thủ trên Windows VM
nmap -sV -p PORT IP_WINDOWS_VM -oN after.txt
```

Thay `PORT` và `IP_WINDOWS_VM` bằng giá trị thật. So sánh `open`, `closed`, `filtered` và dịch vụ trước/sau. Không thực hiện thay đổi này trên Windows máy thật nếu không có kế hoạch khôi phục.

## 4. Tám ảnh minh chứng bắt buộc

- [ ] **Ảnh 01:** `ip -br addr` trên Kali, thấy card Host-Only và IP.
- [ ] **Ảnh 02:** `ifconfig` trên Metasploitable 2, thấy IP máy đích.
- [ ] **Ảnh 03:** kết quả host discovery `nmap -sn`.
- [ ] **Ảnh 04:** kết quả `-sT` **hoặc** `-sS`.
- [ ] **Ảnh 05:** kết quả `-sV`.
- [ ] **Ảnh 06:** kết quả `-O` **hoặc** `-A`.
- [ ] **Ảnh 07:** kết quả một NSE script **và kết luận dựa trên output**.
- [ ] **Ảnh 08:** tệp kết quả `.txt`/`.xml` và/hoặc HTML (nếu có).

**Nên chụp thêm:** ping, FIN/Xmas/NULL, ACK, UDP, SMB và before/after hardening nếu giảng viên yêu cầu làm đầy đủ các mục.

## 5. Bài tập bổ sung (nếu được yêu cầu)

1. So sánh `-sT`, `-sS`, `-sA` trên cùng mục tiêu.
2. Quét 65.535 cổng TCP bằng `sudo nmap -sS -p- 192.168.56.103 -oN all_ports.txt` và so sánh với quét mặc định.
3. Từ `-sV`, tra cứu CVE có nguồn cho ít nhất một phiên bản dịch vụ cũ (nếu có).
4. So sánh NSE MS17-010 trên Metasploitable 2 và Windows VM đã cập nhật (nếu có).
5. So sánh quét thường với decoy `-D` **chỉ trong lab được phép**; quan sát log.
6. Dùng `-oA` và so sánh `.nmap`, `.xml`, `.gnmap`.
7. Lập bản đồ host/cổng/dịch vụ của các máy **được phép** trong mạng Host-Only.

## 6. Gợi ý bố cục báo cáo

1. Mục tiêu và sơ đồ mạng (phân biệt IP minh họa và IP thật).
2. Môi trường, phiên bản phần mềm và kiểm tra kết nối.
3. Host discovery và so sánh TCP/UDP.
4. Nhận diện dịch vụ, hệ điều hành và NSE.
5. Xuất kết quả, lưu minh chứng.
6. Trước/sau hardening; bài tập bổ sung nếu yêu cầu.
7. Trả lời câu hỏi phân tích và kết luận dựa trên kết quả thực tế.

**Lưu ý:** README là checklist thao tác nhanh; xem file Word hướng dẫn LAB 4 để có bảng ghi kết quả và câu hỏi phân tích chi tiết. Không sao chép IP, số cổng, phiên bản hoặc kết luận mẫu thành kết quả thực nghiệm nếu output của bạn khác.
