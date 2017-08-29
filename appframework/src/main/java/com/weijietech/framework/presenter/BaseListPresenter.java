//package com.weijietech.framework.presenter;
//
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//
//import com.weijietech.framework.beans.Entity;
//import com.weijietech.framework.beans.ListEntity;
//import com.weijietech.framework.utils.TLog;
//
//import nucleus.presenter.RxPresenter;
//import rx.Observable;
//import rx.functions.Action2;
//import rx.functions.Func0;
//
//public abstract class BaseListPresenter<T extends Entity, ViewType extends BaseListFragmentUsingNucleus> extends RxPresenter<ViewType> {
//    private final static String TAG = BaseListPresenter.class.getSimpleName();
//    private static final int REQUEST_ITEMS = 1;
//    protected int mCurrentPage = 0;
//    protected boolean refreshFlag = false;
//    private AsyncTask<String, Void, ListEntity<T>> mCacheTask;
//
//    @Override
//    public void onCreate(Bundle savedState) {
//        super.onCreate(savedState);
//        initRxContract();
//
//        if (savedState == null) {
//            start(REQUEST_ITEMS);
//        }
//    }
//
//    public void requestData(boolean refresh) {
//        TLog.v(TAG, "RequestData, refresh is " + refresh);
//        setRefreshFlag(refresh);
//        start(REQUEST_ITEMS);
//    }
//
//    //子类必须在initRxFlow方法中调用该方法去设置默认的数据获取方式
//    public <T> void defaultRestartableLatestCache(final Func0<Observable<T>> observableFactory,
//                                                  final Action2<ViewType, T> onNext, @Nullable final Action2<ViewType, Throwable> onError) {
//        restartableLatestCache(REQUEST_ITEMS, observableFactory, onNext, onError);
//    }
//
//    //should be overrided
//    protected abstract void initRxContract();
//
//    public void setCurrentPageIndex(int pageIndex) {
//        mCurrentPage = pageIndex;
//    }
//
//    private void setRefreshFlag(boolean refresh) {
//        refreshFlag = refresh;
//    }
//}
