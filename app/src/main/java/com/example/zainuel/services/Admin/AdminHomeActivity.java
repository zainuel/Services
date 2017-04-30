package com.example.zainuel.services.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.crazyhitty.chdev.ks.firebasechat.ui.activities.UserListingActivity;
import com.example.zainuel.services.R;

public class AdminHomeActivity extends AppCompatActivity {


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.profile_admin_bnav:
                    replaceFragments(ProfileAdminFragment.class,true);
                    return true;
                case R.id.projects_admin_bnav:
                    replaceFragments(ProjectsAdminFragment.class,true);
                    return true;
                case R.id.help_admin_bnav:
                    //replaceFragments(HelpFragment.class,true);
                    Intent i= new Intent(AdminHomeActivity.this, UserListingActivity.class);
                    startActivity(i);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.admin_bnav);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }


    public void replaceFragments(Class fragmentClass, Boolean addToBackStack) {
        Fragment fragment = null;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
            if(!getSupportFragmentManager().beginTransaction().isEmpty()) {
                getSupportFragmentManager().beginTransaction().
                        remove(getSupportFragmentManager().findFragmentById(R.id.content_admin_main)).commit();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.popBackStackImmediate();
        if(addToBackStack) {
            fragmentManager.beginTransaction().replace(R.id.content_admin_main, fragment)
                    .addToBackStack(null)
                    .commit();
        }
        else {
            fragmentManager.beginTransaction().replace(R.id.content_admin_main, fragment)
                    .commit();
        }

    }

}
