package com.thowo.jmandroidframework.component;

import android.util.Log;

import com.thowo.jmandroidframework.JMAnFunctions;
import com.thowo.jmjavaframework.JMUIListener;

import static android.content.ContentValues.TAG;

public class JMAnUIMessenger implements JMUIListener {

    @Override
    public void trace(String message) {
        Log.d(TAG, "jmtrace: "+message);
    }

    @Override
    public void messageBox(String message) {
        Log.d(TAG, "jmtrace: "+message);
        JMAnFunctions.showMessage(message);
    }

    @Override
    public void errorBox(String message) {

    }

    @Override
    public int confirmBoxYN(String title, String message, String yes, String no, boolean defaultNo) {
        return 0;
    }
}
