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
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * Phần 1.6a: Giao diện cấu hình kết nối Microsoft SQL Server.
 */
public class frm_KetNoiSQL extends JFrame {

    private final JTextField txt_server = SwingUiHelper.textField("127.0.0.1", 22);
    private final JTextField txt_port = SwingUiHelper.textField("14333", 10);
    private final JTextField txt_database = SwingUiHelper.textField("LAB2_MaHoaHienDai", 22);
    private final JTextField txt_user = SwingUiHelper.textField("lab2_user", 22);
    private final JPasswordField txt_password = new JPasswordField(22);
    private final JButton btn_test = SwingUiHelper.button("Test Connection", new Dimension(150, 30));
    private final JButton btn_history = SwingUiHelper.button("Xem lịch sử", new Dimension(130, 30));
    private final JLabel lbl_status = SwingUiHelper.bodyLabel("Chưa kết nối.");

    public frm_KetNoiSQL() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("Kết nối Microsoft SQL Server");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        txt_password.setFont(SwingUiHelper.FONT_MONO);
        txt_password.setPreferredSize(new Dimension(txt_password.getPreferredSize().width, 30));

        JPanel root = new JPanel(new BorderLayout(10, 12));
        root.setBorder(BorderFactory.createEmptyBorder(16, 20, 16, 20));
        root.add(SwingUiHelper.titleLabel("KẾT NỐI MICROSOFT SQL SERVER"), BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(5, 5, 5, 5);
        g.anchor = GridBagConstraints.WEST;

        g.gridx = 0; g.gridy = 0; form.add(SwingUiHelper.bodyLabel("Server:"), g);
        g.gridx = 1; form.add(txt_server, g);

        g.gridx = 0; g.gridy = 1; form.add(SwingUiHelper.bodyLabel("Port:"), g);
        g.gridx = 1; form.add(txt_port, g);

        g.gridx = 0; g.gridy = 2; form.add(SwingUiHelper.bodyLabel("Database:"), g);
        g.gridx = 1; form.add(txt_database, g);

        g.gridx = 0; g.gridy = 3; form.add(SwingUiHelper.bodyLabel("User:"), g);
        g.gridx = 1; form.add(txt_user, g);

        g.gridx = 0; g.gridy = 4; form.add(SwingUiHelper.bodyLabel("Password:"), g);
        g.gridx = 1; form.add(txt_password, g);

        g.gridx = 0; g.gridy = 5; g.gridwidth = 2;
        JPanel btns = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        btns.add(btn_test);
        btns.add(btn_history);
        form.add(btns, g);

        root.add(form, BorderLayout.CENTER);

        lbl_status.setPreferredSize(new Dimension(450, 40));
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 2));
        statusPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, java.awt.Color.LIGHT_GRAY));
        statusPanel.add(lbl_status);
        root.add(statusPanel, BorderLayout.SOUTH);

        setContentPane(root);
        pack();
        setMinimumSize(new Dimension(490, 360));

        btn_test.addActionListener(e -> btn_testActionPerformed());
        btn_history.addActionListener(e -> btn_historyActionPerformed());
    }

    private void btn_testActionPerformed() {
        // TODO (Phần 1.6 - Dán code nút Test Connection vào đây)
    }

    private void btn_historyActionPerformed() {
        // TODO (Phần 1.6 - Dán code nút Xem lịch sử vào đây)
    }

    public static void main(String[] args) {
        SwingUiHelper.launch(frm_KetNoiSQL::new);
    }
}
