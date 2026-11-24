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

    // TODO (Bài 3.5 - Dán: private final ECCSignature ecdsa = new ECCSignature();)

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

    // TODO (Bài 3.5 - Dán hàm showKeys() vào đây)

    private void initComponents() {
        setTitle("ECDSA Signature");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        lbl_result.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lbl_result.setPreferredSize(new Dimension(240, 36));

        JPanel root = new JPanel(new BorderLayout(10, 12));
        root.setBorder(BorderFactory.createEmptyBorder(16, 18, 16, 18));
        root.add(SwingUiHelper.titleLabel("CHỮ KÝ SỐ ECDSA (SHA256withECDSA - secp256r1)"), BorderLayout.NORTH);

        JPanel center = new JPanel(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(4, 5, 4, 5);
        g.anchor = GridBagConstraints.NORTHWEST;
        g.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;
        g.gridx = 0; g.gridy = row; g.weightx = 0;
        center.add(SwingUiHelper.bodyLabel("Public Key:"), g);
        g.gridx = 1; g.weightx = 1.0;
        JScrollPane spPub = new JScrollPane(txt_public);
        spPub.setPreferredSize(new Dimension(600, 50));
        center.add(spPub, g);

        g.gridx = 0; g.gridy = ++row; g.weightx = 0;
        center.add(SwingUiHelper.bodyLabel("Private Key:"), g);
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
        center.add(SwingUiHelper.bodyLabel("Message:"), g);
        g.gridx = 1; g.weightx = 1.0;
        JScrollPane spMsg = new JScrollPane(txt_message);
        spMsg.setPreferredSize(new Dimension(600, 55));
        center.add(spMsg, g);

        g.gridx = 0; g.gridy = ++row; g.weightx = 0;
        center.add(SwingUiHelper.bodyLabel("Signature (Base64):"), g);
        g.gridx = 1; g.weightx = 1.0;
        JScrollPane spSig = new JScrollPane(txt_signature);
        spSig.setPreferredSize(new Dimension(600, 55));
        center.add(spSig, g);

        g.gridx = 1; g.gridy = ++row;
        JPanel pnlActions = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 2));
        pnlActions.add(btn_sign);
        pnlActions.add(btn_verify);
        pnlActions.add(lbl_result);
        center.add(pnlActions, g);

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
        // TODO (Bài 3.5 - Dán code nút Generate Keys vào đây)
    }

    private void btn_savekeysActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO (Bài 3.5 - Dán code nút Save Keys vào đây)
    }

    private void btn_loadkeysActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO (Bài 3.5 - Dán code nút Load Keys vào đây)
    }

    private void btn_signActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO (Bài 3.5 - Dán code nút Sign vào đây)
    }

    private void btn_verifyActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO (Bài 3.5 - Dán code nút Verify vào đây)
    }

    public static void main(String[] args) {
        SwingUiHelper.launch(frm_ECC_Signature::new);
    }
}
