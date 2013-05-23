package dotnet.aoye;

import dotnet.aoye.R;
import dotnet.aoye.Service.ConstantService;
import dotnet.aoye.util.BadgeCheck;
import dotnet.aoye.util.XmlParser;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.view.Menu;

public class SplashActivity extends Activity {
	public final static String PREF_FILE = "preference";
	Handler badgeHdl;
	BadgeCheck cBadge;
	ThisApp thisapp;
	XmlParser parser;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        badgeHdl=new Handler();
        parser=new XmlParser(SplashActivity.this);
        thisapp=new ThisApp();
        badgeHdl.post(readFileThread);
        thisapp.setNetworkStatus(this.isConnecttedToInternet());   
        Intent service = new Intent(SplashActivity.this,ConstantService.class); 
        SplashActivity.this.startService(service); 
        new Handler().postDelayed(new Runnable(){   
            public void run() {  
        		int ver = parser.getConfigInt(parser.APP_VERSION);
                PackageManager packageManager = getPackageManager();
                PackageInfo packInfo = null;
				try {
					packInfo = packageManager.getPackageInfo(getPackageName(),0);
				} catch (NameNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                int cur_ver = packInfo.versionCode;
                Intent i ;
        		if (ver<cur_ver){
        			if(ver>0)
        				i = new Intent(SplashActivity.this, GuideActivity.class);
        				//i = new Intent(SplashActivity.this, WhatsNewActivity.class);
        			else
        				i = new Intent(SplashActivity.this, GuideActivity.class);
        		}else{
        			//i = new Intent(SplashActivity.this, GuideActivity.class);
        			i = new Intent(SplashActivity.this, WelcomeActivity.class);
        		}
                 SplashActivity.this.startActivity(i);
                 overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                 parser.setConfig(parser.APP_VERSION,cur_ver);
                 SplashActivity.this.finish();
            } 
            
        }, 800);   


    }
	 Runnable readFileThread = new Runnable(){  
		 public void run(){
			 cBadge=new BadgeCheck(SplashActivity.this);
			 for(int i=0;i<thisapp.getBadgeCount();i++){
				 cBadge.check(i,true);
					 //thisapp.setBadgeStatus(i,true);
					 //thisapp.setBadgeGotCountAdd();
					 //thisapp.setNewBadgeFlag(true);
				 
			 }
			 thisapp.setScreenHeight(parser.getConfigInt(parser.SCREEN_HEIGHT_CONFIG_TAG));
			 thisapp.setScreenWidth(parser.getConfigInt(parser.SCREEN_WIDTH_CONFIG_TAG));
			 thisapp.setShowHintTips(parser.getConfigBool(parser.SHOW_HINT_TAG));
			 thisapp.setUse24Hour(parser.getConfigBool(parser.USE_24_HOUR_TAG,true));
			 thisapp.setBadgeSyncFlag(parser.getConfigBool(parser.SYNC_BADGE_TAG,true));
			 thisapp.setRenrenPreferred(parser.getConfigBool(parser.RENREN_PREFERRED_TAG));
			 thisapp.setVibrate(parser.getConfigBool(parser.VIBRATE_TAG,true));
			 thisapp.setSleepFlag(parser.getConfigInt(parser.SLEEP_FLAG_TAG, 0));
			 thisapp.setShowLittleTips(parser.getConfigBool(parser.SHOW_TIPS_TAG,true));
		 }
	};

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.activity_splash, menu);
        return true;
    }
    private boolean isConnecttedToInternet(){
        ConnectivityManager connectivity =
        		(ConnectivityManager) SplashActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
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
