package com.xqd.meizhi.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.*;
import android.os.Bundle;
import android.view.View;

/**
 * Created by UJU105 on 2016/6/28.
 */
public class CanvasDemoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(new custumView(this));
    }

    class custumView extends View {
        Paint paint;

        public custumView(Context context) {
            super(context);
            paint = new Paint(); //设置一个笔刷大小是3的蓝色的画笔
            paint.setColor(Color.BLUE);
            // 同样是设置颜色
//            paint.setColor(Color.rgb(255, 0, 0));
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStrokeCap(Paint.Cap.ROUND);
            paint.setStrokeWidth(3);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
//            canvas.drawColor(Color.YELLOW);

            //画圆，中心x坐标，中心y坐标，半径，画笔
//            canvas.drawCircle(300, 300, 190, paint);

            //绘制弧线区域
//            RectF rect = new RectF(300, 300, 600, 600);
//            canvas.drawArc(rect, //弧线所使用的矩形区域大小
//                    0,  //开始角度
//                    300, //扫过的角度
//                    true, //是否使用中心
//                    paint);

            //画一条线
//            canvas.drawLine(10, 10, 200, 200, paint);

            //矩形区域内切椭圆
//            RectF oval = new RectF(300,300,400,500);
//            canvas.drawOval(oval, paint);

            //按照既定点 绘制文本内容
//            canvas.drawPosText("东哥是大帅比啊哈哈哈", new float[]{
//                    100, 100, //第一个字母在坐标10,10
//                    200, 200, //第二个字母在坐标20,20
//                    300, 300, //....
//                    400, 400,
//                    500, 500,
//                    600, 600,
//                    700, 700,
//                    800, 800,
//                    900, 900,
//                    1000, 1000
//            }, paint);

            //画矩形
//            RectF rect = new RectF(250, 200, 400, 400);
//            canvas.drawRect(rect, paint);
//            // 绘制一空心个矩形
//            paint.setStyle(Paint.Style.STROKE);
//            paint.setStrokeWidth(1);

            //圆角矩形
//            canvas.drawRoundRect(rect,
//                    30, //x轴的半径
//                    30, //y轴的半径
//                    paint);

            //画路径
//            Path path = new Path(); //定义一条路径
//            path.moveTo(100, 100); //移动到 坐标10,10
//            path.lineTo(100, 200);
//            path.lineTo(200,80);
//            path.lineTo(100, 100);
//            canvas.drawPath(path, paint);
//            //画文字路径
//            canvas.drawTextOnPath("东哥是大帅比啊哈哈哈", path, 10, 10, paint);


//            canvas.drawText("画贝塞尔曲线:", 10, 310, paint);
//            paint.reset();
//            paint.setStyle(Paint.Style.STROKE);
//            paint.setTextSize(58);
//            Path path2=new Path();
//            path2.moveTo(200, 420);//设置Path的起点
//            path2.quadTo(150, 310, 470, 700); //设置贝塞尔曲线的控制点坐标和终点坐标
//            canvas.drawPath(path2, paint);//画出贝塞尔曲线

            //画图片，就是贴图
//            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_invite_sms);
//            canvas.drawBitmap(bitmap, 250,360, paint);


//****************************************画时钟（示例）*************************************************************************//

            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.STROKE); // 设置paint的风格为“空心”,当然也可以设置为"实心"(Paint.Style.FILL)
            canvas.translate(canvas.getWidth() / 2, canvas.getHeight() / 2); //将位置移动画纸的中心
            canvas.drawCircle(0, 0, 100, paint); //画圆圈

            //使用path绘制路径文字
            canvas.save();
            canvas.translate(-75, -75);
            Path path = new Path();
            path.addArc(new RectF(0, 0, 150, 150), -180, 180);
            Paint citePaint = new Paint(paint);
            citePaint.setTextSize(14);
            citePaint.setStrokeWidth(1);// 设置“空心”的外框的宽度
            canvas.drawTextOnPath("http://www.android777.com", path, 28, 0, citePaint);
            canvas.restore();

            Paint tmpPaint = new Paint(paint); //小刻度画笔对象
            tmpPaint.setStrokeWidth(1);

            float y = 100;
            int count = 60; //总刻度数

            for (int i = 0; i < count; i++) {
                if (i % 5 == 0) {
                    canvas.drawLine(0f, y, 0, y + 12f, paint);
                    canvas.drawText(String.valueOf(i / 5 + 1), -4f, y + 25f, tmpPaint);

                } else {
                    canvas.drawLine(0f, y, 0f, y + 5f, tmpPaint);
                }
                canvas.rotate(360 / count, 0f, 0f); //旋转画纸
            }

            //绘制指针
            tmpPaint.setColor(Color.GRAY);
            tmpPaint.setStrokeWidth(4);
            canvas.drawCircle(0, 0, 7, tmpPaint);
            tmpPaint.setStyle(Paint.Style.FILL);
            tmpPaint.setColor(Color.YELLOW);
            canvas.drawCircle(0, 0, 5, tmpPaint);
            canvas.drawLine(0, 10, 0, -65, paint);
        }
    }
}
