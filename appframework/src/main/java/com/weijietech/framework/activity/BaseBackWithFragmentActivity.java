package com.weijietech.framework.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.weijietech.framework.presenter.BasePresenter;
import com.weijietech.framework.utils.TLog;

/**
 * Created by JuQiu
 * on 16/6/20.
 */

public abstract class BaseBackWithFragmentActivity<P extends BasePresenter> extends BaseWithFragmentActivity<P> {
    final private static String TAG = BaseBackWithFragmentActivity.class.getSimpleName();

    private TextView tvTitle;
    protected boolean noTitle = false;

    @Override
    protected void initWindow() {
        super.initWindow();
        ActionBar actionBar = getSupportActionBar();

        if (noTitle) {
            actionBar.hide();
        } else if (actionBar != null) {
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
        tvTitle = (TextView) findViewById(toolbarTitleId);
        if (tvTitle != null && title != null) {
            tvTitle.setText(title);
        }
        setSupportActionBar(toolbar);
    }

    protected void setCustomTitle(String title) {
        if (tvTitle != null) {
            tvTitle.setText(title);
        }
    }
}
