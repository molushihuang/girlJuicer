package com.xqd.meizhi.socket;

import android.os.Handler;
import android.os.Message;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Administrator on 2017/6/9.
 */

public class SocketServer {
    private ServerSocket server;
    private Socket socket;
    private InputStream in;
    private String str = null;
    private boolean isClint = false;
    public static Handler ServerHandler;

    /**
     * @param port 端口号
     * @steps bind();绑定端口号
     * @effect 初始化服务端
     */
    public SocketServer(int port) {
        try {
            server = new ServerSocket(port);
            isClint = true;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * @steps listen();
     * @effect socket监听数据
     */
    public void beginListen() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    /**
                     * accept();
                     * 接受请求
                     * */
                    socket = server.accept();
                    try {
                        /**得到输入流*/
                        in = socket.getInputStream();
                        /**
                         * 实现数据循环接收
                         * */
                        while (true) {

                            //获取输入信息
                            BufferedReader bff = new BufferedReader(new InputStreamReader(in));
                            //读取信息
                            String result = "";
                            String buffer = "";
                            while ((buffer = bff.readLine()) != null) {
                                result = result + buffer;
                            }
//                            byte[] bt = new byte[50];
//                            in.read(bt);
//                            str = new String(bt, "UTF-8");                  //编码方式  解决收到数据乱码
                            if (result != null && result != "exit") {
                                returnMessage(result);
                            } else if (result == null || result == "exit") {
                                break;                                     //跳出循环结束socket数据接收
                            }

                            bff.close();
                            socket.close();

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
//                        socket.isClosed();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
//                    socket.isClosed();
                }
            }
        }).start();
    }

    /**
     * @steps write();
     * @effect socket服务端发送信息
     */
    public void sendMessage(final String chat) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    PrintWriter out = new PrintWriter(socket.getOutputStream());
                    out.print(chat);
                    out.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    /**
     * @steps read();
     * @effect socket服务端得到返回数据并发送到主界面
     */
    public void returnMessage(String chat) {
        Message msg = new Message();
        msg.obj = chat;
        ServerHandler.sendMessage(msg);
    }

}
