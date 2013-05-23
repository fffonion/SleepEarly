package dotnet.aoye.Service;


import java.io.IOException;

import dotnet.aoye.R;
import dotnet.aoye.ThisApp;
import android.app.Service;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

public class ShowDialogService extends Service{
	
	 

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}  
	public void onStart(Intent intent1, int startid){
		showSystemDialog();
	}
	public void onCreate(){
		super.onCreate();
	}
	 
	private void showSystemDialog() {     
	    /* create ui */   
	        AlertDialog.Builder b = new AlertDialog.Builder(ShowDialogService.this);  
	        b.setTitle("快到睡觉时间了哦~~")
	         .setIcon(R.drawable.octocat_sleep)
	         .setMessage("距离您设定的今日睡觉时间还有30分钟，时间到了会自动锁定手机哦~")
	         .setNegativeButton("知道了",new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
				}
	         });
	        LayoutInflater factory = LayoutInflater.from(this);
			View view = factory.inflate(R.layout.nothing_dialog,null);
	        AlertDialog  d = b.create();   
	        d.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);   
	        d.setCanceledOnTouchOutside(false); 
	        d.setView(view);
	        d.show();     
	  
	        /* set size & pos */  
	        WindowManager.LayoutParams lp = d.getWindow().getAttributes();                 
	        d.getWindow().setAttributes(lp);        
	        Vibrator vibratorm = (Vibrator) getApplication().getSystemService(
	                Service.VIBRATOR_SERVICE);
	        // vibratorm.vibrate(new long[] { 0,100,20,100,140,100,140,100,20,100,20,100,20,50,70,50,70}, -1);
	        ThisApp thisapp=new ThisApp();
	        if(thisapp.getVibrate())
	        	vibratorm.vibrate(new long[] { 0,300,100,300,100,300 }, -1);
	        else{
	        	int prompt_raw[]={R.raw.dong,R.raw.family_mart,R.raw.natsume,R.raw.nurarihyon,
	    			R.raw.yukiteru,R.raw.yuno,R.raw.pu,R.raw.hahaha_dogdays,R.raw.ahahaha_96_1,R.raw.ahahaha_96_2,R.raw.ahahaha_96_3,
	    			R.raw.ahahaha_96_4,R.raw.ahahaha_96_5,R.raw.ahahaha_96_6,R.raw.ahahaha_96_7};
	        	MediaPlayer mp = MediaPlayer.create(this,
	        			prompt_raw[(int)( prompt_raw.length* Math.random())]);
	        	try {
	    			mp.prepare();
	    		} catch (IllegalStateException e) {
	    			// TODO Auto-generated catch block
	    			e.printStackTrace();
	    		} catch (IOException e) {
	    			// TODO Auto-generated catch block
	    			e.printStackTrace();
	    		}
	        	mp.start();
	        }
	          this.stopSelf();
	}

}
