package ddwucom.mobile.week06.exam01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.jar.Attributes;

//
public class MainActivity extends AppCompatActivity {

    Button button;
    EditText editText;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        button = findViewById(R.id.btnDisplay);
        textView = findViewById(R.id.tvDisplay);

//        //(방법-2.1)
//        MyButton myButton = new MyButton();
//        button.setOnClickListener(myButton);

        //(방법-2.4)
//        button.setOnClickListener(myClickListner);

        //(방법-2.5)
        //객체 이름 없이 인터페이스 구현 및 등록
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                textView.setText(editText.getText().toString());
            }
        });

    }

    //익명 내부 클래스 구현으로 작성(2.4방법)
    //별도의 클래스 없이 인터페이스에서 직접 객체 생성
    View.OnClickListener myClickListner = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            textView.setText(editText.getText().toString());
        }
    };

    //별도의 리스너 인터페이스 구현 클래스 작성 (방법-2.1)
    class MyButton implements View.OnClickListener{

        @Override
        public void onClick(View v) {

            textView.setText(editText.getText().toString());
        }
    }
}