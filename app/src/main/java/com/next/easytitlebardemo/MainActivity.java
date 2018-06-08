package com.next.easytitlebardemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.next.easytitlebar.view.EasyTitleBar;

public class MainActivity extends AppCompatActivity {

    private EasyTitleBar easyTitleBar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        easyTitleBar = findViewById(R.id.easyTitleBar);
        easyTitleBar.setTitle("钉钉");
    }
}
