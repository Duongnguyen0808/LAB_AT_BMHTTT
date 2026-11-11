package week_01;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/** Bài 1.4: Giao diện Mật mã Playfair (kèm hiển thị ma trận khóa 5x5). */
public class frm_Playfair extends JFrame {

    private final JTextArea txt_plaintext = SwingUiHelper.textArea(3, true);
    private final JTextField txt_key = SwingUiHelper.textField("KEYWORD", 12);
    private final JTextArea txt_matrix = new JTextArea(5, 11);
    private final JButton btn_matrix = SwingUiHelper.button("Generate Key Matrix", new Dimension(170, 30));
    private final JTextArea txt_ciphertext = SwingUiHelper.textArea(3, true);
    private final JButton btn_encrypt = SwingUiHelper.button("Encrypt", new Dimension(100, 30));
    private final JButton btn_decrypt = SwingUiHelper.button("Decrypt", new Dimension(100, 30));
    private final JButton btn_open = SwingUiHelper.button("Open Ciphertext File", new Dimension(170, 30));
    private final JButton btn_save = SwingUiHelper.button("Save Ciphertext File", new Dimension(170, 30));
    private final JLabel lbl_status = SwingUiHelper.bodyLabel("Sẵn sàng.");

    public frm_Playfair() {
        txt_matrix.setEditable(false);
        txt_matrix.setLineWrap(false);
        txt_matrix.setFont(new Font("Monospaced", Font.BOLD, 14));

        JPanel matrixRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        matrixRow.add(new JScrollPane(txt_matrix));
        matrixRow.add(btn_matrix);

        JPanel matrixWrapper = new JPanel(new BorderLayout());
        matrixWrapper.add(matrixRow, BorderLayout.WEST);

        SwingUiHelper.buildBasicCipherForm(
                this,
                "Playfair Cipher",
                "MẬT MÃ PLAYFAIR",
                "KEYWORD",
                "Chỉ gồm chữ A-Z, ví dụ KEYWORD",
                txt_plaintext, txt_key, txt_ciphertext,
                btn_encrypt, btn_decrypt, btn_open, btn_save, lbl_status,
                SwingUiHelper.bodyLabel("Key Matrix:"), matrixWrapper);

        btn_matrix.addActionListener(e -> btn_matrixActionPerformed());
        btn_encrypt.addActionListener(e -> btn_encryptActionPerformed());
        btn_decrypt.addActionListener(e -> btn_decryptActionPerformed());
        btn_open.addActionListener(e -> btn_openActionPerformed());
        btn_save.addActionListener(e -> btn_saveActionPerformed());
    }

    private void btn_matrixActionPerformed() {
        // TODO (Bài 1.4 - Dán code nút Generate Key Matrix vào đây)
    }

    private void btn_encryptActionPerformed() {
        // TODO (Bài 1.4 - Dán code nút Encrypt vào đây)
    }

    private void btn_decryptActionPerformed() {
        // TODO (Bài 1.4 - Dán code nút Decrypt vào đây)
    }

    private void btn_openActionPerformed() {
        // TODO (Bài 1.4 - Dán code nút Open Ciphertext File vào đây)
    }

    private void btn_saveActionPerformed() {
        // TODO (Bài 1.4 - Dán code nút Save Ciphertext File vào đây)
    }

    public static void main(String[] args) {
        SwingUiHelper.launch(frm_Playfair::new);
    }
}
