package week_02;

import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Bài 2.4: Giao diện Mật mã dòng RC4.
 */
public class frm_RC4 extends JFrame {

    private final JTextArea txt_plaintext = SwingUiHelper.textArea(3, true);
    private final JTextField txt_key = SwingUiHelper.textField("Key", 22);
    private final JTextArea txt_ciphertext = SwingUiHelper.textArea(3, true);
    private final JButton btn_encrypt = SwingUiHelper.button("Encrypt", new Dimension(100, 30));
    private final JButton btn_decrypt = SwingUiHelper.button("Decrypt", new Dimension(100, 30));
    private final JButton btn_save = SwingUiHelper.button("Save File", new Dimension(120, 30));
    private final JButton btn_open = SwingUiHelper.button("Open File", new Dimension(120, 30));
    private final JLabel lbl_status = SwingUiHelper.bodyLabel("Sẵn sàng.");

    public frm_RC4() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        SwingUiHelper.buildBasicCipherForm(
                this,
                "RC4 Cipher",
                "MẬT MÃ DÒNG RC4",
                "Key",
                "1 - 256 byte; bản mã hiển thị dạng HEX",
                txt_plaintext, txt_key, txt_ciphertext,
                btn_encrypt, btn_decrypt, btn_open, btn_save, lbl_status,
                null, null);

        btn_encrypt.addActionListener(e -> btn_encryptActionPerformed());
        btn_decrypt.addActionListener(e -> btn_decryptActionPerformed());
        btn_save.addActionListener(e -> btn_saveActionPerformed());
        btn_open.addActionListener(e -> btn_openActionPerformed());
    }

    private void btn_encryptActionPerformed() {
        // TODO (Bài 2.4 - Dán code nút Encrypt vào đây)
    }

    private void btn_decryptActionPerformed() {
        // TODO (Bài 2.4 - Dán code nút Decrypt vào đây)
    }

    private void btn_saveActionPerformed() {
        // TODO (Bài 2.4 - Dán code nút Save File vào đây)
    }

    private void btn_openActionPerformed() {
        // TODO (Bài 2.4 - Dán code nút Open File vào đây)
    }

    public static void main(String[] args) {
        SwingUiHelper.launch(frm_RC4::new);
    }
}
