package team.child.childmonitoring;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import de.hdodenhof.circleimageview.CircleImageView;

public class CurrentUsers extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FirebaseAuth firebaseAuth;
    private String current_user;
    private DatabaseReference databaseReference;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_users);

        recyclerView=findViewById(R.id.current_users_recyc);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        firebaseAuth=FirebaseAuth.getInstance();
        current_user=firebaseAuth.getCurrentUser().getUid().toString();
        databaseReference=FirebaseDatabase.getInstance().getReference().child("Users");



        toolbar = findViewById(R.id.current_users_appbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Current Childs");

    }

    @Override
    protected void onStart() {
        super.onStart();
        Query query= FirebaseDatabase.getInstance()
                .getReference()
                .child("Monitoring")
                .child(current_user);
        FirebaseRecyclerOptions<User> users=new FirebaseRecyclerOptions.Builder<User>()
                .setQuery(query,User.class)
                .build();

        FirebaseRecyclerAdapter<User,Monitoringadapter>  current_users=new FirebaseRecyclerAdapter<User, Monitoringadapter>(users) {
            @Override
            protected void onBindViewHolder(@NonNull final Monitoringadapter monitoringadapter, final int position, @NonNull User user) {
                final String user_id=getRef(position).getKey();

                databaseReference.child(user_id).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String name , phone , image;
                        name=dataSnapshot.child("username").getValue().toString();
                        phone=dataSnapshot.child("phone").getValue().toString();
                        image=dataSnapshot.child("thumb_link").getValue().toString();


                        monitoringadapter.setname(name);
                        monitoringadapter.setphone(phone);
                        monitoringadapter.setImage(image);

                        monitoringadapter.view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(CurrentUsers.this,SingleChild.class);
                                startActivity(intent);

                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

            @NonNull
            @Override
            public Monitoringadapter onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

                View view= LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.single_user,viewGroup,false);

                return new Monitoringadapter(view);
            }

        };

        current_users.startListening();
        recyclerView.setAdapter(current_users);


    }

    public static class  Monitoringadapter extends RecyclerView.ViewHolder{
            View view;
        public Monitoringadapter(@NonNull View itemView) {

            super(itemView);
            view=itemView;
        }

        public void setname(String name)
        {
            TextView textView=view.findViewById(R.id.single_user_name);

            textView.setText(name);
        }

        public void setphone(String phone)
        {
            TextView textView=view.findViewById(R.id.single_user_phone);
            textView.setText(phone);
        }

        public void setImage (String uri)
        {
            CircleImageView circleImageView=view.findViewById(R.id.single_user_image);
            Picasso.get().load(uri).placeholder(R.drawable.avatar).into(circleImageView);
        }
    }
}
