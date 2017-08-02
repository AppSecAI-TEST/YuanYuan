package xyz.zimuju.sample.temp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;

import xyz.zimuju.sample.R;

public class MTextViewActivity2 extends Activity {
	MyTextView2 view;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.text_main1);
        
        view = (MyTextView2) findViewById(R.id.textview);
        view.setText(getAssetsString(this,"1.txt"));
        view.setMovementMethod(ScrollingMovementMethod.getInstance());
    }
	public String getAssetsString(Context context,String fileName){
		StringBuffer sb = new StringBuffer();
		//根据语言选择加载
		try {
			AssetManager am = context.getAssets();
			InputStream in = am.open(fileName);
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String line;
			while((line = reader.readLine())!=null){
				line += ("\n");
				sb.append(line);
			}
			reader.close();
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
}