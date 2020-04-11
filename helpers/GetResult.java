package com.tema1.helpers;

import com.tema1.players.GameLeader;
import java.util.Map;
import java.util.Collections;
import com.tema1.players.Player;
import java.util.Iterator;
import java.util.LinkedList;

public class GetResult {
    public static LinkedList<Map.Entry<Player, Integer>> getResults() {
        GameLeader up = new GameLeader();
        int ID = 0;
        LinkedList<Map.Entry<Player, Integer>> gameleader = new LinkedList<>();
        // folosind mecanismul unui visitor pattern, fiecare jucator isi aduaga scorul in tabel
        Iterator<Player> it = GameEngine.getMy_players().iterator();
        while (it.hasNext()) {
            Player currPlayer = it.next();
            currPlayer.updateLeader(up, gameleader,ID);
        }
        // se sorteaza tabelul dupa scor
        BordComparator bord = new BordComparator();
        Collections.sort(gameleader,bord);
        // System.out.println(leaderBoard);
        return gameleader;
    }
}