package com.example.zainuel.services;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.rd.PageIndicatorView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.zainuel.services.R.id.gridview;

public class MainActivity extends AppCompatActivity {

    LinearLayout top_navigation_layout;
    FrameLayout view_pager;
    LinearLayout like;

    static final int SIGN_REQUEST = 1;

     GridView gridView;
    private ImageAdapter gridAdapter;


    String[] gridViewString = {
            "AC Repair", "Electrician", "Architect", "Guitar Classes",
            "Carpenter", "Plumber", "Web Developer",
            "Motorcycle Repair", "House Painter"

    } ;

    String[] gridColor ={

            "#FFE3FCFB",
            "#FFE3FCFB",
            "#FFE3FCFB",
            "#FFE3FCFB",
            "#FFE3FCFB",
            "#FFE3FCFB",
            "#FFE3FCFB",
            "#FFE3FCFB",
            "#FFE3FCFB",
            "#FFE3FCFB",
            "#FFE3FCFB",
            "#FFE3FCFB",
            "#FFE3FCFB",
            "#FFE3FCFB",
            "#FFE3FCFB",
            "#FFE3FCFB",
            "#FFE3FCFB"
    };

    int[] gridViewImageId = {
            R.drawable.air_conditioner_48px, R.drawable.disconnected_48px_electrician,
            R.drawable.drafting_compass_48px_architect, R.drawable.guitar_48px,
            R.drawable.hammer_48px_carpenter,R.drawable.plumbing_48px, R.drawable.web_design_50px_48x48,
             R.drawable.motorcycle_48px, R.drawable.painter_48px
    };

    private ViewPager viewPager;
    private static int currentPage = 0;
    private static int total_pages = 0;

    private static final Integer[] images = {R.drawable.events, R.drawable.homerent,
            R.drawable.services, R.drawable.homerent};
    private ArrayList<Integer> ImagesArray = new ArrayList<>();



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    like.setVisibility(View.VISIBLE);
                    view_pager.setVisibility(View.VISIBLE);
                    top_navigation_layout.setVisibility(View.VISIBLE);
                    replaceFragments(Home.class,false);
                    return true;
                case R.id.navigation_dashboard:
                    like.setVisibility(View.GONE);
                    view_pager.setVisibility(View.GONE);
                    top_navigation_layout.setVisibility(View.GONE);
                    replaceFragments(Projects.class,false);
                    return true;
                case R.id.navigation_notifications:
                    like.setVisibility(View.GONE);
                    view_pager.setVisibility(View.GONE);
                    top_navigation_layout.setVisibility(View.GONE);
                    replaceFragments(Profile.class,false);
                    return true;
            }
            return false;
        }

    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        top_navigation_layout = (LinearLayout) findViewById(R.id.home_upper_navigation);
        view_pager = (FrameLayout) findViewById(R.id.viewpage);
        like=(LinearLayout)findViewById(R.id.like);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //custom adapter
        CustomGridView adapterViewAndroid = new CustomGridView(MainActivity.this, gridViewString, gridViewImageId,gridColor);
        gridView=(GridView)findViewById(gridview);
        gridView.setAdapter(adapterViewAndroid);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int i, long id) {
                Toast.makeText(MainActivity.this, "GridView Item: " + gridViewString[+i], Toast.LENGTH_LONG).show();
            }
        });
//end of custom adapter

   /*     GridView gridview = (GridView) findViewById(R.id.gridview);
        gridAdapter =new ImageAdapter(this,R.layout.grid_item_layout);
        gridview.setAdapter(gridAdapter);   */

        view_pager.setVisibility(View.VISIBLE);
        top_navigation_layout.setVisibility(View.VISIBLE);
        replaceFragments(Home.class,false);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l){
               // Class Activ = getTargetActivityForPosition(position);
               // MainActivity.this.startActivity(new Intent(MainActivity.this, Activ.class));


                if(position==7){
                    Intent iinent= new Intent(MainActivity.this,ProjectDetailsSelectActivity.class);
                    startActivity(iinent);
                }

            }});




    };



    private void displayChatMessages(){


    }

    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == SIGN_REQUEST) {
            if(resultCode == RESULT_OK) {
                Toast.makeText(this,
                        "Successfully signed in. Welcome!",
                        Toast.LENGTH_LONG)
                        .show();
                displayChatMessages();
            } else {
                Toast.makeText(this,
                        "We couldn't sign you in. Please try again later.",
                        Toast.LENGTH_LONG)
                        .show();

                // Close the app
                finish();
            }
        }

    }




    public void replaceFragments(Class fragmentClass, Boolean addToBackStack) {
        Fragment fragment = null;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
            if(!getSupportFragmentManager().beginTransaction().isEmpty()) {
                getSupportFragmentManager().beginTransaction().
                        remove(getSupportFragmentManager().findFragmentById(R.id.content)).commit();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.popBackStackImmediate();
        if(addToBackStack) {
            fragmentManager.beginTransaction().replace(R.id.content, fragment)
                    .addToBackStack(null)
                    .commit();
        }
        else {
            fragmentManager.beginTransaction().replace(R.id.content, fragment)
                    .commit();
        }

        init();
    }

    private void init() {


        for (int i = 0; i < images.length; i++)
            ImagesArray.add(images[i]);

        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(new ViewPagerAdapter(MainActivity
                .this, ImagesArray));

        total_pages = images.length;

        PageIndicatorView pageIndicatorView = (PageIndicatorView) findViewById(R.id.pageIndicatorView);
        pageIndicatorView.setViewPager(viewPager);
        pageIndicatorView.setSelectedColor(R.color.highlighted);
        pageIndicatorView.setUnselectedColor(R.color.nothighlighted);


        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == total_pages) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 9000, 15000);


    }




}
