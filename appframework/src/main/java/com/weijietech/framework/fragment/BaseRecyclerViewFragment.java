package com.weijietech.framework.fragment;

import android.annotation.SuppressLint;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.weijietech.framework.contract.BaseRecyclerViewContract;
import com.weijietech.framework.presenter.BaseRecyclerViewPresenter;
import com.weijietech.framework.EmptyLayout;
import com.weijietech.framework.R;
import com.weijietech.framework.adapter.BaseLoadMoreAdapter;
import com.weijietech.framework.adapter.BaseState;
import com.weijietech.framework.beans.Entity;
import com.weijietech.framework.beans.ListEntity;
import com.weijietech.framework.beans.Result;
import com.weijietech.framework.utils.TLog;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@SuppressLint("NewApi")
public abstract class BaseRecyclerViewFragment<T extends Entity, PresenterType extends BaseRecyclerViewPresenter> extends BaseFragment<PresenterType>
        implements BaseRecyclerViewContract<T>, SwipeRefreshLayout.OnRefreshListener, OnItemClickListener, OnScrollListener {
    protected final String TAG = BaseRecyclerViewFragment.class.getSimpleName();

    public static final String BUNDLE_KEY_CATALOG = "BUNDLE_KEY_CATALOG";

    protected SwipeRefreshLayout mSwipeRefreshLayout;

    protected RecyclerView mRecyclerView;

    protected BaseLoadMoreAdapter<T> mAdapter;

    protected EmptyLayout mErrorLayout;

    protected int mStoreEmptyState = -1;

    protected int mCurrentPage = 0;

    protected int mCatalog = 1;
    // 错误信息
    protected Result mResult;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_pull_refresh_recyclerview;
    }

    public void initRecyclerView(RecyclerView view) {}

    @Override
    public void initWidget(View view) {
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefreshlayout);
        mRecyclerView= (RecyclerView) view.findViewById(R.id.recyclerView);
        mErrorLayout = (EmptyLayout) view.findViewById(R.id.error_layout);

        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(
                R.color.swiperefresh_color1, R.color.swiperefresh_color2,
                R.color.swiperefresh_color3, R.color.swiperefresh_color4);

        mErrorLayout.setOnLayoutClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mCurrentPage = 0;
                mState = STATE_REFRESH;
                mErrorLayout.setErrorType(EmptyLayout.NETWORK_LOADING);
                mPresenter.requestData(true);
            }
        });

        if (mAdapter != null) {
            mRecyclerView.setAdapter(mAdapter);
            mErrorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
        } else {
            mAdapter = getRecyclerViewAdapter();
            mRecyclerView.setAdapter(mAdapter);

            if (requestDataIfViewCreated()) {
                mErrorLayout.setErrorType(EmptyLayout.NETWORK_LOADING);
                mState = STATE_NONE;
                if (mPresenter == null) {
                    Log.e(TAG, "Please init presenter first");
                } else {
                    mPresenter.requestData(false);
                }
            } else {
                mErrorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
            }
        }

        mAdapter.setOnLoadMoreListener(new BaseLoadMoreAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                TLog.v(TAG, "onLoadMore");
                if (mAdapter.getState() != BaseState.STATE_NO_MORE) {
                    mCurrentPage++;
                    mPresenter.setCurrentPageIndex(mCurrentPage);
                    mPresenter.requestData(false);
                    mAdapter.setState(BaseState.STATE_LOADING_MORE);
                }
            }
        });

