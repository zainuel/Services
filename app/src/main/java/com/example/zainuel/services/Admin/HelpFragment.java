package com.example.zainuel.services.Admin;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.crazyhitty.chdev.ks.firebasechat.ui.activities.UserListingActivity;
import com.example.zainuel.services.R;


public class HelpFragment extends Fragment {

    Button chat;


    public HelpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_help, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        chat = (Button) view.findViewById(R.id.chat_admin_but);
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getContext(), UserListingActivity.class);
                i.putExtra("type",2);
                startActivity(i);
            }
        });


    }
}
