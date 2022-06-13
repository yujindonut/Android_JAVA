package ddwucom.mobile.week03.hw3.threebuttons;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onClick(View v){

        EditText text = findViewById(R.id.editText);

        String name = text.getText().toString();
        switch(v.getId()){
            case R.id.btnOne:
//                name += "1";
                text.setText(name + "1");
                break;
            case R.id.btnTwo:
//                name += "2";
                text.setText(name + "2");
                break;

            case R.id.btnThree:
//                name += "3";
                text.setText(name + "3");
                break;

            case R.id.btnClear:
//                name="";
                text.setText("");
                break;
        }
        name = text.getText().toString();

        Toast.makeText(this,name,Toast.LENGTH_LONG).show();
    }
}