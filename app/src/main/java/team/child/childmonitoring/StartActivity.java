package team.child.childmonitoring;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class StartActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private FirebaseUser firebaseUser;
    private FirebaseAuth mauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        toolbar=findViewById(R.id.start_activity_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Child Monitoring");
        mauth=FirebaseAuth.getInstance();
        firebaseUser=mauth.getCurrentUser();



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
