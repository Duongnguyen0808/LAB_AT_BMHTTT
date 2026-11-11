package week_01;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/** Phần 5: Cửa sổ Menu chính mở tất cả các bài của LAB 1. */
public class frm_Menu extends JFrame {

    private final JButton btn_caesar = SwingUiHelper.button("1.1 Caesar", new Dimension(200, 34));
    private final JButton btn_vigenere = SwingUiHelper.button("1.2 Vigenère", new Dimension(200, 34));
    private final JButton btn_railfence = SwingUiHelper.button("1.3 Rail Fence", new Dimension(200, 34));
    private final JButton btn_playfair = SwingUiHelper.button("1.4 Playfair", new Dimension(200, 34));
    private final JButton btn_transposition = SwingUiHelper.button("1.5 Transposition", new Dimension(200, 34));
    private final JButton btn_autokey = SwingUiHelper.button("1.6 Autokey", new Dimension(200, 34));
    private final JButton btn_tonghop = SwingUiHelper.button("Nâng cao 01 - Tổng hợp", new Dimension(200, 34));
    private final JButton btn_tancong = SwingUiHelper.button("Nâng cao 02 - Tấn công", new Dimension(200, 34));
    private final JButton btn_sql = SwingUiHelper.button("Kết nối SQL Server", new Dimension(200, 34));

    public frm_Menu() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("LAB 1 - Mã hóa đối xứng cổ điển");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel root = new JPanel(new BorderLayout(10, 12));
        root.setBorder(BorderFactory.createEmptyBorder(16, 18, 16, 18));

        JPanel header = new JPanel(new GridLayout(2, 1, 0, 6));
        JLabel title = new JLabel("LAB 1 - MÃ HÓA ĐỐI XỨNG CỔ ĐIỂN");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        header.add(title);
        header.add(SwingUiHelper.bodyLabel("Chọn một bài để mở form tương ứng:"));
        root.add(header, BorderLayout.NORTH);

        JPanel grid = new JPanel(new GridLayout(3, 3, 12, 10));
        grid.add(btn_caesar);
        grid.add(btn_vigenere);
        grid.add(btn_railfence);
        grid.add(btn_playfair);
        grid.add(btn_transposition);
        grid.add(btn_autokey);
        grid.add(btn_tonghop);
        grid.add(btn_tancong);
        grid.add(btn_sql);
        root.add(grid, BorderLayout.CENTER);

        root.add(SwingUiHelper.bodyLabel("Mỗi form mở ra là một cửa sổ riêng; đóng form con không làm tắt chương trình."),
                BorderLayout.SOUTH);

        setLayout(new BorderLayout());
        add(root, BorderLayout.CENTER);
        pack();
        setMinimumSize(new Dimension(680, 260));

        btn_caesar.addActionListener(e -> btn_caesarActionPerformed());
        btn_vigenere.addActionListener(e -> btn_vigenereActionPerformed());
        btn_railfence.addActionListener(e -> btn_railfenceActionPerformed());
        btn_playfair.addActionListener(e -> btn_playfairActionPerformed());
        btn_transposition.addActionListener(e -> btn_transpositionActionPerformed());
        btn_autokey.addActionListener(e -> btn_autokeyActionPerformed());
        btn_tonghop.addActionListener(e -> btn_tonghopActionPerformed());
        btn_tancong.addActionListener(e -> btn_tancongActionPerformed());
        btn_sql.addActionListener(e -> btn_sqlActionPerformed());
    }

    private void btn_caesarActionPerformed() {
        // TODO (Phần 5 - Dán: new frm_Caesar().setVisible(true);)
    }

    private void btn_vigenereActionPerformed() {
        // TODO (Phần 5 - Dán: new frm_Vigenere().setVisible(true);)
    }

    private void btn_railfenceActionPerformed() {
        // TODO (Phần 5 - Dán: new frm_RailFence().setVisible(true);)
    }

    private void btn_playfairActionPerformed() {
        // TODO (Phần 5 - Dán: new frm_Playfair().setVisible(true);)
    }

    private void btn_transpositionActionPerformed() {
        // TODO (Phần 5 - Dán: new frm_Transposition().setVisible(true);)
    }

    private void btn_autokeyActionPerformed() {
        // TODO (Phần 5 - Dán: new frm_Autokey().setVisible(true);)
    }

    private void btn_tonghopActionPerformed() {
        // TODO (Phần 5 - Dán: new frm_TongHop().setVisible(true);)
    }

    private void btn_tancongActionPerformed() {
        // TODO (Phần 5 - Dán: new frm_TanCong().setVisible(true);)
    }

    private void btn_sqlActionPerformed() {
        // TODO (Phần 5 - Dán: new frm_KetNoiSQL().setVisible(true);)
    }

    public static void main(String[] args) {
        SwingUiHelper.launch(frm_Menu::new);
    }
}
