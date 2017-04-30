package com.example.zainuel.services.Admin;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.zainuel.services.CircleImageView;
import com.example.zainuel.services.R;
import com.google.firebase.auth.FirebaseAuth;


public class ProfileAdminFragment extends Fragment {

    Button UploadDocs;
    CircleImageView ProfilePic;
    FirebaseAuth mAuth;
    TextView username;


    public ProfileAdminFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_admin, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        ProfilePic = (CircleImageView) view.findViewById(R.id.user_image_admin);
        UploadDocs = (Button) view.findViewById(R.id.upload_docs_btn_admin_profile);
        UploadDocs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getContext(),UploadDocsActivity.class);
                startActivity(intent);
            }
        });

        username = (TextView) view.findViewById(R.id.username_admin);
        username.setText(mAuth.getCurrentUser().getDisplayName());
        try{
            String photoURL = mAuth.getCurrentUser().getPhotoUrl().toString() ;
            photoURL = photoURL.replace("/s96-c/","/s800-c/");
            Glide.with(getContext()).load(Uri.parse(photoURL)).dontAnimate().into(ProfilePic);

        } catch (Exception e)
        {
            Toast.makeText(getContext(), "Unable to load profile pic", Toast.LENGTH_SHORT).show();
        }

    }
}
