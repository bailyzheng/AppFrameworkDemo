package com.weijietech.framework.RetrofitException;

import com.google.gson.JsonParseException;
import com.weijietech.framework.utils.TLog;

import org.json.JSONException;

import java.net.ConnectException;
import java.text.ParseException;

import retrofit2.HttpException;


/**
 * Created by 12262 on 2016/5/30.
 */
public class ExceptionEngine {
    private final static String TAG = ExceptionEngine.class.getSimpleName();

    //对应HTTP的状态码
    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;

    public static ApiException handleException(Throwable e){
        ApiException ex;
        if (e instanceof HttpException){             //HTTP错误
            HttpException httpException = (HttpException) e;
            ex = new ApiException(e, ERROR.HTTP_ERROR);
            switch(httpException.code()){
                case UNAUTHORIZED:
                case FORBIDDEN:
                case NOT_FOUND:
                case REQUEST_TIMEOUT:
                case GATEWAY_TIMEOUT:
                case INTERNAL_SERVER_ERROR:
                case BAD_GATEWAY:
                case SERVICE_UNAVAILABLE:
                default:
                    ex.setDisplayMessage("网络错误");  //均视为网络错误
                    break;
            }
            return ex;
        } else if (e instanceof ServerException){    //服务器返回的错误
            TLog.v(TAG, "Error from server");
            ServerException resultException = (ServerException) e;
            ex = new ApiException(resultException, resultException.getCode());
            String showMsg = ERROR.getInstance().getErrMsgByKey(resultException.getMsg());
            int myErrCode = ERROR.getInstance().getErrCodeByKey(resultException.getMsg());
            TLog.v(TAG, "Error code is " + myErrCode);
            if (showMsg != null) {
                ex.setDisplayMessage(showMsg);
                ex.setMyErrorCode(myErrCode);
            } else {
                TLog.w(TAG, "Cannot find error string, key is " + resultException.getMsg());
                ex.setDisplayMessage(resultException.getMsg());
                ex.setMyErrorCode(myErrCode);
            }
            return ex;
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException){
            ex = new ApiException(e, ERROR.PARSE_ERROR);
            ex.setDisplayMessage("解析错误");            //均视为解析错误
            return ex;
        }else if(e instanceof ConnectException){
            ex = new ApiException(e, ERROR.NETWORD_ERROR);
            ex.setDisplayMessage("连接失败，请检查您的网络连接");  //均视为网络错误
            return ex;
        }else {
            ex = new ApiException(e, ERROR.UNKNOWN);
            ex.setDisplayMessage("未知错误，请检查您的网络连接");          //未知错误
            return ex;
        }
    }
}
