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

    // TODO (Bài 3.4 - Dán: private final RSA_AES_Cipher hybrid = new RSA_AES_Cipher();)

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

    // TODO (Bài 3.4 - Dán hàm showKeys() vào đây)

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

        int row = 0;
        g.gridx = 0; g.gridy = row; g.weightx = 0;
        center.add(SwingUiHelper.bodyLabel("Public Key (RSA):"), g);
        g.gridx = 1; g.weightx = 1.0;
        JScrollPane spPub = new JScrollPane(txt_public);
        spPub.setPreferredSize(new Dimension(600, 50));
        center.add(spPub, g);

        g.gridx = 0; g.gridy = ++row; g.weightx = 0;
        center.add(SwingUiHelper.bodyLabel("Private Key (RSA):"), g);
        g.gridx = 1; g.weightx = 1.0;
        JScrollPane spPriv = new JScrollPane(txt_private);
        spPriv.setPreferredSize(new Dimension(600, 50));
        center.add(spPriv, g);

        g.gridx = 1; g.gridy = ++row;
        JPanel pnlKeyBtns = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 2));
        pnlKeyBtns.add(btn_generate);
        pnlKeyBtns.add(btn_savekeys);
        pnlKeyBtns.add(btn_loadkeys);
        center.add(pnlKeyBtns, g);

        g.gridx = 0; g.gridy = ++row; g.weightx = 0;
        center.add(SwingUiHelper.bodyLabel("Plaintext (văn bản dài):"), g);
        g.gridx = 1; g.weightx = 1.0;
        JScrollPane spPlain = new JScrollPane(txt_plaintext);
        spPlain.setPreferredSize(new Dimension(600, 75));
        center.add(spPlain, g);

        g.gridx = 0; g.gridy = ++row; g.weightx = 0;
        center.add(SwingUiHelper.bodyLabel("Ciphertext (Key+IV+Cipher):"), g);
        g.gridx = 1; g.weightx = 1.0;
        JScrollPane spCipher = new JScrollPane(txt_ciphertext);
        spCipher.setPreferredSize(new Dimension(600, 95));
        center.add(spCipher, g);

        g.gridx = 1; g.gridy = ++row;
        JPanel pnlCipherBtns = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 2));
        pnlCipherBtns.add(btn_encrypt);
        pnlCipherBtns.add(btn_decrypt);
        pnlCipherBtns.add(btn_tamper);
        center.add(pnlCipherBtns, g);

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
        // TODO (Bài 3.4 - Dán code nút Generate Keys vào đây)
    }

    private void btn_savekeysActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO (Bài 3.4 - Dán code nút Save Keys vào đây)
    }

    private void btn_loadkeysActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO (Bài 3.4 - Dán code nút Load Keys vào đây)
    }

    private void btn_encryptActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO (Bài 3.4 - Dán code nút Encrypt vào đây)
    }

    private void btn_decryptActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO (Bài 3.4 - Dán code nút Decrypt vào đây)
    }

    private void btn_tamperActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO (Bài 3.4 - Dán code nút Sửa 1 ký tự bản mã vào đây)
    }

    public static void main(String[] args) {
        SwingUiHelper.launch(frm_RSA_AES::new);
    }
}
