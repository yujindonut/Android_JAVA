package ddwucom.mobile.week10.exam01;

public class MyData {
    private int _id;
    private String address;
    private String detailAddress;
    private String state;

    public MyData(int _id, String address, String detailAddress, String state) {
        this._id = _id;
        this.address = address;
        this.detailAddress = detailAddress;
        this.state = state;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

}
