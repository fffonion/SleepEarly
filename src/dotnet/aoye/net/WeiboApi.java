package dotnet.aoye.net;

import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.renren.api.connect.android.AsyncRenren;
import com.renren.api.connect.android.status.StatusSetRequestParam;
import com.renren.api.connect.android.view.RenrenAuthListener;
import com.weibo.net.AccessToken;
import com.weibo.net.AsyncWeiboRunner;
import com.weibo.net.AsyncWeiboRunner.RequestListener;
import com.weibo.net.DialogError;
import com.weibo.net.Oauth2AccessTokenHeader;
import com.weibo.net.ShareActivity;
import com.weibo.net.Token;
import com.weibo.net.Utility;
import com.weibo.net.Weibo;
import com.weibo.net.WeiboDialogListener;
import com.weibo.net.WeiboException;
import com.weibo.net.WeiboParameters;

import dotnet.aoye.R;
import dotnet.aoye.util.XmlParser;
/**
 * A weibo sdk wrapper.
 * 
 * @author fffonion
 *
 */
public class WeiboApi{
	private static final String URL_ACTIVITY_CALLBACK = "http://www.sina.com",
	//FROM = "xweibo"
			ACCESS_TOKEN_TAG="SINA_WEIBO_ACCESS_TOKEN",EXPIRES_TIME_TAG="SINA_WEIBO_EXPIRES_TIME_MILLIS",
			EXPIRES_IN_TAG="SINA_WEIBO_EXPIRES_IN_MILLIS";
	private static final String CONSUMER_KEY = "3154211429";
	//private String CONSUMER_SECRET;
	 private static final String CONSUMER_SECRET = "28fb8cda9339c734b0e099baf5142f4d";
	private static String ACCESS_TOKEN;
	private static long EXPIRES_IN;
	private Handler authHdl;
	private String username = "---";
	private String password = "---";
	Weibo weibo;
	Activity activity;
	private XmlParser parser;
	private boolean authDone;
	public WeiboApi(Activity pActivity){
		this.weibo=  Weibo.getInstance();
		//this.CONSUMER_SECRET=pActivity.getString(R.string.WEIBO_APP_SECRET);
		this.weibo.setupConsumerConfig(CONSUMER_KEY, CONSUMER_SECRET);
		this.weibo.setRedirectUrl("http://www.sina.com");
		this.activity=pActivity;
		parser=new XmlParser(activity);
	}
	/**
	 * Use weibo sdk webview to authorize.
	 * 
	 */
	public void auth(WeiboDialogListener authListener){
		Utility.setAuthorization(new Oauth2AccessTokenHeader());
		authHdl=new Handler();
		authHdl.post(toastThread);
		weibo.authorize(activity,
					authListener);
			// try {
			// // Oauth2.0 璁よ瘉鏂瑰紡
			// Weibo.setSERVER("https://api.weibo.com/2/");
			// Oauth2AccessToken at =
			// weibo.getOauth2AccessToken(AuthorizeActivity.this,
			// Weibo.getAppKey(), Weibo.getAppSecret(), username,
			// password);
			// // xauth璁よ瘉鏂瑰紡
			// /*
			// * Weibo.setSERVER("http://api.t.sina.com.cn/");
			// * AccessToken at =
			// * weibo.getXauthAccessToken(TextActivity.this,
			// * Weibo.APP_KEY, Weibo.APP_SECRET, "", "");
			// * mToken.setText(at.getToken());
			// */
			// RequestToken requestToken =
			// weibo.getRequestToken(AuthorizeActivity.this,
			// Weibo.getAppKey(), Weibo.getAppSecret(),
			// AuthorizeActivity.URL_ACTIVITY_CALLBACK);
			// mToken.setText(requestToken.getToken());
			// Uri uri =
			// Uri.parse(AuthorizeActivity.URL_ACTIVITY_CALLBACK);
			// startActivity(new Intent(Intent.ACTION_VIEW, uri));
			//
			// } catch (WeiboException e) {
			// e.printStackTrace();
			// } // mToken.setText(at.getToken());
			//
			authDone=true;
			//parser.setConfig(parser.WEIBO_TAG, this.weibo.)
	}

	private Runnable toastThread=new Runnable(){
		@Override
		public void run() {
			// TODO Auto-generated method stub
			if(!authDone){
				Toast.makeText(activity,"因特奈特正在傲娇，请稍等~",Toast.LENGTH_SHORT).show();
				authHdl.postDelayed(toastThread, 3000);
			}
		}
		
	};
	
