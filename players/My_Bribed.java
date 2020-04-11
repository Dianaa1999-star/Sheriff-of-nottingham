package com.tema1.players;


import java.util.*;

import com.tema1.goods.Goods;
import com.tema1.goods.GoodsFactory;
import com.tema1.goods.GoodsType;
import java.util.ArrayList;

public class My_Bribed extends My_Basic {
    private Player left = null;
    private  Player right = null;
    public void Sheriff(final LinkedList<Player> players){
        if (left == null){
            int currIndex = players.indexOf(this);
           // System.out.println(currIndex);
            if(currIndex != 0)
            {
                left = players.get(currIndex - 1);
            }
            else {
                left = players.get(players.size() - 1);
            }
            right = players.get((currIndex + 1) % players.size());

        }
        if (left.getId() != this.getId()){
            if(super.getTotalCoins() >= 16) {
                checking(left);
               //System.out.println("da");
            }
            left.addtoStand();
        }
        if ( right.getId() != left.getId()){
            if(super.getTotalCoins() >= 16) {
                //System.out.println("Da");
                checking(right);
            }
            right.addtoStand();
        }
        //System.out.println();
       // System.out.println(super.getTotalCoins());
        //System.out.println(" ce bribed ");
        for(Player player : players){
            if( player.getId() != right.getId() && player.getId() != left.getId() && player.getId() != super.getId()){
               if(player.has_bribe() == true)
                   super.updateTotalCoins(player.getBribe());
                player.addtoStand();
            }
        }

//        System.out.println("right" + right.getId());
//        System.out.println("left"  + left.getId());
    }


    public void Merchant() {
        ArrayList<Goods> ilegalinHand = new ArrayList<>();
        ArrayList<Goods> legalinHand = new ArrayList<>();
        //System.out.println(super.getTotalCoins());
        int sum_ava = super.getTotalCoins();
        int sum_ilegal = 0;
        int max_legal ;
        int max_ilegal = 8;
        for (Goods currgood : super.getCardsinHand()) {
            if (currgood.getType() == GoodsType.Illegal)
                ilegalinHand.add(currgood);
        }

        for (Goods currgood : super.getCardsinHand()) {
            if (currgood.getType() == GoodsType.Legal)
                legalinHand.add(currgood);
        }
        GoodsComparator comparator = new GoodsComparator();
        GoodsComparator comp = new GoodsComparator();
        Collections.sort(ilegalinHand, comparator);
//        System.out.println("carile");
//        System.out.println();
        Collections.sort(legalinHand, comp);
        //for(int i = 0; i <ilegalinHand.size();i++)
        //System.out.println(ilegalinHand.get(i).getId());
        if (ilegalinHand.size() == 0 || super.getTotalcoins() <=  Constants.MIN_BRIBE) {
            super.setBribe(0);
            super.playLegal();
            if (super.getBag().size() == 0) {
                super.playIllegal();
            }
        } else {
            super.setDeclaredgood(GoodsFactory.getInstance().getGoodsById(0));
            int maxitems;
            if (super.getTotalCoins() <= Constants.MAX_BRIBE) {
                maxitems = Constants.NR_MAX_LOW_CASH;
            } else maxitems = Constants.NR_MAX_BAG;
//            System.out.println(" oac");
//            System.out.println(maxitems);
//            System.out.println(" ");
           for(int i = 0 ; i < ilegalinHand.size() && i < Constants.NR_MAX_BAG;i++){
               sum_ilegal = sum_ilegal + 4;
           }

           for(int j = 0 ; j < ilegalinHand.size() && j < Constants.NR_MAX_BAG;j++) {
               if (sum_ilegal >= super.getTotalCoins()) {
                   while (sum_ilegal >= super.getTotalCoins()) {
                       max_ilegal--;
                       sum_ilegal = sum_ilegal - ilegalinHand.get(j).getPenalty();
                   }
               }
           }
         // System.out.println(max_ilegal +" " + " va fi");
            for (int i = 0; i < max_ilegal  && i < ilegalinHand.size(); i++) {
                if(sum_ava >  4){
                Goods currgood = ilegalinHand.get(i);
                super.getBag().add(currgood);
                super.getCardsinHand().remove(currgood);
                sum_ava = sum_ava - 4;
            }
            }
            //System.out.println( " disponibil" +  " " + sum_ava);
            //System.out.println(" suma totala" + super.getTotalcoins());
//            System.out.println(super.getBag().size() + "punga");
//            System.out.println(maxitems);
            //System.out.println(legalinHand.size());
            max_legal =  maxitems - super.getBag().size();
                for (int l = 0; l < max_legal && l < legalinHand.size(); l++) {
                    if(sum_ava > 2){
                    Goods cur = legalinHand.get(l);
                    super.getBag().add(cur);
                    super.getCardsinHand().remove(cur);
                    sum_ava = sum_ava - 2;
                }
                }
                int count = 0;
            for(int i = 0 ; i < super.getBag().size();i++){
                if(super.getBag().get(i).getType() == GoodsType.Illegal){
                    count++;
                }
            }
            //this.getCardsinHand().clear();
            super.setBribe(count <= Constants.NR_MAX_LOW_CASH ? Constants.MIN_BRIBE : Constants.MAX_BRIBE);
           // System.out.println(getBribe());
        }
    }
    @Override
    public String name() {
        return "BRIBED";
    }

    @Override
    public void updateLeader(MakeBord updater, LinkedList<Map.Entry<Player, Integer>> leaderBoard, int ID) {
        updater.reload(this, leaderBoard,this.getId());
    }
}
