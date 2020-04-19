package dangthuongngo.com.model;

public class NhanVien {
    private String ma;
    private String ten;
    private boolean gioiTinh;

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public boolean isGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public NhanVien(String ma, String ten, boolean gioiTinh) {
        this.ma = ma;
        this.ten = ten;
        this.gioiTinh = gioiTinh;
    }

    public NhanVien() {
    }
}
