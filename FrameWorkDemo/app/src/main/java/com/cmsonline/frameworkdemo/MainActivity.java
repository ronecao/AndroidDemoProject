package com.cmsonline.frameworkdemo;

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

import com.cmsonline.framework.EMVIOFramework;
import com.cmsonline.framework.EMVIOListener;
import com.cmsonline.framework.ConfirmCard;
import com.cmsonline.framework.EMVIOUtility;
import com.cmsonline.framework.HTTPPostThread;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

public class MainActivity extends AppCompatActivity implements EMVIOListener,SelectTransactionCompleted {

    private TextView mTextMessage;
    private  MainActivity mMainActivity;
    private EditText amountText;
    private EditText mAuthcodeText;
    private EditText mAPITokenText;
    private Button mSelectionBtn;
    private Button mConnectBtn;
    private Button mUpdateBtn;
    private static final String TAG ="APPLICTION";
    private JSONArray mTransArray;
    EMVIOFramework cmsf;
    public TextView mTransmessage;


    @Override
    public void initCompleted(String result){
        Log.v("APPLICATION", "Completed" + result);
        runOnUiThread(new Runnable()
        {

            public void run()

            {

                mTransmessage.setText("InitCompleted");

            }



        });


    }
    @Override
    public void transactionSelected(int position)
    {

    }


    @Override
    public void pinPadConnectionCompleted(String Result){
        Log.v("APPLICATION",Result);
        //Toast.makeText(getApplicationContext(), "Connection completed"+Result,
                //Toast.LENGTH_SHORT).show();
        final String updatestr=Result;
        runOnUiThread(new Runnable()
        {

            public void run()
            {

                String mes = mTransmessage.getText() + "\r\n" + updatestr;
                mTransmessage.setText(mes);
                Log.v("APPLICATION", updatestr);

            }



        });
    }
    @Override
    public void pinPadDisattached(){

    }

    public void verifySignature(boolean verify)
    {
        cmsf.signatureVerified(true);
    }
    public void voiceRefferal(boolean verify,String phone)
    {
        cmsf.phoneReferral(true,"123456");
    }
    public void TMSupdateCompleted(String Result)
    {
        final String updatestr=Result;
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
    public void confirmCardinfo(String msg)
    {
        Log.v("APPLICATION", msg);
        cmsf.confirmCardinfoResult(ConfirmCard.CMS_CONFIRMED);

    }


    @Override
    public void transactionSaleCompleted(String resultDict){
        Log.v("APPLICATION", resultDict);
        Log.v("APPLICATION", "length "+resultDict.length());
        JSONObject json_result;
        FragmentManager manager = getFragmentManager();
        InfoAlert alert= InfoAlert.newInstance(resultDict);
        //alert.setTargetFragment(MainActivity.this,null);
        alert.show(manager,"TEST");

        int modulevalue;




        try {
            FileOpt file = new FileOpt(getApplication());
            json_result = new JSONObject(resultDict);
            mAuthcodeText.setText(json_result.getString("Reference"));
            modulevalue=file.getModuleValue(EMVIOFramework.getmModule());
            file.addTransaction(modulevalue,resultDict,file.SALE);
        }catch (Exception e)
        {

        }

    }
    @Override
    public void selectAID(String [] AIDName){

    }
    @Override
   public void transAuthorizationCompleted(String resultDict){
        Log.v("APPLICATION", resultDict);
        FragmentManager manager = getFragmentManager();
        InfoAlert alert= InfoAlert.newInstance(resultDict);
        //alert.setTargetFragment(MainActivity.this,null);
        alert.show(manager,"Authroization");
        JSONObject json_result;
        int modulevalue;
        try {
            FileOpt file = new FileOpt(getApplication());
            json_result = new JSONObject(resultDict);
            mAuthcodeText.setText(json_result.getString("Reference"));
            modulevalue=file.getModuleValue(EMVIOFramework.getmModule());
            file.addTransaction(modulevalue,resultDict,file.AUTH);
        }catch (Exception e)
        {

        }

   }
   @Override
   public void transactionCaptureCompleted(String resultDict)
   {
       Log.v("APPLICATION", resultDict);
       FragmentManager manager = getFragmentManager();
       InfoAlert alert= InfoAlert.newInstance(resultDict);
       //alert.setTargetFragment(MainActivity.this,null);
       alert.show(manager,"Authroization");
       JSONObject json_result;
       try {
           json_result = new JSONObject(resultDict);
           mAuthcodeText.setText(json_result.getString("Reference"));
       }catch (Exception e)
       {

       }
   }
   @Override
    public void transactionReturnCompleted(String resultDict){
        Log.v("APPLICATION", resultDict);
        Log.v("APPLICATION", "length "+resultDict.length());
        JSONObject json_result;
        FragmentManager manager = getFragmentManager();
        InfoAlert alert= InfoAlert.newInstance(resultDict);
        //alert.setTargetFragment(MainActivity.this,null);
        alert.show(manager,"TEST");

    }
    public void transactionVoidCompleted(String resultDict){
        Log.v("APPLICATION", resultDict);
        Log.v("APPLICATION", "length "+resultDict.length());
        JSONObject json_result;
        FragmentManager manager = getFragmentManager();
        InfoAlert alert= InfoAlert.newInstance(resultDict);
        //alert.setTargetFragment(MainActivity.this,null);
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
        mMainActivity=this;
        mTextMessage = (TextView) findViewById(R.id.message);
        mTransmessage=(TextView) findViewById(R.id.messagetxtView);
        //BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
       // navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        setupGUI();
    }

    private void setupGUI()
    {
        if(Build.VERSION.SDK_INT>=23) {

            requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO,Manifest.permission.BLUETOOTH_ADMIN,Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_PHONE_STATE}, 0);
            //            this.b("android.permission.BLUETOOTH");

        }

