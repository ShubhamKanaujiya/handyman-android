package com.handyman.handyman;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ForgetPassword extends AppCompatActivity {
    EditText etpassword;
    ConnectionHelper ch;
    Connection con;
    Statement stmt;
    ResultSet rs;
    SharedPreferences sp;
    CaptchaGenerator captchaGenerator;
    String OTP;
 //   private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        etpassword=(EditText)findViewById(R.id.etforgetnumber);
        ch=new ConnectionHelper();
        captchaGenerator=new CaptchaGenerator();
    }
    public void forgetpass(View view)
    {
        if(etpassword.getText().toString().isEmpty())
        {
            etpassword.setError("Empty");
            etpassword.requestFocus();
        }

        else {
         //   String  password;
            //String
        }
        con=ch.getConnection();
        if(con==null)
        {
            Toast.makeText(this, "No connection", Toast.LENGTH_SHORT).show();
        }
        else
        {
            try {
                stmt=con.createStatement();
                String phone;
                phone = etpassword.getText().toString().trim();
                String query="select * from ak_handyman_cr where cmobile='"+phone+"'";
                rs=stmt.executeQuery(query);

                if(rs.next())
                {
                    sp=getSharedPreferences("cust",MODE_PRIVATE);
                    SharedPreferences.Editor ed=sp.edit();
                    ed.putString("cid",rs.getString("cid"));
                   // ed.putString("mobile",rs.getString("cmobile"));
                   // ed.commit();
                    OTP=captchaGenerator.getOTP();
                    Intent intent=new Intent(ForgetPassword.this,CVerifyotp.class);
                    intent.putExtra("otp",OTP);
                    startActivity(intent);
                    Toast.makeText(this, "OTP SENT to="+OTP, Toast.LENGTH_SHORT).show();
                    //finish();
                }
                else
                {
                    Toast.makeText(this, "Invalid Phone", Toast.LENGTH_SHORT).show();
                  //  et_phone.setText("");
                    etpassword.setText("");
                }




            } catch (SQLException e) {
                Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

    }
    }
