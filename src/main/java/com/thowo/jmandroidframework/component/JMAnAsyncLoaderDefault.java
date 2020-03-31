package com.thowo.jmandroidframework.component;

import com.thowo.jmjavaframework.JMAsyncListener;
import com.thowo.jmjavaframework.JMFunctions;
import com.thowo.jmjavaframework.db.JMConnection;
import com.thowo.jmjavaframework.lang.JMConstMessage;

public class JMAnAsyncLoaderDefault implements JMAsyncListener {
    private JMAnActivity activity;

    public JMAnAsyncLoaderDefault(JMAnActivity activity){
        this.activity=activity;
    }

    private String getMessage(String id, String asyncType){
        String ret="";
        String msgId= JMConstMessage.MSG_ASYNC+asyncType;
        if(id.equals(JMConnection.JM_ASYNC_CONNECT)){
            msgId+=JMConstMessage.MSG_ASYNC_STATE_CONNECT_DB;
        }else if(id.equals(JMConnection.JM_ASYNC_FETCH)){
            msgId+=JMConstMessage.MSG_ASYNC_STATE_FETCH;
        }else if(id.equals(JMConnection.JM_ASYNC_UPDATE)){
            msgId+=JMConstMessage.MSG_ASYNC_STATE_UPDATE;
        }else if(id.equals(JMConnection.JM_ASYNC_DELETE)){
            msgId+=JMConstMessage.MSG_ASYNC_STATE_DELETE;
        }
        //ret=JMMessage.getMessage(msgId);
        ret= JMFunctions.getMessege(msgId);
        return ret;
    }

    @Override
    public void onJMStart(String id) {
        this.activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //this.loadingSprite.setText(this.getMessage(id, JMConstMessage.MSG_ASYNC_STARTED));
                JMAnAsyncLoaderDefault.this.activity.getLoadingSprite().showLoading(JMAnAsyncLoaderDefault.this.getMessage(id, JMConstMessage.MSG_ASYNC_STARTED));
            }
        });
    }

    @Override
    public void onJMProcess(String id) {
        this.activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                JMAnAsyncLoaderDefault.this.activity.getLoadingSprite().setText(JMAnAsyncLoaderDefault.this.getMessage(id, JMConstMessage.MSG_ASYNC_PROCESSING));
            }
        });
    }

    @Override
    public void onJMComplete(Object result, String id) {
        this.activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                JMAnAsyncLoaderDefault.this.activity.getLoadingSprite().setText(JMAnAsyncLoaderDefault.this.getMessage(id, JMConstMessage.MSG_ASYNC_COMPLETED));
                JMAnAsyncLoaderDefault.this.activity.getLoadingSprite().hideLoading();
            }
        });
    }

    @Override
    public void onJMError(String errorMessage, String id) {
        this.activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                JMAnAsyncLoaderDefault.this.activity.getLoadingSprite().setText(JMAnAsyncLoaderDefault.this.getMessage(id, JMConstMessage.MSG_ASYNC_ERROR));
                JMFunctions.traceAndShow(errorMessage);
                JMAnAsyncLoaderDefault.this.activity.getLoadingSprite().hideLoading();
            }
        });
    }
}
