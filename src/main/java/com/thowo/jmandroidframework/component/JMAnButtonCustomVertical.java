package com.thowo.jmandroidframework.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;

import com.thowo.jmandroidframework.R;
import com.thowo.jmjavaframework.JMDataContainer;
import com.thowo.jmjavaframework.JMInputInterface;

public class JMAnButtonCustomVertical extends LinearLayoutCompat implements JMInputInterface {
    private String value;
    private String font;
    private int rFrame;
    private int rFramePressed;
    private int rFrameDisabled;
    private ImageView frame=null;
    private boolean isFrontFrame=false;
    private boolean isToggle=false;
    private boolean selected=false;

    private boolean locked;
    private OnClickListener onClickListener;
    private OnTouchListener onTouchListener;
    private int txtColor;

    private TextView tv;
    private ImageView iv;
    private JMDataContainer dataContainer;

    public JMDataContainer getDataContainer(){
        return this.dataContainer;
    }


    public JMAnButtonCustomVertical(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.vertical_custom_button,this);

        tv=findViewById(R.id.bcvCaption);
        txtColor=tv.getCurrentTextColor();


        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.JMAnView);
        int count = typedArray.getIndexCount();
        try{

            for (int i = 0; i < count; ++i) {

                int attr = typedArray.getIndex(i);
                // the attr corresponds to the title attribute
                if(attr == R.styleable.JMAnView_text) {
                    this.value=typedArray.getString(attr);
                    this.tv.setText(this.value);
                }else if(attr == R.styleable.JMAnView_fontTTF) {
                    this.font=typedArray.getString(attr);
                    setFont();
                }else if(attr == R.styleable.JMAnView_icon) {
                    this.iv=findViewById(R.id.bcvTopIcon);
                    Drawable dr=typedArray.getDrawable(attr);
                    this.iv.setImageDrawable(dr);
                }

            }
        }

        // the recycle() will be executed obligatorily
        finally {
            // for reuse
            typedArray.recycle();
        }


        TypedArray typedArray2 = context.obtainStyledAttributes(attrs, R.styleable.JMAnCustomButton);
        int count2 = typedArray2.getIndexCount();
        try{

            for (int i = 0; i < count2; ++i) {

                int attr = typedArray2.getIndex(i);
                // the attr corresponds to the title attribute
                if(attr == R.styleable.JMAnCustomButton_frame) {
                    this.rFrame=typedArray2.getResourceId(attr,0);
                    this.refreshFrame();
                }else if(attr == R.styleable.JMAnCustomButton_framePressed) {
                    this.rFramePressed=typedArray2.getResourceId(attr,-1);
                    this.refreshFrame();
                }else if(attr == R.styleable.JMAnCustomButton_frameDisabled) {
                    this.rFrameDisabled=typedArray2.getResourceId(attr,-1);
                    this.refreshFrame();
                }else if(attr == R.styleable.JMAnCustomButton_isFrontFrame) {
                    this.isFrontFrame=typedArray2.getBoolean(attr,false);
                    this.refreshFrame();
                }else if(attr == R.styleable.JMAnCustomButton_isToggle) {
                    this.isToggle=typedArray2.getBoolean(attr,false);
                    this.refreshFrame();
                }else if(attr == R.styleable.JMAnCustomButton_selected) {
                    this.select(typedArray2.getBoolean(attr,false));
                    this.refreshFrame();
                }

            }
        }

        // the recycle() will be executed obligatorily
        finally {
            // for reuse
            typedArray2.recycle();
        }

    }

    @Override
    public void setEnabled(boolean enabled){
        super.setEnabled(enabled);
        if(!enabled){
            this.setOnTouchListener(null);
            this.setOnClickListener(null);
            this.iv.setColorFilter(Color.argb(230,100,100,100));
            this.tv.setTextColor(Color.rgb(100,100,100));
        }else{
            this.setOnTouchListener(this.onTouchListener);
            this.setOnClickListener(this.onClickListener);
            this.iv.setColorFilter(null);
            this.tv.setTextColor(this.txtColor);
        }
    }

    private void resetTouchEvent(){
        this.onTouchListener=new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(!JMAnButtonCustomVertical.this.isToggle){
                    if(event.getAction()==MotionEvent.ACTION_BUTTON_PRESS){
                        JMAnButtonCustomVertical.this.frame.setBackgroundResource(JMAnButtonCustomVertical.this.rFramePressed);
                    }else{
                        JMAnButtonCustomVertical.this.frame.setBackgroundResource(JMAnButtonCustomVertical.this.rFrame);
                    }
                }else{
                    if(event.getAction()==MotionEvent.ACTION_BUTTON_RELEASE){
                        JMAnButtonCustomVertical.this.select(!JMAnButtonCustomVertical.this.selected);
                        if(JMAnButtonCustomVertical.this.selected){
                            JMAnButtonCustomVertical.this.frame.setBackgroundResource(JMAnButtonCustomVertical.this.rFramePressed);
                        }else{
                            JMAnButtonCustomVertical.this.frame.setBackgroundResource(JMAnButtonCustomVertical.this.rFrame);
                        }
                    }
                }
                return false;
            }
        };
    }

    private void select(boolean selected){
        this.selected=selected;
    }

    private void refreshFrame(){
        ImageView b=findViewById(R.id.bcvFrameBack);
        ImageView f=findViewById(R.id.bcvFrameFront);
        ImageView tmp=b;
        b.setVisibility(VISIBLE);
        f.setVisibility(GONE);
        if(this.isFrontFrame){
            tmp=f;
            b.setVisibility(GONE);
            f.setVisibility(VISIBLE);
        }

        if(this.rFramePressed==-1)this.rFramePressed=this.rFrame;
        if(this.rFrameDisabled==-1)this.rFrameDisabled=this.rFrame;
        tmp.setBackgroundResource(this.rFrame);
        if(!this.isEnabled())tmp.setBackgroundResource(this.rFrameDisabled);
        this.frame=tmp;
        this.resetTouchEvent();
    }

    private void setFont(){
        Typeface tf=Typeface.createFromAsset(getContext().getAssets(),"fonts/" + font);
        tv.setTypeface(tf);
    }

    @Override
    public void setOnClickListener(OnClickListener l){
        super.setOnClickListener(l);
    }




    @Override
    public void displayText(String text, int JMDataContainerConstantAlign) {
        this.value=text;
        this.tv.setText(text);
    }

    @Override
    public void displayError(String errMsg) {
        this.tv.setText(errMsg);
        this.value="";
    }

    @Override
    public void displayHint(String hint) {
        this.tv.setHint(hint);
    }

    @Override
    public void setDataContainer(JMDataContainer dataContainer) {
        this.dataContainer=dataContainer;
    }

    @Override
    public void setHidden(boolean hidden) {

    }

    @Override
    public void setValueString(String value) {

    }

    @Override
    public void setValueObject(Object value) {

    }
}
