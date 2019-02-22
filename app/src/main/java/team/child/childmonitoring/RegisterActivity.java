package team.child.childmonitoring;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {
    private EditText username_edt, password_edt, email_edt, age_edt;
    private CountryCodePicker countryCodePicker;
    private String username, email, password, age ,gender , type;
    Integer gender_id;
    private RadioGroup radioGroup;
    Button submit;
    TextView createaccount, forgotpassword;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private Button register;
    private RadioButton radioButton;
    private Spinner spinner;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username_edt = findViewById(R.id.name_of_user);
        password_edt = findViewById(R.id.password_of_user);
        email_edt = findViewById(R.id.email_of_user);
        age_edt = findViewById(R.id.age_of_user);
        submit = findViewById(R.id.login_btn);
        forgotpassword = findViewById(R.id.forgot_password);
        createaccount = findViewById(R.id.create_Account);
        register = findViewById(R.id.register);
        radioGroup=findViewById(R.id.gender);
        spinner=findViewById(R.id.select_type);







        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");

        countryCodePicker = findViewById(R.id.countrypicker);

        List<String> types=new ArrayList<>();
        types.add("Parent");
        types.add("Child");
        ArrayAdapter<String > spinneradapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,types);
        spinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinneradapter);

        type="parent";



        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        type=adapterView.getSelectedItem().toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
              final String  country_code = String.valueOf(countryCodePicker.getSelectedCountryCodeAsInt());
              final String  country_name = countryCodePicker.getSelectedCountryName();
                gender_id=radioGroup.getCheckedRadioButtonId();
                radioButton=findViewById(gender_id);
                gender=radioButton.getText().toString();

                Toast.makeText(RegisterActivity.this,type , Toast.LENGTH_LONG).show();

                email = email_edt.getText().toString();
                password = password_edt.getText().toString();
                age = age_edt.getText().toString();
                username = username_edt.getText().toString();
                final HashMap<String , String > users=new HashMap<String, String>();
                users.put("username",username);
                users.put("password",password);
                users.put("email",email);
                users.put("Age",age);
                users.put("gender",gender);
                users.put("countrycode",country_code);
                users.put("countryname",country_name);
                users.put("type",type);

                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            final String user_id=mAuth.getCurrentUser().getUid();
                            databaseReference.child(user_id).setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(RegisterActivity.this,"Successfully Created..." , Toast.LENGTH_LONG).show();

                                    Intent start_intent=new Intent(RegisterActivity.this,StartActivity.class);
                                    startActivity(start_intent);
                                    finish();
                                }
                            });


                        }

                    }
                });
            }
        });

    }

}



