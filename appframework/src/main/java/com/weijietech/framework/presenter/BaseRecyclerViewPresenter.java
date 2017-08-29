package com.weijietech.framework.presenter;

import android.os.AsyncTask;

import com.weijietech.framework.contract.BaseRecyclerViewContract;
import com.weijietech.framework.beans.Entity;
import com.weijietech.framework.beans.ListEntity;

public abstract class BaseRecyclerViewPresenter<T extends Entity, ViewType extends BaseRecyclerViewContract> extends BasePresenterImpl<ViewType> {
    private final static String TAG = BaseRecyclerViewPresenter.class.getSimpleName();
    private static final int REQUEST_ITEMS = 1;
    private static final int DEFAULT_PAGE_SIZE = 10;
    protected int mCurrentPage = 0;
    protected boolean refreshFlag = false;
    private AsyncTask<String, Void, ListEntity<T>> mCacheTask;

    @Override
    public void onCreate() {
    }

    public void requestData(boolean refresh) {
//        TLog.v(TAG, "RequestData, refresh is " + refresh);
//        setRefreshFlag(refresh);
////        start(REQUEST_ITEMS);
    }

//    //子类必须在initRxFlow方法中调用该方法去设置默认的数据获取方式
//    public <T> void defaultRestartableLatestCache(final Func0<Observable<T>> observableFactory,
//                                                  final Action2<ViewType, T> onNext, @Nullable final Action2<ViewType, Throwable> onError) {
//        restartableLatestCache(REQUEST_ITEMS, observableFactory, onNext, onError);
//    }

    public void setCurrentPageIndex(int pageIndex) {
        mCurrentPage = pageIndex;
    }

    public int getCurrentPageIndex() {
        return mCurrentPage;
    }

    protected int getPageSize() {
        return DEFAULT_PAGE_SIZE;
    }

    private void setRefreshFlag(boolean refresh) {
        refreshFlag = refresh;
    }
}
