package dotnet.aoye;

import dotnet.aoye.R;
import android.app.Application;
import android.content.res.Configuration;
/**
 * A application wrapper to store global vals for quick fetch.
 * 
 * @author fffonion
 *
 */
public class ThisApp extends Application {
	private static boolean BadgeFlag = false,SyncFlag=true,SyncBadge=true,RenrenLogin=false,NetworkStatus=false,
			RenrenPreferred=false,WeiboLogin=false,Use24Hour=false,ShowHintTips=false,Vibrate=true,
			ScreenOnStatus=true,dontWantSleep=true,ShowLittleTips=true;
	public static boolean ShouldSyncFlag=false;
	public final int BADGE_COUNT=10,AWAKE=0,SLEEPING=1,WILLSLEEP=2,DONT_SLEEP=-1;
	private static int SleepFlag=0,ScreenWidth=0,ScreenHeight=0; 
	private static int[] BadgeImgMaskId={R.id.ppmm_mask,R.id.gyfm_mask,R.id.tgqf_mask,R.id.brzm_mask,
			R.id.znkr_mask,R.id.cjlz_mask,R.id.wjqw_mask,R.id.wjbf_mask,R.id.nzhm_mask,R.id.xssl_mask};
	private static int[] BadgeImgPicId={R.id.ppmm_pic,R.id.gyfm_pic,R.id.tgqf_pic,R.id.brzm_pic,
		R.id.znkr_pic,R.id.cjlz_pic,R.id.wjqw_pic,R.id.wjbf_pic,R.id.nzhm_pic,R.id.xssl_pic};
	private static String[] BadgeName={"popomama","guiyifomen","tonggaiqianfei","bairizuomeng",
			"zinuekuangren","chaojilanzhu","wenjiqiwu","wanjiebufu","niuzaihenmang","xinshoushanglu"};
	private static String[] BadgeNameCN={"婆婆妈妈","皈依佛门","痛改前非","白日做梦","自虐狂人","超级懒猪",
			"闻鸡起舞","万劫不复","牛仔很忙","新手上路"};
	private static int[] BadgeDrawableBig={R.drawable.badge_pic_ppmm_l,R.drawable.badge_pic_gyfm_l,
		R.drawable.badge_pic_tgqf_l,R.drawable.badge_pic_brzm_l,R.drawable.badge_pic_znkr_l,
		R.drawable.badge_pic_cjlz_l,R.drawable.badge_pic_wjqw_l,R.drawable.badge_pic_wjbf_l,
		R.drawable.badge_pic_nzhm_l,R.drawable.badge_pic_xssl_l};
	private static int BadgeTitleBig[]={R.drawable.badge_title_ppmm,R.drawable.badge_title_gyfm,
		R.drawable.badge_title_tgqf,R.drawable.badge_title_brzm,R.drawable.badge_title_znkr,
		R.drawable.badge_title_cjlz,R.drawable.badge_title_wjqw,R.drawable.badge_title_wjbf,
		R.drawable.badge_title_nzhm,R.drawable.badge_title_xssl};
	//Note:automatically got badge should be set to -1 !!!
	private static int[] BadgeStd={5,10,1,3,3,1,1,10,3,-1},BADGE_GOTTEN_COUNT;
	private static final String[] AdString={"宅男宅女万年懒虫熬夜帝们，来看这个无节操的应用吧啊哈哈哈哈哈哈~",
		"成就什么的最喜欢了XD~","什么？！Are you kiding?!","【其实今天天气不错","我认为这是极好的~"};
	private static boolean[] BadgeStatus;
	public String DOWNLOAD_LINK="http://t.cn/zl5HQwA";
	public static String HOMEPAGE_LINK="http://sleepearly.site50.net/";
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}
	@Override
	public void onCreate() {
		super.onCreate();
		BadgeStatus=new boolean[BADGE_COUNT];
	}
	@Override
	public void onLowMemory() {
		super.onLowMemory();
	}
	@Override
	public void onTerminate() {
		super.onTerminate();
	}
	/**
	 * Set Sleep status.
	 * 
	 * @param flag
	 */
	public void setSleepFlag(int flag){
		SleepFlag=flag;
	}
	
	/**
	 * Get Sleep status.
	 * 
	 * @return
	 * status
	 */
	
	public int getSleepFlag(){
		return SleepFlag;
	}
	/**
	 * Set whether Renren preferred.
	 * 
	 * @param flag
	 */
	public void setRenrenPreferred(boolean flag){
		RenrenPreferred=flag;
	}
	
	/**
	 * Get whether Renren preferred.
	 * 
	 * @return
	 * status
	 */
	
	public boolean getRenrenPreferred(){
		return RenrenPreferred;
	}
	/**
	 * Set network status.
	 * 
	 * @param flag
	 */
	public void setNetworkStatus(boolean flag){
		NetworkStatus=flag;
	}
	
	/**
	 * Get network status.
	 * 
	 * @return
	 * status
	 */
	public boolean getNetworkStatus(){
		return NetworkStatus;
	}
	
	/**
	 * Set renren login status.
	 * 
	 * @param flag
	 */
	public void setRenrenLoginFlag(boolean flag){
		RenrenLogin=flag;
	}
	
	/**
	 * Get renren login status.
	 * 
	 * @return
	 * status
	 */
	public boolean getRenrenLoginFlag(){
		return RenrenLogin;
	}
	
	/**
	 * Set weibo login status.
	 * 
	 * @param flag
	 */
	public void setWeiboLoginFlag(boolean flag){
		WeiboLogin=flag;
	}
	
	/**
	 * Get weibo login status.
	 * 
	 * @return
	 * status
	 */
	public boolean getWeiboLoginFlag(){
		return WeiboLogin;
	}
	
	/**
	 * Set screen height.
	 * 
	 * @param height
	 */
	public void setScreenHeight(int height){
		ScreenHeight=height;
	}
	
	/**
	 * Get screen height.
	 * 
	 * @return
	 * height
	 */
	public int getScreenHeight(){
		return ScreenHeight;
	}
	
	/**
	 * Set screen height.
	 * 
	 * @param  width
	 */
	public void setScreenWidth(int width){
		ScreenWidth= width;
	}
	
	/**
	 * Get screen  width.
	 * 
	 * @return
	 *  width
	 */
	public int getScreenWidth(){
		return ScreenWidth;
	}
	
	/**
	 * Get left X boarder.
	 * 
	 * @return
	 */
	public float getLeftX(){
		return (ScreenWidth*180/480);
	}
	
	/**
	 * Get right X boarder.
	 * 
	 * @return
	 */
	public float getRightX(){
		return (ScreenWidth*300/480);
	}
	
	/**
	 * Get up Y boarder.
	 * 
	 * @return
	 */
	public float getTopY(){
		return (ScreenHeight*220/800);
	}
	
	/**
	 * Get bottom Y boarder.
	 * 
	 * @return
	 */
	public float getBottomY(){
		return (ScreenHeight*580/800);
	}
	
	/**
	 * Set new badge gotten status.
	 * 
	 * @param flag
	 * if new badge gotten
	 */
	public void setNewBadgeFlag(boolean flag){
		BadgeFlag=flag;
	}
	
	/**
	 * Get new badge gotten status.
	 * 
	 * @return
	 * status
	 */
	public boolean getNewBadgeFlag(){
		return BadgeFlag;
	}
	
	/**
	 * Set badge gotten status.
	 * 
	 * @param status
	 */
	public void setBadgeStatus(int i,boolean status){
		BadgeStatus[i]=status;
	}

	public void setBadgeStatus(String name,boolean status){
		setBadgeStatus(this.getBadgeId(name),status);
	}
	
	/**
	 * Get badge gotten status.
	 * 
	 * @param status
	 */
	public boolean getBadgeStatus(int i){
		return BadgeStatus[i];
	}
	/**
	 * Get badge gotten status.
	 * 
	 * @param status
	 */
	public boolean getBadgeStatus(String name){
		return BadgeStatus[this.getBadgeId(name)];
	}
	
	/**
	 * Get count of all defined badges.
	 * 
	 * @return
	 */
	public int getBadgeCount(){
		return BADGE_COUNT;
	}
	
	
	/**
	 * Get count of all gotten badges.
	 * 
	 * @return
	 */
	public int getBadgeGotCount(){
		int count = 0;
		for(int i=0;i<BADGE_COUNT;i++)
			if(BadgeStatus[i])	count++;
		return count;
	}
	
	/**
	 * Get standard of badge.
	 * 
	 * @return
	 */
	public int getBadgeStd(String name){
		return BadgeStd[getBadgeId(name)];
	}
	
	/**
	 * Get id in R.java of mask of a badge
	 * 
	 * @param name
	 * badge name
	 * @return
	 */
	public int getBadgeImgMaskId(String name){
		return BadgeImgMaskId[this.getBadgeId(name)];
	}
	/**
	 * Get id in R.java of mask of a badge
	 * 
	 * @param i
	 * order number
	 * @return
	 */
	public int getBadgeImgPicId(int i){
		return BadgeImgPicId[i];
	}
	/**
	 * Get id in R.java of a badge
	 * 
	 * @param name
	 * badge name
	 * @return
	 */
	public int getBadgeImgPicId(String name){
		return BadgeImgPicId[this.getBadgeId(name)];
	}
	/**
	 * Get id in R.java of a badge
	 * 
	 * @param i
	 * order number
	 * @return
	 */
	public int getBadgeImgMaskId(int i){
		return BadgeImgMaskId[i];
	}
	
	/**
	 * Get badge drawable.
	 * 
	 * @return
	 * status
	 */
	public int getBadgeDrawableBig(int i){
		return BadgeDrawableBig[i];
	}
	/**
	 * Get badge drawable.
	 * 
	 * @return
	 * status
	 */
	public int getBadgeDrawableBig(String name){
		return BadgeDrawableBig[getBadgeId(name)];
	}
	
	/**
	 * Get badge title drawable.
	 * 
	 * @return
	 * status
	 */
	public int getBadgeTitleBig(int i){
		return BadgeTitleBig[i];
	}
	/**
	 * Get badge title drawable.
	 * 
	 * @return
	 * status
	 */
	public int getBadgeTitleBig(String name){
		return BadgeTitleBig[getBadgeId(name)];
	}
	
	/**
	 * Get badge pinyin name from order number
	 * 
	 * @param i
	 * order number
	 * @return
	 * badge pinyin name
	 */
	public String getBadgeName(int i){
		return BadgeName[i];
	}
	
	/**
	 * Get badge order number from pinyin name
	 * 
	 * @param BadgePinyinName
	 * @return
	 * order number
	 */
	public int getBadgeId(String BadgePinyinName){
		for(int i=0;i<BADGE_COUNT;i++){
			if (BadgeName[i].equals(BadgePinyinName)){
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Get badge GBK name frome pinyin name
	 * 
	 * @param BadgePinyinName
	 * @return
	 */
	public String getBadgeNameCN(String BadgePinyinName){
		for(int i=0;i<BADGE_COUNT;i++){
			if (BadgeName[i].equals(BadgePinyinName)){
				return BadgeNameCN[i];
			}
		}
		return null;
	}
	/**
	 * Get badge GBK name frome order number
	 * 
	 * @param BadgePinyinName
	 * @return
	 */
	public String getBadgeNameCN(int i){
				return BadgeNameCN[i];
	}
	/**
	 * Set whether sync to network
	 * 
	 * @return
	 */
	public void setSyncFlag(boolean flag){
		SyncFlag=flag;
	}
	
	/**
	 * Get whether sync to network
	 * 
	 * @return
	 */
	public boolean getSyncFlag(){
		return SyncFlag;
	}
	
	/**
	 * Set config of whether sync to network
	 * 
	 * @return
	 */
	public void setBadgeSyncFlag(boolean flag){
		SyncBadge=flag;
	}
	/**
	 * Get config of whether sync to network
	 * 
	 * @return
	 */
	public boolean getBadgeSyncFlag(){
		return SyncBadge;
	}
	
	/**
	 * Set config of whether use 24 hour
	 * 
	 * @return
	 */
	public void setUse24Hour(boolean flag){
		Use24Hour=flag;
	}
	/**
	 * Get config of whether use 24 hour
	 * 
	 * @return
	 */
	public boolean getUse24Hour(){
		return Use24Hour;
	}
	
	/**
	 * Set config of whether show hint tips
	 * 
	 * @return
	 */
	public void setShowHintTips(boolean flag){
		ShowHintTips=flag;
	}
	/**
	 * Get config of whether show tips
	 * 
	 * @return
	 */
	public boolean getShowHintTips(){
		return ShowHintTips;
	}
	

	/**
	 * Set config of whether show little tips
	 * 
	 * @return
	 */
	public void setShowLittleTips(boolean flag){
		ShowLittleTips=flag;
	}
	/**
	 * Get config of whether show little tips
	 * 
	 * @return
	 */
	public boolean getShowLittleTips(){
		return ShowLittleTips;
	}
	/**
	 * Set config of whether vibrate on showing tips
	 * 
	 * @return
	 */
	public void setVibrate(boolean flag){
		Vibrate=flag;
	}
	/**
	 * Get config of whether vibrate on showing tips
	 * 
	 * @return
	 */
	public boolean getVibrate(){
		return Vibrate;
	}
	/**
	 * Set screen on status
	 * 
	 * @return
	 */
	public void setScreenOnStatus(boolean flag) {
		// TODO Auto-generated method stub
		ScreenOnStatus=flag;
	}
	/**
	 * Get screen on status
	 * 
	 * @return
	 */
	public boolean getScreenOnStatus() {
		// TODO Auto-generated method stub
		return ScreenOnStatus;
	}
	/**
	 * Get config of whether sync badge to network
	 * 
	 * @return
	 */
	public boolean getSyncBadge(){
		return SyncBadge;
	}
	
	/**
	 * Get pre-defined ad string
	 * 
	 * @return
	 */
	public String getAdString(){
		return AdString[(int)(5.0D*Math.random())]+HOMEPAGE_LINK;
	}
	/**
	 * Get short home link
	 * 
	 * @return
	 */
	public String getHomeLink(){
		return HOMEPAGE_LINK;
	}
	/**
	 * Get short donwload link
	 * 
	 * @return
	 */
	public String getDownloadLink(){
		return DOWNLOAD_LINK;
	}
}