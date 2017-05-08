package com.example.zainuel.services;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.crazyhitty.chdev.ks.firebasechat.utils.Constants;
import com.example.zainuel.services.Admin.AdminHomeActivity;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity
        implements GoogleApiClient.OnConnectionFailedListener {

    SharedPreferences status;
    ValueEventListener listener;

    LinearLayout lg_lin_lay;

    private static final int RC_SIGN_IN = 100;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    Intent signInIntent;

    private FirebaseAuth mAuth;
    private FirebaseDatabase mDB;
    private DatabaseReference mUsersRef;
    private DatabaseReference mSpRef;
    DatabaseReference mNewUserRef;
    private View signInHide;

    private View mProgressView;
    GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Login");

        lg_lin_lay = (LinearLayout) findViewById(R.id.login_lin_lay);

        // Firebase Authentication
        mAuth = FirebaseAuth.getInstance();
        checkPlayServices();
        mDB = FirebaseDatabase.getInstance();
        mUsersRef = mDB.getReference(Constants.ARG_USERS);
        mSpRef = mDB.getReference(Constants.ARG_SP);



        signInHide = findViewById(R.id.sign_in_hide);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        //google SignIn
        SignInButton signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_WIDE);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });
        mProgressView = findViewById(R.id.login_progress);
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    signInIntent = Auth.GoogleSignInApi
                            .getSignInIntent(mGoogleApiClient);
                    startActivityForResult(signInIntent, RC_SIGN_IN);
                    showProgress(true);
                } else {
                    Toast.makeText(this, "Contacts read Permission DENIED", Toast.LENGTH_SHORT)
                            .show();
                }
            }
        }
    }

    private void attemptLogin() {
        showProgress(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(LoginActivity.this,
                    new String[]{"android.permission.READ_CONTACTS"},1);
        } else {
            signInIntent = Auth.GoogleSignInApi
                    .getSignInIntent(mGoogleApiClient);
            startActivityForResult(signInIntent, RC_SIGN_IN);
        }
    }

    // *****************************  google SignIn ***********************************************
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount account = result.getSignInAccount();
            firebaseAuthWithGoogle(account);
        } else {
            showProgress(false);
            Toast.makeText(this, "SignIn failed, Please try again", Toast.LENGTH_SHORT).show();
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            status = getSharedPreferences("login_status", Context.MODE_PRIVATE);
                            status.edit().putBoolean("in", true).apply();


                            final int  type = getIntent().getIntExtra("type",3);

                            if(type == 1) {
                                mNewUserRef = mDB.getReference("users");
                            }else if(type == 2) {
                                mNewUserRef = mDB.getReference("sp");
                            }

                            listener = new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {

                                    Boolean key=false;
                                    for(DataSnapshot child:dataSnapshot.getChildren())
                                    {
                                        if(child.getKey().toString().equals(mAuth.getCurrentUser().getUid()))
                                        {
                                            key=true;
                                            break;
                                        }
                                    }


                                    if(!key)
                                    {

                                        if(type == 1) {
                                            createUserNode();
                                        }else if(type == 2) {
                                            createSpNode();
                                        }




                                    }


                                    if(type == 1)
                                    {

                                        SharedPreferences tp = getSharedPreferences("type",Context.MODE_PRIVATE);
                                        tp.edit().putInt("type",1).apply();

                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        showProgress(false);
                                        finish();
                                        startActivity(intent);

                                    } else if(type == 2)
                                    {
                                        SharedPreferences tp = getSharedPreferences("type",Context.MODE_PRIVATE);
                                        tp.edit().putInt("type",2).apply();


                                        Intent intent = new Intent(LoginActivity.this, AdminHomeActivity.class);
                                        showProgress(false);
                                        finish();
                                        startActivity(intent);

                                    }


                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            };

                            mNewUserRef.addListenerForSingleValueEvent(listener);


                        }
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            showProgress(false);
                            Toast.makeText(getBaseContext(),
                                    "SignIn failed, Please try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void createUserNode() {
        String uid = mAuth.getCurrentUser().getUid();
        String name = mAuth.getCurrentUser().getDisplayName();
        String email = mAuth.getCurrentUser().getEmail();
        String pURL = mAuth.getCurrentUser().getPhotoUrl().toString();
        mUsersRef.child(uid).child("name").setValue(name);
        mUsersRef.child(uid).child("email").setValue(email);
        mUsersRef.child(uid).child("pURL").setValue(pURL);
        mUsersRef.child(uid).child("uid").setValue(uid);
    }

    private void createSpNode()
    {
        DatabaseReference mSpRef;
        mSpRef= mDB.getReference("sp");
        String uid = mAuth.getCurrentUser().getUid();
        String name = mAuth.getCurrentUser().getDisplayName();
        String email = mAuth.getCurrentUser().getEmail();
        String pURL = mAuth.getCurrentUser().getPhotoUrl().toString();
        mSpRef.child(uid).child("name").setValue(name);
        mSpRef.child(uid).child("email").setValue(email);
        mSpRef.child(uid).child("pURL").setValue(pURL);
        mSpRef.child(uid).child("uid").setValue(uid);
        mSpRef.child(uid).child("rating").setValue("0");
    }


    /**
     * Shows the progress UI and hides the login form.
     */
    private void showProgress(final boolean show) {
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        signInHide.setVisibility(show ? View.GONE : View.VISIBLE);
        signInHide.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                signInHide.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        });

        lg_lin_lay.setVisibility(show ? View.GONE : View.VISIBLE);

        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        mProgressView.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void playServicesErrorDialogue() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Google Play Services Error");
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setMessage("Please Check Your Play Services");
        //alertDialog.setIcon(R.drawable.ic_error_red_500_48dp);
        alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Exit",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        System.exit(0);
                    }
                });
    }

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                Dialog error = apiAvailability
                        .getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST);
                error.setCancelable(false);
                error.setCanceledOnTouchOutside(false);
                error.show();
            } else {
                playServicesErrorDialogue();
            }
            return false;
        }
        return true;
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (listener != null) {
            try {
                mNewUserRef.removeEventListener(listener);
            } catch (Exception e)
            {

            }
        }
    }


}