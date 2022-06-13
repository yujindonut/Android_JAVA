package ddwucom.mobile.week06.eventtest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button button1;
    Button button2;
    Button button3;
    Button button4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
//          뷰클래스 생성 후 리스너를 구현하는 방법
//        MyView view = new MyView(this);
//        view.setOnTouchListener(view);
//        setContentView(view);

        setContentView(R.layout.activity_main);

        ConstraintLayout layout = findViewById(R.id.layout);
        final TextView textView = findViewById(R.id.textView);

        layout.setOnTouchListener( new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
//                Toast.makeText(MainActivity.this,"Layout Touch!", Toast.LENGTH_SHORT);
                //터치를 누를 때
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    textView.setText("Layout Touch Down!!!");
                }
                //터치를 화면에서 뗏을때
                else if(event.getAction() == MotionEvent.ACTION_UP){
                    textView.setText("Layout Touch UP!!!");
                }
                return true;
            }
        } );

        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);

        //기본방법 - 클래스 생성 후 적용
        MyClick myClick = new MyClick();
        button1.setOnClickListener(myClick);
        
        //액티비티가 리스너를 구현
        button2.setOnClickListener(this);
        
//        익명클래스의 객체 사용
        button3.setOnClickListener(myClickListner);

//        익명 클래스의 임시 객체 사용
        button4.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"다섯번째 익명 클래스 임시 객체 방식!!",Toast.LENGTH_LONG).show();
            }
        } );
    }

    public void onMyClick(View v) {
        Toast.makeText(MainActivity.this, "위젯 방식~", Toast.LENGTH_SHORT).show();
    }

    //원래는 interface여서 객체를 못만드는데, 객체를 만들 수 있도록 onClick메소드를 만들어 내용을 채워줌
    //인터페이스 객체를 보관하기 위해서 myClickListner안에 저 onClick메소드 + 인터페이스 객체 생성
    View.OnClickListener myClickListner = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Toast.makeText(MainActivity.this,"네번째 익명 클래스 방식!!",Toast.LENGTH_LONG).show();
        }
    };

    class MyView extends View implements View.OnTouchListener{

        public MyView(Context context){
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            canvas.drawColor(Color.YELLOW);
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            Toast.makeText(MainActivity.this,"세번째방식!!",Toast.LENGTH_LONG).show();
            return true;
        }
    }

    @Override
    public void onClick(View v) { //Activity가 OnclickListener의 역할도 하게된다
        Toast.makeText(MainActivity.this,"두번째 Activity 방식!!",Toast.LENGTH_LONG).show();
    }

    //MainActivity의 내부 class로 선언한 것
    class MyClick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
//            Toast.makeText(this,"첫번째방식!",Toast.LENGTH_LONG).show();
//            Toast의 this에는 MainActivity가 들어와야하는데 MyClick가 들어와서 오류가 난다.
            Toast.makeText(MainActivity.this,"첫번째방식!!",Toast.LENGTH_LONG).show();
        }
    }

//    AppCompatActivity가 물려준 eventHandler 중 onTouchEvent를 재정의 !!
//    메소드 선언부분만 있음! 직접 사용하는 부분이 존재하지 않는다.
//    화면상에서 touch가 일어났는데 view가 touch이벤트를 처리하지 않아서 MainAcitivity까지 온다
//    운영체제(시스템)호출출
   @Override
    public boolean onTouchEvent(MotionEvent event) {

        Toast.makeText(this,"TouchEvent!!", Toast.LENGTH_LONG).show();

//        return super.onTouchEvent(event);
        return true;
    }


}