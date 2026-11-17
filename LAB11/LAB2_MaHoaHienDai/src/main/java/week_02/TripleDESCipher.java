package week_02;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * TripleDES (3DES = DES 3 lần: mã - giải - mã) - khóa 24 byte = K1 || K2 || K3.
 * Chế độ CBC, đệm PKCS5, IV ngẫu nhiên 8 byte gắn ở đầu bản mã giống DES.
 */
public final class TripleDESCipher {

    private static final String TRANSFORMATION = "DESede/CBC/PKCS5Padding";

    private TripleDESCipher() {
    }

    private static SecretKeySpec key(String textKey) {
        CipherUtil.requireKey(textKey);
        byte[] k = CipherUtil.utf8(textKey);
        if (k.length != 24) {
            throw new IllegalArgumentException("Khóa DESede phải đúng 24 byte (gõ 24 ký tự không dấu). Hiện có " + k.length + " byte.");
        }
        return new SecretKeySpec(k, "DESede");
    }

    public static String encrypt(String plaintext, String key) throws Exception {
        byte[] iv = CipherUtil.randomBytes(8); // IV mới cho mỗi lần mã hóa
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, key(key), new IvParameterSpec(iv));
        byte[] encrypted = cipher.doFinal(CipherUtil.utf8(plaintext));
        return CipherUtil.b64(CipherUtil.concat(iv, encrypted)); // gửi kèm IV ở đầu bản mã
    }

    public static String decrypt(String ciphertext, String key) throws Exception {
        byte[] all = CipherUtil.fromB64(ciphertext);
        if (all.length < 8 + 8) {
            throw new IllegalArgumentException("Bản mã quá ngắn hoặc bị hỏng.");
        }
        byte[] iv = java.util.Arrays.copyOfRange(all, 0, 8);
        byte[] data = java.util.Arrays.copyOfRange(all, 8, all.length);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, key(key), new IvParameterSpec(iv));
        return CipherUtil.text(cipher.doFinal(data));
    }
}
