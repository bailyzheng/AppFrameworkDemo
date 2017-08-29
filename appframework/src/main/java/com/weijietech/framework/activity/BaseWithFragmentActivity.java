package com.weijietech.framework.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.weijietech.framework.activity.BaseActivity;
import com.weijietech.framework.presenter.BasePresenter;

/**
 * Created by eagle on 2016-12-08.
 */

public abstract class BaseWithFragmentActivity<T extends BasePresenter>extends BaseActivity<T> {
    private Fragment mFragment;

    protected void addFragment(int frameLayoutId, Fragment fragment) {
        if (fragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if (fragment.isAdded()) {
                if (mFragment != null) {
                    transaction.hide(mFragment).show(fragment);
                } else {
                    transaction.show(fragment);
                }
            } else {
                if (mFragment != null) {
                    transaction.hide(mFragment).add(frameLayoutId, fragment);
                } else {
                    transaction.add(frameLayoutId, fragment);
                }
            }
            mFragment = fragment;
            transaction.commit();
        }
    }

    protected void replaceFragment(int frameLayoutId, Fragment fragment) {
        if (fragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(frameLayoutId, fragment);
            transaction.commit();
        }
    }
}
