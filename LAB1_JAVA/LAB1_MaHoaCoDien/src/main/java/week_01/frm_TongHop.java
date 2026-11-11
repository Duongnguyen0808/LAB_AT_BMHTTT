package week_01;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

/** Phần 3.3: Chương trình tổng hợp 6 thuật toán & tự động lưu lịch sử SQL Server. */
public class frm_TongHop extends JFrame {

    private final JComboBox<String> cbo_algorithm = new JComboBox<>(
            new String[] {"Caesar", "Vigenere", "Rail Fence", "Playfair", "Transposition", "Autokey"});
    private final JLabel lbl_hint = SwingUiHelper.bodyLabel("Khóa mẫu: 3");
    private final JTextArea txt_plaintext = SwingUiHelper.textArea(3, true);
    private final JTextField txt_key = SwingUiHelper.textField("3", 16);
    private final JTextArea txt_ciphertext = SwingUiHelper.textArea(3, true);
    private final JButton btn_encrypt = SwingUiHelper.button("Encrypt", new Dimension(100, 30));
    private final JButton btn_decrypt = SwingUiHelper.button("Decrypt", new Dimension(100, 30));
    private final JButton btn_open = SwingUiHelper.button("Open File", new Dimension(110, 30));
    private final JButton btn_save = SwingUiHelper.button("Save File", new Dimension(110, 30));
    private final JButton btn_sql = SwingUiHelper.button("Kết nối SQL Server", new Dimension(170, 30));
    private final JButton btn_history = SwingUiHelper.button("Xem lịch sử", new Dimension(130, 30));
    private final JLabel lbl_status = SwingUiHelper.bodyLabel("Sẵn sàng.");

    public frm_TongHop() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("Chương trình tổng hợp - 6 thuật toán");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        cbo_algorithm.setFont(SwingUiHelper.FONT_BODY);
        cbo_algorithm.setPreferredSize(new Dimension(200, 28));

        JPanel root = new JPanel(new GridBagLayout());
        root.setBorder(BorderFactory.createEmptyBorder(12, 14, 12, 14));

        int row = 0;
        root.add(SwingUiHelper.titleLabel("CHƯƠNG TRÌNH TỔNG HỢP 6 THUẬT TOÁN CỔ ĐIỂN"),
                SwingUiHelper.gbc(0, row++, 3, 1.0, 0, GridBagConstraints.HORIZONTAL));

        root.add(SwingUiHelper.bodyLabel("Thuật toán:"),
                SwingUiHelper.gbc(0, row, 1, 0, 0, GridBagConstraints.NONE));
        root.add(cbo_algorithm, SwingUiHelper.gbc(1, row, 1, 0.3, 0, GridBagConstraints.HORIZONTAL));
        root.add(lbl_hint, SwingUiHelper.gbc(2, row++, 1, 0.7, 0, GridBagConstraints.HORIZONTAL));

        root.add(SwingUiHelper.bodyLabel("Plaintext:"),
                SwingUiHelper.gbc(0, row, 1, 0, 0, GridBagConstraints.NONE));
        root.add(new JScrollPane(txt_plaintext),
                SwingUiHelper.gbc(1, row++, 2, 1.0, 0.35, GridBagConstraints.BOTH));

        root.add(SwingUiHelper.bodyLabel("Key:"),
                SwingUiHelper.gbc(0, row, 1, 0, 0, GridBagConstraints.NONE));
        root.add(txt_key, SwingUiHelper.gbc(1, row++, 2, 1.0, 0, GridBagConstraints.HORIZONTAL));

        root.add(SwingUiHelper.bodyLabel("Ciphertext:"),
                SwingUiHelper.gbc(0, row, 1, 0, 0, GridBagConstraints.NONE));
        root.add(new JScrollPane(txt_ciphertext),
                SwingUiHelper.gbc(1, row++, 2, 1.0, 0.35, GridBagConstraints.BOTH));

        JPanel rowButtons1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 2));
        rowButtons1.add(btn_encrypt);
        rowButtons1.add(btn_decrypt);
        rowButtons1.add(btn_open);
        rowButtons1.add(btn_save);
        root.add(rowButtons1, SwingUiHelper.gbc(0, row++, 3, 1.0, 0, GridBagConstraints.HORIZONTAL));

        JPanel rowButtons2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 2));
        rowButtons2.add(btn_sql);
        rowButtons2.add(btn_history);
        root.add(rowButtons2, SwingUiHelper.gbc(0, row++, 3, 1.0, 0, GridBagConstraints.HORIZONTAL));

        root.add(lbl_status, SwingUiHelper.gbc(0, row, 3, 1.0, 0, GridBagConstraints.HORIZONTAL));

        setLayout(new BorderLayout());
        add(root, BorderLayout.CENTER);
        pack();
        setMinimumSize(new Dimension(660, getHeight()));

        cbo_algorithm.addActionListener(e -> cbo_algorithmActionPerformed());
        btn_encrypt.addActionListener(e -> btn_encryptActionPerformed());
        btn_decrypt.addActionListener(e -> btn_decryptActionPerformed());
        btn_open.addActionListener(e -> btn_openActionPerformed());
        btn_save.addActionListener(e -> btn_saveActionPerformed());
        btn_sql.addActionListener(e -> btn_sqlActionPerformed());
        btn_history.addActionListener(e -> btn_historyActionPerformed());
    }

    private void cbo_algorithmActionPerformed() {
        // TODO (Phần 3.3 - Dán code cbo_algorithmActionPerformed vào đây)
    }

    private void btn_encryptActionPerformed() {
        // TODO (Phần 3.3 - Dán code nút Encrypt vào đây)
    }

    private void btn_decryptActionPerformed() {
        // TODO (Phần 3.3 - Dán code nút Decrypt vào đây)
    }

    private void btn_openActionPerformed() {
        // TODO (Phần 3.3 - Dán code nút Open File vào đây)
    }

    private void btn_saveActionPerformed() {
        // TODO (Phần 3.3 - Dán code nút Save File vào đây)
    }

    private void btn_sqlActionPerformed() {
        // TODO (Phần 3.5 - Dán code nút Kết nối SQL Server vào đây)
    }

    private void btn_historyActionPerformed() {
        // TODO (Phần 3.5 - Dán code nút Xem lịch sử vào đây)
    }

    public static void main(String[] args) {
        SwingUiHelper.launch(frm_TongHop::new);
    }
}
