package com.xqd.meizhi.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.ArrayMap;
import butterknife.BindView;
import com.xqd.meizhi.R;
import com.xqd.meizhi.activity.base.BaseActivity;
import com.xqd.meizhi.adapter.MyAdapter;
import com.xqd.meizhi.bean.Contant;
import com.xqd.meizhi.myinterface.OnChooseLetterChangedListener;
import com.xqd.meizhi.utils.PinyinTool;
import com.xqd.meizhi.view.SideBar;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Administrator on 2017/7/20.
 */

public class LetterSortActivity extends BaseActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.sidebar)
    SideBar sideBar;

    private List<Contant> mContantList;
    private ArrayMap<String, Integer> lettes;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_letter_sort;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {


        sideBar.setOnTouchingLetterChangedListener(new OnChooseLetterChangedListener() {
            @Override
            public void onChooseLetter(String s) {

                selectRecyclerView(s);
            }

            @Override
            public void onNoChooseLetter() {

            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        setData();
    }

    private void selectRecyclerView(String s) {
        if (s.equals("搜") || s.equals("#")) {
            recyclerView.scrollToPosition(0);
        } else {
            if (lettes.containsKey(s)) {
                recyclerView.scrollToPosition(lettes.get(s));
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void setData() {
        lettes = new ArrayMap<>();
        mContantList = new ArrayList<>();
        mContantList.add(new Contant("张某"));
        mContantList.add(new Contant("李某"));
        mContantList.add(new Contant("韩某"));
        mContantList.add(new Contant("左某"));
        mContantList.add(new Contant("汉某"));
        mContantList.add(new Contant("顾某"));
        mContantList.add(new Contant("焦某"));
        mContantList.add(new Contant("孔某"));
        mContantList.add(new Contant("商某"));
        mContantList.add(new Contant("沈某"));
        mContantList.add(new Contant("夏某"));
        mContantList.add(new Contant("赵四"));
        mContantList.add(new Contant("钱某"));
        mContantList.add(new Contant("孙丽"));
        mContantList.add(new Contant("李四"));
        mContantList.add(new Contant("吴三桂"));
        mContantList.add(new Contant("王某"));
        mContantList.add(new Contant("冯某"));
        mContantList.add(new Contant("陈某"));
        mContantList.add(new Contant("诸某"));

        //获取名字首字母-大写
        for (Contant contant : mContantList) {
            String cahr = null;
            try {
                cahr = String.valueOf(new PinyinTool().toPinYin(contant.getName()).charAt(0)).toUpperCase();
            } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                badHanyuPinyinOutputFormatCombination.printStackTrace();
            }
            contant.setFirstChar(cahr);

        }
        //根据首字母排序
        Collections.sort(mContantList, new Comparator<Contant>() {
            @Override
            public int compare(Contant contant, Contant t1) {
                return contant.getFirstChar().compareTo(t1.getFirstChar());
            }
        });
        //保存每个字母下的联系人在数据中的位置
        for (int i = 0; i < mContantList.size(); i++) {
            mContantList.get(i).setHeadIndex(i);
            if (!lettes.containsKey(mContantList.get(i).getFirstChar())) {
                lettes.put(mContantList.get(i).getFirstChar(), i);
            }
        }

        //加载数据
        recyclerView.setAdapter(new MyAdapter(mContantList, this, lettes));
    }
}
