package com.xqd.meizhi.activity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import butterknife.Bind;
import com.anthole.quickdev.invoke.SystemBarTintInvoke;
import com.xqd.meizhi.R;
import com.xqd.meizhi.activity.base.BaseActivity;
import com.xqd.meizhi.adapter.CollectionAdapter;
import com.xqd.meizhi.bean.GirlBean;
import com.xqd.meizhi.db.DataBaseHelper;
import com.xqd.meizhi.utils.Invoke;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by pherson on 2017-5-11.
 */

public class CollectionActivity extends BaseActivity implements CollectionAdapter.ItemListener {


    @Bind(R.id.main_toolbar)
    Toolbar toolbar;
    @Bind(R.id.recycleview)
    RecyclerView mRecyclerView;

    CollectionAdapter mCollectionAdapter;
    List<GirlBean> mGirlBeanList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_collection;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        SystemBarTintInvoke.apply(this, R.color.blue, true);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true); // 决定左上角图标的右侧是否有向左的小箭头, true
        // 有小箭头，并且图标可以点击
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setTitle("collections");

        mCollectionAdapter = new CollectionAdapter(this, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mCollectionAdapter);

        DataBaseHelper dataBaseHelper2 = new DataBaseHelper(this, "collection_store.db", null, 2);
        SQLiteDatabase db2 = dataBaseHelper2.getWritableDatabase();
        Cursor cursor = db2.rawQuery("select * from collection", null);

        if (cursor.moveToFirst()) {
            do {
                GirlBean girlBean = new GirlBean();
                girlBean.set_id(cursor.getString(cursor.getColumnIndex("myId")));
                girlBean.setCreatedAt(cursor.getString(cursor.getColumnIndex("createdAt")));
                girlBean.setDesc(cursor.getString(cursor.getColumnIndex("title")));
                girlBean.setType(cursor.getString(cursor.getColumnIndex("type")));
                girlBean.setWho(cursor.getString(cursor.getColumnIndex("who")));
                girlBean.setUrl(cursor.getString(cursor.getColumnIndex("url")));
                List<String> imges = new ArrayList<>();
                imges.add(0, cursor.getString(cursor.getColumnIndex("images")));

                girlBean.setImages(imges);

                mGirlBeanList.add(girlBean);

            } while (cursor.moveToNext());
        }

        cursor.close();
        Collections.reverse(mGirlBeanList);
        mCollectionAdapter.addAll(mGirlBeanList);


    }

    @Override
    public void onItemLongClick(final GirlBean item, final int posion) {

        Log.e("position>>>>>>>>>>>>>", posion + "");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Tip")
                .setMessage("delete it ?")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        
                        mCollectionAdapter.remove(posion);
                        mCollectionAdapter.notifyDataSetChanged();

                        DataBaseHelper dataBaseHelper2 = new DataBaseHelper(CollectionActivity.this, "collection_store.db", null, 2);
                        SQLiteDatabase db2 = dataBaseHelper2.getWritableDatabase();
                        db2.execSQL("delete from collection where myId= ?", new String[]{item.get_id()});

                    }
                })
                .setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create().show();

    }

    @Override
    public void onItemClick(GirlBean item) {
        Invoke.openLink(this, item.getUrl(), item.getDesc(), item);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:// 点击返回图标事件
                this.finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
