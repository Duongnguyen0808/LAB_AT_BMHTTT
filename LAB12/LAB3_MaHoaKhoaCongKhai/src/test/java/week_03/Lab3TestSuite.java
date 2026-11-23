package week_03;

import java.awt.GraphicsEnvironment;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.function.Supplier;
import javax.crypto.AEADBadTagException;
import javax.crypto.BadPaddingException;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/**
 * Bộ kiểm thử toàn diện (Integration & Unit Test Suite) cho toàn bộ LAB 3 / LAB 12:
 *  - Tiện ích: CryptoSupport (UTF-8, Base64, concat, slice, AES-256-GCM, HKDF-SHA256, toKeyFile/fromKeyFile)
 *  - Bài 3.1 & 3.2: RSACipher (2048 & 3072 bit, OAEP-SHA256, giới hạn 190/318 byte, loadKeys) + RSADemo console
 *  - Bài 3.3: ECCCipher (ECIES secp256r1 + ECDH + HKDF + AES-GCM, phát hiện khóa sai)
 *  - Bài 3.4: RSA_AES_Cipher (Hệ mã lai RSA + AES-256-GCM cho văn bản > 1000 ký tự, phát hiện sửa 1 ký tự bản mã)
 *  - Bài 3.5: ECCSignature (Chữ ký số ECDSA SHA256withECDSA: VALID & INVALID khi sửa thông điệp hoặc đổi khóa)
 *  - Giao diện Swing: Khởi tạo và giải phóng an toàn cả 7 JFrame forms
 *  - Cơ sở dữ liệu SQL Server: DbConfig, HistoryDao (INSERT & SELECT TOP từ dbo.History)
 */
public final class Lab3TestSuite {

    private static final String SAMPLE_VI = "Xin chào Lab 3 - Mã hóa khóa công khai (RSA, ECC, RSA-AES, ECDSA)!";
    private static int total = 0;
    private static int passed = 0;
    private static int failed = 0;
    private static int skipped = 0;

    @FunctionalInterface
    private interface ThrowingRunnable {
        void run() throws Exception;
    }

    private Lab3TestSuite() {
    }

