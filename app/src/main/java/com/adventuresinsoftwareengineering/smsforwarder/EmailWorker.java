package com.adventuresinsoftwareengineering.smsforwarder;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;

public class EmailWorker extends Worker {
    public EmailWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        GMail androidEmail = new GMail(getInputData().getString("fromEmail"),
                getInputData().getString("fromPassword"),
                getInputData().getString("toEmail"),
                getInputData().getString("emailSubject"),
                getInputData().getString("emailBody"));
        try {
            androidEmail.createEmailMessage();
            androidEmail.sendEmail();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return Result.success();
    }
}
