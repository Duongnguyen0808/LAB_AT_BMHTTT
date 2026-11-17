package week_02;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 * Phần 1.6b: Giao diện xem lịch sử thao tác từ SQL Server dbo.crypto_history.
 */
public class frm_LichSu extends JFrame {

    private final JTable tbl_history = new JTable();
    private final JButton btn_refresh = SwingUiHelper.button("Tải lại", new Dimension(110, 30));
    private final JLabel lbl_status = SwingUiHelper.bodyLabel("Đang tải...");

    public frm_LichSu() {
        initComponents();
        setLocationRelativeTo(null);
        // TODO (Phần 1.6 - Dán 2 dòng khởi tạo bảng & tải dữ liệu vào đây)
    }

    // TODO (Phần 1.6 - Dán hàm private void loadData() vào đây)

    private void initComponents() {
        setTitle("Lịch sử thao tác (SQL Server)");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        tbl_history.setFont(SwingUiHelper.FONT_BODY);
        tbl_history.setRowHeight(24);

        JPanel root = new JPanel(new BorderLayout(10, 12));
        root.setBorder(BorderFactory.createEmptyBorder(16, 18, 16, 18));
        root.add(SwingUiHelper.titleLabel("LỊCH SỬ THAO TÁC - BẢNG DBO.CRYPTO_HISTORY"), BorderLayout.NORTH);

        JScrollPane scroll = new JScrollPane(tbl_history);
        scroll.setPreferredSize(new Dimension(760, 260));
        root.add(scroll, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 2));
        bottom.add(btn_refresh);
        bottom.add(lbl_status);
        root.add(bottom, BorderLayout.SOUTH);

        setContentPane(root);
        pack();

        btn_refresh.addActionListener(e -> btn_refreshActionPerformed());
    }

    private void btn_refreshActionPerformed() {
        // TODO (Phần 1.6 - Dán code nút Tải lại vào đây)
    }

    public static void main(String[] args) {
        SwingUiHelper.launch(frm_LichSu::new);
    }
}