    public static void main(String[] args) throws Exception {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        System.setErr(new PrintStream(System.err, true, StandardCharsets.UTF_8));

        System.out.println("==================================================================");
        System.out.println("   BO KIEM THU TOAN BAI - LAB 3 / LAB 12 (MA HOA KHOA CONG KHAI)");
        System.out.println("   Sinh vien: Dang Duong Nguyen | MSSV: 1150080150 | Lop: CNPM2");
        System.out.println("==================================================================");

        testCryptoSupport();
        testRsaAndDemo();
        testEcc();
        testRsaAesHybrid();
        testEcdsaSignature();
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

    private static void testCryptoSupport() {
        section("1. Lop tien ich CryptoSupport (UTF-8, Base64, AES-GCM, HKDF, KeyFile)");

        check("UTF-8, Base64 (bo qua khoang trang), concat & slice", () -> {
            byte[] raw = CryptoSupport.utf8(SAMPLE_VI);
            assertEquals(SAMPLE_VI, CryptoSupport.text(raw), "UTF-8 round-trip");
            String b64 = CryptoSupport.b64(raw);
            assertTrue(Arrays.equals(raw, CryptoSupport.unb64(" \n " + b64 + " \t ")), "unb64 bo qua whitespace");
            assertThrows(IllegalArgumentException.class, () -> CryptoSupport.unb64("@@@not_base64@@@"));

            byte[] c = CryptoSupport.concat(new byte[]{1, 2}, new byte[]{3}, new byte[]{4, 5});
            assertTrue(Arrays.equals(new byte[]{1, 2, 3, 4, 5}, c), "concat 3 mang");
            assertTrue(Arrays.equals(new byte[]{2, 3, 4}, CryptoSupport.slice(c, 1, 4)), "slice [1,4)");
            assertThrows(IllegalArgumentException.class, () -> CryptoSupport.slice(c, 0, 10));
        });

        check("AES-256-GCM encrypt/decrypt, HKDF-SHA256 & toKeyFile/fromKeyFile", () -> {
            byte[] secret = CryptoSupport.randomBytes(32);
            byte[] salt = CryptoSupport.randomBytes(16);
            byte[] info = CryptoSupport.utf8("LAB3-TEST");
            byte[] key1 = CryptoSupport.hkdf(secret, salt, info);
            byte[] key2 = CryptoSupport.hkdf(secret, salt, info);
            assertEquals(32, key1.length, "HKDF 32 byte");
            assertTrue(Arrays.equals(key1, key2), "HKDF deterministic");

            byte[] nonce = CryptoSupport.randomBytes(12);
            byte[] ct = CryptoSupport.gcmEncrypt(key1, nonce, CryptoSupport.utf8(SAMPLE_VI));
            assertEquals(SAMPLE_VI, CryptoSupport.text(CryptoSupport.gcmDecrypt(key1, nonce, ct)), "GCM round-trip");

            ct[ct.length - 1] ^= 0x01;
            assertThrows(AEADBadTagException.class, () -> CryptoSupport.gcmDecrypt(key1, nonce, ct));

            String kf = CryptoSupport.toKeyFile("PUB_ABC", "PRI_XYZ");
            String[] parsed = CryptoSupport.fromKeyFile(kf);
            assertEquals("PUB_ABC", parsed[0], "Public key from file");
            assertEquals("PRI_XYZ", parsed[1], "Private key from file");
            assertThrows(IllegalArgumentException.class, () -> CryptoSupport.fromKeyFile("PRIVATE=only_private"));
        });
    }

    private static void testRsaAndDemo() {
        section("2. Bai 3.1 & 3.2 - Mat ma RSA (OAEP-SHA256, 2048 & 3072 bit, RSADemo)");

        check("RSA-2048 & RSA-3072 ma hoa/giai ma HELLO RSA (OAEP ngau nhien)", () -> {
            RSACipher rsa = new RSACipher();
            assertThrows(IllegalStateException.class, () -> rsa.encrypt("HELLO RSA"));
            assertThrows(IllegalArgumentException.class, () -> rsa.generateKeys(1024));

            rsa.generateKeys(2048);
            assertTrue(rsa.hasKeys(), "hasKeys sau generateKeys");
            assertEquals(2048, rsa.getBits(), "getBits 2048");
            assertEquals(190, rsa.maxPlaintextBytes(), "maxPlaintextBytes RSA-2048 = 190");

            String c1 = rsa.encrypt("HELLO RSA");
            String c2 = rsa.encrypt("HELLO RSA");
            assertEquals(344, c1.length(), "Do dai Base64 RSA-2048 = 344");
            assertTrue(!c1.equals(c2), "OAEP phai sinh 2 ban ma khac nhau");
            assertEquals("HELLO RSA", rsa.decrypt(c1), "Decrypt RSA-2048");

            // Vuot gioi han 190 byte
            assertThrows(IllegalArgumentException.class, () -> rsa.encrypt("A".repeat(191)));

            // Test loadKeys & wrong key
            RSACipher rsa3072 = new RSACipher();
            rsa3072.generateKeys(3072);
            assertEquals(318, rsa3072.maxPlaintextBytes(), "maxPlaintextBytes RSA-3072 = 318");
            String c3072 = rsa3072.encrypt("HELLO RSA");
            assertEquals(512, c3072.length(), "Do dai Base64 RSA-3072 = 512");
            assertEquals("HELLO RSA", rsa3072.decrypt(c3072), "Decrypt RSA-3072");

            // Dung khoa 3072 giai ma ban ma 2048 -> loi
            assertThrows(BadPaddingException.class, () -> rsa3072.decrypt(c1));

            // Nap lai khoa 2048 tu Base64
            RSACipher loaded = new RSACipher();
            loaded.loadKeys(rsa.getPublicKeyBase64(), rsa.getPrivateKeyBase64());
            assertEquals("HELLO RSA", loaded.decrypt(c1), "Decrypt sau khi loadKeys");
        });

        check("RSADemo console chay thanh cong voi input 2048 + HELLO RSA", () -> {
            InputStream oldIn = System.in;
            PrintStream oldOut = System.out;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                System.setIn(new ByteArrayInputStream("2048\nHELLO RSA\n".getBytes(StandardCharsets.UTF_8)));
                System.setOut(new PrintStream(baos, true, StandardCharsets.UTF_8));
                RSADemo.main(new String[0]);
            } finally {
                System.setIn(oldIn);
                System.setOut(oldOut);
            }
            String out = baos.toString(StandardCharsets.UTF_8);
            assertTrue(out.contains("Gioi han ban ro: 190 byte"), "RSADemo in gioi han 190 byte");
            assertTrue(out.contains("Giai ma: HELLO RSA"), "RSADemo giai ma HELLO RSA");
            assertTrue(out.contains("Khoi phuc dung: true"), "RSADemo khoi phuc dung: true");
        });
    }

