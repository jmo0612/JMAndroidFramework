package com.thowo.jmandroidframework.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.widget.TextView;

import com.thowo.jmandroidframework.R;
import com.thowo.jmjavaframework.JMDataContainer;
import com.thowo.jmjavaframework.JMFormInterface;
import com.thowo.jmjavaframework.JMInputInterface;
//import com.thowo.jmandroidframework.db.JMTextViewFiller;

/**
 * Created by jimi on 10/26/2017.
 */

public class JMAnTextView extends AppCompatTextView implements JMInputInterface {
    private String value;
    private String format;
    private String font;
    private int dataType;
    private JMDataContainer dataContainer;

    public JMDataContainer getDataContainer(){
        return this.dataContainer;
    }


    public JMAnTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.JMAnView);
        int count = typedArray.getIndexCount();
        try{

            for (int i = 0; i < count; ++i) {

                int attr = typedArray.getIndex(i);
                // the attr corresponds to the title attribute
                if(attr == R.styleable.JMAnView_text) {
                    this.value=typedArray.getString(attr);
                    this.setText(this.value);
                }else if(attr == R.styleable.JMAnView_fontTTF) {
                    this.font=typedArray.getString(attr);
                    this.setFont();
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
        setTypeface(tf);
    }


    @Override
    public void displayText(String text, int JMDataContainerConstantAlign) {
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
