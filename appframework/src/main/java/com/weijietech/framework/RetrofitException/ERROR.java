package com.weijietech.framework.RetrofitException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 12262 on 2016/5/31.
 * 与服务器约定好的异常
 */
public class ERROR {
    /**
     * 未知错误
     */
    public static final int UNKNOWN = 1000;
    /**
     * 解析错误
     */
    public static final int PARSE_ERROR = 1001;
    /**
     * 网络错误
     */
    public static final int NETWORD_ERROR = 1002;
    /**
     * 协议出错
     */
    public static final int HTTP_ERROR = 1003;


    private ERRORInfoInterface errorInfoProvider;

    private static ERROR ourInstance = new ERROR();

    public static ERROR getInstance() {
        return ourInstance;
    }

    private ERROR() {
    }

    public void setErrorInfoProvider(ERRORInfoInterface errorInfoProvider) {
        this.errorInfoProvider = errorInfoProvider;
    }

    public String getErrMsgByKey(String key) {
        if (errorInfoProvider != null) {
            return errorInfoProvider.getErrMsgByKey(key);
        } else {
            return null;
        }
    }

    public int getErrCodeByKey(String key) {
        if (errorInfoProvider != null) {
            return errorInfoProvider.getErrCodeByKey(key);
        } else {
            return -1;
        }
    }
}
