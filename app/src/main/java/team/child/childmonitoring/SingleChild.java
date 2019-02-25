package team.child.childmonitoring;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class SingleChild extends AppCompatActivity {
    private FragmentAdapter fragmentAdapter;
    private ViewPager viewPager;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_child);

        viewPager=findViewById(R.id.viewpager);
        fragmentAdapter=new FragmentAdapter(getSupportFragmentManager());
        tabLayout=findViewById(R.id.fragments_fo_single_user);


        viewPager.setAdapter(fragmentAdapter);
        tabLayout.setupWithViewPager(viewPager);

        toolbar = findViewById(R.id.Single_child_toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Single Child");
    }
}
