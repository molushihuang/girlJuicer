package com.xqd.meizhi.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import com.xqd.meizhi.R;

/**
 * Implementation of App Widget functionality.
 */
public class AppWidget extends AppWidgetProvider {


    /**
     * 当 widget 更新时被执行。
     同样，当用户首次添加 widget 时，onUpdate() 也会被调用，这样 widget 就能进行必要的设置工作(如果需要的话) 。但是，如果定义了 widget 的 configure属性(即android:config，后面会介绍)，
     那么当用户首次添加 widget 时，onUpdate()不会被调用；之后更新 widget 时，onUpdate才会被调用。
     * @param context
     * @param appWidgetManager
     * @param appWidgetIds
     */
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

//        CharSequence widgetText = context.getString(R.string.appwidget_text);
        CharSequence widgetText = "妹汁";
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget);
//        views.setTextViewText(R.id.appwidget_text, widgetText);
//        views.setv

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }


    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }

    /**
     * 当 widget 被初次添加 或者 当 widget 的大小被改变时，执行onAppWidgetOptionsChanged()。你可以在该函数中，根据 widget 的大小来显示/隐藏某些内容。可以通过 getAppWidgetOptions() 来返回 Bundle 对象以读取 widget 的大小信息，Bundle中包括以下信息：
     * OPTION_APPWIDGET_MIN_WIDTH -- 包含 widget 当前宽度的下限，以dp为单位。
     * OPTION_APPWIDGET_MIN_HEIGHT -- 包含 widget 当前高度的下限，以dp为单位。
     * OPTION_APPWIDGET_MAX_WIDTH -- 包含 widget 当前宽度的上限，以dp为单位。
     * OPTION_APPWIDGET_MAX_HEIGHT -- 包含 widget 当前高度的上限，以dp为单位。
     *
     * @param context
     * @param appWidgetManager
     * @param appWidgetId
     * @param newOptions
     */
    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }

    @Override
    public void onRestored(Context context, int[] oldWidgetIds, int[] newWidgetIds) {
        super.onRestored(context, oldWidgetIds, newWidgetIds);
    }
}

