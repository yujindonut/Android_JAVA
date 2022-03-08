package ddwucom.mobile.finalreport;

import android.widget.RatingBar;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Restaurant implements Serializable {

    long _id;
    String signatureMenu;
    String restaurantName;
    String ratingBar;
    String price;
    String location;

    public Restaurant( String signatureMenu, String restaurantName, String ratingBar, String price, String location){
        this.signatureMenu = signatureMenu;
        this.restaurantName = restaurantName;
        this.ratingBar = ratingBar;
        this.price = price;
        this.location = location;
    }
    public Restaurant(long _id, String signatureMenu, String restaurantName, String ratingBar, String price, String location){
        this._id = _id;
        this.signatureMenu = signatureMenu;
        this.restaurantName = restaurantName;
        this.ratingBar = ratingBar;
        this.price = price;
        this.location = location;
    }

    public long get_id() { return _id;}

    public String getSignatureMenu() {return signatureMenu; }

    public void setSignatureMenu(String signatureMenu) {this.signatureMenu = signatureMenu;}

    public String getRatingBar() {return ratingBar;}

    public void setRatingBar(String ratingBar) { this.ratingBar = ratingBar; }

    public String getRestaurantName() {return restaurantName;}

    public void setRestaurantName(String restaurantName) {this.restaurantName = restaurantName;}

    public String getPrice() {return price;}

    public void setPrice(String price) {this.price = price;}

    public String getLocation() {return location;}

    public void setLocation(String location) {this.location = location;}

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(signatureMenu);
        sb.append("\t\t\t(");
        sb.append(restaurantName);
        sb.append(",\t\t");
        sb.append(ratingBar);
        sb.append(",\t\t");
        sb.append(price);
        sb.append(",\t\t");
        sb.append(location);
        sb.append(")");
        return sb.toString();
    }
}
