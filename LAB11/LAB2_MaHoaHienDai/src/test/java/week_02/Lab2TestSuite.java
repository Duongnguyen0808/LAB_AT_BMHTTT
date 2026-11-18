package week_02;

import java.awt.GraphicsEnvironment;
import java.io.BufferedReader;
import java.io.Console;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Arrays;
import java.util.Base64;
import java.util.function.Supplier;
import javax.crypto.AEADBadTagException;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/**
 * Bo kiem thu toan dien (Integration & Unit Test Suite) cho toan bo LAB 2 / LAB 11:
 *  - Tien ich: CipherUtil (UTF-8, Base64, Hex, Random, PBKDF2)
 *  - Thuat toan: DES, TripleDES, AES-256-GCM, RC4 (NIST vector), Twofish, AES-256-CTR
 *  - Giao dien Swing: khoi tao an toan 9 JFrame forms
 *  - Co so du lieu SQL Server (tuy chon mat khau): DbConfig, UserDao, HistoryDao
 */
public final class Lab2TestSuite {

    private static final String SAMPLE_VI = "Xin chào Lab 2 - Mã hóa hiện đại!";
    private static int total = 0;
    private static int passed = 0;
    private static int failed = 0;
    private static int skipped = 0;

    @FunctionalInterface
    private interface ThrowingRunnable {
        void run() throws Exception;
    }

    private Lab2TestSuite() {
    }

    public static void main(String[] args) throws Exception {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        System.setErr(new PrintStream(System.err, true, StandardCharsets.UTF_8));

        System.out.println("==================================================================");
        System.out.println("   BO KIEM THU TOAN BAI - LAB 2 / LAB 11 (MA HOA DOI XUNG)");
        System.out.println("   Sinh vien: Dang Duong Nguyen | MSSV: 1150080150 | Lop: CNPM2");
        System.out.println("==================================================================");

        testCipherUtil();
        testDes();
        testTripleDes();
        testAesGcm();
        testRc4();
        testTwofish();
        testAesCtr();
        testSwingForms();
        testSqlServer(resolveDbPassword(args));

        System.out.println("\n==================================================================");
        System.out.printf("TONG KET JAVA TEST: Tong = %d | Dat (PASS) = %d | Loi (FAIL) = %d | Bo qua (SKIP) = %d%n",
                total, passed, failed, skipped);
        System.out.println("==================================================================");

        if (failed > 0) {
            System.exit(1);
        }
    }

    // ------------------------- TEST CASES -------------------------

    private static void testCipherUtil() {
        section("1. Lop tien ich CipherUtil (UTF-8, Base64, Hex, PBKDF2)");

        check("UTF-8 round-trip tieng Viet co dau & chuoi rong", () -> {
            assertEquals(SAMPLE_VI, CipherUtil.text(CipherUtil.utf8(SAMPLE_VI)), "UTF-8 tieng Viet");
            assertEquals("", CipherUtil.text(CipherUtil.utf8("")), "UTF-8 chuoi rong");
        });

        check("Base64 encode/decode & chan Base64 sai dinh dang", () -> {
            byte[] raw = CipherUtil.utf8(SAMPLE_VI);
            String encoded = CipherUtil.b64(raw);
            assertTrue(Arrays.equals(raw, CipherUtil.fromB64("  " + encoded + " \n")), "Base64 round-trip");
            assertThrows(IllegalArgumentException.class, () -> CipherUtil.fromB64("not_valid_base64!@#"));
        });

        check("Hex encode/decode & chan Hex le hoac ky tu la", () -> {
            byte[] data = new byte[]{(byte) 0xBB, (byte) 0xF3, 0x00, 0x1A, (byte) 0xFF};
            String hex = CipherUtil.hex(data);
            assertEquals("BBF3001AFF", hex, "Hex chu hoa");
            assertTrue(Arrays.equals(data, CipherUtil.fromHex("bbf3001aff")), "Hex decode chu thuong");
            assertThrows(IllegalArgumentException.class, () -> CipherUtil.fromHex("ABC"));
            assertThrows(IllegalArgumentException.class, () -> CipherUtil.fromHex("ZZZZ"));
        });

        check("randomBytes, concat, requireKey & PBKDF2-HMAC-SHA256", () -> {
            byte[] r1 = CipherUtil.randomBytes(16);
            byte[] r2 = CipherUtil.randomBytes(16);
            assertEquals(16, r1.length, "Do dai randomBytes");
            assertTrue(!Arrays.equals(r1, r2), "Hai lan randomBytes phai khac nhau");

            byte[] merged = CipherUtil.concat(new byte[]{1, 2}, new byte[]{3, 4, 5});
            assertTrue(Arrays.equals(new byte[]{1, 2, 3, 4, 5}, merged), "Noi mang concat");

            assertThrows(IllegalArgumentException.class, () -> CipherUtil.requireKey(null));
            assertThrows(IllegalArgumentException.class, () -> CipherUtil.requireKey(""));

            byte[] salt = CipherUtil.randomBytes(16);
            byte[] k1 = CipherUtil.pbkdf2("MatKhau@2026".toCharArray(), salt, 120_000, 256);
            byte[] k2 = CipherUtil.pbkdf2("MatKhau@2026".toCharArray(), salt, 120_000, 256);
            byte[] k3 = CipherUtil.pbkdf2("MatKhau@2026".toCharArray(), CipherUtil.randomBytes(16), 120_000, 256);
            assertEquals(32, k1.length, "PBKDF2 256-bit");
            assertTrue(Arrays.equals(k1, k2), "Cung salt phai sinh cung khoa");
            assertTrue(!Arrays.equals(k1, k3), "Khac salt phai sinh khac khoa");
        });
    }

