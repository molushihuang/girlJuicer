package com.xqd.meizhi.utils;

import android.os.CountDownTimer;
import android.widget.TextView;

public class CustomCountDown extends CountDownTimer {

	private TextView btnCode;
	private int sendTimes;
	public CustomCountDown(long millisInFuture, long countDownInterval, TextView btnCode) {
		super(millisInFuture, countDownInterval);
		this.btnCode = btnCode;
	}

	public CustomCountDown(long millisInFuture, long countDownInterval, TextView btnCode, int sendTimes) {
		super(millisInFuture, countDownInterval);
		this.btnCode = btnCode;
		this.sendTimes = sendTimes;
	}
	
	@Override
	public void onTick(long millisUntilFinished) {
		btnCode.setText(String.format("重发验证码(%ss)", millisUntilFinished/1000));
	}

	@Override
	public void onFinish() {
		//  Auto-generated method stub
		btnCode.setText("重发验证码");
		btnCode.setEnabled(true);
		sendTimes=0;
	}
	
	public void cancelCountDownTimer(){
		cancel();
		btnCode.setText("重发验证码");
		btnCode.setEnabled(true);
	}
	
	

}
