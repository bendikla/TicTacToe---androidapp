package no.woact.lanben16.myapplication;




import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;



public class Frag_Leaderboard extends Fragment {
    ListView leaderB;
    TextView leaderBTxt;

    DataBaseHelper myDB;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_leaderboard, container, false);


        leaderB     = view.findViewById(R.id.highscore_list);
        leaderBTxt  = view.findViewById(R.id.leaderB_Title);
        myDB        = new DataBaseHelper(getActivity());

        ArrayList<String> highscore = new ArrayList<>();
        Cursor data = myDB.getListContents();

        if(data.getCount() == 0){
            Toast.makeText(getActivity(), "Database is empty!", Toast.LENGTH_SHORT).show();
        } else {
            while(data.moveToNext()){
                highscore.add(data.getString(data.getColumnIndex("WINNERS")));
                highscore.add(data.getString(data.getColumnIndex("SCORE")));

                String[] dbCols = {"WINNERS", "SCORE"};
                int[]    t     = {android.R.id.text1, android.R.id.text2};

                ListAdapter arrayAdapter = new SimpleCursorAdapter(getActivity(), android.R.layout.simple_list_item_2, data, dbCols, t);
                    leaderB.setAdapter(arrayAdapter);
            }
        }
        leaderBTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Container)getActivity()).setViewPager(0);
            }
        });

        return view;
    }



}