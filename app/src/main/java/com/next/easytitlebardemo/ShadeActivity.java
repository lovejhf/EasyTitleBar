package com.next.easytitlebardemo;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.next.easytitlebar.utils.EasyUtil;
import com.next.easytitlebar.view.EasyTitleBar;

/**
 * Created by Administrator on 2018/6/29.
 * 渐变demo界面
 */

public class ShadeActivity extends Activity {

    private EasyTitleBar titleBar;

    private NestedScrollView mNestedScrollView;

    private LinearLayout ll;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        // getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        setContentView(R.layout.activity_shade);

        initViews();

        initDataAndEvent();
    }

    private void initDataAndEvent() {
        mNestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                Log.e("滑动距离", scrollY + "------" + oldScrollY);
                int baseColor = getResources().getColor(R.color.appColor);
                titleBar.setBackgroundColor(EasyUtil.getColorWithRatio((float) scrollY / EasyUtil.dip2px(ShadeActivity.this, 800), baseColor));
            }
        });
    }


    private void initViews() {
        titleBar = findViewById(R.id.titleBar);
        titleBar.setEasyFitsWindows(true);
        mNestedScrollView = findViewById(R.id.mNestedScrollView);
    }


}
