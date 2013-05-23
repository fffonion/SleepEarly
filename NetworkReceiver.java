package dotnet.aoye.Receiver;
import dotnet.aoye.ThisApp;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
public class NetworkReceiver extends BroadcastReceiver{
    private ThisApp thisapp;
    private Context context;
	public NetworkReceiver(Context context){
           this.context = context;
    }
	public NetworkReceiver(){
	}
    private static final String netACTION="android.net.conn.CONNECTIVITY_CHANGE";
    @Override
    public void onReceive(Context context, Intent intent) {
    	thisapp=new ThisApp();
        if(intent.getAction().equals(netACTION)){
            if(intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false)){
                thisapp.setNetworkStatus(false);
            }else{
            	 thisapp.setNetworkStatus(true);
            }
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

