package com.next.easytitlebardemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.next.easytitlebar.view.EasyTitleBar;

public class MainActivity extends AppCompatActivity {

    private EasyTitleBar titlebar01;
    private EasyTitleBar titlebar02;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();


        initTitleBar01();

        initTitleBar02();
    }

    private void initTitleBar02() {
        final ImageView image = titlebar02.addLeftImg(R.mipmap.icon_more,15,0, new EasyTitleBar.OnItemClickListener() {
            @Override
            public void OnItemEvent() {
                Toast.makeText(MainActivity.this, "search", Toast.LENGTH_SHORT).show();
            }
        });

        titlebar02.addLeftText("Back",3,15, new EasyTitleBar.OnItemClickListener() {
            @Override
            public void OnItemEvent() {
                image.setImageResource(R.mipmap.icon_l);
                Toast.makeText(MainActivity.this, "Back", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initTitleBar01() {
        titlebar01.setTitle("钉钉");


        titlebar01.addRightImg(R.mipmap.icon_more, new EasyTitleBar.OnItemClickListener() {
            @Override
            public void OnItemEvent() {
                Toast.makeText(MainActivity.this, "search", Toast.LENGTH_SHORT).show();
                titlebar01.setTitleStyle(EasyTitleBar.TITLE_STYLE_LEFT);
                //titlebar01.getLeftLayout().setVisibility(View.GONE);
            }
        });

        titlebar01.addRightImg(R.mipmap.icon_history, new EasyTitleBar.OnItemClickListener() {
            @Override
            public void OnItemEvent() {
                Toast.makeText(MainActivity.this, "first", Toast.LENGTH_SHORT).show();
                titlebar01.setTitleStyle(EasyTitleBar.TITLE_STYLE_CENTER);
              //  titlebar01.getLeftLayout().setVisibility(View.VISIBLE);
            }
        });
    }

    private void initViews() {
        titlebar01 = findViewById(R.id.titlebar01);
        titlebar02 = findViewById(R.id.titlebar02);
    }
}
