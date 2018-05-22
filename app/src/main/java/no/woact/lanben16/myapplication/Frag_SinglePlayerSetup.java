package no.woact.lanben16.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class
Frag_SinglePlayerSetup extends Fragment {
    MainActivity m = new MainActivity();

    TextView gameTitle, pl1Txt;
    EditText pl1Name;
    Button startbtn;




    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_singleplayersetup, container, false);

        gameTitle    = view.findViewById(R.id.gameTitle);
        pl1Txt       = view.findViewById(R.id.info_username);
        pl1Name      = view.findViewById(R.id.Player1Name);
        startbtn    = view.findViewById(R.id.startBtn);


        startbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String pl1 = getPlayer1Name("player1");

                if (!m.checkUsernames(pl1Name)) {
                    Toast.makeText(getActivity(), "Wait, please enter a username", Toast.LENGTH_SHORT).show();
                } else if (pl1 != null) { //Connect AI
                    Intent singlP = new Intent(getActivity(), MainActivity.class);
                    singlP.putExtra("singlP", pl1);
                    startActivity(singlP);
                }
            }
        });

        gameTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Container)getActivity()).setViewPager(0);
            }
        });



       return view;

    }

    public String getPlayer1Name(String player){

        if(player.equals("player1")){
            player = pl1Name.getText().toString();
        }
        return player;

    }


}
