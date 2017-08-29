package com.weijietech.framework.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.weijietech.framework.R;

/**
 * Created by eagle on 2017-04-11.
 */

public class CentralToast {
    public final static int INFO = 1;
    public final static int OK = 2;
    public final static int FAIL = 3;

    public static void show(Context context,int type, int resId ) {
        View toastRoot = LayoutInflater.from(context).inflate(R.layout.central_toast, null);

        ImageView ivToast = (ImageView) toastRoot.findViewById(R.id.iv_toast);
        switch (type) {
            case INFO:
                ivToast.setImageResource(R.drawable.toast_info);
                break;
            case OK:
                ivToast.setImageResource(R.drawable.toast_ok);
                break;
            case FAIL:
                ivToast.setImageResource(R.drawable.toast_fail);
                break;
        }

        TextView tvToast = (TextView) toastRoot.findViewById(R.id.tv_toast);
        tvToast.setText(context.getString(resId));

        Toast toast = new Toast(context);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setView(toastRoot);
        toast.show();
    }
    public static void show(Context context,int type, String message) {
        View toastRoot = LayoutInflater.from(context).inflate(R.layout.central_toast, null);

        ImageView ivToast = (ImageView) toastRoot.findViewById(R.id.iv_toast);
        switch (type) {
            case INFO:
                ivToast.setImageResource(R.drawable.toast_info);
                break;
            case OK:
                ivToast.setImageResource(R.drawable.toast_ok);
                break;
            case FAIL:
                ivToast.setImageResource(R.drawable.toast_fail);
                break;
        }

        TextView tvToast = (TextView) toastRoot.findViewById(R.id.tv_toast);
        tvToast.setText(message);

        Toast toast = new Toast(context);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setView(toastRoot);
        toast.show();
    }
}
