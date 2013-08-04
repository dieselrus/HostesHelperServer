package ru.diesel_ru.hosteshelperserver;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;


public class HostesHelperService extends Service {

	final String LOG_TAG = "myLogs";
	public static final int PORT_WORK = 9000;
    public static final int PORT_STOP = 9001;
    
	public void onCreate() {
	    super.onCreate();
	    Log.d(LOG_TAG, "onCreate");
	}
		  
	public int onStartCommand(Intent intent, int flags, int startId) {
	    Log.d(LOG_TAG, "onStartCommand");
	    someTask();
	    
//	    MultiThreadedServer server = new MultiThreadedServer(PORT_WORK);
//		new Thread(server).start();
//		
	    return super.onStartCommand(intent, flags, startId);
	    //return START_NOT_STICKY;
	}

	public void onDestroy() {
		super.onDestroy();
	    Log.d(LOG_TAG, "onDestroy");
	    stopSelf();
	}
		  
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	void someTask() {
//		MultiThreadedServer server = new MultiThreadedServer(PORT_WORK);
//		new Thread(server).start();
//	    try {
//		    Thread monitor = new StopMonitor(PORT_STOP);
//		    monitor.start();
//		    monitor.join();
//		    System.out.println("Right after join.....");
//	    } catch (InterruptedException e) {
//	    	e.printStackTrace();
//	    }
//	    
//	    System.out.println("Stopping Server");
//	    server.stop();
		
	    new Thread(new Runnable() {
	        public void run() {
	    		MultiThreadedServer server = new MultiThreadedServer(PORT_WORK);
	    		new Thread(server).start();
	    	    try {
	    		    Thread monitor = new StopMonitor(PORT_STOP);
	    		    monitor.start();
	    		    monitor.join();
	    		    System.out.println("Right after join.....");
	    	    } catch (InterruptedException e) {
	    	    	e.printStackTrace();
	    	    }
	    	    
	    	    System.out.println("Stopping Server");
	    	    server.stop();
	    	    
	    	    stopSelf();
	        }
	      }).start();
	}
}
