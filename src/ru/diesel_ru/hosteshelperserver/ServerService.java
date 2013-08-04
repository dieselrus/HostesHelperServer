package ru.diesel_ru.hosteshelperserver;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class ServerService extends Service {
	final String LOG_TAG = "myLogs";
	public static final int PORT_WORK = 9000;
	
	public void onCreate() {
	    super.onCreate();
	    Log.d(LOG_TAG, "onCreate");
	}
	
	public int onStartCommand(Intent intent, int flags, int startId) {
	    Log.d(LOG_TAG, "onStartCommand");

	    return super.onStartCommand(intent, flags, startId);
	    //return START_NOT_STICKY;
	}

	public void onDestroy() {
		super.onDestroy();
	    Log.d(LOG_TAG, "onDestroy");
	    stopSelf();
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

}

