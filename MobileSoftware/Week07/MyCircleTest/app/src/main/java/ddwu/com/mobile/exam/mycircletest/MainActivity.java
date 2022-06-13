package ddwu.com.mobile.exam.mycircletest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private MyCircle myCircle;
    int val = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Custom View 객체*/
        myCircle = findViewById(R.id.myCircle);
        registerForContextMenu(myCircle);
        myCircle.setColor(Color.RED);
    }

    //실습1 : 원의 크기를 변경하는 OptionMenu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.memu_main,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case R.id.bigger:
                myCircle.setR(myCircle.getR() + 20);
                break;
            case R.id.smaller:
                myCircle.setR(myCircle.getR() - 20);
                break;
        }
        myCircle.invalidate();//화면을 다시 그려줘라 : onDraw다시 호출
        return true;
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        //메뉴를 실행할 때마다 호출됨
        switch(v.getId()){
            case R.id.myCircle:
                menu.setHeaderTitle("Change Color");
                getMenuInflater().inflate(R.menu.context_menu,menu);
                //밑의 확인 코드를 inflate 위에 쓰면 R.id.xx가 생성되지 않은 상태이기 때문에 nullPointer오류가 남
                if(val == 1)
                    menu.findItem(R.id.RED).setChecked(true);
                if(val == 2)
                    menu.findItem(R.id.GREEN).setChecked(true);
                if(val == 3)
                    menu.findItem(R.id.BLUE).setChecked(true);
                break;
        }
    }
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.RED:
                val = 1;
                myCircle.setColor(Color.RED);
                break;
            case R.id.GREEN:
                myCircle.setColor(Color.GREEN);
                val = 2;
                break;
            case R.id.BLUE:
                val = 3;
                myCircle.setColor(Color.BLUE);
                break;
        }
//        Log.d(TAG, val);
        myCircle.invalidate();
        return true;
    }
}
