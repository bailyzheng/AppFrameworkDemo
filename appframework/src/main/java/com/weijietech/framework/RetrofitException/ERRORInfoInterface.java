package com.weijietech.framework.RetrofitException;

/**
 * Created by eagle on 2016-12-10.
 */

public interface ERRORInfoInterface {
    String getErrMsgByKey(String key);
    String getErrMsgByKey(int key);
    int getErrCodeByKey(String key);
}
