package com.example.zainuel.services;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

public class Activ extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_);
    }

    public void onRadioButtonClicked_1(View view){
   boolean checked=((RadioButton)view).isChecked();

        switch (view.getId()){

            case R.id.motorcycle:
                if(checked){
                    Intent intent=new Intent(this,Activ11.class);
                    startActivity(intent);
                }
                break;

            case R.id.scooter:
                if(checked){
                    Intent intent=new Intent(this,Activ12.class);
                    startActivity(intent);
                }
                break;

        }


    }


}
