package com.tema1.players;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import com.tema1.goods.Goods;
import com.tema1.helpers.GameEngine;

public class My_Greedy extends My_Basic {
    private int roundsnum = 0;

    public int getRoundsnum() {
        return roundsnum;
    }

    public void setRoundsnum(int roundsnum) {
        this.roundsnum = roundsnum;
    }
    @Override
    public String name() {
        return "GREEDY";
    }

    public void Sheriff(final LinkedList<Player> players) {
        Iterator<Player>  it = players.iterator();
        while (it.hasNext()) {
            Player curr = it.next();
            if (curr.getId() != super.getId()) {
                if (curr.has_bribe()) {
                    super.updateTotalCoins(curr.getBribe());
//                    System.out.println(curr.getTotalCoins());
//                    System.out.println(getTotalCoins());
                } else {
                    if(super.getTotalCoins() >= 16)
                    checking(curr);
                }
                curr.addtoStand();
             //System.out.println(curr.getTotalCoins());
//                System.out.println("aici");
            }
        }

    }

    public void Merchant() {
        super.Merchant();

            if ((roundsnum & 1 )== 0 && super.getBag().size() < Constants.NR_MAX_BAG) {
                //super.setCardsinHand(copy2);
                super.playIllegal();
                //System.out.println(getRoundsnum());
                }

           // for(int i = 0 ; i < super.getBag().size();i++)
      //  System.out.println(super.getBag().get(i).getId());
//        System.out.println(roundsnum);
            }


    @Override
    public void updateLeader(MakeBord updater, LinkedList<Map.Entry<Player, Integer>> leaderBoard, int ID) {
        updater.reload(this, leaderBoard, this.getId());
    }
}