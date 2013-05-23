package dotnet.aoye;

import java.util.ArrayList;
import java.util.List;

import dotnet.aoye.R;
import dotnet.aoye.util.XmlParser;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.GestureDetector.OnGestureListener;
import android.view.View.OnTouchListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class GuideActivity extends Activity{

	private ViewPager mPager;
	private List<View> listViews;
	//private ImageView cursor;
	private int offset = 0,drawableWidth;
	//private int curIndex = 0;
	private int pageCount,flipPos;
	private int rrPkgCount,wbPkgCount;
	private float initpos;
	public GestureDetector gDetector; 
	Handler checkPkgHdl;
	ThisApp thisapp;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE); //no title
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,       
        WindowManager.LayoutParams. FLAG_FULLSCREEN);
		setContentView(R.layout.activity_guide);
		thisapp=new ThisApp();
		InitImageView();
		InitViewPager();
		getScreenScale();
		//InitBadge();
		/*checkPkgHdl=new Handler();
		checkPkgHdl.post(checkPkgThread);*/
		checkPkg();
		//check12_24();
	}

	private void check12_24() {
		ContentResolver cv = this.getContentResolver();
		if(android.provider.Settings.System.getString(cv,
				android.provider.Settings.System.TIME_12_24).equals("24"))
		  thisapp.setUse24Hour(true);
		
	}

	private void checkPkg() {
		Log.d("test time",String.valueOf(System.currentTimeMillis()));
		final PackageManager pm = getPackageManager(); 
		List<ApplicationInfo> packages = pm
	                .getInstalledApplications(PackageManager.GET_META_DATA);
	        for (ApplicationInfo packageInfo : packages) {
	            String pkg=packageInfo.packageName;
	        	if((pkg.indexOf("weico")!=-1)||(pkg.indexOf("weibo")!=-1))
	        		wbPkgCount++;
	        	else if (pkg.indexOf("renren")!=-1)
	        		rrPkgCount++;
	        }
	        if (rrPkgCount>wbPkgCount)
	        	thisapp.setRenrenPreferred(true);
	        Log.d("test time",String.valueOf(System.currentTimeMillis()));
	}

	private void getScreenScale() {
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		XmlParser parser=new XmlParser(GuideActivity.this);
		parser.setConfig(parser.SCREEN_WIDTH_CONFIG_TAG, dm.widthPixels);
		parser.setConfig(parser.SCREEN_HEIGHT_CONFIG_TAG, dm.heightPixels);
		thisapp.setScreenHeight(parser.getConfigInt(parser.SCREEN_HEIGHT_CONFIG_TAG));
		thisapp.setScreenWidth(parser.getConfigInt(parser.SCREEN_WIDTH_CONFIG_TAG));
	}
