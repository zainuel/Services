package com.example.zainuel.services;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.zainuel.services.Admin.AdminHomeActivity;
import com.google.firebase.auth.FirebaseAuth;

public class TypeSelectionActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    SharedPreferences status;

    SharedPreferences type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_selection);

        checkLoginStatus();

        Button user = (Button) findViewById(R.id.user_signin_but);
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TypeSelectionActivity.this,LoginActivity.class);
                intent.putExtra("type",1);
                startActivity(intent);
                finish();
            }
        });

        Button sp = (Button) findViewById(R.id.sp_signin_but);
        sp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TypeSelectionActivity.this,LoginActivity.class);
                intent.putExtra("type",2);
                startActivity(intent);
                finish();
            }
        });


    }

    private void checkLoginStatus() {
        status = getSharedPreferences("login_status", Context.MODE_PRIVATE);
        type = getSharedPreferences("type",Context.MODE_PRIVATE);
        boolean logIn = status.getBoolean("in", false);
        int tp = type.getInt("type",3);
        if (logIn) {

            if(tp == 1)
            {
                Intent intent = new Intent(TypeSelectionActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

            } else if(tp == 2)
            {
                Intent intent = new Intent(TypeSelectionActivity.this, AdminHomeActivity.class);
                startActivity(intent);
                finish();

            }


        }
    }
}
