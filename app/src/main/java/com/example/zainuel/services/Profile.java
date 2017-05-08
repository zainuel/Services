package com.example.zainuel.services;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.crazyhitty.chdev.ks.firebasechat.ui.activities.UserListingActivity;
import com.google.firebase.auth.FirebaseAuth;


public class Profile extends Fragment {


    CircleImageView ProfilePic;
    FirebaseAuth mAuth;
    TextView username;
    TextView useremail;
    Button chat;
    Button logout;

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

        chat = (Button) view.findViewById(R.id.chat_user_but);
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getContext(), UserListingActivity.class);
                i.putExtra("type",1);
                startActivity(i);
            }
        });

        logout = (Button) view.findViewById(R.id.logout_but_user);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                SharedPreferences type = getActivity().getSharedPreferences("type", Context.MODE_PRIVATE);
                type.edit().remove("type").apply();
                Intent intent=new Intent(getContext(), TypeSelectionActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        useremail = (TextView) view.findViewById(R.id.user_email_user);
        useremail.setText(mAuth.getCurrentUser().getEmail());



        username = (TextView) view.findViewById(R.id.user_name_user);
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
