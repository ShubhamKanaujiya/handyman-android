package com.handyman.handyman;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Ask extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask);

    }
    public void hlogin(View view)
    {
        Intent intent=new Intent(Ask.this,Hlogin.class);
        startActivity(intent);
    }
    public void clogin(View view)
    {
        Intent intent=new Intent(Ask.this,Clogin.class);
        startActivity(intent);
    }
}
