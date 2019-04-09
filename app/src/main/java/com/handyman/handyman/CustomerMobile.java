package com.handyman.handyman;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.katepratik.msg91api.MSG91;

public class CustomerMobile extends AppCompatActivity {
    EditText cust;
    CaptchaGenerator captchaGenerator;
    MSG91 msg91;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_mobile);
        cust = (EditText) findViewById(R.id.cmobile);
        captchaGenerator = new CaptchaGenerator();
    }

    public void SE(View view) {

        String c;
        c=cust.getText().toString();
        if (c.isEmpty())
        {
            cust.setError("empty");
            cust.requestFocus();

        }
        else
        {
            msg91=new MSG91("196023AwNplgJ6qS5a722783");
            String OTP=captchaGenerator.getOTP();
            msg91.composeMessage("HNDMNG","your otp for handyman customer"+OTP);
            msg91.to(c);
            msg91.send();
            Toast.makeText(this, "otp send", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(CustomerMobile.this,Verify.class);
            intent.putExtra("mobile",c);
            intent.putExtra("otp",OTP);
            startActivity(intent);

        }
    }

}
