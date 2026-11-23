package week_03;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.util.function.Supplier;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Tiện ích dựng giao diện Swing dùng chung cho các JFrame Form của LAB 3.
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

    public static JTextArea textArea(int rows, boolean editable) {
        JTextArea area = new JTextArea(rows, 46);
        area.setFont(FONT_MONO);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setEditable(editable);
        area.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 190, 205)),
                BorderFactory.createEmptyBorder(5, 7, 5, 7)
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
}
