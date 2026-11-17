package week_02;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Bài 2.3: AES-256 GCM tích hợp Đăng ký, Đăng nhập và SQL Server.
 */
public class frm_AES extends JFrame {

    private String currentUser = null;

    private final JTextField txt_username = SwingUiHelper.textField("sinhvien01", 14);
    private final JPasswordField txt_password = new JPasswordField("MatKhau@2026", 14);
    private final JButton btn_register = SwingUiHelper.button("Register", new Dimension(90, 28));
    private final JButton btn_login = SwingUiHelper.button("Login", new Dimension(80, 28));
    private final JButton btn_sql = SwingUiHelper.button("Kết nối SQL Server", new Dimension(150, 28));
    private final JTextField txt_ticket = SwingUiHelper.textField("", 24);

    private final JTextArea txt_plaintext = SwingUiHelper.textArea(3, true);
    private final JPasswordField txt_key = new JPasswordField("MatKhauAES@2026", 18);
    private final JTextArea txt_ciphertext = SwingUiHelper.textArea(3, true);
    private final JButton btn_encrypt = SwingUiHelper.button("Encrypt", new Dimension(100, 30));
    private final JButton btn_decrypt = SwingUiHelper.button("Decrypt", new Dimension(100, 30));
    private final JButton btn_save = SwingUiHelper.button("Save File", new Dimension(120, 30));
    private final JButton btn_open = SwingUiHelper.button("Open File", new Dimension(120, 30));
    private final JLabel lbl_status = SwingUiHelper.bodyLabel("Chưa đăng nhập. Hãy kết nối SQL và Login trước khi dùng AES.");

    public frm_AES() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("AES Cipher - Register / Login");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBorder(BorderFactory.createEmptyBorder(14, 16, 14, 16));

