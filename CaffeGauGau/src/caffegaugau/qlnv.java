package caffegaugau;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author dhhoa
 */
public class qlnv extends javax.swing.JFrame {

    private boolean add = false, change = false;
    int click = 0;
    private DefaultTableModel model;
    String sql;
    Statement st;
    Connection cnn;
    String user = "sa";
    String pass = "songlong"; // thay doi pass cho phu hop
    String url = "jdbc:sqlserver://localhost;databaseName=QuanCaPhe";

    /**
     *
     */
    public qlnv() {
        initComponents();
        loadData();
        Click(click);
        Disabled();

    }

    private void loadData() {
        try {
            String[] arry = {"Mã Nhân Viên", "Họ Tên", "Ngày sinh", "Gioi tính", "SĐT", "Địa Chỉ", "Tài khoan", "Mat khau"};
            model = new DefaultTableModel(arry, 0);

            cnn = DriverManager.getConnection(url, user, pass);
            Statement st = cnn.createStatement();
            sql = "Select * from QLNV";
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                Vector vector = new Vector();
                vector.add(rs.getString("maNV").trim());
                vector.add(rs.getString("tenNV").trim());
                vector.add(rs.getString("ngaySinh").trim());
                vector.add(rs.getString("gioiTinh").trim());
                vector.add(rs.getString("sdt").trim());
                vector.add(rs.getString("diaChi").trim());
                vector.add(rs.getString("taiKhoan").trim());
                vector.add(rs.getString("matKhau").trim());
                model.addRow(vector);
            }
            tableNV.setModel(model);
            st.close();
            cnn.close();
            rs.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void checkGT(String GT) {
        if (GT.equals("Nam")) {
            rbNam1.setSelected(true);
        } else {
            rbNu1.setSelected(true);
        }
    }

    private void reset() {
        add = false;
        change = false;
        tfMaNV1.setText("");
        tfHoten1.setText("");
        ((JTextField) tfNgaySinh.getDateEditor().getUiComponent()).setText("");
        tfSDT1.setText("");
        tfTaikhoan1.setText("");
        pass1.setText("");
        tfDiachi1.setText("");
        lbTrangthai.setText("Trạng Thái");
        btnEdit1.setEnabled(true);
        btnDel1.setEnabled(true);
        btnAdd1.setEnabled(true);
        rbNu1.setSelected(false);
        rbNam1.setSelected(false);
        Enabled();
        buttonGroup1.clearSelection();
        buttonGroup2.clearSelection();
    }

    private void Enabled() {
        tfMaNV1.setEnabled(true);
        tfHoten1.setEnabled(true);
        tfNgaySinh.setEnabled(true);
        tfSDT1.setEnabled(true);
        tfTaikhoan1.setEnabled(true);
        pass1.setEnabled(true);
        tfDiachi1.setEnabled(true);
        btnEdit1.setEnabled(true);
        btnDel1.setEnabled(true);
        btnAdd1.setEnabled(true);

    }

    private void Disabled() {
        tfMaNV1.setEnabled(false);
        tfHoten1.setEnabled(false);
        tfNgaySinh.setEnabled(false);
        tfSDT1.setEnabled(false);
        tfTaikhoan1.setEnabled(false);
        pass1.setEnabled(false);
        tfDiachi1.setEnabled(false);
        rbNu1.setEnabled(true);
        rbNam1.setEnabled(true);
        btnEdit1.setEnabled(false);
        btnDel1.setEnabled(false);
        btnAdd1.setEnabled(false);
    }

    public void Click(int k) {
        Enabled();

        if (k >= 0) {
            tfMaNV1.setText(model.getValueAt(k, 0).toString());
            tfHoten1.setText(model.getValueAt(k, 1).toString());
            ((JTextField) tfNgaySinh.getDateEditor().getUiComponent()).setText(model.getValueAt(k, 2).toString());
            checkGT(model.getValueAt(k, 3).toString());
            tfSDT1.setText(model.getValueAt(k, 4).toString());
            tfDiachi1.setText(model.getValueAt(k, 5).toString());
            tfTaikhoan1.setText(model.getValueAt(k, 6).toString());
            pass1.setText(model.getValueAt(k, 7).toString());

        }
    }

    private String gioiTinh() {
        if (rbNam1.isSelected()) {
            return rbNam1.getText();
        } else {
            return rbNu1.getText();
        }
    }

    private void addNV() {
        String day = ((JTextField) tfNgaySinh.getDateEditor().getUiComponent()).getText();
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection cnn = DriverManager.getConnection(url, user, pass);
            PreparedStatement ps = cnn.prepareStatement("insert into QLNV values(?,?,?,?,?,?,?,?)");
            ps.setString(1, tfMaNV1.getText());
            ps.setString(2, tfHoten1.getText());
            ps.setString(3, day);
            ps.setString(4, gioiTinh());
            ps.setString(5, tfSDT1.getText());
            ps.setString(6, tfDiachi1.getText());
            ps.setString(7, tfTaikhoan1.getText());
            ps.setString(8, pass1.getText());

            int kq = ps.executeUpdate();
            if (kq == 1) {
                lbTrangthai.setText("Thêm nhân viên thành công!");
                loadData();
            } else {
                lbTrangthai.setText("Them that bai");
            }
            ps.close();
            cnn.close();
        } catch (Exception e) {
            lbTrangthai.setText("Them that bai");
            e.printStackTrace();
        }
    }

