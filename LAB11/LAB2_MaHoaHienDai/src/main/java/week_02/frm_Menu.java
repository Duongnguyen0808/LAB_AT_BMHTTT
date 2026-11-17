package week_02;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Menu chính LAB 2 - Mã hóa đối xứng hiện đại.
 */
public class frm_Menu extends JFrame {

    private final JButton btn_des = SwingUiHelper.button("2.1 DES", new Dimension(220, 36));
    private final JButton btn_tripledes = SwingUiHelper.button("2.2 TripleDES", new Dimension(220, 36));
    private final JButton btn_aes = SwingUiHelper.button("2.3 AES + Login", new Dimension(220, 36));
    private final JButton btn_rc4 = SwingUiHelper.button("2.4 RC4", new Dimension(220, 36));
    private final JButton btn_twofish = SwingUiHelper.button("2.5 Twofish", new Dimension(220, 36));
    private final JButton btn_aesctr = SwingUiHelper.button("2.6 Nâng cao - AES-CTR", new Dimension(220, 36));
    private final JButton btn_sql = SwingUiHelper.button("Kết nối SQL Server", new Dimension(220, 36));
    private final JButton btn_history = SwingUiHelper.button("Xem lịch sử", new Dimension(220, 36));

    public frm_Menu() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("LAB 2 - Mã hóa đối xứng hiện đại");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel root = new JPanel(new BorderLayout(12, 14));
        root.setBorder(BorderFactory.createEmptyBorder(18, 20, 18, 20));

        JPanel header = new JPanel(new GridLayout(2, 1, 0, 4));
        header.add(SwingUiHelper.titleLabel("LAB 2 - MÃ HÓA ĐỐI XỨNG HIỆN ĐẠI"));
        header.add(SwingUiHelper.bodyLabel("Chọn một bài để mở form tương ứng:"));
        root.add(header, BorderLayout.NORTH);

        JPanel grid = new JPanel(new GridLayout(4, 2, 12, 10));
        grid.add(btn_des);
        grid.add(btn_tripledes);
        grid.add(btn_aes);
        grid.add(btn_rc4);
        grid.add(btn_twofish);
        grid.add(btn_aesctr);
        grid.add(btn_sql);
        grid.add(btn_history);
        root.add(grid, BorderLayout.CENTER);

        setContentPane(root);
        pack();
        setSize(520, 360);

        btn_des.addActionListener(e -> { new frm_DES().setVisible(true); });
        btn_tripledes.addActionListener(e -> { new frm_TripleDES().setVisible(true); });
        btn_aes.addActionListener(e -> { new frm_AES().setVisible(true); });
        btn_rc4.addActionListener(e -> { new frm_RC4().setVisible(true); });
        btn_twofish.addActionListener(e -> { new frm_Twofish().setVisible(true); });
        btn_aesctr.addActionListener(e -> { new frm_AES_CTR().setVisible(true); });
        btn_sql.addActionListener(e -> { new frm_KetNoiSQL().setVisible(true); });
        btn_history.addActionListener(e -> { new frm_LichSu().setVisible(true); });
    }

    public static void main(String[] args) {
        SwingUiHelper.launch(frm_Menu::new);
    }
}
