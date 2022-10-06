/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package caffegaugau;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author dhhoa
 */
public class thongke extends javax.swing.JFrame {

    int click = 0;
    private DefaultTableModel model;
    String sql;
    private ResultSet rs = null;
    Statement st;
    Connection cnn;
    String user = "sa";
    String pass = "songlong"; // thay doi pass cho phu hop
    String url = "jdbc:sqlserver://localhost;databaseName=QuanCaPhe";
    private boolean leapYear = false, Year = false, Month = false, Day = false;

    public thongke() {
        initComponents();
        load();
        checkYear();

        addDay();
        Refresh();
    }

    private void load() {
        int count = 0;
        long tongTien = 0;
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        try {
            String[] arry = {"Bàn", "Tổng tiền", "Tiền nhận của khách", "Tiền thừa", "Tên nhân viên", "Ngày Bán", "Thời gian"};
            DefaultTableModel model = new DefaultTableModel(arry, 0);

            model = new DefaultTableModel(arry, 0);

            cnn = DriverManager.getConnection(url, user, pass);
            Statement st = cnn.createStatement();
            sql = "Select * from ThongKe";
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                Vector vector = new Vector();
                vector.add(rs.getInt("ban"));
                vector.add(rs.getString("tongTien").trim());
                vector.add(rs.getString("tienKH").trim());
                vector.add(rs.getString("tienThua").trim());
                vector.add(rs.getString("tenNV").trim());
                vector.add(rs.getString("ngay").trim());
                vector.add(rs.getString("thoiGian").trim());
                model.addRow(vector);
                String[] s = rs.getString("tongTien").trim().split("\\s");
                tongTien = convertedToNumbers(s[0]) + tongTien;
                count++;
            }
            tableThongKe.setModel(model);
            st.close();
            cnn.close();
            rs.close();
            lblSoHoaDon.setText(String.valueOf(count));
            lblTongDoanhThu.setText(formatter.format(tongTien) + " " + "VND");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private long convertedToNumbers(String s) {
        String number = "";
        String[] array = s.replace(",", " ").split("\\s");
        for (String i : array) {
            number = number.concat(i);
        }
        return Long.parseLong(number);
    }

    private void checkYear() {
        if (Double.parseDouble(String.valueOf(cbxYear.getValue())) % 4 == 0 && Double.parseDouble(String.valueOf(cbxYear.getValue())) % 100 != 0 || Double.parseDouble(String.valueOf(cbxYear.getValue())) % 400 == 0) {
            leapYear = true;
        } else {
            leapYear = false;
        }
    }

    private void Refresh() {
        Year = false;
        Month = false;
        Day = false;
        cbxDay.setEnabled(false);
        cbxMonth.setEnabled(false);
        cbxYear.setEnabled(false);
        cbxMonth.setSelectedIndex(0);
        cbxDay.setSelectedIndex(0);
    }

    private void checkOption() {
        Refresh();
        if (radXemnam.isSelected()) {
            cbxYear.setEnabled(true);
            cbxDay.setEnabled(false);
            cbxMonth.setEnabled(false);
            Year = true;
        } else if (radXemthang.isSelected()) {
            cbxMonth.setEnabled(true);
            cbxYear.setEnabled(false);
            cbxDay.setEnabled(false);
            Month = true;
        } else if (radXemngay.isSelected()) {
            cbxDay.setEnabled(true);
            cbxMonth.setEnabled(false);
            cbxYear.setEnabled(false);
            Day = true;
        }
    }

    private void addDay() {
        cbxDay.setEnabled(true);
        String[] day = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
        switch (Integer.parseInt(cbxMonth.getSelectedItem().toString())) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                cbxDay.removeAllItems();
                for (String i : day) {
                    cbxDay.addItem(i);
                }
                break;

            case 4:
            case 6:
            case 9:
            case 11:
                cbxDay.removeAllItems();
                for (int i = 0; i < day.length - 1; i++) {
                    cbxDay.addItem(day[i]);
                }
                break;

            case 2:
                cbxDay.removeAllItems();
                if (leapYear == true) {
                    for (int i = 0; i < day.length - 2; i++) {
                        cbxDay.addItem(day[i]);
                    }
                } else {
                    for (int i = 0; i < day.length - 3; i++) {
                        cbxDay.addItem(day[i]);
                    }
                }
                break;
        }
    }

    private double getDay(String s) {
        String[] arry = s.replace("/", " ").split("\\s");
        return Double.parseDouble(arry[arry.length - 3]);
    }

    private double getMonth(String s) {
        String[] arry = s.replace("/", " ").split("\\s");
        return Double.parseDouble(arry[arry.length - 2]);
    }

    private double getYear(String s) {
        String[] arry = s.replace("/", " ").split("\\s");
        return Double.parseDouble(arry[arry.length - 1]);
    }

