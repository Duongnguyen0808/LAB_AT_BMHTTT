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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Bài 2.6 - Nâng cao 01: AES-256 chế độ CTR (Counter).
 */
public class frm_AES_CTR extends JFrame {

    private final JTextArea txt_plaintext = SwingUiHelper.textArea(3, true);
    private final JTextField txt_key = SwingUiHelper.textField("", 36);
    private final JButton btn_genkey = SwingUiHelper.button("Generate Key", new Dimension(130, 28));
    private final JTextField txt_nonce = SwingUiHelper.textField("", 26);
    private final JButton btn_gennonce = SwingUiHelper.button("Generate Nonce", new Dimension(130, 28));
    private final JTextArea txt_ciphertext = SwingUiHelper.textArea(3, true);
    private final JButton btn_encrypt = SwingUiHelper.button("Encrypt", new Dimension(100, 30));
    private final JButton btn_decrypt = SwingUiHelper.button("Decrypt", new Dimension(100, 30));
    private final JButton btn_save = SwingUiHelper.button("Save File", new Dimension(120, 30));
    private final JButton btn_open = SwingUiHelper.button("Open File", new Dimension(120, 30));
    private final JLabel lbl_status = SwingUiHelper.bodyLabel("Bấm Generate Key và Generate Nonce trước.");

    public frm_AES_CTR() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("AES-CTR Cipher");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel root = new JPanel(new BorderLayout(10, 12));
        root.setBorder(BorderFactory.createEmptyBorder(16, 18, 16, 18));

        JPanel header = new JPanel(new GridLayout(2, 1, 0, 4));
        header.add(SwingUiHelper.titleLabel("BÀI NÂNG CAO 01 - AES-256 CHẾ ĐỘ CTR"));
        header.add(SwingUiHelper.bodyLabel("Chế độ Counter biến AES thành mã dòng (không cần đệm, xử lý song song):"));
        root.add(header, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(4, 4, 4, 4);
        g.fill = GridBagConstraints.HORIZONTAL;
        g.weightx = 1.0;
        int row = 0;

        g.gridx = 0; g.gridy = row++;
        center.add(SwingUiHelper.bodyLabel("Plaintext (Bản rõ):"), g);
        g.gridy = row++;
        center.add(new JScrollPane(txt_plaintext), g);

        g.gridy = row++;
        JPanel pnlKey = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        pnlKey.add(SwingUiHelper.bodyLabel("Key (Base64 32 byte):"));
        pnlKey.add(txt_key);
        pnlKey.add(btn_genkey);
        center.add(pnlKey, g);

        g.gridy = row++;
        JPanel pnlNonce = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        pnlNonce.add(SwingUiHelper.bodyLabel("Nonce (Base64 12 byte):"));
        pnlNonce.add(txt_nonce);
        pnlNonce.add(btn_gennonce);
        center.add(pnlNonce, g);

        g.gridy = row++;
        center.add(SwingUiHelper.bodyLabel("Ciphertext (Bản mã):"), g);
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
        setMinimumSize(new Dimension(660, 560));

        btn_genkey.addActionListener(e -> btn_genkeyActionPerformed());
        btn_gennonce.addActionListener(e -> btn_gennonceActionPerformed());
        btn_encrypt.addActionListener(e -> btn_encryptActionPerformed());
        btn_decrypt.addActionListener(e -> btn_decryptActionPerformed());
        btn_save.addActionListener(e -> btn_saveActionPerformed());
        btn_open.addActionListener(e -> btn_openActionPerformed());
    }

    private void btn_genkeyActionPerformed() {
        txt_key.setText(AESCTRCipher.newKey());
        lbl_status.setText("Đã sinh Key 32 byte (Base64).");
    }

    private void btn_gennonceActionPerformed() {
        txt_nonce.setText(AESCTRCipher.newNonce());
        lbl_status.setText("Đã sinh Nonce 12 byte (Base64).");
    }

    private void btn_encryptActionPerformed() {
        String plaintext = txt_plaintext.getText();
        if (plaintext.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Hãy nhập bản rõ (Plaintext).");
            return;
        }
        try {
            String cipher = AESCTRCipher.encrypt(plaintext, txt_key.getText().trim(), txt_nonce.getText().trim());
            txt_ciphertext.setText(cipher);
            String db = HistoryDao.tryInsert("AES-CTR", "ENCRYPT", null, plaintext.length(), cipher.length());
            lbl_status.setText("Đã mã hóa AES-CTR. " + db);
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
            String plain = AESCTRCipher.decrypt(ciphertext, txt_key.getText().trim(), txt_nonce.getText().trim());
            txt_plaintext.setText(plain);
            String db = HistoryDao.tryInsert("AES-CTR", "DECRYPT", null, ciphertext.length(), plain.length());
            lbl_status.setText("Đã giải mã AES-CTR. " + db);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            lbl_status.setText("Giải mã thất bại.");
            JOptionPane.showMessageDialog(this, "Giải mã thất bại: sai Key/Nonce hoặc bản mã bị sửa.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btn_saveActionPerformed() {
        if (txt_ciphertext.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "Chưa có bản mã để lưu.");
            return;
        }
        String content = AESCTRCipher.toFile(txt_nonce.getText().trim(), txt_ciphertext.getText().trim());
        String path = FileTextIO.saveString(this, content, "aes_ctr_ciphertext.txt");
        if (path != null) {
            lbl_status.setText("Đã lưu tệp (chứa cả Nonce và bản mã): " + path);
        }
    }

    private void btn_openActionPerformed() {
        String content = FileTextIO.openString(this);
        if (content == null) {
            return;
        }
        try {
            String[] parts = AESCTRCipher.fromFile(content);
            txt_nonce.setText(parts[0]);
            txt_ciphertext.setText(parts[1]);
            lbl_status.setText("Đã nạp Nonce và bản mã từ tệp. Nhập đúng Key rồi bấm Decrypt.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi định dạng", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUiHelper.launch(frm_AES_CTR::new);
    }
}
