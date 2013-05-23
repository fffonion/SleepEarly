
import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;
//not a bootable activity
public class Prefs extends Activity{
	public final static String PREF_FILE = "preference";
	public final static String BADGE_FILE= "badge";
	//build-in preference tags
	public final static String SLEEP_TIME="sleep_time";
	public final static String RENREN_LOGIN="renren_has_login";
	public final static String WEIBO_LOGIN="weibo_has_login";
	public final static String SHOW_TIP="show_tip_on_picture";
	public final static String HASH="md5_value";
	//build-in badge tags
	public final static String BADGE_A="badge_aahahah";
	public String prefs;
	public boolean badge_status;
	public SharedPreferences.Editor editor;
	public SharedPreferences sp;
	public Prefs(){
	}
	
	public String getConfigByTag(String Tag){
		sp= getSharedPreferences(PREF_FILE,0);  
		prefs = sp.getString(Tag, null);
		return prefs;
	}
	public void setConfigByTag(String Tag,String StringOfTag){
		sp= getSharedPreferences(PREF_FILE,0);
		Log.d("prefs","here2");
		editor= sp.edit();  
		Log.d("prefs","here2");
		editor.putString(Tag,StringOfTag);
		Log.d("prefs","here2");
		updateFile(PREF_FILE);
	}
	
	public void setConfigByTag(String Tag,boolean booleanOfTag){
		sp= getSharedPreferences(PREF_FILE,3);
		editor= sp.edit();  
		editor.putBoolean(Tag,booleanOfTag);  
		updateFile(PREF_FILE);
	}
	public void setConfigByTag(String Tag,long longOfTag){
		sp= getSharedPreferences(PREF_FILE,0);
		editor= sp.edit();  
		editor.putLong(Tag,longOfTag);  
		updateFile(PREF_FILE);
	}
	
	public boolean getBadgeStatus(String Tag){
		sp= getSharedPreferences(BADGE_FILE,0);  
		badge_status = sp.getBoolean(Tag, false);
		return badge_status;
	}
	public void setBadgeStatus(String Tag,boolean BadgeGot){
		sp= getSharedPreferences(BADGE_FILE,0);
		editor= sp.edit();  
		editor.putBoolean(Tag,BadgeGot);  
		updateFile(BADGE_FILE);
	}
	private void updateFile(String File){
		editor.commit();
		//caculate MD5
		//editor.putLong(this.HASH,chekcMD5(File));
		editor.commit();
	}
	
	private long chekcMD5(String Filename){
		return 123456;
	}

}
