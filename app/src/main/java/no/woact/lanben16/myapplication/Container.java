package no.woact.lanben16.myapplication;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class Container extends AppCompatActivity {

    private static final String TAG = "Container";
    private SectionsPageAdapter sectionsPageAdapter;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_container);


        sectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        viewPager = findViewById(R.id.container);
        setupViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager){
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());

        adapter.addFragment(new Frag_Startsite(), "Frag_Startsite");
        adapter.addFragment(new Frag_SinglePlayerSetup(), "singlePlayer");
        adapter.addFragment(new Frag_MultiPlayerSetup(), "multiPlayer");
        adapter.addFragment(new Frag_Leaderboard(), "leaderboard");

        viewPager.setAdapter(adapter);
    }

    public void setViewPager(int fragmentNumber){ viewPager.setCurrentItem(fragmentNumber); }
}
