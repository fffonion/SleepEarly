package dotnet.aoye.util;


import android.app.Activity;
import android.content.SharedPreferences;
/**
 * A xml-parsing wrapper to parse shared_prefs data.
 * 
 * @author bingo,fffonion
 *
 */
public class XmlParser{
	private static final String BADGE_FILE_NAME="badge";
	private static final String CONFIG_FILE_NAME="preference";
	public final String SCREEN_WIDTH_CONFIG_TAG="SCREEN_WIDTH",
			SCREEN_HEIGHT_CONFIG_TAG="SCREEN_HEIGHT",APP_VERSION="version",SHOW_HINT_TAG="show_hint",
			VIBRATE_TAG="vibrate_on_tip",USE_24_HOUR_TAG="use_24_hour",SYNC_BADGE_TAG="sync_badge_to_network",
			WEIBO_TAG="weibo_user_name",RENREN_PREFERRED_TAG="is_renren_preferred",SLEEP_HOUR_TAG="sleep_hour",
			SLEEP_MINUTE_TAG="sleep_minute",SLEEP_FLAG_TAG="sleep_falg_tag",SHOW_TIPS_TAG="show_tips_tag";
	//private String name;
    private int value;
    private int valueStd;
    private Activity activity;
    
	public XmlParser(Activity activity){		
		this.activity = activity;
		//this.name = name;
	}
	/*
	public void XmlIn(String BadgeKey){
		SharedPreferences   user = activity.getSharedPreferences("badge",0);
		value = user.getInt(BadgeKey, 0);
		value ++;
	}
	*/
	
	/**
	 * Set badge status value to add 1.(Write to file)
	 * 
	 * @param BadgeKey
	 * the pinyin name of badge
	 */
	public void setBadgeStatusAdd(String BadgeKey){
		SharedPreferences  user = activity.getSharedPreferences(BADGE_FILE_NAME,0);
	    SharedPreferences.Editor editor = user.edit();
	    value = user.getInt(BadgeKey, 0);
	    editor.putInt(BadgeKey,value+1);
	    editor.commit();
	}
	
	/**
	 * Get badge status.
	 * 
	 * @param BadgeKey
	 * the pinyin name of badge
	 * @return
	 * badge achieved times.
	 */
	public int getBadgeStatus(String BadgeKey) {		
		SharedPreferences   user = activity.getSharedPreferences(BADGE_FILE_NAME,0);
		return user.getInt(BadgeKey, -1);
	}

	/**
	 * Get configuration string values.
	 * 
	 * @param ConfigKey
	 * the name of configuration item
	 * @return
	 * the value
	 */
	public String getConfigString(String ConfigKey) {		
		SharedPreferences user = activity.getSharedPreferences(CONFIG_FILE_NAME,0);
		return user.getString(ConfigKey, null);
	}
	/**
	 * Get configuration long values.
	 * 
	 * @param ConfigKey
	 * the name of configuration item
	 * @return
	 * the value
	 */
	public long getConfigLong(String ConfigKey) {		
		SharedPreferences user = activity.getSharedPreferences(CONFIG_FILE_NAME,0);
		return user.getLong(ConfigKey, 0);
	}
	/**
	 * Get configuration int values.
	 * 
	 * @param ConfigKey
	 * the name of configuration item
	 * @return
	 * the value
	 */
	public int getConfigInt(String ConfigKey) {
		SharedPreferences user = activity.getSharedPreferences(CONFIG_FILE_NAME,0);
		return user.getInt(ConfigKey, 0);
	}
	/**
	 * Get configuration int values.
	 * 
	 * @param ConfigKey
	 * the name of configuration item
	 * @param default_val
	 * the default value
	 * @return
	 * the value
	 */
	public int getConfigInt(String ConfigKey,int defalut_val) {
		SharedPreferences user = activity.getSharedPreferences(CONFIG_FILE_NAME,0);
		return user.getInt(ConfigKey, defalut_val);
	}
	
	/**
	 * Get configuration boolean values.
	 * 
	 * @param ConfigKey
	 * the name of configuration item
	 * @return
	 * the value
	 */
	public boolean getConfigBool(String ConfigKey){
		SharedPreferences user = activity.getSharedPreferences(CONFIG_FILE_NAME,0);
		return user.getBoolean(ConfigKey, false);		
	}
	/**
	 * Get configuration boolean values.
	 * 
	 * @param ConfigKey
	 * the name of configuration item
	 * @param default_val
	 * the default value
	 * @return
	 * the value
	 */
	public boolean getConfigBool(String ConfigKey,boolean default_val){
		SharedPreferences user = activity.getSharedPreferences(CONFIG_FILE_NAME,0);
		return user.getBoolean(ConfigKey, default_val);		
	}
	/**
	 * Set configuration values.
	 * 
	 * @param ConfigKey
	 * the name of configuration item
	 * @param config_val
	 * the value
	 */
	public void setConfig(String ConfigKey,String config_val) {		
		SharedPreferences  user = activity.getSharedPreferences(CONFIG_FILE_NAME,0);
	    SharedPreferences.Editor editor = user.edit();
	    editor.putString(ConfigKey,config_val);
	    editor.commit();
	}
	public void setConfig(String ConfigKey,long config_val) {		
		SharedPreferences  user = activity.getSharedPreferences(CONFIG_FILE_NAME,0);
	    SharedPreferences.Editor editor = user.edit();
	    editor.putLong(ConfigKey,config_val);
	    editor.commit();
	}
	public void setConfig(String ConfigKey,int config_val) {		
		SharedPreferences  user = activity.getSharedPreferences(CONFIG_FILE_NAME,0);
	    SharedPreferences.Editor editor = user.edit();
	    editor.putInt(ConfigKey,config_val);
	    editor.commit();
	}
	public void setConfig(String ConfigKey, boolean config_val) {
		SharedPreferences  user = activity.getSharedPreferences(CONFIG_FILE_NAME,0);
	    SharedPreferences.Editor editor = user.edit();
	    editor.putBoolean(ConfigKey,config_val);
	    editor.commit();
	}

	/*
	public boolean ImageViewcheck(){
		SharedPreferences   user = activity.getSharedPreferences("badge",0);
		valueStd = user.getInt(BadgeKey + "Std", 0);
		value = user.getInt(BadgeKey, 0);
		if (value >= valueStd) return true;
		return false;
	}
	*/


	
}
