package com.next.easytitlebardemo.ui.demo;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import com.next.easynavigition.view.EasyNavigitionBar;
import com.next.easytitlebardemo.R;
import com.next.easytitlebardemo.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class TestActivity extends BaseActivity {

    @BindView(R.id.mNavigitionBar)
    EasyNavigitionBar mNavigitionBar;


    private String[] tabText = {"首页", "发现", "消息", "我的"};
    //未选中icon
    private int[] normalIcon = {R.mipmap.index, R.mipmap.find, R.mipmap.message, R.mipmap.me};
    //选中时icon
    private int[] selectIcon = {R.mipmap.index1, R.mipmap.find1, R.mipmap.message1, R.mipmap.me1};

    private List<android.support.v4.app.Fragment> fragments = new ArrayList<>();

    @Override
    protected int getScreenMode() {
        return 1;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_navigition;
    }

    @Override
    protected void onViewCreated() {

        initNavigition();
    }

    private void initNavigition() {
        fragments.add(new IndexFragment());
        fragments.add(new DiscoverFragment());
        fragments.add(new MessageFragment());
        fragments.add(new MeFragment());

        mNavigitionBar.titleItems(tabText)
                .normalIconItems(normalIcon)
                .selectIconItems(selectIcon)
                .fragmentList(fragments)
                .fragmentManager(getSupportFragmentManager())
                .build();
    }

    @Override
    protected void initEventAndData() {

    }
}
