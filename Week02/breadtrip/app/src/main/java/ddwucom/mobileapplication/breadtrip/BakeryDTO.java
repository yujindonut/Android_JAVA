package ddwucom.mobileapplication.breadtrip;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class BakeryDTO implements Serializable {

    private long id;
    private String bakery;
    private String location;
    private String rate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBakery() {
        return bakery;
    }

    public void setBakery(String bakery) {
        this.bakery = bakery;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRate() {return rate;}

    public void setRate(String rate) {
        this.rate = rate;
    }

    @NonNull
    @Override
    public String toString() {return id + ". " + bakery + " - " + location + " (" + rate + ")"; }
}
