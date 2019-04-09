package com.handyman.handyman;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class CustBooking extends AppCompatActivity {
    ArrayList<HashMap<String,String>> services=new ArrayList<HashMap <String,String>>();
    ConnectionHelper ch;
    Connection con;
    Statement stmt;
    ResultSet rs;
    SimpleAdapter SA;
    String []from={"hname","hphone","des","date","status"};
    int [] to={R.id.hname,R.id.hphone,R.id.des,R.id.date,R.id.status};
    ListView listView;
    //SharedPreferences sharedPreferences;
    String cid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cust_booking);

        listView=(ListView)findViewById(R.id.clist);
        SA=new SimpleAdapter(this,services,R.layout.mybooking,from,to);

        listView.setAdapter(SA);
        ch=new ConnectionHelper();

        Intent intent=getIntent();
        cid=intent.getStringExtra("cid");
        Toast.makeText(this, ""+cid, Toast.LENGTH_SHORT).show();
        con=ch.getConnection();
        if (con==null)
        {
            Toast.makeText(this, "NO Connection", Toast.LENGTH_SHORT).show();
        }
        else
        {
            try {
                stmt=con.createStatement();
                String query="select * from ak_handyman_bookings where cid='"+cid+"'";
                rs=stmt.executeQuery(query);
                while (rs.next())
                {
                    HashMap<String,String> hashMap=new HashMap<String, String>();
                    hashMap.put("hname",rs.getString("hname"));
                    hashMap.put("hphone",getHphone(rs.getString("hid")));
                    hashMap.put("des",rs.getString("description"));
                    hashMap.put("date",rs.getString("cur_date"));
                    hashMap.put("status",rs.getString("status"));
                    services.add(hashMap);
                }
                SA.notifyDataSetChanged();
                listView.invalidateViews();
                listView.refreshDrawableState();
            } catch (SQLException e) {
                Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

    }
    public String getHphone(String hid)
    {
        String hphone="";
        con=ch.getConnection();
        if (con==null)
        {
            Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT).show();
        }
        else
        {
            try {
                stmt=con.createStatement();
                String query="select mobile from ak_handyman where id='"+hid+"'";
                ResultSet resultSet=stmt.executeQuery(query);
                if (resultSet.next())
                {
                    hphone+=resultSet.getString("mobile");
                }

            } catch (SQLException e) {
                Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }
        return hphone;
    }
    }