        JPanel header = new JPanel(new GridLayout(2, 1, 0, 4));
        header.add(SwingUiHelper.titleLabel("AES-GCM - ĐĂNG KÝ / ĐĂNG NHẬP (SQL SERVER)"));
        header.add(SwingUiHelper.bodyLabel("Mã hóa xác thực AES-256 GCM + Quản lý người dùng với PBKDF2:"));
        root.add(header, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(3, 4, 3, 4);
        g.fill = GridBagConstraints.HORIZONTAL;
        g.weightx = 1.0;
        int row = 0;

        // User Auth Box
        JPanel pnlAuth = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 2));
        pnlAuth.setBorder(BorderFactory.createTitledBorder("Xác thực người dùng (SQL Server)"));
        pnlAuth.add(SwingUiHelper.bodyLabel("User:"));
        pnlAuth.add(txt_username);
        pnlAuth.add(SwingUiHelper.bodyLabel("Pass:"));
        txt_password.setPreferredSize(new Dimension(110, 28));
        pnlAuth.add(txt_password);
        pnlAuth.add(btn_register);
        pnlAuth.add(btn_login);
        pnlAuth.add(btn_sql);
        pnlAuth.add(SwingUiHelper.bodyLabel("Ticket:"));
        txt_ticket.setEditable(false);
        pnlAuth.add(txt_ticket);

        g.gridx = 0; g.gridy = row++;
        center.add(pnlAuth, g);

        g.gridy = row++;
        center.add(SwingUiHelper.bodyLabel("Plaintext (Bản rõ):"), g);
        g.gridy = row++;
        center.add(new JScrollPane(txt_plaintext), g);

        g.gridy = row++;
        JPanel pnlKey = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        pnlKey.add(SwingUiHelper.bodyLabel("Khóa bí mật AES:"));
        txt_key.setPreferredSize(new Dimension(160, 28));
        pnlKey.add(txt_key);
        pnlKey.add(SwingUiHelper.bodyLabel("(Khóa dẫn xuất qua PBKDF2 120,000 vòng)"));
        center.add(pnlKey, g);

        g.gridy = row++;
        center.add(SwingUiHelper.bodyLabel("Ciphertext (Bản mã Base64 = salt || nonce || ciphertext + tag):"), g);
        g.gridy = row++;
        center.add(new JScrollPane(txt_ciphertext), g);

        g.gridy = row++;
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 4));
        actions.add(btn_encrypt);
        actions.add(btn_decrypt);
        actions.add(btn_save);
        actions.add(btn_open);
        center.add(actions, g);

        root.add(center, BorderLayout.CENTER);

        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 2));
        statusPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, java.awt.Color.LIGHT_GRAY));
        statusPanel.add(lbl_status);
        root.add(statusPanel, BorderLayout.SOUTH);

        setContentPane(root);
        pack();
        setMinimumSize(new Dimension(720, 600));

        btn_register.addActionListener(e -> btn_registerActionPerformed());
        btn_login.addActionListener(e -> btn_loginActionPerformed());
        btn_sql.addActionListener(e -> btn_sqlActionPerformed());
        btn_encrypt.addActionListener(e -> btn_encryptActionPerformed());
        btn_decrypt.addActionListener(e -> btn_decryptActionPerformed());
        btn_save.addActionListener(e -> btn_saveActionPerformed());
        btn_open.addActionListener(e -> btn_openActionPerformed());
    }

    private void btn_registerActionPerformed() {
        try {
            String user = txt_username.getText().trim();
            UserDao.register(user, txt_password.getPassword());
            JOptionPane.showMessageDialog(this, "Đăng ký thành công cho '" + user + "'. Hãy bấm Login.");
            lbl_status.setText("Đã đăng ký tài khoản '" + user + "' trong SQL Server.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Đăng ký thất bại: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btn_loginActionPerformed() {
        try {
            String user = txt_username.getText().trim();
            if (UserDao.login(user, txt_password.getPassword())) {
                currentUser = user;
                txt_ticket.setText(UserDao.newTicket());
                lbl_status.setText("Đã đăng nhập: " + user);
                JOptionPane.showMessageDialog(this, "Đăng nhập thành công! Ticket đã cấp.");
            } else {
                JOptionPane.showMessageDialog(this, "Sai tên đăng nhập hoặc mật khẩu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Đăng nhập thất bại: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btn_sqlActionPerformed() {
        new frm_KetNoiSQL().setVisible(true);
    }

    private void btn_encryptActionPerformed() {
        if (currentUser == null) {
            JOptionPane.showMessageDialog(this, "Hãy Login trước khi dùng AES.");
            return;
        }
        String plaintext = txt_plaintext.getText();
        if (plaintext.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Hãy nhập bản rõ.");
            return;
        }
        try {
            String cipher = AESCipher.encrypt(plaintext, new String(txt_key.getPassword()));
            txt_ciphertext.setText(cipher);
            String db = HistoryDao.tryInsert("AES-256-GCM", "ENCRYPT", currentUser, plaintext.length(), cipher.length());
            lbl_status.setText("Đã mã hóa AES-256 (GCM). " + db);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Mã hóa thất bại: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btn_decryptActionPerformed() {
        if (currentUser == null) {
            JOptionPane.showMessageDialog(this, "Hãy Login trước khi dùng AES.");
            return;
        }
        String ciphertext = txt_ciphertext.getText();
        if (ciphertext.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Hãy nhập bản mã.");
            return;
        }
        try {
            String plain = AESCipher.decrypt(ciphertext, new String(txt_key.getPassword()));
            txt_plaintext.setText(plain);
            String db = HistoryDao.tryInsert("AES-256-GCM", "DECRYPT", currentUser, ciphertext.length(), plain.length());
            lbl_status.setText("Đã giải mã AES-256. " + db);
        } catch (Exception ex) {
            lbl_status.setText("Giải mã thất bại.");
            JOptionPane.showMessageDialog(this, "Giải mã thất bại: sai khóa hoặc bản mã bị sửa (GCM tag không khớp).", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btn_saveActionPerformed() {
        String path = FileTextIO.saveText(this, txt_ciphertext, "aes_ciphertext.txt");
        if (path != null) {
            lbl_status.setText("Đã lưu tệp: " + path);
        }
    }

    private void btn_openActionPerformed() {
        String path = FileTextIO.openText(this, txt_ciphertext);
        if (path != null) {
            lbl_status.setText("Đã mở tệp: " + path);
        }
    }

    public static void main(String[] args) {
        SwingUiHelper.launch(frm_AES::new);
    }
}
