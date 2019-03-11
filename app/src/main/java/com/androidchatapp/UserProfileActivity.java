package com.androidchatapp;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

//import com.google.firebase.storage.StorageReference;


public class UserProfileActivity extends AppCompatActivity {
    private static final int RESULT_LOAD_IMAGE = 1;

    EditText emailTxt,statusTxt,dobTxt;
    TextView user_profile_name;
    ImageView btn_upload,usrImage,check_btn;//,drawerImage;

    private ImageButton mSelectImage;

   // private StorageReference mstorage;
    private static final int GALLERY_INTENT = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        emailTxt = (EditText) findViewById(R.id.user_email);
        statusTxt = (EditText) findViewById(R.id.user_status);
        dobTxt = (EditText)findViewById(R.id.user_dob);

        user_profile_name = (TextView) findViewById(R.id.user_profile_name);
        usrImage = (ImageView) findViewById(R.id.user_profile_photo);
        btn_upload = (ImageView) findViewById(R.id.camera_thumbnail);
        check_btn = (ImageView) findViewById(R.id.check_save_all);


        user_profile_name.setText(UserDetails.username);

        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view.getId() == R.id.camera_thumbnail){
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent,RESULT_LOAD_IMAGE);

                }


            }

        });

        check_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
            emailTxt.setText(UserDetails.username+"@hotmail.com");
            statusTxt.setText("Fearfully and wonderfully made");

            }
        });


        }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK  && data != null){
            Uri selectedImage = data.getData();
            usrImage.setImageURI(selectedImage);
           //drawerImage.setImageDrawable(usrImage.getDrawable());

        }
    }


    }

