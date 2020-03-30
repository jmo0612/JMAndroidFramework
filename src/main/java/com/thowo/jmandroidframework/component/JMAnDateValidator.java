package com.thowo.jmandroidframework.component;

import android.text.Editable;
import android.text.TextWatcher;

import com.thowo.jmjavaframework.JMDataContainer;
import com.thowo.jmjavaframework.JMDate;
import com.thowo.jmjavaframework.JMFormatCollection;
import com.thowo.jmjavaframework.JMFunctions;
import com.thowo.jmjavaframework.lang.JMConstMessage;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class JMAnDateValidator implements TextWatcher {
    private JMDataContainer dataContainer;
    private boolean time;

    public JMAnDateValidator(JMDataContainer dataContainer, Boolean time){
        this.dataContainer=dataContainer;
        this.time=time;
    }
    public JMAnDateValidator(JMDataContainer dataContainer){
        this.dataContainer=dataContainer;
        this.time=false;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String workingStr=JMDate.toStringYMDhms((String) s);

        boolean isValid= JMFormatCollection.isThisDateValid(workingStr,"yyyy/MM/dd HH:mm:ss");

        if (!isValid) {
            this.dataContainer.setErrorMessage(JMFunctions.getMessege(JMConstMessage.MSG_ELSE+JMConstMessage.MSG_ELSE_DATE_INVALID));
            //this.dataContainer.setValueAsString((String) s);
            return;
        }

        if(this.time){
            this.dataContainer.setValueAsJMDateTime24(new JMDate(JMFormatCollection.dateFromString(workingStr,"yyyy/MM/dd HH:mm:ss")),false);
        }else{
            this.dataContainer.setValueAsJMDateTime24(new JMDate(JMFormatCollection.dateFromString(workingStr,"yyyy/MM/dd")),false);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }



}
