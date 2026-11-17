package week_02;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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

    // TODO (Bài 2.3 - Dán: private String currentUser = null;)

    private final JTextField txt_username = SwingUiHelper.textField("sinhvien01", 18);
    private final JPasswordField txt_password = new JPasswordField(18);
    private final JButton btn_sql = SwingUiHelper.button("Kết nối SQL Server", new Dimension(170, 30));
    private final JButton btn_register = SwingUiHelper.button("Register", new Dimension(110, 30));
    private final JButton btn_login = SwingUiHelper.button("Login", new Dimension(110, 30));
    private final JTextField txt_ticket = SwingUiHelper.textField("", 32);

    private final JTextArea txt_plaintext = SwingUiHelper.textArea(3, true);
    private final JTextField txt_key = SwingUiHelper.textField("", 24);
    private final JTextArea txt_ciphertext = SwingUiHelper.textArea(3, true);
    private final JButton btn_encrypt = SwingUiHelper.button("Encrypt", new Dimension(100, 30));
    private final JButton btn_decrypt = SwingUiHelper.button("Decrypt", new Dimension(100, 30));
    private final JButton btn_save = SwingUiHelper.button("Save File", new Dimension(120, 30));
    private final JButton btn_open = SwingUiHelper.button("Open File", new Dimension(120, 30));
    private final JLabel lbl_status = SwingUiHelper.bodyLabel("Sẵn sàng.");

    public frm_AES() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("AES Cipher - Register / Login");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        txt_password.setFont(SwingUiHelper.FONT_MONO);
        txt_password.setPreferredSize(new Dimension(txt_password.getPreferredSize().width, 30));
        txt_ticket.setEditable(false);

        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBorder(BorderFactory.createEmptyBorder(14, 18, 14, 18));
        root.add(SwingUiHelper.titleLabel("AES-GCM - ĐĂNG KÝ / ĐĂNG NHẬP (SQL SERVER)"), BorderLayout.NORTH);

        JPanel center = new JPanel(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(4, 4, 4, 4);
        g.fill = GridBagConstraints.HORIZONTAL;
        g.weightx = 1.0;
        int row = 0;

        JPanel pnlUser = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 2));
        pnlUser.add(SwingUiHelper.bodyLabel("Username:"));
        pnlUser.add(txt_username);
        center.add(pnlUser, g);

        g.gridy = ++row;
        JPanel pnlPass = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 2));
        pnlPass.add(SwingUiHelper.bodyLabel("Password:"));
        pnlPass.add(txt_password);
        pnlPass.add(SwingUiHelper.bodyLabel("(ít nhất 8 ký tự)"));
        center.add(pnlPass, g);

        g.gridy = ++row;
        JPanel pnlAuthBtns = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 2));
        pnlAuthBtns.add(btn_sql);
        pnlAuthBtns.add(btn_register);
        pnlAuthBtns.add(btn_login);
        center.add(pnlAuthBtns, g);

        g.gridy = ++row;
        JPanel pnlTicket = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 2));
        pnlTicket.add(SwingUiHelper.bodyLabel("Ticket:"));
        pnlTicket.add(txt_ticket);
        center.add(pnlTicket, g);

        g.gridy = ++row;
        center.add(SwingUiHelper.bodyLabel("Plaintext:"), g);
        g.gridy = ++row;
        center.add(new JScrollPane(txt_plaintext), g);

        g.gridy = ++row;
        JPanel pnlKey = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        pnlKey.add(SwingUiHelper.bodyLabel("Key AES:"));
        pnlKey.add(txt_key);
        pnlKey.add(SwingUiHelper.bodyLabel("Mật khẩu để tạo khóa AES-256"));
        center.add(pnlKey, g);

        g.gridy = ++row;
        center.add(SwingUiHelper.bodyLabel("Ciphertext:"), g);
        g.gridy = ++row;
        center.add(new JScrollPane(txt_ciphertext), g);

        g.gridy = ++row;
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
        setMinimumSize(new Dimension(640, 620));

        btn_sql.addActionListener(e -> btn_sqlActionPerformed());
        btn_register.addActionListener(e -> btn_registerActionPerformed());
        btn_login.addActionListener(e -> btn_loginActionPerformed());
        btn_encrypt.addActionListener(e -> btn_encryptActionPerformed());
        btn_decrypt.addActionListener(e -> btn_decryptActionPerformed());
        btn_save.addActionListener(e -> btn_saveActionPerformed());
        btn_open.addActionListener(e -> btn_openActionPerformed());
    }

    private void btn_sqlActionPerformed() {
        // TODO (Bài 2.3 - Dán code nút Kết nối SQL Server vào đây)
    }

    private void btn_registerActionPerformed() {
        // TODO (Bài 2.3 - Dán code nút Register vào đây)
    }

    private void btn_loginActionPerformed() {
        // TODO (Bài 2.3 - Dán code nút Login vào đây)
    }

    private void btn_encryptActionPerformed() {
        // TODO (Bài 2.3 - Dán code nút Encrypt vào đây)
    }

    private void btn_decryptActionPerformed() {
        // TODO (Bài 2.3 - Dán code nút Decrypt vào đây)
    }

    private void btn_saveActionPerformed() {
        // TODO (Bài 2.3 - Dán code nút Save File vào đây)
    }

    private void btn_openActionPerformed() {
        // TODO (Bài 2.3 - Dán code nút Open File vào đây)
    }

    public static void main(String[] args) {
        SwingUiHelper.launch(frm_AES::new);
    }
}
