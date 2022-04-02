package com.adventuresinsoftwareengineering.smsforwarder;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {
    private final SharedPreferences mSharedPref;
    private static final PreferenceManager INSTANCE;

    private static final String KEY_SIM_SLOT = "sim_slot";
    private static final String KEY_EMAIL_USERNAME = "email_username" ;
    private static final String KEY_EMAIL_PASSWORD = "email_password" ;
    private static final String KEY_SMTP_SERVER = "smtp_server" ;
    private static final String KEY_SMTP_PORT = "smtp_port" ;
    private static final String KEY_RECEIVER_EMAIL = "receiver_email" ;

    static {
        INSTANCE = new PreferenceManager();
    }

    public static PreferenceManager getInstance() {
        return INSTANCE;
    }

    private PreferenceManager() {
        Context mContext = SMSForwarderApp.getContext();
        mSharedPref = mContext.getSharedPreferences("Default", Context.MODE_PRIVATE);
    }

    public String getSimSlot() {
        return mSharedPref.getString(KEY_SIM_SLOT, "All");
    }

    public void setSimSlot(String simSlot) {
        SharedPreferences.Editor mEditor = mSharedPref.edit();
        mEditor.putString(KEY_SIM_SLOT, simSlot);
        mEditor.commit();
    }

    public String getEmailUsername() {
        return mSharedPref.getString(KEY_EMAIL_USERNAME, "sender.email@domain.com");
    }

    public void setEmailUserName(String userName) {
        SharedPreferences.Editor mEditor = mSharedPref.edit();
        mEditor.putString(KEY_EMAIL_USERNAME, userName);
        mEditor.commit();
    }

    public String getEmailPassword() {
        return mSharedPref.getString(KEY_EMAIL_PASSWORD, "sender.email.password");
    }

    public void setEmailPassword(String password) {
        SharedPreferences.Editor mEditor = mSharedPref.edit();
        mEditor.putString(KEY_EMAIL_PASSWORD, password);
        mEditor.commit();
    }

    public String getSMTPServer() {
        return mSharedPref.getString(KEY_SMTP_SERVER, "smtp.gmail.com");
    }

    public void setSMTPServer(String server) {
        SharedPreferences.Editor mEditor = mSharedPref.edit();
        mEditor.putString(KEY_SMTP_SERVER, server);
        mEditor.commit();
    }

    public int getSMTPPort() {
        return mSharedPref.getInt(KEY_SMTP_PORT, 587);
    }

    public void setSMTPPort(int port) {
        SharedPreferences.Editor mEditor = mSharedPref.edit();
        mEditor.putInt(KEY_SMTP_PORT, port);
        mEditor.commit();
    }

    public String getReceiverEmail() {
        return mSharedPref.getString(KEY_RECEIVER_EMAIL, "receiver.email@domain.com");
    }

    public void setReceiverEmail(String email) {
        SharedPreferences.Editor mEditor = mSharedPref.edit();
        mEditor.putString(KEY_RECEIVER_EMAIL, email);
        mEditor.commit();
    }
}

