package com.tema1.players;

import  com.tema1.goods.Goods;
import com.tema1.goods.GoodsFactory;
import com.tema1.goods.GoodsType;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

public class My_Basic extends Player {
    public LinkedList<Goods>  copy2= new LinkedList<>();
    @Override
    public String name() {
        return "BASIC";
    }
    public void copy(LinkedList<Goods> copy){
        copy2.addAll(copy);
    }
    public void Sheriff(final LinkedList<Player> players){
        Iterator<Player> it = players.iterator();
        while( it.hasNext()){
            Player curr = it.next();
            if(curr.getId() != super.getId() && super.getTotalCoins() >= 16){
                    checking(curr);
                 //  System.out.println(curr.getTotalCoins() + " " + curr.getId());

            }
            curr.addtoStand();
        }
    }

    public void Merchant(){
        playLegal();
        if(super.getBag().isEmpty()){
            playIllegal();
            setDeclaredgood(GoodsFactory.getInstance().getGoodsById(0));
        }
        //this.getCardsinHand().clear();
    }
    final void playLegal(){
        int [] x = new int[Constants.MAX_CARDS_TYPE];
        int maxx = 0, maxProfit = 0,maxId = -1;
        LinkedList<Goods> cardsinHand = super.getCardsinHand();
        // System.out.println(getCardsinHand());
        //copy(cardsinHand);
        Iterator <Goods> it = cardsinHand.iterator();
        while(it.hasNext()){
            Goods currgood = it.next();
            int curId = currgood.getId();
            if(currgood.getType() == GoodsType.Legal){
                x[curId]++;
            }
        }
        Iterator<Goods> it2 = cardsinHand.iterator();
        while(it2.hasNext()){
            Goods curgood = it2.next();
            int curProfit = curgood.getProfit();
            int curId = curgood.getId();
            if(curgood.getType() == GoodsType.Legal && ((maxx == x[curId]) && maxProfit < curProfit) || ((maxx == x[curId]) && (maxProfit == curProfit) &&
                    maxId < curId ) || maxx < x[curId])
            {
                maxx = x[curId];
                setDeclaredgood(curgood);
                maxProfit = curProfit;
                maxId = curId;
            }
        }
        Goods declaredGood = super.getDeclaredgood();
        for(int i = 0 ; i < maxx && this.getBag().size() < Constants.NR_MAX_BAG; ++i){
            this.addItemtoBag(declaredGood);
            this.discardItem(declaredGood);
        }
        //copy(this.getCardsinHand());
        //super.getCardsinHand().clear();
        //System.out.println(getBag());
    }

    final void playIllegal(){
        Goods profitcard = null;
        Iterator<Goods> it = getCardsinHand().iterator();
        while (it.hasNext()){
            Goods currcard = it.next();
            if(currcard.getType() == GoodsType.Illegal && (profitcard == null || profitcard.getProfit() < currcard.getProfit())){
                profitcard = currcard;
            }
        }
        if(profitcard != null && this.getBag().size() < Constants.NR_MAX_BAG){
            addItemtoBag(profitcard);
            discardItem(profitcard);
        }
        //copy(copy2);
        //super.getCardsinHand().clear();
        // System.out.println(getCardsinHand());
        //System.out.println(getBag().get(0).getId());
    }
    public int getRoundsnum() {return 0;}
    public void setRoundsnum(int a) {

    }

    @Override
    public void updateLeader(MakeBord updater, LinkedList<Map.Entry<Player, Integer>> leaderBoard, int ID) {
        updater.reload(this, leaderBoard,this.getId());
    }
}