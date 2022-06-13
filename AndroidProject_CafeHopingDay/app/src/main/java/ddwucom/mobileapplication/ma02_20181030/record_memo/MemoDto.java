package ddwucom.mobileapplication.ma02_20181030.record_memo;

public class MemoDto {

    private long _id;
    private String photoPath;
    private String memo;
    private String date;
    private String cafeName;
    private String location;
    private String ratingbar;

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getDate() {return date;}

    public String getCafeName() {return cafeName;}

    public void setCafeName(String cafeName) {this.cafeName = cafeName; }

    public String getLocation() {return location; }

    public void setLocation(String location) {this.location = location; }

    public String getRatingbar() {return ratingbar; }

    public void setRatingbar(String ratingbar) {this.ratingbar = ratingbar; }

    public void setDate(String date) {this.date = date; }

    @Override
    public String toString() {
        return new String(_id + " : " + photoPath);
    }
}
