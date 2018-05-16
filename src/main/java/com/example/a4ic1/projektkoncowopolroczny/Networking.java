package com.example.a4ic1.projektkoncowopolroczny;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Mirus on 06.12.2016.
 */
public class Networking {

    static public boolean NetworkStatus(Context context){
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null || !networkInfo.isConnected()) {
            return false;
        }
        else{
            return true;
        }
    }
}
