package ddwucom.mobile.week06.exam02;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.graphics.Color;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    MyView myView;
    Layout linear =

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myView = (MyView) findViewById(R.id.myView);

//        익명 내부 클래스의 임시객체 구현
        //이벤트 핸들러
        //인터페이스 구현 클래스 작성
        //이벤트리스너
        myView.setOnTouchListener( new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                myView.setPosX(event.getX());
                myView.setPosY(event.getY());

                myView.invalidate();
                return false;
            }
        });
//        롱클릭할 경우 원의 색상이 바뀌는 기능
//        익명 내부 클래스의 임시 객체 구현 방법으로
        myView.setOnLongClickListener( new View.OnLongClickListener(){

            @Override
            public boolean onLongClick(View v) {

                Random random = new Random();

                int r = random.nextInt();
                int g = random.nextInt();
                int b = random.nextInt();

                myView.setColor(Color.rgb(r,g,b));
                myView.invalidate();
                return true;
            }
        });
     }
}
