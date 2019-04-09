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

public class Cprofile extends AppCompatActivity
{
    EditText fn, mb, ct;
    String cid, cname, cmobile, ccity;
    SharedPreferences sp;
    ConnectionHelper ch;
    Connection con;
    Statement stmt;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cprofile);
        fn = (EditText) findViewById(R.id.et_c_fullname);
        mb = (EditText) findViewById(R.id.et_c_mobile);
        ct = (EditText) findViewById(R.id.et_c_city);
        sp = getSharedPreferences("cust", MODE_PRIVATE);
        cid = sp.getString("cid", "");
        cname = sp.getString("name", "");
        cmobile = sp.getString("mobile", "");
        ccity = sp.getString("city", "");
        fn.setText(cname);
        mb.setText(cmobile);
        ct.setText(ccity);
        ch = new ConnectionHelper();
    }

    public void cedits(View view)
    {
        fn.setEnabled(true);
        mb.setEnabled(true);
        ct.setEnabled(true);
    }

    public void ccancel(View view)
    {
        fn.setEnabled(false);
        mb.setEnabled(false);
        ct.setEnabled(false);
    }

    public void cupdate(View view) {
        con = ch.getConnection();
        if(fn.getText().toString().isEmpty())
        {
            fn.setError("Empty");
            fn.requestFocus();
        }
        if(mb.getText().toString().isEmpty())
        {
            mb.setError("Empty");
            mb.requestFocus();
        }
        if(ct.getText().toString().isEmpty())
        {
            ct.setError("Empty");
            ct.requestFocus();
        }
        if (con == null)
        {
            Toast.makeText(this, "No Connection", Toast.LENGTH_SHORT).show();
        }
        else
            {
            try
            {
                stmt = con.createStatement();
                String query = "update ak_handyman_cr set cname='"+fn.getText().toString()+"',cmobile='"+mb.getText().toString()+"',ccity='"+ct.getText().toString()+"' where cid='"+cid+"'";
                stmt.execute(query);
                Toast.makeText(this, "Profile Update", Toast.LENGTH_SHORT).show();
            } catch (SQLException e)
            {
                e.printStackTrace();
                Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}