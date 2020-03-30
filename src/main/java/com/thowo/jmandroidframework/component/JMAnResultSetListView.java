package com.thowo.jmandroidframework.component;

import android.content.Context;
import android.content.res.Resources;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thowo.jmandroidframework.R;
import com.thowo.jmjavaframework.JMDataContainer;
import com.thowo.jmjavaframework.db.JMResultSet;
//import com.thowo.jmandroidframework.db.JMTextViewFiller;
//import com.thowo.jmandroidframework.db.JMRowObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by jimi on 9/23/2017.
 */

public class JMAnResultSetListView {
    private JMResultSet rs;

    public JMAnResultSetListView(JMResultSet jmResultSet){
        this.rs=jmResultSet;
    }

    public void displayRowObjectData(JMAnListViewCache vCache,String imgPath){
        if(this.rs!=null){
            for(int i=0;i<this.rs.getColCount();i++){
                assignRowObject(vCache,i);
            }
        }else{
            Log.d(TAG, "displayRowObjectData: NULL");
        }
    }

    private void assignRowObject(JMAnListViewCache vCache, int columnIndex){
        View vTmp=vCache.getView(columnIndex,/*rObj.getColumnName(columnIndex)*/"");

        if(vTmp!=null){
            if(vTmp.getClass()==TextView.class){
                TextView tv=(TextView) vTmp;
                tv.setText(this.rs.getString(columnIndex));
            }else if(vTmp.getClass()==EditText.class){
                EditText edt=(EditText) vTmp;
                edt.setText(this.rs.getString(columnIndex));
            }else if(vTmp.getClass()==Button.class){
                Button btn=(Button) vTmp;
                btn.setText(this.rs.getString(columnIndex));
            }else if(vTmp.getClass()== JMAnImageFrame.class){
                JMAnImageFrame imageFrame=(JMAnImageFrame) vTmp;
                JMDataContainer tmp=imageFrame.getDataContainer();
                tmp.setValue(this.rs,columnIndex);
                //imageFrame.loadImage(rObj.getDBString(columnIndex));
            }else if(vTmp.getClass()==JMAnButton.class){
                JMAnButton jmView=(JMAnButton) vTmp;
                JMDataContainer tmp=jmView.getDataContainer();
                tmp.setValue(this.rs,columnIndex);
                //jmView.displayText(rObj.getValue(columnIndex),rObj.getDataType(columnIndex));
            }else if(vTmp.getClass()==JMAnEditText.class){
                JMAnEditText jmView=(JMAnEditText) vTmp;
                JMDataContainer tmp=jmView.getDataContainer();
                tmp.setValue(this.rs,columnIndex);
                //jmView.displayText(rObj.getValue(columnIndex),rObj.getDataType(columnIndex));
            }else if(vTmp.getClass()==JMAnHorizontalButton.class){
                JMAnHorizontalButton jmView=(JMAnHorizontalButton) vTmp;
                JMDataContainer tmp=jmView.getDataContainer();
                tmp.setValue(this.rs,columnIndex);
                //jmView.displayText(rObj.getValue(columnIndex),rObj.getDataType(columnIndex));
            }else if(vTmp.getClass()==JMAnLoadingSprite.class){
                JMAnLoadingSprite jmView=(JMAnLoadingSprite) vTmp;
                jmView.setText(this.rs.getString(columnIndex));
            }else if(vTmp.getClass()==JMAnTextView.class){
                JMAnTextView jmView=(JMAnTextView) vTmp;
                JMDataContainer tmp=jmView.getDataContainer();
                tmp.setValue(this.rs,columnIndex);
                //jmView.displayText(rObj.getValue(columnIndex),rObj.getDataType(columnIndex));
            }else if(vTmp.getClass()==JMAnVerticalButton.class){
                JMAnVerticalButton jmView=(JMAnVerticalButton) vTmp;
                JMDataContainer tmp=jmView.getDataContainer();
                tmp.setValue(this.rs,columnIndex);
                //jmView.displayText(rObj.getValue(columnIndex),rObj.getDataType(columnIndex));
            }
        }
    }



    public JMResultSet getRowObject(){
        return this.rs;
    }


}
