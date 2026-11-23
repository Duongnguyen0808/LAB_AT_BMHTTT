package week_03;

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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Bài 3.4: Hệ mã lai RSA + AES-256-GCM cho văn bản dài.
 */
public class frm_RSA_AES extends JFrame {

    private final RSA_AES_Cipher hybrid = new RSA_AES_Cipher();

    private final JTextArea txt_public = SwingUiHelper.textArea(2, false);
    private final JTextArea txt_private = SwingUiHelper.textArea(2, false);
    private final JButton btn_generate = SwingUiHelper.button("Generate Keys", new Dimension(140, 30));
    private final JButton btn_savekeys = SwingUiHelper.button("Save Keys", new Dimension(120, 30));
    private final JButton btn_loadkeys = SwingUiHelper.button("Load Keys", new Dimension(120, 30));
    private final JTextArea txt_plaintext = SwingUiHelper.textArea(3, true);
    private final JTextArea txt_ciphertext = SwingUiHelper.textArea(3, true);
    private final JButton btn_encrypt = SwingUiHelper.button("Encrypt", new Dimension(110, 30));
    private final JButton btn_decrypt = SwingUiHelper.button("Decrypt", new Dimension(110, 30));
    private final JButton btn_tamper = SwingUiHelper.button("Sửa 1 ký tự bản mã", new Dimension(180, 30));
    private final JLabel lbl_status = SwingUiHelper.bodyLabel("Bấm Generate Keys hoặc Load Keys.");

    public frm_RSA_AES() {
        initComponents();
        setLocationRelativeTo(null);
    }

    /** Hiện khóa công khai / khóa riêng (Base64) lên form. */
    private void showKeys() {
        txt_public.setText(hybrid.getPublicKeyBase64());
        txt_private.setText(hybrid.getPrivateKeyBase64());
        txt_public.setCaretPosition(0);
        txt_private.setCaretPosition(0);
    }

    private void initComponents() {
        setTitle("RSA + AES Cipher");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel root = new JPanel(new BorderLayout(10, 12));
        root.setBorder(BorderFactory.createEmptyBorder(16, 18, 16, 18));
        root.add(SwingUiHelper.titleLabel("HỆ MÃ LAI RSA + AES-GCM (VĂN BẢN DÀI)"), BorderLayout.NORTH);

        JPanel center = new JPanel(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(4, 5, 4, 5);
        g.anchor = GridBagConstraints.NORTHWEST;
        g.fill = GridBagConstraints.HORIZONTAL;

        g.gridx = 0; g.gridy = 0; g.weightx = 0;
        center.add(SwingUiHelper.bodyLabel("Public key:"), g);
        g.gridx = 1; g.weightx = 1.0;
        JScrollPane spPub = new JScrollPane(txt_public);
        spPub.setPreferredSize(new Dimension(600, 48));
        center.add(spPub, g);

        g.gridx = 0; g.gridy = 1; g.weightx = 0;
        center.add(SwingUiHelper.bodyLabel("Private key:"), g);
        g.gridx = 1; g.weightx = 1.0;
        JScrollPane spPri = new JScrollPane(txt_private);
        spPri.setPreferredSize(new Dimension(600, 48));
        center.add(spPri, g);

        g.gridx = 1; g.gridy = 2;
        JPanel pnlKeyBtns = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 2));
        pnlKeyBtns.add(btn_generate);
        pnlKeyBtns.add(btn_savekeys);
        pnlKeyBtns.add(btn_loadkeys);
        center.add(pnlKeyBtns, g);

        g.gridx = 0; g.gridy = 3; g.weightx = 0;
        center.add(SwingUiHelper.bodyLabel("Plaintext:"), g);
        g.gridx = 1; g.weightx = 1.0;
        JScrollPane spPlain = new JScrollPane(txt_plaintext);
        spPlain.setPreferredSize(new Dimension(600, 90));
        center.add(spPlain, g);

        g.gridx = 0; g.gridy = 4; g.weightx = 0;
        center.add(SwingUiHelper.bodyLabel("Ciphertext:"), g);
        g.gridx = 1; g.weightx = 1.0;
        JScrollPane spCipher = new JScrollPane(txt_ciphertext);
        spCipher.setPreferredSize(new Dimension(600, 90));
        center.add(spCipher, g);

