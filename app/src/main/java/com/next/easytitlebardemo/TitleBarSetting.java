package com.next.easytitlebardemo;

/**
 * Created by Administrator on 2018/6/23.
 */

public class TitleBarSetting {

    //默认返回图标
    private int back_icon = R.drawable.tab_icon_back_black_default;


    public TitleBarSetting backIconRes(int back_icon) {
        this.back_icon = back_icon;
        return this;
    }


    public void reset() {
        back_icon = R.drawable.tab_icon_back_black_default;
    }
}
