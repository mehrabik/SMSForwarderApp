package com.adventuresinsoftwareengineering.smsforwarder;

import android.Manifest;

import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    EditText mSmtpServer;
    EditText mSmtpPort;
    EditText mSenderEmail;
    EditText mSenderPassword;
    EditText mReceiverEmail;
    Spinner mSimSlot;
    Button updateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermissions();

        mSmtpServer = findViewById(R.id.edittext_smtp_server);
        mSmtpPort = findViewById(R.id.edittext_smtp_port);
        mSenderEmail = findViewById(R.id.edittext_sender_email);
        mSenderPassword = findViewById(R.id.edittext_sender_password);
        mReceiverEmail = findViewById(R.id.edittext_receiver_email);
        mSimSlot= findViewById(R.id.spinner_sim_slot);
        updateButton = findViewById(R.id.update_button);

        mSmtpServer.setText(PreferenceManager.getInstance().getSMTPServer());
        mSmtpPort.setText(PreferenceManager.getInstance().getSMTPPort() + "");
        mSenderEmail.setText(PreferenceManager.getInstance().getEmailUsername());
        mSenderPassword.setText(PreferenceManager.getInstance().getEmailPassword());
        mReceiverEmail.setText(PreferenceManager.getInstance().getReceiverEmail());

        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this,
                R.array.sim_slot_array,
                android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSimSlot.setAdapter(arrayAdapter);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferenceManager.getInstance().setSMTPServer(mSmtpServer.getText().toString());
                PreferenceManager.getInstance().setSMTPPort(Integer.parseInt(mSmtpPort.getText().toString()));
                PreferenceManager.getInstance().setEmailUserName(mSenderEmail.getText().toString());
                PreferenceManager.getInstance().setEmailPassword(mSenderPassword.getText().toString());
                PreferenceManager.getInstance().setReceiverEmail(mReceiverEmail.getText().toString());
                PreferenceManager.getInstance().setSimSlot(mSimSlot.getSelectedItem().toString());
            }
        });
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS}, 1);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, 2);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_NETWORK_STATE}, 2);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
    }
}