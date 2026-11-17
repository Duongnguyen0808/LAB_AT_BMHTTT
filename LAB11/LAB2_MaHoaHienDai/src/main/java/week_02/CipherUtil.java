package week_02;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/** Các hàm tiện ích dùng chung cho Lab 2: đổi chuỗi <-> byte, Base64, Hex, số ngẫu nhiên, PBKDF2. */
public final class CipherUtil {

    private static final SecureRandom RANDOM = new SecureRandom();

    private CipherUtil() {
    }

    /** Chuỗi -> mảng byte UTF-8 (giữ đúng tiếng Việt). */
    public static byte[] utf8(String s) {
        return s.getBytes(StandardCharsets.UTF_8);
    }

    /** Mảng byte UTF-8 -> chuỗi. */
    public static String text(byte[] b) {
        return new String(b, StandardCharsets.UTF_8);
    }

    /** Bản mã là dãy byte bất kỳ nên đổi sang Base64 để hiện được trong JTextArea. */
    public static String b64(byte[] b) {
        return Base64.getEncoder().encodeToString(b);
    }

    public static byte[] fromB64(String s) {
        try {
            return Base64.getDecoder().decode(s.trim());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Dữ liệu không phải Base64 hợp lệ.");
        }
    }

    /** Sinh n byte ngẫu nhiên an toàn (dùng cho IV, nonce, salt, khóa). */
    public static byte[] randomBytes(int n) {
        byte[] b = new byte[n];
        RANDOM.nextBytes(b);
        return b;
    }

    /** Byte -> chuỗi HEX chữ hoa, ví dụ {0xBB, 0xF3} -> "BBF3". */
    public static String hex(byte[] data) {
        StringBuilder sb = new StringBuilder(data.length * 2);
        for (byte b : data) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }

    /** Chuỗi HEX -> mảng byte (ngược lại của hex). */
    public static byte[] fromHex(String hex) {
        String clean = hex.replaceAll("\s+", "");
        if (clean.length() % 2 != 0) {
            throw new IllegalArgumentException("Chuỗi HEX phải có số ký tự chẵn.");
        }
        byte[] out = new byte[clean.length() / 2];
        for (int i = 0; i < out.length; i++) {
            out[i] = (byte) Integer.parseInt(clean.substring(i * 2, i * 2 + 2), 16);
        }
        return out;
    }

    /** Ghép hai mảng byte: a || b. */
    public static byte[] concat(byte[] a, byte[] b) {
        byte[] out = new byte[a.length + b.length];
        System.arraycopy(a, 0, out, 0, a.length);
        System.arraycopy(b, 0, out, a.length, b.length);
        return out;
    }

    /** Báo lỗi thân thiện nếu sinh viên quên nhập Key. */
    public static void requireKey(String key) {
        if (key == null || key.trim().isEmpty()) {
            throw new IllegalArgumentException("Hãy nhập Key.");
        }
    }

    /**
     * PBKDF2: biến mật khẩu dạng chữ thành khóa đối xứng an toàn dài keyLenBits.
     * Muốn cùng kết quả, bắt buộc phải truyền CÙNG một mảng salt.
     */
    public static byte[] pbkdf2(char[] password, byte[] salt, int iterations, int keyLenBits) {
        try {
            PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, keyLenBits);
            return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(spec).getEncoded();
        } catch (Exception ex) {
            throw new IllegalStateException("Không tạo được khóa PBKDF2", ex);
        }
    }
}
