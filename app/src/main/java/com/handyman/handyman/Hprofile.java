package com.handyman.handyman;

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

public class Hprofile extends AppCompatActivity {
    EditText fn, ln, mb, ad, ct, pc, em;
    SharedPreferences sp;
    String hid, fname, lname, mobile, address, city, pincode, email;
    ConnectionHelper ch;
    Connection con;
    Statement stmt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hprofile);
        fn = (EditText) findViewById(R.id.et_h_fname);
        ln = (EditText) findViewById(R.id.et_h_lname);
        mb = (EditText) findViewById(R.id.et_h_mobile);
        ad = (EditText) findViewById(R.id.et_h_address);
        ct = (EditText) findViewById(R.id.et_h_city);
        pc = (EditText) findViewById(R.id.et_h_pcode);
        em = (EditText) findViewById(R.id.et_h_email);
        sp = getSharedPreferences("handy", MODE_PRIVATE);
        hid = sp.getString("id", "");
        fname = sp.getString("fname", "");
        lname = sp.getString("lname", "");
        mobile = sp.getString("mobile", "");
        address = sp.getString("address", "");
        city = sp.getString("city", "");
        pincode = sp.getString("pincode", "");
        email = sp.getString("email", "");
        fn.setText(fname);
        ln.setText(lname);
        mb.setText(mobile);
        ad.setText(address);
        ct.setText(city);
        pc.setText(pincode);
        em.setText(email);
        ch = new ConnectionHelper();
    }

    public void edits(View view) {
        fn.setEnabled(true);
        ln.setEnabled(true);
        mb.setEnabled(true);
        ad.setEnabled(true);
        ct.setEnabled(true);
        pc.setEnabled(true);
        em.setEnabled(true);
    }

    public void cancel(View view) {
        fn.setEnabled(false);
        ln.setEnabled(false);
        mb.setEnabled(false);
        ad.setEnabled(false);
        ct.setEnabled(false);
        pc.setEnabled(false);
        em.setEnabled(false);
    }

    public void hupdate(View view) {
        con = ch.getConnection();
        if(fn.getText().toString().isEmpty())
        {
            fn.setError("Empty");
            fn.requestFocus();
        }
        if(ln.getText().toString().isEmpty())
        {
            ln.setError("Empty");
            ln.requestFocus();
        }
        if(mb.getText().toString().isEmpty())
        {
            mb.setError("Empty");
            mb.requestFocus();
        }
        if(ad.getText().toString().isEmpty())
        {
            ad.setError("Empty");
            ad.requestFocus();
        }
        if(ct.getText().toString().isEmpty())
        {
            ct.setError("Empty");
            ct.requestFocus();
        }
        if(pc.getText().toString().isEmpty())
        {
            pc.setError("Empty");
            pc.requestFocus();
        }
        if(em.getText().toString().isEmpty())
        {
            em.setError("Empty");
            em.requestFocus();
        }
        if (con == null) {
            Toast.makeText(this, "No connection", Toast.LENGTH_SHORT).show();
        } else {
            try {
                stmt = con.createStatement();
                String query = "update ak_handyman set fname='" +fn.getText().toString()+ "',lname='" +ln.getText().toString()+ "',mobile='"+mb.getText().toString()+"',address='"+ad.getText().toString()+"', city='"+ct.getText().toString()+"',pincode='"+pc.getText().toString()+"',email='"+em.getText().toString()+"' where id='"+hid+"'";
                 stmt.execute(query);
                Toast.makeText(this, "profile update", Toast.LENGTH_SHORT).show();
            } catch (SQLException e) {
               e.printStackTrace();
                Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }

    }
}