/*
	Runnable checkPkgThread=new Runnable(){
		public void run(){ 
			Log.d("test time",String.valueOf(System.currentTimeMillis()));
			final PackageManager pm = getPackageManager(); 
			List<ApplicationInfo> packages = pm
		                .getInstalledApplications(PackageManager.GET_META_DATA);
		        for (ApplicationInfo packageInfo : packages) {
		            String pkg=packageInfo.packageName;
		        	if((pkg.indexOf("weico")!=-1)||(pkg.indexOf("weibo")!=-1))
		        		wbPkgCount++;
		        	else if (pkg.indexOf("renren")!=-1)
		        		rrPkgCount++;
		        }
		        if (rrPkgCount>wbPkgCount)
		        	thisapp.setRenrenPreferred(true);
		        Log.d("test time",String.valueOf(System.currentTimeMillis()));
		}
	};
	*/
	private void startMain(){
		Intent in =new Intent();
	    in.setClass(GuideActivity.this,WelcomeActivity.class);
	    GuideActivity.this.startActivity(in);
    	overridePendingTransition(R.anim.fade_in,R.anim.crt_close);
    	Intent i=new Intent(GuideActivity.this, PopupActivity.class);
   	    i.putExtra("badgename","xinshoushanglu");
   	    i.putExtra("method","popupbadge");
   	    i.putExtra("promptid", "6");
   	    thisapp.setNewBadgeFlag(true);
   	    GuideActivity.this.startActivity(i);
    	GuideActivity.this.finish();
	}
	private void showCfg(){
		Intent in =new Intent();
	    in.setClass(GuideActivity.this,ConfigActivity.class);
	    GuideActivity.this.startActivity(in);
    	overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
	}
	/*
	private void InitBadge(){
	    SharedPreferences  user = this.getSharedPreferences("badge",0);
	    SharedPreferences.Editor editor = user.edit();
	    editor.putInt("bairizuomeng", 0);
	    editor.putInt("guiyifomen", 0);
	    editor.putInt("wenjiqiwu", 0);
	    editor.putInt("zinuekuangren", 0);
	    editor.putInt("chaojilanzhu", 0);
	    editor.putInt("wanjiebufu", 0);
	    editor.putInt("tonggaiqianfei", 0);
	    editor.putInt("niuzaihenmang", 0);
	    editor.putInt("popomama", 0);
	    editor.putInt("bairizuomengStd", 3);
	    editor.putInt("guiyifomenStd", 5);
	    editor.putInt("wenjiqiwuStd", 1);
	    editor.putInt("zinuekuangrenStd", 3);
	    editor.putInt("chaojilanzhuStd", 1);
	    editor.putInt("wanjiebufuStd", 10);
	    editor.putInt("tonggaiqianfeiStd", 1);
	    editor.putInt("niuzaihenmangStd", 3);
	    editor.putInt("popomamaStd", 5);
	    editor.commit();
	}
	*/
	private void InitViewPager() {
		mPager = (ViewPager) findViewById(R.id.vPager);
		listViews = new ArrayList<View>();
		LayoutInflater mInflater = getLayoutInflater();
		listViews.add(mInflater.inflate(R.layout.guide_1, null));
		listViews.add(mInflater.inflate(R.layout.guide_2, null));
		listViews.add(mInflater.inflate(R.layout.guide_3, null));
		pageCount=3;
		mPager.setAdapter(new MyPagerAdapter(listViews));
		mPager.setCurrentItem(0);
		mPager.setOnPageChangeListener(new ViewPageListener());
	}

	private void InitImageView() {
		/*
		cursor = (ImageView) findViewById(R.id.cursor);
		drawableWidth = BitmapFactory.decodeResource(getResources(), R.drawable.cursor_dark)
				.getWidth();
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenW = dm.widthPixels;
		offset = (int)(screenW *0.3 / 3 - drawableWidth) / 2;
		initpos= (float) (screenW *0.35);
		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);
		cursor.setImageMatrix(matrix);
		*/
	}

	public class MyPagerAdapter extends PagerAdapter {
		public List<View> mListViews;

		public MyPagerAdapter(List<View> mListViews) {
			this.mListViews = mListViews;
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView(mListViews.get(arg1));
		}

		@Override
		public void finishUpdate(View arg0) {
		}

		@Override
		public int getCount() {
			return mListViews.size();
		}

		@Override
		public Object instantiateItem(final View collection, int position) {
			int resId=0;
			
			View v = new View(collection.getContext());
			/*
		    LayoutInflater inflater = (LayoutInflater) collection.getContext()
		            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		    switch (position){
		    case 0:
		    	resId = R.layout.guide_1;
		    	break;
		    case 1:
		    	resId = R.layout.guide_2;
		    	break;
		    case 2:
		    	resId = R.layout.guide_3;
		    	 v = inflater.inflate(R.layout.guide_3, null, false);
		         RelativeLayout r = (RelativeLayout) v.findViewById(R.id.guide3rl);
		    	sttWlcmBtn=(Button)r.findViewById(R.id.startWelcomeBtn);
		    	sttWlcmBtn.setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View v) {
	            	Toast.makeText(collection.getContext(),"click",Toast.LENGTH_LONG).show();
	               	Intent in =new Intent();
	        	    in.setClass(collection.getContext(),WelcomeActivity.class);
	        	    collection.getContext().startActivity(in);
	            	overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
	            }
	        });
			break;
		    }*/
		    ((ViewPager) collection).addView(mListViews.get(position), 0);
			return mListViews.get(position);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == (arg1);
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {
		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View arg0) {
		}
	}


	public class ViewPageListener implements OnPageChangeListener {

		int one = offset * 2 + drawableWidth+(int)initpos;
		int two =(offset * 2 + drawableWidth)* 2+(int)initpos;

		@Override
		public void onPageSelected(int arg0) {
			/*if (arg0==3){
               	Intent in =new Intent();
        	    in.setClass(GuideActivity.this,WelcomeActivity.class);
        	    GuideActivity.this.startActivity(in);
            	overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
			}*/
			/*
			Animation animation = null;
			switch (arg0) {
			case 0:
					animation = new TranslateAnimation(one, initpos,0, 0);
				break;
			case 1:
				if (curIndex == 0) {
					animation = new TranslateAnimation(initpos, one, 0, 0);
				} else if (curIndex == 2) {
					animation = new TranslateAnimation(two, one, 0, 0);
				}
				break;
			case 2:
					animation = new TranslateAnimation(one, two, 0, 0);
				break;
			}
			curIndex = arg0;
			animation.setFillAfter(true);
			animation.setDuration(300);
			cursor.startAnimation(animation);
			*/
		}

		@Override
		public void onPageScrolled(int pagePos, float arg1, int arg2) {
			//arg2 is pixes position of the boarder of view
			/*
			if(pagePos==pageCount-1 && arg2>=flipPos){
				GuideActivity.this.startMain();
			}else{
				flipPos=arg2;
			}
			*/
			if(pagePos==pageCount-2){
				final Button btn2=(Button) mPager.findViewById(R.id.btnGuide2);
				btn2.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						GuideActivity.this.showCfg();
						btn2.setVisibility(View.INVISIBLE);
					}
				});
			}else if(pagePos==pageCount-1){
				Button btn3=(Button) mPager.findViewById(R.id.btnGuide3);
				btn3.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						GuideActivity.this.startMain();
					}
				});
			}
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	}


	
}