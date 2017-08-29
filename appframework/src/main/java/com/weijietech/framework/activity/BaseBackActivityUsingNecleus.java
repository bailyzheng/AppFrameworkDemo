//package com.weijietech.framework.activity;
//
//import android.support.v7.app.ActionBar;
//import android.support.v7.widget.Toolbar;
//import android.widget.TextView;
//
//import nucleus.presenter.Presenter;
//
///**
// * Created by JuQiu
// * on 16/6/20.
// */
//
//public abstract class BaseBackActivityUsingNecleus<P extends Presenter> extends BaseActivityUsingNecleus<P> {
//    @Override
//    protected void initWindow() {
//        super.initWindow();
//        ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null) {
//            actionBar.setDisplayHomeAsUpEnabled(true);
//            actionBar.setHomeButtonEnabled(false);
//            actionBar.setDisplayShowTitleEnabled(false);
//        }
//    }
//
//    @Override
//    public boolean onSupportNavigateUp() {
//        finish();
//        return super.onSupportNavigateUp();
//    }
//
//    protected void initToolBar(int toolbarId, int toolbarTitleId, String title) {
//        Toolbar toolbar = (Toolbar) findViewById(toolbarId);
//        TextView textView = (TextView) findViewById(toolbarTitleId);
//        if (textView != null) {
//            textView.setText(title);
//        }
//        setSupportActionBar(toolbar);
//    }
//}
