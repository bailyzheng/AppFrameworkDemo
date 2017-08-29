package com.weijietech.framework.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.weijietech.framework.R;
import com.weijietech.framework.utils.TLog;

import java.util.List;

public class BaseState {
    protected final static String TAG = BaseState.class.getSimpleName();
    public static final int STATE_EMPTY_ITEM = 0;
    public static final int STATE_LOAD_MORE = 1; //加载更多
    public static final int STATE_NO_MORE = 2; //已加载完毕
    public static final int STATE_NO_DATA = 3; //没有数据
    public static final int STATE_LESS_ONE_PAGE = 4;
    public static final int STATE_NETWORK_ERROR = 5;
    public static final int STATE_OTHER = 6;
    public static final int STATE_LOADING_MORE = 7; //正在加载更多

    protected int state = STATE_LESS_ONE_PAGE;

    public void setState(int state) {
        TLog.v(TAG, "setState -- " + state);
        this.state = state;
    }

    public int getState() {
        return this.state;
    }


}

