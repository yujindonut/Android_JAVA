package ddwucom.mobile.week13.dbtest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    FoodDBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //바로 생성자가 생기면서 DB파일이 만들어짐
//        dbHelper = new FoodDBHelper(this);
//        //수행하는 순간 table이 만들어지게 됨
//        dbHelper.getReadableDatabase();
    }
}