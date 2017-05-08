package com.example.zainuel.services;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class ProjectDetailsSelectActivity extends AppCompatActivity implements
        View.OnClickListener  {

    Button btnDatePicker, btnTimePicker , next;
    EditText txtDate, txtTime , txtAddress;
    private int mYear, mMonth, mDay, mHour, mMinute;

    FirebaseDatabase mDb;
    DatabaseReference mUserRef, mProjectRef;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_details_select);

        btnDatePicker=(Button)findViewById(R.id.selectDateProjectDetails);
        btnTimePicker=(Button)findViewById(R.id.selectTimeProjectDetails);
        txtDate=(EditText)findViewById(R.id.selectedDateProject);
        txtTime=(EditText)findViewById(R.id.selectedTimeProject);
        next = (Button) findViewById(R.id.next_project_details);
        txtAddress = (EditText) findViewById(R.id.address_select_project);

        next.setOnClickListener(this);
        btnDatePicker.setOnClickListener(this);
        btnTimePicker.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
        mDb = FirebaseDatabase.getInstance();
        mUserRef = mDb.getReference("users/"+mAuth.getCurrentUser().getUid()+"/projects/open");
        mProjectRef = mDb.getReference("openProjects");

    }

    @Override
    public void onClick(View v) {

     if(v == btnDatePicker)
     {

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    if (v == btnTimePicker) {

        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {

                        txtTime.setText(hourOfDay + ":" + minute);
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    if(v == next)
    {
        try {
            mUserRef.child(txtTime.getText() + "," + txtDate.getText()).
                    child("address").setValue(txtAddress.getText().toString());
            mUserRef.child(txtTime.getText() + "," + txtDate.getText()).
                    child("assignedEmployee").setValue("-");
            mUserRef.child(txtTime.getText() + "," + txtDate.getText()).
                    child("address").setValue(txtAddress.getText().toString());
            mUserRef.child(txtTime.getText() + "," + txtDate.getText()).
                    child("employeeRating").setValue("-");
            mUserRef.child(txtTime.getText() + "," + txtDate.getText()).
                    child("name").setValue("Air Conditioner Repair");
            mUserRef.child(txtTime.getText() + "," + txtDate.getText()).
                    child("status").setValue("Request Under Process");
            mUserRef.child(txtTime.getText() + "," + txtDate.getText()).
                    child("time").setValue(txtTime.getText().toString());
            mUserRef.child(txtTime.getText() + "," + txtDate.getText()).
                    child("date").setValue(txtDate.getText().toString());
            mUserRef.child(txtTime.getText() + "," + txtDate.getText()).
                    child("type").setValue("acRepair");
            mUserRef.child(txtTime.getText() + "," + txtDate.getText()).
                    child("uid").setValue(mAuth.getCurrentUser().getUid());

            mUserRef.child(txtTime.getText() + "," + txtDate.getText()).
                    child("questions").child(getIntent().getStringExtra("q1")).
                    setValue(getIntent().getStringExtra("a1"));
            mUserRef.child(txtTime.getText() + "," + txtDate.getText()).
                    child("questions").child(getIntent().getStringExtra("q2")).
                    setValue(getIntent().getStringExtra("a2"));
            mUserRef.child(txtTime.getText() + "," + txtDate.getText()).
                    child("questions").child(getIntent().getStringExtra("q3")).
                    setValue(getIntent().getStringExtra("a3"));







            mProjectRef.child("acRepair").child(mAuth.getCurrentUser().getUid()).child(txtTime.getText() + "," + txtDate.getText()).
                    child("name").setValue("Air Conditioner Repair");

            mProjectRef.child("acRepair").child(mAuth.getCurrentUser().getUid()).
                    child(txtTime.getText() + "," + txtDate.getText()).child("address").setValue(txtAddress.getText().toString());

            mProjectRef.child("acRepair").child(mAuth.getCurrentUser().getUid()).
                    child(txtTime.getText() + "," + txtDate.getText()).child("status").setValue("Waiting for project take up by a agent");

            mProjectRef.child("acRepair").child(mAuth.getCurrentUser().getUid()).child(txtTime.getText() + "," + txtDate.getText()).
                    child("time").setValue(txtTime.getText().toString());
            mProjectRef.child("acRepair").child(mAuth.getCurrentUser().getUid()).child(txtTime.getText() + "," + txtDate.getText()).
                    child("date").setValue(txtDate.getText().toString());
            mProjectRef.child("acRepair").child(mAuth.getCurrentUser().getUid()).child(txtTime.getText() + "," + txtDate.getText()).
                    child("type").setValue("acRepair");
            mProjectRef.child("acRepair").child(mAuth.getCurrentUser().getUid()).child(txtTime.getText() + "," + txtDate.getText()).
                    child("uid").setValue(mAuth.getCurrentUser().getUid());

            Toast.makeText(ProjectDetailsSelectActivity.this,"Project Created", Toast.LENGTH_SHORT).show();
            finish();

        }
        catch (Exception e)
        {
            Toast.makeText(ProjectDetailsSelectActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }





}



}

