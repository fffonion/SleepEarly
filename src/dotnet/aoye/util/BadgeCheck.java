package dotnet.aoye.util;

import dotnet.aoye.PopupActivity;
import dotnet.aoye.ThisApp;
import dotnet.aoye.net.RenrenApi;
import dotnet.aoye.net.WeiboApi;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
/**
 * Check badge status.
 * 
 * @author bingo,fffonion
 *
 */
public class BadgeCheck {
	
    private static Activity activity;
    private static RenrenApi rrapi;
    private static WeiboApi wbapi;
    private static String postContent;
    XmlParser parser;
    ThisApp thisapp;
    private int value;
    private int valueStd;
    public BadgeCheck(Activity activity){
    	this.activity = activity ;
    	rrapi = new RenrenApi(activity);
    	wbapi=new WeiboApi(activity);
    	parser=new XmlParser(activity);
    	thisapp=new ThisApp();
    }
    /*
     * BadgeName={"popomama","guiyifomen","tonggaiqianfei","bairizuomeng",
			"zinuekuangren","chaojilanzhu","wenjiqiwu","wanjiebufu","niuzaihenmang"};
     */
    
    /**
     * Check badge status and post to renren and weibo.
     * 
     * @param name
     * badge name
     */
    public void badge(String name){		
		Toast toast;
		String badgeName=thisapp.getBadgeNameCN(name);
		parser.setBadgeStatusAdd(name);
		if (this.check(name,false)) {
			toast = Toast.makeText(activity, "“"+badgeName+"”成就达到", Toast.LENGTH_LONG);
        	toast.setGravity(Gravity.CENTER, 0, 0);
       	    toast.show();
       	    thisapp.setNewBadgeFlag(true);
       	    Intent i=new Intent(activity, PopupActivity.class);
       	    i.putExtra("badgename",name);
       	    i.putExtra("method","popupbadge");
       	    activity.startActivity(i);
       	 if(thisapp.getSyncBadge()){
       		postContent="我刚才在「我不要熬夜了」中达到了“"+badgeName+"”成就！"+thisapp.getAdString();
       		Handler postHdl=new Handler();
       		postHdl.postDelayed(postThread,2000);
       	 }
		}
		if(name.equals(thisapp.getBadgeName(4)))	this.badge(thisapp.getBadgeName(7));
	}
    Runnable postThread=new Runnable(){
		@Override
		public void run() {
			if(thisapp.getNetworkStatus()){
				Log.d("tag","start");
	       	    if(rrapi.hasLogin()) rrapi.publishStatus(postContent);
	       	    if(wbapi.hasLogin()) wbapi.postWeibo(postContent, null);
			}
		}
    	
    };
    /**
     * Check badge status and post to renren and weibo.
     * 
     * @param i
     * badge order number
     */
    public void badge(int i){		
		String name=thisapp.getBadgeName(i);
		this.badge(name);
	}

