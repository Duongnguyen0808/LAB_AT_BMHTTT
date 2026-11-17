package week_02;

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
import javax.swing.JTextField;

/**
 * Bài 2.6 - Nâng cao 01: AES-256 chế độ CTR (Counter).
 */
public class frm_AES_CTR extends JFrame {

    private final JTextArea txt_plaintext = SwingUiHelper.textArea(3, true);
    private final JTextField txt_key = SwingUiHelper.textField("", 42);
    private final JButton btn_genkey = SwingUiHelper.button("Generate Key", new Dimension(140, 30));
    private final JTextField txt_nonce = SwingUiHelper.textField("", 42);
    private final JButton btn_gennonce = SwingUiHelper.button("Generate Nonce", new Dimension(140, 30));
    private final JTextArea txt_ciphertext = SwingUiHelper.textArea(3, true);
    private final JButton btn_encrypt = SwingUiHelper.button("Encrypt", new Dimension(100, 30));
    private final JButton btn_decrypt = SwingUiHelper.button("Decrypt", new Dimension(100, 30));
    private final JButton btn_save = SwingUiHelper.button("Save File", new Dimension(120, 30));
    private final JButton btn_open = SwingUiHelper.button("Open File", new Dimension(120, 30));
    private final JLabel lbl_status = SwingUiHelper.bodyLabel("Sẵn sàng.");

    public frm_AES_CTR() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("AES-CTR Cipher");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel root = new JPanel(new BorderLayout(10, 12));
        root.setBorder(BorderFactory.createEmptyBorder(16, 18, 16, 18));
        root.add(SwingUiHelper.titleLabel("BÀI NÂNG CAO 01 - AES-256 CHẾ ĐỘ CTR"), BorderLayout.NORTH);

        JPanel center = new JPanel(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(4, 4, 4, 4);
        g.fill = GridBagConstraints.HORIZONTAL;
        g.weightx = 1.0;
        int row = 0;

        g.gridx = 0; g.gridy = row++;
        center.add(SwingUiHelper.bodyLabel("Plaintext:"), g);
        g.gridy = row++;
        center.add(new JScrollPane(txt_plaintext), g);

        g.gridy = row++;
        JPanel pnlKey = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        pnlKey.add(SwingUiHelper.bodyLabel("Key (Base64):"));
        pnlKey.add(txt_key);
        pnlKey.add(btn_genkey);
        center.add(pnlKey, g);

        g.gridy = row++;
        JPanel pnlNonce = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        pnlNonce.add(SwingUiHelper.bodyLabel("Nonce (Base64):"));
        pnlNonce.add(txt_nonce);
        pnlNonce.add(btn_gennonce);
        center.add(pnlNonce, g);

        g.gridy = row++;
        center.add(SwingUiHelper.bodyLabel("Ciphertext:"), g);
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
        setMinimumSize(new Dimension(700, 520));

        btn_genkey.addActionListener(e -> btn_genkeyActionPerformed());
        btn_gennonce.addActionListener(e -> btn_gennonceActionPerformed());
        btn_encrypt.addActionListener(e -> btn_encryptActionPerformed());
        btn_decrypt.addActionListener(e -> btn_decryptActionPerformed());
        btn_save.addActionListener(e -> btn_saveActionPerformed());
        btn_open.addActionListener(e -> btn_openActionPerformed());
    }

    private void btn_genkeyActionPerformed() {
        // TODO (Bài 2.6 - Dán code nút Generate Key vào đây)
    }

    private void btn_gennonceActionPerformed() {
        // TODO (Bài 2.6 - Dán code nút Generate Nonce vào đây)
    }

    private void btn_encryptActionPerformed() {
        // TODO (Bài 2.6 - Dán code nút Encrypt vào đây)
    }

    private void btn_decryptActionPerformed() {
        // TODO (Bài 2.6 - Dán code nút Decrypt vào đây)
    }

    private void btn_saveActionPerformed() {
        // TODO (Bài 2.6 - Dán code nút Save File vào đây)
    }

    private void btn_openActionPerformed() {
        // TODO (Bài 2.6 - Dán code nút Open File vào đây)
    }

    public static void main(String[] args) {
        SwingUiHelper.launch(frm_AES_CTR::new);
    }
}