    public boolean checkTK() {
        try {
            cnn = DriverManager.getConnection(url, user, pass);
            String check = "SELECT taiKhoan FROM QLNV WHERE taiKhoan = ?";
            PreparedStatement ps = cnn.prepareStatement(check);
            ps.setString(1, tfTaikhoan1.getText());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                if (rs.getString("taiKhoan").toString().trim().equals(tfTaikhoan1.getText())) {
                    lbTrangthai.setText("Tên tài kho?n bạn nhập đã tồn tại");
                    return false;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(qlnv.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    public boolean checkMNV() {
        try {
            cnn = DriverManager.getConnection(url, user, pass);
            String check = "SELECT maNV FROM QLNV WHERE maNV = ?";
            PreparedStatement ps = cnn.prepareStatement(check);
            ps.setString(1, tfMaNV1.getText());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                if (rs.getString("maNV").toString().trim().equals(tfMaNV1.getText())) {
                    lbTrangthai.setText("Mã nhân viên đã tồn tại");
                    return false;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(qlnv.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    private boolean checkNull() {
        if (tfMaNV1.getText().equals("")) {
            lbTrangthai.setText("Bạn chưa nhập mã nhân viên!");
            return false;
        } else if (tfHoten1.getText().equals("")) {
            lbTrangthai.setText("Bạn chưa nhập họ tên nhân viên!");
            return false;
        } else if (rbNam1.isSelected() == false && rbNu1.isSelected() == false) {
            lbTrangthai.setText("Bạn chưa chọn giới tính!");
            return false;
        } else if (((JTextField) tfNgaySinh.getDateEditor().getUiComponent()).getText().equals("")) {
            lbTrangthai.setText("Bạn chưa nhập ngày sinh!");
            return false;
        } else if (tfSDT1.getText().equals("")) {
            lbTrangthai.setText("Bạn chưa nhập số điện thoại!");
            return false;
        } else if (tfDiachi1.getText().equals("")) {
            lbTrangthai.setText("Bạn chưa nhập địa chỉ!");
            return false;
        } else if (tfTaikhoan1.getText().equals("")) {
            lbTrangthai.setText("Bạn chưa nhập tài khoản!");
            return false;
        } else if (pass1.getText().equals("")) {
            lbTrangthai.setText("Bạn chưa nhập mật khẩu!");
            return false;
        } else {
            String Phone = tfSDT1.getText();
            int len = Phone.length();
            if (len != 10) {
                JOptionPane.showMessageDialog(this, "SỐ điện thoại phải đủ 10 chữ số");
                return false;
            }

        }

        checkTK();
        checkMNV();
        return true;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        btnBack = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableNV = new javax.swing.JTable();
        tfFind = new javax.swing.JTextField();
        btnFind = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        lbMaNV1 = new javax.swing.JLabel();
        tfMaNV1 = new javax.swing.JTextField();
        lbHoten1 = new javax.swing.JLabel();
        tfHoten1 = new javax.swing.JTextField();
        lbSDT1 = new javax.swing.JLabel();
        tfSDT1 = new javax.swing.JTextField();
        tfDiachi1 = new javax.swing.JTextField();
        lbDiachi1 = new javax.swing.JLabel();
        lbGioitinh1 = new javax.swing.JLabel();
        rbNam1 = new javax.swing.JRadioButton();
        rbNu1 = new javax.swing.JRadioButton();
        jLabel6 = new javax.swing.JLabel();
        tfTaikhoan1 = new javax.swing.JTextField();
        pass1 = new javax.swing.JPasswordField();
        jLabel7 = new javax.swing.JLabel();
        lbDiachi2 = new javax.swing.JLabel();
        tfNgaySinh = new com.toedter.calendar.JDateChooser();
        jPanel5 = new javax.swing.JPanel();
        btnAdd1 = new javax.swing.JButton();
        btnDel1 = new javax.swing.JButton();
        btnReset1 = new javax.swing.JButton();
        btnCancel1 = new javax.swing.JButton();
        btnEdit1 = new javax.swing.JButton();
        lbQLNV = new javax.swing.JLabel();
        lbTrangthai = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnBack.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Photos/smart-home.png"))); // NOI18N
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        tableNV.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tableNV.setForeground(new java.awt.Color(0, 204, 204));
        tableNV.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã NV", "Ho Tên", "SÐT", "Ðia Chi", "Gioi tính"
            }
        ));
        tableNV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableNVMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableNV);

        tfFind.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        btnFind.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnFind.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Photos/search (1).png"))); // NOI18N
        btnFind.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFindActionPerformed(evt);
            }
        });

        jPanel2.setForeground(new java.awt.Color(204, 255, 204));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(204, 0, 51));
        jLabel5.setText("Thông tin nhân viên");

        lbMaNV1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbMaNV1.setForeground(new java.awt.Color(255, 51, 0));
        lbMaNV1.setText("Mã Nhân viên:");

        tfMaNV1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tfMaNV1.setForeground(new java.awt.Color(51, 51, 51));
        tfMaNV1.setDoubleBuffered(true);
        tfMaNV1.setInheritsPopupMenu(true);

        lbHoten1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbHoten1.setForeground(new java.awt.Color(255, 51, 0));
        lbHoten1.setText("Họ và Tên:");

        tfHoten1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tfHoten1.setForeground(new java.awt.Color(51, 51, 51));
        tfHoten1.setDoubleBuffered(true);
        tfHoten1.setInheritsPopupMenu(true);

        lbSDT1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbSDT1.setForeground(new java.awt.Color(255, 51, 0));
        lbSDT1.setText("Số điện thoại:");

        tfSDT1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tfSDT1.setForeground(new java.awt.Color(51, 51, 51));
        tfSDT1.setDoubleBuffered(true);
        tfSDT1.setInheritsPopupMenu(true);
        tfSDT1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfSDT1KeyReleased(evt);
            }
        });

        tfDiachi1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tfDiachi1.setForeground(new java.awt.Color(51, 51, 51));
        tfDiachi1.setDoubleBuffered(true);
        tfDiachi1.setInheritsPopupMenu(true);

        lbDiachi1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbDiachi1.setForeground(new java.awt.Color(255, 51, 0));
        lbDiachi1.setText("Địa chỉ:");

        lbGioitinh1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbGioitinh1.setForeground(new java.awt.Color(255, 51, 0));
        lbGioitinh1.setText("Giới tính:");

        buttonGroup1.add(rbNam1);
        rbNam1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        rbNam1.setForeground(new java.awt.Color(255, 51, 0));
        rbNam1.setText("Nam");

        buttonGroup1.add(rbNu1);
        rbNu1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        rbNu1.setForeground(new java.awt.Color(255, 51, 0));
        rbNu1.setText("Nữ");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 51, 0));
        jLabel6.setText("Tài khoản:");

        tfTaikhoan1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tfTaikhoan1.setForeground(new java.awt.Color(51, 51, 51));
        tfTaikhoan1.setDoubleBuffered(true);
        tfTaikhoan1.setInheritsPopupMenu(true);

        pass1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        pass1.setForeground(new java.awt.Color(51, 51, 51));
        pass1.setDoubleBuffered(true);
        pass1.setInheritsPopupMenu(true);
        pass1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pass1ActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 51, 0));
        jLabel7.setText("Mật khẩu:");

        lbDiachi2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbDiachi2.setForeground(new java.awt.Color(255, 51, 0));
        lbDiachi2.setText("Ngày Sinh");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lbSDT1)
                        .addGap(18, 18, 18)
                        .addComponent(tfSDT1, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbHoten1)
                            .addComponent(lbMaNV1)
                            .addComponent(lbDiachi2)
                            .addComponent(lbDiachi1)
                            .addComponent(lbGioitinh1))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(rbNu1, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rbNam1, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(tfMaNV1, javax.swing.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
                                            .addComponent(tfHoten1)
                                            .addComponent(tfDiachi1))
                                        .addGap(33, 33, 33)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                    .addComponent(jLabel5))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(pass1, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tfTaikhoan1, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(tfNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(12, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfMaNV1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbMaNV1)
                    .addComponent(jLabel6)
                    .addComponent(tfTaikhoan1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbHoten1)
                    .addComponent(tfHoten1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(pass1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbDiachi1)
                    .addComponent(tfDiachi1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbDiachi2)
                    .addComponent(tfNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(lbGioitinh1))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rbNu1)
                            .addComponent(rbNam1))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbSDT1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfSDT1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnAdd1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnAdd1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Photos/user (1).png"))); // NOI18N
        btnAdd1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnDel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnDel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Photos/user.png"))); // NOI18N
        btnDel1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDelActionPerformed(evt);
            }
        });

        btnReset1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnReset1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Photos/icons8-add-30.png"))); // NOI18N
        btnReset1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        btnCancel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnCancel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Photos/x-button.png"))); // NOI18N
        btnCancel1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnEdit1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnEdit1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Photos/user(2).png"))); // NOI18N
        btnEdit1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAdd1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnEdit1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnDel1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnReset1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnCancel1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnEdit1)
                    .addComponent(btnCancel1)
                    .addComponent(btnAdd1)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(btnDel1)
                        .addComponent(btnReset1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lbQLNV.setFont(new java.awt.Font("Times New Roman", 0, 48)); // NOI18N
        lbQLNV.setText("Quản lý nhân viên");

        lbTrangthai.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        lbTrangthai.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbTrangthai.setText("Trạng Thái");

        jLabel8.setIcon(new javax.swing.ImageIcon("C:\\Users\\dhhoa\\Downloads\\icons8-minus-48.png")); // NOI18N
        jLabel8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel8MouseClicked(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Photos/Button-Close-icon-48.png"))); // NOI18N
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(tfFind, javax.swing.GroupLayout.PREFERRED_SIZE, 623, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(btnFind, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnBack, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lbTrangthai, javax.swing.GroupLayout.PREFERRED_SIZE, 580, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbQLNV))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel8)
                        .addGap(27, 27, 27)
                        .addComponent(jLabel1))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel8))
                        .addGap(62, 62, 62))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnBack, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tfFind, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnFind, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(lbQLNV))
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 393, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lbTrangthai, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void pass1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pass1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pass1ActionPerformed

    private void tfSDT1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfSDT1KeyReleased

    }//GEN-LAST:event_tfSDT1KeyReleased

    private void btnFindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFindActionPerformed

    }//GEN-LAST:event_btnFindActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed

    }//GEN-LAST:event_btnEditActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        MainFr mf = new MainFr();
        mf.setVisible(true);
        mf.setLocationRelativeTo(null);
        mf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        reset();
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnDelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDelActionPerformed
        try {
            int del = JOptionPane.showConfirmDialog(null, "Bạn có muốn xóa nhân viên này hay không?", "Thông báo", 2);
            if (del == JOptionPane.YES_OPTION) {
                cnn = DriverManager.getConnection(url, user, pass);
                String sqldel = "DELETE FROM QLNV WHERE maNV =?";
                PreparedStatement ps = cnn.prepareStatement(sqldel);
                ps.setString(1, tfMaNV1.getText().trim());
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Xóa thành công nhân viên!");
                Click(click);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        reset();
        loadData();

    }//GEN-LAST:event_btnDelActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        if (checkNull()) {
            addNV();
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void tableNVMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableNVMouseClicked
        int k = tableNV.getSelectedRow();
        // show dòng index lên form
        Click(k);
    }//GEN-LAST:event_tableNVMouseClicked

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed

    }//GEN-LAST:event_btnBackActionPerformed

    private void jLabel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseClicked
        this.setState(JFrame.ICONIFIED);
    }//GEN-LAST:event_jLabel8MouseClicked

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        int result = JOptionPane.showConfirmDialog(this,
                "Bạn có muốn thoát khỏi chương trình", "",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (result == JOptionPane.YES_OPTION) {

            MainFr mf = new MainFr();
            mf.setVisible(true);
            mf.setLocationRelativeTo(null);
            mf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.dispose();
        }
    }//GEN-LAST:event_jLabel1MouseClicked

    /**
     * @param args the command line arguments
     */
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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(qlnv.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(qlnv.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(qlnv.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(qlnv.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new qlnv().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd1;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnCancel1;
    private javax.swing.JButton btnDel1;
    private javax.swing.JButton btnEdit1;
    private javax.swing.JButton btnFind;
    private javax.swing.JButton btnReset1;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbDiachi1;
    private javax.swing.JLabel lbDiachi2;
    private javax.swing.JLabel lbGioitinh1;
    private javax.swing.JLabel lbHoten1;
    private javax.swing.JLabel lbMaNV1;
    private javax.swing.JLabel lbQLNV;
    private javax.swing.JLabel lbSDT1;
    private javax.swing.JLabel lbTrangthai;
    private javax.swing.JPasswordField pass1;
    private javax.swing.JRadioButton rbNam1;
    private javax.swing.JRadioButton rbNu1;
    private javax.swing.JTable tableNV;
    private javax.swing.JTextField tfDiachi1;
    private javax.swing.JTextField tfFind;
    private javax.swing.JTextField tfHoten1;
    private javax.swing.JTextField tfMaNV1;
    private com.toedter.calendar.JDateChooser tfNgaySinh;
    private javax.swing.JTextField tfSDT1;
    private javax.swing.JTextField tfTaikhoan1;
    // End of variables declaration//GEN-END:variables
}
