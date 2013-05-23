package dotnet.aoye;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
public class DieActivity extends Activity {
	private Button receive;
	private RenrenApi renrenApi;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE); //no title
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,       
                WindowManager.LayoutParams. FLAG_FULLSCREEN);
		setContentView(R.layout.activity_die);
		receive = (Button) findViewById(R.id.receive);
		receive.setOnClickListener(new ReceiveListener ());
		
	}
	
	/*public void onAttachedToWindow()	  {
	    getWindow().setType(2004);
	    super.onAttachedToWindow();
	}*/
	

	 public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
	  {
	    boolean bool = false;
	    if ((paramInt == 3) || (paramInt == 4)) {
	      Toast.makeText(this, "不发状态就想退出么亲~", Toast.LENGTH_SHORT).show();
	    }
	    else {
	      bool = super.onKeyDown(paramInt, paramKeyEvent);
	    }
	    return bool;
	  }
	  
	  
   class ReceiveListener implements OnClickListener    {

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = getIntent();
		Boolean bool = intent.getBooleanExtra("flag", true);
		Time t=new Time("GMT+8");
		t.setToNow();
		if ((bool) && (t.hour < 3) ) publish(); 
		finish();
	}
	   
   }
   
   public void publish()
   {
     this.renrenApi = new RenrenApi(this);
     this.renrenApi.publishStatus("我又没有把持住自己！！！我又在应该睡觉的时候玩手机！！我该死！！！求鄙视！！！");
     finish();
   }
   
   public void onStop()
   {
	 Intent intent = getIntent();
     Boolean bool = intent.getBooleanExtra("flag", true);
	 if (bool) publish(); 
	 super.onStop();
	 finish();
   }
}
