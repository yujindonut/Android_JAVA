package ddwucom.mobile.week12.exam01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class SubActivity extends AppCompatActivity {

    private static final String TAG = "SubActivity";
    String data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        Intent intent = getIntent();
        data = intent.getStringExtra("message");
        EditText message = findViewById(R.id.text_sub);
        message.setText(data);
//        Log.d(TAG,data)
    }
    public void onClick(View v){
        final EditText message = findViewById(R.id.text_sub);
        switch(v.getId()) {
            case R.id.btn_close:
                finish();
        }
    }
}