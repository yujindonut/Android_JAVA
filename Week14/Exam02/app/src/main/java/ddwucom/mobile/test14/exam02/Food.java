package ddwucom.mobile.test14.exam02;

import java.io.Serializable;

public class Food implements Serializable{
    long _id;
    String food;
    String nation;

    public Food(String food, String nation) {
        this.food = food;
        this.nation = nation;
    }

    public Food(long _id, String food, String nation) {
        this._id = _id;
        this.food = food;
        this.nation = nation;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getFood() {
        return food;
    }

    public String getNation() {
        return nation;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(_id);
        sb.append(".\t\t");
        sb.append(food);
        sb.append("\t\t\t(");
        sb.append(nation);
        sb.append(")");
        return sb.toString();
    }

}
