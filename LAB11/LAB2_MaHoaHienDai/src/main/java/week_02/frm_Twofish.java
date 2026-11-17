package week_02;

import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Bài 2.5: Giao diện Mật mã Twofish (CBC) dùng thư viện BouncyCastle.
 */
public class frm_Twofish extends JFrame {

    private final JTextArea txt_plaintext = SwingUiHelper.textArea(3, true);
    private final JTextField txt_key = SwingUiHelper.textField("Twofish@Lab2", 24);
    private final JTextField txt_iv = SwingUiHelper.textField("", 30);
    private final JButton btn_iv = SwingUiHelper.button("Generate IV", new Dimension(130, 30));
    private final JTextArea txt_ciphertext = SwingUiHelper.textArea(3, true);
    private final JButton btn_encrypt = SwingUiHelper.button("Encrypt", new Dimension(100, 30));
    private final JButton btn_decrypt = SwingUiHelper.button("Decrypt", new Dimension(100, 30));
    private final JButton btn_save = SwingUiHelper.button("Save File", new Dimension(120, 30));
    private final JButton btn_open = SwingUiHelper.button("Open File", new Dimension(120, 30));
    private final JLabel lbl_status = SwingUiHelper.bodyLabel("Sẵn sàng.");

    public frm_Twofish() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        JPanel pnlIv = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        pnlIv.add(SwingUiHelper.bodyLabel("IV (HEX):"));
        pnlIv.add(txt_iv);
        pnlIv.add(btn_iv);

        SwingUiHelper.buildBasicCipherForm(
                this,
                "Twofish Cipher",
                "MẬT MÃ TWOFISH (CBC)",
                "Twofish@Lab2",
                "Mật khẩu bất kỳ (khóa = SHA-256)",
                txt_plaintext, txt_key, txt_ciphertext,
                btn_encrypt, btn_decrypt, btn_open, btn_save, lbl_status,
                null, pnlIv);

        btn_iv.addActionListener(e -> btn_ivActionPerformed());
        btn_encrypt.addActionListener(e -> btn_encryptActionPerformed());
        btn_decrypt.addActionListener(e -> btn_decryptActionPerformed());
        btn_save.addActionListener(e -> btn_saveActionPerformed());
        btn_open.addActionListener(e -> btn_openActionPerformed());
    }

    private void btn_ivActionPerformed() {
        // TODO (Bài 2.5 - Dán code nút Generate IV vào đây)
    }

    private void btn_encryptActionPerformed() {
        // TODO (Bài 2.5 - Dán code nút Encrypt vào đây)
    }

    private void btn_decryptActionPerformed() {
        // TODO (Bài 2.5 - Dán code nút Decrypt vào đây)
    }

    private void btn_saveActionPerformed() {
        // TODO (Bài 2.5 - Dán code nút Save File vào đây)
    }

    private void btn_openActionPerformed() {
        // TODO (Bài 2.5 - Dán code nút Open File vào đây)
    }

    public static void main(String[] args) {
        SwingUiHelper.launch(frm_Twofish::new);
    }
}
