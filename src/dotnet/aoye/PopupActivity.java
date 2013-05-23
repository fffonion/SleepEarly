package dotnet.aoye;

import java.io.IOException;

import dotnet.aoye.R;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class PopupActivity extends Activity {
	Handler hdl;
	Animation introm,introf,intros,amRotate,amFadein;
	TextView titleTv,achivTv;
	ImageView badgeImg,starImg,backTv,tipTv,titleBgImg;
	MediaPlayer mp;
	ThisApp thisapp;
	private int prompt_raw[]={R.raw.dong,R.raw.family_mart,R.raw.natsume,R.raw.nurarihyon,
			R.raw.yukiteru,R.raw.yuno,R.raw.pu,R.raw.hahaha_dogdays,R.raw.ahahaha_96_1,R.raw.ahahaha_96_2,R.raw.ahahaha_96_3,
			R.raw.ahahaha_96_4,R.raw.ahahaha_96_5,R.raw.ahahaha_96_6,R.raw.ahahaha_96_7};
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Bundle bundle=getIntent().getExtras();
        String method=bundle.getString("method");
        String promptId=bundle.getString("promptid");
        thisapp=new ThisApp();
        setContentView(R.layout.activity_popup);
        if (method==null){
        }
        else{
        	String badgename=bundle.getString("badgename");
        	if(promptId!=null)
        		mp= MediaPlayer.create(this, prompt_raw[Integer.parseInt(promptId)]);
        	else{
        		if(badgename.equals("wanjiebufu"))
        			mp= MediaPlayer.create(this, prompt_raw[4+(int)(2.0D* Math.random())]);
        		else
        			mp= MediaPlayer.create(this, prompt_raw[(int)( prompt_raw.length* Math.random())]);
        	}
        	
        	if(method.equals("popupbadge")){
    			popupbadge(badgename);
    		}else if(method.equals("showbadge"))
    			showbadge(badgename);
			}
    	}	
    
    
    private void popupbadge(String name){
    	initVals();
        mp.start();
        easterEgg01();
        badgeImg.setImageDrawable(getResources().getDrawable(thisapp.getBadgeDrawableBig(name)));
        titleTv.setText("解锁新成就：");
        titleBgImg.setImageDrawable(getResources().getDrawable(thisapp.getBadgeTitleBig(name)));
        startAnim();
        new Handler().postDelayed(new Runnable(){  
        	public void run(){
        		PopupActivity.this.finish();
        		overridePendingTransition(0, R.anim.crt_close);
        	}
        },4000);
    }
    
    private void easterEgg01() {
		// TODO Auto-generated method stub
		badgeImg.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				mp.start();
				return false;
			}
		});
	}


	private void showbadge(String name){
    	initVals();
    	//mp.start();
    	easterEgg01();
    	badgeImg.setImageDrawable(getResources().getDrawable(thisapp.getBadgeDrawableBig(name)));
        titleTv.setText("已获得成就：");
        titleBgImg.setImageDrawable(getResources().getDrawable(thisapp.getBadgeTitleBig(name)));
        startAnim();
        backTv=(ImageView) findViewById(R.id.backPpupImg);
        tipTv=(ImageView) findViewById(R.id.tipPpupImg);
        backTv.setVisibility(View.VISIBLE);
        achivTv.setVisibility(View.INVISIBLE);
        tipTv.setVisibility(View.INVISIBLE);
        backTv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				PopupActivity.this.finish();
        		overridePendingTransition(0, R.anim.crt_close);
			}
		});
        		
    }
    
    
    private void initVals(){
    	introm=AnimationUtils.loadAnimation(this, R.anim.flash_fade_in_deacc_medium);
        introf=AnimationUtils.loadAnimation(this, R.anim.flash_fade_in_deacc_fast);
        intros=AnimationUtils.loadAnimation(this, R.anim.flash_fade_in_deacc_slow);
        LinearInterpolator lin = new LinearInterpolator(); 
        amRotate = new RotateAnimation ( 0,+360, Animation.RELATIVE_TO_SELF, 0.5f, 
        			Animation.RELATIVE_TO_SELF, 0.5f ); 
        amRotate.setDuration (2000); 
        amRotate.setRepeatCount(Animation.INFINITE); 
        amRotate.setInterpolator(lin);
        amFadein=new AlphaAnimation(0, 1);
        amFadein.setDuration(500);
        amFadein.setStartOffset(500);
        amFadein.setRepeatCount (1);
		try {
			mp.prepare();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		titleTv= (TextView) findViewById(R.id.titlePpupTv);
        titleBgImg= (ImageView) findViewById(R.id.badgeNamePpupImg);
        achivTv= (TextView) findViewById(R.id.badgeAchievedPpupTv);
        achivTv.setText("已完成成就："+
        		String.valueOf(100*thisapp.getBadgeGotCount()/thisapp.getBadgeCount())+"%");
        badgeImg= (ImageView) findViewById(R.id.badgePpupImg);
        starImg= (ImageView) findViewById(R.id.starPpupImg);
        starImg.setAnimation (amRotate);
        hdl=new Handler();
    }
    
    private void startAnim(){
    	 titleTv.startAnimation(introm);
         titleBgImg.startAnimation(introf);
         badgeImg.startAnimation(intros);
         amFadein.startNow();
         amRotate.startNow ();
         //hdl.post(bumpOut);
         
    }
    Runnable bumpOut=new Runnable(){

		@Override
		public void run() {
			hdl.postDelayed(bumpOut, 500);
			//starImg.startAnimation(bumpanim);
			
		}
    	
    };
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.activity_popup, menu);
        return true;
    }
    /*
    @Override
    public void onPause(){
    	super.onPause();
    	this.finish();
    }
    */
    @Override
    public void onDestroy(){
    	mp.release();
    	super.onDestroy();
    }
}