    private static void testEcc() {
        section("3. Bai 3.3 - Mat ma Duong cong Elliptic ECC (ECIES secp256r1)");

        check("ECC generateKeys, encrypt/decrypt HELLO ECC, Save/Load Keys & chan khoa sai", () -> {
            ECCCipher ecc = new ECCCipher();
            assertThrows(IllegalStateException.class, () -> ecc.encrypt("HELLO ECC"));

            ecc.generateKeys();
            assertEquals(124, ecc.getPublicKeyBase64().length(), "Khoa cong khai secp256r1 Base64 = 124 ky tu");

            String ct = ecc.encrypt("HELLO ECC");
            assertEquals("HELLO ECC", ecc.decrypt(ct), "ECC decrypt HELLO ECC");

            // Luu va nap lai khoa
            String keyFile = CryptoSupport.toKeyFile(ecc.getPublicKeyBase64(), ecc.getPrivateKeyBase64());
            String[] parts = CryptoSupport.fromKeyFile(keyFile);

            // Doi sang cap khoa moi (khoa sai) -> AEADBadTagException
            ecc.generateKeys();
            assertThrows(AEADBadTagException.class, () -> ecc.decrypt(ct));

            // Nap lai khoa cu -> giai ma thanh cong
            ecc.loadKeys(parts[0], parts[1]);
            assertEquals("HELLO ECC", ecc.decrypt(ct), "ECC decrypt sau khi loadKeys");
        });
    }

    private static void testRsaAesHybrid() {
        section("4. Bai 3.4 - He ma lai RSA + AES-256-GCM (Van ban dai > 1000 ky tu)");

        check("Ma hoa/giai ma van ban dai > 1200 ky tu & phat hien sua 1 ky tu ban ma", () -> {
            RSA_AES_Cipher hybrid = new RSA_AES_Cipher();
            hybrid.generateKeys();

            String longText = ("Hệ mã lai RSA + AES: AES-GCM mã hóa toàn bộ văn bản dài, RSA chỉ mã hóa khóa AES 32 byte. ").repeat(16);
            assertTrue(longText.length() > 1000, "Van ban thu nghiem > 1000 ky tu");

            String ct = hybrid.encrypt(longText);
            assertEquals(longText, hybrid.decrypt(ct), "RSA-AES giai ma dung toan van > 1000 ky tu");

            // Mo phong nut 'Sua 1 ky tu ban ma' (btn_tamper)
            int i = ct.length() / 2;
            char replaced = ct.charAt(i) == 'A' ? 'B' : 'A';
            String tampered = ct.substring(0, i) + replaced + ct.substring(i + 1);
            assertThrows(AEADBadTagException.class, () -> hybrid.decrypt(tampered));
        });
    }

    private static void testEcdsaSignature() {
        section("5. Bai 3.5 - Chu ky so ECDSA (SHA256withECDSA - secp256r1)");

        check("Ky THONG DIEP A -> VALID; sua thanh THONG DIEP B hoac doi khoa -> INVALID", () -> {
            ECCSignature ecdsa = new ECCSignature();
            ecdsa.generateKeys();

            String sig = ecdsa.sign("THONG DIEP A");
            assertTrue(ecdsa.verify("THONG DIEP A", sig), "THONG DIEP A phai VALID");
            assertTrue(!ecdsa.verify("THONG DIEP B", sig), "Sua thanh THONG DIEP B phai INVALID");

            // Nguoi nhan chi nap khoa cong khai (khong co khoa rieng)
            ECCSignature verifierOnly = new ECCSignature();
            verifierOnly.loadKeys(ecdsa.getPublicKeyBase64(), "");
            assertTrue(verifierOnly.verify("THONG DIEP A", sig), "Nguoi nhan chi co Public Key van Verify = VALID");
            assertThrows(IllegalStateException.class, () -> verifierOnly.sign("THONG DIEP A"));

            // Doi sang cap khoa moi -> Verify = INVALID
            ecdsa.generateKeys();
            assertTrue(!ecdsa.verify("THONG DIEP A", sig), "Khoa cong khai khac nguoi ky phai INVALID");
        });
    }

