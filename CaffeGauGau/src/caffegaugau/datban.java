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
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author dhhoa
 */
public class datban extends javax.swing.JFrame {

    /**
     * Creates new form DatBam
     */
    private boolean add = false, change = false;
    int click = -1;
    private DefaultTableModel model;
    String sql;
    Statement st;
    Connection cnn;
    String user = "sa";
    String pass = "songlong"; // thay doi pass cho phu hop
    String url = "jdbc:sqlserver://localhost;databaseName=QuanCaPhe";

    public datban() {
        initComponents();
        setLocationRelativeTo(null);
        Load();
        loadBan();
        Click(click);
        Disabled();

    }

    private boolean checkNull() {
        if (tfTenkhach.getText().equals("")) {
            lbTrangthai.setText("Bạn chưa nhập tên khách hàng");
            return false;
        } else if (tfSDT.getText().equals("")) {
            lbTrangthai.setText("Bạn cần phải nhập số điện thoại của khách");
            return false;
        } else if (cboSoban.getSelectedItem() == null) {
            lbTrangthai.setText("Bạn cần chọn bàn cho khách");
            return false;
        } else //         if(tfTimes.getText().equals("")){
        //            lbTrangthai.setText("Chưa có thời gian đặt bàn của khách");
        //            return false;
        //        }else
        if (((JTextField) tfDay.getDateEditor().getUiComponent()).getText().equals("")) {
            lbTrangthai.setText("Chưa ấn định ngày đặt bàn");
            return false;
        } else if (radNo.isSelected() == false && radDathanhtoan.isSelected() == false) {
            lbTrangthai.setText("Bạn chưa chọn tình trạng thanh toán!");
            return false;
        }
        String Phone = tfSDT.getText();
        int len = Phone.length();
        if (len != 10) {
            JOptionPane.showMessageDialog(this, "SỐ điệm thoại phải đủ 10 chữ số");
            return false;
        }

        checkBan();

        return true;

    }

    private void Enabled() {
        tfTenkhach.setEnabled(true);
        tfSDT.setEnabled(true);
        cboSoban.setEnabled(true);
        cbxHours.setEnabled(true);
        cbxMinute.setEnabled(true);
        tfDay.setEnabled(true);
        radNo.setEnabled(true);
        radDathanhtoan.setEnabled(true);
        tfNote.setEnabled(true);
        btnEdit.setEnabled(true);
        btnDel.setEnabled(true);
        btnSave.setEnabled(true);
    }

    private void Disabled() {
        tfTenkhach.setEnabled(false);
        tfSDT.setEnabled(false);
        cboSoban.setEnabled(false);
        cbxHours.setEnabled(false);
        cbxMinute.setEnabled(false);
        tfDay.setEnabled(false);
        radNo.setEnabled(false);
        radDathanhtoan.setEnabled(false);
        tfNote.setEnabled(false);
        btnSave.setEnabled(false);
        btnDel.setEnabled(false);
        btnEdit.setEnabled(false);
    }

