package week_02;

import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Bài 2.1: Giao diện Mật mã DES (CBC).
 */
public class frm_DES extends JFrame {

    private final JTextArea txt_plaintext = SwingUiHelper.textArea(3, true);
    private final JTextField txt_key = SwingUiHelper.textField("12345678", 16);
    private final JTextArea txt_ciphertext = SwingUiHelper.textArea(3, true);
    private final JButton btn_encrypt = SwingUiHelper.button("Encrypt", new Dimension(100, 30));
    private final JButton btn_decrypt = SwingUiHelper.button("Decrypt", new Dimension(100, 30));
    private final JButton btn_save = SwingUiHelper.button("Save File", new Dimension(120, 30));
    private final JButton btn_open = SwingUiHelper.button("Open File", new Dimension(120, 30));
    private final JLabel lbl_status = SwingUiHelper.bodyLabel("Sẵn sàng.");

    public frm_DES() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        SwingUiHelper.buildBasicCipherForm(
                this,
                "DES Cipher",
                "MẬT MÃ DES (CBC)",
                "12345678",
                "Đúng 8 ký tự ASCII = 8 byte",
                txt_plaintext, txt_key, txt_ciphertext,
                btn_encrypt, btn_decrypt, btn_open, btn_save, lbl_status,
                null, null);

        btn_encrypt.addActionListener(e -> btn_encryptActionPerformed());
        btn_decrypt.addActionListener(e -> btn_decryptActionPerformed());
        btn_save.addActionListener(e -> btn_saveActionPerformed());
        btn_open.addActionListener(e -> btn_openActionPerformed());
    }

    private void btn_encryptActionPerformed() {
        String plaintext = txt_plaintext.getText();
        if (plaintext.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Hãy nhập bản rõ (Plaintext).");
            return;
        }
        try {
            String cipher = DESCipher.encrypt(plaintext, txt_key.getText());
            txt_ciphertext.setText(cipher);
            String db = HistoryDao.tryInsert("DES-CBC", "ENCRYPT", null, plaintext.length(), cipher.length());
            lbl_status.setText("Đã mã hóa DES (CBC + PKCS5). " + db);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            lbl_status.setText("Lỗi mã hóa: " + ex.getMessage());
            JOptionPane.showMessageDialog(this, "Mã hóa thất bại: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btn_decryptActionPerformed() {
        String ciphertext = txt_ciphertext.getText();
        if (ciphertext.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Hãy nhập hoặc mở tệp bản mã (Ciphertext).");
            return;
        }
        try {
            String plain = DESCipher.decrypt(ciphertext, txt_key.getText());
            txt_plaintext.setText(plain);
            String db = HistoryDao.tryInsert("DES-CBC", "DECRYPT", null, ciphertext.length(), plain.length());
            lbl_status.setText("Đã giải mã DES. " + db);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            lbl_status.setText("Giải mã thất bại.");
            JOptionPane.showMessageDialog(this, "Giải mã thất bại: sai khóa hoặc bản mã bị sửa.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btn_saveActionPerformed() {
        String path = FileTextIO.saveText(this, txt_ciphertext, "des_ciphertext.txt");
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
        SwingUiHelper.launch(frm_DES::new);
    }
}
