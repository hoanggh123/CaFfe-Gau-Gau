/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caffegaugau;

public class NhanVien {

    private String maNV;
    private String matKhau;
    private String hoTen;
    private Boolean taiKhoan = false;

    public NhanVien() {
    }

    @Override
    public String toString() {
        return this.hoTen;
    }

    public NhanVien(String matKhau, boolean taiKhoan) {

        this.matKhau = matKhau;
        this.taiKhoan = taiKhoan;
    }
    public String getMatKhau() {
        return matKhau;
    }
    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }
    public Boolean gettaiKhoan() {
        return taiKhoan;
    }
    public void setVaiTro(Boolean taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

}
