package week_02;

import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES-256 chế độ GCM (vừa mã hóa vừa xác thực toàn vẹn bằng tag 128 bit).
 * Khóa 256 bit sinh từ mật khẩu bằng PBKDF2 (120 000 vòng) và salt 16 byte ngẫu nhiên.
 * Cấu trúc bản mã: salt (16 byte) || nonce (12 byte) || ciphertext + tag.
 */
public final class AESCipher {

    private static final int TAG_BITS = 128; // tag xác thực 16 byte
    private static final int ITERATIONS = 120_000;

    private AESCipher() {
    }

    public static String encrypt(String plaintext, String password) throws Exception {
        CipherUtil.requireKey(password);
        byte[] salt = CipherUtil.randomBytes(16);
        byte[] nonce = CipherUtil.randomBytes(12); // GCM khuyến nghị nonce 12 byte
        byte[] key = CipherUtil.pbkdf2(password.toCharArray(), salt, ITERATIONS, 256);
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, "AES"), new GCMParameterSpec(TAG_BITS, nonce));
        byte[] encrypted = cipher.doFinal(CipherUtil.utf8(plaintext));
        return CipherUtil.b64(CipherUtil.concat(salt, CipherUtil.concat(nonce, encrypted)));
    }

    public static String decrypt(String ciphertext, String password) throws Exception {
        CipherUtil.requireKey(password);
        byte[] all = CipherUtil.fromB64(ciphertext);
        if (all.length < 16 + 12 + 16) {
            throw new IllegalArgumentException("Bản mã quá ngắn hoặc bị hỏng.");
        }
        byte[] salt = Arrays.copyOfRange(all, 0, 16);
        byte[] nonce = Arrays.copyOfRange(all, 16, 28);
        byte[] data = Arrays.copyOfRange(all, 28, all.length);
        byte[] key = CipherUtil.pbkdf2(password.toCharArray(), salt, ITERATIONS, 256);
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "AES"), new GCMParameterSpec(TAG_BITS, nonce));
        return CipherUtil.text(cipher.doFinal(data));
    }
}
