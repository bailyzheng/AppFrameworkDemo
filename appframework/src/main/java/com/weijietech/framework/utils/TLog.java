package com.weijietech.framework.utils;

import android.util.Log;

public class TLog {
	public static final String LOG_TAG = "SIMICO";
	public static boolean DEBUG = true;

	public TLog() {
	}

	public static final void analytics(String log) {
		if (DEBUG)
			Log.d(LOG_TAG, log);
	}

	public static final void error(String log) {
		if (DEBUG)
			Log.e(LOG_TAG, "" + log);
	}

	public static final void log(String log) {
		if (DEBUG)
			Log.i(LOG_TAG, log);
	}

	public static final void log(String tag, String log) {
		if (DEBUG)
			Log.i(tag, log);
	}

	public static final void logv(String log) {
		if (DEBUG)
			Log.v(LOG_TAG, log);
	}

	public static final void warn(String log) {
		if (DEBUG)
			Log.w(LOG_TAG, log);
	}

	public static final void i(String TAG, String log) {
		if (DEBUG) {
			Log.i(TAG, log);
		}
	}

	public static final void v(String TAG, String log) {
		if (DEBUG) {
			Log.v(TAG, log);
		}
	}

	public static final void d(String TAG, String log) {
		if (DEBUG) {
			Log.d(TAG, log);
		}
	}

	public static final void e(String TAG, String log) {
		if (DEBUG) {
			Log.e(TAG, log);
		}
	}
	public static final void w(String TAG, String log) {
		if (DEBUG) {
			Log.w(TAG, log);
		}
	}
}
