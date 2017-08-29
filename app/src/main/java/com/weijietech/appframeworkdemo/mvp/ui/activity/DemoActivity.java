package com.weijietech.appframeworkdemo.mvp.ui.activity;

import android.app.ProgressDialog;
import android.view.View;
import android.widget.Toast;

import com.weijietech.appframeworkdemo.R;
import com.weijietech.appframeworkdemo.mvp.contract.DemoContract;
import com.weijietech.appframeworkdemo.mvp.presenter.DemoPresenter;
import com.weijietech.framework.activity.BaseBackActivity;
import com.weijietech.framework.utils.DialogHelper;

import butterknife.OnClick;

/**
 * Created by eagle on 2016/11/11.
 */
public class DemoActivity extends BaseBackActivity<DemoPresenter> implements View.OnClickListener, DemoContract {
    private final static String TAG = DemoActivity.class.getSimpleName();

    private ProgressDialog mDialog;

    @Override
    protected void initPresenter() {
        mPresenter = new DemoPresenter();
        mPresenter.attachView(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_demo;
    }

    @Override
    protected void initWidget() {

    }

    @Override
    protected void initWindow() {
        initToolBar(R.id.toolbar, R.id.toolbar_title, "Demo");
        super.initWindow();
    }

    @OnClick({})
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
        }
    }

    @Override
    public void showProgress() {
        showWaitDialog("请稍候...");
    }

    @Override
    public void showProgress(String msg) {

    }

    @Override
    public void hideProgress() {
        hideWaitDialog();
    }

    @Override
    public void showMsg(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMsg(int messageId) {

    }

    public ProgressDialog showWaitDialog(String message) {
        if (mDialog == null) {
            mDialog = DialogHelper.getProgressDialog(this, message);
        }

        mDialog.setMessage(message);
        mDialog.show();

        return mDialog;
    }

    public void hideWaitDialog() {
        ProgressDialog dialog = mDialog;
        if (dialog != null) {
            mDialog = null;
            try {
                dialog.dismiss();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