        Button initBtn =  (Button)findViewById(R.id.initBtn);
        initBtn.setText(R.string.init_button);
        //initBtn.setPadding(50,50,50,50);

        amountText= (EditText) findViewById(R.id.amountInput);
        mAuthcodeText = (EditText) findViewById(R.id.authCodeEditText);

        ClickListener l = new ClickListener();
        initBtn.setOnClickListener(l);

        Button saleBtn=(Button)findViewById(R.id.saleBtn);
        saleBtn.setOnClickListener(l);

        Button capBtn=(Button)findViewById(R.id.capBtn);
        capBtn.setOnClickListener(l);

        Button authBtn=(Button)findViewById(R.id.authBtn);
        authBtn.setOnClickListener(l);
        Button voidBtn=(Button)findViewById(R.id.voidBtn);
        voidBtn.setOnClickListener(l);
        Button returnBtn=(Button)findViewById(R.id.returnBtn);
        returnBtn.setOnClickListener(l);
        mSelectionBtn = (Button) findViewById(R.id.selbtn);
        mSelectionBtn.setOnClickListener(l);

        mConnectBtn = (Button) findViewById(R.id.connectbtn);
        mConnectBtn.setOnClickListener(l);

        mUpdateBtn = (Button) findViewById(R.id.TMSbtn);
        mUpdateBtn.setOnClickListener(l);
        mAPITokenText = (EditText) findViewById(R.id.APIKey);

    }

    class ClickListener implements View.OnClickListener{
        Editable amtstr;
        String transDict;
        @Override
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.initBtn:

                    String amounterString = "123";
                    double amtdouble= (double)Integer.parseInt(amounterString)/100;
                    amounterString= String.valueOf(amtdouble);
                    Log.v("appliction",amounterString);
                    mAPITokenText.setText("3fyl02KZBOERy7yEd6SkWqbuYmGqHQ2T");//cardflight
                    cmsf= new EMVIOFramework(getApplicationContext(),mAPITokenText.getText().toString(),"",MainActivity.this);
                    break;
                case R.id.saleBtn:
                    double dd;
                     amtstr= amountText.getText();
                    try{
                        dd=Double.valueOf(amtstr.toString());


                    }catch (Exception e)
                    {
                        FragmentManager manager = getFragmentManager();
                        InfoAlert alert= InfoAlert.newInstance("Ammount format error");
                        //alert.setTargetFragment(MainActivity.this,null);
                        alert.show(manager,"TEST");

                        Log.v("APPLICATION", "dd format error");
                        return;
                    }

                    transDict =  "{\"Amount\":\""+amtstr+"\",";
                    //transDict =  "{\"Amount\":\""+".00"+"\",";
                    transDict = transDict + "\"Tender\":\"Credit\"}";

                    cmsf.processSale(getApplicationContext(),transDict);
                    break;
                case R.id.authBtn:
                    amtstr= amountText.getText();
                    try{
                        dd=Double.valueOf(amtstr.toString());


                    }catch (Exception e)
                    {
                        FragmentManager manager = getFragmentManager();
                        InfoAlert alert= InfoAlert.newInstance("Ammount format error");
                        //alert.setTargetFragment(MainActivity.this,null);
                        alert.show(manager,"TEST");

                        Log.v("APPLICATION", "dd format error");
                        return;
                    }

                    transDict=    "{\"Amount\":\""+amtstr+"\",";

                    transDict=transDict + "\"Tender\":\"Credit\"}";

                    cmsf.processAuthorization(getApplicationContext(),transDict);
                    break;
                case R.id.capBtn:
                    /*transDict=    "{\"Amount\":\"1.00\",\"Reference\":\""+mAuthcodeText.getText()+"\",";
                    transDict=transDict + "\"Tender\":\"Credit\"}";
                    cmsf.processCapture(getApplicationContext(),transDict);*/


                    //cmsf.processVoid(getApplicationContext(),transDict);
                    FragmentManager manager = getFragmentManager();
                    FileOpt file= new FileOpt(getApplication());
                    JSONArray transarray= file.getTranactions(file.getModuleValue(EMVIOFramework.getmModule()),file.AUTH);
                    mTransArray=transarray;
                    if (transarray ==null)
                    {
                        return;
                    }
                    DatePickerFragment dialog = DatePickerFragment.newInstance(transarray.toString());
                    //dialog.setTargetFragment(get,0);//for get result from fragment
                    //dialog.sett
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
                                //transDict=transDict + "}";
                                transDict=transDict + "\"Tender\":\"Credit\",\"Cardinfo\":\""+cardinfo+"\"}";


                                cmsf.processCapture(getApplicationContext(),transDict);
                            }catch (JSONException e)
                            {

                            }
                        }
                    });
                    dialog.show(manager,"ok");


                    break;
                case R.id.voidBtn:
                    //mAuthcodeText.setText("388321a0-7648-4630-8b65-e813c33ad12a");
                    transDict=    "{\"Amount\":\"1.00\",\"Reference\":\""+mAuthcodeText.getText()+"\",";
                    transDict=transDict + "\"Tender\":\"Credit\"}";
                    //cmsf.processVoid(getApplicationContext(),transDict);
                    FragmentManager manager2 = getFragmentManager();
                    FileOpt file2= new FileOpt(getApplication());
                    JSONArray transarray2= file2.getTranactions(file2.getModuleValue(EMVIOFramework.getmModule()),file2.SALE);
                    mTransArray=transarray2;
                    if (transarray2 ==null)
                    {
                        return;
                    }
                    DatePickerFragment dialog2 = DatePickerFragment.newInstance(transarray2.toString());
                    //dialog.setTargetFragment(get,0);//for get result from fragment
                    //dialog.sett
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
                                transDict=transDict + "\"Tender\":\"Credit\",\"Cardinfo\":\""+cardinfo+"\"}";


                                cmsf.processVoid(getApplicationContext(),transDict);
                            }catch (JSONException e)
                            {

                            }
                        }
                    });
                    dialog2.show(manager2,"ok");
                    break;
                case R.id.returnBtn:
                    amtstr= amountText.getText();
                    transDict=    "{\"Amount\":\""+amtstr+"\",\"Reference\":\""+mAuthcodeText.getText()+"\",";
                    transDict=transDict + "\"Tender\":\"Credit\"}";
                    cmsf.processReturn(getApplicationContext(),transDict);
                    break;
                case R.id.selbtn:
                    for ( String name : cmsf.getPinpads())
                    {
                        Log.v("Application","pinpad:"+name);

                            cmsf.selectPinpad(name);
                            //break;

                    }
                    break;
                case R.id.connectbtn:
                    cmsf.connectPinpad();
                    break;
                case R.id.TMSbtn:
                    cmsf.TMSupdate();
                    break;
            }
        }
    }
}
