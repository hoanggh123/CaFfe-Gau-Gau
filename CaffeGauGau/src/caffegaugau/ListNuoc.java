/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package caffegaugau;

import java.io.Serializable;

/**
 *
 * @author dhhoa
 */
public class ListNuoc implements Serializable {

    public String MaTU, LoaiTU, TenTU, DonVi;
    public int SoLuong, GiaBan;

    public ListNuoc() {
    }

    public ListNuoc(String LoaiTU, String TenTU, String DonVi, String MaTU, int SoLuong, int GiaBan) {
        this.LoaiTU = LoaiTU;
        this.TenTU = TenTU;
        this.DonVi = DonVi;
        this.MaTU = MaTU;
        this.SoLuong = SoLuong;
        this.GiaBan = GiaBan;
    }

    public String getMaTU() {
        return MaTU;
    }

    public void setMaTU(String MaTU) {
        this.MaTU = MaTU;
    }

    public String getLoaiTU() {
        return LoaiTU;
    }

    public void setLoaiTU(String LoaiTU) {
        this.LoaiTU = LoaiTU;
    }

    public String getTenTU() {
        return TenTU;
    }

    public void setTenTU(String TenTU) {
        this.TenTU = TenTU;
    }

    public String getDonVi() {
        return DonVi;
    }

    public void setDonVi(String DonVi) {
        this.DonVi = DonVi;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int SoLuong) {
        this.SoLuong = SoLuong;
    }

    public int getGiaBan() {
        return GiaBan;
    }

    public void setGiaBan(int GiaBan) {
        this.GiaBan = GiaBan;
    }
}
