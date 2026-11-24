package week_03;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Phần 3: Menu chính LAB 3 - Mã hóa khóa công khai.
 */
public class frm_Menu extends JFrame {

    private final JButton btn_rsa = SwingUiHelper.button("3.2 RSA (Swing)", new Dimension(200, 32));
    private final JButton btn_ecc = SwingUiHelper.button("3.3 ECC", new Dimension(200, 32));
    private final JButton btn_rsaaes = SwingUiHelper.button("3.4 RSA + AES", new Dimension(200, 32));
    private final JButton btn_signature = SwingUiHelper.button("3.5 Chữ ký ECDSA", new Dimension(200, 32));
    private final JButton btn_sql = SwingUiHelper.button("Kết nối SQL Server", new Dimension(200, 32));
    private final JButton btn_history = SwingUiHelper.button("Xem lịch sử", new Dimension(200, 32));

    public frm_Menu() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("LAB 3 - Mã hóa khóa công khai");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel root = new JPanel(new BorderLayout(10, 14));
        root.setBorder(BorderFactory.createEmptyBorder(18, 20, 18, 20));

        JPanel header = new JPanel(new GridLayout(2, 1, 0, 6));
        JLabel title = new JLabel("LAB 3 - MÃ HÓA KHÓA CÔNG KHAI");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        header.add(title);
        header.add(SwingUiHelper.bodyLabel("Chọn một bài để mở form tương ứng (bài 3.1 chạy RSADemo.java bằng Run File):"));
        root.add(header, BorderLayout.NORTH);

        JPanel grid = new JPanel(new GridLayout(2, 3, 12, 10));
        grid.add(btn_rsa);
        grid.add(btn_ecc);
        grid.add(btn_rsaaes);
        grid.add(btn_signature);
        grid.add(btn_sql);
        grid.add(btn_history);
        root.add(grid, BorderLayout.CENTER);

        setContentPane(root);
        pack();
        setMinimumSize(new Dimension(660, 220));

        btn_rsa.addActionListener(e -> btn_rsaActionPerformed(e));
        btn_ecc.addActionListener(e -> btn_eccActionPerformed(e));
        btn_rsaaes.addActionListener(e -> btn_rsaaesActionPerformed(e));
        btn_signature.addActionListener(e -> btn_signatureActionPerformed(e));
        btn_sql.addActionListener(e -> btn_sqlActionPerformed(e));
        btn_history.addActionListener(e -> btn_historyActionPerformed(e));
    }

    private void btn_rsaActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO (Phần 3 - Dán: new frm_RSA().setVisible(true);)
    }

    private void btn_eccActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO (Phần 3 - Dán: new frm_ECC().setVisible(true);)
    }

    private void btn_rsaaesActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO (Phần 3 - Dán: new frm_RSA_AES().setVisible(true);)
    }

    private void btn_signatureActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO (Phần 3 - Dán: new frm_ECC_Signature().setVisible(true);)
    }

    private void btn_sqlActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO (Phần 3 - Dán: new frm_KetNoiSQL().setVisible(true);)
    }

    private void btn_historyActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO (Phần 3 - Dán: new frm_LichSu().setVisible(true);)
    }

    public static void main(String[] args) {
        SwingUiHelper.launch(frm_Menu::new);
    }
}
