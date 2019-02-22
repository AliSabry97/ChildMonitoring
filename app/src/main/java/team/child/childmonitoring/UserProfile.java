package team.child.childmonitoring;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfile extends AppCompatActivity {
    private Toolbar toolbar;

    private CircleImageView circle_profile_img;
    private Button upload_photo , get_qr_code , scancode;
    private TextView username_text ;
    public  static TextView qr_result_text;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        circle_profile_img=findViewById(R.id.profilepic);
        upload_photo=findViewById(R.id.upload_photo);
        get_qr_code=findViewById(R.id.get_qr_code);
        username_text=findViewById(R.id.display_name);
        scancode=findViewById(R.id.scan_code);
        qr_result_text=findViewById(R.id.result_qr);

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

    }
}
