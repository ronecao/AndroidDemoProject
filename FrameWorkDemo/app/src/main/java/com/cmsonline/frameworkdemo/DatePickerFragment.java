package com.cmsonline.frameworkdemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;

/**
 * Created by chris on 16/12/2017.
 */

public class DatePickerFragment extends DialogFragment {

    public static final String ARG_TRANS_STR = "ARG_TRANS_STR";
    public static final String EXTRA_DATE ="EXTRA_DATE";
    //private Crime mCrime;
    private RecyclerView mTransRecyclerView;
    private int mlayoutID;
    private String mtranscontent;
    private static SelectTransactionCompleted mseltransCompleted;

    public static DatePickerFragment newInstance(String content) {
        Bundle args=new Bundle();
        args.putString(ARG_TRANS_STR,content);
        DatePickerFragment dpf = new DatePickerFragment();
        dpf.setArguments(args);


        return dpf;
    }
    public  void setseltransCompleted(SelectTransactionCompleted selcompletd)
    {
        mseltransCompleted=selcompletd;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        mlayoutID = R.layout.date_dial;//(int)getArguments().getSerializable(ARG_LAYOUT_ID);
        mtranscontent=getArguments().getString(ARG_TRANS_STR);
        View v = LayoutInflater.from( getActivity()).inflate( mlayoutID, null);

        mTransRecyclerView =(RecyclerView) v.findViewById(R.id.crime_recycler_view);
        //mCrimeRecyclerView.setLayoutManager(getActivity());
        mTransRecyclerView. setLayoutManager( new LinearLayoutManager( getActivity()));
        JSONObject json_test;
        JSONArray jArray=null;
        try {
             /*json_test = new JSONObject(mtranscontent);
             jArray=json_test.getJSONArray("data");*/
             jArray= new JSONArray(mtranscontent);
        }catch (JSONException e)
        {
            Log.v("TAG", "JSON ERROR");
        }

        mTransRecyclerView.setAdapter(new TransAdapter(jArray) );
        //mCrime=CrimeLab.get(getActivity()).getCrime(crimeid);

        if(mlayoutID==R.layout.date_dial) {
            Date date = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            //View v = LayoutInflater. from( getActivity()) .inflate( R. layout. dialog_ date, null);
            //mDatePicker = (DatePicker) v.findViewById(R.id.dialog_date_date_picker);
            //mDatePicker.init(year, month, day, null);

            return new AlertDialog.Builder(getActivity())
                    .setView(v)
                    .setTitle(R.string.date_picker_title)
                    .setPositiveButton(android.R.string.ok,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    /*int year = mDatePicker.getYear();
                                    int month = mDatePicker.getMonth();
                                    int day = mDatePicker.getDayOfMonth();
                                    Date date = new GregorianCalendar(year, month, day).getTime();
                                    sendResult(Activity.RESULT_OK, date);*/
                                }
                            })
                    .create();
        }

        return null;

    }


    private class TransHolder extends RecyclerView.ViewHolder implements View.OnClickListener{//one objct content
        private TextView mAmountTextView;
        private TextView mAuthTextView;
        private TextView mReferenceTextView;
        private TextView mCardinfoTextView;
        //private Crime mCrime;
        public TransHolder(View itemView){
            super (itemView);
            mAmountTextView=(TextView) itemView.findViewById(R.id.list_item_trans_amountView);
            mAuthTextView=(TextView) itemView.findViewById(R.id.list_item_trans_AuthView);
            mReferenceTextView=(TextView) itemView.findViewById(R.id.list_item_trans_ReferenceView);
            mCardinfoTextView = (TextView)itemView.findViewById(R.id.list_item_trans_cardinfoView);

            itemView.setOnClickListener(this);

        }
        public void bindTrans(JSONObject trans){
           // mCrime =crime;
            //CharSequence cs = DateFormat.format("EEEE,MM dd, yyyy h:mmaa",mCrime.getDate());
            Log.v("APPlication",trans.toString());
            try {
                mAmountTextView.setText(trans.getString("AuthorizedAmount"));
                mAuthTextView.setText(trans.getString("AuthCode"));
                String cardinfostr=trans.getString("Cardinfo");
                JSONObject jsobj=new JSONObject(cardinfostr);
                String cardstr=jsobj.getString("First6")+"******"+jsobj.getString("Last4");
                mCardinfoTextView.setText(cardstr);
                mReferenceTextView.setText(trans.getString("Reference"));



            }catch (JSONException e)
            {

            }


        }
        @Override
        public void onClick(View v)
        {

            Log.v("TAG","view.lickec" + v.getTag());
            //sendResult()
            int position =(int) v.getTag();
            mseltransCompleted.transactionSelected(position);
            dismiss();

        }
    }
    private class TransAdapter extends RecyclerView.Adapter<TransHolder> {
        private JSONArray mTranses;
        public TransAdapter(JSONArray transes)
        {
            mTranses =transes;
        }
        @Override
        public TransHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View v = layoutInflater.inflate(R.layout.list_itme_view,parent,false);
            return new TransHolder(v);

        }
        @Override
        public void onBindViewHolder(TransHolder holder,int position)
        {
            try {
                if (position<mTranses.length()-1) {
                    String oneTrans = mTranses.getString(position+1);
                    //oneTrans=oneTrans.replace("\\","");
                    JSONObject crime = new JSONObject(oneTrans);
                    holder.bindTrans(crime);
                    holder.itemView.setTag(position);
                }
            }catch (JSONException e)
            {
                e.printStackTrace();
                Log.e("APPlication",e.toString());
            }


        }

        public int getItemCount(){
            return mTranses.length()-1;
        }
    }
}
