package ddwucom.mobile.finalreport;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class IntroduceMyself_Activity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduce_me);
    }
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_back:
                finish();
        }
    }
}