package com.slycepay.frameworkdemo;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
//import android.support.v4.app.Fragment;
import android.app.FragmentManager;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.slycepay.framework.SlycePay;
import com.slycepay.framework.SlycePayListener;
import com.slycepay.framework.ConfirmCard;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

public class MainActivity extends AppCompatActivity implements SlycePayListener {

    private EditText amountText;
    private EditText mAuthcodeText;
    private EditText mAPITokenText;
    private Button mInitBtn;
    private Button mSaleBtn;
    private Button mCapBtn;
    private Button mReturnBtn;
    private Button mAuthBtn;
    private Button mVoidBtn;
    private Button mConnectBtn;
    private static final String TAG ="APPLICTION";
    private JSONArray mTransArray;
    SlycePay cmsf;
    public TextView mTransmessage;


    @Override
    public void initCompleted(String result){
        Log.v("APPLICATION", "Completed" + result);
        final String res = result;
        runOnUiThread(new Runnable() {
            public void run() {
                mTransmessage.setText("InitCompleted " + res);
                enableUtility();
            }
        });


    }


    @Override
    public void deviceConnectionCompleted(String Result){
        Log.v("APPLICATION","pinPadConnectionCompleted"+Result);
        //Toast.makeText(getApplicationContext(), "Connection completed"+Result,
        //Toast.LENGTH_SHORT).show();
        final String updatestr=Result;
        runOnUiThread(new Runnable() {
            public void run() {

                String mes = mTransmessage.getText() + "\r\n" + updatestr;
                mTransmessage.setText(mes);
                Log.v("APPLICATION", updatestr);
                enableTrans();
            }
        });

    }
    @Override
    public void deviceDisattached(){
        String mes = mTransmessage.getText() + "\r\n" + "Disattached";
        mTransmessage.setText(mes);

    }

    @Override
   public void transactionMessageUpdate(String msg)
    {
        final String updatestr=msg;
        runOnUiThread(new Runnable() {
            public void run()
            {
                String mes = mTransmessage.getText() + "\r\n" + updatestr;
                mTransmessage.setText(mes);
                Log.v("APPLICATION", updatestr);
            }
        });
    }

