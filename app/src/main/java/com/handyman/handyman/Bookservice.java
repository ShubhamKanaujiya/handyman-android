package com.handyman.handyman;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class Bookservice extends AppCompatActivity {
    String str;
    ConnectionHelper ch;
    Connection con;
    Statement stmt;
    ResultSet rs;
    TextView vname,vmobile,vwork;
    String name,mobile,work,cid,cname,cmobile;
    EditText et_des,etadrs;
    SharedPreferences sharedPreferences;
    SimpleDateFormat sdf;
    Date dt;
    String date;
    AlertDialog.Builder ad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookservice);
        vname=(TextView)findViewById(R.id.vname);
        vmobile=(TextView)findViewById(R.id.vmobile);
        vwork=(TextView)findViewById(R.id.vwork);
        et_des=(EditText) findViewById(R.id.et_des);
        etadrs=(EditText) findViewById(R.id.etadrs);
        sdf=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        dt=new Date();
        date=sdf.format(dt);
        sharedPreferences=getSharedPreferences("cust",MODE_PRIVATE);
        cid=sharedPreferences.getString("cid","");
        cname=sharedPreferences.getString("name","");
        cmobile=sharedPreferences.getString("mobile","");
        Intent intent=getIntent();
        str=intent.getStringExtra("hmid");

       // Toast.makeText(this, ""+str, Toast.LENGTH_SHORT).show();
        ch=new ConnectionHelper();
        con=ch.getConnection();
        if(con==null)
        {
            Toast.makeText(this, "No connection", Toast.LENGTH_SHORT).show();
        }
        else
        {
            try {
                stmt=con.createStatement();
                String query="select * from ak_handyman where id='"+str+"'";
                rs=stmt.executeQuery(query);
                if(rs.next())
                {
                     name=rs.getString("fname")+rs.getString("lname");
                     mobile=rs.getString("mobile");
                     work=rs.getString("works");
                     //str=rs.getString("id");
                }
                vname.setText(name);
                vmobile.setText(mobile);
                vwork.setText(work);
            } catch (SQLException e) {
                e.printStackTrace();
                Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }
    }
    public void book(View view)
    {
        if(etadrs.getText().toString().isEmpty())
        {
            etadrs.setError("Empty");
            etadrs.requestFocus();
        }
        else if(et_des.getText().toString().isEmpty())
        {
            et_des.setError("Empty");
            et_des.requestFocus();
        }
        else
        {
            String caddress=etadrs.getText().toString();
            String cdes=et_des.getText().toString();
            try {
                stmt=con.createStatement();
                String query="insert into ak_handyman_bookings values('"+str+"','"+cid+"','"+ cname +"','"+cmobile+"','"+name+"','"+caddress+"','"+cdes+"','Pending','"+date+"')";
                stmt.execute(query);
                Toast.makeText(this, "Service Booked", Toast.LENGTH_SHORT).show();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            ad=new AlertDialog.Builder(Bookservice.this);
            ad.setTitle("book your service");
            ad.setMessage("Do you want to Call"+mobile);
            ad.setCancelable(false);
            ad.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent callintent=new Intent(Intent.ACTION_DIAL);
                    callintent.setData(Uri.parse("tel:"+mobile));
                    startActivity(callintent);
                    finish();
                }
            });

            ad.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            ad.show();
        }
    }
}
