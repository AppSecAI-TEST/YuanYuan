package com.example.bmobsmsdemo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import cn.bmob.newsmssdk.BmobSMS;
import cn.bmob.newsmssdk.BmobSmsState;
import cn.bmob.newsmssdk.exception.BmobException;
import cn.bmob.newsmssdk.listener.QuerySMSStateListener;
import cn.bmob.newsmssdk.listener.RequestSMSCodeListener;
import cn.bmob.newsmssdk.listener.SMSCodeListener;
import cn.bmob.newsmssdk.listener.VerifySMSCodeListener;

public class MainActivity extends Activity implements OnClickListener{
	EditText et_smscode;
	EditText et_smsid;
	Button sendCode;
	Button queryState;
	Button ifVerfy;
	Button sendCustommessage;
	Context context;
	String phoneNumber = "";
	int smsId = 123;
	private String sendTime;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context = getApplicationContext();
		BmobSMS.initialize(context, "", new MySMSCodeListener());
		initViews();
		
	}
	private void initViews() {
		et_smscode = (EditText) findViewById(R.id.et_smscode);
		et_smsid = (EditText) findViewById(R.id.et_smsid);
		sendCode = (Button) findViewById(R.id.sendCode);
		queryState = (Button) findViewById(R.id.queryState);
		ifVerfy = (Button) findViewById(R.id.ifVerfy);
		sendCustommessage = (Button) findViewById(R.id.sendCustommessage);
		sendCode.setOnClickListener(this);
		queryState.setOnClickListener(this);
		ifVerfy.setOnClickListener(this);
		sendCustommessage.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.sendCode:
			BmobSMS.requestSMSCode(context, phoneNumber, "仅测试", new RequestSMSCodeListener() {
				
				@Override
				public void done(Integer smsId, BmobException ex) {
					if(ex == null){
						toast("ok " + smsId);
						MainActivity.this.smsId = smsId;
						et_smsid.setText(smsId+"");
						Log.e("demo", ""+smsId);
					}else {
						toast(ex.toString());
					}
				}

			});
			break;
		case R.id.queryState:
			BmobSMS.querySmsState(context, smsId, new QuerySMSStateListener() {
				
				@Override
				public void done(BmobSmsState state, BmobException ex) {
					if(ex == null){
						toast("getSmsState" + state.getSmsState() +"getVerifyState "+state.getVerifyState());
						
					}else {
						toast(ex.toString());
					}
				}
			});
			break;
		case R.id.ifVerfy:
			BmobSMS.verifySmsCode(context, phoneNumber, et_smscode.getText().toString().trim(), new VerifySMSCodeListener() {
				
				@Override
				public void done(BmobException ex) {
					if(ex == null){
						toast("ok ");
					}else {
						toast(ex.toString());
					}
				}
			});
			break;
		case R.id.sendCustommessage:
			sendTime = "2016-11-10 18:00:13";
			BmobSMS.requestSMS(context, phoneNumber, "sms content", "", new RequestSMSCodeListener() {
				
				@Override
				public void done(Integer smsId, BmobException ex) {
					if(ex == null){
						toast("ok " + smsId);
					}else {
						toast(ex.toString());
					}
					
				}
			});
			break;

		default:
			break;
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	public void toast(String string){
		Toast.makeText(context, string, Toast.LENGTH_LONG).show();
	}
	
	class MySMSCodeListener implements SMSCodeListener{

		@Override
		public void onReceive(String content) {
			if(et_smscode != null){
				et_smscode.setText(content);
			}
		}
		
	}

}