    private static void testDes() {
        section("2. Bai 2.1 - Mat ma DES (CBC + PKCS5Padding + IV 8 byte)");

        check("Ma hoa & giai ma DES dung khoa 8 byte (co dau tieng Viet)", () -> {
            String c1 = DESCipher.encrypt(SAMPLE_VI, "12345678");
            String c2 = DESCipher.encrypt(SAMPLE_VI, "12345678");
            assertTrue(!c1.equals(c2), "Moi lan Encrypt phai cho ban ma khac (IV ngau nhien)");
            assertEquals(SAMPLE_VI, DESCipher.decrypt(c1, "12345678"), "Decrypt(Encrypt(x)) == x");
        });

        check("Chan khoa DES sai do dai (4 byte, 9 byte, co dau > 8 byte)", () -> {
            assertThrows(IllegalArgumentException.class, () -> DESCipher.encrypt(SAMPLE_VI, "1234"));
            assertThrows(IllegalArgumentException.class, () -> DESCipher.encrypt(SAMPLE_VI, "123456789"));
            assertThrows(IllegalArgumentException.class, () -> DESCipher.encrypt(SAMPLE_VI, "ábcdefgh"));
        });

        check("Phat hien sai khoa hoac ban ma hong khi giai ma DES", () -> {
            String cipher = DESCipher.encrypt(SAMPLE_VI, "12345678");
            assertDecryptDiffersOrFails(() -> DESCipher.decrypt(cipher, "87654321"), SAMPLE_VI, "DES sai khoa");
            assertThrows(IllegalArgumentException.class, () -> DESCipher.decrypt("AAAA", "12345678"));
        });
    }

    private static void testTripleDes() {
        section("3. Bai 2.2 - Mat ma TripleDES / 3DES (24 byte key)");
        String validKey = "123456789012345678901234";

        check("Ma hoa & giai ma TripleDES dung khoa 24 byte", () -> {
            String c1 = TripleDESCipher.encrypt(SAMPLE_VI, validKey);
            String c2 = TripleDESCipher.encrypt(SAMPLE_VI, validKey);
            assertTrue(!c1.equals(c2), "IV 3DES phai ngau nhien");
            assertEquals(SAMPLE_VI, TripleDESCipher.decrypt(c1, validKey), "3DES round-trip");
        });

        check("Chan khoa TripleDES sai do dai (23 byte, 25 byte, 8 byte)", () -> {
            assertThrows(IllegalArgumentException.class, () -> TripleDESCipher.encrypt(SAMPLE_VI, "12345678901234567890123"));
            assertThrows(IllegalArgumentException.class, () -> TripleDESCipher.encrypt(SAMPLE_VI, validKey + "5"));
            assertThrows(IllegalArgumentException.class, () -> TripleDESCipher.encrypt(SAMPLE_VI, "12345678"));
        });

        check("Giai ma TripleDES sai khoa", () -> {
            String cipher = TripleDESCipher.encrypt(SAMPLE_VI, validKey);
            String wrongKey = "12345678901234567890123X";
            assertDecryptDiffersOrFails(() -> TripleDESCipher.decrypt(cipher, wrongKey), SAMPLE_VI, "3DES sai khoa");
        });
    }

