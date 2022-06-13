package mobile.example.alarmtest;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class NotiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noti);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                finish();
                break;
        }
    }
}
