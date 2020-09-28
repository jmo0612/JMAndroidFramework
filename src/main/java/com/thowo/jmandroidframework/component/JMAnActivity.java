package com.thowo.jmandroidframework.component;

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.thowo.jmandroidframework.JMAnFunctions;
import com.thowo.jmandroidframework.R;
import com.thowo.jmjavaframework.JMFunctions;
import com.thowo.jmjavaframework.lang.JMConstMessage;


/**
 * Created by jimi on 8/16/2017.
 */

public class JMAnActivity extends AppCompatActivity{
    private LinearLayout mContent;
    private JMAnLoadingSprite mLoading;
    private String title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        JMAnFunctions.update(this);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setContentView(int layoutResID){
        super.setContentView(R.layout.activity_jm);
        mLoading=(JMAnLoadingSprite) findViewById(R.id.jm_loading);
        mContent=(LinearLayout) findViewById(R.id.jm_content);
        LayoutInflater inflater = LayoutInflater.from(getBaseContext());
        inflater.inflate(layoutResID,mContent);
    }




    public void confirmExit(String msg){
        confirmExit(JMFunctions.getMessege(JMConstMessage.MSG_UI+JMConstMessage.MSG_UI_CONFIRM), msg, ContextCompat.getDrawable(this, R.drawable.icon_question), JMFunctions.getMessege(JMConstMessage.MSG_UI+JMConstMessage.MSG_UI_YES), JMFunctions.getMessege(JMConstMessage.MSG_UI+JMConstMessage.MSG_UI_NO), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which==AlertDialog.BUTTON_POSITIVE)finishIt();
            }
        });
    }

    public void confirmExit(final String title, final String msg, final Drawable resIcon, final String positive, final String negative, DialogInterface.OnClickListener onClickListener){
        new androidx.appcompat.app.AlertDialog.Builder(new ContextThemeWrapper(this,R.style.Theme_AppCompat_Dialog_Alert))
                .setTitle(title)
                .setMessage(msg)
                .setIcon(resIcon)
                .setPositiveButton(positive, onClickListener)
                .setNegativeButton(negative, onClickListener).show();
    }

    @Override
    public void onResume(){
        super.onResume();
        JMAnFunctions.update(this);
    }


    public void finishIt(){
        super.finish();
    }


    public JMAnLoadingSprite getLoadingSprite(){
        return mLoading;
    }


}
