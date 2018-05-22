package no.woact.lanben16.myapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Frag_Startsite extends Fragment {

    private static final String TAG = "Frag_Startsite";
    private Button singleP_btn, multiP_btn, leaderB_btn;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_gamesetup, container, false);

        singleP_btn = view.findViewById(R.id.singleP_btn);
        multiP_btn  = view.findViewById(R.id.multiP_btn);
        leaderB_btn = view.findViewById(R.id.leaderB_btn);

        Log.d(TAG, "OnCreateView:Started!");

        singleP_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                ((Container)getActivity()).setViewPager(1);
            }
        });

        multiP_btn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                ((Container)getActivity()).setViewPager(2);
            }
        });

        leaderB_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                ((Container)getActivity()).setViewPager(3);
            }
        });

        return view;
    }
}
