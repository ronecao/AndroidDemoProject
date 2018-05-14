package com.slycepay.frameworkdemo;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Display;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;

/**
 * Created by chris on 14/12/2017.
 */

public class FileOpt {
    public final int SALE=1;
    public final int VOID=2;
    public final int RETURN=3;
    public final int AUTH=4;
    public final int CAPTURE=5;

    public final int M10=1;
    public final int A200=2;
    public final int MP200=3;

    private String M10SALE="M10SALE";
    private String M10AUTH="M10AUTH";

    private String A200SALE="A200SALE";
    private String A200AUTH="A200AUTH";

    private String MP200SALE="MP200SALE";
    private String MP200AUTH="MP200AUTH";

    private Context mContext;
    public FileOpt(Context c)
    {
        mContext =c;
    }
    public int addTransaction(int Module,String content,int transtype)
    {
        String filename = null;
        String filecontent = null;
        JSONObject json_result =null;
        switch (Module)
        {
            case M10:
                switch (transtype) {
                    case SALE:
                        filename = M10SALE;
                        break;
                    case AUTH:
                        filename = M10AUTH;
                        break;
                }
                break;
            case A200:
                switch (transtype)
                {
                    case SALE:
                        filename = A200SALE;
                        break;
                    case AUTH:
                        filename = A200AUTH;
                        break;
                }
                break;
            case MP200:
                switch (transtype) {
                    case SALE:
                        filename = MP200SALE;
                        break;
                    case AUTH:
                        filename = MP200AUTH;
                        break;
                }
                break;

        }
        if (filename==null) {
            return -1;
        }
        filecontent = read(filename);

        if (filecontent == "FileNotFound") {
            filecontent="{ \"data\":[{\"element\":\"input\"}]}";
        }
        else if(filecontent==null) {
            return -3;
        }

        try {
            json_result = new JSONObject(filecontent);
        }catch (Exception e)
        {
            return -2;
        }
        try{
            JSONArray transarray = json_result.getJSONArray("data");
            transarray.put(content);
            json_result.put("data",transarray);
            int saveresult = save(filename,json_result.toString());
            if (saveresult != 0)
            {
                return -4;
            }

        }catch (JSONException e) {
            return -3;
        }



        return 0;
    }

