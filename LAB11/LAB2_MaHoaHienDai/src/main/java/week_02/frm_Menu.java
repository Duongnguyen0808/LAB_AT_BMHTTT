package week_02;

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
 * Phần 3: Menu chính LAB 2 - Mã hóa đối xứng hiện đại.
 */
public class frm_Menu extends JFrame {

    private final JButton btn_des = SwingUiHelper.button("2.1 DES", new Dimension(200, 32));
    private final JButton btn_tripledes = SwingUiHelper.button("2.2 TripleDES", new Dimension(200, 32));
    private final JButton btn_aes = SwingUiHelper.button("2.3 AES + Login", new Dimension(200, 32));
    private final JButton btn_rc4 = SwingUiHelper.button("2.4 RC4", new Dimension(200, 32));
    private final JButton btn_twofish = SwingUiHelper.button("2.5 Twofish", new Dimension(200, 32));
    private final JButton btn_aesctr = SwingUiHelper.button("2.6 Nâng cao - AES-CTR", new Dimension(200, 32));
    private final JButton btn_sql = SwingUiHelper.button("Kết nối SQL Server", new Dimension(200, 32));
    private final JButton btn_history = SwingUiHelper.button("Xem lịch sử", new Dimension(200, 32));

    public frm_Menu() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("LAB 2 - Mã hóa đối xứng hiện đại");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel root = new JPanel(new BorderLayout(10, 14));
        root.setBorder(BorderFactory.createEmptyBorder(18, 20, 18, 20));

        JPanel header = new JPanel(new GridLayout(2, 1, 0, 6));
        JLabel title = new JLabel("LAB 2 - MÃ HÓA ĐỐI XỨNG HIỆN ĐẠI");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        header.add(title);
        header.add(SwingUiHelper.bodyLabel("Chọn một bài để mở form tương ứng:"));
        root.add(header, BorderLayout.NORTH);

        JPanel grid = new JPanel(new GridLayout(3, 3, 12, 10));
        grid.add(btn_des);
        grid.add(btn_tripledes);
        grid.add(btn_aes);
        grid.add(btn_rc4);
        grid.add(btn_twofish);
        grid.add(btn_aesctr);
        grid.add(btn_sql);
        grid.add(btn_history);
        grid.add(new JLabel());
        root.add(grid, BorderLayout.CENTER);

        root.add(SwingUiHelper.bodyLabel("Bài nâng cao 02 (AES-CBC) viết bằng Python: chạy tệp aes_cbc_gui.py."),
                BorderLayout.SOUTH);

        setContentPane(root);
        pack();
        setMinimumSize(new Dimension(660, 260));

        btn_des.addActionListener(e -> btn_desActionPerformed());
        btn_tripledes.addActionListener(e -> btn_tripledesActionPerformed());
        btn_aes.addActionListener(e -> btn_aesActionPerformed());
        btn_rc4.addActionListener(e -> btn_rc4ActionPerformed());
        btn_twofish.addActionListener(e -> btn_twofishActionPerformed());
        btn_aesctr.addActionListener(e -> btn_aesctrActionPerformed());
        btn_sql.addActionListener(e -> btn_sqlActionPerformed());
        btn_history.addActionListener(e -> btn_historyActionPerformed());
    }

    private void btn_desActionPerformed() {
        // TODO (Phần 3 - Dán: new frm_DES().setVisible(true);)
    }

    private void btn_tripledesActionPerformed() {
        // TODO (Phần 3 - Dán: new frm_TripleDES().setVisible(true);)
    }

    private void btn_aesActionPerformed() {
        // TODO (Phần 3 - Dán: new frm_AES().setVisible(true);)
    }

    private void btn_rc4ActionPerformed() {
        // TODO (Phần 3 - Dán: new frm_RC4().setVisible(true);)
    }

    private void btn_twofishActionPerformed() {
        // TODO (Phần 3 - Dán: new frm_Twofish().setVisible(true);)
    }

    private void btn_aesctrActionPerformed() {
        // TODO (Phần 3 - Dán: new frm_AES_CTR().setVisible(true);)
    }

    private void btn_sqlActionPerformed() {
        // TODO (Phần 3 - Dán: new frm_KetNoiSQL().setVisible(true);)
    }

    private void btn_historyActionPerformed() {
        // TODO (Phần 3 - Dán: new frm_LichSu().setVisible(true);)
    }

    public static void main(String[] args) {
        SwingUiHelper.launch(frm_Menu::new);
    }
}
