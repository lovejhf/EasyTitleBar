package com.next.easytitlebardemo.ui.demo;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;

import com.next.easytitlebar.view.EasyTitleBar;
import com.next.easytitlebardemo.R;
import com.next.easytitlebardemo.base.BaseFragment;
import com.next.easytitlebardemo.util.EasyStatusBarUtil;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/7/30.
 */

public class IndexFragment extends BaseFragment {

    @BindView(R.id.titleBar)
    EasyTitleBar titleBar;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_index;
    }

    @Override
    protected void onViewCreated() {
        if(((MainActivity)getActivity()).getMode()==0){
            titleBar.setFitColor(ContextCompat.getColor(getContext(), R.color.status_bar_color));
        }else{
            titleBar.setFitColor(ContextCompat.getColor(getContext(),R.color.white));
        }
    }

    @Override
    protected void initEventAndData() {

    }
    @Override
    public void lazyLoad() {
        super.lazyLoad();
        EasyStatusBarUtil.StatusBarLightMode(getActivity(), R.color.white, R.color.status_bar_color); //设置白底黑字
    }


}
