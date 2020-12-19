package com.thowo.jmandroidframework;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.ContextThemeWrapper;
import android.widget.Toast;

import com.thowo.jmandroidframework.component.JMAnActivity;
import com.thowo.jmandroidframework.component.JMAnCodeScanner;
import com.thowo.jmjavaframework.JMFunctions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class JMAnFunctions {
    private static Context current;

    public static void update(Context context){
        JMAnFunctions.current=context;
    }

    public static void showMessage(final String msg){
        JMAnActivity tmp=(JMAnActivity) JMAnFunctions.current;
        tmp.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new androidx.appcompat.app.AlertDialog.Builder(new ContextThemeWrapper(current,R.style.Theme_AppCompat_Dialog_Alert))
                .setTitle(R.string.app_name)
                .setMessage(msg).show();
            }
        });

    }

    public static void init(String localeId){
        JMFunctions.init(copyResourceToCacheDir(R.raw.jmlanguagepack,"jmlanguagepack.xls",false),current.getExternalCacheDir().getAbsolutePath(),current.getExternalFilesDir(null).getAbsolutePath(),localeId);
    }

    public static File copyResourceToCacheDir(int resId, String newName, boolean replace){
        File ret=null;
        String destFileName=current.getExternalCacheDir().getAbsolutePath()+"/"+newName;
        JMFunctions.trace(destFileName);
        ret=new File(destFileName);

        boolean copy=replace || !JMFunctions.fileExist(ret);
        if(copy){
            if(!JMFunctions.fileExist(ret))JMFunctions.createFile(ret);
            InputStream tmp=current.getApplicationContext().getResources().openRawResource(resId);
            try {
                OutputStream outputStream=new FileOutputStream(ret);
                int length;
                byte[] bytes=new byte[1024];
                while ((length=tmp.read(bytes))!=-1){
                    outputStream.write(bytes,0,length);
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                JMFunctions.trace(e.getMessage());
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                JMFunctions.trace(e.getMessage());
                return null;
            }
        }
        return ret;
    }

    public static void startNewActivity(JMAnActivity currentActivity, Class<?> activityClass, int requestCode){
        Intent intent=new Intent(currentActivity,activityClass);
        currentActivity.startActivityForResult(intent,requestCode);
    }
    public static void startNewActivity(JMAnActivity currentActivity, Class<?> activityClass, int requestCode, Intent intent){
        currentActivity.startActivityForResult(intent,requestCode);
    }
    public static void startNewActivity(JMAnActivity currentActivity, Class<?> activityClass, Intent intent){
        currentActivity.startActivityForResult(intent,JMAnConst.REQUESTCODE_DEFAULT);
    }
    public static void startNewActivity(JMAnActivity currentActivity, Class<?> activityClass){
        Intent intent=new Intent(currentActivity,activityClass);
        currentActivity.startActivityForResult(intent,JMAnConst.REQUESTCODE_DEFAULT);
    }

    public static void scanCode(JMAnActivity currentActivity){
        startNewActivity(currentActivity,JMAnCodeScanner.class,JMAnConst.REQUESTCODE_SCANCODE);
    }
}
