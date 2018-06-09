package com.next.easytitlebardemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.next.easytitlebar.view.EasyTitleBar;

public class MainActivity extends AppCompatActivity {

    private EasyTitleBar easyTitleBar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        easyTitleBar = findViewById(R.id.easyTitleBar);
        easyTitleBar.setTitle("钉钉");


        easyTitleBar.addItem(R.mipmap.ic_launcher, new EasyTitleBar.OnItemClickListener() {
            @Override
            public void OnItemEvent() {
                Toast.makeText(MainActivity.this,"search",Toast.LENGTH_SHORT).show();
                easyTitleBar.setTitleStyle(EasyTitleBar.TITLE_STYLE_LEFT);
                easyTitleBar.getLeftLayout().setVisibility(View.GONE);
            }
        });

        easyTitleBar.addItem(R.mipmap.ic_launcher, new EasyTitleBar.OnItemClickListener() {
            @Override
            public void OnItemEvent() {
                Toast.makeText(MainActivity.this,"first",Toast.LENGTH_SHORT).show();
                easyTitleBar.setTitleStyle(EasyTitleBar.TITLE_STYLE_CENTER);
                easyTitleBar.getLeftLayout().setVisibility(View.VISIBLE);
            }
        });
    }
}
