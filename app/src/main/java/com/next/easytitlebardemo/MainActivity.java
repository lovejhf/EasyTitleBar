package com.next.easytitlebardemo;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.next.easytitlebar.view.EasyTitleBar;

public class MainActivity extends AppCompatActivity {

    private EasyTitleBar titleBar;
    private EasyTitleBar titlebar02;

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
        titlebar02 = findViewById(R.id.titlebar02);
        titlebar02.addLeftImg(R.mipmap.ic_launcher, new EasyTitleBar.LayoutBuilder.OnMenuClickListener() {
            @Override
            public void OnMenuEvent() {
                Toast.makeText(MainActivity.this, "ic_launcher", Toast.LENGTH_SHORT).show();
            }
        });
        titlebar02.addLeftText("halou", new EasyTitleBar.LayoutBuilder.OnMenuClickListener() {
            @Override
            public void OnMenuEvent() {
                Toast.makeText(MainActivity.this, "halou", Toast.LENGTH_SHORT).show();
            }
        });
        titlebar02.addRightText("哈哈哈 ");
        titlebar02.addRightImg(R.mipmap.icon_history, new EasyTitleBar.LayoutBuilder.OnMenuClickListener() {
            @Override
            public void OnMenuEvent() {
                Toast.makeText(MainActivity.this, "icon_history", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initTitleBar01() {
        titleBar.setTitle("钉钉");


    /*    titlebar01.addRightImg(R.mipmap.icon_more, new EasyTitleBar.OnItemClickListener() {
            @Override
            public void OnItemEvent() {
                Toast.makeText(MainActivity.this, "search", Toast.LENGTH_SHORT).show();
                titlebar01.setTitleStyle(EasyTitleBar.TITLE_STYLE_LEFT);
                //titlebar01.getLeftLayout().setVisibility(View.GONE);
            }
        });*/
        titleBar.getRightLayout(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "嘻嘻嘻", Toast.LENGTH_SHORT).show();
            }
        });

        titleBar.addRightView(new EasyTitleBar.LayoutBuilder(MainActivity.this)
                .icon(R.mipmap.icon_more)
                .onItemClickListener(new EasyTitleBar.LayoutBuilder.OnMenuClickListener() {
                    @Override
                    public void OnMenuEvent() {
                        titleBar.setTitleStyle(EasyTitleBar.TITLE_STYLE_LEFT);
                        titleBar.getBackLayout().setVisibility(View.GONE);
                    }
                })
                .createImage());

        titleBar.addRightView(new EasyTitleBar.LayoutBuilder(MainActivity.this)
                .text("居中")
                .onItemClickListener(new EasyTitleBar.LayoutBuilder.OnMenuClickListener() {
                    @Override
                    public void OnMenuEvent() {
                        titleBar.setTitleStyle(EasyTitleBar.TITLE_STYLE_CENTER);
                        titleBar.getBackLayout().setVisibility(View.VISIBLE);
                    }
                })
                .createText());

    }

    private void initViews() {
        titleBar = findViewById(R.id.titleBar);
        titlebar02 = findViewById(R.id.titlebar02);
    }
}