    public String getTransactions(int Module,int index,int transtype)
    {
        String filename = null;
        String filecontent = null;
        JSONObject json_result = null;
        switch (Module)
        {
            case M10:
                switch (transtype) {
                    case SALE:
                        filename = M10SALE;
                        break;
                    case AUTH:
                        filename = M10AUTH;
                        break;
                }
                break;
            case A200:
                switch (transtype)
                {
                    case SALE:
                        filename = A200SALE;
                        break;
                    case AUTH:
                        filename = A200AUTH;
                        break;
                }
                break;
            case MP200:
                switch (transtype)
                {
                    case SALE:
                        filename = MP200SALE;
                        break;
                    case AUTH:
                        filename = MP200AUTH;
                        break;
                }
                break;

        }
        if (filename == null) {
            return null;
        }
        filecontent = read(filename);

        if (filecontent == "FileNotFound" || filecontent == null) {
            return null;
        }


        try {
            json_result = new JSONObject(filecontent);
        }catch (Exception e)
        {
            return null;
        }
        try{
            JSONArray transarray = json_result.getJSONArray("data");

         return  transarray.get(index).toString();

        }catch (JSONException e) {
            return null;
        }




    }
    public JSONArray getTranactions(int Module,int transtype)
    {
        String filename = null;
        String filecontent;
        JSONObject json_result;
        switch (Module) {
            case M10:
                switch (transtype) {
                    case SALE:
                        filename = M10SALE;
                        break;
                    case AUTH:
                        filename = M10AUTH;
                        break;
                }
                break;
            case A200:
                switch (transtype) {
                    case SALE:
                        filename = A200SALE;
                        break;
                    case AUTH:
                        filename = A200AUTH;
                        break;
                }
                break;
            case MP200:
                switch (transtype) {
                    case SALE:
                        filename = MP200SALE;
                        break;
                    case AUTH:
                        filename = MP200AUTH;
                        break;
                }
                break;

        }
        if (filename==null) {
            return null;
        }
        filecontent = read(filename);
        if (filecontent ==" FileNotFound" || filecontent == null) {
            return null;
        }
        try {
            json_result = new JSONObject(filecontent);
        }catch (Exception e) {
            return null;
        }
        try{
            JSONArray transarray = json_result.getJSONArray("data");

            return  transarray;

        }catch (JSONException e) {
            return null;
        }
    }
    public int removeTransaction(int Module,int index,int transtype) {
        String filename=null;
        String filecontent;
        JSONObject json_result;
        switch (Module) {
            case M10:
                switch (transtype) {
                    case SALE:
                        filename = M10SALE;
                        break;
                    case AUTH:
                        filename = M10AUTH;
                        break;
                }
                break;
            case A200:
                switch (transtype) {
                    case SALE:
                        filename = A200SALE;
                        break;
                    case AUTH:
                        filename = A200AUTH;
                        break;
                }
                break;
            case MP200:
                switch (transtype) {
                    case SALE:
                        filename = MP200SALE;
                        break;
                    case AUTH:
                        filename = MP200AUTH;
                        break;
                }
                break;

        }
        if (filename==null) {
            return -1;
        }
        filecontent=read(filename);

        if (filecontent=="FileNotFound") {
            return -4;
        }
        else if(filecontent==null) {
            return -5;
        }

        try {
            json_result = new JSONObject(filecontent);
        }catch (Exception e) {
            return -2;
        }
        try{
            JSONArray transarray= json_result.getJSONArray("data");
            transarray.remove(index);
            json_result.put("data",transarray);
            int saveresult=save(filename,json_result.toString());
            if (saveresult!=0) {
                return -4;
            }

        }catch (JSONException e) {
            return -3;
        }
        return 0;
    }
    public int deleteFile(int Module,int transtype) {
        String filename=null;
        String filecontent=null;
        JSONObject json_result =null;
        switch (Module) {
            case M10:
                switch (transtype) {
                    case SALE:
                        filename = M10SALE;
                        break;
                    case AUTH:
                        filename = M10AUTH;
                        break;
                }
                break;
            case A200:
                switch (transtype) {
                    case SALE:
                        filename = A200SALE;
                        break;
                    case AUTH:
                        filename = A200AUTH;
                        break;
                }
                break;
            case MP200:
                switch (transtype) {
                    case SALE:
                        filename = MP200SALE;
                        break;
                    case AUTH:
                        filename = MP200AUTH;
                        break;
                }
                break;

        }
        if (filename==null) {
            return -1;
        }
        filecontent=read(filename);
        if (filecontent=="FileNotFound") {
            return -4;
        }
        else if(filecontent==null) {
            return -5;
        }
        try {
            json_result = new JSONObject(filecontent);
        }catch (Exception e) {
            return -2;
        }
        try{
            JSONArray transarray= json_result.getJSONArray("data");
            for(int index=0;index<transarray.length();index++)
            transarray.remove(index);
            json_result.put("data",transarray);
            int saveresult=save(filename,json_result.toString());
            if (saveresult!=0) {
                return -4;
            }
        }catch (JSONException e) {
            return -3;
        }
        return 0;
    }
    public int getModuleValue(String name)
    {
        switch (name)
        {
            case "B200":
            case "A200":
                return A200;
            case "M10":
                return M10;
            case "MP200":
                return MP200;
        }
        return 0;
    }
    private   int save(String fileName,String content ) {
    try {
        FileOutputStream outputStream = mContext.openFileOutput(fileName,
                Activity.MODE_PRIVATE);
        outputStream.write(content.getBytes());
        outputStream.flush();
        outputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return -1;
        } catch (IOException e) {
            e.printStackTrace();
            return -2;
        }
        return 0;

    }

    private String read(String fileName) {
        try {
            FileInputStream inputStream = mContext.openFileInput(fileName);
            byte[] bytes = new byte[1024*1000];
            ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
            while (inputStream.read(bytes) != -1) {
                arrayOutputStream.write(bytes, 0, bytes.length);
            }
            inputStream.close();
            arrayOutputStream.close();
            String content = new String(arrayOutputStream.toByteArray());
            return content;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "FileNotFound";
        } catch (IOException e) {
            e.printStackTrace();
            return "IOERROR";
        }


    }



}