    private void FindDay() {
        int count = 0;
        long tongTien = 0;
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        try {
            String[] arry = {"Bàn", "Tổng tiền", "Tiền nhận của khách", "Tiền thừa", "Tên nhân viên", "Ngày Bán", "Thời gian"};
            DefaultTableModel modle = new DefaultTableModel(arry, 0);

            cnn = DriverManager.getConnection(url, user, pass);
            Statement st = cnn.createStatement();
            sql = "Select * from ThongKe";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                if (getDay(rs.getString("ngay")) == Double.parseDouble(cbxDay.getSelectedItem().toString()) && getMonth(rs.getString("ngay")) == Double.parseDouble(cbxMonth.getSelectedItem().toString()) && getYear(rs.getString("ngay")) == Double.parseDouble(cbxYear.getValue().toString())) {
                    Vector vector = new Vector();
                    vector.add(rs.getInt("ban"));
                    vector.add(rs.getString("tongTien").trim());
                    vector.add(rs.getString("tienKH").trim());
                    vector.add(rs.getString("tienThua").trim());
                    vector.add(rs.getString("tenNV").trim());
                    vector.add(rs.getString("ngay").trim());
                    vector.add(rs.getString("thoiGian").trim());
                    modle.addRow(vector);
                    String[] s = rs.getString("tongTien").trim().split("\\s");
                    tongTien = convertedToNumbers(s[0]) + tongTien;
                    count++;
                }
            }
            tableThongKe.setModel(modle);
            lblSoHoaDon.setText(String.valueOf(count));
            lblTongDoanhThu.setText(formatter.format(tongTien) + " " + "VND");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void FindMonth() {
        int count = 0;
        long tongTien = 0;
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        try {
            String[] arry = {"Bàn", "Tổng tiền", "Tiền nhận của khách", "Tiền thừa", "Tên nhân viên", "Ngày Bán", "Thời gian"};
            DefaultTableModel modle = new DefaultTableModel(arry, 0);

            cnn = DriverManager.getConnection(url, user, pass);
            Statement st = cnn.createStatement();
            sql = "Select * from ThongKe";
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                if (getMonth(rs.getString("ngay")) == Double.parseDouble(cbxMonth.getSelectedItem().toString()) && getYear(rs.getString("ngay")) == Double.parseDouble(cbxYear.getValue().toString())) {
                    Vector vector = new Vector();
                    vector.add(rs.getInt("ban"));
                    vector.add(rs.getString("tongTien").trim());
                    vector.add(rs.getString("tienKH").trim());
                    vector.add(rs.getString("tienThua").trim());
                    vector.add(rs.getString("tenNV").trim());
                    vector.add(rs.getString("ngay").trim());
                    vector.add(rs.getString("thoiGian").trim());
                    modle.addRow(vector);
                    String[] s = rs.getString("tongTien").trim().split("\\s");
                    tongTien = convertedToNumbers(s[0]) + tongTien;
                    count++;
                }
            }
            tableThongKe.setModel(modle);
            lblSoHoaDon.setText(String.valueOf(count));
            lblTongDoanhThu.setText(formatter.format(tongTien) + " " + "VND");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void FindYear() {
        int count = 0;
        long tongTien = 0;
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        try {
            String[] arry = {"Bàn", "Tổng tiền", "Tiền nhận của khách", "Tiền thừa", "Tên nhân viên", "Ngày Bán", "Thời gian"};
            DefaultTableModel modle = new DefaultTableModel(arry, 0);

            cnn = DriverManager.getConnection(url, user, pass);
            Statement st = cnn.createStatement();
            sql = "Select * from ThongKe";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                if (getYear(rs.getString("ngay")) == Double.parseDouble(cbxYear.getValue().toString())) {
                    Vector vector = new Vector();
                    vector.add(rs.getInt("ban"));
                    vector.add(rs.getString("tongTien").trim());
                    vector.add(rs.getString("tienKH").trim());
                    vector.add(rs.getString("tienThua").trim());
                    vector.add(rs.getString("tenNV").trim());
                    vector.add(rs.getString("ngay").trim());
                    vector.add(rs.getString("thoiGian").trim());
                    modle.addRow(vector);
                    String[] s = rs.getString("tongTien").trim().split("\\s");
                    tongTien = convertedToNumbers(s[0]) + tongTien;
                    count++;
                }
            }
            tableThongKe.setModel(modle);
            lblSoHoaDon.setText(String.valueOf(count));
            lblTongDoanhThu.setText(formatter.format(tongTien) + " " + "VND");
        } catch (Exception ex) {
            ex.printStackTrace();
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        Jpanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableThongKe = new javax.swing.JTable();
        lbTrangthai = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        radXemngay = new javax.swing.JRadioButton();
        radXemthang = new javax.swing.JRadioButton();
        radXemnam = new javax.swing.JRadioButton();
        cbxDay = new javax.swing.JComboBox<>();
        cbxMonth = new javax.swing.JComboBox<>();
        btnBack = new javax.swing.JButton();
        btnFind = new javax.swing.JButton();
        cbxYear = new javax.swing.JSpinner();
        btnHome = new javax.swing.JButton();
        lblTongDoanhThu = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblSoHoaDon = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tableThongKe.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tableThongKe);

        javax.swing.GroupLayout JpanelLayout = new javax.swing.GroupLayout(Jpanel);
        Jpanel.setLayout(JpanelLayout);
        JpanelLayout.setHorizontalGroup(
            JpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JpanelLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 546, Short.MAX_VALUE)
                .addContainerGap())
        );
        JpanelLayout.setVerticalGroup(
            JpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JpanelLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        lbTrangthai.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbTrangthai.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbTrangthai.setText("Trạng thái");

        buttonGroup1.add(radXemngay);
        radXemngay.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        radXemngay.setText("Xem theo ngày");
        radXemngay.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                radXemngayItemStateChanged(evt);
            }
        });

        buttonGroup1.add(radXemthang);
        radXemthang.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        radXemthang.setText("Xem theo tháng");
        radXemthang.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                radXemthangItemStateChanged(evt);
            }
        });

        buttonGroup1.add(radXemnam);
        radXemnam.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        radXemnam.setText("Xem theo năm");
        radXemnam.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                radXemnamItemStateChanged(evt);
            }
        });

        cbxMonth.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));
        cbxMonth.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                cbxMonthPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        btnBack.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Photos/return.png"))); // NOI18N
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        btnFind.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnFind.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Photos/search.png"))); // NOI18N
        btnFind.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFindActionPerformed(evt);
            }
        });

        cbxYear.setEditor(new javax.swing.JSpinner.NumberEditor(cbxYear, "####"));

        btnHome.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btnHome.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Photos/smart-home (1).png"))); // NOI18N
        btnHome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHomeActionPerformed(evt);
            }
        });

        lblTongDoanhThu.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblTongDoanhThu.setText("0 VND");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("Tổng Số Hóa Đơn Bán Ra:");

        lblSoHoaDon.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblSoHoaDon.setText("0");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnBack)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnFind)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnHome))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblSoHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(cbxDay, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(radXemngay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(29, 29, 29)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(radXemthang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cbxMonth, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(radXemnam, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cbxYear))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblTongDoanhThu, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(radXemngay)
                    .addComponent(radXemthang)
                    .addComponent(radXemnam))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxDay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lblSoHoaDon)
                    .addComponent(lblTongDoanhThu))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnFind, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnHome, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnBack, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Jpanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbTrangthai, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(Jpanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbTrangthai, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(94, 94, 94))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnHomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHomeActionPerformed
        MainFr home = new MainFr();
        this.setVisible(false);
        home.setVisible(true);
    }//GEN-LAST:event_btnHomeActionPerformed

    private void radXemngayItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_radXemngayItemStateChanged
        checkOption();
    }//GEN-LAST:event_radXemngayItemStateChanged

    private void radXemthangItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_radXemthangItemStateChanged
        checkOption();
    }//GEN-LAST:event_radXemthangItemStateChanged

    private void radXemnamItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_radXemnamItemStateChanged
        checkOption();
    }//GEN-LAST:event_radXemnamItemStateChanged

    private void cbxMonthPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cbxMonthPopupMenuWillBecomeInvisible
        checkYear();
        if (Day == true)
            addDay();
        else
            return;
    }//GEN-LAST:event_cbxMonthPopupMenuWillBecomeInvisible

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        load();

    }//GEN-LAST:event_btnBackActionPerformed

    private void btnFindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFindActionPerformed
        if (Day == true)
            FindDay();
        else if (Month == true)
            FindMonth();
        else if (Year == true)
            FindYear();
        else
            lbTrangthai.setText("Bạn cần chọn phương thức tìm kiếm!");
    }//GEN-LAST:event_btnFindActionPerformed

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
            java.util.logging.Logger.getLogger(thongke.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(thongke.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(thongke.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(thongke.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new thongke().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Jpanel;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnFind;
    private javax.swing.JButton btnHome;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cbxDay;
    private javax.swing.JComboBox<String> cbxMonth;
    private javax.swing.JSpinner cbxYear;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbTrangthai;
    private javax.swing.JLabel lblSoHoaDon;
    private javax.swing.JLabel lblTongDoanhThu;
    private javax.swing.JRadioButton radXemnam;
    private javax.swing.JRadioButton radXemngay;
    private javax.swing.JRadioButton radXemthang;
    private javax.swing.JTable tableThongKe;
    // End of variables declaration//GEN-END:variables
}
