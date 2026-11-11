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
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

/** Phần 3.5: Form cấu hình và kiểm tra kết nối Microsoft SQL Server. */
public class frm_KetNoiSQL extends JFrame {

    private final JTextField txt_server = SwingUiHelper.textField("127.0.0.1", 24);
    private final JTextField txt_port = SwingUiHelper.textField("14333", 10);
    private final JTextField txt_database = SwingUiHelper.textField("LAB1_MaHoaCoDien", 24);
    private final JTextField txt_user = SwingUiHelper.textField("lab1_user", 24);
    private final JPasswordField txt_password = new JPasswordField(24);
    private final JButton btn_test = SwingUiHelper.button("Test Connection", new Dimension(150, 30));
    private final JButton btn_history = SwingUiHelper.button("Xem lịch sử", new Dimension(130, 30));
    private final JLabel lbl_status = SwingUiHelper.bodyLabel("Chưa kết nối.");

    public frm_KetNoiSQL() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("Kết nối Microsoft SQL Server");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        txt_password.setFont(SwingUiHelper.FONT_BODY);

        JPanel root = new JPanel(new GridBagLayout());
        root.setBorder(BorderFactory.createEmptyBorder(12, 16, 12, 16));

        int row = 0;
        root.add(SwingUiHelper.titleLabel("KẾT NỐI MICROSOFT SQL SERVER"),
                SwingUiHelper.gbc(0, row++, 2, 1.0, 0, GridBagConstraints.HORIZONTAL));

        root.add(SwingUiHelper.bodyLabel("Server:"),
                SwingUiHelper.gbc(0, row, 1, 0, 0, GridBagConstraints.NONE));
        root.add(txt_server, SwingUiHelper.gbc(1, row++, 1, 1.0, 0, GridBagConstraints.HORIZONTAL));

        root.add(SwingUiHelper.bodyLabel("Port:"),
                SwingUiHelper.gbc(0, row, 1, 0, 0, GridBagConstraints.NONE));
        root.add(txt_port, SwingUiHelper.gbc(1, row++, 1, 1.0, 0, GridBagConstraints.HORIZONTAL));

        root.add(SwingUiHelper.bodyLabel("Database:"),
                SwingUiHelper.gbc(0, row, 1, 0, 0, GridBagConstraints.NONE));
        root.add(txt_database, SwingUiHelper.gbc(1, row++, 1, 1.0, 0, GridBagConstraints.HORIZONTAL));

        root.add(SwingUiHelper.bodyLabel("User:"),
                SwingUiHelper.gbc(0, row, 1, 0, 0, GridBagConstraints.NONE));
        root.add(txt_user, SwingUiHelper.gbc(1, row++, 1, 1.0, 0, GridBagConstraints.HORIZONTAL));

        root.add(SwingUiHelper.bodyLabel("Password:"),
                SwingUiHelper.gbc(0, row, 1, 0, 0, GridBagConstraints.NONE));
        root.add(txt_password, SwingUiHelper.gbc(1, row++, 1, 1.0, 0, GridBagConstraints.HORIZONTAL));

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 2));
        buttons.add(btn_test);
        buttons.add(btn_history);
        root.add(buttons, SwingUiHelper.gbc(0, row++, 2, 1.0, 0, GridBagConstraints.HORIZONTAL));

        lbl_status.setPreferredSize(new Dimension(450, 44));
        root.add(lbl_status, SwingUiHelper.gbc(0, row, 2, 1.0, 0, GridBagConstraints.HORIZONTAL));

        setLayout(new BorderLayout());
        add(root, BorderLayout.CENTER);
        pack();
        setMinimumSize(new Dimension(500, getHeight()));

        btn_test.addActionListener(e -> btn_testActionPerformed());
        btn_history.addActionListener(e -> btn_historyActionPerformed());
    }

    private void btn_testActionPerformed() {
        // TODO (Phần 3.5 - Dán code nút Test Connection vào đây)
    }

    private void btn_historyActionPerformed() {
        // TODO (Phần 3.5 - Dán code nút Xem lịch sử vào đây)
    }

    public static void main(String[] args) {
        SwingUiHelper.launch(frm_KetNoiSQL::new);
    }
}
