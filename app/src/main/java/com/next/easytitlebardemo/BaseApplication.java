package com.next.easytitlebardemo;

import android.app.Application;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;

import com.next.easytitlebar.view.EasyTitleBar;

/**
 * Created by Administrator on 2018/6/25.
 */

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        initTitleBar();
    }

    private void initTitleBar() {
        EasyTitleBar.init()
                .backIconRes(R.mipmap.icon_l)
                .backgroud(ContextCompat.getColor(this, R.color.appColor))
                .titleSize(12)
                .showLine(false)
                .titleColor(Color.parseColor("#ffffff"))
                .titleBarHeight(48);
    }
}
