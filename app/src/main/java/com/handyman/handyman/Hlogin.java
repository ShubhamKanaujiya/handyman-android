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

public class Hlogin extends AppCompatActivity {
    EditText etmobileno,etpass;
    ConnectionHelper ch;
    Connection con;
    Statement stmt;
    ResultSet rs;
    SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hlogin);
        etmobileno=(EditText)findViewById(R.id.etmobileno);
        etpass=(EditText)findViewById(R.id.etpass);
        ch=new ConnectionHelper();
    }
    public void hreg(View view)
    {
        Intent intent=new Intent(Hlogin.this,HRegister.class);
        startActivity(intent);
    }
    public void reglogin(View view)
    {
        if(etmobileno.getText().toString().isEmpty())
        {
            etmobileno.setError("Empty");
            etpass.requestFocus();
        }
        else if(etpass.getText().toString().isEmpty())
        {
            etpass.setError("Empty");
            etpass.requestFocus();
        }
        else
        {
            String phone,password;
            phone=etmobileno.getText().toString().trim();
            password=etpass.getText().toString().trim();

            con=ch.getConnection();

            if(con==null)
            {
                Toast.makeText(this, "No connection", Toast.LENGTH_SHORT).show();
            }
            else
            {
                try {
                    stmt=con.createStatement();
                    String query="select * from ak_handyman where mobile='"+phone+"' and password='"+password+"'";
                    rs=stmt.executeQuery(query);

                    if(rs.next())
                    {
                        sp=getSharedPreferences("handy",MODE_PRIVATE);
                        SharedPreferences.Editor ed=sp.edit();
                       ed.putString("id",rs.getString("id"));
                        ed.putString("fname",rs.getString("fname"));
                        ed.putString("lname",rs.getString("lname"));
                        ed.putString("mobile",rs.getString("mobile"));
                        ed.putString("work",rs.getString("works"));
                        ed.putString("address",rs.getString("address"));
                        ed.putString("city",rs.getString("city"));
                        ed.putString("pincode",rs.getString("pincode"));
                        ed.putString("email",rs.getString("email"));
                        ed.putString("password",rs.getString("password"));
                        ed.commit();
                        Intent intent=new Intent(Hlogin.this,Hdashboard.class);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        Toast.makeText(this, "Invalid Phone or Password", Toast.LENGTH_SHORT).show();
                        etmobileno.setText("");
                        etpass.setText("");
                    }




                } catch (SQLException e) {
                    Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

        }


    }
    public void hforgetpassword(View view)
    {
        Intent intent=new Intent(Hlogin.this,HForgetpassword.class);
        startActivity(intent);
    }



}

