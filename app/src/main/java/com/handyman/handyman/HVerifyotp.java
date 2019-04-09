package com.handyman.handyman;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class HVerifyotp extends AppCompatActivity {
    String otp;
    EditText hverifyotp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hverifyotp);
        hverifyotp=(EditText)findViewById(R.id.hverifyotp);
        Intent intent=getIntent();
        otp= intent.getStringExtra("otp");
    }
    public void hverifyotp(View view){
        String input=hverifyotp.getText().toString().trim();

        if(input.isEmpty())
        {
            hverifyotp.setError("Empty");
            hverifyotp.requestFocus();
        }
        if (input.equals(otp))
        {
            Intent intent=new Intent(HVerifyotp.this,HNewPassword.class);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(this, "Not Match", Toast.LENGTH_SHORT).show();
        }

    }

}
