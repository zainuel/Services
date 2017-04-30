package com.example.zainuel.services;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

public class Activ11 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activ11);
    }

    public void onRadioButtonClicked_2(View view){
        boolean checked=((RadioButton)view).isChecked();

        switch (view.getId()){

            case R.id.bajaj:
                if(checked){
                    Intent intent=new Intent(this,Activ111.class);
                    startActivity(intent);
                }
                break;

            case R.id.honda:
                if(checked){
                   // Intent intent=new Intent(this,Activ12.class);
                   // startActivity(intent);
                }
                break;

        }


    }


}
