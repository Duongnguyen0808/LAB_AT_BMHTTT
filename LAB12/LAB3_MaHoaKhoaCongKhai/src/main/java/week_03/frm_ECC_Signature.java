package week_03;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
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
 * Bài 3.5: Chữ ký số ECDSA (SHA256withECDSA, đường cong secp256r1).
 */
public class frm_ECC_Signature extends JFrame {

    private final ECCSignature ecdsa = new ECCSignature();

    private final JTextArea txt_public = SwingUiHelper.textArea(2, false);
    private final JTextArea txt_private = SwingUiHelper.textArea(2, false);
    private final JButton btn_generate = SwingUiHelper.button("Generate Keys", new Dimension(140, 30));
    private final JButton btn_savekeys = SwingUiHelper.button("Save Keys", new Dimension(120, 30));
    private final JButton btn_loadkeys = SwingUiHelper.button("Load Keys", new Dimension(120, 30));
    private final JTextArea txt_message = SwingUiHelper.textArea(2, true);
    private final JTextArea txt_signature = SwingUiHelper.textArea(2, true);
    private final JButton btn_sign = SwingUiHelper.button("Sign", new Dimension(110, 30));
    private final JButton btn_verify = SwingUiHelper.button("Verify", new Dimension(110, 30));
    private final JLabel lbl_result = new JLabel("");
    private final JLabel lbl_status = SwingUiHelper.bodyLabel("Bấm Generate Keys hoặc Load Keys.");

    public frm_ECC_Signature() {
        initComponents();
        setLocationRelativeTo(null);
    }

    /** Hiện khóa công khai / khóa riêng (Base64) lên form. */
    private void showKeys() {
        txt_public.setText(ecdsa.getPublicKeyBase64());
        txt_private.setText(ecdsa.getPrivateKeyBase64());
        txt_public.setCaretPosition(0);
        txt_private.setCaretPosition(0);
    }

    private void initComponents() {
        setTitle("ECDSA Signature");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        lbl_result.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lbl_result.setPreferredSize(new Dimension(240, 36));

        JPanel root = new JPanel(new BorderLayout(10, 12));
        root.setBorder(BorderFactory.createEmptyBorder(16, 18, 16, 18));
        root.add(SwingUiHelper.titleLabel("CHỮ KÝ SỐ ECDSA (SHA256withECDSA)"), BorderLayout.NORTH);

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
        center.add(SwingUiHelper.bodyLabel("Message:"), g);
        g.gridx = 1; g.weightx = 1.0;
        JScrollPane spMsg = new JScrollPane(txt_message);
        spMsg.setPreferredSize(new Dimension(600, 58));
        center.add(spMsg, g);

        g.gridx = 0; g.gridy = 4; g.weightx = 0;
        center.add(SwingUiHelper.bodyLabel("Signature:"), g);
        g.gridx = 1; g.weightx = 1.0;
        JScrollPane spSig = new JScrollPane(txt_signature);
        spSig.setPreferredSize(new Dimension(600, 58));
        center.add(spSig, g);

        g.gridx = 1; g.gridy = 5;
        JPanel pnlBtns = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 2));
        pnlBtns.add(btn_sign);
        pnlBtns.add(btn_verify);
        pnlBtns.add(lbl_result);
        center.add(pnlBtns, g);

        root.add(center, BorderLayout.CENTER);
        root.add(lbl_status, BorderLayout.SOUTH);

        setContentPane(root);
        pack();

        btn_generate.addActionListener(e -> btn_generateActionPerformed(e));
        btn_savekeys.addActionListener(e -> btn_savekeysActionPerformed(e));
        btn_loadkeys.addActionListener(e -> btn_loadkeysActionPerformed(e));
        btn_sign.addActionListener(e -> btn_signActionPerformed(e));
        btn_verify.addActionListener(e -> btn_verifyActionPerformed(e));
    }

    private void btn_generateActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            ecdsa.generateKeys();
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
        String path = FileTextIO.saveString(this, content, "ecdsa_keys.txt");
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
            ecdsa.loadKeys(keys[0], keys[1]);
            txt_public.setText(keys[0]);
            txt_private.setText(keys[1]);
            lbl_status.setText("Đã nạp khóa từ tệp.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Tệp khóa không hợp lệ: " + ex.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btn_signActionPerformed(java.awt.event.ActionEvent evt) {
        String message = txt_message.getText();
        try {
            txt_signature.setText(ecdsa.sign(message));
            lbl_result.setText("");
            lbl_status.setText("Đã ký bằng KHÓA RIÊNG. " + HistoryDao.tryInsert("ECDSA", "SIGN", message.length(), true));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Ký thất bại: " + ex.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btn_verifyActionPerformed(java.awt.event.ActionEvent evt) {
        String message = txt_message.getText();
        try {
            boolean ok = ecdsa.verify(message, txt_signature.getText());
            lbl_result.setText(ok ? "VALID" : "INVALID");
            lbl_result.setForeground(ok ? new Color(0, 128, 0) : Color.RED);
            lbl_status.setText("Đã xác minh bằng KHÓA CÔNG KHAI. " + HistoryDao.tryInsert("ECDSA", "VERIFY", message.length(), ok));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Xác minh thất bại: " + ex.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUiHelper.launch(frm_ECC_Signature::new);
    }
}
