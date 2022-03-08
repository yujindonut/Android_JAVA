package ddwucom.mobile.week04.exam01;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    LinearLayout layout; //클래스의 멤버 변수로 만들어놓음
    //layout객체를 viewGroup으로 설정해놓음
    //매번 찾아오지 않고 앱을 실행할때만 liner_layout을 생성함
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
//        layout = findViewById(R.id.linearLayout);
        //매번 찾아오지 않고 앱을 실행할때만 linear_layout을 연결해줌

        layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setBackgroundColor(Color.LTGRAY);

        TextView text = new TextView(this);
        text.setText("textView");
        text.setGravity(Gravity.CENTER);
        text.setTextColor(Color.RED);
        text.setTextSize(20);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT );
        layout.addView(text,params);

        setContentView(layout);
        //나중의 setContentView로 씌어지게됨
    }

    public void onClick (View v) {
//        LinearLayout layout = findViewById(R.id.linearLayout);
        //매번 findViewById를 통해서 매번 찾아오는 것 = 성능에 영향을 줄 수도

        switch(v.getId()) {
            case R.id.btnVertical:
                layout.setOrientation(LinearLayout.VERTICAL);
                break;
            case R.id.btnHorizontal:
                layout.setOrientation(LinearLayout.HORIZONTAL);
                break;
        }
    }
}