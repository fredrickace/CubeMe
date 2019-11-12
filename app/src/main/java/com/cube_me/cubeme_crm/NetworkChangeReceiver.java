package com.cube_me.cubeme_crm;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;


/**
 * Created by FredrickCyril on 10/9/16.
 */

public class NetworkChangeReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {


        ObservableObject.getInstance().updateValue(intent);

//        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
//        if(networkInfo != null && networkInfo.isConnectedOrConnecting()){
//            BaseActivity.networkIsConnected = true;
////            networkChangeCommunicator.onNetworkConnected();
//            Toast.makeText(context, "NetWork is Connected", Toast.LENGTH_SHORT).show();
//        }
//        else {
//            BaseActivity.networkIsConnected = false;
////            networkChangeCommunicator.onNetworkDisconnected();
//            Toast.makeText(context, "Network Unavailable. Switching to Offline Mode", Toast.LENGTH_SHORT).show();
//        }


    }



}
