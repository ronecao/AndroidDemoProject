package com.transactionplatform.frameworkdemo;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Toast;

import com.transactionplatform.framework.TransactionPlatformAndroidBridge;

/**
 * Created by chris on 23/10/2017.
 */

public class WebWrapperFragment extends Fragment {
    private final Handler handler=new Handler();
    WebView mwebView;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //super.onCreateView(inflater, container, savedInstanceState);
        //View v= inflater.inflate(R.layout.web_fragment,container,false);
        View v=inflater.inflate(R.layout.web_fragment,container,false);

        mwebView = v.findViewById(R.id.web_veiw_content);
        mwebView.getSettings().setJavaScriptEnabled(true);//enable js
        String url="file:///android_asset/index.html";
        mwebView.loadUrl(url);
        mwebView.addJavascriptInterface(new TransactionPlatformAndroidBridge(getContext(),mwebView), "EMVIO");//EMVIO is id window.EMVIO.function name
        mwebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {

                Log.d("WEB", "onJsAlert(" + view + "," + url + "," + message + "," + result + ")");

                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                result.confirm();
                return true;
            }

        });//active on

        return v;
    }








}
