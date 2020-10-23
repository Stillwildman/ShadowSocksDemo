package com.vincent.shadowsocksdemo.utilities

import android.util.Log

/**
 * Created by Vincent on 2020/2/4.
 */
object LogUtil {

    fun i(tag: String, message: String) {
        if (DebugHelper.IS_DEBUG_ON) {
            Log.i(tag, message)
        }
    }

    fun i(tag: String, message: String, tr: Throwable) {
        if (DebugHelper.IS_DEBUG_ON) {
            Log.i(tag, message, tr)
        }
    }

    fun d(tag: String, message: String) {
        if (DebugHelper.IS_DEBUG_ON) {
            Log.d(tag, message)
        }
    }

    fun d(tag: String, message: String, tr: Throwable) {
        if (DebugHelper.IS_DEBUG_ON) {
            Log.d(tag, message, tr)
        }
    }

    fun e(tag: String, message: String) {
        if (DebugHelper.IS_DEBUG_ON) {
            Log.e(tag, message)
        }
    }

    fun e(tag: String, message: String, tr: Throwable) {
        if (DebugHelper.IS_DEBUG_ON) {
            Log.e(tag, message, tr)
        }
    }

    fun w(tag: String, message: String) {
        if (DebugHelper.IS_DEBUG_ON) {
            Log.w(tag, message)
        }
    }

    fun w(tag: String, message: String, tr: Throwable) {
        if (DebugHelper.IS_DEBUG_ON) {
            Log.w(tag, message, tr)
        }
    }

}