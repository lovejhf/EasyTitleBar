package com.next.easytitlebardemo.ui.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.next.easytitlebar.view.EasyTitleBar;
import com.next.easytitlebardemo.R;
import com.next.easytitlebardemo.base.BaseFragment;
import com.next.easytitlebardemo.util.StatusBarUtil;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/7/30.
 */

public class MessageFragment extends BaseFragment {

    @BindView(R.id.titleBar)
    EasyTitleBar titleBar;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_message;
    }

    @Override
    protected void onViewCreated() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.schedule_menu_view, null);
        titleBar.addRightView(view);
        titleBar.getRightLayout(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                titleBar.getBackLayout().setVisibility(View.VISIBLE);
                if(titleBar.getTitleStyle()==1){
                    titleBar.setTitleStyle(EasyTitleBar.TITLE_STYLE_CENTER);
                }else{
                    titleBar.setTitleStyle(EasyTitleBar.TITLE_STYLE_LEFT);
                }
            }
        });
    }

    @Override
    protected void initEventAndData() {

    }

}
