package com.tema1.players;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;

public abstract class MakeBord {
    abstract void reload(My_Basic myBasic, LinkedList<Map.Entry<Player, Integer>> leaderBoard, int ID);

    abstract void reload(My_Greedy myGreedy, LinkedList<Map.Entry<Player, Integer>> leaderBoard, int ID);

    abstract void reload(My_Bribed myBribed, LinkedList<Map.Entry<Player, Integer>> leaderBoard, int ID);
}