    private static void testSwingForms() {
        section("6. Giao dien Swing (Khoi tao an toan 7 JFrame forms)");
        if (GraphicsEnvironment.isHeadless()) {
            skip("Khoi tao 7 Swing JFrames", "Moi truong headless");
            return;
        }

        check("Khoi tao & dispose sach ca 7 JFrame (Menu, KetNoiSQL, LichSu, RSA, ECC, RSA_AES, ECC_Signature)", () -> {
            @SuppressWarnings("unchecked")
            Supplier<JFrame>[] factories = new Supplier[]{
                frm_Menu::new,
                frm_KetNoiSQL::new,
                frm_LichSu::new,
                frm_RSA::new,
                frm_ECC::new,
                frm_RSA_AES::new,
                frm_ECC_Signature::new
            };
            SwingUtilities.invokeAndWait(() -> {
                for (Supplier<JFrame> factory : factories) {
                    JFrame frame = factory.get();
                    assertTrue(frame.getTitle() != null && !frame.getTitle().isBlank(), "Form phai co title");
                    frame.dispose();
                }
            });
        });
    }

    private static void testSqlServer(char[] dbPassword) {
        section("7. Tich hop SQL Server (DbConfig & HistoryDao - LAB3_MaHoaKhoaCongKhai)");
        if (dbPassword == null || dbPassword.length == 0) {
            skip("Kiem thu tich hop SQL Server (127.0.0.1:14333 / LAB3_MaHoaKhoaCongKhai)",
                    "Khong co mat khau lab3_user (truyen --db-pass=LabUser@2026)");
            return;
        }

        try {
            boolean connected = check("DbConfig.set + DbConfig.test ket noi thanh cong den LAB3_MaHoaKhoaCongKhai", () -> {
                DbConfig.set("127.0.0.1", 14333, "LAB3_MaHoaKhoaCongKhai", "lab3_user", dbPassword);
                String msg = DbConfig.test();
                assertTrue(msg.contains("LAB3_MaHoaKhoaCongKhai"), "Thong bao phai chua ten database: " + msg);
            });
            if (!connected) {
                return;
            }

            check("HistoryDao: ghi nhan du cac thao tac RSA, ECC, RSA-AES, ECDSA (ca Thanh cong = Co va Khong)", () -> {
                assertTrue(HistoryDao.tryInsert("RSA", "ENCRYPT", 9, true).contains("Đã lưu lịch sử SQL Server"), "Log RSA ENCRYPT");
                assertTrue(HistoryDao.tryInsert("RSA", "DECRYPT", 344, true).contains("Đã lưu lịch sử SQL Server"), "Log RSA DECRYPT");
                assertTrue(HistoryDao.tryInsert("ECC", "ENCRYPT", 9, true).contains("Đã lưu lịch sử SQL Server"), "Log ECC ENCRYPT");
                assertTrue(HistoryDao.tryInsert("ECC", "DECRYPT", 188, true).contains("Đã lưu lịch sử SQL Server"), "Log ECC DECRYPT");
                assertTrue(HistoryDao.tryInsert("RSA-AES", "ENCRYPT", 1262, true).contains("Đã lưu lịch sử SQL Server"), "Log RSA-AES ENCRYPT");
                assertTrue(HistoryDao.tryInsert("RSA-AES", "DECRYPT", 1756, true).contains("Đã lưu lịch sử SQL Server"), "Log RSA-AES DECRYPT OK");
                assertTrue(HistoryDao.tryInsert("RSA-AES", "DECRYPT", 1756, false).contains("Đã lưu lịch sử SQL Server"), "Log RSA-AES DECRYPT FAIL");
                assertTrue(HistoryDao.tryInsert("ECDSA", "SIGN", 12, true).contains("Đã lưu lịch sử SQL Server"), "Log ECDSA SIGN");
                assertTrue(HistoryDao.tryInsert("ECDSA", "VERIFY", 12, true).contains("Đã lưu lịch sử SQL Server"), "Log ECDSA VERIFY VALID");
                assertTrue(HistoryDao.tryInsert("ECDSA", "VERIFY", 12, false).contains("Đã lưu lịch sử SQL Server"), "Log ECDSA VERIFY INVALID");

                DefaultTableModel model = HistoryDao.loadLatest(20);
                assertTrue(model.getRowCount() >= 10, "Bang dbo.History phai co >= 10 dong lich su");
            });
        } finally {
            Arrays.fill(dbPassword, '\0');
            DbConfig.clear();
        }
    }

    private static char[] resolveDbPassword(String[] args) {
        for (String a : args) {
            if (a.startsWith("--db-pass=")) {
                return a.substring("--db-pass=".length()).toCharArray();
            }
        }
        String env = System.getenv("LAB3_DB_PASS");
        if (env != null && !env.isBlank()) {
            return env.toCharArray();
        }
        return null;
    }

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
}
