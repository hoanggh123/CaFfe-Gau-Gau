/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author dhhoa
 */
public class NuocUong extends javax.swing.JFrame {

    int click = -1;
    private DefaultTableModel model;
    String sql;
    Statement st;
    Connection cnn;
    String user = "sa";
    String pass = "songlong"; // thay doi pass cho phu hop
    String url = "jdbc:sqlserver://localhost;databaseName=QuanCaPhe";

    /**
     * Creates new form NuocUong
     */
    public NuocUong() {
        initComponents();
        setLocationRelativeTo(null);
        loadData();
        loadLoaiNuoc();
        click = 0;
        Click(click);
        Disabled();
    }

    private void checkKyTu(String arry) {
        char[] character = arry.toCharArray();
        for (int i = 0; i < character.length; i++) {
            if (String.valueOf(character[i]).matches("\\D+")) {
                btnSave.setEnabled(false);
                lbTrangthai.setText("Số lượng không thể chứa kí tự");
                break;
            } else {
                btnSave.setEnabled(true);
            }
        }
    }

    private String cutChar(String arry) {
        return arry.replaceAll("\\D+", "");
    }

    private String cutNumber(String arry) {
        return arry.replaceAll("\\d+", "");
    }

    private void loadLoaiNuoc() {
        cbLoaiNuoc.removeAllItems();
        cbLoaiNuoc.addItem("Cafe");
        cbLoaiNuoc.addItem("Sinh Tố");
        cbLoaiNuoc.addItem("Nước Giải Khát");
    }
    private void loadData() {
        String[] arry = {"Mã Thức Uống", "Loại Nước", "Tên Nước", "Đơn Vị", "Số Lượng", "Giá Bán"};
        model = new DefaultTableModel(arry, 0);
        try {
//            Vector data = null;
            cnn = DriverManager.getConnection(url, user, pass);
            Statement st = cnn.createStatement();
            sql = "Select * from QLNuoc";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Vector vector = new Vector();
                vector.add(rs.getString("maNuoc").trim());
                vector.add(rs.getString("loaiNuoc").trim());
                vector.add(rs.getString("tenNuoc").trim());
                vector.add(rs.getString("donVi").trim());
                vector.add(rs.getInt("soLuong"));
                vector.add(rs.getString("giaBan").trim());

                model.addRow(vector);
            }
            tableDrink.setModel(model);
            st.close();
            cnn.close();
            rs.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void Click(int k) {
        Enabled();
        cbLoaiNuoc.removeAllItems();
        if (k >= 0) {
            tfMatu.setText(model.getValueAt(k, 0).toString());
            cbLoaiNuoc.addItem(model.getValueAt(k, 1).toString());
            tfTen.setText(model.getValueAt(k, 2).toString());
            tfDonVi.setText(model.getValueAt(k, 3).toString());
            tfSoLuong.setText(model.getValueAt(k, 4).toString());
            String[] s1 = model.getValueAt(k, 5).toString().split("\\s");
            tfGia.setText(s1[0]);
            //tfGia.setText(model.getValueAt(click, 5).toString());
        }
    }

    private double convertedToNumbers(String s) {
        String number = "";
        String[] array = s.replace(",", " ").split("\\s");
        for (String i : array) {
            number = number.concat(i);
        }
        return Double.parseDouble(number);
    }

    //UnBlock input
    private void Enabled() {
        tfMatu.setEnabled(true);
        cbLoaiNuoc.setEnabled(true);
        tfTen.setEnabled(true);
        tfDonVi.setEnabled(true);
        tfSoLuong.setEnabled(true);
        tfGia.setEnabled(true);
        btnDel.setEnabled(true);
        btnEdit.setEnabled(true);
        btnSave.setEnabled(true);

    }

    //Block input
    private void Disabled() {
        tfMatu.setEnabled(false);
        cbLoaiNuoc.setEnabled(false);
        tfTen.setEnabled(false);
        tfDonVi.setEnabled(false);
        tfSoLuong.setEnabled(false);
        tfGia.setEnabled(false);
        btnDel.setEnabled(false);
        btnEdit.setEnabled(false);
        btnSave.setEnabled(false);
    }

    //Reset input
    private void reset() {

        loadLoaiNuoc();
        tfMatu.setText("");
        cbLoaiNuoc.setSelectedIndex(0);
        tfTen.setText("");
        tfDonVi.setText("");
        tfSoLuong.setText("");
        tfGia.setText("");
        lbTrangthai.setText("Trạng Thái");
        Enabled();
    }

    private boolean checkNull() {
        if (tfMatu.getText().equals("")) {
            lbTrangthai.setText("Bạn chưa nhập mã thức uống!");
            return false;
        } else if (cbLoaiNuoc.getSelectedItem().equals("")) {
            lbTrangthai.setText("Bạn chưa chọn loại thức uống!");
            return false;
        } else if (tfTen.getText().equals("")) {
            lbTrangthai.setText("Bạn chưa nhập tên thức uống");
            return false;
        } else if (tfDonVi.getText().equals("")) {
            lbTrangthai.setText("Bạn chưa nhập đơn vị tính!");
            return false;
        } else if (tfSoLuong.getText().equals("")) {
            lbTrangthai.setText("Bạn chưa nhập số lượng nước!");
            return false;
        } else if (tfGia.getText().equals("")) {
            lbTrangthai.setText("Bạn chưa nhập giá!");
            return false;
        }
        return true;
    }

    private void addDrink() {
        String nuoc = (String) cbLoaiNuoc.getSelectedItem();
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection cnn = DriverManager.getConnection(url, user, pass);
            PreparedStatement ps = cnn.prepareStatement("insert into QLNuoc values(?,?,?,?,?,?)");
            ps.setString(1, tfMatu.getText());
            ps.setString(2, nuoc);
            ps.setString(3, tfTen.getText());
            ps.setString(4, tfGia.getText());
            ps.setString(5, tfDonVi.getText());
            ps.setString(6, tfSoLuong.getText());
            int kq = ps.executeUpdate();
            if (kq == 1) {
                JOptionPane.showMessageDialog(this, "Thêm thành công");
            }
            loadData();
            ps.close();
            cnn.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Thêm that bai");
            e.printStackTrace();
        }

    }

