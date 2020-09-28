package com.thowo.jmandroidframework.component;
/*
 * Params=STRING:
 *           [0] Is Datetime (TRUE | FALSE)
 *           [1] Date AI (0:NORMAL | 1:PREFER_PAST | 2:PREFER_FUTURE)
 * */

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
import com.thowo.jmjavaframework.JMDate;
import com.thowo.jmjavaframework.JMDateBuilder;
import com.thowo.jmjavaframework.JMFormatCollection;
import com.thowo.jmjavaframework.JMFunctions;
import com.thowo.jmjavaframework.JMInputInterface;

public class JMAnEditTextDate extends AppCompatAutoCompleteTextView implements JMInputInterface {
    private boolean isDate;
    private Context ctx;
    private JMDate value;
    private boolean isTime=false;
    private Integer dateAI=JMDateBuilder.CONST_AI_NORMAL;
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
    public JMAnEditTextDate(Context context, AttributeSet attrs) {
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
                    value= JMDateBuilder.create(tmp,this.dateAI).getDate();
                    if(JMAnEditTextDate.this.isTime){
                        JMAnEditTextDate.this.setText(value.dateTimeFull24());
                    }else{
                        JMAnEditTextDate.this.setText(value.dateFull());
                    }
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
                    if(prm.length>0){
                        this.updateDateString(prm[0]);
                        if(prm.length>1){
                            this.updateDateAI(prm[1]);
                        }
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
    public void updateDateAI(String paramAI){
        this.dateAI=JMFormatCollection.strToInteger(paramAI);
        value= JMDateBuilder.create(this.getText().toString(),this.dateAI).getDate();
        if(JMAnEditTextDate.this.isTime){
            JMAnEditTextDate.this.setText(value.dateTimeFull24());
        }else{
            JMAnEditTextDate.this.setText(value.dateFull());
        }
    }
    public void updateDateString(String isTime){
        this.isTime=isTime.toUpperCase().equals("TRUE");
        if(this.value==null)return;
        if(this.isTime){
            this.setText(this.value.dateTimeFull24());
        }else{
            this.setText(this.value.dateFull());
        }
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
                    if(JMAnEditTextDate.this.isTime){
                        JMAnEditTextDate.this.setText(value.dateTimeDB());
                    }else{
                        JMAnEditTextDate.this.setText(value.dateDB());
                    }
                    JMAnEditTextDate.this.selectAll();
                }else{
                    JMAnEditTextDate.this.value=JMDateBuilder.create(JMAnEditTextDate.this.getText().toString(),JMAnEditTextDate.this.dateAI).getDate();
                    if(JMAnEditTextDate.this.isTime){
                        JMAnEditTextDate.this.setText(value.dateTimeFull24());
                    }else{
                        JMAnEditTextDate.this.setText(value.dateFull());
                    }
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
        this.value=JMDateBuilder.create(text,this.dateAI).getDate();
        if(JMAnEditTextDate.this.isTime){
            JMAnEditTextDate.this.setText(value.dateTimeFull24());
        }else{
            JMAnEditTextDate.this.setText(value.dateFull());
        }
    }
    @Override
    public void displayError(String errMsg) {
        this.setError(errMsg);
        this.value=JMDate.now();
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