    private static void testAesGcm() {
        section("4. Bai 2.3 - AES-256-GCM (PBKDF2 + Xac thuc Tag 128-bit)");

        check("Ma hoa & giai ma AES-256-GCM + kiem tra cau truc (salt || nonce || ct+tag)", () -> {
            String c1 = AESCipher.encrypt(SAMPLE_VI, "MatKhauAES@2026");
            String c2 = AESCipher.encrypt(SAMPLE_VI, "MatKhauAES@2026");
            assertTrue(!c1.equals(c2), "Salt/nonce moi lan ma hoa khac nhau");
            byte[] raw = Base64.getDecoder().decode(c1);
            int expectedLen = 16 + 12 + CipherUtil.utf8(SAMPLE_VI).length + 16;
            assertEquals(expectedLen, raw.length, "Do dai ban ma AES-GCM");
            assertEquals(SAMPLE_VI, AESCipher.decrypt(c1, "MatKhauAES@2026"), "AES-GCM round-trip");
        });

        check("Phat hien sai mat khau & sua 1 bit ban ma (AEADBadTagException)", () -> {
            String cipher = AESCipher.encrypt(SAMPLE_VI, "MatKhauAES@2026");
            assertThrows(AEADBadTagException.class, () -> AESCipher.decrypt(cipher, "MatKhauSai@2026"));

            byte[] tampered = Base64.getDecoder().decode(cipher);
            tampered[tampered.length - 5] ^= 0x01; // dao 1 bit trong tag/ciphertext
            String tamperedB64 = Base64.getEncoder().encodeToString(tampered);
            assertThrows(AEADBadTagException.class, () -> AESCipher.decrypt(tamperedB64, "MatKhauAES@2026"));
            assertThrows(IllegalArgumentException.class, () -> AESCipher.decrypt("AAAA", "MatKhauAES@2026"));
        });
    }

    private static void testRc4() {
        section("5. Bai 2.4 - Mat ma dong RC4 (KSA + PRGA)");

        check("Test vector chuan NIST: 'Plaintext' + 'Key' -> 'BBF316E8D940AF0AD3'", () -> {
            String hex = RC4Cipher.encrypt("Plaintext", "Key");
            assertEquals("BBF316E8D940AF0AD3", hex, "RC4 NIST vector");
            assertEquals("Plaintext", RC4Cipher.decrypt(hex, "Key"), "RC4 giai ma lai Plaintext");
        });

        check("RC4 tieng Viet dai & chan khoa khong hop le (> 256 byte, rong)", () -> {
            String longText = SAMPLE_VI.repeat(30);
            String hex = RC4Cipher.encrypt(longText, "SecretRC4Key");
            assertEquals(CipherUtil.utf8(longText).length * 2, hex.length(), "Do dai HEX = 2 * byte ban ro");
            assertEquals(longText, RC4Cipher.decrypt(hex, "SecretRC4Key"), "RC4 chuoi dai");

            assertThrows(IllegalArgumentException.class, () -> RC4Cipher.encrypt("abc", ""));
            assertThrows(IllegalArgumentException.class, () -> RC4Cipher.encrypt("abc", "A".repeat(257)));
            assertThrows(IllegalArgumentException.class, () -> RC4Cipher.decrypt("NOTHEX", "Key"));
        });
    }

