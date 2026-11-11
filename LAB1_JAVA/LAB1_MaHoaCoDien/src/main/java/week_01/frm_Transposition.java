package week_01;

import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/** Bài 1.5: Giao diện Mật mã Hoán vị cột (Transposition). */
public class frm_Transposition extends JFrame {

    private final JTextArea txt_plaintext = SwingUiHelper.textArea(3, true);
    private final JTextField txt_key = SwingUiHelper.textField("2,3,1", 12);
    private final JTextArea txt_ciphertext = SwingUiHelper.textArea(3, true);
    private final JButton btn_encrypt = SwingUiHelper.button("Encrypt", new Dimension(100, 30));
    private final JButton btn_decrypt = SwingUiHelper.button("Decrypt", new Dimension(100, 30));
    private final JButton btn_open = SwingUiHelper.button("Open Ciphertext File", new Dimension(170, 30));
    private final JButton btn_save = SwingUiHelper.button("Save Ciphertext File", new Dimension(170, 30));
    private final JLabel lbl_status = SwingUiHelper.bodyLabel("Sẵn sàng.");

    public frm_Transposition() {
        SwingUiHelper.buildBasicCipherForm(
                this,
                "Transposition Cipher",
                "MẬT MÃ HOÁN VỊ CỘT (TRANSPOSITION)",
                "2,3,1",
                "Thứ tự đọc cột, cách nhau dấu phẩy, ví dụ 2,3,1",
                txt_plaintext, txt_key, txt_ciphertext,
                btn_encrypt, btn_decrypt, btn_open, btn_save, lbl_status,
                null, null);

        btn_encrypt.addActionListener(e -> btn_encryptActionPerformed());
        btn_decrypt.addActionListener(e -> btn_decryptActionPerformed());
        btn_open.addActionListener(e -> btn_openActionPerformed());
        btn_save.addActionListener(e -> btn_saveActionPerformed());
    }

    private void btn_encryptActionPerformed() {
        // TODO (Bài 1.5 - Dán code nút Encrypt vào đây)
    }

    private void btn_decryptActionPerformed() {
        // TODO (Bài 1.5 - Dán code nút Decrypt vào đây)
    }

    private void btn_openActionPerformed() {
        // TODO (Bài 1.5 - Dán code nút Open Ciphertext File vào đây)
    }

    private void btn_saveActionPerformed() {
        // TODO (Bài 1.5 - Dán code nút Save Ciphertext File vào đây)
    }

    public static void main(String[] args) {
        SwingUiHelper.launch(frm_Transposition::new);
    }
}
