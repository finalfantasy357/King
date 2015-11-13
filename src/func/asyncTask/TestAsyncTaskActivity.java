package func.asyncTask;


import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.ViewById;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.andoridfunc.R;

@EActivity(R.layout.func_async_task)
public class TestAsyncTaskActivity extends Activity {
	@ViewById
	TextView tv_start,tv_percent;
	
	@ViewById
	ImageView iv_pic;
	
	@Click(R.id.tv_start)
	public void clickStart(){
		System.out.println("点击开始");
		new DownloadImageTask().execute("http://www.baidu.com/img/baidu_jgylogo3.gif");
	}
	
	
	class DownloadImageTask extends AsyncTask<String, Integer, Bitmap>{
		Bitmap bitmap;
		
		//后台线程
		@Override
		protected Bitmap doInBackground(String... params) {
			// TODO Auto-generated method stub
			 try {  
		            URL url = new URL(params[0]);  
		            HttpURLConnection conn = (HttpURLConnection) url  
		                    .openConnection();  
		            InputStream inputStream = conn.getInputStream();  
		            bitmap = BitmapFactory.decodeStream(inputStream);  
		            // 进度条的更新，我这边只是用一个循环来示范，在实际应用中要使用已下载文件的大小和文件总大小的比例来更新  
		            for (int i = 1; i <= 10; i++) {  
		                publishProgress(i * 10);  
		                Thread.sleep(200);  
		            }  
		            inputStream.close();  
		        } catch (Exception e) {  
		            e.printStackTrace();  
		        }  
		        return bitmap;  
		}
		
		//UI线程
		@Override
		protected void onPostExecute(Bitmap result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			ImageView imageView = (ImageView) findViewById(R.id.iv_pic);  
	        imageView.setImageBitmap(result);
		}
		
		//UI线程，更新进度
		@Override
		protected void onProgressUpdate(Integer... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
			tv_percent.setText(values[0]+"%");
		}
		
	}
	
	

}
