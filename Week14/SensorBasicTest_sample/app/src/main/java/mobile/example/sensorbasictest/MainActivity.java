package mobile.example.sensorbasictest;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

	private TextView tvText;
	private SensorManager sensorManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		tvText = (TextView)findViewById(R.id.tvText);
		sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
	}
	
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.btnSensor:
			
			String result = "";
			List<Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);
			sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
			//type을 지정해줘서 defaultsensor를 가져와도 된다!
			for (Sensor sensor : sensorList) {
				String sensorSpec = String.format("Sensor name: %s\nSensor Type: %s\n", sensor.getName(),
						sensor.getType());
			}

			tvText.setText(result);
			
			break;
		}
	}
	
	
}
