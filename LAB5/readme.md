# LAB pfSense -- Hướng dẫn thực hành

## 1. Phạm vi bài làm

Bài thực hành xây dựng mô hình tường lửa pfSense gồm ba vùng mạng:

-   WAN: kết nối ra mạng ngoài.
-   LAN: mạng nội bộ `10.0.0.0/8`.
-   DMZ: mạng máy chủ `172.16.0.0/16`.

Phần thực hiện trong bài nộp:

-   Cấu hình nền tảng pfSense.
-   Cấu hình Domain Controller.
-   Cấu hình Ubuntu LAN-Test.
-   Cấu hình DMZ-Web và IIS.
-   Kiểm tra Outbound NAT.
-   Chuẩn hóa firewall rule.
-   Tình huống 1: Chặn ICMP nhưng vẫn cho Web/DNS.
-   Tình huống 2: Chỉ cho một host cụ thể ra Internet.
-   Tình huống 3: Cô lập DMZ khỏi LAN.

**Không thực hiện Tình huống 4 và Tình huống 5.**

## 2. Mô hình địa chỉ IP

  -----------------------------------------------------------------------------
  Thiết bị       Interface      IP                Gateway        DNS
  -------------- -------------- ----------------- -------------- --------------
  pfSense        WAN            DHCP              Upstream       Upstream

  pfSense        LAN            `10.0.0.1/8`      \-             \-

  pfSense        DMZ            `172.16.0.1/16`   \-             \-

  Domain         LAN            `10.0.0.2/8`      `10.0.0.1`     `10.0.0.2`
  Controller                                                     

  Ubuntu         LAN            `10.0.0.3/8`      `10.0.0.1`     Theo môi
  LAN-Test                                                       trường

  Máy thật quản  Host-Only #2   `10.0.0.100/8`    Không đặt      Không đặt
  trị                                                            

  DMZ-Web        DMZ            `172.16.0.2/16`   `172.16.0.1`   `8.8.8.8` khi
                                                                 được phép
                                                                 Internet
  -----------------------------------------------------------------------------

## 3. Máy ảo sử dụng

### pfSense

-   RAM: khoảng 2 GB.
-   CPU: 2 vCPU.
-   Adapter 1: Bridged Adapter -- WAN.
-   Adapter 2: Host-Only Adapter #2 -- LAN.
-   Adapter 3: Internal Network `dmz-net` -- DMZ.

### Domain Controller

-   Windows Server.
-   IP: `10.0.0.2/8`.
-   Gateway: `10.0.0.1`.
-   DNS: `10.0.0.2`.
-   Cài AD DS và DNS Server.

### Ubuntu LAN-Test

-   IP: `10.0.0.3/8`.
-   Gateway: `10.0.0.1`.
-   Dùng để kiểm thử Tình huống 2.

### DMZ-Web

-   Windows Server.
-   IP: `172.16.0.2/16`.
-   Gateway: `172.16.0.1`.
-   Adapter: Internal Network `dmz-net`.
-   Cài IIS.
-   Không join domain.

## 4. Các lệnh kiểm thử chính

### Domain Controller

``` text
ping 10.0.0.1
ping 8.8.8.8
Resolve-DnsName example.com
curl.exe -4 https://example.com
```

### Ubuntu LAN-Test

``` text
ip -br addr
ping -c 4 10.0.0.1
ping -c 4 8.8.8.8
```

### DMZ-Web

Cài IIS:

``` text
Install-WindowsFeature Web-Server -IncludeManagementTools
```

Kiểm tra IIS:

``` text
curl.exe http://localhost
```

## 5. Tình huống 1 -- Chặn ICMP nhưng vẫn cho Web/DNS

Thứ tự rule trên LAN:

1.  `Block ICMP | LAN net -> Any`
2.  `Pass TCP/UDP 53 | LAN net -> Any`
3.  `Pass TCP 80/443 | LAN net -> Any`

Sau khi cấu hình phải **Apply Changes** và **Reset States**.

Kiểm thử:

``` text
ping 8.8.8.8
Resolve-DnsName example.com -Server 8.8.8.8
curl.exe -4 https://example.com
```

Kết quả mong đợi:

-   Ping thất bại.
-   DNS thành công.
-   HTTPS thành công.

## 6. Tình huống 2 -- Chỉ cho một host cụ thể ra Internet

Rule trên LAN:

1.  `Pass Any | 10.0.0.2 -> Any`
2.  `Block Any | LAN net -> Any`

Kiểm thử:

-   Domain Controller `10.0.0.2`: `ping 8.8.8.8` phải thành công.
-   Ubuntu `10.0.0.3`: `ping -c 4 8.8.8.8` phải thất bại.

## 7. Tình huống 3 -- Cô lập DMZ khỏi LAN

Trên Domain Controller cho phép ICMP tạm thời:

``` text
netsh advfirewall firewall add rule name="LAB-Allow-ICMPv4-Echo" protocol=icmpv4:8,any dir=in action=allow
```

### Baseline

Ban đầu trên DMZ chỉ để:

`Pass Any | DMZ net -> Any`

Từ DMZ-Web:

``` text
ping 10.0.0.2
```

Ping phải thành công trước khi thêm rule Block.

### Cô lập DMZ

Rule:

1.  `Block Any | DMZ net -> LAN net`
2.  `Pass Any | DMZ net -> Any`

Apply Changes và Reset States.

Kiểm thử lại:

``` text
ping 10.0.0.2
ping 8.8.8.8
```

Kết quả mong đợi:

-   DMZ -\> DC thất bại.
-   DMZ vẫn có thể ra Internet nếu NAT/rule/DNS đã cấu hình đúng.

Xóa rule ICMP tạm trên DC sau khi kiểm thử:

``` text
netsh advfirewall firewall delete rule name="LAB-Allow-ICMPv4-Echo"
```

## 8. Ảnh minh chứng quan trọng

Không cần chụp mọi thao tác. Ưu tiên các ảnh sau:

1.  pfSense có đủ 3 adapter WAN/LAN/DMZ.
2.  Console pfSense hiển thị LAN `10.0.0.1/8`.
3.  Status \> Interfaces hiển thị WAN, LAN và DMZ đều up.
4.  Domain Controller có IP `10.0.0.2/8`.
5.  Ubuntu LAN-Test có IP `10.0.0.3/8`.
6.  DMZ-Web có IP `172.16.0.2/16` và IIS hoạt động.
7.  Outbound NAT cho LAN/DMZ.
8.  Ruleset LAN nền tảng.
9.  Tình huống 1: ảnh rule + ping fail + DNS/HTTPS success.
10. Tình huống 2: ảnh rule + DC success + Ubuntu fail.
11. Tình huống 3: baseline DMZ -\> DC thành công, ảnh rule Block, và DMZ
    -\> DC thất bại sau khi Block.

## 9. File báo cáo

File Word đi kèm:

`LAB_pfSense_Full_Step_by_Step_TH1_TH2_TH3.docx`

Trong file Word đã có hướng dẫn chi tiết từng bước, lệnh kiểm thử, kết
quả mong đợi và vị trí cần chụp ảnh.

## 10. Lưu ý

-   Thực hiện trong môi trường máy ảo của bài lab.
-   Sau mỗi lần thay đổi firewall rule quan trọng: **Apply Changes →
    Reset States** trước khi kiểm thử lại.
-   Rule pfSense được xét từ trên xuống, vì vậy thứ tự rule rất quan
    trọng.
-   Ảnh trong báo cáo phải là ảnh kết quả thực tế sau khi thực hiện trên
    máy của sinh viên.
