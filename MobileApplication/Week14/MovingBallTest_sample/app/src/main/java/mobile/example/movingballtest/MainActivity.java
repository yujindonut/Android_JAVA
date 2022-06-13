package mobile.example.movingballtest;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

	BallView ballView;
	private SensorManager sensorManager;
	private Sensor accelerometer;
	private Sensor magnetometer;
	private Sensor light;

	private static final float NS2S = 1.0f / 1000000000.0f;
	private float timestamp;

	float xMax;
	float yMax;
	private float[] mGravity;
	private float[] mGeomagnetic;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_main);
		ballView = new BallView(this);
		setContentView(ballView);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);	//세로모드 고정
		Point size = new Point();
		Display display = getWindowManager().getDefaultDisplay();
		display.getSize(size);
		Log.d("x : ", String.valueOf(size.x));
		Log.d("y: " , String.valueOf(size.y));
		xMax = (float) size.x - 10;
		yMax = (float) size.y - 10;
		sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
		light = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

	}

	SensorEventListener sensorLightEventListener = new SensorEventListener() {
		@Override
		public void onSensorChanged(SensorEvent event) {
			//조도값이 들어옴
			Log.d("lightSensor : " , String.valueOf(event.values[0]));
			if(event.values[0] <= 10){
				ballView.setColor(Color.LTGRAY);
				ballView.invalidate();
			}
			timestamp = event.timestamp;
		}

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {

		}
	};
	@Override
	protected void onPause() {
		super.onPause();
		sensorManager.unregisterListener(sensorLightEventListener);
		sensorManager.unregisterListener(ballView);
	}


	@Override
	protected void onResume() {
		super.onResume();
		sensorManager.registerListener(sensorLightEventListener, light, SensorManager.SENSOR_DELAY_UI);
		sensorManager.registerListener(ballView, accelerometer,SensorManager.SENSOR_DELAY_FASTEST);
		sensorManager.registerListener(ballView, magnetometer,SensorManager.SENSOR_DELAY_FASTEST);
	}

	
	class BallView extends View implements SensorEventListener {

		Paint paint;

		int width;
		int height;
		int color;
		
		int x;
		int y;
		int r;
		
		boolean isStart;

		public int getColor() {
			return color;
		}
		public void setColor(int color){
			this.color = color;
		}
		public BallView(Context context) {
			super(context);
			paint = new Paint();
			color = Color.RED;
			paint.setAntiAlias(true);
			isStart = true;
			r = 100;
		}
		
		public void onDraw(Canvas canvas) {
			if(isStart) {
				width = canvas.getWidth();
				height = canvas.getHeight(); 
				x =  width / 2;
				y =  height / 2;//화면 중앙에
				isStart = false;

			}
			paint.setColor(color);
			canvas.drawCircle(x, y, r, paint);
		}

		@Override
		public void onSensorChanged(SensorEvent event) {
			// x, y 좌표 계산 후 invalidate 계산
			if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
				mGravity = event.values.clone();
			}
			if(event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
				mGeomagnetic = event.values.clone();
			}

			if(mGravity != null && mGeomagnetic != null) {
				float rotationMatrix[] = new float[9];
				boolean success = SensorManager.getRotationMatrix(rotationMatrix, null, mGravity, mGeomagnetic );
				if(success){
					float values[] = new float[3];
					SensorManager.getOrientation(rotationMatrix, values);
					for(int i = 0; i < values.length; i++){
						Double degrees = Math.toDegrees(values[i]);
						values[i] = degrees.floatValue();
					}
					float azimuth = values[0];
					float pitch = values[1];
					float roll = values[2];
//					String result = String.format(Locale.KOREA, "Azimuth: %f\nPitch: %f\nRoll: %f",
//							azimuth,pitch,roll);
					y += -(int) pitch;
					Log.d("y : ", String.valueOf(y));
					x += (int) roll;
					Log.d("x : ", String.valueOf(x));
					Log.d("xMax" , String.valueOf(xMax) );
					Log.d("yMax" , String.valueOf(yMax) );
					if(xMax - 50 < x) {
						x = (int) xMax - 50;
					} else if(x <= 0) {
						x = 0;
					}
					if (y > yMax - 300){
						y = (int) yMax -300;
					} else if(y <= 0){
						y = 0;
					}
					invalidate();
				}
			}

		}
		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {

		}
	}
}
