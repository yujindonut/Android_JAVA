package ddwucom.mobile.week12.exam01;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        message = findViewById(R.id.text_main);
        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message.setText("");
            }
        });
    }
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn_callactivity:
                String msg = message.getText().toString();
                Intent intent = new Intent(this, SubActivity.class);
                intent.putExtra("message", msg);
                startActivity(intent);
        }
    }
}