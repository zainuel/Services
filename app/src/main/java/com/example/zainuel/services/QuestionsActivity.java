package com.example.zainuel.services;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.kofigyan.stateprogressbar.StateProgressBar;

import java.util.ArrayList;

public class QuestionsActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener{

    RadioGroup radioGroup;
    RadioButton rb1, rb2, rb3, rb4;
    Button button;
    String rb1s, rb2s, rb3s, rb4s;

    TextView q;

    int question;



    ArrayList<String> selected;


    String[] descriptionData = {"Type", "Brand", "Service"};

    String[] h = {"What type of vehicle do you own ?","What brand of vechile you own ?",
            "What type of service are you looking for ?"};

    String[] q1 = {"Motor cycle(Geared Vehicle)","Scooter"};
    String[] q2 = {"Bajaj","Honda","KTM","Vespa"};
    String[] q3 = {"General Service (upto 200cc)","General Service (200cc to 350cc)",
            "General Service (500cc to 1000cc)","Other Repairs"};
    String[] q4 = {};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);


        radioGroup = (RadioGroup) findViewById(R.id.rg_questions);
        radioGroup.setOnCheckedChangeListener(this);

        selected = new ArrayList<>();

        q = (TextView) findViewById(R.id.q_questions);

        rb1 = (RadioButton) findViewById(R.id.rb1_questions);

        rb2 = (RadioButton) findViewById(R.id.rb2_questions);

        rb3 = (RadioButton) findViewById(R.id.rb3_questions);

        rb4 = (RadioButton) findViewById(R.id.rb4_questions);

        final StateProgressBar stateProgressBar = (StateProgressBar) findViewById(R.id.spb_questions);
        stateProgressBar.setStateDescriptionData(descriptionData);

        question = 1;


        q.setText(h[0]);
        rb1.setText(q1[0]);
        rb2.setText(q1[1]);
        rb3.setVisibility(View.GONE);
        rb4.setVisibility(View.GONE);

        button = (Button) findViewById(R.id.but_questions);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(question == 1)
                {
                    if(rb1.isChecked())
                    {
                        selected.add(rb1s);
                    } else if(rb2.isChecked())
                    {
                        selected.add(rb2s);

                    }



                    rb1.setChecked(false);
                    rb2.setChecked(false);
                    rb3.setChecked(false);
                    rb4.setChecked(false);


                    rb1.setText(q2[0]);
                    rb2.setText(q2[1]);
                    rb3.setVisibility(View.VISIBLE);
                    rb3.setText(q2[2]);
                    rb4.setVisibility(View.VISIBLE);
                    rb4.setText(q2[3]);

                    question = 2;

                    stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);






                }else if(question == 2)
                {

                    if(rb1.isChecked())
                    {
                        selected.add(rb1s);
                    } else if(rb2.isChecked())
                    {
                        selected.add(rb2s);

                    } else if(rb3.isChecked())
                    {
                        selected.add(rb3s);
                    } else if(rb4.isChecked())
                    {
                        selected.add(rb4s);

                    }



                    rb1.setChecked(false);
                    rb2.setChecked(false);
                    rb3.setChecked(false);
                    rb4.setChecked(false);

                    rb1.setText(q3[0]);
                    rb2.setText(q3[1]);
                    rb3.setText(q3[2]);
                    rb4.setText(q3[3]);

                    question = 3;

                    stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.THREE);

                }else if(question == 3)
                {

                    if(rb1.isChecked())
                    {
                        selected.add(rb1s);
                    } else if(rb2.isChecked())
                    {
                        selected.add(rb2s);

                    } else if(rb3.isChecked())
                    {
                        selected.add(rb3s);
                    } else if(rb4.isChecked())
                    {
                        selected.add(rb4s);

                    }





                    Intent intent = new Intent(QuestionsActivity.this,
                            ProjectDetailsSelectActivity.class);
                    intent.putExtra("q1",h[0]);
                    intent.putExtra("q2",h[1]);
                    intent.putExtra("q3",h[2]);
                    intent.putExtra("a1",selected.get(0));
                    intent.putExtra("a2",selected.get(1));
                    intent.putExtra("a3",selected.get(2));
                    startActivity(intent);
                    finish();



                }else if(question == 4)
                {

                }

            }
        });








    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

        if(checkedId == rb1.getId())
        {
            rb1s = rb1.getText().toString();
        }

        if(checkedId == rb2.getId())
        {
            rb2s = rb2.getText().toString();
        }

        if(checkedId == rb3.getId())
        {
            rb3s = rb3.getText().toString();
        }

        if(checkedId == rb4.getId())
        {
            rb4s = rb4.getText().toString();
        }



    }
}
