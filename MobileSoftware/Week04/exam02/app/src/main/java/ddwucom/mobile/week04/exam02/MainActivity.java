package ddwucom.mobile.week04.exam02;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    final static String TAG = "MainActivity";
    EditText text;
    int num1, num2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = findViewById(R.id.etDisplay);
    }
    public void onClick(View v){

        String num = text.getText().toString();
        switch(v.getId()){
            case R.id.btn_1:
                text.setText(num + "1");
                break;
            case R.id.btn_2:
                text.setText(num + "2");
                break;
            case R.id.btn_plus:
                num1 = Integer.parseInt(num);
//                Log.d(TAG,String.valueOf(num1));
                text.setText("");
                break;
            case R.id.btn_equal:
                num2 = Integer.parseInt(num);
                String answer = String.valueOf(num1+num2);
//                Log.d(TAG,answer);
                text.setText(answer);
                //text부분은 문자열을 입력해야하기 때문에 int는 들어갈 수 없음
        }
    }
}