    /**
     * Check if badge achieved
     * 
     * @param name
     * @return
     */
    public boolean check(String name,boolean firstcheck){
		value = parser.getBadgeStatus(name);
		valueStd = thisapp.getBadgeStd(name);
		if ((value == valueStd && !firstcheck)||(value >= valueStd && firstcheck)){
			thisapp.setBadgeStatus(name,true);
			return true;
		}
		else return false;
    }
    /**
     * Check if badge achieved
     * 
     * @param i
     * badge order number
     * @return
     */
    public boolean check(int i,boolean firstcheck){
		String name=thisapp.getBadgeName(i);
    	return this.check(name,firstcheck);
    }
    /*
    public void znkrcheck(){		
		Toast toast;
		XmlParser Znkr = new XmlParser ("zinuekuangren",activity);
		Znkr.XmlIn();	
		Znkr.XmlOut();
		if (Znkr.check()) {
			toast = Toast.makeText(activity, "“自虐狂人”成就达到", Toast.LENGTH_LONG);
        	toast.setGravity(Gravity.CENTER, 0, 0);
       	    toast.show();
       	 rrapi.publishStatus("“自虐狂人”成就达到");
		}
		Znkr = new XmlParser ("wanjiebufu",activity);
		Znkr.XmlIn();	
		Znkr.XmlOut();
		if (Znkr.check()) {
			toast = Toast.makeText(activity, "“万劫不复”成就达到", Toast.LENGTH_LONG);
        	toast.setGravity(Gravity.CENTER, 0, 0);
       	    toast.show();
       	 rrapi.publishStatus("“万劫不复”成就达到");
		}
	}
	public void brzmcheck(){
		Toast toast;
		XmlParser Brzm = new XmlParser ("bairizuomeng",activity);
		Brzm.XmlIn();
		Brzm.XmlOut();
		if (Brzm.check()) {
			toast = Toast.makeText(activity, "“白日做梦”成就达到", Toast.LENGTH_LONG);
        	toast.setGravity(Gravity.CENTER, 0, 0);
       	    toast.show();
       	 rrapi.publishStatus("“白日做梦”成就达到");
		}

	}
	
	public void gyfmcheck(){
		Toast toast;
		XmlParser Gyfm = new XmlParser ("guiyifomen",activity);
		Gyfm.XmlIn();
		Gyfm.XmlOut();
		if (Gyfm.check()) {
			toast = Toast.makeText(activity, "“皈依佛门”成就达到", Toast.LENGTH_LONG);
        	toast.setGravity(Gravity.CENTER, 0, 0);
       	    toast.show();
       	 rrapi.publishStatus("“皈依佛门”成就达到");
		}
	}
	
	public static void ppmmcheck(){
		 Toast toast;
		 XmlParser Ppmm = new XmlParser("popomama",activity);             	 
	     Ppmm.XmlIn();
	     Ppmm.XmlOut();
	     if (Ppmm.check()) {
			toast = Toast.makeText(activity, "“婆婆妈妈”成就达到", Toast.LENGTH_SHORT);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
			rrapi.publishStatus("“婆婆妈妈”成就达到");
		 }
	}
	
	public void nzhmcheck(){
		 Toast toast;
		 XmlParser Nzhm = new XmlParser("niuzaihenmang",activity);             	 
       Nzhm.XmlIn();
       Nzhm.XmlOut();
       if (Nzhm.check()) {
      	 toast = Toast.makeText(activity, "“牛仔很忙”成就达到", Toast.LENGTH_LONG);
      	 toast.setGravity(Gravity.CENTER, 0, 0);
      	 toast.show();
      	rrapi.publishStatus("“牛仔很忙”成就达到");
		 }
	}
	
	public void wjqwcheck(){
		 Toast toast;
		 XmlParser Nzhm = new XmlParser("wenjiqiwu",activity);             	 
       Nzhm.XmlIn();
       Nzhm.XmlOut();
       if (Nzhm.check()) {
      	 toast = Toast.makeText(activity, "“闻鸡起舞”成就达到", Toast.LENGTH_LONG);
      	 toast.setGravity(Gravity.CENTER, 0, 0);
      	 toast.show();
      	 rrapi.publishStatus("“闻鸡起舞”成就达到");
		 }
	}
	
	public void cjlzcheck(){
		 Toast toast;
		XmlParser Nzhm = new XmlParser("chaojilanzhu",activity);             	 
      Nzhm.XmlIn();
      Nzhm.XmlOut();
      if (Nzhm.check()) {
				toast = Toast.makeText(activity, "“超级懒猪”成就达到", Toast.LENGTH_LONG);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
				rrapi.publishStatus("“超级懒猪”成就达到");
		 }
	}
	
	public void tgqfcheck(){
		 Toast toast;
		XmlParser Tgqf = new XmlParser ("tonggaiqianfei",activity);
		Tgqf.XmlIn();
		Tgqf.XmlOut();
		if (Tgqf.check()) {
			toast = Toast.makeText(activity, "“痛改前非”成就达到", Toast.LENGTH_LONG);
     	    toast.setGravity(Gravity.CENTER, 0, 0);
     	    toast.show();
      	rrapi.publishStatus("“痛改前非”成就达到");
		}
	}
	*/
}
