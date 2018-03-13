package com.cmsonline.frameworkdemo;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;

/**
 * Created by chris on 23/10/2017.
 */

public class WebActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstate) {//let Fragment control the UI

        super.onCreate(savedInstate);
        setContentView(R.layout.web_activity);
        FragmentManager fm = getFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        if(fragment == null)
        {
            fragment =new WebWrapperFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container,fragment)
                    .commit();
        }

    }
}