    @Override
    public void transactionSaleCompleted(String resultDict){
        Log.v("APPLICATION", resultDict);
        Log.v("APPLICATION", "length "+resultDict.length());
        JSONObject json_result;
        FragmentManager manager = getFragmentManager();
        InfoAlert alert= InfoAlert.newInstance(resultDict);
        alert.show(manager,"Result");
        int modulevalue;
        try {
            FileOpt file = new FileOpt(getApplication());
            json_result = new JSONObject(resultDict);
            mAuthcodeText.setText(json_result.getString("Reference"));
            modulevalue=file.getModuleValue(SlycePay.getmModule());
            file.addTransaction(modulevalue,resultDict,file.SALE);
        }catch (Exception e)
        {

        }
    }
    @Override
    public void captureSignature(){
        byte [] image = new byte[1024];
        cmsf.signatureCaptured(true,image);
    }
    @Override
    public void selectAID(String [] AIDName){

    }
    @Override
    public void transAuthorizationCompleted(String resultDict){
        Log.v("APPLICATION", resultDict);
        FragmentManager manager = getFragmentManager();
        InfoAlert alert= InfoAlert.newInstance(resultDict);
        alert.show(manager,"Authroization");
        JSONObject json_result;
        int modulevalue;
        try {
            FileOpt file = new FileOpt(getApplication());
            json_result = new JSONObject(resultDict);
            mAuthcodeText.setText(json_result.getString("Reference"));
            modulevalue=file.getModuleValue(SlycePay.getmModule());
            file.addTransaction(modulevalue,resultDict,file.AUTH);
        }catch (Exception e)
        {

        }
   }
   @Override
   public void transactionCaptureCompleted(String resultDict) {
       Log.v("APPLICATION", resultDict);
       FragmentManager manager = getFragmentManager();
       InfoAlert alert= InfoAlert.newInstance(resultDict);
       alert.show(manager,"Authroization");
       JSONObject json_result;
       try {
           json_result = new JSONObject(resultDict);
           mAuthcodeText.setText(json_result.getString("Reference"));
       }catch (Exception e) {

       }
   }
   @Override
    public void transactionReturnCompleted(String resultDict){
        Log.v("APPLICATION", resultDict);
        Log.v("APPLICATION", "length "+resultDict.length());
        JSONObject json_result;
        FragmentManager manager = getFragmentManager();
        InfoAlert alert= InfoAlert.newInstance(resultDict);
        alert.show(manager,"TEST");
    }
    @Override
    public void transactionVoidCompleted(String resultDict){
        Log.v("APPLICATION", resultDict);
        Log.v("APPLICATION", "length "+resultDict.length());
        JSONObject json_result;
        FragmentManager manager = getFragmentManager();
        InfoAlert alert= InfoAlert.newInstance(resultDict);
        alert.show(manager,"TEST");
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                hideKeyboard(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * check if keyboard need hide or not
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {

                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * get inputmanager，hide keyboard
     * @param token
     */
    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTransmessage=(TextView) findViewById(R.id.messagetxtView);
        setupGUI();
    }
    private  void disablebtn() {
        mConnectBtn.setEnabled(false);
        mSaleBtn.setEnabled(false);
        mVoidBtn.setEnabled(false);
        mReturnBtn.setEnabled(false);
        mAuthBtn.setEnabled(false);
        mCapBtn.setEnabled(false);

    }

    private  void enableUtility() {
        mConnectBtn.setEnabled(true);
    }
    private  void enableTrans() {
        mSaleBtn.setEnabled(true);
        mVoidBtn.setEnabled(true);
        mReturnBtn.setEnabled(true);
        mAuthBtn.setEnabled(true);
        mCapBtn.setEnabled(true);

    }

    /**
     * setup GUI component
     */
    private void setupGUI()
    {
        if(Build.VERSION.SDK_INT>=23) {
            requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO,Manifest.permission.BLUETOOTH_ADMIN,Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_PHONE_STATE}, 0);
        }
        amountText = (EditText) findViewById(R.id.amountInput);
        mAuthcodeText = (EditText) findViewById(R.id.authCodeEditText);

        ClickListener l = new ClickListener();
        mInitBtn =  (Button)findViewById(R.id.initBtn);
        mInitBtn.setOnClickListener(l);

        mSaleBtn = (Button)findViewById(R.id.saleBtn);
        mSaleBtn.setOnClickListener(l);

        mCapBtn = (Button)findViewById(R.id.capBtn);
        mCapBtn.setOnClickListener(l);

        mAuthBtn = (Button)findViewById(R.id.authBtn);
        mAuthBtn.setOnClickListener(l);

        mVoidBtn = (Button)findViewById(R.id.voidBtn);
        mVoidBtn.setOnClickListener(l);

        mReturnBtn = (Button)findViewById(R.id.returnBtn);
        mReturnBtn.setOnClickListener(l);

        mConnectBtn = (Button) findViewById(R.id.connectbtn);
        mConnectBtn.setOnClickListener(l);

        mAPITokenText = (EditText) findViewById(R.id.APIKey);

        disablebtn();

    }

