package com.example.zainuel.services;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

public class Activ111 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activ111);
    }

    public void onRadioButtonClicked_3(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {

            case R.id.generalserv1:
                if (checked) {
                    Intent intent = new Intent(this, Activ1111.class);
                    startActivity(intent);
                }
                break;

            case R.id.generalserv2:
                if (checked) {
                    // Intent intent=new Intent(this,Activ12.class);
                    // startActivity(intent);
                }
                break;

        }
    }
}
