package com.example.code;

import java.util.Random;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
public class MainActivity extends Activity {
	public  ImageView imageViewA = null; 
	public  ImageView imageViewB = null;
	public  ImageView imageViewC = null;
	public  ImageView imageViewD = null;
	public 	 ImageButton startButton = null;
	public  ImageButton answerButton = null; 
	public  EditText answerText = null;
	int a,b,c,d;
	int time=0;
	int count=0;
	
	MediaPlayer player = null;
	boolean stop = false;
	
	Handler handler = new Handler(){
		public void handleMessage(Message msg){
			super.handleMessage(msg);
			if(count<1000){
				Random rand = new Random();
				int a1 = rand.nextInt(10)+1;
				int b1 = rand.nextInt(10)+1;
				int c1 = rand.nextInt(10)+1;
				int d1 = rand.nextInt(10)+1;
				setIcon(imageViewA, a1);
				setIcon(imageViewB, b1);
				setIcon(imageViewC, c1);
				setIcon(imageViewD, d1);
			}
			else {
				stop = true;
				Message message = new Message();
				message.what=1;
				hand.sendMessage(message);
			}
		}
	};
	
	Handler hand = new Handler(){
		public void handleMessage(Message msg){
			super.handleMessage(msg);
			Random rand = new Random();
			while(true){
				a = rand.nextInt(10)+1;
				b = rand.nextInt(10)+1;
				c = rand.nextInt(10)+1;
				d = rand.nextInt(10)+1;
				if(a!=b&&a!=c&&a!=d&&b!=c&&b!=d&&c!=d){
					if(isGet24(a, b, c, d)){
						setIcon(imageViewA, a);
						setIcon(imageViewB, b);
						setIcon(imageViewC, c);
						setIcon(imageViewD, d);
						answerButton.setEnabled(true);
						break;
					}
				}
			}
		}
	};
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		player = MediaPlayer.create(this, R.raw.ksstherain);          //初始化声音
		imageViewA = (ImageView)findViewById(R.id.imageViewA);
		imageViewB = (ImageView)findViewById(R.id.imageViewB);
		imageViewC = (ImageView)findViewById(R.id.imageViewC);
		imageViewD = (ImageView)findViewById(R.id.imageViewD);
		startButton=(ImageButton)findViewById(R.id.startbutton);
		answerButton = (ImageButton)findViewById(R.id.answerbutton);
		answerText = (EditText)findViewById(R.id.editText1);
		/***********************“开始”按钮添加监视器**************************/
		startButton.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				answerButton.setEnabled(false);
				answerText.setText(null);
				stop = false;
				count=0;
				final Thread t=new Thread(new MyThread());
				t.start();
			}
		});
		/**********************“答案”按钮添加监视器*************************/
		answerButton.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						answerText.setText(null);
						//得到答案
						String answer = GameUtil.getResultList(a,b,c,d).get(0);
						answerText.setText(answer);
						
					}
		});
		/*****************************************************************************/
		player.setLooping(true);
		player.start();
	}
	
	
	@SuppressWarnings("deprecation") 
	@Override
	public boolean onKeyDown(int keyCode,KeyEvent event){
		if(keyCode == KeyEvent.KEYCODE_BACK ){
			AlertDialog isExit = new AlertDialog.Builder(this).create(); //创建退出对话框
			isExit.setTitle("系统提示");
			isExit.setMessage("再玩一会吧!");
			isExit.setButton( AlertDialog.BUTTON_POSITIVE, "不了", listener);
			isExit.setButton(AlertDialog.BUTTON_NEGATIVE, "继续", listener);
			isExit.show();
		}
		if(keyCode==KeyEvent.KEYCODE_HOME){
			if(player.isPlaying())
				player.stop();
		}
		return false;
	}@Override
	public boolean onCreateOptionsMenu(Menu menu){
		super.onCreateOptionsMenu(menu);
		menu.add(0,0,0, "打开背景音乐");
		menu.add(0,1,1, "关闭背景音乐");
		menu.add(0,2,2,"退出");
		
		return true;
	}
	public boolean onOptionsItemSelected(MenuItem item){
		super.onOptionsItemSelected(item);
		switch (item.getItemId()) {
			case 0:{  
				if(player.isPlaying()){}
				else {
					player = MediaPlayer.create(this, R.raw.ksstherain);
					player.setLooping(true);
					player.start();
				}
				break;
			}
			case 1:player.stop();break;
			case 2:{
				AlertDialog isExit = new AlertDialog.Builder(this).create(); //创建退出对话框
				isExit.setTitle("系统提示");
				isExit.setMessage("再玩一会吧!");
				isExit.setButton( AlertDialog.BUTTON_POSITIVE, "不了", listener);
				isExit.setButton(AlertDialog.BUTTON_NEGATIVE, "继续", listener);
				isExit.show();
			}
		}
		return true;
	}
	DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
		
		@Override
		public void onClick(DialogInterface dialog, int which) {
			switch(which){
			case AlertDialog.BUTTON_POSITIVE: {
				player.stop();
				finish();
				break;
			}
			case AlertDialog.BUTTON_NEGATIVE:break;
			default:break;
			}
			
		}
	};
	static void setIcon(ImageView view,int i){
		switch (i) {
			case 1:view.setImageResource(R.drawable.one);break;
			case 2:view.setImageResource(R.drawable.two);break;
			case 3:view.setImageResource(R.drawable.three);break;
			case 4:view.setImageResource(R.drawable.four);break;
			case 5:view.setImageResource(R.drawable.five);break;
			case 6:view.setImageResource(R.drawable.six);break;
			case 7:view.setImageResource(R.drawable.seven);break;
			case 8:view.setImageResource(R.drawable.eight);break;
			case 9:view.setImageResource(R.drawable.nine);break;
			case 10:view.setImageResource(R.drawable.ten);break;
			default:view.setImageResource(R.drawable.first);break;			
		}
	}
	
	String showFuHao(int op){
		switch(op){
		case 1:return "+";
		case 2:return "-";
		case 3:return "*";
		case 4:return "/";
		}
		return "";
	}
	static void setIcon(ImageButton button,int i){
		switch (i) {
			case 1:button.setImageResource(R.drawable.one);break;
			case 2:button.setImageResource(R.drawable.two);break;
			case 3:button.setImageResource(R.drawable.three);break;
			case 4:button.setImageResource(R.drawable.four);break;
			case 5:button.setImageResource(R.drawable.five);break;
			case 6:button.setImageResource(R.drawable.six);break;
			case 7:button.setImageResource(R.drawable.seven);break;
			case 8:button.setImageResource(R.drawable.eight);break;
			case 9:button.setImageResource(R.drawable.nine);break;
			case 10:button.setImageResource(R.drawable.ten);break;
			default:button.setImageResource(R.drawable.first);break;			
		}
	}
	public boolean isGet24(int i,int j,int k,int t){                 //判断是否能得到24
		boolean flag = GameUtil.isGet24(i, j, k, t); 
		return flag;
	}
	
	/************************************************************************/
	class MyThread implements Runnable{
		
		@Override
		public void run() {
			while(true){
				Message message = new Message();
				try{
					Thread.sleep(5);
					if(stop==true){
						return ;
					}
					message.what = 1;
					handler.sendMessage(message);
					count+=5;
					if(count==1000){
						stop = true;
					}
					
				}catch(Exception e){}
			}
			
		}
		
	}
}
