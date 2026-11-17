package week_02;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Đăng ký / đăng nhập tài khoản bằng PBKDF2-HMAC-SHA256 và salt 16 byte.
 * Mật khẩu KHÔNG BAO GIỜ lưu dạng rõ - chỉ lưu salt và password_hash (32 byte).
 */
public final class UserDao {

    private static final int ITERATIONS = 120_000; // số vòng lặp PBKDF2

    private UserDao() {
    }

    /** Đăng ký: sinh salt ngẫu nhiên -> băm PBKDF2 -> INSERT vào dbo.app_users. */
    public static void register(String username, char[] password) throws SQLException {
        if (username == null || username.trim().length() < 3) {
            throw new IllegalArgumentException("Username phải từ 3 ký tự.");
        }
        if (password == null || password.length < 8) {
            throw new IllegalArgumentException("Mật khẩu phải từ 8 ký tự.");
        }
        byte[] salt = CipherUtil.randomBytes(16);
        byte[] hash = CipherUtil.pbkdf2(password, salt, ITERATIONS, 256);
        String sql = "INSERT INTO dbo.app_users(username, salt, password_hash) VALUES (?, ?, ?)";
        try (Connection con = DbConfig.open(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username.trim());
            ps.setBytes(2, salt);
            ps.setBytes(3, hash);
            ps.executeUpdate();
        }
    }

    /** true nếu đúng username và mật khẩu. */
    public static boolean login(String username, char[] password) throws SQLException {
        String sql = "SELECT salt, password_hash FROM dbo.app_users WHERE username = ?";
        try (Connection con = DbConfig.open(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username.trim());
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return false; // không có username này
                }
                byte[] salt = rs.getBytes("salt");
                byte[] stored = rs.getBytes("password_hash");
                byte[] actual = CipherUtil.pbkdf2(password, salt, ITERATIONS, 256);
                return MessageDigest.isEqual(stored, actual); // so sánh thời gian hằng
            }
        }
    }

    /** Ticket = chuỗi ngẫu nhiên 128 bit cấp sau khi đăng nhập thành công (phiên làm việc). */
    public static String newTicket() {
        return CipherUtil.hex(CipherUtil.randomBytes(16));
    }
}
