package com.example.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class CustomReceiver extends BroadcastReceiver {
    private static final String ACTION_CUSTOM_BROADCAST=
            BuildConfig.APPLICATION_ID+".ACTION_CUSTOM_BROADCAST";
    @Override
    public void onReceive(Context context, Intent intent) {
        String intenAction = intent.getAction();
        if(intenAction!=null){
            String toastMessage="";
            switch (intenAction){
                case Intent.ACTION_POWER_CONNECTED:
                    toastMessage=context.getString(R.string.power_connected);
                    break;
                case Intent.ACTION_POWER_DISCONNECTED:
                    toastMessage=context.getString(R.string.power_disconnet);
                    break;
                case ACTION_CUSTOM_BROADCAST:
                    toastMessage=intent.getStringExtra("DATA");
                    break;
            }
            Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show();
        }
    }
}