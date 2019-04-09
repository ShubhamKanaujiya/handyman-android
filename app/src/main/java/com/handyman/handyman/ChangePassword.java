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

public class ChangePassword extends AppCompatActivity {
    EditText edcurpas;
    ConnectionHelper ch;
    Connection con;
    Statement stmt;
    ResultSet rs;
    SharedPreferences sp;
     String cid,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        edcurpas=(EditText)findViewById(R.id.edcurpass);
        ch = new ConnectionHelper();
        sp=getSharedPreferences("cust",MODE_PRIVATE);
        cid = sp.getString("cid", "");
        password=sp.getString("password","");
       // Toast.makeText(this, ""+password, Toast.LENGTH_SHORT).show();
    }

    public void checkcurpass(View view) {
        String input=edcurpas.getText().toString().trim();
        if(edcurpas.getText().toString().isEmpty())
        {
            edcurpas.setError("Empty");
            edcurpas.requestFocus();
        }
        con=ch.getConnection();
        if(con==null)
        {
            Toast.makeText(this, "No Connection", Toast.LENGTH_SHORT).show();
        }
        else
        {
            try {
                stmt = con.createStatement();
                String query = "select cpswd from ak_handyman_cr where cid='"+cid+"'";
                rs = stmt.executeQuery(query);
                if(rs.next())
                {

                    if(input.equals(password)) {
                        Intent intent = new Intent(ChangePassword.this, Newpassword.class);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        Toast.makeText(this, "wrong password", Toast.LENGTH_SHORT).show();
                        edcurpas.setText("");
                    }
                }

            } catch (SQLException e) {
                e.printStackTrace();
                Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }



    }
}