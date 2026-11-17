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
    private final JTextField txt_key = SwingUiHelper.textField("MySecretKey123", 22);
    private final JTextField txt_iv = SwingUiHelper.textField("", 30);
    private final JButton btn_geniv = SwingUiHelper.button("Generate IV", new Dimension(120, 28));
    private final JTextArea txt_ciphertext = SwingUiHelper.textArea(3, true);
    private final JButton btn_encrypt = SwingUiHelper.button("Encrypt", new Dimension(100, 30));
    private final JButton btn_decrypt = SwingUiHelper.button("Decrypt", new Dimension(100, 30));
    private final JButton btn_save = SwingUiHelper.button("Save File", new Dimension(120, 30));
    private final JButton btn_open = SwingUiHelper.button("Open File", new Dimension(120, 30));
    private final JLabel lbl_status = SwingUiHelper.bodyLabel("Bấm Generate IV trước khi mã hóa.");

    public frm_Twofish() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        JPanel pnlIv = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        pnlIv.add(SwingUiHelper.bodyLabel("IV (HEX 16 byte):"));
        pnlIv.add(txt_iv);
        pnlIv.add(btn_geniv);

        SwingUiHelper.buildBasicCipherForm(
                this,
                "Twofish Cipher",
                "MẬT MÃ TWOFISH (CBC)",
                "MySecretKey123",
                "Khóa chữ bất kỳ (SHA-256 sinh 256 bit)",
                txt_plaintext, txt_key, txt_ciphertext,
                btn_encrypt, btn_decrypt, btn_open, btn_save, lbl_status,
                null, pnlIv);

        btn_geniv.addActionListener(e -> btn_genivActionPerformed());
        btn_encrypt.addActionListener(e -> btn_encryptActionPerformed());
        btn_decrypt.addActionListener(e -> btn_decryptActionPerformed());
        btn_save.addActionListener(e -> btn_saveActionPerformed());
        btn_open.addActionListener(e -> btn_openActionPerformed());
    }

    private void btn_genivActionPerformed() {
        txt_iv.setText(TwofishCipher.newIv());
        lbl_status.setText("Đã sinh IV ngẫu nhiên 16 byte (32 ký tự HEX).");
    }

    private void btn_encryptActionPerformed() {
        String plaintext = txt_plaintext.getText();
        if (plaintext.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Hãy nhập bản rõ (Plaintext).");
            return;
        }
        try {
            String cipher = TwofishCipher.encrypt(plaintext, txt_key.getText(), txt_iv.getText().trim());
            txt_ciphertext.setText(cipher);
            String db = HistoryDao.tryInsert("Twofish-CBC", "ENCRYPT", null, plaintext.length(), cipher.length());
            lbl_status.setText("Đã mã hóa Twofish (CBC + PKCS7). " + db);
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
            String plain = TwofishCipher.decrypt(ciphertext, txt_key.getText(), txt_iv.getText().trim());
            txt_plaintext.setText(plain);
            String db = HistoryDao.tryInsert("Twofish-CBC", "DECRYPT", null, ciphertext.length(), plain.length());
            lbl_status.setText("Đã giải mã Twofish. " + db);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            lbl_status.setText("Giải mã thất bại.");
            JOptionPane.showMessageDialog(this, "Giải mã thất bại: sai Key/IV hoặc bản mã bị sửa.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btn_saveActionPerformed() {
        if (txt_ciphertext.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "Chưa có bản mã để lưu.");
            return;
        }
        String content = TwofishCipher.toFile(txt_iv.getText().trim(), txt_ciphertext.getText().trim());
        String path = FileTextIO.saveString(this, content, "twofish_ciphertext.txt");
        if (path != null) {
            lbl_status.setText("Đã lưu tệp (chứa cả IV và bản mã): " + path);
        }
    }

    private void btn_openActionPerformed() {
        String content = FileTextIO.openString(this);
        if (content == null) {
            return;
        }
        try {
            String[] parts = TwofishCipher.fromFile(content);
            txt_iv.setText(parts[0]);
            txt_ciphertext.setText(parts[1]);
            lbl_status.setText("Đã nạp IV và bản mã từ tệp. Nhập đúng Key rồi bấm Decrypt.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi định dạng", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUiHelper.launch(frm_Twofish::new);
    }
}
