package team.child.childmonitoring;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;

public class UserProfile extends AppCompatActivity {
    private Toolbar toolbar;

    private CircleImageView circle_profile_img;
    private Button upload_photo , get_qr_code , scancode;
    private TextView username_text ;

    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private StorageReference storageReference;


    private String current_user;
    private static final int mycode = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        circle_profile_img=findViewById(R.id.profilepic);
        upload_photo=findViewById(R.id.upload_photo);
        get_qr_code=findViewById(R.id.get_qr_code);
        username_text=findViewById(R.id.display_name);
        scancode=findViewById(R.id.scan_code);



        firebaseAuth=FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        current_user=firebaseAuth.getCurrentUser().getUid();

        databaseReference= FirebaseDatabase.getInstance().getReference().child("Users");



        toolbar = findViewById(R.id.userprofile_appbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("MyProfile");

        get_qr_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent qr_intent=new Intent(UserProfile.this,QrCodeActivity.class);
                startActivity(qr_intent);
            }
        });

        scancode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent scan_code=new Intent(UserProfile.this,ScanCode.class);
                startActivity(scan_code);
            }
        });


        databaseReference.child(current_user).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                username_text.setText(dataSnapshot.child("username").getValue().toString());
               if (dataSnapshot.child("picture_link").getValue()!="default")
               {
                   Picasso.get().load(dataSnapshot.child("thumb_link").getValue().toString()).placeholder(R.drawable.avatar).into(circle_profile_img);
               }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





        //to Upload Photo to database

        upload_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pick = new Intent();
                pick.setType("image/*");
                pick.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(pick, "Select Picture"), mycode);
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode==mycode && resultCode==RESULT_OK)
        {
            Uri imageuri;
            imageuri=data.getData();
            CropImage.activity(imageuri)
                    .setAspectRatio(1,1)
                    .start(this);
        }

        if (requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE)

        {
         CropImage.ActivityResult activityResult=CropImage.getActivityResult(data);

            if (resultCode==RESULT_OK)
            {
                Uri resulturi=activityResult.getUri();


                File thumb_file=new File(resulturi.getPath());
                    Bitmap thumb_bitmap=null;

                try {
                    thumb_bitmap=new Compressor(this)
                            .setMaxHeight(200)
                            .setMaxWidth(200)
                            .setQuality(80)
                            .compressToBitmap(thumb_file);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                thumb_bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
                final byte [] thumbnail=baos.toByteArray();


                StorageReference fileup=storageReference.child("profilepictures").child(current_user+".jpg");

                final StorageReference thumb=storageReference.child("profilepictures").child("thumbnails").child(current_user+".jpg");



                fileup.putFile(resulturi).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if (task.isSuccessful())
                        {
                            task.getResult().getMetadata().getReference().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    String down_url;
                                    down_url=uri.toString();
                                    databaseReference.child(current_user).child("picture_link").setValue(down_url);

                                }
                            });

                            UploadTask uploadTask=thumb.putBytes(thumbnail);

                            uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                    if (task.isSuccessful())
                                    {
                                        task.getResult().getMetadata().getReference().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                String thumb_uri;
                                                thumb_uri=uri.toString();
                                                databaseReference.child(current_user).child("thumb_link").setValue(thumb_uri);
                                            }
                                        });
                                    }
                                }
                            });
                        }
                    }
                });
            }
        }
    }
}
