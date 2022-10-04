/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package caffegaugau;

import Database.*;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Vector;
import javax.swing.*;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;
//import net.sf.jasperreports.engine.*;
//import net.sf.jasperreports.view.*;

/**
 *
 * @author dhhoa
 */
public class BanCf extends javax.swing.JFrame {

    private caffegaugau detail;
    private Thread thread;

    private MyDatabase SQL;

    private Connection conn = null, connCheck = null;
    private ResultSet rs = null, rsCheck = null;

    private boolean Pay = false;

    private ImageIcon icon1 = new ImageIcon(getClass().getResource("/Photos/coffee-table (1).png"));
    private ImageIcon icon2 = new ImageIcon(getClass().getResource("/Photos/coffee-table.png"));
    private ImageIcon icon3 = new ImageIcon(getClass().getResource("/Photos/coffee-table (3).png"));

    JButton[] ban = new JButton[Constants.soBanNgang * Constants.soBanDoc];

    /**
     * Creates new form BanCf
     */
    public BanCf() {
        initComponents();
    }

    private int getHours(String s) {
        String[] array = s.replace(":", " ").split("\\s");

        return Integer.parseInt(array[0]);
    }

    private int getMinute(String s) {
        String[] array = s.replace(":", " ").split("\\s");

        return Integer.parseInt(array[1]);
    }

