package ddwucom.mobile.week12.activitytest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SubActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub2);
    }

    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.buttonOk:
                Intent resultIntent = new Intent();
                resultIntent.putExtra("result_data", "good");
                //데이터를 돌려주기 위해서 resultCode를 돌려준다
                setResult(RESULT_OK, resultIntent);
                break;
            case R.id.buttonCancel:
                setResult(RESULT_CANCELED);
                break;
        }
        finish();
    }
}