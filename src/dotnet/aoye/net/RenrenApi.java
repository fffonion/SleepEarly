package dotnet.aoye.net;

import android.app.Activity;
import android.content.Context;

import com.renren.api.connect.android.AsyncRenren;
import com.renren.api.connect.android.Renren;
import com.renren.api.connect.android.status.StatusSetRequestParam;
import com.renren.api.connect.android.view.RenrenAuthListener;

import dotnet.aoye.R;
import dotnet.aoye.R.raw;
/**
 * Renren sdk wrapper 1.0
 * 
 * @author fffonion
 */
public class RenrenApi {
	public static final String APP_ID="207231";
	public static final String API_KEY = "541fb75289f94b76b43c1e6eeb172e72";
	/*
	public static final String SECRET_KEY = "9274838b9cb24281bc72e2b718b79732";
	*/
	public String SECRET_KEY;
	Renren renren;
	Activity activity;
	
	public RenrenApi(Activity pActivity){
		this.activity=pActivity;
		this.SECRET_KEY=pActivity.getString(R.string.RENREN_APP_SECRET);
		this.renren=new Renren("541fb75289f94b76b43c1e6eeb172e72",this.SECRET_KEY, "207231", pActivity);
		this.renren.init(pActivity);
	}
	/**
	 * Publish Status.
	 * 
	 * @param pString
	 * status string
	 */
	public void publishStatus(String pString)
	  {
	    StatusSetRequestParam localStatusSetRequestParam = new StatusSetRequestParam("#我不要熬夜了#"+pString);
	    new AsyncRenren(this.renren).publishStatus(localStatusSetRequestParam,null,true);
	  }
	
	/**
	 * Login. Use sdk webview.
	 * 
	 * @param prrlistener
	 * a RenrenAuthListener
	 */
	public void login(RenrenAuthListener prrlistener){
		
		this.renren.authorize(this.activity, prrlistener);
	}
	/**
	 * Logout.
	 * 
	 * @param pcontext
	 */
	public void logout(Context pcontext){
		this.renren.logout(pcontext);
	}
	/**
	 * Check if stored session key expired.
	 * 
	 * @return
	 */
	public boolean hasLogin()
	{
	   return this.renren.isSessionKeyValid();
	}
}
