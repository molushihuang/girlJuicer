package com.xqd.meizhi.myinterface;

/**
 * Created by Administrator on 2017/7/20.
 */

public interface OnChooseLetterChangedListener {
    /**
     * 滑动时
     * @param s
     */
    void onChooseLetter(String s);

    /**
     * 手指离开
     */
    void onNoChooseLetter();
}
