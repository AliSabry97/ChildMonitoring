package team.child.childmonitoring;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    private EditText email_edt , password_edt;
    private FirebaseAuth mauth;
    private Button login;
    private String  email , password;
    private TextView createacc, forgotpass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createacc = findViewById(R.id.create_Account);
        forgotpass = findViewById(R.id.forgot_password);
        email_edt = findViewById(R.id.login_email);
        password_edt = findViewById(R.id.login_password);
        login = findViewById(R.id.login_btn);
        mauth = FirebaseAuth.getInstance();


            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    email = email_edt.getText().toString();
                    password = password_edt.getText().toString();

                    Sigin(email, password);

                }
            });


            createacc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent create = new Intent(MainActivity.this, RegisterActivity.class);
                    startActivity(create);
                }
            });


            forgotpass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent paw = new Intent(MainActivity.this, ForgotPassword.class);
                    startActivity(paw);
                }
            });

    }
    public void Sigin(String email, String pass) //sign in method
    {

        if (!TextUtils.isEmpty(email) || !TextUtils.isEmpty(pass)) {

            mauth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                        Intent intent = new Intent(MainActivity.this, StartActivity.class);
                        startActivity(intent);
                        finish();
                    } else {

                        Toast.makeText(MainActivity.this, "Cann't Sign in please type correct email and password", Toast.LENGTH_LONG).show();
                    }
                }

            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser firebaseUser = mauth.getCurrentUser();
        if (firebaseUser!=null)
        {
            Intent intent = new Intent(MainActivity.this, StartActivity.class);
            startActivity(intent);
            finish();
        }
    }


}
