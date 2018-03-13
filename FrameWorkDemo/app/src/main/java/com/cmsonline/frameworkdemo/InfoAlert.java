package com.cmsonline.frameworkdemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by chris on 18/09/2017.
 */

public class InfoAlert extends DialogFragment {
    public static final String ARG_MSG="MESSAGE";
    public static InfoAlert newInstance(String msg) {
        Bundle args=new Bundle();
        args.putSerializable(ARG_MSG,msg);
        InfoAlert dpf = new InfoAlert();
        dpf.setArguments(args);

        return dpf;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String message= (String)getArguments().getSerializable(ARG_MSG);
        View v = LayoutInflater.from( getActivity()).inflate(R.layout.infor_alert, null);
        TextView msgtext = v.findViewById(R.id.msgcontent);

        msgtext.setText(message);
            return new AlertDialog.Builder(getActivity())
                    .setView(v)
                    .setTitle("Alert")
                    .setPositiveButton(android.R.string.ok,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                    .create();
        }


}
