package func.handler_and_thread;


import java.util.Timer;
import java.util.TimerTask;

import com.example.andoridfunc.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.TextView;

/**
 * Handler主要两个作用 a:线程间传递消息  b：让Runnable对象在Handler对应的线程里面运行
 * @author XX1-PC
 *
 */
public class TestHandler extends Activity {
	TextView tv_main_handler, tv_thread_handler,textView6;
	int num = 0;

	//a:线程传消息给Handler主线程处理
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			//tv_main_handler.setText(String.valueOf(msg.what));
			textView6.setText(String.valueOf(msg.what));
			
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.func_handler_and_thread);
		findView();
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				num++;
				Message msg = new Message();
				msg.what = num;
				handler.sendMessage(msg);
				//b:让Runnable对象在Handler对应的线程里面运行
				handler.post(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						tv_main_handler.setText(String.valueOf(num));
						
					}
				});
				

			}
		}, 1000, 1000);
		
		
		TestThread testThread = new TestThread();
		testThread.start();

	}

	private void findView() {
		// TODO Auto-generated method stub
		tv_main_handler = (TextView) findViewById(R.id.textView3);
		tv_thread_handler = (TextView) findViewById(R.id.textView4);
		textView6= (TextView) findViewById(R.id.textView6);
	}

	public class TestThread extends Thread {
		int thread_num = 100;

		Handler threadHandler = new Handler() {
			public void handleMessage(Message msg) {
				
				tv_thread_handler.setText(String.valueOf(msg.what));
			};
		};

		@Override
		public void run() {
			// TODO Auto-generated method stub
			Looper.prepare();
			Timer timer = new Timer();
			timer.schedule(new TimerTask() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					thread_num--;
					Message msg = new Message();
					msg.what = thread_num;
					threadHandler.sendMessage(msg);

				}
			}, 1000, 500);
			Looper.loop();

		}
	}

}
