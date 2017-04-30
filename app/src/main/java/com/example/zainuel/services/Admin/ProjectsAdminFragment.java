package com.example.zainuel.services.Admin;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.zainuel.services.ProjectObj;
import com.example.zainuel.services.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ProjectsAdminFragment extends Fragment implements AdminProjectsListViewAdapter.customButtonListener {

    FirebaseDatabase mDb;
    DatabaseReference mRef;
    FirebaseAuth mAuth;
    ValueEventListener listener;
    ListView lv;
    AdminProjectsListViewAdapter adapter;

    ArrayList<ProjectObj> projectObjs;



    public ProjectsAdminFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_projects_admin, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        lv = (ListView) view.findViewById(R.id.admin_proj_lv);

        projectObjs = new ArrayList<>();

        mAuth = FirebaseAuth.getInstance();
        mDb = FirebaseDatabase.getInstance();
        mRef = mDb.getReference("openProjects");
        listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot child : dataSnapshot.getChildren()) {

                    for (DataSnapshot gchild : child.getChildren()) {

                        for (DataSnapshot g1child : gchild.getChildren()) {


                            ProjectObj pro = g1child.getValue(ProjectObj.class);
                            projectObjs.add(pro);


                        }

                    }
                }

                adapter = new AdminProjectsListViewAdapter(projectObjs, getContext());
                lv.setAdapter(adapter);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        mRef.addListenerForSingleValueEvent(listener);
    }


    @Override
    public void onButtonClickListner(int position) {
        
    }
}
