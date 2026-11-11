package week_01;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

/** Phần 4: Form Phân tích (tấn công) mật mã cổ điển. */
public class frm_TanCong extends JFrame {

    private final JTextArea txt_cipher = SwingUiHelper.textArea(2, true);
    private final JButton btn_bruteforce = SwingUiHelper.button("Brute Force Caesar", new Dimension(170, 30));
    private final JButton btn_frequency = SwingUiHelper.button("Frequency Analysis", new Dimension(170, 30));
    private final JTextField txt_known_plain = SwingUiHelper.textField("HELLO", 12);
    private final JTextField txt_known_cipher = SwingUiHelper.textField("RIJVS", 12);
    private final JButton btn_known = SwingUiHelper.button("Known Plaintext (Vigenere)", new Dimension(220, 30));
    private final JTextArea txt_result = new JTextArea(14, 48);
    private final JLabel lbl_status = SwingUiHelper.bodyLabel("Sẵn sàng.");

    public frm_TanCong() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("Phân tích mật mã cổ điển");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        txt_result.setEditable(false);
        txt_result.setLineWrap(false);
        txt_result.setFont(SwingUiHelper.FONT_MONO);

        JPanel root = new JPanel(new GridBagLayout());
        root.setBorder(BorderFactory.createEmptyBorder(12, 14, 12, 14));

        int row = 0;
        root.add(SwingUiHelper.titleLabel("PHÂN TÍCH (TẤN CÔNG) MẬT MÃ CỔ ĐIỂN"),
                SwingUiHelper.gbc(0, row++, 2, 1.0, 0, GridBagConstraints.HORIZONTAL));

        root.add(SwingUiHelper.bodyLabel("Ciphertext:"),
                SwingUiHelper.gbc(0, row, 1, 0, 0, GridBagConstraints.NONE));
        root.add(new JScrollPane(txt_cipher),
                SwingUiHelper.gbc(1, row++, 1, 1.0, 0.2, GridBagConstraints.BOTH));

        JPanel attackButtons = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 2));
        attackButtons.add(btn_bruteforce);
        attackButtons.add(btn_frequency);
        root.add(attackButtons, SwingUiHelper.gbc(1, row++, 1, 1.0, 0, GridBagConstraints.HORIZONTAL));

        JPanel knownRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 2));
        knownRow.add(txt_known_plain);
        knownRow.add(SwingUiHelper.bodyLabel("Bản mã:"));
        knownRow.add(txt_known_cipher);
        knownRow.add(btn_known);
        root.add(SwingUiHelper.bodyLabel("Bản rõ đã biết:"),
                SwingUiHelper.gbc(0, row, 1, 0, 0, GridBagConstraints.NONE));
        root.add(knownRow, SwingUiHelper.gbc(1, row++, 1, 1.0, 0, GridBagConstraints.HORIZONTAL));

        root.add(SwingUiHelper.bodyLabel("Kết quả:"),
                SwingUiHelper.gbc(0, row, 1, 0, 0, GridBagConstraints.NORTHWEST));
        root.add(new JScrollPane(txt_result),
                SwingUiHelper.gbc(1, row++, 1, 1.0, 0.8, GridBagConstraints.BOTH));

        root.add(lbl_status, SwingUiHelper.gbc(0, row, 2, 1.0, 0, GridBagConstraints.HORIZONTAL));

        setLayout(new BorderLayout());
        add(root, BorderLayout.CENTER);
        pack();
        setMinimumSize(new Dimension(700, 500));

        btn_bruteforce.addActionListener(e -> btn_bruteforceActionPerformed());
        btn_frequency.addActionListener(e -> btn_frequencyActionPerformed());
        btn_known.addActionListener(e -> btn_knownActionPerformed());
    }

    private void btn_bruteforceActionPerformed() {
        // TODO (Phần 4 - Dán code nút Brute Force Caesar vào đây)
    }

    private void btn_frequencyActionPerformed() {
        // TODO (Phần 4 - Dán code nút Frequency Analysis vào đây)
    }

    private void btn_knownActionPerformed() {
        // TODO (Phần 4 - Dán code nút Known Plaintext vào đây)
    }

    public static void main(String[] args) {
        SwingUiHelper.launch(frm_TanCong::new);
    }
}
