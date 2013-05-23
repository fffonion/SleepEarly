package dotnet.aoye;




import java.text.DecimalFormat;
import java.util.Calendar;

import dotnet.aoye.R;
import dotnet.aoye.net.RenrenApi;
import dotnet.aoye.net.WeiboApi;
import dotnet.aoye.util.BadgeCheck;
import dotnet.aoye.util.XmlParser;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.app.AlertDialog;
import android.widget.Toast;

public class SleepActivity extends Activity {

	private static RenrenApi rrapi;
	private static WeiboApi wbapi;
	BadgeCheck cBadge;
	ThisApp thisapp;
	Handler btnHdl,hideHdl,backHdl;
	XmlParser parser;
	private static boolean isGotoDialog=false;
	private Button abandonBtn;
	private ImageView bgImg;
	private TextView mhTxt,secTxt,hideTipTxt;
	private Animation fadeOutAnim,fadeInAnim;
	private Handler timeHandler;
	private Calendar ca;
	private DecimalFormat zerozeroDF;
	private final String[] hints = {"睡觉的时候不要摸我嘛，人家会害羞的~","管住你的手，静下你的心",
			"睡不着的话可以躺着闭目养神哦~","如果要打电话发短信的话必须放弃睡觉才可以哦~~",
			"再按返回键……返回键就要变身了o(￣︶￣)n ","叫你不听话~","不！许！按！(╬￣皿￣)＝○＃(￣＃)３￣) ",
			"恭喜你已获得“一按成名”勋章←.←","既然已经开始睡觉就不要弄手机了哦~",
			"返回键已展开A.T.Field,前方高能注意","连按100次并转发@三位好友获得苦逼开发者果照一张(骗你的)",
			"啊哈哈哈啊哈哈哈哈哈哈哈哈哈哈哈哈哈啊哈哈哈啊哈哈啊啊哈哈哈啊哈哈哈哈啊哈哈啊哈哈啊哈哈哈哈",
			"七✪星✪龙✪珠✪出✪现✪了✪！","骚年，睡觉不玩手机是一门哲♂学"};
	
	private final String[] stopSleep = {"你想要燃烧青春不再睡觉么？","不可以后悔哦骚年~",
		"小知识:除了奥特曼以外的人都是要睡觉的~","小知识:睡眠不足有可能会导致shen虚啊~",
		"每天多睡一小时，真想再活五百年~","连续十天睡眠时间不超过5小时有可能变成超级赛亚人"};
	
	private final String[] postString={"啊我竟然在这个点起床了=。=","我是超级赛亚人我不用睡觉",
			"我真的是超级赛亚人我真的不用睡觉","我正在梦游。。。"};
	private static int hideTime=7,timeFlag, pastTime=-1,pastMin;//minutes
	
