package com.next.easytitlebardemo;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.next.easytitlebar.utils.EasyUtil;
import com.next.easytitlebar.view.EasyTitleBar;

import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {

    private EasyTitleBar titleBar;
    private EasyTitleBar whattitleBar;
    private EasyTitleBar titlebar03;

    protected void onCreate(Bundle savedInstanceState) {

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        // getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        setContentView(R.layout.activity_main);
        initViews();


        initTitleBar01();

        initTitleBar02();

        super.onCreate(savedInstanceState);
    }

    private void initTitleBar02() {
        whattitleBar.addLeftImg(R.mipmap.ic_launcher, new EasyTitleBar.MenuBuilder.OnMenuClickListener() {
            @Override
            public void OnMenuEvent() {
                Toast.makeText(MainActivity.this, "ic_launcher", Toast.LENGTH_SHORT).show();
            }
        });
        whattitleBar.addLeftText("halou", new EasyTitleBar.MenuBuilder.OnMenuClickListener() {
            @Override
            public void OnMenuEvent() {
                Toast.makeText(MainActivity.this, "halou", Toast.LENGTH_SHORT).show();
            }
        });
        whattitleBar.addRightText("哈哈哈 ");
        whattitleBar.addRightImg(R.mipmap.icon_history, new EasyTitleBar.MenuBuilder.OnMenuClickListener() {
            @Override
            public void OnMenuEvent() {
                Toast.makeText(MainActivity.this, "icon_history", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initTitleBar01() {

    /*    titlebar01.addRightImg(R.mipmap.icon_more, new EasyTitleBar.OnItemClickListener() {
            @Override
            public void OnItemEvent() {
                Toast.makeText(MainActivity.this, "search", Toast.LENGTH_SHORT).show();
                titlebar01.setTitleStyle(EasyTitleBar.TITLE_STYLE_LEFT);
                //titlebar01.getLeftLayout().setVisibility(View.GONE);
            }
        });*/
        Log.e("getMenuTextSize", EasyUtil.px2sp(this, titleBar.getMenuTextSize())+"");
        titleBar.addRightView(new EasyTitleBar.MenuBuilder(MainActivity.this,titleBar)
                .icon(R.mipmap.icon_more)
                .onItemClickListener(new EasyTitleBar.MenuBuilder.OnMenuClickListener() {
                    @Override
                    public void OnMenuEvent() {
                        titleBar.setTitleStyle(EasyTitleBar.TITLE_STYLE_LEFT);
                        titleBar.getBackLayout().setVisibility(View.GONE);
                    }
                })
                .createImage());

        titleBar.addRightView(
                new EasyTitleBar.MenuBuilder(MainActivity.this,titleBar)
                .text("居中")
                .onItemClickListener(new EasyTitleBar.MenuBuilder.OnMenuClickListener() {
                    @Override
                    public void OnMenuEvent() {
                        titleBar.setTitleStyle(EasyTitleBar.TITLE_STYLE_CENTER);
                        titleBar.getBackLayout().setVisibility(View.VISIBLE);
                    }
                })
                .createText());
        titleBar.getRightLayout(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "嘻嘻嘻", Toast.LENGTH_SHORT).show();
            }
        });
        titleBar.getRightLayout(1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "11111111111111", Toast.LENGTH_SHORT).show();
            }
        });

        View view = LayoutInflater.from(this).inflate(R.layout.schedule_menu_view, null);
        titlebar03.addRightView(view);
    }

    private void initViews() {
        titleBar = findViewById(R.id.titleBar);
        whattitleBar = findViewById(R.id.whattitleBar);
        titlebar03 = findViewById(R.id.titlebar03);
    }
}
