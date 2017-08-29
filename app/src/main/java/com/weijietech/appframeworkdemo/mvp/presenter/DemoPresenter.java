package com.weijietech.appframeworkdemo.mvp.presenter;

import com.weijietech.appframeworkdemo.mvp.contract.DemoContract;
import com.weijietech.framework.presenter.BasePresenterImpl;
import com.weijietech.framework.utils.TLog;

public class DemoPresenter extends BasePresenterImpl<DemoContract> {
    private final static String TAG = DemoPresenter.class.getSimpleName();

    @Override
    public void onCreate() {
        TLog.v(TAG, "onCreate");
    }

}
