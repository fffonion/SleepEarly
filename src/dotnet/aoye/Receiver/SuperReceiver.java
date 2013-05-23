package dotnet.aoye.Receiver;

import java.util.Calendar;

import dotnet.aoye.Service.ConstantService;
import dotnet.aoye.Service.ShowToastService;
import android.content.BroadcastReceiver; 
import android.content.Context; 
import android.content.Intent; 
import android.util.Log;
import dotnet.aoye.ThisApp;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


/**
 * a broadcastreceiver to receive broadcast when device boots
 * and start ConstantService
 * @author BINGO,fffonion
 *
 */
public class SuperReceiver extends BroadcastReceiver { 
	private ThisApp thisapp;
    private Context context;
    private static final String NET_ACTION="android.net.conn.CONNECTIVITY_CHANGE";
    private static final String BOOT_ACTION="android.intent.action.BOOT_COMPLETED";
	public SuperReceiver(Context context){
           this.context = context;
    }
	public SuperReceiver(){
	}
	@Override 
    public void onReceive(Context context, Intent intent) { 
		thisapp=new ThisApp();
		Log.d("dotnet.Receiver",intent.getAction());
        if(intent.getAction().equals(NET_ACTION)){
            if(intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false)){
                thisapp.setNetworkStatus(false);
            }else{
            	thisapp.setNetworkStatus(true);
            }
            Log.d("dotnet.Receiver", "network changed");
        }else if (intent.getAction().equals(BOOT_ACTION)){
	        Intent service = new Intent(context,ConstantService.class); 
	        context.startService(service); 
	        Log.v("dotnet.Receiver", "开机自动服务自动启动....."); 
        }else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)){
        	thisapp.setScreenOnStatus(true);
        	Log.d("dotnet.Receiver", "SCREEN ON");
        	return;
        }else if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)){
        	thisapp.setScreenOnStatus(false);
        	Log.d("dotnet.Receiver", "SCREEN OFF");
        	return;
        }
    } 

	public boolean isConnecttedToInternet(){
	        ConnectivityManager connectivity =
	        		(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	          if (connectivity != null)
	          {
	              NetworkInfo[] info = connectivity.getAllNetworkInfo();
	              if (info != null)
	                  for (int i = 0; i < info.length; i++)
	                      if (info[i].getState() == NetworkInfo.State.CONNECTED)
	                      {
	                          return true;
	                      }
	  
	          }
	          return false;
	    }
} 