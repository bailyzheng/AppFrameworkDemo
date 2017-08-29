package com.weijietech.framework.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.weijietech.framework.contract.BaseViewContract;
import com.weijietech.framework.presenter.BasePresenter;

import butterknife.ButterKnife;

/**
 * Created by JuQiu
 * on 16/6/20.
 */

public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements BaseViewContract {
//    protected RequestManager mImageLoader;
    private boolean mIsDestroy;
    protected T mPresenter;
    private final String mPackageNameUmeng = this.getClass().getName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (initBundle(getIntent().getExtras())) {
            setContentView(getContentView());
            ButterKnife.bind(this);

            initPresenter();
            if (mPresenter != null) {
                mPresenter.onCreate();
            }

            initWindow();
            initWidget();
            initData();

        } else {
            finish();
        }

        //umeng analytics
//        MobclickAgent.setDebugMode(false);
//        MobclickAgent.openActivityDurationTrack(false);
//        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (mPresenter != null) {
            mPresenter.onViewCreated();
        }
    }

    protected abstract void initPresenter();
    protected T getPresenter() {
        return mPresenter;
    }

    @Override
    protected void onResume() {
        super.onResume();
//        MobclickAgent.onPageStart(this.mPackageNameUmeng);
//        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        MobclickAgent.onPageEnd(this.mPackageNameUmeng);
//        MobclickAgent.onPause(this);
    }

    protected abstract int getContentView();

    protected boolean initBundle(Bundle bundle) {
        return true;
    }

    protected void initWindow() {
    }

    protected void initWidget() {
    }

    protected void initData() {
    }

//    public synchronized RequestManager getImageLoader() {
//        if (mImageLoader == null)
//            mImageLoader = Glide.with(this);
//        return mImageLoader;
//    }

    @Override
    protected void onDestroy() {
        mIsDestroy = true;
        if (mPresenter != null) {
            mPresenter.onDestroy();
            mPresenter = null;
        }
//        ButterKnife.unbind(this);
        super.onDestroy();
    }

    public boolean isDestroy() {
        return mIsDestroy;
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void showProgress(String msg) {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showMsg(String message) {

    }

    @Override
    public void showMsg(int messageId) {

    }

    @Override
    public void handleErrorCode(int errno) {

    }
}
