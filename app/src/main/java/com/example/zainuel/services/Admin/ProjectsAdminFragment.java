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
import android.widget.Toast;

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
    DatabaseReference mUserRef;
    FirebaseAuth mAuth;
    ValueEventListener listener;
    ListView lv;
    AdminProjectsListViewAdapter adapter;

    AdminProjectsListViewAdapter.customButtonListener clistener;




    ProgressDialog pd;
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

        pd = new ProgressDialog(getContext());
        pd.setMessage("Loading ...");
        pd.show();

        clistener = this;

        final View et = view.findViewById(R.id.et);





        lv = (ListView) view.findViewById(R.id.admin_proj_lv);


        projectObjs = new ArrayList<>();

        mAuth = FirebaseAuth.getInstance();
        mDb = FirebaseDatabase.getInstance();
        mRef = mDb.getReference("openProjects");
        listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                projectObjs.clear();


                for (DataSnapshot child : dataSnapshot.getChildren()) {

                    for (DataSnapshot gchild : child.getChildren()) {

                        for (DataSnapshot g1child : gchild.getChildren()) {


                            ProjectObj pro = g1child.getValue(ProjectObj.class);
                            projectObjs.add(pro);


                        }

                    }
                }

                adapter = new AdminProjectsListViewAdapter(projectObjs, getContext());
                adapter.setCustomButtonListner(clistener);


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

                lv.setAdapter(adapter);
                lv.setEmptyView(et);
                pd.cancel();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        mRef.addListenerForSingleValueEvent(listener);
    }


    @Override
    public void onButtonClickListner(int position) {
        ProjectObj obj = projectObjs.get(position);

        String key = obj.getTime()+","+obj.getDate();
        mUserRef= mDb.getReference("users/"+obj.getUid()+"/projects/open/"+key);
        mUserRef.child("assignedEmployee").setValue(mAuth.getCurrentUser().getDisplayName());
        mUserRef.child("status").setValue("Employee has been assigned");
        mUserRef.child("employeeRating").setValue("1");

        mRef.child(obj.getType()+"/"+obj.getUid()+"/"+key).removeValue();

        mRef = mDb.getReference("sp/"+mAuth.getCurrentUser().getUid()+"/projects/"+key);
        obj.setAssignedEmployee(mAuth.getCurrentUser().getDisplayName());
        obj.setStatus("Employee has been assigned");
        obj.setEmployeeRating("1");
        mRef.setValue(obj);

        Toast.makeText(getContext(),"Project Takeup Successful",Toast.LENGTH_SHORT).show();

    }
}
