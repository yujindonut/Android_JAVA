package mobile.example.alarmtest;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.*;
import android.os.SystemClock;
import android.widget.*;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class MyBroadcastReceiver extends BroadcastReceiver {
	PendingIntent sender = null;
	AlarmManager alarmManager = null;

	public void onReceive(Context context, Intent intent) {
		Toast.makeText(context, "one time!", Toast.LENGTH_LONG).show();
		// Notification 출력
		//채널등록까지!

		intent = new Intent(context,NotiActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//		sender = PendingIntent.getActivity(context,0,intent,0);
//		alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
//				SystemClock.elapsedRealtime() + 3000, 10000 * 6, sender);

		NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "MY_CHANNEL")
				.setSmallIcon(R.drawable.ic_stat_name)
				.setContentTitle("기상시간")
				.setWhen(System.currentTimeMillis())
				.setContentText("일어나! 공부할 시간이야!")
				.setPriority(NotificationCompat.PRIORITY_DEFAULT)
				.setContentIntent(sender)//알림을 탭했을때, NotiActivity를 보이게 함
				.setAutoCancel(true);//터치를 하면 알람이 닫혀지게

		NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

		int notificationId = 100;

		notificationManager.notify(notificationId, builder.build());
	}
}