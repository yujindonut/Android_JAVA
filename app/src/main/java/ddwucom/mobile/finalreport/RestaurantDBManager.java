package ddwucom.mobile.finalreport;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

public class RestaurantDBManager {

    public static String TAG = "RestauratDBManager";
    RestaurantDBHelper restaurantDBHelper;
    ArrayList<Restaurant> restaurantList;

    public RestaurantDBManager(Context context) {
        restaurantDBHelper = new RestaurantDBHelper(context);
    }

    //  전체데이터 출력
    public ArrayList<Restaurant> getAllRestaurant() {
        restaurantList = new ArrayList<>();
        SQLiteDatabase db = restaurantDBHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + RestaurantDBHelper.TABLE_NAME, null);
        while (cursor.moveToNext()) {
            long id = cursor.getInt(cursor.getColumnIndex(RestaurantDBHelper.COL_ID));
            Log.d("RestauratDBManager", String.valueOf(id));
            String signatureMenu = cursor.getString(cursor.getColumnIndex(RestaurantDBHelper.COL_SIGNATUREMENU));
            String restaurantName = cursor.getString(cursor.getColumnIndex(RestaurantDBHelper.COL_RESTAURANT));
            String ratingBar = cursor.getString(cursor.getColumnIndex(RestaurantDBHelper.COL_RATINGBAR));
            String price = cursor.getString(cursor.getColumnIndex(RestaurantDBHelper.COL_PRICE));
            String location = cursor.getString(cursor.getColumnIndex(RestaurantDBHelper.COL_LOCATION));
            restaurantList.add(new Restaurant(id, signatureMenu, restaurantName, ratingBar, price, location));
        }
        restaurantDBHelper.close();
        cursor.close();
        return restaurantList;
    }

    //    DB에 새로운 food 추가
    public boolean addNewRestaurant(Restaurant newRestaurant) {
        SQLiteDatabase db = restaurantDBHelper.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(RestaurantDBHelper.COL_SIGNATUREMENU, newRestaurant.getSignatureMenu());
        value.put(RestaurantDBHelper.COL_RESTAURANT, newRestaurant.getRestaurantName());
        value.put(RestaurantDBHelper.COL_RATINGBAR, newRestaurant.getRatingBar());
        value.put(RestaurantDBHelper.COL_PRICE, newRestaurant.getPrice());
        value.put(RestaurantDBHelper.COL_LOCATION, newRestaurant.getLocation());
        //정상적으로 insert가 이루어지면 1이상 반환
        long count = db.insert(RestaurantDBHelper.TABLE_NAME, null, value);
        restaurantDBHelper.close();
        if (count > 0) return true;
        return false;
    }

    public boolean modifyRestaurant(Restaurant restaurant) {

        SQLiteDatabase sqLiteDatabase = restaurantDBHelper.getWritableDatabase();

        ContentValues row = new ContentValues();
        row.put(RestaurantDBHelper.COL_SIGNATUREMENU, restaurant.getSignatureMenu());
        row.put(RestaurantDBHelper.COL_RESTAURANT, restaurant.getRestaurantName());
        row.put(RestaurantDBHelper.COL_RATINGBAR, restaurant.getRatingBar());
        row.put(RestaurantDBHelper.COL_PRICE, restaurant.getPrice());
        row.put(RestaurantDBHelper.COL_LOCATION, restaurant.getLocation());

        String whereClause = RestaurantDBHelper.COL_ID + "=?";
        String[] whereArgs = new String[]{String.valueOf(restaurant.get_id())};

        int result = sqLiteDatabase.update(RestaurantDBHelper.TABLE_NAME, row, whereClause, whereArgs);
        restaurantDBHelper.close();
        if (result > 0) return true;
        return true;
    }

    //    _id 를 기준으로 DB에서 food 삭제
    public boolean removeRestaurant(long id) {
        SQLiteDatabase sqLiteDatabase = restaurantDBHelper.getWritableDatabase();

        String whereClause = restaurantDBHelper.COL_ID + "=?";
        String[] whereArgs = new String[]{String.valueOf(id)};

        int result = sqLiteDatabase.delete(RestaurantDBHelper.TABLE_NAME, whereClause, whereArgs);
        restaurantDBHelper.close();
        if (result > 0)
            return true;
        return false;
    }

    public Restaurant getRestaurant(String searchName) {

        Restaurant restaurant = null;
        SQLiteDatabase myDB = restaurantDBHelper.getReadableDatabase(); //select
        String selection = "restaurantName=?";
        String[] selectArgs = new String[]{searchName};
        Cursor cursor =
                myDB.query(RestaurantDBHelper.TABLE_NAME, null, selection, selectArgs,
                        null, null, null, null);
        if (cursor.getCount() > 0) {
            long id;
            String signatureMenu = null;
            String restaurantName = null;
            String ratingBar = null;
            String price = null;
            String location = null;
            while (cursor.moveToNext()) {
                id = cursor.getInt(cursor.getColumnIndex(RestaurantDBHelper.COL_ID));
                Log.d(TAG, String.valueOf(id));
                signatureMenu = cursor.getString(cursor.getColumnIndex(RestaurantDBHelper.COL_SIGNATUREMENU));
                restaurantName = cursor.getString(cursor.getColumnIndex(RestaurantDBHelper.COL_RESTAURANT));
                ratingBar = cursor.getString(cursor.getColumnIndex(RestaurantDBHelper.COL_RATINGBAR));
                price = cursor.getString(cursor.getColumnIndex(RestaurantDBHelper.COL_PRICE));
                location = cursor.getString(cursor.getColumnIndex(RestaurantDBHelper.COL_LOCATION));
                restaurant = new Restaurant(id, signatureMenu, restaurantName, ratingBar, price, location);
            }
            cursor.close();
            restaurantDBHelper.close();
            return restaurant;
        }
        cursor.close();
        restaurantDBHelper.close();
        return null;
    }
}
