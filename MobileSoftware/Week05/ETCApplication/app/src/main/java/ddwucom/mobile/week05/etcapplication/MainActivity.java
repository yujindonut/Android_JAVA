package ddwucom.mobile.week05.etcapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    SoundPool soundPool;
    int sound;
    Vibrator vibrator;

    Toast myToast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        sound = soundPool.load(this,R.raw.dingdong,1);
        //시스템으로부터 진동기능 서비스를 얻어오기
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.btnSound:
                soundPool.play(sound,1,1,0,0,1);
                break;

            case R.id.btnVibration:
                vibrator.vibrate(500);//0.5초동안 진동기능을 수행해라
//                진동을 계속 지속하는 코드
//                vibrator.vibrate(new long[] {100,50,200,50},0);
//                vibrate.cancel();
                break;
            case R.id.btnToast:
                myToast = Toast.makeText(this,"한유진",Toast.LENGTH_LONG);
                break;
        }
    }
}