    private void editDrink() {
        String editnuoc = (String) cbLoaiNuoc.getSelectedItem();
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection cnn = DriverManager.getConnection(url, user, pass);
            PreparedStatement ps = cnn.prepareStatement("UPDATE QLNuoc SET loaiNuoc=?,tenNuoc=?,giaBan=?,donVi=?,soLuong=? WHERE maNuoc=?");
            ps.setString(1, editnuoc);
            ps.setString(2, tfTen.getText());
            ps.setString(3, tfGia.getText());
            ps.setString(4, tfDonVi.getText());
            ps.setString(5, tfSoLuong.getText());
            ps.setString(6, tfMatu.getText());
            int update = ps.executeUpdate();
            if (update == 1) {
                JOptionPane.showMessageDialog(this, "Thức uống đã được thay đổi");
                loadData();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Thay đổi that bai");
            e.printStackTrace();
        }

    }

    public boolean checkMTU() {
        try {
            cnn = DriverManager.getConnection(url, user, pass);
            String check = "SELECT maNuoc FROM QLNuoc WHERE maNuoc = ?";
            PreparedStatement ps = cnn.prepareStatement(check);
            ps.setString(1, tfMatu.getText());
            ResultSet rs = ps.executeQuery();
            String nuoc;
            while (rs.next()) {
                nuoc = rs.getString("maNuoc");
                if (nuoc.equals(tfMatu.getText())) {
                    lbTrangthai.setText("Mã nước bạn nhập đã tồn tại");
                    return false;
                }
                cnn.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(NuocUong.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    public boolean checkTTU() {
        try {
            cnn = DriverManager.getConnection(url, user, pass);
            String check = "SELECT tenNuoc FROM QLNuoc WHERE tenNuoc = ?";
            PreparedStatement ps = cnn.prepareStatement(check);
            ps.setString(1, tfTen.getText());
            ResultSet rs = ps.executeQuery();
            String nuoc;
            while (rs.next()) {
                nuoc = rs.getString("tenNuoc");
                if (nuoc.equals(tfTen.getText())) {
                    lbTrangthai.setText("Tên nước bạn nhập đã tồn tại");
                    return false;
                }
                cnn.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(NuocUong.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

//    private boolean check() {
//        try {
//            ResultSet rs = st.executeQuery(sql);
//            String mtu;
//            String ttu;
//            while (rs.next()) {
//                mtu = rs.getString("maNuoc");
//                ttu = rs.getString("tenNuoc");
//                if (mtu.equals(tfMatu.getText())) {
//                    JOptionPane.showMessageDialog(this, "Ma thuc uong da ton tai");
//                    return false;
//                }
//                if (ttu.equals(tfTen.getText())) {
//                    JOptionPane.showMessageDialog(this, "Ma thuc uong da ton tai");
//                    return false;
//                }
//                cnn.close();
//            }
//            st.close();
//            cnn.close();
//            rs.close();
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return true;
//    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lbMatu = new javax.swing.JLabel();
        lbLoai = new javax.swing.JLabel();
        lbTen = new javax.swing.JLabel();
        lbGia = new javax.swing.JLabel();
        tfMatu = new javax.swing.JTextField();
        tfTen = new javax.swing.JTextField();
        tfGia = new javax.swing.JTextField();
        cbLoaiNuoc = new javax.swing.JComboBox<>();
        lbThongtin = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        tfDonVi = new javax.swing.JTextField();
        tfSoLuong = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        btnAdd = new javax.swing.JButton();
        btnDel = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        lbTrangthai = new javax.swing.JLabel();
        tfFind = new javax.swing.JTextField();
        btnFind = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableDrink = new javax.swing.JTable();
        lbQLTU = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        lbMatu.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbMatu.setText("Mã thức uống:");

        lbLoai.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbLoai.setText("Loại thức uống:");

        lbTen.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbTen.setText("Tên thức uống:");

        lbGia.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbGia.setText("Giá tiền:");

        tfMatu.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        tfTen.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        tfGia.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tfGia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfGiaKeyReleased(evt);
            }
        });

        cbLoaiNuoc.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        lbThongtin.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lbThongtin.setText("Thông tin Thức uống");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Đơn vị tính:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Số lượng:");

        tfDonVi.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        tfSoLuong.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tfSoLuong.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfSoLuongKeyReleased(evt);
            }
        });

        btnAdd.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Photos/icons8-add-30.png"))); // NOI18N
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnDel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnDel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Photos/delete.png"))); // NOI18N
        btnDel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDelActionPerformed(evt);
            }
        });

        btnSave.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Photos/icons8-save-30.png"))); // NOI18N
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnCancel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Photos/x-button.png"))); // NOI18N
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnEdit.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Photos/edit.png"))); // NOI18N
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnDel, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(64, 64, 64))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnSave, javax.swing.GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE)
                    .addComponent(btnCancel, javax.swing.GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE)
                    .addComponent(btnDel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEdit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAdd, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lbTrangthai.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbTrangthai.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbTrangthai.setText("Trạng thái");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(96, 96, 96)
                        .addComponent(lbThongtin))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbLoai)
                            .addComponent(lbMatu)
                            .addComponent(lbTen)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(lbGia))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbLoaiNuoc, 0, 300, Short.MAX_VALUE)
                            .addComponent(tfMatu)
                            .addComponent(tfDonVi)
                            .addComponent(tfSoLuong)
                            .addComponent(tfTen)
                            .addComponent(tfGia)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lbTrangthai, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(lbThongtin)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbMatu)
                    .addComponent(tfMatu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbLoai)
                    .addComponent(cbLoaiNuoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbTen)
                    .addComponent(tfTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(tfDonVi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(tfSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbGia)
                    .addComponent(tfGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbTrangthai, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(9, Short.MAX_VALUE))
        );

        tfFind.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        btnFind.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnFind.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Photos/search (1).png"))); // NOI18N
        btnFind.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFindActionPerformed(evt);
            }
        });

        btnBack.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Photos/smart-home.png"))); // NOI18N
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        tableDrink.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tableDrink.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Mã thức uống", "Loại thức uống", "Tên thức uống", "Giá bán"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tableDrink.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableDrinkMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableDrink);

        lbQLTU.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        lbQLTU.setText("Quản lý thức uống");

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
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(tfFind, javax.swing.GroupLayout.PREFERRED_SIZE, 528, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnFind, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1))
                        .addGap(26, 26, 26)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnBack)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbQLTU)
                        .addGap(150, 150, 150)
                        .addComponent(jLabel8)
                        .addGap(12, 12, 12)
                        .addComponent(jLabel1))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(btnBack))
                    .addComponent(jLabel8)
                    .addComponent(jLabel1)
                    .addComponent(lbQLTU))
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnFind, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tfFind))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 389, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(37, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tfGiaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfGiaKeyReleased

    }//GEN-LAST:event_tfGiaKeyReleased

    private void tfSoLuongKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfSoLuongKeyReleased

    }//GEN-LAST:event_tfSoLuongKeyReleased

    private void btnFindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFindActionPerformed

    }//GEN-LAST:event_btnFindActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        MainFr mn = new MainFr();
        this.setVisible(false);
        mn.setVisible(true);
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed

        reset();

    }//GEN-LAST:event_btnAddActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed

        editDrink();

    }//GEN-LAST:event_btnEditActionPerformed

    private void btnDelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDelActionPerformed
        try {
            int del = JOptionPane.showConfirmDialog(null, "Bạn có muốn xóa thức uống này hay không?", "Thông báo", 2);
            if (del == JOptionPane.YES_OPTION) {
                cnn = DriverManager.getConnection(url, user, pass);
                String sqldel = "DELETE FROM QLNuoc WHERE maNuoc =?";
                PreparedStatement ps = cnn.prepareStatement(sqldel);
                ps.setString(1, tfMatu.getText().trim());
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Xóa thành công thức uống!");
                Click(click);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        reset();
        loadData();
    }//GEN-LAST:event_btnDelActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        if (checkMTU() && checkTTU()) {
            addDrink();
        }

    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed

        int result = JOptionPane.showConfirmDialog(this,
                "Bạn có muốn thoát khỏi chương trình", "",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if (result == JOptionPane.YES_OPTION) {
            MainFr mf = new MainFr();
            mf.setVisible(true);
            this.dispose();
        }

//       Disabled();
//        reset();
//        loadData();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void tableDrinkMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableDrinkMouseClicked
        click = tableDrink.getSelectedRow();
        // show dòng index lên form
        Click(click);
    }//GEN-LAST:event_tableDrinkMouseClicked

    private void jLabel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseClicked
        this.setState(JFrame.ICONIFIED);
    }//GEN-LAST:event_jLabel8MouseClicked

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        int result = JOptionPane.showConfirmDialog(this,
                "Bạn có muốn thoát khỏi chương trình", "",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if (result == JOptionPane.YES_OPTION) {
//            System.exit(0);
            MainFr mf = new MainFr();
            mf.setVisible(true);
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
            java.util.logging.Logger.getLogger(NuocUong.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NuocUong.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NuocUong.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NuocUong.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NuocUong().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnDel;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnFind;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox<String> cbLoaiNuoc;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbGia;
    private javax.swing.JLabel lbLoai;
    private javax.swing.JLabel lbMatu;
    private javax.swing.JLabel lbQLTU;
    private javax.swing.JLabel lbTen;
    private javax.swing.JLabel lbThongtin;
    private javax.swing.JLabel lbTrangthai;
    private javax.swing.JTable tableDrink;
    private javax.swing.JTextField tfDonVi;
    private javax.swing.JTextField tfFind;
    private javax.swing.JTextField tfGia;
    private javax.swing.JTextField tfMatu;
    private javax.swing.JTextField tfSoLuong;
    private javax.swing.JTextField tfTen;
    // End of variables declaration//GEN-END:variables
}
