package com.xqd.meizhi.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.webkit.*;
import android.widget.ProgressBar;
import butterknife.Bind;
import com.anthole.quickdev.commonUtils.StringUtils;
import com.anthole.quickdev.commonUtils.T;
import com.anthole.quickdev.http.PersistentCookieStore;
import com.anthole.quickdev.http.base.AsyncHttpClientUtil;
import com.anthole.quickdev.invoke.SystemBarTintInvoke;
import com.xqd.meizhi.Constants;
import com.xqd.meizhi.R;
import com.xqd.meizhi.activity.base.BaseActivity;
import com.xqd.meizhi.bean.GirlBean;
import com.xqd.meizhi.db.DataBaseHelper;
import com.xqd.meizhi.utils.Invoke;
import cz.msebera.android.httpclient.cookie.Cookie;

import java.util.List;

import static com.xqd.meizhi.Constants.IntentKeys.GIRLBEAN;

public class WebActivity extends BaseActivity {


    @Bind(R.id.webview)
    WebView webview;
    @Bind(R.id.main_toolbar)
    Toolbar toolbar;
    @Bind(R.id.pb_web)
    ProgressBar pb;

    String title;
    String url;
    GirlBean mGirlBean;
    boolean hasBean = false;

    @Override
    public int getLayoutId() {
        return R.layout.activity_webview;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        SystemBarTintInvoke.apply(this, R.color.titlebar_bg_black, true);

        Intent intent = getIntent();
        title = intent.hasExtra(Constants.IntentKeys.TITLE) ? intent.getStringExtra(Constants.IntentKeys.TITLE) : "";
        url = intent.hasExtra(Constants.IntentKeys.URL) ? intent.getStringExtra(Constants.IntentKeys.URL) : "";

        if (intent.hasExtra(GIRLBEAN)) {
            mGirlBean = (GirlBean) intent.getSerializableExtra(GIRLBEAN);
            hasBean = true;

        }

        toolbar.setTitle(title);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true); // 决定左上角图标的右侧是否有向左的小箭头, true
        // 有小箭头，并且图标可以点击
        actionBar.setDisplayShowHomeEnabled(false);

        Log.e("WebActivity的网页链接", url);
        initWebView();
        initCookie();
        webview.loadUrl(url);

    }

    @SuppressLint("JavascriptInterface")
    private void initWebView() {
        webview.setHorizontalScrollBarEnabled(false);
        webview.setVerticalScrollBarEnabled(false);
        webview.setHorizontalScrollbarOverlay(false);
        WebSettings settings = webview.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
        }
        settings.setLoadWithOverviewMode(true);//设置加载进来的页面自适应手机屏幕
        settings.setUseWideViewPort(true);
        settings.setPluginState(WebSettings.PluginState.ON);
//        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            CookieManager.getInstance().setAcceptThirdPartyCookies(webview, true);
        }

        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                pb.setProgress(newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String rTitle) {
                super.onReceivedTitle(view, rTitle);
                if (StringUtils.isEmpty(title)) {
                    toolbar.setTitle(rTitle);
                }

            }
        });
        webview.setOnLongClickListener(new OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });


        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                try {
                    if (url.startsWith("mailto:") || url.startsWith("geo:") || url.startsWith("tel:")) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);
                    } else if (url.startsWith("http://") || url.startsWith("https://")) {
                        view.loadUrl(url);// 使用当前WebView处理跳转
                    }
                } catch (Exception e) {

                }
                return true;// true表示此事件在此处被处理，不需要再广播
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
//                dismissLoadingDialog();
                pb.setVisibility(View.GONE);

            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                pb.setVisibility(View.VISIBLE);
//                showLoadingDialog(null);
            }

            @Override
            // 转向错误时的处理
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

            }
        });
    }

    private void initCookie() {
        AsyncHttpClientUtil.getInstance(this);
        PersistentCookieStore myCookieStore = AsyncHttpClientUtil.getCookieStore();
        CookieSyncManager.createInstance(this);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.removeAllCookie();
        List<Cookie> cookies = myCookieStore.getCookies();
        // for(Cookie cookie:cookies){
        // String cookieString = cookie.getName() + "=" + cookie.getValue() + ";
        // domain=" + cookie.getDomain();
        // cookieManager.setCookie(url, cookieString);
        // }
        CookieSyncManager.getInstance().sync();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 1, 1, "Open with browser");
        menu.add(0, 2, 2, "Copy link");
        menu.add(0, 3, 3, "Collection");
//        menu.add(0, 4, 4, "Share");
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:// 点击返回图标事件
                this.finish();
                break;
            case 1:
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
                break;
            case 2:
                Invoke.clipboardCopy(this, url);
                T.showShort(this, "Copied to clipboard");
                break;
            case 3:
                boolean hasTile = false;
                DataBaseHelper dataBaseHelper = new DataBaseHelper(this, "collection_store.db", null, 2);
                SQLiteDatabase db = dataBaseHelper.getWritableDatabase();

                Cursor cursor1 = db.rawQuery("select * from collection", null);
                if (cursor1.moveToFirst()) {
                    do {
                        if (title.equals(cursor1.getString(cursor1.getColumnIndex("title")))) {
                            hasTile = true;
                        }
                    } while (cursor1.moveToNext());
                }
                if (hasTile) {
                    T.showShort(this, "already collected ");
                } else {
//                    db.beginTransaction();
                    db.execSQL("insert into collection(title,url,myId,createdAt,type,who,images)values(?,?,?,?,?,?,?)",
                            new String[]{title, url, hasBean ? mGirlBean.get_id() : "", hasBean ? mGirlBean.getCreatedAt() : "", hasBean ? mGirlBean.getType() : ""
                                    , hasBean ? mGirlBean.getWho() : "", hasBean ? (mGirlBean.getImages() == null ? "" : mGirlBean.getImages().get(0)) : ""});

//                    db.setTransactionSuccessful();
//                    db.endTransaction();
                    T.showShort(this, "collection success ");
                }

                cursor1.close();

//                ContentValues contentValues = new ContentValues();
//                contentValues.put("title", title);
//                contentValues.put("url", url);
//
//                db.insert("collection", null, contentValues);

                break;
//            case 4:
//                DataBaseHelper dataBaseHelper2 = new DataBaseHelper(this, "collection_store.db", null, 2);
//                SQLiteDatabase db2 = dataBaseHelper2.getWritableDatabase();
//                Cursor cursor = db2.rawQuery("select * from collection", null);
//                if (cursor.moveToFirst()) {
//                    do {
//                        Log.e("title=", cursor.getString(cursor.getColumnIndex("title")));
//                        Log.e("url=", cursor.getString(cursor.getColumnIndex("url")));
//                        Log.e("myId=", cursor.getString(cursor.getColumnIndex("myId")));
//                        Log.e("createdAt=", cursor.getString(cursor.getColumnIndex("createdAt")));
//                        Log.e("type=", cursor.getString(cursor.getColumnIndex("type")));
//                        Log.e("who=", cursor.getString(cursor.getColumnIndex("who")));
//                        Log.e("images=", cursor.getString(cursor.getColumnIndex("images")));
//                    } while (cursor.moveToNext());
//                }
//                cursor.close();
//                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        webview.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        webview.onResume();
    }


    @Override
    public void onBackPressed() {
        if (webview != null && webview.canGoBack()) {
            webview.goBack();
        } else {
            super.onBackPressed();
        }
    }

}
