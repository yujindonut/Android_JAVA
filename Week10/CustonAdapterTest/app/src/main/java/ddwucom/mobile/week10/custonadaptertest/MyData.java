package ddwucom.mobile.week10.custonadaptertest;

public class MyData {

    private int _id;
    private String name;
    private String phone;

    public int get_id() {
        return _id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public MyData(int _id, String name, String phone) {
        this._id = _id;
        this.name = name;
        this.phone = phone;
    }
}
