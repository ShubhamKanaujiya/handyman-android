package com.handyman.handyman;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import static com.handyman.handyman.R.layout.activity_bookservice;
import static com.handyman.handyman.R.layout.customlist;

public class Handylist extends AppCompatActivity {
    String str;
    ArrayList<HashMap<String,String>> services=new ArrayList<HashMap <String,String>>();
    ConnectionHelper ch;
    Connection con;
    Statement stmt;
    ResultSet rs;
    SimpleAdapter SA;
    String []from={"name","phone","work","id"};
    int [] to={R.id.tvname,R.id.tvphone,R.id.tvwork,R.id.tvid};
    ListView hlist;
    //SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handylist);
        Intent intent=getIntent();
        str=intent.getStringExtra("Service");
        Toast.makeText(this, ""+str, Toast.LENGTH_SHORT).show();
        hlist=(ListView)findViewById(R.id.hlist);
        SA=new SimpleAdapter(this,services, R.layout.customlist,from,to);
        hlist.setAdapter(SA);
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
                String query="select * from ak_handyman where works='"+str+"'";
                rs=stmt.executeQuery(query);
                while(rs.next())
                {
                    HashMap<String,String> hm=new HashMap<String, String>();
                    hm.put("name",rs.getString("fname")+" "+rs.getString("lname"));
                    hm.put("phone",rs.getString("mobile"));
                    hm.put("work",rs.getString("works"));
                    hm.put("id",rs.getString("id"));
                    services.add(hm);
                    }
                    SA.notifyDataSetChanged();
                       hlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        str=((TextView)view.findViewById(R.id.tvid)).getText().toString();
                        Intent intent1=new Intent(Handylist.this,Bookservice.class);
                        intent1.putExtra("hmid",str);
                        startActivity(intent1);

                    }
                });

            } catch (SQLException e) {
                e.printStackTrace();
                Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }
    }
}