        g.gridx = 1; g.gridy = 5;
        JPanel pnlBtns = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 2));
        pnlBtns.add(btn_encrypt);
        pnlBtns.add(btn_decrypt);
        pnlBtns.add(btn_tamper);
        center.add(pnlBtns, g);

        root.add(center, BorderLayout.CENTER);
        root.add(lbl_status, BorderLayout.SOUTH);

        setContentPane(root);
        pack();

        btn_generate.addActionListener(e -> btn_generateActionPerformed(e));
        btn_savekeys.addActionListener(e -> btn_savekeysActionPerformed(e));
        btn_loadkeys.addActionListener(e -> btn_loadkeysActionPerformed(e));
        btn_encrypt.addActionListener(e -> btn_encryptActionPerformed(e));
        btn_decrypt.addActionListener(e -> btn_decryptActionPerformed(e));
        btn_tamper.addActionListener(e -> btn_tamperActionPerformed(e));
    }

    private void btn_generateActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            hybrid.generateKeys();
            showKeys();
            lbl_status.setText("Đã tạo cặp khóa mới. Bản mã/chữ ký cũ sẽ không dùng được với khóa mới.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Không tạo được khóa: " + ex.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btn_savekeysActionPerformed(java.awt.event.ActionEvent evt) {
        if (txt_public.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Chưa có khóa để lưu.");
            return;
        }
        String content = CryptoSupport.toKeyFile(txt_public.getText(), txt_private.getText());
        String path = FileTextIO.saveString(this, content, "rsa_aes_keys.txt");
        if (path != null) {
            lbl_status.setText("Đã lưu khóa vào: " + path + " (giữ bí mật dòng PRIVATE).");
        }
    }

    private void btn_loadkeysActionPerformed(java.awt.event.ActionEvent evt) {
        String content = FileTextIO.openString(this);
        if (content == null) {
            return;
        }
        try {
            String[] keys = CryptoSupport.fromKeyFile(content);
            hybrid.loadKeys(keys[0], keys[1]);
            txt_public.setText(keys[0]);
            txt_private.setText(keys[1]);
            lbl_status.setText("Đã nạp khóa từ tệp.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Tệp khóa không hợp lệ: " + ex.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btn_encryptActionPerformed(java.awt.event.ActionEvent evt) {
        String plaintext = txt_plaintext.getText();
        if (plaintext.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Hãy nhập Plaintext trước khi mã hóa.");
            return;
        }
        try {
            String ciphertext = hybrid.encrypt(plaintext);
            txt_ciphertext.setText(ciphertext);
            txt_ciphertext.setCaretPosition(0);
            lbl_status.setText("Đã mã hóa RSA-AES (" + ciphertext.length() + " ký tự Base64). "
                    + HistoryDao.tryInsert("RSA-AES", "ENCRYPT", plaintext.length(), true));
        } catch (Exception ex) {
            HistoryDao.tryInsert("RSA-AES", "ENCRYPT", plaintext.length(), false);
            JOptionPane.showMessageDialog(this, "Mã hóa thất bại: " + ex.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btn_decryptActionPerformed(java.awt.event.ActionEvent evt) {
        String ciphertext = txt_ciphertext.getText();
        if (ciphertext.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Hãy nhập Ciphertext trước khi giải mã.");
            return;
        }
        try {
            String plaintext = hybrid.decrypt(ciphertext);
            txt_plaintext.setText(plaintext);
            txt_plaintext.setCaretPosition(0);
            lbl_status.setText("Đã giải mã RSA-AES. " + HistoryDao.tryInsert("RSA-AES", "DECRYPT", ciphertext.length(), true));
        } catch (Exception ex) {
            HistoryDao.tryInsert("RSA-AES", "DECRYPT", ciphertext.length(), false);
            lbl_status.setText("Giải mã RSA-AES THẤT BẠI (sai khóa hoặc bản mã bị sửa).");
            JOptionPane.showMessageDialog(this, "Giải mã thất bại: sai khóa riêng hoặc bản mã bị sửa.\n" + ex,
                    "Giải mã thất bại", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btn_tamperActionPerformed(java.awt.event.ActionEvent evt) {
        String c = txt_ciphertext.getText();
        if (c.length() < 400) {
            JOptionPane.showMessageDialog(this, "Hãy Encrypt trước.");
            return;
        }
        int i = c.length() / 2; // một ký tự ở giữa phần dữ liệu AES
        char replaced = c.charAt(i) == 'A' ? 'B' : 'A';
        txt_ciphertext.setText(c.substring(0, i) + replaced + c.substring(i + 1));
        lbl_status.setText("Đã sửa ký tự thứ " + i + " của bản mã. Bấm Decrypt để xem AES-GCM phát hiện.");
    }

    public static void main(String[] args) {
        SwingUiHelper.launch(frm_RSA_AES::new);
    }
}
