package com.thowo.jmandroidframework;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.view.ContextThemeWrapper;

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
        new androidx.appcompat.app.AlertDialog.Builder(new ContextThemeWrapper(current,R.style.Theme_AppCompat_Dialog_Alert))
                .setTitle(R.string.app_name)
                .setMessage(msg).show();
    }

    public static void init(File languageExcelFile){
        JMFunctions.init(languageExcelFile);
    }

    public static File copyResourceToFileDir(int resId, String newName, boolean replace){
        File ret=null;
        String destFileName=current.getFilesDir().getAbsolutePath()+newName;
        ret=new File(destFileName);

        boolean copy=replace || !JMFunctions.fileExist(ret);
        if(copy){
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
}
