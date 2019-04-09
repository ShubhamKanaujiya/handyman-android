package com.handyman.handyman;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class HNewPassword extends AppCompatActivity {
    EditText hnewpass,hcnfpass;
    ConnectionHelper ch;
    Connection con;
    Statement stmt;
    String hid,passwd;
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hnew_password);
        hnewpass=(EditText)findViewById(R.id.hnewpass);
        hcnfpass=(EditText)findViewById(R.id.hcnfpass);
        sp=getSharedPreferences("handy",MODE_PRIVATE);
        hid=sp.getString("id","");
        passwd=sp.getString("password","");
        ch=new ConnectionHelper();
    }
    public void hchangepassword(View view)
    {
        con=ch.getConnection();
        if(hnewpass.getText().toString().isEmpty())
        {
           hnewpass.setError("Empty");
           hnewpass.requestFocus();
        }
        if(hcnfpass.getText().toString().isEmpty())
        {
            hcnfpass.setError("Empty");
            hcnfpass.requestFocus();
        }
        if (hnewpass.getText().toString().equals(hcnfpass.getText().toString()))
        {
            if (con == null)
            {
                Toast.makeText(this, "No Connection", Toast.LENGTH_SHORT).show();
            }
            else
                {
                try
                {
                    stmt = con.createStatement();
                    String query = "update ak_handyman set password='"+hcnfpass.getText().toString()+"' where id='"+hid+"'";
                    stmt.execute(query);
                    Toast.makeText(this, "Password Changed", Toast.LENGTH_SHORT).show();
                }

                catch (SQLException e)
                {
                    e.printStackTrace();
                    Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

        }
        else
        {
            Toast.makeText(this, "Invalid Password", Toast.LENGTH_SHORT).show();
            hnewpass.setText("");
            hcnfpass.setText("");
        }

    }

}

