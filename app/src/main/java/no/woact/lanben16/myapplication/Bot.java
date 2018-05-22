package no.woact.lanben16.myapplication;

import java.util.Random;


public class Bot {

    MainActivity m;
    Random r = new Random();

    public Bot(MainActivity m) {
        this.m = m;
    }

    public boolean play () {

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (m.getGameTiles()[i][j].getText().toString().equals("")) {
                        int col = r.nextInt(3);
                        int row = r.nextInt(3);

                        if (!m.player1Turn) {
                            if (m.getGameTiles()[col][row].isClickable()) {
                                m.clickBtn(m.getGameTiles()[col][row]);
                                return true;
                            } else {
                                    play();

                            }
                        }
                    }
                }
            }
        return false;
    }
}