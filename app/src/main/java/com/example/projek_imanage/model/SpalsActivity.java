package com.example.projek_imanage.model;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.projek_imanage.R;
import com.example.projek_imanage.fitur.Homeitem;

public class SpalsActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spals);
        Thread thread = new Thread(){
            public void run(){
                try {
                    sleep(4000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }finally {
                    startActivity(new Intent(SpalsActivity.this, Homeitem.class));
                    finish();
                }
            }
        };
        thread.start();
    }
}
