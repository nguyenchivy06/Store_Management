package JavaGui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

public class QuanLiCuaHangForm extends javax.swing.JFrame {

    // Logger to handle exception logging
    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(QuanLiCuaHangForm.class.getName());

    private final ArrayList<SanPham> dsSanPham = new ArrayList<>();
    private final DefaultTableModel tableModel = new DefaultTableModel();

    private JTable table;
    private JTextField tfMa, tfTen, tfLoai, tfGia, tfSoLuong, tfNgayNhap, tfTimKiem;
    private JButton btnThem, btnXoa, btnTim, btnLamMoi, btnSua, btnXuat;

    public QuanLiCuaHangForm() {
        initComponents();
        setLayout(new java.awt.BorderLayout());
        customUI();
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
// </editor-fold>                        

    private void customUI() {
        setTitle("QUAN LY CUA HANG");
        setSize(950, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(245, 245, 245));

        // === Tiêu đề ===
        JLabel title = new JLabel("QUAN LY CUA HANG", JLabel.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 28));
        title.setForeground(Color.RED);
        title.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        add(title, BorderLayout.NORTH);

        // === Bảng sản phẩm ===
        String[] cols = {"Ma san pham", "Ten san pham", "Loai", "Gia (VND)", "So luong ", "Ngay nhap"};
        tableModel.setColumnIdentifiers(cols);
        table = new JTable(tableModel);
        table.setRowHeight(25);
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.setGridColor(Color.LIGHT_GRAY);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // === Panel nhập liệu ===
        JPanel inputPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Thong tin san pham"));
        inputPanel.setBackground(new Color(220, 240, 255));

        tfMa = new JTextField();
        tfTen = new JTextField();
        tfLoai = new JTextField();
        tfGia = new JTextField();
        tfSoLuong = new JTextField();
        tfNgayNhap = new JTextField();

        inputPanel.add(new JLabel("Ma san pham:"));
        inputPanel.add(tfMa);
        inputPanel.add(new JLabel("Ten san pham:"));
        inputPanel.add(tfTen);
        inputPanel.add(new JLabel("Loai:"));
        inputPanel.add(tfLoai);
        inputPanel.add(new JLabel("Gia (VND):"));
        inputPanel.add(tfGia);
        inputPanel.add(new JLabel("So luong :"));
        inputPanel.add(tfSoLuong);
        inputPanel.add(new JLabel("Ngay nhap:"));
        inputPanel.add(tfNgayNhap);

        add(inputPanel, BorderLayout.WEST);

        // === Panel chức năng ===
        JPanel controlPanel = new JPanel();
        controlPanel.setBackground(new Color(230, 230, 230));
        tfTimKiem = new JTextField(10);

        btnThem = new JButton("Them san pham");
        btnSua = new JButton("Sua san pham");
        btnLamMoi = new JButton("Lam moi");
        btnTim = new JButton("Tim kiem");
        btnXoa = new JButton("Xoa san pham");
        btnXuat = new JButton("Xuat file");

        // Trang trí nút
        styleButton(btnThem, new Color(102, 205, 170));
        styleButton(btnSua, new Color(70, 130, 180));
        styleButton(btnXoa, new Color(220, 20, 60));
        styleButton(btnLamMoi, new Color(255, 165, 0));
        styleButton(btnTim, new Color(123, 104, 238));
        styleButton(btnXuat, new Color(34, 139, 34));

        controlPanel.add(btnThem);
        controlPanel.add(btnSua);
        controlPanel.add(btnLamMoi);
        controlPanel.add(new JLabel("Tu khoa:"));
        controlPanel.add(tfTimKiem);
        controlPanel.add(btnTim);
        controlPanel.add(btnXoa);
        controlPanel.add(btnXuat);

        add(controlPanel, BorderLayout.SOUTH);

        // === Sự kiện ===
        btnThem.addActionListener(e -> themSanPham());
        btnSua.addActionListener(e -> suaSanPham());
        btnLamMoi.addActionListener(e -> capNhatBang());
        btnTim.addActionListener(e -> timSanPham());
        btnXoa.addActionListener(e -> xoaSanPham());
        btnXuat.addActionListener(e -> xuatFile());
    }

