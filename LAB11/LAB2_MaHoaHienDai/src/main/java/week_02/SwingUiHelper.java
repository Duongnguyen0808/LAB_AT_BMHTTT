package week_02;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
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
 * Tiện ích dựng giao diện Swing dùng chung cho các form của LAB 2.
 */
public final class SwingUiHelper {

    public static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 18);
    public static final Font FONT_BODY = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font FONT_BOLD = new Font("Segoe UI", Font.BOLD, 13);
    public static final Font FONT_MONO = new Font("Consolas", Font.PLAIN, 13);

    private SwingUiHelper() {
    }

    public static void launch(Supplier<JFrame> factory) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
            }
            factory.get().setVisible(true);
        });
    }

    public static JLabel titleLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(FONT_TITLE);
        lbl.setForeground(new Color(24, 43, 73));
        return lbl;
    }

    public static JLabel bodyLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(FONT_BODY);
        return lbl;
    }

    public static JTextArea textArea(int rows, boolean wrap) {
        JTextArea area = new JTextArea(rows, 46);
        area.setFont(FONT_MONO);
        area.setLineWrap(wrap);
        area.setWrapStyleWord(wrap);
        area.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 190, 205)),
                BorderFactory.createEmptyBorder(6, 8, 6, 8)
        ));
        return area;
    }

    public static JTextField textField(String initialText, int columns) {
        JTextField tf = new JTextField(initialText, columns);
        tf.setFont(FONT_MONO);
        tf.setPreferredSize(new Dimension(tf.getPreferredSize().width, 30));
        return tf;
    }

    public static JButton button(String text, Dimension size) {
        JButton btn = new JButton(text);
        btn.setFont(FONT_BOLD);
        btn.setPreferredSize(size);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    public static void buildBasicCipherForm(
            JFrame frame,
            String windowTitle,
            String heading,
            String initialKey,
            String keyTooltip,
            JTextArea txtPlain,
            JTextField txtKey,
            JTextArea txtCipher,
            JButton btnEncrypt,
            JButton btnDecrypt,
            JButton btnOpen,
            JButton btnSave,
            JLabel lblStatus,
            JComponent extraTop,
            JComponent extraMiddle) {

        frame.setTitle(windowTitle);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel root = new JPanel(new BorderLayout(10, 12));
        root.setBorder(BorderFactory.createEmptyBorder(16, 18, 16, 18));

        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 2));
        header.add(titleLabel(heading));
        root.add(header, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(4, 4, 4, 4);
        g.fill = GridBagConstraints.HORIZONTAL;
        g.weightx = 1.0;
        int row = 0;

        if (extraTop != null) {
            g.gridx = 0; g.gridy = row++; g.gridwidth = 2;
            center.add(extraTop, g);
            g.gridwidth = 1;
        }

        g.gridx = 0; g.gridy = row++; g.weightx = 0;
        center.add(bodyLabel("Plaintext:"), g);
        g.gridy = row++; g.weightx = 1.0;
        center.add(new JScrollPane(txtPlain), g);

        if (txtKey != null) {
            g.gridy = row++;
            JPanel pnlKey = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
            pnlKey.add(bodyLabel("Key:"));
            pnlKey.add(txtKey);
            if (keyTooltip != null && !keyTooltip.isEmpty()) {
                pnlKey.add(bodyLabel(keyTooltip));
            }
            center.add(pnlKey, g);
        }

        if (extraMiddle != null) {
            g.gridy = row++;
            center.add(extraMiddle, g);
        }

        g.gridy = row++;
        center.add(bodyLabel("Ciphertext:"), g);
        g.gridy = row++;
        center.add(new JScrollPane(txtCipher), g);

        g.gridy = row++;
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 4));
        actions.add(btnEncrypt);
        actions.add(btnDecrypt);
        actions.add(btnSave);
        actions.add(btnOpen);
        center.add(actions, g);

        root.add(center, BorderLayout.CENTER);

        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 2));
        statusPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(220, 224, 230)));
        statusPanel.add(lblStatus);
        root.add(statusPanel, BorderLayout.SOUTH);

        frame.setContentPane(root);
        frame.pack();
        frame.setMinimumSize(new Dimension(640, 500));
        frame.setLocationRelativeTo(null);
    }
}
