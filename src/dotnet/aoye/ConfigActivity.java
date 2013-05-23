package dotnet.aoye;

import com.renren.api.connect.android.exception.RenrenAuthError;
import com.renren.api.connect.android.view.RenrenAuthListener;
import com.weibo.net.DialogError;
import com.weibo.net.WeiboDialogListener;
import com.weibo.net.WeiboException;

import dotnet.aoye.R;
import dotnet.aoye.Service.ConstantService;
import dotnet.aoye.net.RenrenApi;
import dotnet.aoye.net.WeiboApi;
import dotnet.aoye.util.XmlParser;
import dotnet.aoye.widget.wheel.NumericWheelAdapter;
import dotnet.aoye.widget.wheel.OnWheelChangedListener;
import dotnet.aoye.widget.wheel.OnWheelScrollListener;
import dotnet.aoye.widget.wheel.WheelView;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.GestureDetector.OnGestureListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;
public class ConfigActivity extends Activity  implements OnGestureListener,OnTouchListener{
	public final static String PREF_FILE = "preference";
	//build-in preference tags
	public final static String SLEEP_TIME="sleep_time";
	public final static String RENREN_LOGIN="renren_has_login";
	public final static String WEIBO_LOGIN="weibo_has_login";
	public final static String SHOW_TIP="show_tip_on_picture";
	public final static String HASH="md5_value";
	//build-in badge tags
	private Button backBtn,badgeBtn,weiboBtn,renrenBtn;
	public String prefs;
	public SharedPreferences.Editor editor;
	public SharedPreferences sp;
	public GestureDetector gDetector;
	private XmlParser parser;
	private CheckBox cfgTv1,cfgTv2,cfgTv4,cfgTv8;
    private Button settime;
    private RadioButton rrRdBtn,wbRdBtn;
    private TextView settimeTv;
    private boolean timeScrolled ,timeChanged;
	RelativeLayout configrl;
	ThisApp thisapp;
	WeiboApi wbapi;
	RenrenApi rrapi;
	private Context thisContext;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE); //no title
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,       
        WindowManager.LayoutParams. FLAG_FULLSCREEN);
        setContentView(R.layout.activity_config);
        thisapp=new ThisApp();
        backBtn=(Button) findViewById(R.id.backCfgBtn);
        badgeBtn=(Button) findViewById(R.id.badgeCfgBtn);
        weiboBtn=(Button) findViewById(R.id.weiboCfgBtn);
        renrenBtn=(Button) findViewById(R.id.renrenCfgBtn);
        gDetector = new GestureDetector((OnGestureListener) this);    
        configrl= (RelativeLayout) findViewById(R.id.configRL);
        configrl.setOnTouchListener(this);
        configrl.setLongClickable(true);
        settimeTv=(TextView) findViewById(R.id.settimeCfgTv);
        
        wbapi=new WeiboApi(this);
        rrapi=new RenrenApi(this);
        
        thisContext=ConfigActivity.this;
        backBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
            	ConfigActivity.this.endOfConfig();
            }
        });
        badgeBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
            	gotoBadgeActivity();
            }
        });
        parser = new XmlParser(ConfigActivity.this);
		settime = (Button) findViewById(R.id.ConfigActivity_settime);
        cfgTv1 = (CheckBox) findViewById(R.id.cfgTv1);
        cfgTv2 = (CheckBox) findViewById(R.id.cfgTv2);
        cfgTv4 = (CheckBox) findViewById(R.id.cfgTv4);
        cfgTv8 = (CheckBox) findViewById(R.id.cfgTv8);
        rrRdBtn=(RadioButton) findViewById(R.id.renrenShowOnIndexRd);
        wbRdBtn=(RadioButton) findViewById(R.id.weiboShowOnIndexRd);
        settime.setOnClickListener(new ShowSetTimeDialogListener());
        cfgTv1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				
					parser.setConfig(parser.VIBRATE_TAG, isChecked);
					thisapp.setVibrate(isChecked);
					//thisapp.ShowTips=isChecked;
			}
		});

        cfgTv2.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
					parser.setConfig(parser.USE_24_HOUR_TAG,isChecked);
					thisapp.setUse24Hour(isChecked);
			}
		});
        cfgTv4.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
					parser.setConfig(parser.SYNC_BADGE_TAG, isChecked);
					thisapp.setBadgeSyncFlag(isChecked);
			}
		});
        cfgTv8.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
					parser.setConfig(parser.SHOW_TIPS_TAG, isChecked);
					thisapp.setShowLittleTips(isChecked);
			}
		});
        renrenBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(thisapp.getRenrenLoginFlag())
					logout_rr();
				else
					login_rr();
			}
		});
        
        weiboBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(thisapp.getWeiboLoginFlag())
					logout_wb();
				else
					login_wb();
			}
		});
        
        rrRdBtn.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				thisapp.setRenrenPreferred(isChecked);
				parser.setConfig(parser.RENREN_PREFERRED_TAG, isChecked);
			}
        });
    }
    @Override
    public void onStart(){
    	 cfgTv1.setChecked(thisapp.getVibrate());
         cfgTv2.setChecked(thisapp.getUse24Hour());
         cfgTv4.setChecked(thisapp.getSyncBadge());
         cfgTv8.setChecked(thisapp.getShowLittleTips());
         rrRdBtn.setChecked(thisapp.getRenrenPreferred());
         wbRdBtn.setChecked(!rrRdBtn.isChecked());
         settimeTv.setText("当前设定为"+String.valueOf(parser.getConfigInt(parser.SLEEP_HOUR_TAG))+
         		"时"+String.valueOf(parser.getConfigInt(parser.SLEEP_MINUTE_TAG))+"分");
         showWBBtn(thisapp.getWeiboLoginFlag());
         showRRBtn(thisapp.getRenrenLoginFlag());
         super.onStart();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.activity_config, menu);
        return true;
    }
    public void endOfConfig(){
    	this.finish();
    	overridePendingTransition(0, R.anim.minimize_right_top);
    }
    private void gotoBadgeActivity(){
 	   Intent i=new Intent(ConfigActivity.this, BadgeActivity.class);
 	   ConfigActivity.this.startActivity(i);
 	   overridePendingTransition(R.anim.maximize_left_bottom,R.anim.fade_out);
    }

	public boolean onKeyDown(int keyCodeInt, KeyEvent paramKeyEvent)
    {
    	if((keyCodeInt==KeyEvent.KEYCODE_BACK))
        this.endOfConfig();
    	return false;
    }

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		if (e2.getX() - e1.getX()>80 && e1.getY() - e2.getY() > 100 ) {  
			if(e2.getX()>thisapp.getRightX() && e2.getY()<thisapp.getTopY())
				this.endOfConfig();
			else if(e1.getX()<thisapp.getLeftX() && e1.getY()>thisapp.getBottomY())
				gotoBadgeActivity();
			return false;
		}
		return false;
	}
	private void login_rr(){
    	
		if(!thisapp.getNetworkStatus()){
    		Toast.makeText(thisContext, R.string.no_internet, Toast.LENGTH_SHORT).show();
    		return;
    	}
    	RenrenAuthListener rrlistener = new RenrenAuthListener() {
            /*success*/
    		Handler renrenHandler=new Handler();
            public void onComplete(Bundle values) {
                showRRBtn(true);           
                renrenHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(thisContext,
                            thisContext.getString(R.string.auth_success),
                            Toast.LENGTH_SHORT).show();
                        thisapp.setRenrenLoginFlag(true);
                    }
                });
            }
            /*failure*/
            @Override
            public void onRenrenAuthError(RenrenAuthError renrenAuthError) {
                renrenHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(thisContext,
                            thisContext.getString(R.string.auth_failed),
                            Toast.LENGTH_SHORT).show();
                    }
                });
            }
            @Override
            public void onCancelLogin() {
            }
            @Override
            public void onCancelAuth(Bundle values) {
            }
        };
    	this.rrapi.login(rrlistener);
    }
    
    private void logout_rr(){
    	if(!thisapp.getNetworkStatus()){
    		Toast.makeText(thisContext, R.string.no_internet,Toast.LENGTH_SHORT).show();
    		return;
    	}
    	this.rrapi.logout(getApplicationContext());
    	Toast.makeText(thisContext, R.string.logout_success, 0).show();
        showRRBtn(false);
        thisapp.setRenrenLoginFlag(false);
    }
    private void login_wb(){
    	if(!thisapp.getNetworkStatus()){
    		Toast.makeText(thisContext, R.string.no_internet, Toast.LENGTH_SHORT).show(); 
    		return ;
    	}
    	WeiboDialogListener wbListener = new WeiboDialogListener() {
			@Override
			public void onComplete(Bundle values) {
				// TODO Auto-generated method stub
				showWBBtn(true);  
				Toast.makeText(thisContext,
                         thisContext.getString(R.string.auth_success),
                         Toast.LENGTH_SHORT).show();
                     thisapp.setWeiboLoginFlag(true);
                     /*Toast.makeText(thisContext,
                             String.valueOf(wbapi.getExpiresTime()),
                             Toast.LENGTH_SHORT).show();*/
                     wbapi.storeAccessToken(wbapi.getAccessToken(), wbapi.getExpiresTime());
                     /*wbapi.initAccessToken();
                     Toast.makeText(thisContext,
                             String.valueOf(wbapi.getExpiresTime()),
                             Toast.LENGTH_SHORT).show();*/
                     
			}
			@Override
			public void onWeiboException(WeiboException e) {
				// TODO Auto-generated method stub
			}
			@Override
			public void onError(DialogError e) {
				// TODO Auto-generated method stub
			}
			@Override
			public void onCancel() {
				// TODO Auto-generated method stub
			}
        };
    	this.wbapi.auth(wbListener);
    }
    
    private void logout_wb(){
    	if(!thisapp.getNetworkStatus()){
    		Toast.makeText(thisContext, R.string.no_internet,Toast.LENGTH_SHORT).show();
    		return;
    	}
    	this.wbapi.logout(getApplicationContext());
    	Toast.makeText(thisContext, R.string.logout_success, 0).show();
        showWBBtn(false);
        thisapp.setWeiboLoginFlag(false);
    }
    
    private void showWBBtn(boolean flag){
        if(flag)
           weiboBtn.setBackgroundResource(R.drawable.config_weibo_in);
        else 
        	weiboBtn.setBackgroundResource(R.drawable.config_weibo_out);
            

    }
    private void showRRBtn(boolean flag){
        if(flag)
            renrenBtn.setBackgroundResource(R.drawable.config_renren_in);
        else 
         	renrenBtn.setBackgroundResource(R.drawable.config_renren_out);
            
    }
    /*
	public String getConfigByTag(String Tag){
		sp= getSharedPreferences(PREF_FILE,0);  
		prefs = sp.getString(Tag, null);
		return prefs;
	}
	public void setConfigByTag(String Tag,String StringOfTag){
		sp= getSharedPreferences(PREF_FILE,0);
		editor= sp.edit();  
		editor.putString(Tag,StringOfTag);
		editor.commit();
	}
	
	public void setConfigByTag(String Tag,boolean booleanOfTag){
		sp= getSharedPreferences(PREF_FILE,3);
		editor= sp.edit();  
		editor.putBoolean(Tag,booleanOfTag);  
		editor.commit();
	}
	public void setConfigByTag(String Tag,long longOfTag){
		sp= getSharedPreferences(PREF_FILE,0);
		editor= sp.edit();  
		editor.putLong(Tag,longOfTag);  
		editor.commit();
	}
	*/
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		return gDetector.onTouchEvent(event);
	}

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}
	/**
	 * a Dialog for user to set time to sleep
	 * @author BINGO
	 * 
	 */	
	public class ShowSetTimeDialogListener implements View.OnClickListener{
		int hour,min;
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			LayoutInflater factory = LayoutInflater.from(ConfigActivity.this);
			View view = factory.inflate(R.layout.timesetting_dialog,null);
			final WheelView hours = (WheelView) view.findViewById(R.id.hour);
			hours.setAdapter(new NumericWheelAdapter(0, 23));
			hours.setLabel("点");
			hours.setCyclic(true);
			final WheelView mins = (WheelView) view.findViewById(R.id.mins);
			mins.setAdapter(new NumericWheelAdapter(0, 59, "%02d"));
			mins.setLabel("分");
			mins.setCyclic(true);
		
		
			// set current time
			
			hour = parser.getConfigInt(parser.SLEEP_HOUR_TAG);
			min = parser.getConfigInt(parser.SLEEP_MINUTE_TAG);
			hours.setCurrentItem(hour);
			mins.setCurrentItem(min);
		
		
		
			OnWheelChangedListener wheelListener = new OnWheelChangedListener() {
				public void onChanged(WheelView wheel, int oldValue, int newValue) {
					if (!timeScrolled) {
						timeChanged = true;
						timeChanged = false;
					}
				}
			};

			hours.addChangingListener(wheelListener);
			mins.addChangingListener(wheelListener);

			OnWheelScrollListener scrollListener = new OnWheelScrollListener() {
				public void onScrollingStarted(WheelView wheel) {
					timeScrolled = true;
				}
				public void onScrollingFinished(WheelView wheel) {
					timeScrolled = false;
					timeChanged = true;
					hour = hours.getCurrentItem();
					min = mins.getCurrentItem();
					timeChanged = false;
				}
			};			
			hours.addScrollingListener(scrollListener);
			mins.addScrollingListener(scrollListener);		
			
			new AlertDialog.Builder(ConfigActivity.this)
			 	.setIcon(R.drawable.octocat_settime)
	            .setPositiveButton("定好了", new DialogInterface.OnClickListener() {								
			     @Override
	                  public void onClick(DialogInterface dialog, int which) {	
			    	 if(parser.getConfigInt(parser.SLEEP_HOUR_TAG,-1)!=-1){
				    	 Intent service = new Intent();
						 service.setClass(ConfigActivity.this,ConstantService.class); 
						 startService(service);
			    	 }
					 parser.setConfig(parser.SLEEP_HOUR_TAG,hour);
			    	 parser.setConfig(parser.SLEEP_MINUTE_TAG,min);
			    	 settimeTv.setText("当前设定为"+String.valueOf(parser.getConfigInt(parser.SLEEP_HOUR_TAG))
			    			 +"时"+String.valueOf(parser.getConfigInt(parser.SLEEP_MINUTE_TAG))+"分");
	           }
			})
	          .setNegativeButton("取消" , new DialogInterface.OnClickListener() {	
	      	   @Override
	             public void onClick(DialogInterface dialog, int which) {                
	             }
			}).setView(view)
			  .setTitle("定一个睡觉时间")
		      .show();					   	
		   }			
		}
}
