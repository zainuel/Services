package com.example.zainuel.services;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Activ1111 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activ1111);
    }
    public void confirm(View view){

        Intent intent=new Intent(this,Progressbar.class);
        startActivity(intent);
        //replaceFragments(Home.class,false);

    }
}
