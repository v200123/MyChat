package com.coffee_just.mychat.utils;

import android.content.Context;
import android.widget.Toast;

public class MyToast  {

    public static Toast OutToast(Context context,String Msg)
    {
        return Toast.makeText(context,Msg,Toast.LENGTH_SHORT);
    }

}
