package com.thowo.jmandroidframework.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.thowo.jmandroidframework.R;
//import com.thowo.jmandroidframework.db.JMTextViewFiller;

/**
 * Created by jimi on 6/30/2017.
 */

public class JMAnLoadingSprite extends FrameLayout {
    private String value;
    private String font;
    private TextView tv;


    public JMAnLoadingSprite(Context context, AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.loading_sprite,this);
        tv=(TextView) findViewById(R.id.messageLS);

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
                }else if(attr == R.styleable.JMAnView_bg) {
                    this.setBackgroundResource(typedArray.getResourceId(attr,0));
                }

            }
        }

        // the recycle() will be executed obligatorily
        finally {
            // for reuse
            typedArray.recycle();
        }
        hideLoading();
    }

    private void setFont(){
        Typeface tf=Typeface.createFromAsset(getContext().getAssets(),"fonts/" + this.font);
        this.tv.setTypeface(tf);
    }

    public void hideLoading(){
        this.setVisibility(INVISIBLE);
        this.invalidate();
    }

    public void showLoading(){
        this.setVisibility(VISIBLE);
        this.invalidate();
    }
    public void showLoading(String text){
        this.setText(text);
        this.showLoading();
    }
    public void setText(String text){
        this.tv.setText(text);
    }
}
