package ddwucom.mobile.week07.exam01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.subItem01:
                Log.i(TAG, "짜장면 is clicked!");
                break;
            case R.id.subItem02:
                Log.i(TAG, "짬뽕 is clicked!");
                break;
        }
        return true;
    }
    public void onMenuItemClick(MenuItem item){
        switch(item.getItemId()){
            case R.id.subItem03:
                Log.i(TAG, "김치찌개 is clicked!");
                break;
            case R.id.subItem04:
                Log.i(TAG, "순두부찌개 is clicked!");
                break;
        }
    }

}