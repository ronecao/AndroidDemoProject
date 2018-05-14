package com.slycepay.frameworkdemo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by chris on 16/12/2017.
 */

public class TransPickerFragment extends DialogFragment {
    public static final String ARG_TRANS_STR = "ARG_TRANS_STR";
    private RecyclerView mTransRecyclerView;
    private int mlayoutID;
    private String mtranscontent;
    private static SelectTransactionCompleted mseltransCompleted;
    public static TransPickerFragment newInstance(String content) {
        Bundle args = new Bundle();
        args.putString(ARG_TRANS_STR,content);
        TransPickerFragment dpf = new TransPickerFragment();
        dpf.setArguments(args);
        return dpf;
    }
    public  void setseltransCompleted(SelectTransactionCompleted selcompletd) {
        mseltransCompleted=selcompletd;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mlayoutID = R.layout.all_dial;
        mtranscontent=getArguments().getString(ARG_TRANS_STR);
        View v = LayoutInflater.from( getActivity()).inflate( mlayoutID, null);
        mTransRecyclerView =(RecyclerView) v.findViewById(R.id.List_recycler_view);
        mTransRecyclerView. setLayoutManager( new LinearLayoutManager( getActivity()));
        JSONArray jArray = null;
        try {
             jArray = new JSONArray(mtranscontent);
        }catch (JSONException e) {
            Log.v("TAG", "JSON ERROR");
        }

        mTransRecyclerView.setAdapter(new TransAdapter(jArray) );
        if(mlayoutID == R.layout.all_dial) {
            Date date = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return new AlertDialog.Builder(getActivity())
                    .setView(v)
                    .setTitle(R.string.trans_picker_title)
                    .setPositiveButton(android.R.string.ok,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

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
        public TransHolder(View itemView){
            super (itemView);
            mAmountTextView=(TextView) itemView.findViewById(R.id.list_item_trans_amountView);
            mAuthTextView=(TextView) itemView.findViewById(R.id.list_item_trans_AuthView);
            mReferenceTextView=(TextView) itemView.findViewById(R.id.list_item_trans_ReferenceView);
            mCardinfoTextView = (TextView)itemView.findViewById(R.id.list_item_trans_cardinfoView);
            itemView.setOnClickListener(this);
        }
        public void bindTrans(JSONObject trans){
            Log.v("APPlication",trans.toString());
            try {
                mAmountTextView.setText(trans.getString("AuthorizedAmount"));
                mAuthTextView.setText(trans.getString("AuthCode"));
                String cardinfostr = trans.getString("Cardinfo");
                JSONObject jsobj = new JSONObject(cardinfostr);
                String cardstr = jsobj.getString("First6")+"******"+jsobj.getString("Last4");
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
            int position = (int) v.getTag();
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
        public TransHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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
            }catch (JSONException e) {
                e.printStackTrace();
                Log.e("APPlication",e.toString());
            }
        }

        public int getItemCount(){
            return mTranses.length()-1;
        }
    }
}
