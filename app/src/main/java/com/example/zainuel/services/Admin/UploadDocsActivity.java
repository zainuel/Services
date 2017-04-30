package com.example.zainuel.services.Admin;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.zainuel.services.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class UploadDocsActivity extends AppCompatActivity {

    final int PICK_IMAGE_REQUEST = 007;
    Button chooseImage,  uploadImages,delImg;
    EditText selectedImageName;
    ImageView selectedImgPreview;
    Uri filePath;
    ProgressDialog pd;
    UploadTask uploadTask;
    FirebaseStorage storage;
    StorageReference storageRef;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_docs);

        storage = FirebaseStorage.getInstance();
        storageRef=storage.getReference();
        mAuth = FirebaseAuth.getInstance();
        uploadImages = (Button) findViewById(R.id.img_uplod_admin);
        chooseImage = (Button)findViewById(R.id.choose_img_but_admin);
        selectedImgPreview = (ImageView)findViewById(R.id.adminSelectedImageView);
        selectedImageName =(EditText)findViewById(R.id.sel_img_nam_admin);
        delImg=(Button)findViewById(R.id.del_img_admin);

        pd = new ProgressDialog(this);
        pd.setCanceledOnTouchOutside(false);
        pd.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                uploadTask.cancel();

            }
        });
        pd.setMessage("Uploading....");

        delImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!selectedImageName.getText().toString().equals(null))
                {
                    pd.show();

                    StorageReference childRef = storageRef.
                            child(selectedImageName.getText().toString());

                    childRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // File deleted successfully

                            pd.dismiss();
                            Toast.makeText(UploadDocsActivity.this, "Deletion successful", Toast.LENGTH_SHORT).show();


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Uh-oh, an error occurred!

                            pd.dismiss();
                            Toast.makeText(UploadDocsActivity.this, "Deletion Failed -> " + exception, Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {

                    Toast.makeText(UploadDocsActivity.this, "Enter a valid image name", Toast.LENGTH_SHORT).show();
                }

            }
        });


        chooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
            }
        });

        uploadImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(filePath != null) {

                    pd.show();

                    StorageReference childRef = storageRef.child("userdocs").child(mAuth.getCurrentUser().getEmail()).
                            child(selectedImageName.getText().toString());

                    //uploading the image
                    uploadTask = childRef.putFile(filePath);

                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            pd.dismiss();
                            Toast.makeText(UploadDocsActivity.this, "Upload successful", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            pd.dismiss();
                            Toast.makeText(UploadDocsActivity.this, "Upload Failed -> " + e, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else {
                    Toast.makeText(UploadDocsActivity.this, "Select an image", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }






    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();

            try {
                //getting image from gallery


                Bitmap bitmap = MediaStore.Images.Media.getBitmap(UploadDocsActivity.this.getContentResolver(), filePath);

                //Setting image to ImageView
                selectedImgPreview.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}





