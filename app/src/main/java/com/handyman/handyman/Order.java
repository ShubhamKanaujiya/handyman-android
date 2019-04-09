package com.handyman.handyman;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.katepratik.msg91api.MSG91;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Order extends AppCompatActivity {
    TextView or,cn,cp,des,ad;
    ConnectionHelper ch;
    ResultSet rs;
    Connection con;
    SharedPreferences sp;
    Statement stmt;
    String orderid;
    String oi,cphone,cname,desc,cadd;
    MSG91 msg91;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        or=(TextView)findViewById(R.id.odrid);
        cn=(TextView)findViewById(R.id.odrcname);
        cp=(TextView)findViewById(R.id.odrcphone);
        des=(TextView)findViewById(R.id.odrdes);
        ad=(TextView)findViewById(R.id.odradrs);
        Intent intent=getIntent();
        orderid=intent.getStringExtra("oid");
        Toast.makeText(this, " odd "+orderid, Toast.LENGTH_SHORT).show();
        ch=new ConnectionHelper();
        con=ch.getConnection();
        if (con==null)
        {
            Toast.makeText(this, "No Connection", Toast.LENGTH_SHORT).show();
        }
        else
        {
            try {
                stmt=con.createStatement();
                String q="select * from ak_handyman_bookings where orderid='"+orderid+"'";
                rs=stmt.executeQuery(q);
                if (rs.next())
                {
                    oi=rs.getString("orderid");
                    cname=rs.getString("cname");
                    cphone=rs.getString("cno");
                    desc=rs.getString("description");
                    cadd=rs.getString("current_location");
                }
                or.setText(oi);
                cn.setText(cname);
                cp.setText(cphone);
                des.setText(desc);
                ad.setText(cadd);

            } catch (SQLException e) {
                Toast.makeText(this, "SQL "+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

    }
    public void acc(View view)
    {
        con=ch.getConnection();
        try
        {
            stmt=con.createStatement();
            String query="update ak_handyman_bookings set status='Confirmed' where orderid='"+oi+"'";
            stmt.execute(query);

            AlertDialog.Builder ad=new AlertDialog.Builder(Order.this);
            ad.setTitle("Call To Confirm");
            ad.setMessage("Call On"+cp.getText().toString()+" ?");
            ad.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent=new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:"+cp.getText().toString()));
                    startActivity(intent);
                }
            });
            ad.show();
        }
        catch (SQLException e)
        {
            Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public void rej(View view)
    {
        con=ch.getConnection();
        try {
            stmt=con.createStatement();
            String query="update ak_handyman_bookings set status='Rejected' where orderid='"+oi+"'";
            stmt.execute(query);

            msg91=new MSG91("196023AwNplgJ6qS5a722783");
            msg91.composeMessage("HNDMNG","Dear"+cname+" Your Order Id "+oi+" for "+desc+" has been rejected");
            msg91.to(cphone);
            msg91.send();
        } catch (SQLException e) {
            Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

        }


    }
}
