package team.child.childmonitoring;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.provider.CallLog;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.Manifest;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;



public class StartActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private FirebaseUser firebaseUser;
    private FirebaseAuth mauth;
    private TextView textView;
    private static  final int Permission_Read_call_log=100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        toolbar=findViewById(R.id.start_activity_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Child Monitoring");
        mauth=FirebaseAuth.getInstance();
        firebaseUser=mauth.getCurrentUser();
        textView=findViewById(R.id.calllogs);




        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.M && checkSelfPermission(android.Manifest.permission.READ_CALL_LOG)!= PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.WRITE_CALL_LOG ) != PackageManager.PERMISSION_GRANTED)
            requestPermissions(new String[ ] {android.Manifest.permission.READ_CALL_LOG , Manifest.permission.WRITE_CALL_LOG},Permission_Read_call_log);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.start_actvitiy_menu,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId())
        {
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                sendtologin();
                break;
            case R.id.current_users:
                Intent currentuser=new Intent(StartActivity.this,CurrentUsers.class);
                startActivity(currentuser);
                break;
            case R.id.myprofile:
                Intent profile=new Intent(StartActivity.this,UserProfile.class);
                startActivity(profile);
                break;

        }
        return  true;
    }
    private void sendtologin()
    {
        Intent login=new Intent(StartActivity.this,MainActivity.class);
        startActivity(login);
        finish();
    }



}


