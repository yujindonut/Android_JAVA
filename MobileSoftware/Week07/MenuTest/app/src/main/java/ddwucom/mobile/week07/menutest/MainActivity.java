package ddwucom.mobile.week07.menutest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    static final String TAG = "MainActivity";
    
    final static int MENU_FIRST = 100;
    final static int MENU_SECOND = 200;

    PopupMenu popup;
     
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        TextView textView = findViewById(R.id.textView);
        
        //팝업메뉴 객체 생성
        popup = new PopupMenu(this,textView);
        //팝업 메뉴 항목의 구현
        popup.getMenuInflater().inflate(R.menu.menu_main, popup.getMenu() );
        //팝업 메뉴의 실행
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Toast.makeText(MainActivity.this,"Hi", Toast.LENGTH_LONG).show();
                return false;
            }
        });
        textView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
            //뷰가 터치가 되면 popup을 띄워라
                popup.show();
                return false;
            }
        });
        //textView는 contextMenu를 띄울 자격을 갖게됨
//        registerForContextMenu(textView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        switch(v.getId()){
            case R.id.textView:
                menu.setHeaderTitle("Context Menu");
//                직접 코드로 메뉴를 생성
                menu.add(0,MENU_FIRST, 0, "FIRST");
                menu.add(0,MENU_SECOND, 0, "SECOND");
//                xml로 만들어 메뉴를 inflation시키는 것
                getMenuInflater().inflate(R.menu.menu_context,menu);
                break;
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            //직접 코드를 구현했을 때의 id지정한것
            case MENU_FIRST:
                Log.i(TAG, "context1");
                break;
            case MENU_SECOND:
                Log.i(TAG, "context1");
                break;
            case R.id.third:
                Log.i(TAG, "context1");
                break;
            case R.id.fourth:
                Log.i(TAG, "context1");
                break;
        }
        
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.group_menu,menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        //조건을 설정해서 메뉴가 바뀌도록 설정
        if(true){
            //이걸 안하면 처음 만들어진 menu가 같이 다나옴
            menu.clear();

            //메뉴 바꿔치기
            getMenuInflater().inflate(R.menu.group_menu,menu);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case R.id.gItem01:
                if(item.isChecked())
                    //체크가 되어있으면 체크를 풀어라
                    item.setChecked(false);
                //item이 selected되면 check를 눌러라
                item.setChecked(true);
                break;
            case R.id.gItem02:
                if(item.isChecked())
                    //체크가 되어있으면 체크를 풀어라
                    item.setChecked(false);
                //item이 selected되면 check를 눌러라
                item.setChecked(true);
                break;
            case R.id.gItem03:
                item.setChecked(true);
                //radioBox는 자동으로 하나가 선택되면 다른거는 선택이 해지가 됨
                break;
            case R.id.gItem04:
                item.setChecked(true);
                break;

        }
        return true;
    }
    public void onMenuItemClick(MenuItem item) {
        Log.i(TAG, "item01 is clicked!!!");
    }
}