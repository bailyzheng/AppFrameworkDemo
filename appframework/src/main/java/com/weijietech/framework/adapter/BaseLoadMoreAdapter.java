package com.weijietech.framework.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.weijietech.framework.R;
import com.weijietech.framework.utils.TLog;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by RaphetS on 2016/10/1.
 *  支持上拉加载
 *  底部有进度条
 */

public abstract class BaseLoadMoreAdapter<T> extends RecyclerView.Adapter {
    protected final static String TAG = BaseLoadMoreAdapter.class.getSimpleName();
    protected Context mContext;
    private BaseState state = new BaseState();
    private OnLoadMoreListener mOnLoadMoreListener;
    private OnItemClickListener mItemClickListener;
    private onLongItemClickListener mLongItemClickListener;
    protected List<T> mDatas;

    private Map<Integer, Integer> layoutMap;

    protected final static int TYPE_INVALID=100;
    protected final static int TYPE_ITEM=101;
    protected final static int TYPE_PROGRESS=102;
    protected final static int TYPE_PROGRESS_NO_MORE=103;

    private LinearLayout mFooterView;

    protected int _loadmoreText;
    protected int _loadFinishText;
    protected int _noDateText;

    public BaseLoadMoreAdapter(Context mContext, RecyclerView recyclerView) {
        this.mContext = mContext;
        mDatas = new ArrayList<T>();

        _loadmoreText = R.string.loading;
        _loadFinishText = R.string.loading_no_more;
        _noDateText = R.string.error_view_no_data;

        init(recyclerView);
        layoutMap = getLayoutMap();
    }
    private void init(RecyclerView recyclerView) {
        //mRecyclerView添加滑动事件监听
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int    totalItemCount = linearLayoutManager.getItemCount();
                int    lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
                if (!isLoading() &&dy>0&&lastVisibleItemPosition>=totalItemCount-1) {
                    //此时是刷新状态
                    if (mOnLoadMoreListener != null) {
                        mOnLoadMoreListener.onLoadMore();
                    }
                }
            }
        });
    }
    public void updateData(List<T> data) {
        mDatas.clear();
        mDatas.addAll(data);
        notifyDataSetChanged();
    }

    public abstract Map<Integer, Integer> getLayoutMap();

    public void addAll(List<T> data) {
        mDatas.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View retView;
        if (viewType==TYPE_ITEM){
            return onCreateItemViewHolder(parent, viewType);
        }else if(viewType==TYPE_PROGRESS){
            retView = LayoutInflater.from(mContext).inflate(R.layout.progress_item, parent, false);
            ProgressViewHolder progressViewHolder = new ProgressViewHolder(retView);
            return progressViewHolder;
        }else if (viewType==TYPE_PROGRESS_NO_MORE){
            retView = LayoutInflater.from(mContext).inflate(R.layout.progress_item_no_more, parent, false);
            ProgressViewHolder progressViewHolder = new ProgressViewHolder(retView);
            return progressViewHolder;
        } else {
            return onCreateItemViewHolder(parent, viewType);
        }
    }

    protected RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        View retView;
        retView= LayoutInflater.from(mContext).inflate(layoutMap.get(viewType),parent,false);
        BaseViewHolder baseViewHolder=new BaseViewHolder(retView);
        return baseViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
      if (holder instanceof BaseViewHolder){
          convert(mContext, holder, mDatas.get(position));
          if (mItemClickListener != null) {
              ((BaseViewHolder) holder).mItemView.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      mItemClickListener.onItemClick(v, position);
                  }
              });
          }
          if (mLongItemClickListener != null) {
              ((BaseViewHolder) holder).mItemView.setOnLongClickListener(new View.OnLongClickListener() {
                  @Override
                  public boolean onLongClick(View v) {
                      mLongItemClickListener.onLongItemClick(v, position);
                      return true;
                  }
              });
          }
      }
    }

    @Override
    public int getItemViewType(int position) {
//        TLog.v(TAG, "getItemViewType --- getState is " + getState() + " " + position);
        if (position==getItemCount()-1){
            if (getState() == BaseState.STATE_LOADING_MORE) {
                TLog.v(TAG, "getItemViewType --- return TYPE_PROGRESS");
                return TYPE_PROGRESS;
            } else if (getState() == BaseState.STATE_NO_MORE) {
                TLog.v(TAG, "getItemViewType --- return TYPE_PROGRESS_NO_MORE");
                return TYPE_PROGRESS_NO_MORE;
            } else {
                TLog.v(TAG, "getItemViewType --- return default TYPE_PROGRESS");
                return TYPE_PROGRESS;
            }
        }else {
            return getItemRealViewType(position);
        }
    }

    protected int getItemRealViewType(int position) {
        return TYPE_ITEM;
    }

    public abstract void convert(Context mContext, RecyclerView.ViewHolder holder, T t);

    @Override
    public int getItemCount() {
        return mDatas.size()+1;
    }
    public void setLoading(boolean b){
        if (b) {
            setState(BaseState.STATE_LOADING_MORE);
        } else {
            if (getState() == BaseState.STATE_LOADING_MORE) {
                setState(BaseState.STATE_LOAD_MORE);
            }
        }
    }

    private boolean isLoading() {
        return getState() == BaseState.STATE_LOADING_MORE ? true : false;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface onLongItemClickListener {
        void onLongItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    public void setOnLongItemClickListener(onLongItemClickListener listener) {
        this.mLongItemClickListener = listener;
    }
    public void setOnLoadMoreListener(OnLoadMoreListener listener){
        this.mOnLoadMoreListener=listener;
    }

    public interface OnLoadMoreListener{
        void onLoadMore();
    }
    public class ProgressViewHolder extends RecyclerView.ViewHolder{
        public ProgressViewHolder(View itemView) {
            super(itemView);
        }
    }

    public void clear() {
        mDatas.clear();
        notifyDataSetChanged();
    }

    public List<T> getData() {
        return mDatas == null ? (mDatas = new ArrayList<T>()) : mDatas;
    }

    public T getItem(int position) {
        return mDatas == null ? null : mDatas.get(position);
    }

//    @Override
    public int getCount() {
        switch (getState()) {
            case BaseState.STATE_EMPTY_ITEM:
                return getDataSizePlus1();
            case BaseState.STATE_NETWORK_ERROR:
            case BaseState.STATE_LOAD_MORE:
                return getDataSizePlus1();
            case BaseState.STATE_NO_DATA:
                return 1;
            case BaseState.STATE_NO_MORE:
                return getDataSizePlus1();
            case BaseState.STATE_LESS_ONE_PAGE:
                return getDataSize();
            default:
                break;
        }
        return getDataSize();
    }

    public int getDataSize() {
        return mDatas.size();
    }

    public int getState() {
        return this.state.getState();
    }

    public void setState(int state) {
        this.state.setState(state);
    }

    public void addData(List<T> data) {
        if (mDatas != null && data != null && !data.isEmpty()) {
            mDatas.addAll(data);
        }
        notifyDataSetChanged();
    }

    public int getDataSizePlus1() {
        if (hasFooterView()) {
            return getDataSize() + 1;
        }
        return getDataSize();
    }

    protected boolean hasFooterView() {
        return true;
    }
}