	/**
	 * Post a plain-text weibo or one with picture
	 * 
	 * @param pString
	 * the weibo content
	 * @param picPath
	 * picture upload together with weibo
	 * leave null if plain-text is wanted
	 */
	public void postWeibo(String pString,String picPath){
		//this.weibo.getInstance();
		try {
			upload2Weibo(this.weibo,Weibo.getAppKey(),pString,"","");
			//thisWeibo.weibo.share2weibo(this.activity, this.weibo.getAccessToken().getToken(), this.weibo.getAccessToken()
	         //       .getSecret(), pString, picPath);
        } catch (WeiboException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {

        }
		
	}
	/*
	 * upload method;plain-text
	 */
	private String upload2Weibo(Weibo weibo, String source,String status, String lon,
            String lat) throws WeiboException {
		
		WeiboParameters bundle = new WeiboParameters();
        bundle.add("source", source);
        //bundle.add("pic", file);
        bundle.add("status", status);
        if (!TextUtils.isEmpty(lon)) {
            bundle.add("lon", lon);
        }
        if (!TextUtils.isEmpty(lat)) {
            bundle.add("lat", lat);
        }
        String rlt = "";
        String url = Weibo.SERVER + "statuses/update.json";
        rlt = weibo.request(this.activity, url, bundle, Utility.HTTPMETHOD_POST, this.weibo.getAccessToken());
        return rlt;
	}
  
	/**
	 * Logout weibo.
	 * 
	 * @param pcontext
	 * the activity who calls this method
	 */
	public void logout(Context pcontext){
		
	}
	/**
	 * Check if session key expired.
	 * 
	 * @return
	 */
	public boolean hasLogin()
	{
		return this.isKeyValid();
		//return isKeyValid();
	}
	/*******************************************************************************
	 * token.getExpireIn总时间 accesstoken.setExpireIn时间差
	*******************************************************************************/
	/*
	 * check session key
	 */
	private boolean isKeyValid()
	{
		Token token=new Token();
		token=weibo.getAccessToken();
		/*if(token.getToken()!= null && 
				System.currentTimeMillis() < this.getExpiresTime()) {
			return true;
		}*/
		initAccessToken();
		token=new Token();
		/*Toast.makeText(activity, String.valueOf(System.currentTimeMillis()-
				this.getExpiresTroubleShooter()), 0).show();*/
		if(token.getToken()!= null && 
				System.currentTimeMillis() < this.getExpiresTroubleShooter()) {
			return true;
		}
		return false;
	}
	/**
	 * Return access token string.
	 * 
	 * @return
	 */
	public String getAccessToken(){
		Token token=new Token();
		token=this.weibo.getAccessToken();
		return token.getToken();
	}
	
	//make expires time big
	private long getExpiresTroubleShooter(){
		return this.getExpiresTime()*1000+System.currentTimeMillis();
	}
	
	/**
	 * Get expires time.
	 * 
	 * @return
	 */
	public long getExpiresTime(){
		Token token=new Token();
		token=this.weibo.getAccessToken();
		return token.getExpiresIn();
	}
	/**
	 * Init access token from stored data
	 * 
	 */
	public void initAccessToken(){
		String token = parser.getConfigString(ACCESS_TOKEN_TAG);
		//int expires_in=0;
		int expires_in =(int)(parser.getConfigLong(EXPIRES_IN_TAG)-System.currentTimeMillis())/1000;
		//Log.d("api ein",String.valueOf(expires_in));
		String expires_time =String.valueOf(parser.getConfigLong(EXPIRES_TIME_TAG));
		Utility.setAuthorization(new Oauth2AccessTokenHeader());
		//or in utility line 140 get rid of mauth!=null 
		AccessToken accessToken = new AccessToken(token, CONSUMER_SECRET);
		accessToken.setExpiresIn(expires_in);
		Weibo.getInstance().setAccessToken(accessToken);
	}
	
	/**
	 * Store access token data
	 * 
	 * @param AccessToken
	 * string
	 * @param ExpiresIn
	 */
	public void storeAccessToken(String AccessToken,long ExpiresIn){
		parser.setConfig(ACCESS_TOKEN_TAG, AccessToken);
		parser.setConfig(EXPIRES_IN_TAG, ExpiresIn);
		//parser.setConfig(EXPIRES_TIME_TAG, (long)(this.getExpiresIn()*1000+System.currentTimeMillis()));
	}
	/*
	 * WeiboDialogListener wrapper
	 */
	/*
	public class WeiboDialogListener implements WeiboDialogListener {
		@Override
		public void onComplete(Bundle values) {
			String token = values.getString("access_token");
			String expires_in = values.getString("expires_in");
			AccessToken accessToken = new AccessToken(token, CONSUMER_SECRET);
			accessToken.setExpiresIn(expires_in);
			Weibo.getInstance().setAccessToken(accessToken);
			//Toast.makeText(activity, "登录成功~>.<", Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onError(DialogError e) {
			//Toast.makeText(getApplicationContext(),"Auth error : " + e.getMessage(), Toast.LENGTH_LONG).show();
		}

		@Override
		public void onCancel() {
			//Toast.makeText(getApplicationContext(), "Auth cancel",Toast.LENGTH_LONG).show();
		}

		@Override
		public void onWeiboException(WeiboException e) {
			//Toast.makeText(getApplicationContext(),"Auth exception : " + e.getMessage(), Toast.LENGTH_LONG).show();
		}

	}
	*/
}
