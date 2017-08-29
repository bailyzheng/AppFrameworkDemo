package com.weijietech.appframeworkdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.weijietech.appframeworkdemo.mvp.ui.activity.DemoActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goToDemoActivity(View view) {
        Intent intent = new Intent(this, DemoActivity.class);
        startActivity(intent);
    }
}
