package com.example.zainuel.services.Admin;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.zainuel.services.ProjectObj;
import com.example.zainuel.services.ProjectsListViewAdapter;
import com.example.zainuel.services.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class OngoingProjectsAdminFragment extends Fragment {

    FirebaseDatabase mDb;
    DatabaseReference mRef;
    FirebaseAuth mAuth;
    ValueEventListener listener;
    ListView lv;
    ProjectsListViewAdapter adapter;

    ProgressDialog pd;

    ArrayList<ProjectObj> projectObjs;
    ArrayList<String> pDates;


    public OngoingProjectsAdminFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ongoing_projects_admin, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pd = new ProgressDialog(getContext());
        pd.setMessage("Loading ...");
        pd.show();

        lv = (ListView) view.findViewById(R.id.my_proj_lv);

        final View et = view.findViewById(R.id.et);


        pDates = new ArrayList<>();
        projectObjs = new ArrayList<>();

        mAuth = FirebaseAuth.getInstance();
        mDb = FirebaseDatabase.getInstance();
        mRef = mDb.getReference("sp/" + mAuth.getCurrentUser().getUid() + "/projects");
        listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {



                for (DataSnapshot gchild : dataSnapshot.getChildren()) {
                    ProjectObj pro = gchild.getValue(ProjectObj.class);
                    String dt = gchild.getKey();

                    projectObjs.add(pro);
                    pDates.add(dt);

                }


                adapter = new ProjectsListViewAdapter(projectObjs, getContext());
                lv.setAdapter(adapter);
                lv.setEmptyView(et);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        ProjectObj obj = projectObjs.get(position);


                        Intent intent = new Intent(getContext(),ProjectRequirementsActivity.class);
                        intent.putExtra("uid",obj.getUid());
                        intent.putExtra("date",obj.getTime()+","+obj.getDate());
                        startActivity(intent);

                    }
                });
                pd.cancel();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        mRef.addListenerForSingleValueEvent(listener);
    }
}
