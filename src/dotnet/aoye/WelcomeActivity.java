package dotnet.aoye;




import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.Calendar;

import com.renren.api.connect.android.exception.RenrenAuthError;
import com.renren.api.connect.android.view.RenrenAuthListener;
import com.weibo.net.DialogError;
import com.weibo.net.WeiboDialogListener;
import com.weibo.net.WeiboException;

import dotnet.aoye.R;
import dotnet.aoye.Receiver.SuperReceiver;
import dotnet.aoye.Service.ConstantService;
import dotnet.aoye.net.RenrenApi;
import dotnet.aoye.net.WeiboApi;
import dotnet.aoye.util.BadgeCheck;
import dotnet.aoye.util.XmlParser;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class WelcomeActivity extends Activity implements OnGestureListener,OnTouchListener{

	//reserved for renren authoration
	RenrenApi rrapi;
	RenrenAuthListener rrlistener;
	WeiboApi wbapi;
	BadgeCheck cBadge;
	ThisApp thisapp;
	public GestureDetector gDetector;  
	private Button loginBtn,logoutBtn,sleepBtn,badgeBtn,cfgBtn;
	private TextView mhTxt,secTxt,hideTipTxt;
	private Handler renrenHandler,timeHandler,bumpHandler,hideBtnHdl;
	private ImageView bgImg, timeFlagImg;
	private Animation bumpOutAnim;
	RelativeLayout welcomerl;
	//private SimpleDateFormat dtFmt_HHMM,dtFmt_SS,dtFmt_HH;
	private final int timeFlagDrawable[]={R.drawable.time_flag_morning,R.drawable.time_flag_am,
			R.drawable.time_flag_pm,R.drawable.time_flag_night,R.drawable.time_flag_deepnight},
			WEIBO_LOGIN_DRAWABLE=R.drawable.weibologinbtn,WEIBO_LOGOUT_DRAWABLE=R.drawable.weibologoutbtn,
			RENREN_LOGIN_DRAWABLE=R.drawable.renrenloginbtn,RENREN_LOGOUT_DRAWABLE=R.drawable.renrenlogoutbtn;
	private static int timeFlag,timeFlagLast,hideTime=7,pastMin;
	private int pastTime=-1;
	//morning, am, pm, night, deep night;0-renren,1-weibo
	//private boolean isSleepTime;
	private Calendar ca;
	private DecimalFormat zerozeroDF;
	private Context thisContext;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*getWindow().requestFeature(Window.FEATURE_NO_TITLE); //no title
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,       
                WindowManager.LayoutParams. FLAG_FULLSCREEN); //full screen*/
        setContentView(R.layout.activity_welcome);
        //ThisApp App = (ThisApp)getApplication();
        //App.setSplashExitFlag(true);
        /*
        //for dailing
        Uri uri = Uri.parse("tel:");
        Intent dialIntent = new Intent(Intent.ACTION_DIAL, uri);
        startActivity(dialIntent);
        //for sms
        Intent mmsintent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("smsto","", null));
        startActivity(mmsintent);
    	*/
        init();
      
        setLayouts();
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(thisapp.getRenrenPreferred())	login_rr();
                else	login_wb();
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	if(thisapp.getRenrenPreferred())	logout_rr();
            	else	logout_wb();
            }
        });
        
        sleepBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	showSleepDialog();
    	    	//thisContext.finish();
            }
        });
        badgeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	gotoBadgeActivity();
            }
        });
        cfgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	gotoCfgActivity();
            	
            }
        });
        /*buttona =(Button) findViewById(R.id.button1);
        buttona.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				
			   // Intent localIntent = new Intent(this, BadgeActivity.class);
			    //startActivity(localIntent);
			    //rrapi.publishStatus("我是一坨bb~");
				//Toast.makeText(thisContext, "~", 0).show();
			}
        });*/
        }
    @Override
    public void onStart(){
    	setButtonAppearance();
    	Bundle bundle=getIntent().getExtras();
        String awakeflag=new String();
        /*awakeflag=bundle.getString("returnfromsleep");
        if(awakeflag!=null){
        	hideBtnHdl.post(hideButtonThread);
        	hideTipTxt.setVisibility(View.VISIBLE);
 		   	sleepBtn.setVisibility(View.INVISIBLE);
        }*/
    	super.onStart();
    }
    @Override
    public void onResume(){
    	 XmlParser parser=new XmlParser(this);
    	 thisapp.setScreenHeight(parser.getConfigInt(parser.SCREEN_HEIGHT_CONFIG_TAG));
		 thisapp.setScreenWidth(parser.getConfigInt(parser.SCREEN_WIDTH_CONFIG_TAG));
		 thisapp.setShowHintTips(parser.getConfigBool(parser.SHOW_HINT_TAG));
		 thisapp.setUse24Hour(parser.getConfigBool(parser.USE_24_HOUR_TAG,true));
		 thisapp.setBadgeSyncFlag(parser.getConfigBool(parser.SYNC_BADGE_TAG,true));
		 thisapp.setRenrenPreferred(parser.getConfigBool(parser.RENREN_PREFERRED_TAG));
		 thisapp.setVibrate(parser.getConfigBool(parser.VIBRATE_TAG,true));
		 thisapp.setSleepFlag(parser.getConfigInt(parser.SLEEP_FLAG_TAG, 0));
		 super.onResume();
    }
    @Override
    public void onRestart(){
    	XmlParser parser=new XmlParser(this);
    	thisapp.setScreenHeight(parser.getConfigInt(parser.SCREEN_HEIGHT_CONFIG_TAG));
		 thisapp.setScreenWidth(parser.getConfigInt(parser.SCREEN_WIDTH_CONFIG_TAG));
		 thisapp.setShowHintTips(parser.getConfigBool(parser.SHOW_HINT_TAG));
		 thisapp.setUse24Hour(parser.getConfigBool(parser.USE_24_HOUR_TAG,true));
		 thisapp.setBadgeSyncFlag(parser.getConfigBool(parser.SYNC_BADGE_TAG,true));
		 thisapp.setRenrenPreferred(parser.getConfigBool(parser.RENREN_PREFERRED_TAG));
		 thisapp.setVibrate(parser.getConfigBool(parser.VIBRATE_TAG,true));
		 thisapp.setSleepFlag(parser.getConfigInt(parser.SLEEP_FLAG_TAG, 0));
		 super.onRestart();
    }
	/*
    private void post2Network() {
		Log.d("test time",String.valueOf(System.currentTimeMillis()));
		String str="123";
		if(thisapp.getSyncFlag()){
			rrapi = new RenrenApi(WelcomeActivity.this);
		    if(rrapi.hasLogin()){
		    	ProgressDialog pd2=ProgressDialog.show(WelcomeActivity.this,"发送到人人",
		    			"正在和位于火星的服务器取得联系……");
		    	rrapi.publishStatus(str);
		    	pd2.cancel();
		    }
		    wbapi=new WeiboApi(WelcomeActivity.this);
		    if(wbapi.hasLogin()){
		    	ProgressDialog pd2=ProgressDialog.show(WelcomeActivity.this,"发送到微博",
		    			"正在和位于火星的服务器取得联系……");
		    	wbapi.postWeibo(str,null);
		    	pd2.cancel();
		    }
		}
		Log.d("test time",String.valueOf(System.currentTimeMillis()));
	}
	 */

    /*Initial variables*/
	private void init(){
        //renren = new Renren(thisContext.rrapi.API_KEY, thisContext.rrapi.SECRET_KEY, thisContext.rrapi.APP_ID, thisContext);
		thisContext=WelcomeActivity.this;
		WelcomeActivity.this.rrapi=new RenrenApi(WelcomeActivity.this);
		WelcomeActivity.this.wbapi=new WeiboApi(WelcomeActivity.this);
        thisapp=new ThisApp();
    	renrenHandler = new Handler();
    	//thisapp.setRenrenPreferred(false);
    	loginBtn = (Button) findViewById(R.id.loginBtn);
    	logoutBtn = (Button) findViewById(R.id.logoutBtn);
        sleepBtn= (Button) findViewById(R.id.sleepBtn);
        badgeBtn= (Button) findViewById(R.id.badgeBtn);
        cfgBtn= (Button) findViewById(R.id.configBtn);
        mhTxt=(TextView) findViewById(R.id.minhourWelcomeTxt);
        secTxt=(TextView) findViewById(R.id.secWelcomeTxt);
        hideTipTxt=(TextView) findViewById(R.id.hideTipWelcomeTxt);
        timeFlagImg=(ImageView) findViewById(R.id.timeFlagImg);
        cBadge=new BadgeCheck(WelcomeActivity.this);
        zerozeroDF=new DecimalFormat("00");
        //post time handle
    	/*dtFmt_HHMM=new SimpleDateFormat("HH:mm");//HH 24hour, hh 12 hour
    	dtFmt_SS=new SimpleDateFormat("ss");
    	dtFmt_HH=new SimpleDateFormat("HH");*/
        timeHandler = new Handler();
        timeHandler.post(timeThread);
        //post anim handle
        bumpHandler = new Handler();
        bumpHandler.post(bumpThread);
        hideBtnHdl=new Handler();
        //init gesture detector
    	gDetector = new GestureDetector((OnGestureListener) this);    
        welcomerl=(RelativeLayout) findViewById(R.id.welcomeRL);
        welcomerl.setOnTouchListener(this);
        welcomerl.setLongClickable(true);
    	/*
        bgImg=(ImageView) findViewById(R.id.bgWelcomeImg);
        bgImg.setOnTouchListener(this);
        bgImg.setLongClickable(true);
        */
        bumpOutAnim = AnimationUtils.loadAnimation(this, R.anim.bump_out);
        //Stop strict mode checking
        try {
            Class strictModeClass=Class.forName("android.os.StrictMode");
            Class strictModeThreadPolicyClass=Class.forName("android.os.StrictMode$ThreadPolicy");
            Object laxPolicy = strictModeThreadPolicyClass.getField("LAX").get(null);
            Method method_setThreadPolicy = strictModeClass.getMethod(
                    "setThreadPolicy", strictModeThreadPolicyClass );
            method_setThreadPolicy.invoke(null,laxPolicy);
        } catch (Exception e) {
        		Log.d("Strict Mode","Error blocking strict mode.");
        }
        
        
    }
	private void setLayouts(){
		//set user fontface
        final Typeface tfTransponderAOE = Typeface.createFromAsset(getAssets(), "fonts/TransponderAOE.ttf");
        secTxt.setTypeface(tfTransponderAOE, Typeface.BOLD);
        final Typeface tfTMBGSTD = Typeface.createFromAsset(getAssets(), "fonts/TMBGSTD.ttf");
        mhTxt.setTypeface(tfTMBGSTD, Typeface.NORMAL);
    	//judge if there's network
    	if(!this.thisapp.getNetworkStatus()){
    		/*loginBtn.setEnabled(false);
    		logoutBtn.setEnabled(false);*/
    		Toast.makeText(thisContext, R.string.no_internet, Toast.LENGTH_SHORT).show();
    	}
	}
    
	public void setButtonAppearance(){
		thisapp.setRenrenLoginFlag(this.rrapi.hasLogin());
		thisapp.setWeiboLoginFlag(this.wbapi.hasLogin());
		if(thisapp.getRenrenPreferred()){
        	loginBtn.setBackgroundDrawable(getResources().getDrawable(RENREN_LOGIN_DRAWABLE));
        	logoutBtn.setBackgroundDrawable(getResources().getDrawable(RENREN_LOGOUT_DRAWABLE));
        }else{
        	loginBtn.setBackgroundDrawable(getResources().getDrawable(WEIBO_LOGIN_DRAWABLE));
        	logoutBtn.setBackgroundDrawable(getResources().getDrawable(WEIBO_LOGOUT_DRAWABLE));
        }
		if (thisapp.getRenrenPreferred()){
    		if(thisapp.getRenrenLoginFlag())
	    		showloginBtn(false);
	    		//Log.d("renren","login-false");
    		else
    			showloginBtn(true);
    		
    	}else{
    		if(thisapp.getWeiboLoginFlag())
	    		showloginBtn(false);
    		else
    			showloginBtn(true);
    	}
	}
    
    Runnable timeThread = new Runnable(){     
        public void run(){  
            timeHandler.postDelayed(timeThread, 1000);
            ca=Calendar.getInstance();
            int hour =ca.get(Calendar.HOUR_OF_DAY);
            if (hour>3 && hour<8) {
            	timeFlag=0;
            }else if(hour>=8 && hour<12){
            	
            	timeFlag=1;
            }else if(hour>=12 && hour<18){
            	timeFlag=2;
            }else if(hour>=18 && hour<23){
            	timeFlag=3;
            }else{
            	timeFlag=4;
            }
            int min=ca.get(Calendar.MINUTE);
            if(timeFlagLast != timeFlag){
            	timeFlagImg.setImageDrawable(getResources().getDrawable(timeFlagDrawable[timeFlag]));
            	timeFlagLast = timeFlag;
            }
            
            if(pastMin!=min){
            	timeFlagImg.setImageDrawable(getResources().getDrawable(timeFlagDrawable[timeFlag]));
            	pastMin=min;
            	thisapp.setSleepFlag(thisapp.AWAKE);
            }
            secTxt.setText(zerozeroDF.format(ca.get(Calendar.SECOND)));
            if (!thisapp.getUse24Hour() && hour>12)
            		hour-=12;
            mhTxt.setText(String.valueOf(hour)+":"+zerozeroDF.format(min));
            /*mhTxt.setText(dtFmt_HHMM.format(Calendar.getInstance().getTime()));
            secTxt.setText(dtFmt_SS.format(Calendar.getInstance().getTime()));*/
        }     
    };  
    
    
    Runnable bumpThread = new Runnable(){     
        public void run(){  
            bumpHandler.postDelayed(bumpThread, 900);
            
            if(thisapp.getNewBadgeFlag()){
            	if (badgeBtn.getVisibility()==View.INVISIBLE){
            		badgeBtn.setVisibility(View.VISIBLE);
            	}
            	badgeBtn.startAnimation(bumpOutAnim);
            }else if(badgeBtn.getVisibility()==View.VISIBLE && pastTime==-1){//not waiting
            	badgeBtn.setVisibility(View.INVISIBLE);
            }else if(thisapp.getSleepFlag()>thisapp.AWAKE && pastTime!=-1){//is waiting
            	sleepBtn.startAnimation(bumpOutAnim);
            }
        }
    };  
    
    /*LOGIN/LOGOUT buttons*/
    private void showloginBtn(boolean flag){
        if(flag){
            loginBtn.setVisibility(View.VISIBLE);
            logoutBtn.setVisibility(View.GONE);
        } else {
            loginBtn.setVisibility(View.GONE);
            logoutBtn.setVisibility(View.VISIBLE);
        }

    }
    private void showSleepDialog() {
		 final CheckBox checkbox; 
		 boolean isChecked;
		 LayoutInflater factory = LayoutInflater.from(this);
		 View view = factory.inflate(R.layout.wbyayactivity_dialog,null);
		 checkbox =(CheckBox) view.findViewById(R.id.tongbu);		
		 isChecked = checkbox.isChecked();
		 checkbox.setEnabled(rrapi.hasLogin()||wbapi.hasLogin());
		 ca=Calendar.getInstance();
         int temp = ca.get(Calendar.HOUR_OF_DAY);
		 XmlParser con = new XmlParser (WelcomeActivity.this);
         if (con.getConfigBool("use12hour")) 
         	if (temp>12) temp = temp - 12;
         String string = "好好考虑啊,现在是 "+String.valueOf(temp)
					+"点"+zerozeroDF.format(ca.get(Calendar.MINUTE))+"分。";
		 temp = ca.get(Calendar.HOUR_OF_DAY);
		 if (temp >= 23)   {
			 string = string +  "已经不早了哦，应该睡觉了哦~~";
		 }  else if  ((temp < 23) && (temp >= 21)) {
			 string = string +  "还不算太晚，真的要睡觉吗???";
		 }  else if  ( (temp < 21) && (temp >= 19) ) {
			 string = string +  "这么早就想睡觉吗？是不是心情不好啊？可以找个人聊聊啊~";
		 }  else if   (temp <= 5)  {
			 string = string + "都这么晚了还不睡，作死啊！！！！";	
		 }  else {
			 string = string +  "大白天的睡什么觉啊，昨天又刷夜了？";
			 cBadge.badge("bairizuomeng");
		 }
		 new AlertDialog.Builder(this)
		 .setTitle(R.string.wbyay_dialog_title)
		 .setMessage(string)
		 .setIcon(R.drawable.octocat_sleep)
		 .setPositiveButton(R.string.wbyay_dialog_PositiveButton , new DialogInterface.OnClickListener() {								
		     @Override
         public void onClick(DialogInterface dialog, int which) {	
		    	thisapp.setSyncFlag(checkbox.isChecked());
		    	 Intent intent = new Intent();
		    	intent.setClass(thisContext , SleepActivity.class);
		    	intent.putExtra("flag", checkbox.isChecked());
		    	startActivity(intent);
		    	cBadge.badge("tonggaiqianfei");
		    	overridePendingTransition(R.anim.maximize_left_bottom,0);
         }
		})
     .setNegativeButton(R.string.wbyay_dialog_NegativeButton , new DialogInterface.OnClickListener() {	
    	   @Override
           public void onClick(DialogInterface dialog, int which) {
    		   pastTime=0;
           }
		}).setView(view).
		   show();
		 hideBtnHdl.post(hideButtonThread);
		 hideTipTxt.setVisibility(View.VISIBLE);
		 sleepBtn.setVisibility(View.INVISIBLE);
		 
	}
    
    Runnable hideButtonThread=new Runnable(){
		@Override
		public void run() {
			if(pastTime<hideTime){
				if(hideTipTxt.getVisibility()==View.INVISIBLE)	hideTipTxt.setVisibility(View.VISIBLE);
				hideBtnHdl.postDelayed(hideButtonThread, 60000);
				pastTime++;
				String leftTime=String.valueOf(hideTime-pastTime+1);
				SpannableString ss = new SpannableString
						("睡觉按钮\n将在约"+leftTime+"分钟后原地(满血)复活");
				ss.setSpan(new RelativeSizeSpan((float)1.2), 0, 4,
		        		Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		        ss.setSpan(new ForegroundColorSpan(Color.rgb(242, 81, 69)), 8, 10+leftTime.length(),
		        		Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		        ss.setSpan(new RelativeSizeSpan((float)0.7), 13+leftTime.length(), 17+leftTime.length(),
		        		Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
				hideTipTxt.setText(ss);
			}else{
				pastTime=-1;
				hideTime=(int)(hideTime*Math.sqrt(Math.sqrt(hideTime)));
				hideTipTxt.setVisibility(View.INVISIBLE);
				sleepBtn.setVisibility(View.VISIBLE);

			}
		}
		
	};
  private void gotoCfgActivity(){
  		Intent i=new Intent(thisContext, ConfigActivity.class);
  		thisContext.startActivity(i);
  		overridePendingTransition(R.anim.maximize_right_top,R.anim.fade_out);
  }
  private void gotoBadgeActivity(){
	   badgeBtn.setVisibility(View.INVISIBLE);
	   Intent i=new Intent(thisContext, BadgeActivity.class);
	   thisContext.startActivity(i);
	   overridePendingTransition(R.anim.maximize_left_bottom,R.anim.fade_out);
  }

    private void login_rr(){
    	if(!thisapp.getNetworkStatus()){
    		Toast.makeText(thisContext, R.string.no_internet, Toast.LENGTH_SHORT).show();
    		return;
    	}
    	RenrenAuthListener rrlistener = new RenrenAuthListener() {
            /*success*/
            public void onComplete(Bundle values) {
                showloginBtn(false);           
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
        showloginBtn(true);
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
				showloginBtn(false);  
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
        showloginBtn(true);
        thisapp.setWeiboLoginFlag(false);
    }
	
    @Override
    public void onAttachedToWindow() {   
    	 if((thisapp.getSleepFlag()==thisapp.SLEEPING))
    		 this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD);//int=2002
    	  super.onAttachedToWindow();   
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	//getMenuInflater().inflate(R.menu.activity_badge, menu);
        return true;
    }
    

    public boolean onKeyDown(int keyCodeInt, KeyEvent event)
    {
    	/*
    	String s="test"+String.valueOf((int)1000D * Math.random());
    	wbapi.postWeibo(s, null);
    	Log.d("Weiboooooo",s);
    	*/
    	/*if(((keyCodeInt==KeyEvent.KEYCODE_BACK)||(keyCodeInt==KeyEvent.KEYCODE_HOME))
    		&&(thisapp.getSleepFlag()==thisapp.SLEEPING)){
        Toast.makeText(this, "你正在睡觉(～﹃～)~zZ不能退出哟~", 0).show();
    	}else */
    	if (keyCodeInt == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) { 
    		Intent home = new Intent(Intent.ACTION_MAIN);  
    		home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  
    		home.addCategory(Intent.CATEGORY_HOME);  
    		startActivity(home);  
    	}
    	while (true)
    		return super.onKeyDown(keyCodeInt, event);
    }

	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		// TODO Auto-generated method stub
		return gDetector.onTouchEvent(arg1);
	}


    
	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		if(Math.abs(velocityX) <= 0)	return false;
		//Log.d("Fling test!",String.valueOf(e1.getX())+"==="+String.valueOf(e1.getY()));
	    if (e2.getX() - e1.getX()>80 && e1.getY() - e2.getY() > 100 
	    		&& e1.getX()<thisapp.getLeftX() && e1.getY()>thisapp.getBottomY()){  
	    	//from L-B
	    	if(thisapp.getNewBadgeFlag())	gotoBadgeActivity();
	    	else
	    		if(pastTime==-1) showSleepDialog();
	    } else if (e1.getX() - e2.getX()>80 && e1.getY() - e2.getY() > 100 
	    		&& e1.getX()>thisapp.getRightX() && e1.getY()>thisapp.getBottomY()) {  
	    	//R-B
	    	if(thisapp.getRenrenPreferred()){
		    	if(!rrapi.hasLogin()) login_rr();
		    	else	logout_rr();
	    	}else{
	    		if(!wbapi.hasLogin()) login_wb();
		    	else	logout_wb();
	    	}
	    } else if (e2.getX() - e1.getX()>80 && e2.getY() - e1.getY() > 100 
	    		&& e1.getX()<thisapp.getLeftX() && e1.getY()<thisapp.getTopY()) {  
	    	//from L-T
	    } else if (e1.getX() - e2.getX()>80 && e2.getY() - e1.getY() > 100 
	    		&& e1.getX()>thisapp.getRightX() && e1.getY()<thisapp.getTopY()) {  
	    	//R-T
	    	gotoCfgActivity();
	    }  
	  
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




}