	/*
	public  SleepActivity(){
		String[] arrayOfString1 = new String[5];
	    arrayOfString1[0] = 
	    arrayOfString1[1] = 
	    arrayOfString1[2] = "管住你的手，静下你的心";
	    arrayOfString1[3] = "睡不着的话可以躺着闭目养神哦~";
	    arrayOfString1[4] = "如果需要打电话发短信的话必须放弃睡觉才可以哦~~";
	    this.hints = arrayOfString1;
	}
	*/
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE); //no title
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,       
                WindowManager.LayoutParams. FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sleep);
        init();
        abandonBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				bgImg.setAnimation(fadeOutAnim);
				bgImg.setImageDrawable(getResources().getDrawable(R.drawable.screaming));
				bgImg.setAnimation(fadeInAnim);
				SleepActivity.this.ShowDialog();
			}
		});	/*
            new CountDownTimer(18000000L, 1800000L)           
            {           
            	public void onFinish()
                {
            		thisapp.setSleepFlag(thisapp.AWAKE);
            		//SleepActivity.this.finish();
                }
                public void onTick(long paramLong){}
            }.start(); 
            */ 
        checkBadge();
 }

	@Override
    public void onAttachedToWindow() {   
    	 if((thisapp.getSleepFlag()==thisapp.SLEEPING))
    		 this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD);//int=2002
    	  super.onAttachedToWindow();   
    }
	public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
	  {
	    boolean bool = false;
	    if ((paramInt == 3) || (paramInt == 4))
	    {
	      int j = (int)(hints.length * Math.random());
	      Toast.makeText(this, this.hints[j], Toast.LENGTH_SHORT).show();
	        bgImg.setAnimation(fadeOutAnim);
			bgImg.setImageDrawable(getResources().getDrawable(R.drawable.sunflower));
			bgImg.setAnimation(fadeInAnim);
	    }
	    else
	    {
	      bool = super.onKeyDown(paramInt, paramKeyEvent);
	    }
	      return bool;
	  }
	@Override
	public void onStart(){
		isGotoDialog=false;
		Bundle bundle=getIntent().getExtras();
	        String sleepflag=bundle.getString("SleepFlag");
	       // thisapp.setSleepFlag(parser.getConfigInt(parser.SLEEP_FLAG_TAG,thisapp.DONT_SLEEP));
	        //if(sleepflag!=null)
	        	thisapp.setSleepFlag(thisapp.SLEEPING);
	        System.out.println(thisapp.getSleepFlag());
        super.onStart();
	}
	
	@Override
	public void onPause(){
		if(!SleepActivity.this.isFinishing()){
				if(!isGotoDialog){
					Toast.makeText(SleepActivity.this, R.string.back_to_sleep, Toast.LENGTH_SHORT).show();
					backHdl.postDelayed(backThread,2500);
					parser.setConfig(parser.SLEEP_FLAG_TAG,thisapp.getSleepFlag());
			}
		}else{
			thisapp.setSleepFlag(thisapp.AWAKE);
		}
		
		//SleepActivity.this.startActivity(intent);
		super.onPause();
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
	Runnable backThread=new Runnable(){
		@Override
		public void run() {
			Intent intent = new Intent(SleepActivity.this, SleepActivity.class);
			intent.putExtra("SleepFlag", "2");
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	        getApplication().startActivity(intent);
		}
		
	};
	private void ShowDialog(){
		isGotoDialog=true;
		 ca = Calendar.getInstance();
		 XmlParser parser = new XmlParser (SleepActivity.this);
		 int temp = ca.get(Calendar.HOUR_OF_DAY);
         if (parser.getConfigBool(parser.USE_24_HOUR_TAG)) 
         	if (temp>12) temp = temp - 12;
		 String hour = String.valueOf(temp);
		 String minute = String.valueOf(ca.get(Calendar.MINUTE));
		 String string;
		 if(ca.get(Calendar.HOUR_OF_DAY)>5 && ca.get(Calendar.HOUR_OF_DAY)<12
	    		 &&thisapp.getSleepFlag()==thisapp.SLEEPING){
		 		thisapp.setSleepFlag(thisapp.AWAKE);
		 		string = "现在是"+hour+"点"+minute+"分,可以起床了哟~";
				 new AlertDialog.Builder(this)
				 .setTitle("还没睡醒?")
				 .setMessage(string)
				 .setIcon(R.drawable.octocat_awake)
				 .setPositiveButton("知道了啦真啰嗦", new DialogInterface.OnClickListener() {								
				     @Override
		            public void onClick(DialogInterface dialog, int which) {		    	 
				    	if(ca.get(Calendar.HOUR_OF_DAY)<6)	cBadge.badge("wenjiqiwu");
				    	if(ca.get(Calendar.HOUR_OF_DAY)>10)	cBadge.badge("chaojilanzhu");
				    	Intent intent = new Intent();
				    	intent.putExtra("returnfromsleep", "1");
				    	intent.setClass(SleepActivity.this, WelcomeActivity.class);
						SleepActivity.this.startActivity(intent);
						overridePendingTransition(R.anim.fade_in, R.anim.minimize_right_bottom);
		            }
				}).
				show();
	     }else{
			 string = "现在是"+hour+"点"+minute+"分,"+stopSleep[(int)(stopSleep.length*Math.random())];
			 new AlertDialog.Builder(this)
			 .setTitle(R.string.sleeping_dialog_title)
			 .setMessage(string)
			 .setIcon(R.drawable.octocat_awake)
			 .setPositiveButton(R.string.sleeping_dialog_PositiveButton , new DialogInterface.OnClickListener() {								
			     @Override
	            public void onClick(DialogInterface dialog, int which) {		    	 
	               cBadge.badge("popomama");
	                bgImg.setAnimation(fadeOutAnim);
					bgImg.setImageDrawable(getResources().getDrawable(R.drawable.sunflower));
					bgImg.setAnimation(fadeInAnim);
					pastTime=0;
					hideHdl.post(hideButtonThread);
					hideTipTxt.setVisibility(View.VISIBLE);
					abandonBtn.setVisibility(View.INVISIBLE);
	            }
			})
	        .setNegativeButton(R.string.sleeping_dialog_NegativeButton , new DialogInterface.OnClickListener() {	
	       	   @Override
	              public void onClick(DialogInterface dialog, int which) {  
	     		   thisapp.setSleepFlag(thisapp.AWAKE);
	       		   SleepActivity.this.sleepFail();
	       		   //cBadge.badge("niuzaihenmang"); moved to sleepfail();
	              }
			}).
			show();
	     }
	}

	Runnable hideButtonThread=new Runnable(){
		@Override
		public void run() {
			if(pastTime<hideTime){
				hideHdl.postDelayed(hideButtonThread, 60000);
				pastTime++;
				String leftTime=String.valueOf(hideTime-pastTime+1);
				SpannableString ss = new SpannableString
						("起床按钮\n将在约"+leftTime+"分钟后原地(满血)复活");
				ss.setSpan(new RelativeSizeSpan((float)1.2), 0, 4,
		        		Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		        ss.setSpan(new ForegroundColorSpan(Color.rgb(69, 242, 122)), 8, 10+leftTime.length(),
		        		Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		        ss.setSpan(new RelativeSizeSpan((float)0.7), 13+leftTime.length(), 17+leftTime.length(),
		        		Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
				hideTipTxt.setText(ss);
			}else{
				pastTime=-1;
				hideTime=(int)(hideTime*Math.sqrt(Math.sqrt(hideTime)));
				hideTipTxt.setVisibility(View.INVISIBLE);
				abandonBtn.setVisibility(View.VISIBLE);

			}
		}
		
	};
	private void init(){
		 abandonBtn = (Button) findViewById(R.id.abandonSleepBtn);
	     bgImg=(ImageView) findViewById(R.id.bgSleepImg);
	     fadeInAnim= AnimationUtils.loadAnimation(this, R.anim.fade_in);
	     fadeOutAnim= AnimationUtils.loadAnimation(this, R.anim.fade_out);
	     cBadge=new BadgeCheck(SleepActivity.this);
	     mhTxt=(TextView) findViewById(R.id.minhourSleepTxt);
	     secTxt=(TextView) findViewById(R.id.secSleepTxt);
	     hideTipTxt=(TextView) findViewById(R.id.hideTipSleepTxt);
	     //post time handle
	     timeHandler = new Handler();
	     timeHandler.post(timeThread);
	     
	     backHdl=new Handler();
	     hideHdl=new Handler();
	     zerozeroDF=new DecimalFormat("00");
	     rrapi=new RenrenApi(this);
	     thisapp=new ThisApp();
	     parser=new XmlParser(this);
	     
	     final Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/TransponderAOE.ttf");
	     secTxt.setTypeface(tf, Typeface.BOLD);
	     final Typeface tfTMBGSTD = Typeface.createFromAsset(getAssets(), "fonts/TMBGSTD.ttf");
	     mhTxt.setTypeface(tfTMBGSTD, Typeface.NORMAL);
	     
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
            if(pastMin!=min){
            	if(hour>6 &&hour<12&& thisapp.getSleepFlag()==thisapp.SLEEPING &&thisapp.getScreenOnStatus())
            		SleepActivity.this.ShowDialog();
            	pastMin=min;
            }
            secTxt.setText(zerozeroDF.format(ca.get(Calendar.SECOND)));
            if (!thisapp.getUse24Hour() && hour>12)
            		hour-=12;
            mhTxt.setText(String.valueOf(hour)+":"+zerozeroDF.format(min));
            /*mhTxt.setText(dtFmt_HHMM.format(Calendar.getInstance().getTime()));
            secTxt.setText(dtFmt_SS.format(Calendar.getInstance().getTime()));*/
        }     
    };   
    private void checkBadge(){
    	ca=Calendar.getInstance();
    	int hour = ca.get(Calendar.HOUR_OF_DAY);
		 if (hour >= 23)   {
		 }  else if  ((hour < 23) && (hour >= 21)) {
			 cBadge.badge("guiyifomen");
		 }  else if  ( (hour < 21) && (hour >= 19) ) {
			 cBadge.badge("guiyifomen");
		 }  else if   (hour <= 5)  {
			 cBadge.badge("zinuekuangren");
		 }  else {
			// cBadge.badge("bairizuomeng");
		 }
    }
	private void sleepFail() {
		Handler postHdl=new Handler();
		Intent intent = new Intent();
		intent.putExtra("returnfromsleep", "2");
		intent.setClass(SleepActivity.this, WelcomeActivity.class);
		//thisapp.ShouldSyncFlag=true;
		SleepActivity.this.startActivity(intent);
		overridePendingTransition(R.anim.fade_in, R.anim.minimize_right_bottom);
		//post2Network();
		postHdl.postDelayed(postThread, 1500);
		//move nzhm to postThread
	}
	
		Runnable postThread=new Runnable() {
			@Override
			public void run() {
				if(thisapp.getNetworkStatus()){
					//Log.d("test time",String.valueOf(System.currentTimeMillis()));
					String str=postString[(int)(postString.length*Math.random())]+thisapp.getHomeLink();
					if(thisapp.getSyncFlag()){
						rrapi = new RenrenApi(SleepActivity.this);
					    if(rrapi.hasLogin())	rrapi.publishStatus(str);
					    }
					    wbapi=new WeiboApi(SleepActivity.this);
					    if(wbapi.hasLogin())	wbapi.postWeibo(str,null);
					//Log.d("test time",String.valueOf(System.currentTimeMillis()));
					/************************************************************************/
					//TOAST HERE!!!!
					/***********************************************************************/
					    cBadge.badge("niuzaihenmang");
				}
				    SleepActivity.this.finish();
			}
		};
			

	/*
	private void showSuccessButton(Boolean flag){
		
		if(flag){
            abandonBtn.setVisibility(View.GONE);
        } else {
            abandonBtn.setVisibility(View.VISIBLE);
        }		
        
	}
	
	private void gotoSuccessSleepActivity(){
		Intent intent = new Intent();
		//intent.setClass(SleepActivity.this, SuccessSleepActivity.class);
		SleepActivity.this.startActivity(intent);
		SleepActivity.this.finish();
		
	}
	*/

	
}
