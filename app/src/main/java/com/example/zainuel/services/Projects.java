package com.example.zainuel.services;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Projects extends Fragment {

    FirebaseDatabase mDb;
    DatabaseReference mRef;
    FirebaseAuth mAuth;
    ValueEventListener listener;
    ListView lv;
    MyProjectsListViewAdapter adapter;

    ArrayList<ProjectObj> projectObjs;
    ArrayList<String> pDates;


    public Projects() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_projects, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lv = (ListView) view.findViewById(R.id.my_proj_lv);


        pDates = new ArrayList<>();
        projectObjs = new ArrayList<>();

        mAuth = FirebaseAuth.getInstance();
        mDb = FirebaseDatabase.getInstance();
        mRef = mDb.getReference("users/" + mAuth.getCurrentUser().getUid() + "/projects/open");
        listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    for (DataSnapshot gchild : child.getChildren()) {
                        ProjectObj pro = gchild.getValue(ProjectObj.class);
                        String dt = gchild.getKey();

                        projectObjs.add(pro);
                        pDates.add(dt);

                    }
                }

                adapter = new MyProjectsListViewAdapter(projectObjs, getContext());
                lv.setAdapter(adapter);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        mRef.addListenerForSingleValueEvent(listener);
    }


}