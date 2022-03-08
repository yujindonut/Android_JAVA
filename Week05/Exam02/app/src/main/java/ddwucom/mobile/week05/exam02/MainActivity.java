package ddwucom.mobile.week05.exam02;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private MyView myView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myView = findViewById(R.id.myView);
//       이렇게 쓰면 layout을 같이 사용X
//      MyView vw = new MyView(this);
//        setContentView(vw);
    }

    public void onClick (View v) {
        Random random = new Random();
        //random.nextInt(3) : 정수범위 0~2
        int randomR = (random.nextInt(3) + 1) * 100;
        int randomX = randomR + random.nextInt(myView.getWidth() - randomR * 2);
        int randomY = randomR + random.nextInt(myView.getHeight() - randomR * 2);

        switch (v.getId()) {
            case R.id.button:

                myView.setCircleX(randomX);
                myView.setCircleY(randomY);
                myView.setCircleR(randomR);

                myView.invalidate();
                break;

        }
    }
}