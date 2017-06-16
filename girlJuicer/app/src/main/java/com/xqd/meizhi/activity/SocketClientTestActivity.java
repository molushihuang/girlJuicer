package com.xqd.meizhi.activity;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import butterknife.Bind;
import butterknife.OnClick;
import com.anthole.quickdev.commonUtils.StringUtils;
import com.anthole.quickdev.commonUtils.T;
import com.xqd.meizhi.R;
import com.xqd.meizhi.activity.base.BaseActivity;
import com.xqd.meizhi.socket.SocketClient;
import com.xqd.meizhi.utils.Invoke;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketClientTestActivity extends BaseActivity {
    private SocketClient client;

    @Bind(R.id.et_input)
    EditText etInput;
    @Bind(R.id.main_toolbar)
    Toolbar toolbar;

    String msgData = "test";
    String ip = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_socket_test;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

        toolbar.setTitle("SocketClient");

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true); // 决定左上角图标的右侧是否有向左的小箭头, true
        // 有小箭头，并且图标可以点击
        actionBar.setDisplayShowHomeEnabled(false);


        //服务端的IP地址和端口号


    }

    @OnClick(R.id.bt_get)
    public void getMessage() {
        msgData = Invoke.readTextFromAsset("html.txt", this);
        Log.e("html.txt>>>>>>>>>>>>>>>", msgData);
    }

    @OnClick(R.id.bt_connection)
    public void connection() {
        if (StringUtils.isEmpty(etInput.getText().toString().trim())) {
            T.showShort(this, "请输入ip地址");
            return;
        }
        if (!ip.equals(etInput.getText().toString().trim())) {
            ip = etInput.getText().toString().trim();
            client = new SocketClient();
            client.clintValue(this, ip, 6667);
            //开启客户端接收消息线程
            client.openClientThread();
        }
    }


    @OnClick(R.id.bt_send)
    public void sendMessage() {
        if (client != null) {
            client.sendMsg(msgData);

        } else {
            T.showShort(this, "请与对方手机建立连接");
        }
    }


}
