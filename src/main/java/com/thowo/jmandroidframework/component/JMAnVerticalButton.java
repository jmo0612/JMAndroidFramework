package com.thowo.jmandroidframework.component;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.thowo.jmandroidframework.R;
import com.thowo.jmjavaframework.JMDataContainer;
import com.thowo.jmjavaframework.JMFormInterface;
import com.thowo.jmjavaframework.JMInputInterface;
//import com.thowo.jmandroidframework.db.JMTextViewFiller;

/**
 * Created by jimi on 6/29/2017.
 */

public class JMAnVerticalButton extends LinearLayout implements JMInputInterface {
    private String value;
    private String font;

    private boolean locked;
    private OnClickListener onClickListener;
    private int txtColor;

    private TextView tv;
    private ImageView iv;
    private JMDataContainer dataContainer;

    public JMDataContainer getDataContainer(){
        return this.dataContainer;
    }


    public JMAnVerticalButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context,R.layout.vertical_button,this);

        tv=findViewById(R.id.captionVB);
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
                    this.iv=findViewById(R.id.topIconVB);
                    Drawable dr=typedArray.getDrawable(attr);
                    this.iv.setImageDrawable(dr);
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


    }

    private void setFont(){
        Typeface tf=Typeface.createFromAsset(getContext().getAssets(),"fonts/" + font);
        tv.setTypeface(tf);
    }

    public void setMyOnClickedListener(View.OnClickListener listener){
        ImageView clickArea=findViewById(R.id.clickAreaVB);
        clickArea.setOnClickListener(listener);
        this.onClickListener=listener;
    }


    public void lock(){
        this.locked=true;
        ImageView clickArea=findViewById(R.id.clickAreaVB);
        clickArea.setOnClickListener(null);
        clickArea.setEnabled(false);
        iv.setColorFilter(Color.argb(230,100,100,100));
        clickArea.setEnabled(false);
        tv.setTextColor(Color.rgb(100,100,100));
    }

    public void unlock(){
        this.locked=false;
        ImageView clickArea=findViewById(R.id.clickAreaVB);
        clickArea.setOnClickListener(this.onClickListener);
        clickArea.setEnabled(true);
        iv.setColorFilter(null);
        clickArea.setEnabled(true);
        tv.setTextColor(txtColor);
        //clickArea.setImageResource(R.drawable.glossy_button_selector);
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
