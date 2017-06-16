package com.xqd.meizhi.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import com.anthole.quickdev.commonUtils.StringUtils;
import com.anthole.quickdev.commonUtils.jsonUtils.JSONUtils;
import com.bumptech.glide.Glide;
import com.xqd.meizhi.R;
import com.xqd.meizhi.activity.base.BaseActivity;
import com.xqd.meizhi.bean.GirlBean;
import com.xqd.meizhi.bean.Parser;
import com.xqd.meizhi.bean.SocketTestBean;
import com.xqd.meizhi.socket.SocketServer;

import java.util.List;

public class SocketServerTestActivityActivity extends BaseActivity {
    /**
     * 启动服务端端口
     * 服务端IP为手机IP
     */
    private SocketServer server = new SocketServer(6667);

    @Bind(R.id.iv_pic)
    ImageView iv_pic;
    @Bind(R.id.tv_picAd)
    TextView tv_picAd;
    @Bind(R.id.tv_videoAd)
    TextView tv_videoAd;
    @Bind(R.id.main_toolbar)
    Toolbar toolbar;

    String msgData;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_socket_server_test_activity;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

        toolbar.setTitle("SocketServer");

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true); // 决定左上角图标的右侧是否有向左的小箭头, true
        // 有小箭头，并且图标可以点击
        actionBar.setDisplayShowHomeEnabled(false);

        /**socket服务端开始监听*/
        server.beginListen();
        /**socket收到消息线程*/
        SocketServer.ServerHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {

                msgData = msg.obj.toString();
                Log.e(">>>>>>>>>>>>>>>>>>>>>>",msgData);
//                String launchertest = JSONUtils.getString(msgData, "launchertest", "");
//                String content = JSONUtils.getString(launchertest, "content", "");
//                List<SocketTestBean> list = Parser.parseSocketList(content);
//
//                SocketTestBean socketTestBean = new SocketTestBean();
//
//                for (SocketTestBean testBean : list) {
//                    if (testBean.getPicPosition().equals("1")) {
//                        socketTestBean = testBean;
//                    }
//                }
//
//                Glide.with(SocketServerTestActivityActivity.this)
//                        .load(socketTestBean.getPicPath())
//                        .placeholder(com.anthole.quickdev.R.drawable.default_error)
//                        .error(com.anthole.quickdev.R.drawable.default_error)
//                        .fitCenter()
//                        .into(iv_pic);
//
//                tv_picAd.setText(socketTestBean.getPicPath());
//                tv_videoAd.setText(socketTestBean.getDirectUrl());
//
//                final SocketTestBean finalSocketTestBean = socketTestBean;
//                tv_videoAd.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (!StringUtils.isEmpty(finalSocketTestBean.getDirectUrl())) {
//                            try {
//                                String extension = MimeTypeMap.getFileExtensionFromUrl(finalSocketTestBean.getDirectUrl());
////                                String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
//                                String mimeType = "video/*";
//                                Intent mediaIntent = new Intent(Intent.ACTION_VIEW);
//                                mediaIntent.setDataAndType(Uri.parse(finalSocketTestBean.getDirectUrl()), mimeType);
//                                startActivity(mediaIntent);
//                            } catch (Exception e) {
//
//                            }
//                        }
//                    }
//                });
            }
        };
    }
}
