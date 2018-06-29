package com.next.easytitlebardemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * Created by Administrator on 2018/6/29.
 */

public class DemoListActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_demo_list);
    }

    //渐变
    public void onShade(View view){
        startActivity(new Intent(DemoListActivity.this,ShadeActivity.class));
    }

    public void onNormal(View view){
        startActivity(new Intent(DemoListActivity.this,MainActivity.class));
    }
}
