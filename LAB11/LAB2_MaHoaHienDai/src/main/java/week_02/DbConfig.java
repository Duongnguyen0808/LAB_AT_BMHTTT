package week_02;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Lưu thông tin kết nối SQL Server mà sinh viên nhập trên form frm_KetNoiSQL.
 * Mật khẩu chỉ nằm trong bộ nhớ khi chương trình chạy, KHÔNG ghi vào mã nguồn hay tệp.
 */
public final class DbConfig {

    private static String server;
    private static int port;
    private static String database;
    private static String user;
    private static char[] password;

    private DbConfig() {
    }

    /** Ghi nhớ thông tin kết nối (gọi khi bấm nút Test Connection). */
    public static synchronized void set(String s, int p, String db, String u, char[] pwd) {
        server = s;
        port = p;
        database = db;
        user = u;
        password = pwd == null ? new char[0] : pwd.clone();
    }

    /** true nếu sinh viên đã cấu hình; false nếu chưa (chưa mở form kết nối). */
    public static synchronized boolean isReady() {
        return server != null && !server.isEmpty() && database != null && user != null && password != null;
    }

    public static synchronized Connection open() throws SQLException {
        if (!isReady()) {
            throw new SQLException("Chưa kết nối SQL Server. Hãy mở form 'Kết nối SQL Server' và bấm 'Test Connection'.");
        }
        String url = String.format(
            "jdbc:sqlserver://%s:%d;databaseName=%s;encrypt=true;trustServerCertificate=true;loginTimeout=5",
            server, port, database
        );
        return DriverManager.getConnection(url, user, new String(password));
    }

    /** Kết nối thử; trả về chuỗi mô tả để hiện lên form. */
    public static String test() throws SQLException {
        try (Connection con = open()) {
            DatabaseMetaData meta = con.getMetaData();
            return "Kết nối thành công: " + meta.getDatabaseProductName() + " "
                 + meta.getDatabaseProductVersion() + " - database " + con.getCatalog();
        }
    }
}