    private void Load() {
        String[] arry = {"Tên khách hàng", "SĐT", "Bàn", "Thời gian", "Ngày", "Thanh toán", "Ghi Chú"};
        model = new DefaultTableModel(arry, 0);
        try {
            cnn = DriverManager.getConnection(url, user, pass);
            Statement st = cnn.createStatement();
            sql = "Select * from DatBan";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Vector vector = new Vector();
                vector.add(rs.getString("tenKH").trim());
                vector.add(rs.getString("sdt").trim());
                vector.add(rs.getInt("ban"));
                vector.add(rs.getString("thoiGian").trim());
                vector.add(rs.getString("ngay").trim());
                vector.add(rs.getString("thanhToan").trim());
                vector.add(rs.getString("ghiChu").trim());
                model.addRow(vector);
            }
            tableDatban.setModel(model);
            st.close();
            cnn.close();
            rs.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void loadBan() {
        cboSoban.removeAllItems();

        for (int i = 1; i <= 25; i++) {
            cboSoban.addItem(String.valueOf(i));
        }
    }

    private void loadHours() {
        cbxHours.removeAllItems();

        for (int i = 0; i <= 23; i++) {
            cbxHours.addItem(String.valueOf(i));
        }
    }

    private void loadMinute() {
        cbxMinute.removeAllItems();

        for (int i = 0; i <= 59; i++) {
            if (i < 10) {
                cbxMinute.addItem(String.valueOf("0" + i));
            } else {
                cbxMinute.addItem(String.valueOf(i));
            }
        }
    }

    private void reset() {
        loadBan();
        loadHours();
        loadMinute();
        tfTenkhach.setText("");
        tfSDT.setText("");
        cboSoban.setSelectedIndex(0);
        cbxHours.setSelectedIndex(0);
        cbxMinute.setSelectedIndex(0);
        ((JTextField) tfDay.getDateEditor().getUiComponent()).setText("");
        radNo.setSelected(false);
        radDathanhtoan.setSelected(false);
        tfNote.setText("");
        btnAdd.setEnabled(true);
        btnEdit.setEnabled(false);
        btnDel.setEnabled(false);
        btnSave.setEnabled(false);
        btnCancel.setEnabled(false);
        lbTrangthai.setText("Trạng thái");
    }

    private void checkThanhtoan(String tt) {
        if (tt.equals("Không")) {
            radNo.setSelected(true);
        } else {
            radDathanhtoan.setSelected(true);
        }
    }

    public void Click(int k) {
        Enabled();
        cboSoban.removeAllItems();
        cbxHours.removeAllItems();
        cbxMinute.removeAllItems();
        if (k >= 0) {
//               k=tableDatban.getSelectedRow();
//        TableModel model=tableDatban.getModel();

            tfTenkhach.setText(model.getValueAt(k, 0).toString());
            tfSDT.setText(model.getValueAt(k, 1).toString());
            cboSoban.addItem(model.getValueAt(k, 2).toString());
            String[] s = model.getValueAt(k, 3).toString().split(":");

            cbxHours.addItem(s[0]);
            cbxMinute.addItem(s[1]);

            ((JTextField) tfDay.getDateEditor().getUiComponent()).setText(model.getValueAt(k, 4).toString());
            tfNote.setText(model.getValueAt(k, 6).toString());
            checkThanhtoan(model.getValueAt(k, 5).toString());
        }

    }

    private void addBan() {
        String ban = (String) cboSoban.getSelectedItem();
        String gio = (String) cbxHours.getSelectedItem();
        String phut = (String) cbxMinute.getSelectedItem();
        String day = ((JTextField) tfDay.getDateEditor().getUiComponent()).getText();

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection cnn = DriverManager.getConnection(url, user, pass);
            PreparedStatement ps = cnn.prepareStatement("insert into DatBan values(?,?,?,?,?,?,?)");
            ps.setString(1, tfTenkhach.getText());
            ps.setString(2, tfSDT.getText());
            ps.setString(3, ban);
            ps.setString(4, gio + lbl2Cham.getText() + phut);
            ps.setString(5, day);
            ps.setString(6, thanhtoan());
            ps.setString(7, tfNote.getText());
            int kq = ps.executeUpdate();
            if (kq == 1) {
                JOptionPane.showMessageDialog(this, "Thêm thành công");
            }
            Load();
            ps.close();
            cnn.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Thêm that bai");
            e.printStackTrace();
        }

    }

    private String thanhtoan() {
        if (radNo.isSelected()) {
            return radNo.getText();
        } else {
            return radDathanhtoan.getText();
        }
    }