    private void checkStatus() {
        String queryString = "SELECT * FROM DatBan";
        rsCheck = SQL.excuteQuery(conn, queryString);
        String[] day = lblDate.getText().split("\\s");

        try {
            while (rsCheck.next()) {
                if (String.valueOf(rsCheck.getString("ngay").trim()).equals(day[1])) {

                    if ((getHours(lblTime.getText()) - getHours(String.valueOf(rsCheck.getString("thoiGian").trim()))) == -1) {
                        //if(getMinute(rsCheck.getString("thoiGian").trim())>=30 && (getMinute(lblTime.getText())+(30-getMinute(String.valueOf(rsCheck.getString("thoiGian").trim()))))==60 || getMinute(rsCheck.getString("thoiGian").trim())<30 && (60-getMinute(lblTime.getText()))<=(30-getMinute(String.valueOf(rsCheck.getString("thoiGian").trim())))){
                        if (getMinute(rsCheck.getString("thoiGian").trim()) < 30 && (60 - getMinute(lblTime.getText())) <= (30 - getMinute(String.valueOf(rsCheck.getString("thoiGian").trim())))) {

                            for (JButton jButton : ban) {
                                if (jButton.getText().equals(String.valueOf(rsCheck.getInt("ban")))) {
                                    jButton.setEnabled(false);
                                }
                            }
                        }
                    } else if ((getHours(lblTime.getText()) - getHours(String.valueOf(rsCheck.getString("thoiGian").trim()))) == 0) {
                        if (getMinute(String.valueOf(rsCheck.getString("thoiGian").trim())) >= 30 && (getMinute(String.valueOf(rsCheck.getString("thoiGian").trim())) - getMinute(lblTime.getText())) <= 30 || getMinute(String.valueOf(rsCheck.getString("thoiGian").trim())) < 30 && (getMinute(lblTime.getText()) - getMinute(String.valueOf(rsCheck.getString("thoiGian").trim()))) <= 30) {
                            for (JButton jButton : ban) {
                                if (jButton.getText().equals(String.valueOf(rsCheck.getInt("ban")))) {
                                    jButton.setEnabled(false);
                                }
                            }
                        } else if ((getMinute(lblTime.getText()) - getMinute(rsCheck.getString("thoiGian").trim())) >= 30) {
                            for (JButton jButton : ban) {
                                if (jButton.getText().equals(String.valueOf(rsCheck.getInt("ban")))) {
                                    jButton.setEnabled(true);
                                    String sql = "DELETE FROM DatBan WHERE ban=" + rsCheck.getInt("ban");
                                    SQL.excuteUpdata(conn, sql);
                                }
                            }
                        }
                    } else if ((getHours(lblTime.getText()) - getHours(String.valueOf(rsCheck.getString("thoiGian").trim()))) == 1) {
                        if (getMinute(String.valueOf(rsCheck.getString("thoiGian").trim())) >= 30 && ((60 - getMinute(String.valueOf(rsCheck.getString("thoiGian").trim()))) + getMinute(lblTime.getText())) < 30) {
                            for (JButton jButton : ban) {
                                if (jButton.getText().equals(String.valueOf(rsCheck.getInt("ban")))) {
                                    jButton.setEnabled(false);
                                }
                            }
                        } else if (((60 - getMinute(rsCheck.getString("thoiGian").trim())) + getMinute(lblTime.getText())) >= 30 && getMinute(String.valueOf(rsCheck.getString("thoiGian").trim())) >= 30) {
                            for (JButton jButton : ban) {
                                if (jButton.getText().equals(String.valueOf(rsCheck.getInt("ban")))) {
                                    jButton.setEnabled(true);
                                    String sql = "DELETE FROM DatBan WHERE ban=" + rsCheck.getInt("ban");
                                    SQL.excuteUpdata(conn, sql);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private String cutChar(String arry) {
        return arry.replaceAll("\\D+", "");
    }

    private void Disabled() {
        cbxNuoc.setEnabled(false);
        tfSoLuong.setEnabled(false);
        btnAdd.setEnabled(false);
    }

    private void taoBan(){
        int count=1;
        JButton oldButton=new JButton();
        oldButton.setBounds(0,0,0,0);
        for(int i = 0; i < Constants.soBanDoc; i++){
            for(int j = 0; j < Constants.soBanNgang; j++){
                
                JButton button = new JButton(""+count,icon3);
                button.setHorizontalTextPosition(JButton.CENTER);
                button.setVerticalTextPosition(JButton.BOTTOM);
                
                button.setBounds(oldButton.getX()+oldButton.getWidth(), oldButton.getY(), Constants.Button_Width, Constants.Button_Height);
                button.addActionListener((ActionListener) this);
                
                oldButton.setBounds(button.getX(),button.getY() , button.getWidth()+Constants.distance, button.getHeight()+Constants.distance);
                
                ban[count-1]=button;
                count++;
            }
            oldButton.setBounds(0, oldButton.getY()+oldButton.getHeight(), 0, 0);
        }
    }

    private void veBan() {
        for (JButton jButton : ban) {
            PanelBan.add(jButton);
        }
    }

    private void checkBill() {
        if (tableHoaDon.getRowCount() == 0) {
            tfPay.setEnabled(false);
            tfTienNhanCuaKach.setEnabled(false);
        } else {
            tfPay.setEnabled(true);
            tfTienNhanCuaKach.setEnabled(true);
        }
    }

    private void loadLoaiNuoc() {
        cbLoaiNuoc.removeAllItems();
        cbLoaiNuoc.addItem("Cafe");
        cbLoaiNuoc.addItem("Sinh Tố");
        cbLoaiNuoc.addItem("Nước Giải Khát");
    }

    private void Start() {
        if (thread == null) {
            thread = new Thread();
            thread.start();
        }
    }

    private void Update() {
        lblTime.setText(String.valueOf(new SimpleDateFormat("HH:mm:ss").format(new java.util.Date())));
        if (lbBan.getText().equals("0")) {
            cbLoaiNuoc.setEnabled(false);
        } else {
            cbLoaiNuoc.setEnabled(true);
        }
        checkStatus();
    }

    private void loadData(String sql) {
        try {
            String[] arry = {"Tên thức uống", "Số lượng", "Thành tiền"};
            DefaultTableModel model = new DefaultTableModel(arry, 0);

            rs = SQL.excuteQuery(conn, sql);

            while (rs.next()) {
                Vector vector = new Vector();
                vector.add(rs.getString("tenNuoc").trim());
                vector.add(rs.getInt("soLuong"));
                vector.add(rs.getString("thanhTien").trim());
                model.addRow(vector);
            }
            tableHoaDon.setModel(model);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void deleteThongTinHoaDon() {
        String sqlDelete = "DELETE FROM ThongTinHoaDon";
        SQL.excuteUpdata(conn, sqlDelete);
    }

    private void deleteHoaDon() {
        String sqlDelete = "DELETE FROM HoaDon";
        SQL.excuteUpdata(conn, sqlDelete);
    }

    private void checkTinhTrangban() {
        try {
            for (JButton jButton : ban) {
                String sql = "SELECT * FROM BanHang WHERE ban=" + jButton.getText();
                rs = SQL.excuteQuery(conn, sql);
                if (rs.next()) {
                    jButton.setIcon(icon1);
                } else {
                    jButton.setIcon(icon3);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void checkSoLuongHang() {
        String sqlCheck = "SELECT * FROM QLNuoc WHERE tenNuoc=N'" + cbxNuoc.getSelectedItem() + "'";
        try {

            rs = SQL.excuteQuery(conn, sqlCheck);
            while (rs.next()) {
                if (rs.getInt("soLuong") == 0) {
                    lblStatus.setText(rs.getString("tenNuoc") + " hết hàng!!");
                    btnAdd.setEnabled(false);
                    tfSoLuong.setEnabled(false);
                } else {
                    lblStatus.setText(rs.getString("tenNuoc") + " còn " + rs.getInt("soLuong") + " " + rs.getString("donVi") + " !!");
                    //btnAdd.setEnabled(true);
                    tfSoLuong.setEnabled(true);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
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

    private void tinhTongTien() {
        lbTongTien.setText("0 VNĐ");

        String sqlPay = "SELECT * FROM BanHang WHERE ban=" + lbBan.getText();
        try {
            rs = SQL.excuteQuery(conn, sqlPay);
            while (rs.next()) {
                String[] s1 = rs.getString("thanhTien").toString().trim().split("\\s");
                String[] s2 = lbTongTien.getText().split("\\s");

                double totalMoney = convertedToNumbers(s1[0]) + convertedToNumbers(s2[0]);
                DecimalFormat formatter = new DecimalFormat("###,###,###");

                lbTongTien.setText(formatter.format(totalMoney) + " " + s1[1]);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void luuThongKe() {
        String[] s = lbTongTien.getText().split("\\s");
        String sqlThongKe = "INSERT INTO ThongKe (ban,tongTien,tienKH,tienThua,tenNV,ngay,thoiGian) VALUES(" + lbBan.getText() + ",N'" + lbTongTien.getText() + "',N'" + (tfTienNhanCuaKach.getText() + " " + s[1]) + "',N'" + lbTienthua.getText() + "',N'" + lbNhanVien.getText() + "','" + lblDate.getText() + "','" + lblTime.getText() + "')";

        SQL.excuteUpdata(conn, sqlThongKe);
    }

    private void consistency() {
        try {
            String sqlTemp = "SELECT * FROM QLNuoc WHERE tenNuoc =N'" + cbxNuoc.getSelectedItem() + "'";

            ResultSet rsTemp = SQL.excuteQuery(conn, sqlTemp);

            if (rsTemp.next()) {

                String sqlUpdate = "UPDATE QLNuoc SET soLuong=" + (rsTemp.getInt("soLuong") - Integer.parseInt(tfSoLuong.getText())) + " WHERE tenNuoc=N'" + cbxNuoc.getSelectedItem() + "'";

                SQL.excuteUpdata(conn, sqlUpdate);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void setIcon(String i) {
        for (JButton jButton : ban) {
            if (jButton.getText().equals(i)) {
                jButton.setIcon(icon3);
            }
        }
    }

    private void Delete() {
        String sqlDelete = "DELETE FROM BanHang WHERE ban=" + lbBan.getText();
        SQL.excuteUpdata(conn, sqlDelete);
    }

    private void addThucUong() {
        String sql = "SELECT * FROM BanHang WHERE ban=" + lbBan.getText();
        String sqlInsert = "INSERT INTO BanHang (ban,tenNuoc,soLuong,thanhTien) VALUES(" + lbBan.getText() + ",N'" + cbxNuoc.getSelectedItem() + "'," + tfSoLuong.getText() + ",N'" + lbThanhTien.getText() + "')";

        SQL.excuteUpdata(conn, sqlInsert);
        lblStatus.setText("Thêm sản phẩm thành công!");
        Disabled();
        loadData(sql);
        checkBill();
    }

    private void addHoaDon() {
        String sql = "SELECT * FROM BanHang WHERE ban=" + lbBan.getText();
        try {
            rs = SQL.excuteQuery(conn, sql);
            while (rs.next()) {
                String sqlInsert = "INSERT INTO HoaDon (tenNuoc,soLuong,thanhTien) VALUES(N'" + rs.getString("tenNuoc") + "'," + rs.getInt("soLuong") + ",N'" + rs.getString("thanhTien") + "')";
                SQL.excuteUpdata(conn, sqlInsert);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void Refresh() {
        Pay = false;
        lbBan.setText("0");
        tfSoLuong.setText("");
        lbGia.setText("0 VNĐ");
        lbThanhTien.setText("0 VNĐ");
        lbTongTien.setText("0 VNĐ");
        tfTienNhanCuaKach.setText("");
        lbTienthua.setText("0 VNĐ");
        tfPay.setEnabled(false);
        btnPrint.setEnabled(false);
        Disabled();
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
        jLabel14 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        PanelBan = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableHoaDon = new javax.swing.JTable();
        lblStatus = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        cbLoaiNuoc = new javax.swing.JComboBox<>();
        cbxNuoc = new javax.swing.JComboBox<>();
        btnAdd = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        lbGia = new javax.swing.JLabel();
        lbThanhTien = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        tfSoLuong = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lbNhanVien = new javax.swing.JLabel();
        lblDate = new javax.swing.JLabel();
        lblTime = new javax.swing.JLabel();
        lbBan = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        tfPay = new javax.swing.JButton();
        tfTienNhanCuaKach = new javax.swing.JTextField();
        btnPrint = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lbTienthua = new javax.swing.JLabel();
        btnHome = new javax.swing.JButton();
        lbTongTien = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Edwardian Script ITC", 0, 48)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Quán Cafe Demo");

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Photos/coffee-table.png"))); // NOI18N
        jLabel14.setText("Đang Được Chọn");
        jLabel14.setToolTipText("");
        jLabel14.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel14.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel14.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Photos/coffee-table (1).png"))); // NOI18N
        jLabel13.setText("Đã Có Khách");
        jLabel13.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel13.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel13.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Photos/coffee-table (3).png"))); // NOI18N
        jLabel15.setText("Khách đặt");
        jLabel15.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel15.setEnabled(false);
        jLabel15.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel15.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Photos/coffee-table (3).png"))); // NOI18N
        jLabel10.setText("Bàn Trống");
        jLabel10.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel10.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel10.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Quản lý bàn", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 18))); // NOI18N

        javax.swing.GroupLayout PanelBanLayout = new javax.swing.GroupLayout(PanelBan);
        PanelBan.setLayout(PanelBanLayout);
        PanelBanLayout.setHorizontalGroup(
            PanelBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 426, Short.MAX_VALUE)
        );
        PanelBanLayout.setVerticalGroup(
            PanelBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 350, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(PanelBan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PanelBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        tableHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tableHoaDon);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
        );

        lblStatus.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblStatus.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblStatus.setText("Trạng Thái");

        cbLoaiNuoc.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cbLoaiNuoc.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                cbLoaiNuocPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        cbxNuoc.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cbxNuoc.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                cbxNuocPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        btnAdd.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Photos/plus.png"))); // NOI18N
        btnAdd.setEnabled(false);
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Số lượng");

        lbGia.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbGia.setText("0 VNĐ");

        lbThanhTien.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbThanhTien.setText("0 VNĐ");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setText("Giá:");

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setText("Thành tiền:");

        tfSoLuong.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfSoLuongKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(cbLoaiNuoc, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbxNuoc, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbGia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tfSoLuong))
                        .addGap(18, 18, 18)
                        .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(57, 57, 57))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbThanhTien, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbLoaiNuoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbxNuoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tfSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbGia)
                    .addComponent(lbThanhTien)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel7.setText("Bàn số:");

        jLabel8.setFont(new java.awt.Font("Tahoma", 3, 15)); // NOI18N
        jLabel8.setText("Họ tên nhân viên:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 3, 15)); // NOI18N
        jLabel6.setText("Ngày:");

        jLabel9.setFont(new java.awt.Font("Tahoma", 3, 15)); // NOI18N
        jLabel9.setText("Thời gian:");

        lbNhanVien.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        lbNhanVien.setText("name");

        lblDate.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        lblDate.setText("day");

        lblTime.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        lblTime.setText("time");

        lbBan.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbBan.setText("0");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbNhanVien, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTime, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel6)
                            .addComponent(jLabel9))
                        .addGap(0, 57, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbBan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(lbBan))
                .addGap(34, 34, 34)
                .addComponent(jLabel8)
                .addGap(16, 16, 16)
                .addComponent(lbNhanVien)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addComponent(lblDate)
                .addGap(7, 7, 7)
                .addComponent(jLabel9)
                .addGap(18, 18, 18)
                .addComponent(lblTime)
                .addContainerGap(93, Short.MAX_VALUE))
        );

        tfPay.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tfPay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Photos/pay (1).png"))); // NOI18N
        tfPay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfPayActionPerformed(evt);
            }
        });

        tfTienNhanCuaKach.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        tfTienNhanCuaKach.setText("0");
        tfTienNhanCuaKach.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfTienNhanCuaKachKeyReleased(evt);
            }
        });

        btnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Photos/printer.png"))); // NOI18N
        btnPrint.setEnabled(false);
        btnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Tổng tiền:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Tiền nhận của khách:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Tiền thừa:");

        lbTienthua.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbTienthua.setText("0 VNĐ");

        btnHome.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Photos/smart-home (1).png"))); // NOI18N
        btnHome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHomeActionPerformed(evt);
            }
        });

        lbTongTien.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbTongTien.setText("0 VNĐ");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lbTienthua, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
                            .addComponent(tfTienNhanCuaKach)
                            .addComponent(lbTongTien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfPay, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPrint)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnHome)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(lbTongTien))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(tfTienNhanCuaKach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(lbTienthua))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(tfPay, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnHome, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnPrint, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(320, 320, 320))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel14))
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 657, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblStatus)))
                .addGap(7, 7, 7))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbLoaiNuocPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cbLoaiNuocPopupMenuWillBecomeInvisible
        cbxNuoc.removeAllItems();
        String sql = "SELECT * FROM QLNuoc WHERE loaiNuoc=N'" + cbLoaiNuoc.getSelectedItem().toString() + "'";
        try {

            rs = SQL.excuteQuery(conn, sql);
            while (rs.next()) {
                this.cbxNuoc.addItem(rs.getString("tenNuoc").trim());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (cbxNuoc.getItemCount() == 0) {
            cbxNuoc.setEnabled(false);
            tfSoLuong.setEnabled(false);
            lbGia.setText("0 VNĐ");
            tfSoLuong.setText("");
            lbThanhTien.setText("0 VNĐ");
        } else {
            cbxNuoc.setEnabled(true);
            lbGia.setText("0 VNĐ");
            tfSoLuong.setText("");
            lbThanhTien.setText("0 VNĐ");
        }
    }//GEN-LAST:event_cbLoaiNuocPopupMenuWillBecomeInvisible

    private void cbxNuocPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cbxNuocPopupMenuWillBecomeInvisible
        String sql = "SELECT * FROM QLNuoc WHERE tenNuoc=N'" + cbxNuoc.getSelectedItem() + "'";
        try {

            rs = SQL.excuteQuery(conn, sql);
            if (rs.next()) {
                lbGia.setText(rs.getString("giaBan").trim());
                tfSoLuong.setEnabled(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        checkSoLuongHang();
    }//GEN-LAST:event_cbxNuocPopupMenuWillBecomeInvisible

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        addThucUong();
        consistency();
        tinhTongTien();
    }//GEN-LAST:event_btnAddActionPerformed

    private void tfSoLuongKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfSoLuongKeyReleased
        DecimalFormat formatter = new DecimalFormat("###,###,###");

        tfSoLuong.setText(cutChar(tfSoLuong.getText()));

        if (tfSoLuong.getText().equals("")) {
            String[] s = lbGia.getText().split("\\s");
            lbThanhTien.setText("0" + " " + s[1]);
            btnAdd.setEnabled(false);
        } else {
            //tfSoLuong.setText(cutChar(tfSoLuong.getText()));

            String sqlCheck = "SELECT * FROM QLNuoc WHERE tenNuoc=N'" + cbxNuoc.getSelectedItem() + "'";
            try {

                rs = SQL.excuteQuery(conn, sqlCheck);

                while (rs.next()) {
                    if ((rs.getInt("soLuong") - Integer.parseInt(tfSoLuong.getText())) < 0) {
                        String[] s = lbGia.getText().split("\\s");
                        lbThanhTien.setText("0" + " " + s[1]);

                        lblStatus.setText("Số lượng sản phẩm bán không được vượt quá số lượng hàng trong kho!!");
                        btnAdd.setEnabled(false);
                    } else {
                        int soluong = Integer.parseInt(tfSoLuong.getText().toString());
                        String[] s = lbGia.getText().split("\\s");
                        lbThanhTien.setText(formatter.format(convertedToNumbers(s[0]) * soluong) + " " + s[1]);

                        lblStatus.setText("Số lượng sản phẩm bán hợp lệ!!");
                        btnAdd.setEnabled(true);
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }//GEN-LAST:event_tfSoLuongKeyReleased

    private void tfPayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfPayActionPerformed
        deleteHoaDon();
        deleteThongTinHoaDon();
        if (Pay == true) {
            String sql = "SELECT * FROM BanHang WHERE ban=" + lbBan.getText();
            Disabled();
            String[] s = lbTongTien.getText().split("\\s");
            String sqlHoaDon = "INSERT INTO ThongTinHoaDon (ban,tongTien,tienKH,tienThua,tenNV,ngay,thoiGian) VALUES(" + lbBan.getText() + ",N'" + lbTongTien.getText() + "',N'" + (tfTienNhanCuaKach.getText() + " " + s[1]) + "',N'" + lbTienthua.getText() + "',N'" + lbNhanVien.getText() + "','" + lblDate.getText() + "','" + lblTime.getText() + "')";

            SQL.excuteUpdata(conn, sqlHoaDon);

            addHoaDon();
            luuThongKe();
            Delete();
            loadData(sql);
            setIcon(lbBan.getText());
            lblStatus.setText("Thực hiện thanh toán bàn " + lbBan.getText() + " thành công!");
            Refresh();

            btnPrint.setEnabled(true);
            btnAdd.setEnabled(false);
            tfPay.setEnabled(false);
            tfTienNhanCuaKach.setEnabled(false);
        } else if (Pay == false) {
            JOptionPane.showMessageDialog(null, "Bạn cần nhập số tiền khách hàng thanh toán !");
        }
    }//GEN-LAST:event_tfPayActionPerformed

    private void tfTienNhanCuaKachKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfTienNhanCuaKachKeyReleased
        DecimalFormat formatter = new DecimalFormat("###,###,###");

        tfTienNhanCuaKach.setText(cutChar(tfTienNhanCuaKach.getText()));

        if (tfTienNhanCuaKach.getText().equals("")) {
            String[] s = lbTongTien.getText().split("\\s");
            lbTienthua.setText("0" + " " + s[1]);
        } else {
            tfTienNhanCuaKach.setText(formatter.format(convertedToNumbers(tfTienNhanCuaKach.getText())));

            String s1 = tfTienNhanCuaKach.getText();
            String[] s2 = lbTongTien.getText().split("\\s");

            if ((convertedToNumbers(s1) - convertedToNumbers(s2[0])) >= 0) {
                lbTienthua.setText(formatter.format((convertedToNumbers(s1) - convertedToNumbers(s2[0]))) + " " + s2[1]);
                lblStatus.setText("Số tiền khách hàng đưa đã hợp lệ!");
                Pay = true;
            } else {

                lbTienthua.setText(formatter.format((convertedToNumbers(s1) - convertedToNumbers(s2[0]))) + " " + s2[1]);
                lblStatus.setText("Số tiền khách hàng đưa nhỏ hơn tổng tiền mua hàng trong hóa đơn!");
                Pay = false;
            }
        }
    }//GEN-LAST:event_tfTienNhanCuaKachKeyReleased

    private void btnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintActionPerformed
//        try {
//            JasperReport report = JasperCompileManager.compileReport("C:\\Users\\D.Thanh Trung\\Documents\\NetBeansProjects\\QLCF\\src\\qlcf\\HoaDon.jrxml");
//
//            JasperPrint print = JasperFillManager.fillReport(report, null, conn);
//
//            JasperViewer.viewReport(print, false);
//        } catch (JRException ex) {
//            ex.printStackTrace();
//        }
    }//GEN-LAST:event_btnPrintActionPerformed

    private void btnHomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHomeActionPerformed
        MainFr home = new MainFr();
        this.setVisible(false);
        home.setVisible(true);
    }//GEN-LAST:event_btnHomeActionPerformed

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
            java.util.logging.Logger.getLogger(BanCf.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BanCf.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BanCf.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BanCf.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BanCf().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelBan;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnHome;
    private javax.swing.JButton btnPrint;
    private javax.swing.JComboBox<String> cbLoaiNuoc;
    private javax.swing.JComboBox<String> cbxNuoc;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbBan;
    private javax.swing.JLabel lbGia;
    private javax.swing.JLabel lbNhanVien;
    private javax.swing.JLabel lbThanhTien;
    private javax.swing.JLabel lbTienthua;
    private javax.swing.JLabel lbTongTien;
    private javax.swing.JLabel lblDate;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JLabel lblTime;
    private javax.swing.JTable tableHoaDon;
    private javax.swing.JButton tfPay;
    private javax.swing.JTextField tfSoLuong;
    private javax.swing.JTextField tfTienNhanCuaKach;
    // End of variables declaration//GEN-END:variables
}
