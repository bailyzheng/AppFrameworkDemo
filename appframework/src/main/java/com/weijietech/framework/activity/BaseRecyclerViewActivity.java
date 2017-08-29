package com.weijietech.framework.activity;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.loopj.android.http.TextHttpResponseHandler;
import com.weijietech.framework.R;
import com.weijietech.framework.adapter.BaseGeneralRecyclerAdapter;
import com.weijietech.framework.adapter.BaseRecyclerAdapter;
import com.weijietech.framework.beans.PageBean;
import com.weijietech.framework.beans.ResultBean;
import com.weijietech.framework.widget.RecyclerRefreshLayout;

import java.lang.reflect.Type;
import java.util.Date;

import cz.msebera.android.httpclient.Header;

/**
 * Created by huanghaibin_dev
 * on 16-6-23.
 */
public abstract class BaseRecyclerViewActivity<T> extends BaseBackActivity implements
        BaseRecyclerAdapter.OnItemClickListener,
        RecyclerRefreshLayout.SuperRefreshLayoutListener,
        BaseGeneralRecyclerAdapter.Callback {

    protected RecyclerRefreshLayout mRefreshLayout;

    protected RecyclerView mRecyclerView;

    protected BaseRecyclerAdapter<T> mAdapter;

    protected TextHttpResponseHandler mHandler;

    protected PageBean<T> mBean;

    protected boolean mIsRefresh;

    @Override
    protected int getContentView() {
        return R.layout.activity_base_recycler;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        mAdapter = mAdapter == null ? getRecyclerAdapter() : mAdapter;
        mRefreshLayout = (RecyclerRefreshLayout) findViewById(R.id.new_refreshLayout);
        mRecyclerView = (RecyclerView) findViewById(R.id.new_recyclerView);
        mRecyclerView.setLayoutManager(getLayoutManager());
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
        mRefreshLayout.setSuperRefreshLayoutListener(this);
        mRefreshLayout.setColorSchemeResources(
                R.color.swiperefresh_color1, R.color.swiperefresh_color2,
                R.color.swiperefresh_color3, R.color.swiperefresh_color4);
    }

    @Override
    protected void initData() {
        super.initData();
        mBean = new PageBean<>();
        mHandler = new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                onLoadingFailure();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                try {
//                    ResultBean<PageBean<T>> resultBean = AppOperator.createGson().fromJson(responseString, getType());
                    ResultBean<PageBean<T>> resultBean = new ResultBean<>();
                    if (resultBean != null && resultBean.isSuccess() && resultBean.getResult().getItems() != null) {
                        onLoadingSuccess();
                        setListData(resultBean);
                    } else {
                        onLoadingFailure();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    onFailure(statusCode, headers, responseString, e);
                }
            }

            @Override
            public void onStart() {
                super.onStart();
                onLoadingStart();
            }

            @Override
            public void onFinish() {
                super.onFinish();
                onLoadingFinish();
            }
        };

        mRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(true);
                onRefreshing();
            }
        });
    }

    @Override
    public void onItemClick(int position, long itemId) {
        onItemClick(mAdapter.getItem(position), position);
    }

    @Override
    public void onRefreshing() {
        mIsRefresh = true;
        requestData();
    }

    @Override
    public void onLoadMore() {
        requestData();
    }

    protected void onItemClick(T item, int position) {

    }

    protected void requestData() {

    }

    protected void setListData(ResultBean<PageBean<T>> resultBean) {
        mBean.setNextPageToken(resultBean.getResult().getNextPageToken());
        mBean.setPrevPageToken(resultBean.getResult().getPrevPageToken());
        if (mIsRefresh) {
            mBean.setItems(resultBean.getResult().getItems());
            mAdapter.clear();
            mAdapter.addAll(mBean.getItems());
            mBean.setPrevPageToken(resultBean.getResult().getPrevPageToken());
            mRefreshLayout.setCanLoadMore(true);
        } else {
            mAdapter.addAll(resultBean.getResult().getItems());
        }
        mAdapter.setState(resultBean.getResult().getItems() == null || resultBean.getResult().getItems().size() < 20 ? BaseRecyclerAdapter.STATE_NO_MORE : BaseRecyclerAdapter.STATE_LOADING, true);
    }

    protected void onLoadingStart() {

    }

    protected void onLoadingSuccess() {

    }

    protected void onLoadingFinish() {
        mRefreshLayout.onComplete();
        mIsRefresh = false;
    }

    protected void onLoadingFailure() {
        if (mAdapter.getItems().size() == 0) {
            mAdapter.setState(BaseRecyclerAdapter.STATE_LOAD_ERROR, true);
        } else {
            mAdapter.setState(BaseRecyclerAdapter.STATE_NO_MORE, true);
        }
    }


    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(this);
    }

    protected abstract Type getType();

    protected abstract BaseRecyclerAdapter<T> getRecyclerAdapter();

//    @Override
//    public RequestManager getImgLoader() {
//        return getImageLoader();
//    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public Date getSystemTime() {
        return new Date();
    }
}
