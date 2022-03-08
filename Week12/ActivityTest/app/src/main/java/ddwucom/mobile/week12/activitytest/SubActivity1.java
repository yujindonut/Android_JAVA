package ddwucom.mobile.week12.activitytest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SubActivity1 extends AppCompatActivity {

    final static String TAG = "SubActivity1";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub1);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        ArrayList<String> foods = (ArrayList<String>) intent.getSerializableExtra("foods");

        Log.d(TAG,"id: " + id);
        Log.d(TAG, foods.get(0));

    }
}
