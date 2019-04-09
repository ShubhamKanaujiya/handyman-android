package com.handyman.handyman;

import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class HRegister extends AppCompatActivity {
    EditText[] ets = new EditText[8];
    int[] id = {R.id.et_fname, R.id.et_lname, R.id.et_mobile, R.id.et_address, R.id.et_city, R.id.et_pcode, R.id.et_email, R.id.et_password};
    String[] values = new String[ets.length];
    int i;
    ConnectionHelper ch;
    Connection con;
    Statement stmt;
    String []works={"Plumber","Carpenter","Painter","Mechanic","Electrician","Electronic Repair"};
    ArrayAdapter AD;
    Spinner spin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hregister);

        spin=(Spinner)findViewById(R.id.spin);
        AD=new ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line,works);
        spin.setAdapter(AD);
        for (i = 0; i < ets.length; i++) {
            ets[i] = (EditText) findViewById(id[i]);
        }

        ch=new ConnectionHelper();
    }

    public void hlog(View view) {
        Intent intent = new Intent(HRegister.this, Hlogin.class);
        startActivity(intent);
        ActivityCompat.finishAffinity(HRegister.this);
    }

    public void register(View view) {
        for (i = 0; i < ets.length; i++) {
            if (ets[i].getText().toString().isEmpty()) {
                ets[i].setError(" Empty");
                ets[i].requestFocus();
                break;
            }
        }
        if (i == ets.length) {
            for (i = 0; i < ets.length; i++)
            {
                values[i] = ets[i].getText().toString().trim();
            }
            // Toast.makeText(this, ""+values[0]+values[1]+values[2]+values[3]+values[4]+values[5]+values[6]+values[7], Toast.LENGTH_SHORT).show();
            con = ch.getConnection();
            if (con == null) {
                Toast.makeText(this, "No Connection", Toast.LENGTH_SHORT).show();
            }
            else
                {
                try {
                    stmt = con.createStatement();
                    String query = "insert into ak_handyman values('"+values[0]+"','"+values[1]+"','"+values[2]+
                            "','"+spin.getSelectedItem().toString()+"','"+values[3]+"','"+values[4]+"','"+values[5]+"','"+values[6]+"','"+values[7]+"')";
                    stmt.execute(query);
                    Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(HRegister.this, Hlogin.class);
                    startActivity(intent);
                    ActivityCompat.finishAffinity(HRegister.this);
                } catch (SQLException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}