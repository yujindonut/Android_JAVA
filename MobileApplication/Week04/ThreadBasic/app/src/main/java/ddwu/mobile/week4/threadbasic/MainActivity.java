package ddwu.mobile.week4.threadbasic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    final static String TAG = "MainActivity";

    EditText etText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etText = findViewById(R.id.etText);
    }

    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnStart:
                TestThread t = new TestThread(handler);
                t.start();
                etText.setText("Thread start!");
                Toast.makeText(this, "Running!", Toast.LENGTH_SHORT).show();
                break;
        }
    }
    Handler handler = new Handler() {
        @Override
//        sendMessage하면 handleMessage가 받게됨
        public void handleMessage(@NonNull Message msg) {
            int i = msg.arg1;
            etText.setText("i: " + i);
        }
    };
    class TestThread extends Thread {
        Handler handler;
        public TestThread(Handler handler) {
            this.handler = handler;
        }
        @Override
        public void run() {
            for (int i=0; i < 100; i++) {
                Log.d(TAG, "i: " + i );
//                etText.setText("i : " + i); 이런식으로 작성하면 mainthread에 작성되어야할
//                화면 부분이 다른 실행흐름에서 작성되기때문에 강제 앱 종료됨
                Message msg = Message.obtain();
                msg.arg1 = i;
                handler.sendMessage(msg);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

