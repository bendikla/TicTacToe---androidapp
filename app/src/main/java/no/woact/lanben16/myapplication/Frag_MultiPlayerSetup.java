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

public class Frag_MultiPlayerSetup extends Fragment {

    MainActivity m = new MainActivity();

    TextView pl1Txt, pl2Txt, gameTitle;
    EditText pl1Name, pl2Name;
    Button startGame;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_multiplayersetup, container, false);


        pl1Txt    = view.findViewById(R.id.Player1Info);
        pl2Txt    = view.findViewById(R.id.Player2Info);
        gameTitle = view.findViewById(R.id.gameTitle);
        pl1Name   = view.findViewById(R.id.Player1Name);
        pl2Name   = view.findViewById(R.id.Player2Name);
        startGame = view.findViewById(R.id.startBtn);

        m.checkUsernames(pl1Name);
        m.checkUsernames(pl2Name);

        startGame.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String pl1 = getPlayerNames("player1");
                String pl2 = getPlayerNames("player2");

                if (!m.checkUsernames(pl1Name) && !m.checkUsernames(pl2Name) || !m.checkUsernames(pl1Name) || !m.checkUsernames(pl2Name)) {
                    Toast.makeText(getActivity(), "Wait, please enter two usernames", Toast.LENGTH_SHORT).show();
                } else {


                    Intent startGame = new Intent(getActivity(), MainActivity.class);
                    startGame.putExtra("pl1", pl1);
                    startGame.putExtra("pl2", pl2);
                    startActivity(startGame);
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

    public String getPlayerNames(String player){

        if(player.equals("player1")){

            player = pl1Name.getText().toString();

        } else if (player.equals("player2")) {

            player = pl2Name.getText().toString();
        }
        return player;
    }
}
