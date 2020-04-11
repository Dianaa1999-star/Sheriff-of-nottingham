package com.tema1.players;

import com.tema1.goods.Goods;

import java.util.Comparator;

public class GoodsComparator implements Comparator<Goods> {
    @Override
    public int compare(Goods o1, Goods o2) {
        if(o1.getProfit()  == o2.getProfit()){
            return o2.getId() - o1.getId();
        }
        return o2.getProfit() - o1.getProfit();
    }
}