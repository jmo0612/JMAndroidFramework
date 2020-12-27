package com.thowo.jmandroidframework.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;

import com.thowo.jmandroidframework.JMAnFunctions;
import com.thowo.jmandroidframework.R;
import com.thowo.jmjavaframework.JMDataContainer;
import com.thowo.jmjavaframework.JMFunctions;
import com.thowo.jmjavaframework.JMInputInterface;
import com.thowo.jmjavaframework.JMVec2;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class JMAnFrame extends LinearLayoutCompat{


    private final int longTouchDuration=5;

    private int rFrameBack;
    private int rFrameBackPressed;
    private int rFrameBackDisabled;
    private int rFrameFront;
    private int rFrameFrontPressed;
    private int rFrameFrontDisabled;


    private ImageView ivBack=null;
    private ImageView ivFront=null;

    private Runnable onClickRunnable;
    private Runnable onLongClickRunnable;
    private OnTouchListener onTouchListener;

    private int touchCounter=0;
    private ScheduledExecutorService touchUpdater;

    private boolean isToggle=false;
    private boolean selected=false;

    protected LinearLayout getRoot(){
        return (LinearLayout)findViewById(R.id.frameContent);
    }

    private int getDrawableRes(Context context, String name) {
        String packageName = context.getPackageName();
        return context.getResources().getIdentifier(name, "drawable", packageName);
    }

    public JMAnFrame(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.jm_frame,this);

        this.rFrameFront=R.drawable.glossy_button;
        this.rFrameFrontPressed=R.drawable.glossy_button_clicked;
        this.rFrameFrontDisabled=R.drawable.glossy_button_locked;
        this.refreshFrame();

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.JMAnFrame);
        int count = typedArray.getIndexCount();
        try{

            for (int i = 0; i < count; ++i) {

                int attr = typedArray.getIndex(i);
                // the attr corresponds to the title attribute
                if(attr == R.styleable.JMAnFrame_frameBack) {
                    this.rFrameBack=typedArray.getResourceId(attr,-1);
                    this.refreshFrame();
                }else if(attr == R.styleable.JMAnFrame_frameBackPressed) {
                    this.rFrameBackPressed=typedArray.getResourceId(attr,-1);
                    this.refreshFrame();
                }else if(attr == R.styleable.JMAnFrame_frameBackDisabled) {
                    this.rFrameBackDisabled=typedArray.getResourceId(attr,-1);
                    this.refreshFrame();
                }else if(attr == R.styleable.JMAnFrame_frameFront) {
                    this.rFrameFront=typedArray.getResourceId(attr,0);
                    this.refreshFrame();
                }else if(attr == R.styleable.JMAnFrame_frameFrontPressed) {
                    this.rFrameFrontPressed=typedArray.getResourceId(attr,-1);
                    this.refreshFrame();
                }else if(attr == R.styleable.JMAnFrame_frameFrontDisabled) {
                    this.rFrameFrontDisabled=typedArray.getResourceId(attr,-1);
                    this.refreshFrame();
                }else if(attr == R.styleable.JMAnFrame_isToggle) {
                    this.isToggle=typedArray.getBoolean(attr,false);
                    this.refreshFrame();
                }else if(attr == R.styleable.JMAnFrame_selected) {
                    boolean tmp=typedArray.getBoolean(attr,false);
                    if(tmp){
                        this.select();
                    }else{
                        this.unselect();
                    }
                    this.refreshFrame();
                }

            }
        }finally {
            // for reuse
            typedArray.recycle();
        }

    }

    @Override
    public void setEnabled(boolean enabled){
        super.setEnabled(enabled);
        if(!enabled){
            this.setMyOnClickListener(null);
            this.setMyOnLongClickListener(null);
        }else{
            this.setMyOnClickListener(this.onClickRunnable);
            this.setMyOnLongClickListener(this.onLongClickRunnable);
        }
    }

    private void resetTouchEvent(){
        this.onTouchListener=new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(JMAnFrame.this.isEnabled()){
                    if(event.getAction()==MotionEvent.ACTION_DOWN){
                        if(!JMAnFrame.this.isToggle){
                            JMAnFrame.this.setIvBGFromRId(JMAnFrame.this.ivBack,JMAnFrame.this.rFrameBackPressed);
                            JMAnFrame.this.setIvBGFromRId(JMAnFrame.this.ivFront,JMAnFrame.this.rFrameFrontPressed);
                        }
                        if(JMAnFrame.this.touchUpdater==null){
                            JMAnFrame.this.touchUpdater=Executors.newSingleThreadScheduledExecutor();
                            JMAnFrame.this.touchUpdater.scheduleAtFixedRate(new Runnable() {
                                @Override
                                public void run() {
                                    if(JMAnFrame.this.touchCounter<=JMAnFrame.this.longTouchDuration){
                                        if(JMAnFrame.this.touchCounter<=5)JMAnFrame.this.touchCounter++;
                                    }else{
                                        if(JMAnFrame.this.onLongClickRunnable!=null)JMAnFrame.this.onLongClickRunnable.run();
                                        JMAnFrame.this.touchUpdater.shutdownNow();
                                    }
                                }
                            },200,200,TimeUnit.MILLISECONDS);
                        }
                    }else if(event.getAction()==MotionEvent.ACTION_UP){
                        if(!JMAnFrame.this.isToggle){
                            JMAnFrame.this.setIvBGFromRId(JMAnFrame.this.ivBack,JMAnFrame.this.rFrameBack);
                            JMAnFrame.this.setIvBGFromRId(JMAnFrame.this.ivFront,JMAnFrame.this.rFrameFront);
                        }else{
                            if(JMAnFrame.this.touchCounter<=JMAnFrame.this.longTouchDuration)JMAnFrame.this.setMySelected(!JMAnFrame.this.selected);
                        }
                        if(JMAnFrame.this.touchCounter<=JMAnFrame.this.longTouchDuration){
                            if(JMAnFrame.this.onClickRunnable!=null)JMAnFrame.this.onClickRunnable.run();
                        }
                        if(JMAnFrame.this.touchUpdater!=null)JMAnFrame.this.touchUpdater.shutdownNow();
                        JMAnFrame.this.touchUpdater=null;
                        JMAnFrame.this.touchCounter=0;
                    }
                }

                return true;
            }
        };
        this.setOnTouchListener(this.onTouchListener);
    }

    private void setMySelected(boolean selected){
        this.selected=selected;
        if(selected){
            this.setIvBGFromRId(this.ivBack,this.rFrameBackPressed);
            this.setIvBGFromRId(this.ivFront,this.rFrameFrontPressed);
        }else{
            this.setIvBGFromRId(this.ivBack,this.rFrameBack);
            this.setIvBGFromRId(this.ivFront,this.rFrameFront);
        }

    }

    public void select(){
        if(!this.isEnabled())return;
        this.isToggle=true;
        this.setMySelected(true);
    }
    public void unselect(){
        if(!this.isEnabled())return;
        this.isToggle=true;
        this.setMySelected(false);
    }

    private void setIvBGFromRId(ImageView iv,int rId){
        if(iv==null || rId==-1)return;
        iv.setBackgroundResource(rId);
    }

    private void refreshFrame(){
        this.ivBack=findViewById(R.id.bcvFrameBack);
        this.ivFront=findViewById(R.id.bcvFrameFront);

        this.setIvBGFromRId(this.ivBack,this.rFrameBack);
        this.setIvBGFromRId(this.ivFront,this.rFrameFront);

        if(!this.isEnabled()){
            this.setIvBGFromRId(this.ivBack,this.rFrameBackDisabled);
            this.setIvBGFromRId(this.ivFront,this.rFrameFrontDisabled);
        }
        this.resetTouchEvent();
    }


    public void setMyOnClickListener(Runnable runnable){
        this.onClickRunnable=runnable;
        this.resetTouchEvent();
    }

    public void setMyOnLongClickListener(Runnable runnable){
        this.onLongClickRunnable=runnable;
        this.resetTouchEvent();
    }

}