    // Hàm style nút
    private void styleButton(JButton btn, Color bg) {
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("SansSerif", Font.BOLD, 12));
    }

    // ====== Xử lý ======
    private void themSanPham() {
        String ma = tfMa.getText().trim();
        String ten = tfTen.getText().trim();
        String loai = tfLoai.getText().trim();
        String giaS = tfGia.getText().trim();
        String slS = tfSoLuong.getText().trim();
        String ngayNhap = tfNgayNhap.getText().trim();

        if (ma.isEmpty() || ten.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ma san pham va Ten san pham khong duoc bo trong.");
            return;
        }

        double gia;
        int sl;
        try {
            gia = Double.parseDouble(giaS);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gia phai la so.");
            return;
        }

        try {
            sl = Integer.parseInt(slS);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "So luong phai la so nguyen.");
            return;
        }

        dsSanPham.add(new SanPham(ma, ten, loai, gia, sl, ngayNhap));
        capNhatBang(); // Cập nhật lại bảng để hiển thị sản phẩm vừa thêm
    }

    private void suaSanPham() {
        int sel = table.getSelectedRow();
        if (sel == -1) {
            JOptionPane.showMessageDialog(this, "Hay chon 1 dong de sua.");
            return;
        }

        String ma = tfMa.getText().trim();
        String ten = tfTen.getText().trim();
        String loai = tfLoai.getText().trim();
        String giaS = tfGia.getText().trim();
        String slS = tfSoLuong.getText().trim();
        String ngayNhap = tfNgayNhap.getText().trim();

        double gia;
        int sl;
        try {
            gia = Double.parseDouble(giaS);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gia phai la so.");
            return;
        }
        try {
            sl = Integer.parseInt(slS);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "So luong phai la so nguyen.");
            return;
        }

        SanPham sp = dsSanPham.get(sel);
        sp.ma = ma;
        sp.ten = ten;
        sp.loai = loai;
        sp.gia = gia;
        sp.soLuong = sl;
        sp.ngayNhap = ngayNhap;

        capNhatBang();
    }

    private void capNhatBang() {
        tableModel.setRowCount(0);
        for (SanPham sp : dsSanPham) {
            Vector<Object> row = new Vector<>();
            row.add(sp.ma);
            row.add(sp.ten);
            row.add(sp.loai);
            row.add(sp.gia + " VND");
            row.add(sp.soLuong + " gram");
            row.add(sp.ngayNhap);
            tableModel.addRow(row);
        }
    }

    private void timSanPham() {
        String q = tfTimKiem.getText().trim().toLowerCase();
        tableModel.setRowCount(0);
        for (SanPham sp : dsSanPham) {
            if (sp.ma.toLowerCase().contains(q) || sp.ten.toLowerCase().contains(q)) {
                Vector<Object> row = new Vector<>();
                row.add(sp.ma);
                row.add(sp.ten);
                row.add(sp.loai);
                row.add(sp.gia + " VND");
                row.add(sp.soLuong + " gram");
                row.add(sp.ngayNhap);
                tableModel.addRow(row);
            }
        }
    }

    private void xoaSanPham() {
        int sel = table.getSelectedRow();
        if (sel == -1) {
            JOptionPane.showMessageDialog(this, "Hay chon 1 dong de xoa.");
            return;
        }
        String ma = (String) tableModel.getValueAt(sel, 0);
        dsSanPham.removeIf(sp -> sp.ma.equals(ma));
        capNhatBang();
    }

    private void xuatFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chon noi luu file");
        fileChooser.setSelectedFile(new java.io.File("sanpham.csv")); // tên mặc định

        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            java.io.File fileToSave = fileChooser.getSelectedFile();

            try (FileWriter writer = new FileWriter(fileToSave)) {
                writer.write("Ma san pham,Ten san pham,Loai,Gia (VND),So luong ,Ngay nhap\n");
                for (SanPham sp : dsSanPham) {
                    writer.write(sp.ma + "," + sp.ten + "," + sp.loai + ","
                            + sp.gia + " VND," + sp.soLuong + " ," + sp.ngayNhap + "\n");
                }
                JOptionPane.showMessageDialog(this, "Xuat file thanh cong tai: " + fileToSave.getAbsolutePath());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Loi khi xuat file: " + e.getMessage());
            }
        }
    }

    static class SanPham {

        String ma, ten, loai, ngayNhap;
        double gia;
        int soLuong;

        SanPham(String ma, String ten, String loai, double gia, int sl, String ngayNhap) {
            this.ma = ma;
            this.ten = ten;
            this.loai = loai;
            this.gia = gia;
            this.soLuong = sl;
            this.ngayNhap = ngayNhap;
        }
    }

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, (UnsupportedLookAndFeelException) ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new QuanLiCuaHangForm().setVisible(true));
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
