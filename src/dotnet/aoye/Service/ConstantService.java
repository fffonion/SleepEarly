package dotnet.aoye.Service;

import java.util.Calendar;
import dotnet.aoye.SleepActivity;
import dotnet.aoye.ThisApp;
import dotnet.aoye.Receiver.SuperReceiver;
import dotnet.aoye.util.XmlParser;

import android.app.Service;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;

import android.app.AlarmManager;
import android.app.PendingIntent;

/**
 * a service to :show hints during daytime
 *               remind that it's almost the time which user set to sleep
 *               force to goto SleepActivity
 * @author BINGO
 *
 */
public class ConstantService extends Service {
	private long SleepTimeMilli;
	private long SleepTimeHintMilli;
	private ThisApp thisapp;
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
    
	
	public void onCreate(){
		super.onCreate();
	}
	

   public void onStart(Intent intent1, int startid){  
	   thisapp=new ThisApp();
	   SharedPreferences  user = getSharedPreferences("preference",0);
       Calendar c = Calendar.getInstance();
       SuperReceiver sReceiver=new SuperReceiver();
       IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
       filter.addAction(Intent.ACTION_SCREEN_OFF);
       registerReceiver(sReceiver, filter);
    
       /*start to toast tips for daily life*/
       if(thisapp.getShowLittleTips()){
	       Intent gotoShowToastServiceintent = new Intent();
	       gotoShowToastServiceintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	       gotoShowToastServiceintent.setClass(ConstantService.this,ShowToastService.class);
	       PendingIntent pi3=PendingIntent.getService(this, 0, gotoShowToastServiceintent,0);
	       AlarmManager gotoShowToastServiceintentAlarm= (AlarmManager) getSystemService(ALARM_SERVICE);
	       gotoShowToastServiceintentAlarm.setRepeating(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),3600000, pi3); 
       }
       
	   c.set(Calendar.HOUR_OF_DAY,user.getInt("sleep_hour",0));
       c.set(Calendar.MINUTE, user.getInt("sleep_minute", 0));
       c.set(Calendar.SECOND, 0);
       SleepTimeMilli = c.getTimeInMillis();
       if ((Calendar.HOUR_OF_DAY) > user.getInt("sleep_hour",0) && ((Calendar.MINUTE) > user.getInt("sleep_minute",0)))
       if (user.getInt("sleep_hour", 0) < 5 )
       SleepTimeMilli = SleepTimeMilli + 24*60*60*1000;
       SleepTimeHintMilli = SleepTimeMilli-1800000;
       
       /* force to gotoSleepActivity if time is up*/       
       
       Intent gotoSleepActivityintent = new Intent();
       gotoSleepActivityintent.putExtra("SleepFlag", "1");
       gotoSleepActivityintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
       gotoSleepActivityintent.setClass(ConstantService.this,SleepActivity.class);
       PendingIntent pi1=PendingIntent.getActivity(this, 0, gotoSleepActivityintent,0);
       AlarmManager gotoSleepActivityAlarm= (AlarmManager) getSystemService(ALARM_SERVICE);
       gotoSleepActivityAlarm.set(AlarmManager.RTC_WAKEUP,SleepTimeMilli, pi1);
       
       
       /*start to remind user it's alomst time to sleep*/
       
       Intent gotoShowDialogServiceintent = new Intent();
       gotoShowDialogServiceintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
       gotoShowDialogServiceintent.setClass(ConstantService.this,ShowDialogService.class);
       PendingIntent pi2=PendingIntent.getService(this, 0, gotoShowDialogServiceintent,0);
       AlarmManager gotoShowDialogServiceintentAlarm= (AlarmManager) getSystemService(ALARM_SERVICE);
       //gotoShowDialogServiceintentAlarm.set(AlarmManager.RTC_WAKEUP,SleepTimeHintMilli, pi2);
       c = Calendar.getInstance();
       if (c.getTimeInMillis() < SleepTimeHintMilli )
    	   gotoShowDialogServiceintentAlarm.set(AlarmManager.RTC_WAKEUP,SleepTimeHintMilli, pi2);
       
       //if(thisapp.getSleepFlag()>thisapp.AWAKE)
    	   //registerReceiver();
   }
   
   public void registerReceiver(){  
	   /*SuperReceiver sReceiver = new SuperReceiver();
	   IntentFilter myIntentFilter = new IntentFilter();  
       myIntentFilter.addAction("SCREEN_STATE_CALL");  
       registerReceiver(sReceiver, myIntentFilter);  */
	   SuperReceiver sReceiver = new SuperReceiver();
	   IntentFilter filter = new IntentFilter();  
       filter.addAction("SCREEN_STATE_CALL");  
       filter.setPriority(Integer.MAX_VALUE);  
       registerReceiver(sReceiver, filter);  
   }  
}