    class ClickListener implements View.OnClickListener{
        Editable amtstr;
        String transDict;
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.initBtn:
                    mAPITokenText.setText("3fyl02KZBOERy7yEd6SkWqbuYmGqHQ2T");
                    cmsf= new SlycePay(getApplicationContext(),mAPITokenText.getText().toString(),MainActivity.this);
                    cmsf.initFramework(getApplicationContext());
                    Toast.makeText(MainActivity.this, "SlycePay Version"+SlycePay.Version(getApplicationContext()), Toast.LENGTH_SHORT).show();
                    break;
                case R.id.saleBtn:
                    mTransmessage.setText("");
                    amtstr= amountText.getText();
                    transDict =  "{\"Amount\":\""+amtstr+"\"}";
                    cmsf.processSale(getApplicationContext(),transDict);
                    break;
                case R.id.authBtn:
                    amtstr= amountText.getText();
                    mTransmessage.setText("");
                    transDict=    "{\"Amount\":\""+amtstr+"\"}";
                    cmsf.processAuthorization(getApplicationContext(),transDict);
                    break;
                case R.id.capBtn:
                    mTransmessage.setText("");
                    FragmentManager manager = getFragmentManager();
                    FileOpt file= new FileOpt(getApplication());
                    JSONArray transarray= file.getTranactions(file.getModuleValue(SlycePay.getmModule()),file.AUTH);
                    mTransArray=transarray;
                    if (transarray ==null) {
                        return;
                    }
                    TransPickerFragment dialog = TransPickerFragment.newInstance(transarray.toString());
                    dialog.setseltransCompleted(new SelectTransactionCompleted() {
                        @Override
                        public void transactionSelected(int position) {
                            Log.v("Application","postiont"+position);
                            try {
                                Log.v("Application",  mTransArray.getString(position+1));
                                String transtr=mTransArray.getString(position+1);
                                JSONObject transinfo= new JSONObject(transtr);
                                String cardinfo = transinfo.getString("Cardinfo");
                                cardinfo=cardinfo.replace("\"","\\\"");
                                transDict =    "{\"Amount\":\""+transinfo.getString("AuthorizedAmount")+"\",\"Reference\":\""+transinfo.getString("Reference")+"\",";
                                transDict=transDict +"\"Cardinfo\":\""+cardinfo+"\"}";
                                cmsf.processCapture(getApplicationContext(),transDict);
                            }catch (JSONException e)
                            {

                            }
                        }
                    });
                    dialog.show(manager,"ok");
                    break;
                case R.id.voidBtn:
                    mTransmessage.setText("");
                    transDict=    "{\"Amount\":\"1.00\",\"Reference\":\""+mAuthcodeText.getText()+"\"}";

                    FragmentManager manager2 = getFragmentManager();
                    FileOpt file2= new FileOpt(getApplication());
                    JSONArray transarray2= file2.getTranactions(file2.getModuleValue(SlycePay.getmModule()),file2.SALE);
                    mTransArray=transarray2;
                    if (transarray2 ==null) {
                        return;
                    }
                    TransPickerFragment dialog2 = TransPickerFragment.newInstance(transarray2.toString());
                    dialog2.setseltransCompleted(new SelectTransactionCompleted() {
                        @Override
                        public void transactionSelected(int position) {
                            Log.v("Application","postiont"+position);
                            try {
                                Log.v("Application",  mTransArray.getString(position+1));
                                String transtr=mTransArray.getString(position+1);
                                JSONObject transinfo= new JSONObject(transtr);
                                String cardinfo = transinfo.getString("Cardinfo");
                                cardinfo=cardinfo.replace("\"","\\\"");
                                transDict =    "{\"Amount\":\""+transinfo.getString("AuthorizedAmount")+"\",\"Reference\":\""+transinfo.getString("Reference")+"\",";
                                transDict=transDict + "\"Cardinfo\":\""+cardinfo+"\"}";
                                cmsf.processVoid(getApplicationContext(),transDict);
                            }catch (JSONException e) {

                            }
                        }
                    });
                    dialog2.show(manager2,"ok");
                    break;
                case R.id.returnBtn:
                    mTransmessage.setText("");
                    amtstr= amountText.getText();
                    transDict=    "{\"Amount\":\""+amtstr+"\",\"Reference\":\""+mAuthcodeText.getText()+"\"}";
                    cmsf.processReturn(getApplicationContext(),transDict);
                    break;
                case R.id.connectbtn:
                    final String [] pinpadarray = cmsf.getDevice();
                    // make sure change this value before run it
                    cmsf.connectDevice(pinpadarray[1]);
                    String mes = mTransmessage.getText() + "\r\n" + "connecting to pinpad";
                    mTransmessage.setText(mes);
                    break;
            }
        }
    }
}
