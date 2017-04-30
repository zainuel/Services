package com.example.zainuel.services;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;


public class Profile extends Fragment {


    CircleImageView ProfilePic;
    FirebaseAuth mAuth;
    TextView username;

    public Profile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);



    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        ProfilePic = (CircleImageView) view.findViewById(R.id.user_image_admin);


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
