package ddwucom.mobile.week02.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

        Log.d(TAG,"logcat을 사용해보는 실습");

//        TextView myText = new TextView(this);
//        myText.setText(R.string.sayHi);
//        setContentView(myText);

    }
}