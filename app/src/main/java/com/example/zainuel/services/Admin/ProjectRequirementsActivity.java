package com.example.zainuel.services.Admin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.zainuel.services.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProjectRequirementsActivity extends AppCompatActivity {

    ArrayList<String> q = new ArrayList<>();
    ArrayList<String> a = new ArrayList<>();
    TextView q1;
    TextView q2;
    TextView q3;
    TextView a1;
    TextView a2;
    TextView a3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_requirements);



        String uid = getIntent().getStringExtra("uid");
        String date = getIntent().getStringExtra("date");



        FirebaseDatabase mDb = FirebaseDatabase.getInstance();
        DatabaseReference mRef = mDb.getReference("users/"+uid+"/projects/open/"+date+"/questions");

        q1 = (TextView) findViewById(R.id.q1);
        q2 = (TextView) findViewById(R.id.q2);
        q3 = (TextView) findViewById(R.id.q3);
        a1 = (TextView) findViewById(R.id.a1);
        a2 = (TextView) findViewById(R.id.a2);
        a3 = (TextView) findViewById(R.id.a3);


        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot child:dataSnapshot.getChildren())
                {
                    q.add(child.getKey());
                    a.add(child.getValue().toString());
                }


                init();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });

    }


    void init()
    {
        q1.setText(q.get(0));
        q2.setText(q.get(1));
        q3.setText(q.get(2));
        a1.setText(a.get(0));
        a2.setText(a.get(1));
        a3.setText(a.get(2));
    }
}
