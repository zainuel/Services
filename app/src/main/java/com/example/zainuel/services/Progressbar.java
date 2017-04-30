package com.example.zainuel.services;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class Progressbar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progressbar);

       // Context context=getApplicationContext();
      //  new AlertDialog.Builder(context).setTitle("Successfull").setMessage("Your Booking has been confirmed").show();


       Context context=getApplicationContext();
       Toast toast= Toast.makeText(context,"Booking Confirmed",Toast.LENGTH_LONG);
        toast.show();

    }

}
