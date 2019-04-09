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

public class Clogin extends AppCompatActivity {
    EditText et_phone,et_password;
    ConnectionHelper ch;
    Connection con;
    Statement stmt;
    ResultSet rs;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clogin);
        et_phone=(EditText)findViewById(R.id.et_mobi);
        et_password=(EditText)findViewById(R.id.et_pass);
        ch=new ConnectionHelper();
    }
    public void cuser(View view)
    {
        Intent intent=new Intent(Clogin.this,CustomerMobile.class);
        startActivity(intent);
    }
    public void cdashboard(View view)
    {

        if(et_phone.getText().toString().isEmpty())
        {
            et_phone.setError("Empty");
            et_phone.requestFocus();
        }
        else if(et_password.getText().toString().isEmpty())
        {
            et_password.setError("Empty");
            et_password.requestFocus();
        }

        else
        {
            String phone,password;
            phone=et_phone.getText().toString().trim();
            password=et_password.getText().toString().trim();

            con=ch.getConnection();

            if(con==null)
            {
                Toast.makeText(this, "No connection", Toast.LENGTH_SHORT).show();
            }
            else
            {
                try {
                    stmt=con.createStatement();
                    String query="select * from ak_handyman_cr where cmobile='"+phone+"' and cpswd='"+password+"'";
                    rs=stmt.executeQuery(query);

                    if(rs.next())
                    {
                        sp=getSharedPreferences("cust",MODE_PRIVATE);
                        SharedPreferences.Editor ed=sp.edit();
                        ed.putString("cid",rs.getString("cid"));
                        ed.putString("name",rs.getString("cname"));
                        ed.putString("mobile",rs.getString("cmobile"));
                        ed.putString("password",rs.getString("cpswd"));
                        ed.putString("address",rs.getString("caddress"));
                        ed.putString("city",rs.getString("ccity"));
                        ed.commit();
                        Intent intent=new Intent(Clogin.this,Dashboard.class);
                        startActivity(intent);

                        finish();
                    }
                    else
                    {
                        Toast.makeText(this, "Invalid Phone or Password", Toast.LENGTH_SHORT).show();
                        et_phone.setText("");
                        et_password.setText("");
                    }




                } catch (SQLException e) {
                    Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

        }


    }
    public void cmobile(View view)
    {
        Intent intent=new Intent(Clogin.this,CustomerMobile.class);
        startActivity(intent);
        finishAffinity();
    }
    public void forgetpassword(View view)
    {
        Intent intent=new Intent(Clogin.this,ForgetPassword.class);
        startActivity(intent);
    }

}
