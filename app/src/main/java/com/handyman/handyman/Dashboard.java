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
import android.widget.TextView;
import android.widget.Toast;

public class Dashboard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    String cid;
    SharedPreferences sharedPreferences;
    TextView tvhname,tvhemail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
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
        tvhname=(TextView)hview.findViewById(R.id.tvhname);
        tvhemail=(TextView)hview.findViewById(R.id.tvhemail);
        sharedPreferences=getSharedPreferences("cust",MODE_PRIVATE);
        cid=sharedPreferences.getString("cid","");
        String cn=sharedPreferences.getString("name","");
        String ce=sharedPreferences.getString("mobile","");
        tvhname.setText(cn);
        tvhemail.setText(ce);
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

    public void painter(View view)
    {
        Intent intent=new Intent(Dashboard.this,Handylist.class);
        intent.putExtra("Service","Painter");
        startActivity(intent);
    }
    public void carpenter(View view)
    {
        Intent intent=new Intent(Dashboard.this,Handylist.class);
        intent.putExtra("Service","Carpenter");
        startActivity(intent);
    }
    public void plumber(View view)
    {
        Intent intent=new Intent(Dashboard.this,Handylist.class);
        intent.putExtra("Service","Plumber");
        startActivity(intent);
    }
    public void mechnic(View view)
    {
        Intent intent=new Intent(Dashboard.this,Handylist.class);
        intent.putExtra("Service","Mechanic");
        startActivity(intent);
    }
    public void electrician(View view)
    {
        Intent intent=new Intent(Dashboard.this,Handylist.class);
        intent.putExtra("Service","Electrician");
        startActivity(intent);
    }
    public void electronicrepair(View view)
    {
        Intent intent=new Intent(Dashboard.this,Handylist.class);
        intent.putExtra("Service","Electronic Repair");
        startActivity(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
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

        if (id == R.id.mybooking) {
            Intent intent=new Intent(Dashboard.this,CustBooking.class);
            intent.putExtra("cid",cid);
            startActivity(intent);
            // Handle the camera action
        } else if (id == R.id.myprofile) {
            Intent intent=new Intent(Dashboard.this,Cprofile.class);
            startActivity(intent);

        } else if (id == R.id.changepassword) {
           Intent intent=new Intent(Dashboard.this,ChangePassword.class);
            startActivity(intent);

        } else if (id == R.id.logout) {

                SharedPreferences.Editor ed=sharedPreferences.edit();
                ed.clear();
                ed.commit();
                Intent intent=new Intent(Dashboard.this,Ask.class);
                startActivity(intent);
                ActivityCompat.finishAffinity(Dashboard.this);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
