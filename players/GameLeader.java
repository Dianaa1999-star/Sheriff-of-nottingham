package com.tema1.players;
import java.util.AbstractMap;
import java.util.LinkedList;
import java.util.Map;


public class GameLeader extends  MakeBord{
    @Override
    public void reload(My_Greedy myGreedy, LinkedList<Map.Entry<Player, Integer>> leaderBoard, int ID) {
        leaderBoard.add(new AbstractMap.SimpleEntry<>(myGreedy, myGreedy.Score()));
        ID = myGreedy.getId();
    }

    @Override
    public void reload(My_Basic myBasic, LinkedList<Map.Entry<Player, Integer>> leaderBoard, int ID) {
        leaderBoard.add(new AbstractMap.SimpleEntry<>(myBasic, myBasic.Score()));
        ID = myBasic.getId();
    }

    @Override
    public void reload(My_Bribed myBribed, LinkedList<Map.Entry<Player, Integer>> leaderBoard, int ID) {
        leaderBoard.add(new AbstractMap.SimpleEntry<>(myBribed, myBribed.Score()));
        ID = myBribed.getId();
    }
}