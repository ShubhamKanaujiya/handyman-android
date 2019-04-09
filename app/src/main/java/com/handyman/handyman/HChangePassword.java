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

public class HChangePassword extends AppCompatActivity {
    EditText edhcurpas;
    ConnectionHelper ch;
    Connection con;
    Statement stmt;
    ResultSet rs;
    SharedPreferences sp;
    String hid,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hchange_password);
        sp = getSharedPreferences("handy", MODE_PRIVATE);
        hid = sp.getString("id", "");
        password=sp.getString("password","");
        edhcurpas = (EditText) findViewById(R.id.edhcurpass);
        ch = new ConnectionHelper();
    }

    public void checkhcurpass(View view) {
        String input=edhcurpas.getText().toString().trim();
        if (edhcurpas.getText().toString().isEmpty()) {
            edhcurpas.setError("Empty");
            edhcurpas.requestFocus();
        }


        con = ch.getConnection();
        if (con == null) {
            Toast.makeText(this, "No Connection", Toast.LENGTH_SHORT).show();
        } else {
            try {
                stmt = con.createStatement();
                String query = "select password from ak_handyman where id='"+hid+"'";
                rs = stmt.executeQuery(query);
                if (rs.next()) {
                    if(input.equals(password)) {
                        Intent intent = new Intent(HChangePassword.this, HNewPassword.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Toast.makeText(this, "wrong password", Toast.LENGTH_SHORT).show();
                         edhcurpas.setText("");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }


    }
}