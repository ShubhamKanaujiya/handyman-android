package com.handyman.handyman;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Cregister extends AppCompatActivity {
    EditText [] ets=new EditText[4];
    int[] id = {R.id.fullname,R.id.etmobil, R.id.etcity, R.id.et_pswrd};
    String[] values = new String[ets.length];
    int i;
    ConnectionHelper ch;
    Connection con;
    Statement stmt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cregister);
        for (i = 0; i < ets.length; i++) {
            ets[i] = (EditText) findViewById(id[i]);
        }
        ch=new ConnectionHelper();
    }
    public void clog(View view) {
        Intent intent = new Intent(Cregister.this, Clogin.class);
        startActivity(intent);
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
                    String query = "insert into ak_handyman_cr values('"+values[0]+"','"+values[1]+"','"+values[2]+ "','"+values[3]+"')";
                    stmt.execute(query);
                    Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Cregister.this, Clogin.class);
                    startActivity(intent);
                } catch (SQLException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();

                }

            }
        }
    }
}
