package no.woact.lanben16.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button[][] tttBtns = new Button[3][3];

    public boolean player1Turn = true;
    public boolean pl1Win = false;
    public boolean pl2Win = false;
    public boolean singlePlayer = false;

    private int roundCount;
    private int player1WPoints;
    private int player2WPoints;
    private int drawPoints;

    TextView wintxt1, wintxt2, player1, player2, winCounter1, winCounter2, drawP;
    Chronometer klokke;
    Button btnStartSite;

    DataBaseHelper myDB;
    DatabaseReference ref;
    Bot b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        klokke         = findViewById(R.id.klokke);
        player1        = findViewById(R.id.player1Name);
        player2        = findViewById(R.id.player2Name);
        wintxt1        = findViewById(R.id.winTxt1);
        wintxt2        = findViewById(R.id.winTxt2);
        winCounter1    = findViewById(R.id.winCounter1);
        winCounter2    = findViewById(R.id.winCounter2);
        drawP          = findViewById(R.id.drawCounter);
        btnStartSite   = findViewById(R.id.btnStartsite);
        myDB           = new DataBaseHelper(this);
        ref            = FirebaseDatabase.getInstance().getReference();
        b              = new Bot(this);

        klokke.start();
        setPlayernames();


        //Finner id på spill knappene, og setter onClickListener
       for(int i = 0; i < 3; i++){
           for(int j = 0; j < 3; j++){
               String btnID = "tttBtn_" + i + j; //Alle knappeId'ene starter med tttBtn_ og navnene etter representerer plass i 2D arrayet.
               int iD = getResources().getIdentifier(btnID, "id", getPackageName());
               tttBtns[i][j] = findViewById(iD);
               tttBtns[i][j].setOnClickListener(this);
           }
       }

        if(player2.getText().equals("TTTBOT")){ //Singleplayer
            singlePlayer = true;
        }

        btnStartSite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String pl1Name  = player1.getText().toString();
                String pl1Score = winCounter1.getText().toString();

                String pl2Name = player2.getText().toString();
                String pl2Score = winCounter2.getText().toString();

                int player1S = Integer.parseInt(pl1Score);
                int player2S = Integer.parseInt(pl2Score);

                if(player1S > player2S && player1S != 0){
                    AddData(pl1Name, player1S);
                } else {
                    if(!singlePlayer && player2S != 0) {
                        AddData(pl2Name, player2S);
                    }
                }

                Intent intent = new Intent(MainActivity.this, Container.class);
                startActivity(intent);
            }
        });

    }

    public Button[][] getGameTiles(){
        return tttBtns;
    }


    public void AddData(String player, int wins){
        boolean insertData = myDB.addData(player, wins);

        String id = ref.push().getKey();

        ref.child(id).child("PlayerId").setValue(player);
        ref.child(id).child("Score").setValue(wins);

        if(insertData){
            Toast.makeText(MainActivity.this,"Highscore updated!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this,"ERROR", Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    public void onClick(View v) {
        clickBtn((Button) v);
    }

    public boolean checkUsernames(TextView textaddr){
        String username;
        username = textaddr.getText().toString();

        if(username.equals("")){
            return false;
        } else {
            return true;
        }
    }

    public void clickBtn(Button clickableBtn){
        if(!clickableBtn.getText().toString().equals("")){
            return;
        }

        if(!singlePlayer) {
            if (player1Turn) {
                clickableBtn.setTextColor(getResources().getColor(R.color.TxtColor));
                clickableBtn.setText("X");
                clickableBtn.setClickable(false);

            } else {
                clickableBtn.setTextColor(getResources().getColor(R.color.player2));
                clickableBtn.setText("O");
                clickableBtn.setClickable(false);
            }

            roundCount++;

            if (checkForWin()) {
                if (player1Turn) {
                    player1Wins();
                } else {
                    player2Wins();
                }
            } else if (roundCount == 9) {
                draw();
            } else  {
                player1Turn = !player1Turn;
            }

        } else{

            if (player1Turn) {
                clickableBtn.setTextColor(getResources().getColor(R.color.TxtColor));
                clickableBtn.setText("X");
                clickableBtn.setClickable(false);
                player1Turn = false;

                    if(!checkForWin()) {
                        b.play();
                    }

            } else {
                clickableBtn.setTextColor(getResources().getColor(R.color.player2));
                clickableBtn.setText("O");
                clickableBtn.setClickable(false);
                player1Turn = true;
            }

            roundCount++;

            if (checkForWin()) {
                if (!player1Turn) {
                    player1Wins();
                } else {
                    player2Wins();
                }
            } else if (roundCount == 9) {
                draw();
            }
        }

    }


    public boolean checkForWin(){
        String[][] gameBtns = new String[3][3];

        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                gameBtns[i][j] = tttBtns[i][j].getText().toString();
            }
        }

        for ( int i = 0; i < 3; i++){
            if(gameBtns[i][0].equals(gameBtns[i][1])
                    && gameBtns[i][0].equals(gameBtns[i][2])
                    && !gameBtns[i][0].equals("")){
                return true;
            }
        }

        for ( int i = 0; i < 3; i++){
            if(gameBtns[0][i].equals(gameBtns[1][i])
                    && gameBtns[0][i].equals(gameBtns[2][i])
                    && !gameBtns[0][i].equals("")){
                return true;
            }
        }

        //Diagonal
        if(gameBtns[0][0].equals(gameBtns[1][1])
                && gameBtns[0][0].equals(gameBtns[2][2])
                && !gameBtns[0][0].equals("")){
            return true;
        }
        if(gameBtns[0][2].equals(gameBtns[1][1])
                && gameBtns[0][2].equals(gameBtns[2][0])
                && !gameBtns[0][2].equals("")){
            return true;
        }

        return false;
    }

    public boolean stopClick(){
            if(pl1Win || pl2Win){
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        if(tttBtns[i][j].getText().toString().equals("")) {
                            tttBtns[i][j].setClickable(false);
                            return true;
                        }
                    }
                }
            }
        return false;
    }

    public void player1Wins(){
        player1WPoints++;
        Toast.makeText(this, player1.getText() + " wins!", Toast.LENGTH_SHORT).show();
        updatePoints();
        pl1Win = true;
        stopClick();
        resetBoard();
    }

    public void player2Wins(){
        player2WPoints++;
        Toast.makeText(this, player2.getText() + " wins!", Toast.LENGTH_SHORT).show();
        updatePoints();
        pl2Win = true;
        stopClick();
        resetBoard();
    }

    public void draw(){
        drawPoints++;
        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
        updatePoints();
        resetBoard();
    }

    public void updatePoints(){
       winCounter1.setText(String.valueOf(player1WPoints));
       winCounter2.setText(String.valueOf(player2WPoints));
       drawP.setText(String.valueOf(drawPoints));
    }

    public void resetBoard() {
        for(int i = 0; i < 3; i++){
            for(int j =0; j < 3; j++){
                tttBtns[i][j].setText("");
                tttBtns[i][j].setClickable(true);
            }
        }

        roundCount = 0;
        player1Turn = true;
        pl1Win = false;
        pl2Win = false;

    }


    public void setPlayernames(){
        Intent intent = getIntent();

        String pl1name = intent.getStringExtra("pl1");
        player1.setText(pl1name);

        String pl2name = intent.getStringExtra("pl2");
        player2.setText(pl2name);

        if(pl2name == null) { //Hvis 1Player
            Intent singlP = getIntent();
            String bot = singlP.getStringExtra("singlP");


            if (bot != null) {
                String Bot = "TTTBOT";
                player2.setText(Bot);
                player1.setText(bot);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("roundCount", roundCount);
        outState.putInt("player1WPoints", player1WPoints);
        outState.putInt("player2WPoints", player2WPoints);
        outState.putInt("drawPoints", drawPoints);
        outState.putBoolean("player1Turn", player1Turn);
        outState.putLong("klokke", klokke.getBase());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        roundCount     = savedInstanceState.getInt("roundCount");
        player1WPoints = savedInstanceState.getInt("player1WPoints");
        player2WPoints = savedInstanceState.getInt("player2WPoints");
        drawPoints     = savedInstanceState.getInt("drawPoints");
        player1Turn    = savedInstanceState.getBoolean("player1Turn");
        klokke.setBase(savedInstanceState.getLong("klokke"));

    }
}
