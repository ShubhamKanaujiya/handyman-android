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

public class Newpassword extends AppCompatActivity {
    EditText newpass,cnfpass;
    ConnectionHelper ch;
    Connection con;
    Statement stmt;
    String cid,passwd;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newpassword);
        newpass=(EditText)findViewById(R.id.newpass);
        cnfpass=(EditText)findViewById(R.id.cnfpass);
        sp=getSharedPreferences("cust",MODE_PRIVATE);
        cid = sp.getString("cid", "");
        passwd = sp.getString("password", "");
        ch=new ConnectionHelper();
    }
    public void changepass(View view) {
        con = ch.getConnection();
        if(newpass.getText().toString().isEmpty())
        {
            newpass.setError("Empty");
            newpass.requestFocus();
        }
        else if(cnfpass.getText().toString().isEmpty())
        {
            cnfpass.setError("Empty");
            cnfpass.requestFocus();
        }
        if (newpass.getText().toString().equals(cnfpass.getText().toString()))
        {
            if (con == null) {
                Toast.makeText(this, "No Connection", Toast.LENGTH_SHORT).show();
            }
            else {
                try {
                    stmt = con.createStatement();
                    String query = "update ak_handyman_cr set cpswd='"+cnfpass.getText().toString()+"' where cid='"+cid+"'";
                    stmt.execute(query);
                    Toast.makeText(this, "Password Changed", Toast.LENGTH_SHORT).show();
                    }

                catch (SQLException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

        }
        else
        {
            Toast.makeText(this, "Invalid Password", Toast.LENGTH_SHORT).show();
            newpass.setText("");
            cnfpass.setText("");
        }

    }
}
