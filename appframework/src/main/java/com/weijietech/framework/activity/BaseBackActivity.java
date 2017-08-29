package com.weijietech.framework.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.weijietech.framework.presenter.BasePresenter;

/**
 * Created by JuQiu
 * on 16/6/20.
 */

public abstract class BaseBackActivity<P extends BasePresenter> extends BaseActivity<P> {
    @Override
    protected void initWindow() {
        super.initWindow();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    protected void initToolBar(int toolbarId, int toolbarTitleId, String title) {
        Toolbar toolbar = (Toolbar) findViewById(toolbarId);
        if (toolbarTitleId > 0) {
            TextView textView = (TextView) findViewById(toolbarTitleId);
            if (textView != null) {
                textView.setText(title);
            }
        } else {
            toolbar.setTitle(title);
        }
        setSupportActionBar(toolbar);
    }

    protected void setToolBarTitle(String title) {
//        Toolbar toolbar = (Toolbar) findViewById(toolbarId);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(title);

    }
}
