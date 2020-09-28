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

public class JMAnEditTextBoolean extends AppCompatAutoCompleteTextView implements JMInputInterface {
    private boolean isDate;
    private Context ctx;
    private Boolean value=false;
    private String strTrue="";
    private String strFalse="";
    private String format;
    private int dataType;
    private String font;
    private String dateErrMsg;
    private JMDataContainer dataContainer;

    public JMDataContainer getDataContainer(){
        return this.dataContainer;
    }
    public JMAnEditTextBoolean(Context context, AttributeSet attrs) {
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
                    value=JMFormatCollection.strToBoolean(tmp,this.strTrue,this.strFalse);
                    this.setText(JMFormatCollection.booleanToString(value,this.strTrue,this.strFalse));
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
                }else if(attr == R.styleable.JMAnView_params) {
                    String tmp=typedArray.getString(attr);
                    String[] prm=JMFormatCollection.strToArray(tmp,"\\|");
                    for(int j=0;j<prm.length;j++){
                        JMFunctions.trace("jumlah: "+prm[j]);
                    }
                    if(prm.length==2){
                        this.updateBooleanString(prm[0],prm[1]);
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
    public void updateBooleanString(String strTrue, String strFalse){
        this.strTrue=strTrue;
        this.strFalse=strFalse;
        this.setText(JMFormatCollection.booleanToString(this.value,this.strTrue,this.strFalse));
    }
    private void setListeners(){
        this.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b==true){
                    JMAnEditTextBoolean.this.setText(JMFormatCollection.booleanToString(JMAnEditTextBoolean.this.value,JMAnEditTextBoolean.this.strTrue,JMAnEditTextBoolean.this.strFalse));
                    JMAnEditTextBoolean.this.selectAll();
                }else{
                    JMAnEditTextBoolean.this.value=JMFormatCollection.strToBoolean(JMAnEditTextBoolean.this.getText().toString(),JMAnEditTextBoolean.this.strTrue,JMAnEditTextBoolean.this.strFalse);
                    JMAnEditTextBoolean.this.setText(JMFormatCollection.booleanToString(JMAnEditTextBoolean.this.value,JMAnEditTextBoolean.this.strTrue,JMAnEditTextBoolean.this.strFalse));
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
        this.value=JMFormatCollection.strToBoolean(text,this.strTrue,this.strFalse);
        this.setText(JMFormatCollection.booleanToString(this.value,this.strTrue,this.strFalse));
    }
    @Override
    public void displayError(String errMsg) {
        this.setError(errMsg);
        this.value=false;
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