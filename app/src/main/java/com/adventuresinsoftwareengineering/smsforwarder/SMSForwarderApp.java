package com.adventuresinsoftwareengineering.smsforwarder;

import android.app.Application;
import android.content.Context;

public class SMSForwarderApp extends Application {
    private static SMSForwarderApp INSTANCE;

    public SMSForwarderApp() {
        INSTANCE = this;
    }

    public static Context getContext() {
        return INSTANCE;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
