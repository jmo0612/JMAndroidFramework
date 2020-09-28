package com.thowo.jmandroidframework.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatEditText;

import com.thowo.jmandroidframework.R;
import com.thowo.jmjavaframework.JMDataContainer;
import com.thowo.jmjavaframework.JMFormatCollection;
import com.thowo.jmjavaframework.JMFunctions;
import com.thowo.jmjavaframework.JMInputInterface;

public class JMAnEditTextDouble extends AppCompatAutoCompleteTextView implements JMInputInterface {
    private boolean isDate;
    private Context ctx;
    private Double value=0.0;
    private String format;
    private int dataType;
    private String font;
    private String dateErrMsg;
    private JMDataContainer dataContainer;

    public JMDataContainer getDataContainer(){
        return this.dataContainer;
    }
    public JMAnEditTextDouble(Context context, AttributeSet attrs) {
        super(context, attrs);
        ctx=context;
        setDefaultAttribs();
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.JMAnView);
        int count = typedArray.getIndexCount();
        try{

            for (int i = 0; i < count; ++i) {

                int attr = typedArray.getIndex(i);
                // the attr corresponds to the title attribute
                if(attr == R.styleable.JMAnView_text) {
                    String tmp=typedArray.getString(attr);
                    value=JMFormatCollection.strToDouble(tmp);
                    //value=typedArray.getString(attr);
                    this.setText(JMFormatCollection.decimal(value));
                }else if(attr == R.styleable.JMAnView_fontTTF) {
                    font=typedArray.getString(attr);
                    setFont();
                }else if(attr == R.styleable.JMAnView_bg) {
                    this.setBackgroundResource(typedArray.getResourceId(attr,0));
                }else if(attr == R.styleable.JMAnView_lookup) {
                    String tmp=typedArray.getString(attr);
                    String[] items=JMFormatCollection.strToArray(tmp,"\\|");
                    if(items.length>0){
                        this.setAutoComplete(items);
                    }
                }
            }
        }

        // the recycle() will be executed obligatorily
        finally {
            // for reuse
            typedArray.recycle();
        }
        this.setListeners();
    }
    public void setAutoComplete(String[] items) {
        ArrayAdapter<String> tmp= new ArrayAdapter<String>(this.ctx, android.R.layout.simple_dropdown_item_1line, items);
        this.setAdapter(tmp);
    }
    private void setListeners(){
        this.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b==true){
                    JMAnEditTextDouble.this.setText(String.valueOf(JMAnEditTextDouble.this.value));
                    JMAnEditTextDouble.this.selectAll();
                }else{
                    JMAnEditTextDouble.this.value=JMFormatCollection.strToDouble(JMAnEditTextDouble.this.getText().toString());
                    JMAnEditTextDouble.this.setText(JMFormatCollection.decimal(JMAnEditTextDouble.this.value));
                }
            }
        });
    }
    private void setDefaultAttribs(){
        setBackgroundResource(R.drawable.text_box);
        setPadding(20,1,20,1);
    }
    private void setFont(){
        Typeface tf=Typeface.createFromAsset(getContext().getAssets(),"fonts/" + font);
        setTypeface(tf);
    }

    @Override
    public void displayText(String text, int JMDataContainerConstantAlign) {
        this.value=JMFormatCollection.strToDouble(text);
        this.setText(JMFormatCollection.decimal(this.value));
    }
    @Override
    public void displayError(String errMsg) {
        this.setError(errMsg);
        this.value=0.0;
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