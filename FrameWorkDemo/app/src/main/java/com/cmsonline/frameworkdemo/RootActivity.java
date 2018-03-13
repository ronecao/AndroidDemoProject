package com.cmsonline.frameworkdemo;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;

import android.text.Editable;

import android.util.Log;
import android.view.View;
import android.widget.Button;


/**
 * Created by chris on 23/10/2017.
 */

public class RootActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstate) {//let Fragment control the UI

        super.onCreate(savedInstate);

        setContentView(R.layout.root_activity);
        ClickListener l = new ClickListener();

        Button nativeBtn= findViewById(R.id.nativeButton);

        nativeBtn.setOnClickListener(l);
        Button webBtn= findViewById(R.id.webButton);
        webBtn.setOnClickListener(l);

    }
    class ClickListener implements View.OnClickListener{
        Editable amtstr;
        String transDict;
        @Override
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.nativeButton:
                    Log.v("Global","native");
                    Intent i = new Intent (getApplicationContext(),MainActivity.class);
                    startActivity(i);
                    //startActivityForResult(intent,REQUEST_CHANGE_CODE);

                    break;
                case R.id.webButton:
                    Log.v("Global","web");
                    Intent i1 = new Intent (getApplicationContext(),WebActivity.class);
                    startActivity(i1);
                    break;

            }
        }
    }
}
