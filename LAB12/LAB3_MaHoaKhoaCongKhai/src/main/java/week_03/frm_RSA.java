package week_03;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Bài 3.2: Mật mã RSA (OAEP - SHA-256) với giao diện Swing.
 */
public class frm_RSA extends JFrame {

    /** Đối tượng RSA giữ cặp khóa trong suốt thời gian form mở. */
    private final RSACipher rsa = new RSACipher();

    private final JComboBox<String> cbo_bits = new JComboBox<>(new String[]{"2048", "3072"});
    private final JButton btn_generate = SwingUiHelper.button("Generate Keys", new Dimension(140, 30));
    private final JLabel lbl_keyinfo = SwingUiHelper.bodyLabel("Chưa có khóa.");
    private final JTextArea txt_plaintext = SwingUiHelper.textArea(2, true);
    private final JTextArea txt_ciphertext = SwingUiHelper.textArea(3, true);
    private final JButton btn_encrypt = SwingUiHelper.button("Encrypt", new Dimension(110, 30));
    private final JButton btn_decrypt = SwingUiHelper.button("Decrypt", new Dimension(110, 30));
    private final JLabel lbl_status = SwingUiHelper.bodyLabel("Bấm Generate Keys để tạo cặp khóa.");

    public frm_RSA() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("RSA Cipher");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        cbo_bits.setFont(SwingUiHelper.FONT_BODY);
        cbo_bits.setPreferredSize(new Dimension(110, 28));

        JPanel root = new JPanel(new BorderLayout(10, 12));
        root.setBorder(BorderFactory.createEmptyBorder(16, 18, 16, 18));
        root.add(SwingUiHelper.titleLabel("MẬT MÃ RSA (OAEP - SHA-256)"), BorderLayout.NORTH);

        JPanel center = new JPanel(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(5, 5, 5, 5);
        g.anchor = GridBagConstraints.NORTHWEST;
        g.fill = GridBagConstraints.HORIZONTAL;

        g.gridx = 0; g.gridy = 0; g.weightx = 0;
        center.add(SwingUiHelper.bodyLabel("Key size:"), g);

        JPanel pnlKey = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        pnlKey.add(cbo_bits);
        pnlKey.add(btn_generate);
        pnlKey.add(lbl_keyinfo);
        g.gridx = 1; g.weightx = 1.0;
        center.add(pnlKey, g);

        g.gridx = 0; g.gridy = 1; g.weightx = 0;
        center.add(SwingUiHelper.bodyLabel("Plaintext:"), g);
        g.gridx = 1; g.weightx = 1.0;
        JScrollPane spPlain = new JScrollPane(txt_plaintext);
        spPlain.setPreferredSize(new Dimension(600, 65));
        center.add(spPlain, g);

        g.gridx = 0; g.gridy = 2; g.weightx = 0;
        center.add(SwingUiHelper.bodyLabel("Ciphertext:"), g);
        g.gridx = 1; g.weightx = 1.0;
        JScrollPane spCipher = new JScrollPane(txt_ciphertext);
        spCipher.setPreferredSize(new Dimension(600, 110));
        center.add(spCipher, g);

        g.gridx = 1; g.gridy = 3;
        JPanel pnlBtns = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 2));
        pnlBtns.add(btn_encrypt);
        pnlBtns.add(btn_decrypt);
        center.add(pnlBtns, g);

        root.add(center, BorderLayout.CENTER);
        root.add(lbl_status, BorderLayout.SOUTH);

        setContentPane(root);
        pack();

        btn_generate.addActionListener(e -> btn_generateActionPerformed(e));
        btn_encrypt.addActionListener(e -> btn_encryptActionPerformed(e));
        btn_decrypt.addActionListener(e -> btn_decryptActionPerformed(e));
    }

    private void btn_generateActionPerformed(java.awt.event.ActionEvent evt) {
        int bits = Integer.parseInt((String) cbo_bits.getSelectedItem());
        try {
            long start = System.currentTimeMillis();
            rsa.generateKeys(bits);
            long ms = System.currentTimeMillis() - start;
            lbl_keyinfo.setText("RSA-" + bits + ": tạo khóa " + ms + " ms, tối đa " + rsa.maxPlaintextBytes() + " byte");
            lbl_status.setText("Đã tạo cặp khóa. Khóa công khai dùng để mã hóa, khóa riêng để giải mã.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Không tạo được khóa: " + ex.getMessage(),
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
            String ciphertext = rsa.encrypt(plaintext);
            txt_ciphertext.setText(ciphertext);
            txt_ciphertext.setCaretPosition(0);
            lbl_status.setText("Đã mã hóa RSA (" + ciphertext.length() + " ký tự Base64). "
                    + HistoryDao.tryInsert("RSA", "ENCRYPT", plaintext.length(), true));
        } catch (Exception ex) {
            HistoryDao.tryInsert("RSA", "ENCRYPT", plaintext.length(), false);
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
            String plaintext = rsa.decrypt(ciphertext);
            txt_plaintext.setText(plaintext);
            txt_plaintext.setCaretPosition(0);
            lbl_status.setText("Đã giải mã RSA. " + HistoryDao.tryInsert("RSA", "DECRYPT", ciphertext.length(), true));
        } catch (Exception ex) {
            HistoryDao.tryInsert("RSA", "DECRYPT", ciphertext.length(), false);
            lbl_status.setText("Giải mã RSA THẤT BẠI (sai khóa hoặc bản mã bị sửa).");
            JOptionPane.showMessageDialog(this, "Giải mã thất bại: sai khóa riêng hoặc bản mã bị sửa.\n" + ex,
                    "Giải mã thất bại", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUiHelper.launch(frm_RSA::new);
    }
}
