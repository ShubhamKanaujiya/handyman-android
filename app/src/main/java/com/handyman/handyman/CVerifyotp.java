package com.handyman.handyman;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CVerifyotp extends AppCompatActivity {
    String otp;
    EditText cverifyotp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cverifyotp);
        cverifyotp=(EditText)findViewById(R.id.cverifyotp);
        Intent intent=getIntent();
        otp= intent.getStringExtra("otp");
        //Toast.makeText(this, ""+otp, Toast.LENGTH_LONG).show();
    }
    public void cverifyotp(View view) {
        String input=cverifyotp.getText().toString().trim();

             if(input.isEmpty())
             {
            cverifyotp.setError("Empty");
            cverifyotp.requestFocus();
            }
            if (input.equals(otp))
            {
                Intent intent=new Intent(CVerifyotp.this,Newpassword.class);
                startActivity(intent);
            }
            else
            {
                Toast.makeText(this, "Not Match", Toast.LENGTH_SHORT).show();
            }


    }
}
