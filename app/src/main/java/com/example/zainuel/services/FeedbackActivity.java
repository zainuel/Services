package com.example.zainuel.services;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FeedbackActivity extends AppCompatActivity {

    EditText fb;
    RatingBar rb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);


        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase mDB = FirebaseDatabase.getInstance();
        final DatabaseReference  mRef = mDB.getReference("feedback/"+mAuth.getCurrentUser().getUid());

        fb = (EditText) findViewById(R.id.fb_et);
        rb = (RatingBar) findViewById(R.id.ratingBar);

        Button s = (Button) findViewById(R.id.submit);
        s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRef.child("rating").setValue(rb.getRating());
                mRef.child("feedback").setValue(fb.getText().toString());
                Toast.makeText(FeedbackActivity.this, "Feedback Successfully Submitted", Toast.LENGTH_SHORT).show();
                finish();
            }
        });





    }
}
