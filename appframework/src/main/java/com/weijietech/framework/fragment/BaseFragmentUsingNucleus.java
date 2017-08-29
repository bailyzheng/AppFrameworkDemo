//package com.weijietech.framework.fragment;
//
//import android.app.ProgressDialog;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.app.FragmentActivity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.weijietech.framework.BaseApplication;
//import com.weijietech.framework.DialogControl;
//import com.weijietech.framework.R;
//
//import butterknife.ButterKnife;
//import nucleus.presenter.Presenter;
//import nucleus.view.NucleusSupportFragment;
//
///**
// * 碎片基类
// *
// * @author FireAnt（http://my.oschina.net/LittleDY）
// * @created 2014年9月25日 上午11:18:46
// *
// */
//public abstract class BaseFragmentUsingNucleus<PresenterType extends Presenter> extends NucleusSupportFragment<PresenterType> implements
//        View.OnClickListener, BaseFragmentInterface {
//    public static final int STATE_NONE = 0;
//    public static final int STATE_REFRESH = 1;
//    public static final int STATE_LOADMORE = 2;
//    public static final int STATE_NOMORE = 3;
//    public static final int STATE_PRESSNONE = 4;// 正在下拉但还没有到刷新的状态
//    public static int mState = STATE_NONE;
//
//    protected LayoutInflater mInflater;
//    protected View mRoot;
//    protected Bundle mBundle;
//
//    public BaseApplication getApplication() {
//        return (BaseApplication) getActivity().getApplication();
//    }
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        mBundle = getArguments();
//        initBundle(mBundle);
//    }
//
//    protected void initBundle(Bundle bundle) {
//
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        mInflater = inflater;
//        if (mRoot != null) {
//            ViewGroup parent = (ViewGroup) mRoot.getParent();
//            if (parent != null)
//                parent.removeView(mRoot);
//        } else {
//            mRoot = inflater.inflate(getLayoutId(), container, false);
//            // Do something
//            onBindViewBefore(mRoot);
//            // Bind view
//            ButterKnife.bind(this, mRoot);
//            // Get savedInstanceState
//            if (savedInstanceState != null)
//                onRestartInstance(savedInstanceState);
//            // Init
//            initWidget(mRoot);
//            initData();
//        }
//        return mRoot;
//    }
//
//    protected abstract int getLayoutId();
//
//    protected void onBindViewBefore(View root) {
//        // ...
//    }
//
//    protected void onRestartInstance(Bundle bundle) {
//
//    }
//
//    protected void initWidget(View root) {
//
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        ButterKnife.unbind(this);
////        RequestManager manager = mImgLoader;
////        if (manager != null)
////            manager.onDestroy();
//        mBundle = null;
//    }
//    protected View inflateView(int resId) {
//        return this.mInflater.inflate(resId, null);
//    }
//
//    public boolean onBackPressed() {
//        return false;
//    }
//
//    protected void hideWaitDialog() {
//        FragmentActivity activity = getActivity();
//        if (activity instanceof DialogControl) {
//            ((DialogControl) activity).hideWaitDialog();
//        }
//    }
//
//    protected ProgressDialog showWaitDialog() {
//        return showWaitDialog(R.string.loading);
//    }
//
//    protected ProgressDialog showWaitDialog(int resid) {
//        FragmentActivity activity = getActivity();
//        if (activity instanceof DialogControl) {
//            return ((DialogControl) activity).showWaitDialog(resid);
//        }
//        return null;
//    }
//
//    protected ProgressDialog showWaitDialog(String str) {
//        FragmentActivity activity = getActivity();
//        if (activity instanceof DialogControl) {
//            return ((DialogControl) activity).showWaitDialog(str);
//        }
//        return null;
//    }
//
//    @Override
//    public void initView(View view) {
//
//    }
//
//    @Override
//    public void initData() {
//
//    }
//
//    @Override
//    public void onClick(View v) {
//
//    }
//}
