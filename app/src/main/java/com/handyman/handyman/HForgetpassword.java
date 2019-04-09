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

public class HForgetpassword extends AppCompatActivity {
    EditText ethpassword;
    ConnectionHelper ch;
    Connection con;
    Statement stmt;
    ResultSet rs;
    SharedPreferences sp;
    CaptchaGenerator captchaGenerator;
    String OTP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hforgetpassword);
        ethpassword = (EditText) findViewById(R.id.ethforgetnumber);
        ch = new ConnectionHelper();
        captchaGenerator = new CaptchaGenerator();
    }

    public void hforgetpass(View view) {
        if (ethpassword.getText().toString().isEmpty()) {
            ethpassword.setError("Empty");
            ethpassword.requestFocus();
        }

        con = ch.getConnection();
        if (con == null) {
            Toast.makeText(this, "No connection", Toast.LENGTH_SHORT).show();
        } else {
            try {
                stmt = con.createStatement();
                String phone;
                phone = ethpassword.getText().toString().trim();
                String query = "select * from ak_handyman where mobile='" + phone + "'";
                rs = stmt.executeQuery(query);

                if (rs.next()) {
                    sp = getSharedPreferences("handy", MODE_PRIVATE);
                    SharedPreferences.Editor ed = sp.edit();
                    ed.putString("id", rs.getString("id"));
                    // ed.putString("mobile",rs.getString("cmobile"));
                    // ed.commit();
                    OTP = captchaGenerator.getOTP();
                    Intent intent = new Intent(HForgetpassword.this, HVerifyotp.class);
                    intent.putExtra("otp", OTP);
                    startActivity(intent);
                    Toast.makeText(this, "OTP SENT to=" + OTP, Toast.LENGTH_SHORT).show();
                    //finish();
                } else {
                    Toast.makeText(this, "Invalid Phone", Toast.LENGTH_SHORT).show();
                    //  et_phone.setText("");
                    ethpassword.setText("");
                }


            } catch (SQLException e) {
                Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

    }
}