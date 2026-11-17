package week_02;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * Giao diện cấu hình kết nối Microsoft SQL Server.
 */
public class frm_KetNoiSQL extends JFrame {

    private final JTextField txt_server = SwingUiHelper.textField("127.0.0.1", 18);
    private final JTextField txt_port = SwingUiHelper.textField("14333", 8);
    private final JTextField txt_database = SwingUiHelper.textField("LAB2_MaHoaHienDai", 18);
    private final JTextField txt_user = SwingUiHelper.textField("lab2_user", 18);
    private final JPasswordField txt_password = new JPasswordField("LabUser@2026", 18);
    private final JButton btn_test = SwingUiHelper.button("Test Connection", new Dimension(140, 32));
    private final JButton btn_history = SwingUiHelper.button("Xem lịch sử", new Dimension(130, 32));
    private final JLabel lbl_status = SwingUiHelper.bodyLabel("Nhập thông tin rồi bấm Test Connection.");

    public frm_KetNoiSQL() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("Kết nối Microsoft SQL Server");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel root = new JPanel(new BorderLayout(10, 14));
        root.setBorder(BorderFactory.createEmptyBorder(16, 20, 16, 20));

        JPanel header = new JPanel(new GridLayout(2, 1, 0, 4));
        header.add(SwingUiHelper.titleLabel("KẾT NỐI MICROSOFT SQL SERVER"));
        header.add(SwingUiHelper.bodyLabel("Cấu hình chuỗi kết nối JDBC cho CSDL LAB2_MaHoaHienDai:"));
        root.add(header, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(5, 5, 5, 5);
        g.anchor = GridBagConstraints.WEST;

        g.gridx = 0; g.gridy = 0; form.add(SwingUiHelper.bodyLabel("Server:"), g);
        g.gridx = 1; form.add(txt_server, g);

        g.gridx = 0; g.gridy = 1; form.add(SwingUiHelper.bodyLabel("Cổng (Port):"), g);
        g.gridx = 1; form.add(txt_port, g);

        g.gridx = 0; g.gridy = 2; form.add(SwingUiHelper.bodyLabel("Database:"), g);
        g.gridx = 1; form.add(txt_database, g);

        g.gridx = 0; g.gridy = 3; form.add(SwingUiHelper.bodyLabel("User:"), g);
        g.gridx = 1; form.add(txt_user, g);

        g.gridx = 0; g.gridy = 4; form.add(SwingUiHelper.bodyLabel("Password:"), g);
        txt_password.setPreferredSize(new Dimension(200, 30));
        g.gridx = 1; form.add(txt_password, g);

        g.gridx = 0; g.gridy = 5; g.gridwidth = 2;
        JPanel btns = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        btns.add(btn_test);
        btns.add(btn_history);
        form.add(btns, g);

        root.add(form, BorderLayout.CENTER);

        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 2));
        statusPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, java.awt.Color.LIGHT_GRAY));
        statusPanel.add(lbl_status);
        root.add(statusPanel, BorderLayout.SOUTH);

        setContentPane(root);
        pack();
        setMinimumSize(new Dimension(480, 360));

        btn_test.addActionListener(e -> btn_testActionPerformed());
        btn_history.addActionListener(e -> btn_historyActionPerformed());
    }

    private void btn_testActionPerformed() {
        try {
            int port = Integer.parseInt(txt_port.getText().trim());
            DbConfig.set(txt_server.getText().trim(), port, txt_database.getText().trim(),
                    txt_user.getText().trim(), txt_password.getPassword());
            String desc = DbConfig.test();
            lbl_status.setText(desc);
            JOptionPane.showMessageDialog(this, desc, "Kết nối thành công", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Cổng (port) phải là số nguyên (ví dụ 14333 hoặc 1433).",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            lbl_status.setText("Lỗi: " + ex.getMessage());
            JOptionPane.showMessageDialog(this, "Kết nối thất bại:\n" + ex.getMessage(),
                    "Lỗi kết nối", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btn_historyActionPerformed() {
        new frm_LichSu().setVisible(true);
    }

    public static void main(String[] args) {
        SwingUiHelper.launch(frm_KetNoiSQL::new);
    }
}
