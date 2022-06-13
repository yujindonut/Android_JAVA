package ddwucom.mobile.week03.hw3.Profile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onClickHello(View v){

        EditText etName = findViewById(R.id.etName);
        EditText etPhone = findViewById(R.id.etPhone);

        String name = etName.getText().toString();
        String phone = etPhone.getText().toString();

        Log.d(TAG,name);

        String introduce = "안녕하세요. 저는 "+name+"입니다.\n전화번호는 "+phone+"입니다.";

        Toast.makeText(this,introduce,Toast.LENGTH_LONG).show();
    }
    public void onClickTurnOff(View v){
        finish();
    }
/*
    public void onClick(View v){
        switch(v.getId()){
            case R.id.btnHello:
                EditText etName = findViewById(R.id.etName);
                EditText etPhone = findViewById(R.id.etPhone);

                String etname = etName.getText().toString();
                String etphone = etPhone.getText().toString();
                break;
            case R.id.btnExit:
                finish();
                break;
        }
    }
 */
}