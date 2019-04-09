package com.handyman.handyman;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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

public class Hdashboard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ListView l;
    TextView tvcname,tvcemail;
    String[] from={"cname","address","phone","order"};
    int[] to={R.id.hdname,R.id.hdadrs,R.id.hdphone,R.id.orderid};
    String hid,oid;
    String hservices;
    ArrayList<HashMap<String,String>> hservice=new ArrayList<HashMap<String, String>>();
    ConnectionHelper ch;
    Connection con;
    Statement stmt;
    ResultSet rs;
    SimpleAdapter SA;
    String num;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hdashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View hview=navigationView.getHeaderView(0);
        tvcname=(TextView)hview.findViewById(R.id.tvcname);
        tvcemail=(TextView)hview.findViewById(R.id.tvcemail);
        sharedPreferences=getSharedPreferences("handy",MODE_PRIVATE);
        hid=sharedPreferences.getString("id","");
        String cn=sharedPreferences.getString("fname","")+sharedPreferences.getString("lname","");
        String ce=sharedPreferences.getString("email","");
        tvcname.setText(cn);
        tvcemail.setText(ce);
        Toast.makeText(this, ""+hid, Toast.LENGTH_SHORT).show();
        l=(ListView)findViewById(R.id.hdash);
        SA=new SimpleAdapter(this,hservice,R.layout.bookinglist,from,to);
        l.setAdapter(SA);
        ch=new ConnectionHelper();
        con=ch.getConnection();
        if (con==null)
        {
            Toast.makeText(this, "NO Connection", Toast.LENGTH_SHORT).show();
        }
        else
        {
            try {
                stmt=con.createStatement();
                String q="select * from ak_handyman_bookings where hid='"+hid+"'";
                rs=stmt.executeQuery(q);
                //hservice.clear();
                while (rs.next())
                {
                    HashMap<String ,String> hm=new HashMap<String, String>();
                    hm.put("cname",rs.getString("cname"));
                    hm.put("address",rs.getString("current_location"));
                    hm.put("phone",rs.getString("cno"));
                    hm.put("order",rs.getString("orderid"));
                    hservice.add(hm);
                }
                SA.notifyDataSetChanged();
                l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                        oid=((TextView)view.findViewById(R.id.orderid)).getText().toString();
                        Toast.makeText(Hdashboard.this, ""+oid, Toast.LENGTH_SHORT).show();
                        Intent ord=new Intent(Hdashboard.this,Order.class);
                       ord.putExtra("oid",oid);
                        startActivity(ord);

                    }
                });
            } catch (SQLException e) {
                Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }

    }




    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.hdashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.profile) {
            Intent intent=new Intent(Hdashboard.this,Hprofile.class);
            startActivity(intent);
            // Handle the camera action
        } else if (id == R.id.password) {
            Intent intent=new Intent(Hdashboard.this,HChangePassword.class);
            startActivity(intent);

        } else if (id == R.id.logout) {
            SharedPreferences.Editor ed=sharedPreferences.edit();
            ed.clear();
            ed.commit();
            Intent intent=new Intent(Hdashboard.this,Ask.class);
            startActivity(intent);
            ActivityCompat.finishAffinity(Hdashboard.this);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
