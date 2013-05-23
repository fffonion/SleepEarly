package dotnet.aoye;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class TipActivity extends Activity {

	private ImageButton apple,chengzi,dazao,fanhui,huluobo,kuihuazi,lianzi,milk,ningmeng,ou,putao,xiangjiao,xiaomi;
    private TextView pingming,text1,text2,text3,text4;
    private ImageView pic;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tip_activity);
        setTitle("熬夜宝典");
        apple=(ImageButton)findViewById(R.id.apple);
        chengzi=(ImageButton)findViewById(R.id.chengzi);
        dazao=(ImageButton)findViewById(R.id.dazao);
        //fanhui=(ImageButton)findViewById(R.id.fanhui);
        huluobo=(ImageButton)findViewById(R.id.huluobo);
        kuihuazi=(ImageButton)findViewById(R.id.kuihuazi);
        milk=(ImageButton)findViewById(R.id.milk);
        ningmeng=(ImageButton)findViewById(R.id.ningmeng);
        ou=(ImageButton)findViewById(R.id.ou);
        putao=(ImageButton)findViewById(R.id.putao);
        xiangjiao=(ImageButton)findViewById(R.id.xiangjiao);
        xiaomi=(ImageButton)findViewById(R.id.xiaomi);
        lianzi=(ImageButton)findViewById(R.id.lianzi);
        pingming=(TextView)findViewById(R.id.pingming);
        text1=(TextView)findViewById(R.id.text1);
        text2=(TextView)findViewById(R.id.text2);
        text3=(TextView)findViewById(R.id.text3);
        text4=(TextView)findViewById(R.id.text4);
        pic=(ImageView)findViewById(R.id.pic);
        apple.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				pingming.setText("苹果");
				text1.setText("增进记忆");
				text2.setText("降低胆固醇");
				text3.setText("通便止泻");
				text4.setText("降低血压");
				pic.setImageResource(R.drawable.apple);
			}
        	
        });
        chengzi.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				pingming.setText("橙子");
				text1.setText("和胃降逆");
				text2.setText("宽胸开结");
				text3.setText("增强抵抗力");
				text4.setText("止咳化痰");
				pic.setImageResource(R.drawable.chengzi);
			}
        	
        });
        dazao.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				pingming.setText("大枣");
				text1.setText("抗肿瘤");
				text2.setText("降血压胆固醇");
				text3.setText("补中益气");
				text4.setText("养血安神");
				pic.setImageResource(R.drawable.dazao);
			}
        	
        });
       /* fanhui.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(TipActivity.this,Tip2Activity.class);
				startActivity(intent);
			}
        	
        });*/
        huluobo.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				pingming.setText("胡萝卜");
				text1.setText("益肝明目");
				text2.setText("利膈宽肠");
				text3.setText("健脾除疳");
				text4.setText("降糖降脂");
				pic.setImageResource(R.drawable.huoluobo);
			}
        	
        });
        kuihuazi.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				pingming.setText("葵花籽");
				text1.setText("保护心脏");
				text2.setText("预防高血压");
				text3.setText("调节脑细胞代谢");
				text4.setText("防癌抗癌");
				pic.setImageResource(R.drawable.kuihuazi);
			}
        	
        });
        lianzi.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				pingming.setText("莲子");
				text1.setText("补脾止泻");
				text2.setText("益肾涩清");
				text3.setText("养心安神");
				text4.setText("强心祛心火");
				pic.setImageResource(R.drawable.lianzi);
			}
        	
        });
        milk.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				pingming.setText("牛奶");
				text1.setText("补充维生素");
				text2.setText("安眠");
				text3.setText("保证肠道健康");
				text4.setText("全营养食物");
				pic.setImageResource(R.drawable.milk);
			}
        	
        });
        ningmeng.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				pingming.setText("柠檬");
				text1.setText("鲜果美容");
				text2.setText("甘地润肺");
				text3.setText("止渴生津");
				text4.setText("调剂血管");
				pic.setImageResource(R.drawable.ningmeng);
			}
        	
        });
        ou.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				pingming.setText("藕");
				text1.setText("清热凉血");
				text2.setText("益血生肌");
				text3.setText("清热生津");
				text4.setText("止血散瘀");
				pic.setImageResource(R.drawable.ou);
			}
        	
        });
        putao.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				pingming.setText("葡萄");
				text1.setText("阻止血栓");
				text2.setText("滋肝肾");
				text3.setText("补益气血");
				text4.setText("防癌抗癌");
				pic.setImageResource(R.drawable.putao);
			}
        	
        });
        xiangjiao.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				pingming.setText("香蕉");
				text1.setText("降压通便");
				text2.setText("预防神经疲劳");
				text3.setText("润肺止咳");
				text4.setText("减肥食品 ");
				pic.setImageResource(R.drawable.xiangjiao);
			}
        	
        });
        xiaomi.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				pingming.setText("小米");
				text1.setText("富含B1，B2");
				text2.setText("清凉解暑");
				text3.setText("健胃除湿");
				text4.setText("合睡安眠");
				pic.setImageResource(R.drawable.xiaomi);
			}
        	
        });
    }

   
}
