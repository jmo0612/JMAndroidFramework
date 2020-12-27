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
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
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

public class JMAnButtonCustomVertical extends JMAnFrame implements JMInputInterface {

    private String value;
    private String font;
    private float fontSize=12;
    private int iconSize=50;
    private Bitmap originalIcon;

    private int textPadding=5;
    private int contentPadding=10;
    private int txtColor;

    private TextView tv;
    private ImageView iv;
    private JMDataContainer dataContainer;

    public JMDataContainer getDataContainer(){
        return this.dataContainer;
    }


    public JMAnButtonCustomVertical(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        View.inflate(context, R.layout.vertical_custom_button,super.getRoot());

        tv=findViewById(R.id.bcvCaption);
        txtColor=tv.getCurrentTextColor();

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.JMAnCustomButton);
        int count = typedArray.getIndexCount();
        try{

            for (int i = 0; i < count; ++i) {

                int attr = typedArray.getIndex(i);
                // the attr corresponds to the title attribute
                if(attr == R.styleable.JMAnCustomButton_text2) {
                    this.value=typedArray.getString(attr);
                    this.tv.setText(this.value);
                }else if(attr == R.styleable.JMAnCustomButton_fontTTF2) {
                    this.font=typedArray.getString(attr);
                    setFont();
                }else if(attr == R.styleable.JMAnCustomButton_icon2) {
                    this.iv=findViewById(R.id.bcvTopIcon);
                    Drawable dr=typedArray.getDrawable(attr);
                    this.originalIcon=((BitmapDrawable) dr).getBitmap();
                    this.iv.setImageDrawable(dr);
                    this.setIconSize(this.iconSize);
                }else if(attr == R.styleable.JMAnCustomButton_fontSize2) {
                    this.fontSize=typedArray.getDimension(attr,12);
                    tv.setTextSize(this.fontSize);
                }else if(attr == R.styleable.JMAnCustomButton_iconSize2) {
                    this.setIconSize(typedArray.getDimensionPixelSize(attr,10));
                }else if(attr == R.styleable.JMAnCustomButton_textPadding) {
                    this.textPadding=typedArray.getDimensionPixelSize(attr,5);
                    this.refreshPadding();
                }else if(attr == R.styleable.JMAnCustomButton_contentPadding) {
                    this.contentPadding=typedArray.getDimensionPixelSize(attr,10);
                    this.refreshPadding();
                }

            }
        }

        // the recycle() will be executed obligatorily
        finally {
            // for reuse
            typedArray.recycle();
        }
    }


    private void refreshPadding(){
        LinearLayout l=findViewById(R.id.buttonContent);
        l.setPadding(this.contentPadding,this.contentPadding,this.contentPadding,this.contentPadding);
        //this.iv.setPadding(0,this.contentPadding,0,0);
        this.tv.setPadding(0,this.textPadding,0,0);
    }

    private void setIconSize(int size){
        List<JMVec2> nSize=JMFunctions.scaledSize(JMVec2.create(this.originalIcon.getWidth(),this.originalIcon.getHeight()),JMVec2.create(size,size),JMFunctions.SCALE_FIT);
        //Drawable tmp=this.iv.getDrawable();
        //tmp.setBounds(nSize.get(1).getIntX(),nSize.get(1).getIntY(),nSize.get(1).getIntX()+nSize.get(0).getIntX(),nSize.get(1).getIntY()+nSize.get(0).getIntY());
        //tmp.setBounds(0,0,size,size);
        Bitmap nB=Bitmap.createScaledBitmap(this.originalIcon,nSize.get(0).getIntX(),nSize.get(0).getIntY(),false);
        this.iv.setImageBitmap(nB);
        this.iconSize=size;
    }

    private void setFont(){
        Typeface tf=Typeface.createFromAsset(getContext().getAssets(),"fonts/" + font);
        tv.setTypeface(tf);
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
