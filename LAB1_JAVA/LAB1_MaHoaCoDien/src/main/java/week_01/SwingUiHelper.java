package week_01;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.function.Supplier;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Tiện ích dựng giao diện Swing dùng chung cho các form của LAB 1
 * (giúp code từng form gọn nhẹ, dễ chạy trực tiếp trong VS Code không cần NetBeans GUI Builder).
 */
final class SwingUiHelper {

    static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 18);
    static final Font FONT_BODY = new Font("Segoe UI", Font.PLAIN, 13);
    static final Font FONT_MONO = new Font("Monospaced", Font.PLAIN, 13);

    private SwingUiHelper() {
    }

    static void launch(Supplier<JFrame> factory) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
                // Giữ Look and Feel mặc định nếu hệ thống không hỗ trợ.
            }
            factory.get().setVisible(true);
        });
    }

    static JLabel titleLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(FONT_TITLE);
        return lbl;
    }

    static JLabel bodyLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(FONT_BODY);
        return lbl;
    }

    static JTextArea textArea(int rows, boolean wrap) {
        JTextArea area = new JTextArea(rows, 44);
        area.setFont(FONT_BODY);
        area.setLineWrap(wrap);
        area.setWrapStyleWord(wrap);
        return area;
    }

    static JTextField textField(String initialText, int columns) {
        JTextField field = new JTextField(initialText, columns);
        field.setFont(FONT_BODY);
        return field;
    }

    static JButton button(String text, Dimension size) {
        JButton btn = new JButton(text);
        btn.setFont(FONT_BODY);
        if (size != null) {
            btn.setPreferredSize(size);
        }
        return btn;
    }

    static GridBagConstraints gbc(int x, int y, int width, double weightX, double weightY, int fill) {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = x;
        c.gridy = y;
        c.gridwidth = width;
        c.weightx = weightX;
        c.weighty = weightY;
        c.fill = fill;
        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(5, 5, 5, 5);
        return c;
    }

    /**
     * Dựng phần giao diện chuẩn cho 6 form thuật toán cơ bản (Bài 1.1 - 1.6),
     * hỗ trợ chèn thêm hàng tùy chọn (ví dụ ma trận Playfair) giữa Key và Ciphertext.
     */
    static void buildBasicCipherForm(
            JFrame frame,
            String windowTitle,
            String headerText,
            String defaultKey,
            String keyHint,
            JTextArea txtPlaintext,
            JTextField txtKey,
            JTextArea txtCiphertext,
            JButton btnEncrypt,
            JButton btnDecrypt,
            JButton btnOpen,
            JButton btnSave,
            JLabel lblStatus,
            JComponent extraMiddleLeft,
            JComponent extraMiddleRight) {

        frame.setTitle(windowTitle);
        frame.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        JPanel root = new JPanel(new GridBagLayout());
        root.setBorder(BorderFactory.createEmptyBorder(12, 14, 12, 14));

        int row = 0;
        root.add(titleLabel(headerText), gbc(0, row++, 3, 1.0, 0, GridBagConstraints.HORIZONTAL));

        root.add(bodyLabel("Plaintext:"), gbc(0, row, 1, 0, 0, GridBagConstraints.NONE));
        root.add(new JScrollPane(txtPlaintext), gbc(1, row++, 2, 1.0, 0.35, GridBagConstraints.BOTH));

        txtKey.setText(defaultKey);
        root.add(bodyLabel("Key:"), gbc(0, row, 1, 0, 0, GridBagConstraints.NONE));
        root.add(txtKey, gbc(1, row, 1, 0.3, 0, GridBagConstraints.HORIZONTAL));
        root.add(bodyLabel(keyHint), gbc(2, row++, 1, 0.7, 0, GridBagConstraints.HORIZONTAL));

        if (extraMiddleLeft != null && extraMiddleRight != null) {
            root.add(extraMiddleLeft, gbc(0, row, 1, 0, 0, GridBagConstraints.NORTHWEST));
            root.add(extraMiddleRight, gbc(1, row++, 2, 1.0, 0.3, GridBagConstraints.BOTH));
        }

        root.add(bodyLabel("Ciphertext:"), gbc(0, row, 1, 0, 0, GridBagConstraints.NONE));
        root.add(new JScrollPane(txtCiphertext), gbc(1, row++, 2, 1.0, 0.35, GridBagConstraints.BOTH));

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 2));
        buttons.add(btnEncrypt);
        buttons.add(btnDecrypt);
        buttons.add(btnOpen);
        buttons.add(btnSave);
        root.add(buttons, gbc(0, row++, 3, 1.0, 0, GridBagConstraints.HORIZONTAL));

        root.add(lblStatus, gbc(0, row, 3, 1.0, 0, GridBagConstraints.HORIZONTAL));

        frame.setLayout(new BorderLayout());
        frame.add(root, BorderLayout.CENTER);
        frame.pack();
        frame.setMinimumSize(new Dimension(640, frame.getHeight()));
        frame.setLocationRelativeTo(null);
    }
}