//        mAdapter.setOnItemClickListener(new BaseLoadMoreAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                Toast.makeText(getContext(),position+"",Toast.LENGTH_SHORT).show();
//            }
//        });

        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);

        initRecyclerView(mRecyclerView);

        if (mStoreEmptyState != -1) {
            mErrorLayout.setErrorType(mStoreEmptyState);
        }
    }

    @Override
    public void onDestroyView() {
        mStoreEmptyState = mErrorLayout.getErrorState();
        super.onDestroyView();
    }

    protected abstract BaseLoadMoreAdapter<T> getRecyclerViewAdapter();

    // 下拉刷新数据
    @Override
    public void onRefresh() {
        TLog.v(TAG, "onRefresh");
        if (mState == STATE_REFRESH) {
            TLog.v(TAG, "STATE_REFRESH is Refreshing");
            return;
        }
        // 设置顶部正在刷新
//        mListView.setSelection(0);
        setSwipeRefreshLoadingState();
        mCurrentPage = 0;
        mPresenter.setCurrentPageIndex(mCurrentPage);
        mState = STATE_REFRESH;
        mPresenter.requestData(true);
    }

    protected boolean requestDataIfViewCreated() {
        return true;
    }

    protected ListEntity<T> parseList(InputStream is) throws Exception {
        return null;
    }

    protected ListEntity<T> readList(Serializable seri) {
        return null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
    }

    public void executeOnLoadDataSuccessOnce(List<T> data) {
        if (data == null) {
            TLog.v(TAG, "data is null");
            data = new ArrayList<T>();
        }

        mErrorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
        if (mCurrentPage == 0) {
            mAdapter.clear();
        }

        for (int i = 0; i < data.size(); i++) {
            if (compareTo(mAdapter.getData(), data.get(i))) {
                TLog.v(TAG, "remove");
                data.remove(i);
                i--;
            }
        }
        int adapterState = BaseState.STATE_EMPTY_ITEM;
        if ((mAdapter.getCount() + data.size()) == 0) {
            adapterState = BaseState.STATE_EMPTY_ITEM;
        } else if (data.size() == 0
                || (data.size() < getPageSize() && mCurrentPage == 0)) {
//            TLog.v(TAG, "state no more");
//            TLog.v(TAG, "data.size is " + data.size());
//            TLog.v(TAG, "getPageSize is " + getPageSize());
//            TLog.v(TAG, "mCurrentPage is " + mCurrentPage);
            adapterState = BaseState.STATE_NO_MORE;
            mAdapter.notifyDataSetChanged();
        } else {
            adapterState = BaseState.STATE_NO_MORE;
        }
        mAdapter.setState(adapterState);
        TLog.v(TAG, "adapterState is " + adapterState);
        mAdapter.addData(data);
        // 判断等于是因为最后有一项是listview的状态
        if (mAdapter.getCount() == 1) {

            if (needShowEmptyNoData()) {
                mErrorLayout.setErrorType(EmptyLayout.NODATA);
            } else {
                mAdapter.setState(BaseState.STATE_EMPTY_ITEM);
                mAdapter.notifyDataSetChanged();
            }
        }

        executeOnLoadFinish();
    }
    public void executeOnLoadDataSuccess(List<T> data) {
        if (data == null) {
            TLog.v(TAG, "data is null");
            data = new ArrayList<T>();
        }

        mErrorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
        if (mCurrentPage == 0) {
            mAdapter.clear();
        }

        for (int i = 0; i < data.size(); i++) {
            if (compareTo(mAdapter.getData(), data.get(i))) {
                TLog.v(TAG, "remove");
                data.remove(i);
                i--;
            }
        }
        int adapterState = BaseState.STATE_EMPTY_ITEM;
        if ((mAdapter.getCount() + data.size()) == 0) {
            adapterState = BaseState.STATE_EMPTY_ITEM;
        } else if (data.size() == 0
                || (data.size() < getPageSize() && mCurrentPage == 0)) {
//            TLog.v(TAG, "state no more");
//            TLog.v(TAG, "data.size is " + data.size());
//            TLog.v(TAG, "getPageSize is " + getPageSize());
//            TLog.v(TAG, "mCurrentPage is " + mCurrentPage);
            adapterState = BaseState.STATE_NO_MORE;
            mAdapter.notifyDataSetChanged();
        } else {
            adapterState = BaseState.STATE_LOAD_MORE;
        }
        mAdapter.setState(adapterState);
        TLog.v(TAG, "adapterState is " + adapterState);
        mAdapter.addData(data);
        // 判断等于是因为最后有一项是listview的状态
        if (mAdapter.getCount() == 1) {

            if (needShowEmptyNoData()) {
                mErrorLayout.setErrorType(EmptyLayout.NODATA);
            } else {
                mAdapter.setState(BaseState.STATE_EMPTY_ITEM);
                mAdapter.notifyDataSetChanged();
            }
        }

        executeOnLoadFinish();
    }

    public void executeOnAddData(List<T> data) {
        if (data == null) {
            TLog.v(TAG, "data is null");
            data = new ArrayList<T>();
        }

        mErrorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);

        for (int i = 0; i < data.size(); i++) {
            if (compareTo(mAdapter.getData(), data.get(i))) {
                TLog.v(TAG, "remove");
                data.remove(i);
                i--;
            }
        }
        int adapterState = BaseState.STATE_NO_MORE;
        mAdapter.notifyDataSetChanged();
        mAdapter.setState(adapterState);
        mAdapter.addData(data);
        // 判断等于是因为最后有一项是listview的状态
        if (mAdapter.getCount() == 1) {

            if (needShowEmptyNoData()) {
                mErrorLayout.setErrorType(EmptyLayout.NODATA);
            } else {
                mAdapter.setState(BaseState.STATE_EMPTY_ITEM);
                mAdapter.notifyDataSetChanged();
            }
        }

        executeOnLoadFinish();
    }

    public void executeOnLoadDataError(String error) {
        if (mCurrentPage == 0
                /*&& !CacheManager.isExistDataCache(getActivity(), getCacheKey())*/) {
            mErrorLayout.setErrorType(EmptyLayout.NETWORK_ERROR);
        } else {

            //在无网络时，滚动到底部时，mCurrentPage先自加了，然而在失败时却
            //没有减回来，如果刻意在无网络的情况下上拉，可以出现漏页问题
            //find by TopJohn
            mCurrentPage--;

            mErrorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
            mAdapter.setState(BaseState.STATE_NETWORK_ERROR);
            mAdapter.notifyDataSetChanged();
        }
        executeOnLoadFinish();
    }

    // 完成刷新
    protected void executeOnLoadFinish() {
        TLog.v(TAG, "executeOnLoadFinish enter");
        setSwipeRefreshLoadedState();
        mState = STATE_NONE;
        mAdapter.setLoading(false);
    }

    public void executeOnLoadDataNoMore() {
        mAdapter.setState(STATE_NOMORE);
    }

    /**
     * 是否需要隐藏listview，显示无数据状态
     *
     * @author 火蚁 2015-1-27 下午6:18:59
     */
    protected boolean needShowEmptyNoData() {
        return true;
    }

    protected boolean compareTo(List<? extends Entity> data, Entity enity) {
        int s = data.size();
        if (enity != null) {
            for (int i = 0; i < s; i++) {
                if (enity.getEntityUuid() != null && data.get(i).getEntityUuid() != null) {
                    if (enity.getEntityUuid().equals(data.get(i).getEntityUuid())) {
                        return true;
                    }
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    protected abstract int getPageSize();

    protected void onRefreshNetworkSuccess() {
    }


    /**
     * 设置顶部正在加载的状态
     */
    protected void setSwipeRefreshLoadingState() {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(true);
            // 防止多次重复刷新
            mSwipeRefreshLayout.setEnabled(false);
        }
    }

    /**
     * 设置顶部加载完毕的状态
     */
    protected void setSwipeRefreshLoadedState() {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(false);
            mSwipeRefreshLayout.setEnabled(true);
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
//        if (mAdapter == null || mAdapter.getCount() == 0) {
//            return;
//        }
//        // 数据已经全部加载，或数据为空时，或正在加载，不处理滚动事件
//        if (mState == STATE_LOADMORE || mState == STATE_REFRESH) {
//            TLog.v(TAG, "state is loadmore or refresh(" + mState +"), just return");
//            return;
//        }
//        // 判断是否滚动到底部
//        boolean scrollEnd = false;
//        try {
//            if (view.getPositionForView(mAdapter.getFooterView()) == view
//                    .getLastVisiblePosition())
//                scrollEnd = true;
//        } catch (Exception e) {
//            scrollEnd = false;
//        }
//
//        if (mState == STATE_NONE && scrollEnd) {
//            if (mAdapter.getState() == BaseState.STATE_LOAD_MORE
//                    || mAdapter.getState() == BaseState.STATE_NETWORK_ERROR) {
//                mCurrentPage++;
//                TLog.v(TAG, "get more data, mCurrentPage is " + mCurrentPage);
//                getPresenter().setCurrentPageIndex(mCurrentPage);
//                mState = STATE_LOADMORE;
//                getPresenter().requestData(false);
//            }
//        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {

    }


    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showMsg(String message) {

    }
}
