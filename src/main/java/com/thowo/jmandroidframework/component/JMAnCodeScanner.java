package com.thowo.jmandroidframework.component;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.zxing.Result;
import com.thowo.jmandroidframework.JMAnConst;
import com.thowo.jmandroidframework.R;
import com.thowo.jmjavaframework.JMFunctions;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class JMAnCodeScanner extends JMAnActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView zXingScannerView=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.zXingScannerView=new ZXingScannerView(this);
        setContentView(this.zXingScannerView);
    }

    @Override
    public void onResume() {
        super.onResume();
        this.zXingScannerView.setResultHandler(this::handleResult);
        this.zXingScannerView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.zXingScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result rawResult) {
        Intent intent=new Intent();
        intent.putExtra(JMAnConst.RESULTCODE_SCANCODE,rawResult.getText());
        setResult(JMAnConst.REQUESTCODE_SCANCODE,intent);
        finish();//finishing activity
    }
}