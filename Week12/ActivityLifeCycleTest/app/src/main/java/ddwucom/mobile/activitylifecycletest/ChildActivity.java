// LogCat의 필터에 ChildActivity 추가

package ddwucom.mobile.activitylifecycletest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class ChildActivity extends Activity {

	static final String TAG = "ChildActivity"; 
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_child);
		Log.i(TAG, "onCreate");
	}

	public void mOnClick(View v) {
		Log.i(TAG, "close ChildActivity");
		finish();
	}
	
	public void onStart() {
		super.onStart();
		Log.i(TAG, "onStart");
	}

	public void onResume() {
		super.onResume();
		Log.i(TAG, "onResume");
	}

	public void onPause() {
		super.onPause();
		Log.i(TAG, "onPause");
	}

	public void onRestart() {
		super.onRestart();
		Log.i(TAG, "onRestart");
	}

	public void onStop() {
		super.onStop();
		Log.i(TAG, "onStop");
	}

	public void onDestroy() {
		super.onDestroy();
		Log.i(TAG, "onDestroy");
	}

}
