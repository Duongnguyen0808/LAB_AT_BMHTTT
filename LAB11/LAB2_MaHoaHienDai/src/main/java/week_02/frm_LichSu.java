package week_02;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 * Giao diện xem lịch sử thao tác từ SQL Server dbo.crypto_history.
 */
public class frm_LichSu extends JFrame {

    private final JTable tbl_history = new JTable();
    private final JButton btn_refresh = SwingUiHelper.button("Tải lại", new Dimension(120, 32));
    private final JLabel lbl_status = SwingUiHelper.bodyLabel("Sẵn sàng.");

    public frm_LichSu() {
        initComponents();
        setLocationRelativeTo(null);
        tbl_history.setModel(HistoryDao.emptyModel());
        loadData();
    }

    private void initComponents() {
        setTitle("Lịch sử thao tác (SQL Server)");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel root = new JPanel(new BorderLayout(10, 12));
        root.setBorder(BorderFactory.createEmptyBorder(16, 18, 16, 18));

        JPanel header = new JPanel(new GridLayout(2, 1, 0, 4));
        header.add(SwingUiHelper.titleLabel("LỊCH SỬ THAO TÁC - BẢNG DBO.CRYPTO_HISTORY"));
        header.add(SwingUiHelper.bodyLabel("Danh sách 50 giao dịch mã hóa / giải mã gần nhất:"));
        root.add(header, BorderLayout.NORTH);

        root.add(new JScrollPane(tbl_history), BorderLayout.CENTER);

        JPanel bottom = new JPanel(new BorderLayout(8, 4));
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        btnPanel.add(btn_refresh);
        bottom.add(btnPanel, BorderLayout.WEST);
        bottom.add(lbl_status, BorderLayout.SOUTH);
        root.add(bottom, BorderLayout.SOUTH);

        setContentPane(root);
        pack();
        setSize(800, 420);

        btn_refresh.addActionListener(e -> btn_refreshActionPerformed());
    }

    private void loadData() {
        try {
            tbl_history.setModel(HistoryDao.loadLatest(50));
            lbl_status.setText("Đã tải " + tbl_history.getRowCount() + " dòng từ SQL Server.");
        } catch (Exception ex) {
            lbl_status.setText("Lỗi đọc dữ liệu: " + ex.getMessage());
            JOptionPane.showMessageDialog(this, "Không đọc được lịch sử:\n" + ex.getMessage(),
                    "Lỗi SQL", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btn_refreshActionPerformed() {
        loadData();
    }

    public static void main(String[] args) {
        SwingUiHelper.launch(frm_LichSu::new);
    }
}
