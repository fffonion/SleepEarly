package dotnet.aoye;


import java.lang.annotation.Target;

import dotnet.aoye.R;
import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.GestureDetector.OnGestureListener;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

public class BadgeActivity extends Activity implements OnGestureListener,OnTouchListener{
	ThisApp thisapp;
	private GestureDetector gDetector;
	private RelativeLayout badgerl;
	private ImageView bgImg;
	/*popomama,guiyifomen,tonggaiqianfei,
		bairizuomeng,zinuekuangren,chaojilanzhu,wenjiqiwu,wanjiebufu,niuzaihenmang;*/
	private ImageView BadgeImgPicV[], BadgeImgMaskV[];
	private Button backBtn;
	//XmlParser parser;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,       
                WindowManager.LayoutParams. FLAG_FULLSCREEN);*/
        setContentView(R.layout.activity_badge);
        //back to home
        
        init();
        thisapp.setNewBadgeFlag(false);
        if (android.os.Build.VERSION.SDK_INT>10){
        	//v10_back_button();
        }
        backBtn=(Button) findViewById(R.id.backBadgeBtn);
        backBtn.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				BadgeActivity.this.onStop();
			}
        	
        });
        /*
        for(int i=0;i<thisapp.BADGE_COUNT;i++){
        	final int ii=i;
        	if(BadgeImgPicV[i].getDrawable()==getResources().getDrawable(R.drawable.frame_nomask))
        		//setArrayClickListener(i);
        		BadgeImgMaskV[ii].setOnClickListener(new View.OnClickListener() {
                	@Override
        			public void onClick(View v) {
        				// TODO Auto-generated method stub
        				Intent intent=new Intent(BadgeActivity.this, PopupActivity.class);
        				intent.putExtra("badgename",thisapp.getBadgeName(ii));
        				intent.putExtra("method","showbadge");
        				BadgeActivity.this.startActivity(intent);
        			}
                });
        }
        */
        	
    }
    @TargetApi(11)
	private void v10_back_button() {
	    	ActionBar actionBar = getActionBar();
	    	actionBar.setDisplayHomeAsUpEnabled(true);
	}
    
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(this, WelcomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
	/*
	private void setArrayClickListener(final int index){
		BadgeImgV[index].setOnClickListener(new View.OnClickListener() {
        	@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(BadgeActivity.this, PopupActivity.class);
				intent.putExtra("badgename",thisapp.getBadgeName(index));
				intent.putExtra("method","showbadge");
				BadgeActivity.this.startActivity(intent);
			}
        });
    }
    */
    private void init(){
    	/*
    	 //init gesture detector
    	gDetector = new GestureDetector((OnGestureListener) this);    
        badgerl=(RelativeLayout) findViewById(R.id.badgeRL);
        badgerl.setOnTouchListener(this);
        badgerl.setLongClickable(true);
    	
        bgImg=(ImageView) findViewById(R.id.bgImg);
        bgImg.setOnTouchListener(this);
        bgImg.setLongClickable(true);
        */
    	 thisapp=new ThisApp();
    	 //parser = new XmlParser(BadgeActivity.this);
    	 BadgeImgPicV=new ImageView[thisapp.getBadgeCount()];
    	 BadgeImgMaskV=new ImageView[thisapp.getBadgeCount()];
    	 for(int i=0;i<thisapp.getBadgeCount();i++){
    		 BadgeImgPicV[i]=(ImageView) findViewById(thisapp.getBadgeImgPicId(i));
    		 BadgeImgMaskV[i]=(ImageView) findViewById(thisapp.getBadgeImgMaskId(i));
    		//BadgeImgPicV[i].setVisibility(View.INVISIBLE);
    	 }
    	 /*
    	 popomama = (ImageView) findViewById(R.id.popomama);
         guiyifomen = (ImageView) findViewById(R.id.guiyifomen);
         tonggaiqianfei = (ImageView) findViewById(R.id.tonggaiqianfei);
         bairizuomeng = (ImageView) findViewById(R.id.bairizuomeng);
         zinuekuangren = (ImageView) findViewById(R.id.zinuekuangren);
         chaojilanzhu = (ImageView) findViewById(R.id.chaojilanzhu);
         wenjiqiwu = (ImageView) findViewById(R.id.wenjiqiwu);
         wanjiebufu = (ImageView) findViewById(R.id.wanjiebufu);
         niuzaihenmang = (ImageView) findViewById(R.id.niuzaihenmang);
         popomama.setVisibility(View.GONE);
         guiyifomen.setVisibility(View.GONE);
         tonggaiqianfei.setVisibility(View.GONE);
         bairizuomeng.setVisibility(View.GONE);
         zinuekuangren.setVisibility(View.GONE);
         chaojilanzhu.setVisibility(View.GONE);
         wenjiqiwu.setVisibility(View.GONE);
         wanjiebufu.setVisibility(View.GONE);
         niuzaihenmang.setVisibility(View.GONE);
         */
        
         for(int i=0;i<thisapp.getBadgeCount();i++){
        	 if(thisapp.getBadgeStatus(i)){
        	 //if(true){
        		//show
        		 BadgeImgMaskV[i].setImageDrawable(getResources().getDrawable(R.drawable.frame_nomask));
        		//setArrayClickListener(i);
         		final int ii=i;
        		BadgeImgMaskV[ii].setOnClickListener(new View.OnClickListener() {
                 	@Override
         			public void onClick(View v) {
         				// TODO Auto-generated method stub
         				Intent intent=new Intent(BadgeActivity.this, PopupActivity.class);
         				intent.putExtra("badgename",thisapp.getBadgeName(ii));
         				intent.putExtra("method","showbadge");
         				BadgeActivity.this.startActivity(intent);
         			}
                 });
        	 }
        	 
         }
        	 /*ImgView.Visblty		Badge.status		action
        	  *-----------------------------------------------
        	  * true				true				none
        	  * true				false				NANI?!
        	  * false				true				setVisblty(View.VISIBLE)
        	  * false				false				none
        	  */
         
         /*
         parser = new XmlParser("popomama",BadgeActivity.this);
         if (parser.ImageViewcheck()) popomama.setVisibility(View.VISIBLE);
         
         
         parser = new XmlParser("guiyifomen",BadgeActivity.this);
         if (parser.ImageViewcheck()) guiyifomen.setVisibility(View.VISIBLE);
         
         
         parser = new XmlParser("tonggaiqianfei",BadgeActivity.this);
         if (parser.ImageViewcheck()) tonggaiqianfei.setVisibility(View.VISIBLE);
         
         
         parser = new XmlParser("bairizuomeng",BadgeActivity.this);
         if (parser.ImageViewcheck()) bairizuomeng.setVisibility(View.VISIBLE);
         
         
         parser = new XmlParser("zinuekuangren",BadgeActivity.this);
         if (parser.ImageViewcheck()) zinuekuangren.setVisibility(View.VISIBLE);
         
         parser = new XmlParser("chaojilanzhu",BadgeActivity.this);
         if (parser.ImageViewcheck()) chaojilanzhu.setVisibility(View.VISIBLE);
         
         parser = new XmlParser("wenjiqiwu",BadgeActivity.this);
         if (parser.ImageViewcheck()) wenjiqiwu.setVisibility(View.VISIBLE);
         
         parser = new XmlParser("niuzaihenmang",BadgeActivity.this);
         if (parser.ImageViewcheck()) niuzaihenmang.setVisibility(View.VISIBLE);
         
         parser = new XmlParser("wanjiebufu",BadgeActivity.this);
         if (parser.ImageViewcheck()) wanjiebufu.setVisibility(View.VISIBLE);
         */
  	}
    
    @Override
    public void onStop(){
    	super.onStop();
    	this.finish();
    	overridePendingTransition(0,R.anim.minimize_right_top);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.activity_badge, menu);
        return true;
    }

	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		// TODO Auto-generated method stub
		return gDetector.onTouchEvent(arg1);
	}

	@Override
	public boolean onDown(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onFling(MotionEvent arg0, MotionEvent arg1, float arg2,
			float arg3) {
		// TODO Auto-generated method stub
		Toast.makeText(BadgeActivity.this, "别摸我", Toast.LENGTH_SHORT).show();
		//bgImg.layout(0, t, 0, 0)
		return false;
	}

	@Override
	public void onLongPress(MotionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2,
			float arg3) {
		
		return false;
	}

	@Override
	public void onShowPress(MotionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}

    
}
