package com.thowo.jmandroidframework.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import androidx.appcompat.widget.AppCompatButton;
import android.util.AttributeSet;

import com.thowo.jmandroidframework.R;
import com.thowo.jmjavaframework.JMDataContainer;
import com.thowo.jmjavaframework.JMFormInterface;


/**
 * Created by jimi on 6/29/2017.
 */


public class JMAnButton extends AppCompatButton implements JMFormInterface {
    private String value;
    private String font;
    private JMDataContainer dataContainer;

    public JMDataContainer getDataContainer(){
        return this.dataContainer;
    }

    public JMAnButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setDefaultAttribs();
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.JMAnView);
        int count = typedArray.getIndexCount();
        try{

            for (int i = 0; i < count; ++i) {

                int attr = typedArray.getIndex(i);
                // the attr corresponds to the title attribute
                if(attr == R.styleable.JMAnView_text) {
                    value=typedArray.getString(attr);
                    this.setText(value);
                }else if(attr == R.styleable.JMAnView_fontTTF) {
                    font=typedArray.getString(attr);
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
    }

    private void setDefaultAttribs(){
        setBackgroundResource(R.drawable.glossy_button_selector);
        setPadding(20,1,20,1);
    }

    private void setFont(){
        Typeface tf=Typeface.createFromAsset(getContext().getAssets(),"fonts/" + font);
        setTypeface(tf);
    }

    @Override
    public void displayText(String text) {
        this.value=text;
        this.setText(text);
    }

    @Override
    public void displayError(String errMsg) {
        this.setText(errMsg);
        this.value="";
    }

    @Override
    public void displayHint(String hint) {
        this.setHint(hint);
    }

    @Override
    public void setDataContainer(JMDataContainer dataContainer) {
        this.dataContainer=dataContainer;
    }
}
