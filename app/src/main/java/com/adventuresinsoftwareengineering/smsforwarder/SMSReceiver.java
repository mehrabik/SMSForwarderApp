package com.adventuresinsoftwareengineering.smsforwarder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;

import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import java.util.Set;

public class SMSReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION.equals(intent.getAction())) {
            for (SmsMessage smsMessage : Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                String messageBody = smsMessage.getMessageBody();
                String phoneNumber = smsMessage.getOriginatingAddress();

                String receivedSim = detectSim(intent.getExtras());
                String targetSim = PreferenceManager.getInstance().getSimSlot();

                if(targetSim.equals("All") || receivedSim.equals(targetSim)) {
                    String fromEmail = PreferenceManager.getInstance().getEmailUsername();
                    String fromPassword = PreferenceManager.getInstance().getEmailPassword();
                    String toEmail = PreferenceManager.getInstance().getReceiverEmail();
                    String emailSubject = "SMSReceived";
                    String emailBody = "From: " + phoneNumber + "\n\nSim: " + receivedSim + "\n\nContent: " + messageBody;

                    Data myData = new Data.Builder()
                            .putString("fromEmail", fromEmail)
                            .putString("fromPassword", fromPassword)
                            .putString("toEmail", toEmail)
                            .putString("emailSubject", emailSubject)
                            .putString("emailBody", emailBody)
                            .build();

                    Constraints constraints = new Constraints.Builder()
                            .setRequiredNetworkType(NetworkType.CONNECTED)
                            .build();

                    WorkRequest mWorkRequest = new OneTimeWorkRequest.Builder(EmailWorker.class)
                            .setConstraints(constraints)
                            .setInputData(myData)
                            .build();

                    WorkManager.getInstance(SMSForwarderApp.getContext()).enqueue(mWorkRequest);
                }
            }
        }
    }

    private String detectSim(Bundle bundle) {
        int slot = -1;
        Set<String> keySet = bundle.keySet();
        for (String key : keySet) {
            switch (key) {
                case "phone":
                    slot = bundle.getInt("phone", -1);
                    break;
                case "slot":
                    slot = bundle.getInt("slot", -1);
                    break;
                case "simId":
                    slot = bundle.getInt("simId", -1);
                    break;
                case "simSlot":
                    slot = bundle.getInt("simSlot", -1);
                    break;
                case "slot_id":
                    slot = bundle.getInt("slot_id", -1);
                    break;
                case "simnum":
                    slot = bundle.getInt("simnum", -1);
                    break;
                case "slotId":
                    slot = bundle.getInt("slotId", -1);
                    break;
                case "slotIdx":
                    slot = bundle.getInt("slotIdx", -1);
                    break;
                default:
                    break;
            }
        }

        if (slot == 0) {
            return "SIM1";
        } else if (slot == 1) {
            return "SIM2";
        } else {
            return "All";
        }
    }
}