    private static void testTwofish() {
        section("6. Bai 2.5 - Mat ma Twofish CBC (BouncyCastle)");

        check("Sinh IV 16 byte (32 ky tu HEX) & ma hoa/giai ma Twofish CBC", () -> {
            String iv1 = TwofishCipher.newIv();
            String iv2 = TwofishCipher.newIv();
            assertEquals(32, iv1.length(), "IV HEX 32 ky tu");
            assertTrue(!iv1.equals(iv2), "Hai lan sinh IV khac nhau");

            String cipher = TwofishCipher.encrypt(SAMPLE_VI, "Twofish@Lab2", iv1);
            assertEquals(SAMPLE_VI, TwofishCipher.decrypt(cipher, "Twofish@Lab2", iv1), "Twofish round-trip");
            assertDecryptDiffersOrFails(() -> TwofishCipher.decrypt(cipher, "Twofish@Lab2", iv2), SAMPLE_VI, "Twofish sai IV");
            assertDecryptDiffersOrFails(() -> TwofishCipher.decrypt(cipher, "WrongKey", iv1), SAMPLE_VI, "Twofish sai Key");
        });

        check("Dinh dang file 2 dong IV=... & chan IV/file sai dinh dang", () -> {
            String iv = TwofishCipher.newIv();
            String saved = TwofishCipher.toFile(iv, "q1Xk3==");
            String[] parsed = TwofishCipher.fromFile(saved);
            assertEquals(iv, parsed[0], "Doc lai IV tu file");
            assertEquals("q1Xk3==", parsed[1], "Doc lai ciphertext tu file");

            assertThrows(IllegalArgumentException.class, () -> TwofishCipher.encrypt(SAMPLE_VI, "k", "ABCD"));
            assertThrows(IllegalArgumentException.class, () -> TwofishCipher.fromFile("NO_HEADER\nabc"));
        });
    }

    private static void testAesCtr() {
        section("7. Bai 2.6 (Nang cao 01 - Java) - AES-256 CTR (Khong dem)");

        check("Sinh Key 32 byte / Nonce 12 byte & kiem tra do dai khong dem", () -> {
            String key = AESCTRCipher.newKey();
            String nonce = AESCTRCipher.newNonce();
            assertEquals(32, Base64.getDecoder().decode(key).length, "CTR key = 32 byte");
            assertEquals(12, Base64.getDecoder().decode(nonce).length, "CTR nonce = 12 byte");

            for (String msg : new String[]{"A", "1234567890123456", SAMPLE_VI, SAMPLE_VI.repeat(80)}) {
                String cipher = AESCTRCipher.encrypt(msg, key, nonce);
                int plainLen = CipherUtil.utf8(msg).length;
                int cipherLen = Base64.getDecoder().decode(cipher).length;
                assertEquals(plainLen, cipherLen, "AES-CTR khong dem: len(ct) == len(pt)");
                assertEquals(msg, AESCTRCipher.decrypt(cipher, key, nonce), "AES-CTR round-trip");
            }
        });

        check("Sai Key/Nonce, Nonce do dai sai & dinh dang file NONCE=...", () -> {
            String k = AESCTRCipher.newKey();
            String n = AESCTRCipher.newNonce();
            String c = AESCTRCipher.encrypt(SAMPLE_VI, k, n);

            String decryptedWrongKey = AESCTRCipher.decrypt(c, AESCTRCipher.newKey(), n);
            assertTrue(!SAMPLE_VI.equals(decryptedWrongKey), "Sai key khong duoc ra ban ro goc");

            String saved = AESCTRCipher.toFile(n, c);
            String[] parsed = AESCTRCipher.fromFile(saved);
            assertEquals(n, parsed[0], "Doc lai Nonce");
            assertEquals(c, parsed[1], "Doc lai ciphertext");

            assertThrows(IllegalArgumentException.class, () -> AESCTRCipher.encrypt(SAMPLE_VI, "AAAA", n));
            assertThrows(IllegalArgumentException.class, () -> AESCTRCipher.fromFile("BAD_FORMAT\n123"));
        });
    }

