package com.thowo.jmandroidframework.component;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.thowo.jmjavaframework.JMDataContainer;
import com.thowo.jmjavaframework.db.JMResultSet;
import com.thowo.jmjavaframework.table.JMCell;
import com.thowo.jmjavaframework.table.JMRow;

import java.util.List;

import static android.content.ContentValues.TAG;

public class JMAnRowListView {
    private JMRow row;
    private List<JMCell> cells;

    public JMAnRowListView(JMRow row){
        this.row=row;
    }

    public void displayRowObjectData(JMAnListViewCache vCache,String imgPath){
        if(this.row!=null){
            this.cells=this.row.getCells();
            for(int i=0;i<this.cells.size();i++){
                assignRowObject(vCache,i);
            }
        }else{
            Log.d(TAG, "displayRowObjectData: NULL");
        }
    }

    private void assignRowObject(JMAnListViewCache vCache, int columnIndex){
        View vTmp=vCache.getView(columnIndex,/*rObj.getColumnName(columnIndex)*/"");

        if(vTmp!=null){
            if(vTmp.getClass()== JMAnImageFrame.class){
                JMAnImageFrame imageFrame=(JMAnImageFrame) vTmp;
                //imageFrame
                //this.cells.get(columnIndex).getDataContainer().addInterface(imageFrame,false);
            }else if(vTmp.getClass()==JMAnButton.class){
                JMAnButton jmView=(JMAnButton) vTmp;
                jmView.setText(this.cells.get(columnIndex).getText());
                //this.cells.get(columnIndex).getDataContainer().addInterface(jmView,false);
            }else if(vTmp.getClass()==JMAnEditText.class){
                JMAnEditText jmView=(JMAnEditText) vTmp;
                jmView.setText(this.cells.get(columnIndex).getText());
                //this.cells.get(columnIndex).getDataContainer().addInterface(jmView,false);
            }else if(vTmp.getClass()==JMAnEditTextBoolean.class){
                JMAnEditTextBoolean jmView=(JMAnEditTextBoolean) vTmp;
                jmView.setText(this.cells.get(columnIndex).getText());
                //this.cells.get(columnIndex).getDataContainer().addInterface(jmView,false);
            }else if(vTmp.getClass()==JMAnEditTextDate.class){
                JMAnEditTextDate jmView=(JMAnEditTextDate) vTmp;
                jmView.setText(this.cells.get(columnIndex).getText());
                //this.cells.get(columnIndex).getDataContainer().addInterface(jmView,false);
            }else if(vTmp.getClass()==JMAnEditTextDouble.class){
                JMAnEditTextDouble jmView=(JMAnEditTextDouble) vTmp;
                jmView.setText(this.cells.get(columnIndex).getText());
                //this.cells.get(columnIndex).getDataContainer().addInterface(jmView,false);
            }else if(vTmp.getClass()==JMAnEditTextInteger.class){
                JMAnEditTextInteger jmView=(JMAnEditTextInteger) vTmp;
                jmView.setText(this.cells.get(columnIndex).getText());
                //this.cells.get(columnIndex).getDataContainer().addInterface(jmView,false);
            }else if(vTmp.getClass()==JMAnHorizontalButton.class){
                JMAnHorizontalButton jmView=(JMAnHorizontalButton) vTmp;
                //
                //this.cells.get(columnIndex).getDataContainer().addInterface(jmView,false);
            }else if(vTmp.getClass()==JMAnTextView.class){
                JMAnTextView jmView=(JMAnTextView) vTmp;
                jmView.setText(this.cells.get(columnIndex).getText());
                //this.cells.get(columnIndex).getDataContainer().addInterface(jmView,false);
            }else if(vTmp.getClass()==JMAnVerticalButton.class){
                JMAnVerticalButton jmView=(JMAnVerticalButton) vTmp;
                //this.cells.get(columnIndex).getDataContainer().addInterface(jmView,false);
            }
        }
    }



    public JMRow getRowObject(){
        return this.row;
    }
}
