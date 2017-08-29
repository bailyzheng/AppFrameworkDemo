/*
 * Copyright (c) 2016 咖枯 <kaku201313@163.com | 3772304@qq.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.weijietech.framework.presenter;

import android.support.annotation.NonNull;

import com.weijietech.framework.contract.BaseViewContract;
import com.weijietech.framework.utils.MyUtils;

import org.reactivestreams.Subscription;

import io.reactivex.disposables.Disposable;

/**
 * @author 咖枯
 * @version 1.0 2016/6/2
 */
public class BasePresenterImpl<T extends BaseViewContract> implements BasePresenter {
    protected T mView;
    protected Disposable mSubscription;

    @Override
    public void onCreate() {

    }

    @Override
    public void onViewCreated() {

    }

    @Override
    public void onDestroy() {
        MyUtils.cancelSubscription(mSubscription);
        mView = null;
    }

    @Override
    public void attachView(@NonNull BaseViewContract view) {
        // TODO?
        mView = (T) view;
    }

    protected T getAttachedView() {
        return mView;
    }

//    @Override
//    public void beforeRequest() {
//        mView.showProgress();
//    }
//
//    @Override
//    public void success(E data) {
//        mView.hideProgress();
//    }
//
//    @Override
//    public void onError(String errorMsg) {
//        mView.hideProgress();
//        mView.showMsg(errorMsg);
//    }

}