    private static void testSwingForms() {
        section("8. Giao dien Swing (Khoi tao an toan 9 JFrame forms)");
        if (GraphicsEnvironment.isHeadless()) {
            skip("Khoi tao 9 Swing JFrames", "Moi truong headless khong co man hinh do hoa");
            return;
        }

        check("Khoi tao & dispose sach ca 9 JFrame (Menu, SQL, LichSu, DES, 3DES, AES, RC4, Twofish, AES_CTR)", () -> {
            @SuppressWarnings("unchecked")
            Supplier<JFrame>[] factories = new Supplier[]{
                frm_Menu::new,
                frm_KetNoiSQL::new,
                frm_LichSu::new,
                frm_DES::new,
                frm_TripleDES::new,
                frm_AES::new,
                frm_RC4::new,
                frm_Twofish::new,
                frm_AES_CTR::new
            };
            SwingUtilities.invokeAndWait(() -> {
                for (Supplier<JFrame> factory : factories) {
                    JFrame frame = factory.get();
                    assertTrue(frame.getTitle() != null && !frame.getTitle().isBlank(), "Form phai co tieu de");
                    frame.dispose();
                }
            });
        });
    }

    private static void testSqlServer(char[] dbPassword) {
        section("9. Tich hop SQL Server (DbConfig, UserDao PBKDF2, HistoryDao)");
        if (dbPassword == null || dbPassword.length == 0) {
            skip("Kiem thu tich hop SQL Server (127.0.0.1:14333 / LAB2_MaHoaHienDai)",
                    "Khong co mat khau lab2_user (truyen --db-pass=<pass> hoac nhap luc chay)");
            return;
        }

        String testUser = "autotest_" + (System.currentTimeMillis() % 100000);
        boolean sqlConnected = false;
        try {
            sqlConnected = check("DbConfig.set + DbConfig.test ket noi thanh cong den LAB2_MaHoaHienDai", () -> {
                DbConfig.set("127.0.0.1", 14333, "LAB2_MaHoaHienDai", "lab2_user", dbPassword);
                String msg = DbConfig.test();
                assertTrue(msg.contains("LAB2_MaHoaHienDai"), "Thong bao phai chua ten database: " + msg);
            });
            if (!sqlConnected) {
                skip("UserDao & HistoryDao SQL tests", "Bo qua vi ket noi SQL Server that bai");
                return;
            }

            check("UserDao: validate input, register, chan trung username, login dung/sai & newTicket", () -> {
                assertThrows(IllegalArgumentException.class, () -> UserDao.register("ab", "MatKhau@2026".toCharArray()));
                assertThrows(IllegalArgumentException.class, () -> UserDao.register("valid_user", "123".toCharArray()));

                UserDao.register(testUser, "MatKhau@2026".toCharArray());
                assertThrows(IllegalArgumentException.class, () -> UserDao.register(testUser, "MatKhau@2026".toCharArray()));

                assertTrue(UserDao.login(testUser, "MatKhau@2026".toCharArray()), "Login dung mat khau -> true");
                assertTrue(!UserDao.login(testUser, "MatKhauSai@1".toCharArray()), "Login sai mat khau -> false");
                assertTrue(!UserDao.login("khong_ton_tai_" + testUser, "MatKhau@2026".toCharArray()), "User khong ton tai -> false");

                String ticket = UserDao.newTicket();
                assertEquals(32, ticket.length(), "Ticket 128-bit = 32 ky tu HEX");
            });

            check("HistoryDao: tryInsert ghi log & loadLatest doc lai dung du lieu", () -> {
                String status = HistoryDao.tryInsert("TEST-SUITE", "VERIFY", testUser, 33, 64);
                assertTrue(status.contains("Đã lưu lịch sử SQL Server"), "Trang thai tryInsert: " + status);

                DefaultTableModel model = HistoryDao.loadLatest(20);
                boolean found = false;
                for (int r = 0; r < model.getRowCount(); r++) {
                    if ("TEST-SUITE".equals(model.getValueAt(r, 2)) && testUser.equals(model.getValueAt(r, 4))) {
                        found = true;
                        break;
                    }
                }
                assertTrue(found, "loadLatest phai tim thay dong lich su vua ghi");
            });
        } finally {
            Arrays.fill(dbPassword, '\0');
            if (sqlConnected) {
                cleanupSqlTestData(testUser);
            }
            DbConfig.clear();
        }
    }

