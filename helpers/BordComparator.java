package com.tema1.helpers;

import com.tema1.players.Player;

import java.util.Comparator;
import java.util.Map;

public class BordComparator implements Comparator<Map.Entry<Player, Integer>> {
    @Override
    public int compare(Map.Entry<Player, Integer>o1, Map.Entry<Player, Integer> o2) {
        return o2.getValue() - o1.getValue();
    }
}

