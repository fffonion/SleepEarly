package dotnet.aoye.Service;

import dotnet.aoye.R;
import dotnet.aoye.TipActivity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class ShowToastService extends Service{


	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void onCreate(){
		
	}
	
	public void onStart(Intent intent, int startid) {
		String[] Tips = new String[21];
	    Tips[0] = "熬夜的话不要吃泡面来填饱肚子，以免火气太大，最好尽量以水果、面包、清粥小菜来充饥。";
	    Tips[1] = "开始熬夜前，来一颗维他命ｂ群营养丸，维他命ｂ能够解除疲劳，增强人体免疫力。";
	    Tips[2] = "提神饮料，最好以绿茶为主，可以提神，又可以消除体内多余的自由基，让您神清气爽；但是肠不好的人，最好改喝枸杞子泡热水的茶，可以解压，还可以明目";
	    Tips[3] = "熬夜前千万记得卸妆，或是先把脸洗干净，以免厚厚的粉层或油渍，在熬夜的煎熬下长满脸痘痘";
	    Tips[4] = "熬夜之后，第二天中午时千万记得打个小盹";
	    Tips[5] = "00：00～01：00 浅眠期 多梦而敏感，身体不适者易在此时痛醒。";
	    Tips[6] = "01：00～02：00 排毒期 此时肝脏为排除毒素而活动旺盛，应让身体进入睡眠状态，让肝脏得以完成代谢废物。";
	    Tips[7] = "02：00～03：00 休眠期 重症病人最易发病的时刻，常有患病者在此时死亡，熬夜最好勿超过这个时间。";
	    Tips[8] = "14：00～15：00 高峰期 是分析力和创造力得以发挥淋漓的极致时段！";
	    Tips[9] = "16：00～17：00 低潮期 体力耗弱的阶段，最好补充水果来解馋，避免因饥饿而贪食致肥胖。";
	    Tips[10] = "17：00～18：00 松散期 此时血糖略增，嗅觉与味觉最敏感，不妨准备晚膳来提振精神。";
	    Tips[11] = "19：00～20：00 暂憩期 最好能在饭后30分钟去散个步或沐浴，放松一下，纾 解一日的疲倦困顿。";
	    Tips[12] = "20：00～22：00 夜修期 此为晚上活动的巅峰时段，建议您善用此时进行商议， 进修等需要思虑周密的活动。";
	    Tips[13] = "23：00～24：00 夜眠期 经过整日忙碌，此时应该放松心情进入梦乡，千万别让身体过度负荷，那可得不偿失哦";
	    Tips[14] = "药补不如食补，食补不如觉补";
	    Tips[15] = "凌晨两点后才入睡容易打乱生理时钟，对身体脂质新陈代谢带来严重影响，容易增加心脏病风险。";
	    Tips[16] = "晚上九点前喝牛奶有助于睡眠；是真的哟~~";
	    Tips[17] = "睡觉前适量的体育运动，能够促进人的大脑分泌出抑制兴奋的物质，促进深度睡眠，迅速缓解疲劳";
	    Tips[18] = "房内维持适度的阴暗与安静，有助于达到深沉休息的目的";
	    Tips[19] = "晚饭不宜吃得太晚和太饱，但是早饭是可以和中饭一起吃的【喂";
	    Tips[20] = "24：00～25：00 OO期 对不起，作者在卖萌( >﹏<。)~";
	    
		// TODO Auto-generated method stub
	    int j = (int)(21.0D * Math.random());
		Toast.makeText(this, Tips[j], Toast.LENGTH_LONG).show();
		NotificationManager nm = (NotificationManager)getSystemService(ShowToastService.NOTIFICATION_SERVICE); 
		
		Notification n = new Notification(R.drawable.ic_launcher, "我不要熬夜了", System.currentTimeMillis());   
		n.flags = Notification.FLAG_AUTO_CANCEL;                
		Intent i = new Intent(ShowToastService.this, TipActivity.class);
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);           
		//PendingIntent
		PendingIntent contentIntent = PendingIntent.getActivity(ShowToastService.this,0,i,0);
		
		n.setLatestEventInfo(
				ShowToastService.this,
				"嗷嗷君的小贴士", 
		        Tips[j], 
		        contentIntent);
		        
		nm.notify(3, n);
       super.onDestroy(); 
	}
}