    public boolean checkBan() {
        String ban = (String) cboSoban.getSelectedItem();
        try {
            cnn = DriverManager.getConnection(url, user, pass);
            String check = "SELECT ban FROM DatBan WHERE ban = ?";
            PreparedStatement ps = cnn.prepareStatement(check);
            ps.setString(1, ban);
            ResultSet rs = ps.executeQuery();
            String nuoc;
            while (rs.next()) {
                nuoc = rs.getString("ban");
                if (nuoc.equals(ban)) {
                    lbTrangthai.setText("Bàn này dã có ngu?i dat");
                    return false;
                }

            }
        } catch (SQLException ex) {
            Logger.getLogger(datban.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    private void editBan() {
        String ban = (String) cboSoban.getSelectedItem();
        String gio = (String) cbxHours.getSelectedItem();
        String phut = (String) cbxMinute.getSelectedItem();
        String day = ((JTextField) tfDay.getDateEditor().getUiComponent()).getText();
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection cnn = DriverManager.getConnection(url, user, pass);
            PreparedStatement ps = cnn.prepareStatement("UPDATE DatBan SET sdt=?,ban=?,thoiGian=?,ngay=?,thanhToan=?,ghiChu=? WHERE tenKH=?");
            ps.setString(1, tfSDT.getText());
            ps.setString(2, ban);
            ps.setString(3, gio + lbl2Cham.getText() + phut);
            ps.setString(4, day);
            ps.setString(5, thanhtoan());
            ps.setString(6, tfNote.getText());
            ps.setString(7, tfTenkhach.getText());

            int update = ps.executeUpdate();
            if (update == 1) {
                JOptionPane.showMessageDialog(this, "Thức uống đã được thay đổi");
                Load();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Thay đổi that bai");
            e.printStackTrace();
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        lbTenkhach = new javax.swing.JLabel();
        lbSoban = new javax.swing.JLabel();
        lbTime = new javax.swing.JLabel();
        lbDay = new javax.swing.JLabel();
        tfTenkhach = new javax.swing.JTextField();
        cboSoban = new javax.swing.JComboBox<>();
        tfSDT = new javax.swing.JTextField();
        lbSDT = new javax.swing.JLabel();
        lbThanhtoan = new javax.swing.JLabel();
        radNo = new javax.swing.JRadioButton();
        radDathanhtoan = new javax.swing.JRadioButton();
        jPanel2 = new javax.swing.JPanel();
        tfNote = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableDatban = new javax.swing.JTable();
        cbxHours = new javax.swing.JComboBox<>();
        cbxMinute = new javax.swing.JComboBox<>();
        lbl2Cham = new javax.swing.JLabel();
        tfDay = new com.toedter.calendar.JDateChooser();
        btnHome = new javax.swing.JButton();
        lbTrangthai = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        btnSave = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnDel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Times New Roman", 0, 48)); // NOI18N
        jLabel1.setText("ĐẶT BÀN");

        lbTenkhach.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbTenkhach.setText("Tên khách hàng:");

        lbSoban.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbSoban.setText("Bàn số:");

        lbTime.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbTime.setText("Thời gian:");

        lbDay.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbDay.setText("Ngày:");

        tfTenkhach.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        cboSoban.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        tfSDT.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tfSDT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfSDTKeyReleased(evt);
            }
        });

        lbSDT.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbSDT.setText("Số điện thoại:");

        lbThanhtoan.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbThanhtoan.setText("Trả trước:");

        radNo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        radNo.setText("Không");
        radNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radNoActionPerformed(evt);
            }
        });

        radDathanhtoan.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        radDathanhtoan.setText("Đã thanh toán");
        radDathanhtoan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radDathanhtoanActionPerformed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Ghi chú", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14))); // NOI18N

        tfNote.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tfNote)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(tfNote)
                .addContainerGap())
        );

        tableDatban.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6"
            }
        ));
        tableDatban.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableDatbanMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableDatban);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 648, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        cbxHours.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        cbxMinute.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        lbl2Cham.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl2Cham.setText(":");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbThanhtoan)
                            .addComponent(lbSDT)
                            .addComponent(lbSoban)
                            .addComponent(lbTenkhach)
                            .addComponent(lbTime)
                            .addComponent(lbDay))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(tfSDT)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(radNo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(radDathanhtoan)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(cboSoban, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tfTenkhach)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(cbxHours, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lbl2Cham, javax.swing.GroupLayout.PREFERRED_SIZE, 6, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbxMinute, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(tfDay, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbTenkhach)
                            .addComponent(tfTenkhach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbSDT)
                            .addComponent(tfSDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbSoban)
                            .addComponent(cboSoban, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbTime)
                            .addComponent(cbxHours, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbxMinute, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl2Cham))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbDay)
                            .addComponent(tfDay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(14, 14, 14)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbThanhtoan)
                            .addComponent(radNo)
                            .addComponent(radDathanhtoan))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        btnHome.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnHome.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Photos/smart-home.png"))); // NOI18N
        btnHome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHomeActionPerformed(evt);
            }
        });

        lbTrangthai.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbTrangthai.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbTrangthai.setText("Trạng thái");

        btnSave.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Photos/floppy-disk (1).png"))); // NOI18N
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

        btnAdd.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Photos/plus.png"))); // NOI18N
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Photos/edit.png"))); // NOI18N
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnDel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Photos/delete.png"))); // NOI18N
        btnDel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnDel, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnCancel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSave, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEdit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 18, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbTrangthai, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnHome)
                                .addGap(414, 414, 414)
                                .addComponent(jLabel1)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(242, 242, 242))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(14, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addGap(12, 12, 12))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnHome)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbTrangthai)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tfSDTKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfSDTKeyReleased

    }//GEN-LAST:event_tfSDTKeyReleased

    private void radNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radNoActionPerformed

    }//GEN-LAST:event_radNoActionPerformed

    private void radDathanhtoanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radDathanhtoanActionPerformed

    }//GEN-LAST:event_radDathanhtoanActionPerformed

    private void tableDatbanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableDatbanMouseClicked

        click = tableDatban.getSelectedRow();
        // show dòng index lên form
        Click(click);
    }//GEN-LAST:event_tableDatbanMouseClicked

    private void btnHomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHomeActionPerformed

    }//GEN-LAST:event_btnHomeActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        if (checkNull()) {
            addBan();
            reset();
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        MainFr mn  = new MainFr();
        this.setVisible(false);
        mn.setVisible(true);
       
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        reset();
        add = true;
        Enabled();

        btnSave.setEnabled(true);
        btnAdd.setEnabled(false);
        btnCancel.setEnabled(true);

    }//GEN-LAST:event_btnAddActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed


    }//GEN-LAST:event_btnEditActionPerformed

    private void btnDelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDelActionPerformed

        String ban = (String) cboSoban.getSelectedItem();
        try {
            int del = JOptionPane.showConfirmDialog(null, "Bạn có muốn xóa khách đặt bàn này hay không?", "Thông báo", 2);
            if (del == JOptionPane.YES_OPTION) {
                cnn = DriverManager.getConnection(url, user, pass);
                String sqldel = "DELETE FROM DatBan WHERE ban =?";
                PreparedStatement ps = cnn.prepareStatement(sqldel);
                ps.setString(1, ban.trim());
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Xóa thành công khách đặt bàn");
                Click(click);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        reset();
        Load();
    }//GEN-LAST:event_btnDelActionPerformed

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
            java.util.logging.Logger.getLogger(datban.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(datban.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(datban.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(datban.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new datban().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnDel;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnHome;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox<String> cboSoban;
    private javax.swing.JComboBox<String> cbxHours;
    private javax.swing.JComboBox<String> cbxMinute;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbDay;
    private javax.swing.JLabel lbSDT;
    private javax.swing.JLabel lbSoban;
    private javax.swing.JLabel lbTenkhach;
    private javax.swing.JLabel lbThanhtoan;
    private javax.swing.JLabel lbTime;
    private javax.swing.JLabel lbTrangthai;
    private javax.swing.JLabel lbl2Cham;
    private javax.swing.JRadioButton radDathanhtoan;
    private javax.swing.JRadioButton radNo;
    private javax.swing.JTable tableDatban;
    private com.toedter.calendar.JDateChooser tfDay;
    private javax.swing.JTextField tfNote;
    private javax.swing.JTextField tfSDT;
    private javax.swing.JTextField tfTenkhach;
    // End of variables declaration//GEN-END:variables
}