    private static void cleanupSqlTestData(String testUser) {
        if (!DbConfig.isReady()) {
            return;
        }
        try (Connection con = DbConfig.open()) {
            try (PreparedStatement ps = con.prepareStatement("DELETE FROM dbo.crypto_history WHERE app_username = ?")) {
                ps.setString(1, testUser);
                ps.executeUpdate();
            }
            try (PreparedStatement ps = con.prepareStatement("DELETE FROM dbo.app_users WHERE username = ?")) {
                ps.setString(1, testUser);
                ps.executeUpdate();
            }
        } catch (Exception ex) {
            System.out.println("  [WARN] Khong xoa duoc du lieu test SQL tam: " + ex.getMessage());
        }
    }

    private static char[] resolveDbPassword(String[] args) {
        for (String a : args) {
            if (a.startsWith("--db-pass=")) {
                return a.substring("--db-pass=".length()).toCharArray();
            }
        }
        String env = System.getenv("LAB2_DB_PASS");
        if (env != null && !env.isBlank()) {
            return env.toCharArray();
        }
        Console console = System.console();
        if (console != null) {
            char[] pwd = console.readPassword("%n[SQL Server] Nhap mat khau lab2_user de test DB (Enter de bo qua): ");
            return (pwd != null && pwd.length > 0) ? pwd : null;
        }
        // Fallback khi chay trong Terminal IDE khong ho tro System.console()
        if (!Boolean.getBoolean("lab2.noninteractive")) {
            try {
                System.out.print("\n[SQL Server] Nhap mat khau lab2_user de test DB (Enter de bo qua): ");
                System.out.flush();
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
                String line = br.readLine();
                if (line != null) {
                    line = line.replace("\uFEFF", "").trim();
                    if (!line.isEmpty()) {
                        return line.toCharArray();
                    }
                }
            } catch (Exception ignored) {
            }
        }
        return null;
    }

    // ------------------------- MINI ASSERTION HELPERS -------------------------

    private static void section(String title) {
        System.out.println("\n--- " + title + " ---");
    }

    private static boolean check(String name, ThrowingRunnable body) {
        total++;
        try {
            body.run();
            passed++;
            System.out.println("  [PASS] " + name);
            return true;
        } catch (Throwable ex) {
            failed++;
            System.out.println("  [FAIL] " + name + " -> " + ex.getClass().getSimpleName() + ": " + ex.getMessage());
            return false;
        }
    }

    private static void skip(String name, String reason) {
        total++;
        skipped++;
        System.out.println("  [SKIP] " + name + " (" + reason + ")");
    }

    private static void assertTrue(boolean cond, String msg) {
        if (!cond) {
            throw new AssertionError(msg);
        }
    }

    private static void assertEquals(Object expected, Object actual, String msg) {
        if (expected == null ? actual != null : !expected.equals(actual)) {
            throw new AssertionError(msg + " | Ky vong: [" + expected + "] nhung nhan: [" + actual + "]");
        }
    }

    private static <T extends Throwable> void assertThrows(Class<T> expectedType, ThrowingRunnable action) {
        try {
            action.run();
        } catch (Throwable ex) {
            if (expectedType.isInstance(ex)) {
                return;
            }
            throw new AssertionError("Ky vong loi " + expectedType.getSimpleName()
                    + " nhung nhan " + ex.getClass().getSimpleName() + ": " + ex.getMessage(), ex);
        }
        throw new AssertionError("Ky vong nem loi " + expectedType.getSimpleName() + " nhung khong co loi.");
    }

    private static void assertDecryptDiffersOrFails(SupplierWithException<String> action, String original, String label) {
        try {
            String out = action.get();
            if (original.equals(out)) {
                throw new AssertionError(label + ": giai ma sai khoa/IV nhung van ra ban ro goc!");
            }
        } catch (AssertionError ae) {
            throw ae;
        } catch (Exception expected) {
            // Padding/tag exception khi sai khoa hoac sai IV la ket qua dung
        }
    }

    @FunctionalInterface
    private interface SupplierWithException<T> {
        T get() throws Exception;
    }
}
