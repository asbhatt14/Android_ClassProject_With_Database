package com.example.ankur.agencyapp.Controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.example.ankur.agencyapp.DAO.AgentDAO;
import com.example.ankur.agencyapp.Model.Agents;

import java.util.ArrayList;

/**
 * Created by ALONE on 8/12/2017.
 */

public class SMSBroadCastReceiver extends BroadcastReceiver {

    final SmsManager sms = SmsManager.getDefault();
    public static final String SMS_BUNDLE = "pdus";

    public void onReceive(Context context, Intent intent) {
        final Bundle bundle = intent.getExtras();
        try {

            if (bundle != null) {

                final Object[] pdusObj = (Object[]) bundle.get("pdus");

                for (int i = 0; i < pdusObj.length; i++) {

                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();

                    String senderNum = phoneNumber;
                    String message = currentMessage.getDisplayMessageBody();

                    Log.i("SmsReceiver", "senderNum: "+ senderNum + "; message: " + message);

                    AgentDAO ado=new AgentDAO(context);
                    ArrayList<Agents> data= (ArrayList<Agents>) ado.dbSearch();

                    for(int j=0;j<data.size();j++)
                    {
                        if(senderNum.compareTo(data.get(j).getAgeentPhoneNumber())==0)
                        {
                            Intent i1=new Intent(context,SplashActivity.class);
                            context.startActivity(i1);
                        }
                    }
                    // Show alert
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context, "senderNum: "+ senderNum + ", message: " + message, duration);
                    toast.show();

                } // end for loop
            } // bundle is null

        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" +e);

        }
    }
}
