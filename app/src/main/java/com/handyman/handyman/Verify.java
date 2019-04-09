package com.handyman.handyman;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class Verify extends AppCompatActivity {
    EditText et_otp;
    String otp,mobile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
        et_otp=(EditText)findViewById(R.id.et_otp);
        Intent intent=getIntent();
        otp= intent.getStringExtra("otp");
        mobile=intent.getStringExtra("moblie");
    }
    public void veri(View view)
    {
        String o;
        o=et_otp.getText().toString().trim();
        if(o.isEmpty())
        {
            et_otp.setError("Enter OTP");
            et_otp.requestFocus();
        }
        else if(o.equals(otp))
        {

            Intent intent=new Intent(Verify.this,Cregister.class);
            intent.putExtra("mobile",mobile);
            startActivity(intent);
    }
    else
        {
            Toast.makeText(this, "Enter Correct OTP", Toast.LENGTH_SHORT).show();
        }

    }
}
