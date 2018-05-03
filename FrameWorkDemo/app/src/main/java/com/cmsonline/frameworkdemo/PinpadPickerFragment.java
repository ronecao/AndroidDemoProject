package com.cmsonline.frameworkdemo;

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

import java.util.Calendar;
import java.util.Date;

public class PinpadPickerFragment extends DialogFragment {
    public static final String ARG_PINPAD_STR = "ARG_PINPAD_STR";
    private RecyclerView mPinpadRecyclerView;
    private int mlayoutID;
    private String[] mpinpadarray;
    private static SelectPinpadCompleted mselpinpadCompleted;

    public static PinpadPickerFragment newInstance(String[] content) {
        Bundle args=new Bundle();
        args.putStringArray(ARG_PINPAD_STR,content);
        PinpadPickerFragment dpf = new PinpadPickerFragment();
        dpf.setArguments(args);
        return dpf;
    }
    public  void setPinpadSelectedListener(SelectPinpadCompleted selcompletd) {
        mselpinpadCompleted=selcompletd;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mlayoutID = R.layout.all_dial;
        mpinpadarray=getArguments().getStringArray(ARG_PINPAD_STR);
        View v = LayoutInflater.from( getActivity()).inflate( mlayoutID, null);
        mPinpadRecyclerView =(RecyclerView) v.findViewById(R.id.List_recycler_view);
        mPinpadRecyclerView. setLayoutManager( new LinearLayoutManager( getActivity()));
        mPinpadRecyclerView.setAdapter(new PinpadAdapter(mpinpadarray) );
        if(mlayoutID==R.layout.all_dial) {
            Date date = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return new AlertDialog.Builder(getActivity())
                    .setView(v)
                    .setTitle(R.string.pinpad_picker_title)
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

    private class TransHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mPinpadname;
        public TransHolder(View itemView) {
            super (itemView);
            mPinpadname=(TextView) itemView.findViewById(R.id.list_item_pinpadname);
            itemView.setOnClickListener(this);
            }
        public void bindPinpad(String pinpadname) {
            mPinpadname.setText(pinpadname);
        }
        @Override
        public void onClick(View v) {
            Log.v("TAG","view.lickec" + v.getTag());
            int position =(int) v.getTag();
            mselpinpadCompleted.PinpadSelected(position);
            dismiss();
        }
    }
    private class PinpadAdapter extends RecyclerView.Adapter<TransHolder> {
        private String[] mPinpads;
        public PinpadAdapter(String[] pinpads) {
            mPinpads =pinpads;
        }
        @Override
        public TransHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View v = layoutInflater.inflate(R.layout.list_pinpad_view,parent,false);
            return new TransHolder(v);

        }
        @Override
        public void onBindViewHolder(TransHolder holder, int position) {
            try {
                if (position<mPinpads.length) {
                    String pinpad = mPinpads[position];
                    holder.bindPinpad(pinpad);
                    holder.itemView.setTag(position);
                }
            }catch (Exception e) {
                e.printStackTrace();
                Log.e("APPlication",e.toString());
            }
        }

        public int getItemCount(){
            return mPinpads.length;
        }
    }
}
