package com.tema1.players;

import com.tema1.goods.Goods;
import com.tema1.goods.GoodsType;
import com.tema1.goods.IllegalGoods;
import com.tema1.helpers.GameEngine;

import java.util.*;

public abstract class Player {
    private HashMap<Goods, Integer> merchantStand;
    private LinkedList<Goods> cardsinHand;
    private ArrayList<Goods> bag;
    private int totalcoins, bribe;
    private Goods declaredgood;
    private int id;
    public int score;

    Player() {
        totalcoins = Constants.START_COINS;
        declaredgood = null;
        bribe = 0;
        merchantStand = new HashMap<Goods, Integer>();
        cardsinHand = new LinkedList<>();
        bag = new ArrayList<>();
    }

    public HashMap<Goods, Integer> getMerchstand() {
        return merchantStand;
    }

    public String name() {
        return "this";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCardsinHand(LinkedList<Goods> cardsinHand) {
        this.cardsinHand = cardsinHand;
    }

    final void checking(final Player player) {
        int SumInter = player.computePenalty();
        //System.out.println(SumInter);
        totalcoins = totalcoins + SumInter;
        player.change(SumInter);
        //System.out.println(player.getTotalcoins());
       //System.out.println(SumInter);

    }

    private int computePenalty() {
        int SumInter = 0;
        boolean is_a_liar = false;
        ArrayList<Goods> removedCards = new ArrayList<>();
        Iterator<Goods> it = bag.iterator();
        while (it.hasNext()) {
            Goods i = it.next();
            if ( i != declaredgood || i.getType() == GoodsType.Illegal) {
                is_a_liar = true;
                SumInter = SumInter + i.getPenalty();
                removedCards.add(i);
                GameEngine.push_back(i);
            }
        }
        if (is_a_liar == false) {
            Iterator<Goods> iterator2 = bag.iterator();
            while (iterator2.hasNext()){
                Goods i = iterator2.next();

                SumInter = SumInter - i.getPenalty();
        }
        }
        bag.removeAll(removedCards);
        return SumInter;
    }

    final void change(int Sum) {
        totalcoins = totalcoins - Sum;
    }

    final void addtoStand() {
        Iterator<Goods> it = getBag().iterator();
        while (it.hasNext()) {
            Goods i = it.next();
            pushItem(i);
            if (i.getType() == GoodsType.Illegal) {
                Map<Goods, Integer> bonus = ((IllegalGoods) i).getIllegalBonus();
                //System.out.println(bonus);
                Iterator<Goods> iterator = bonus.keySet().iterator();
                while (iterator.hasNext()) {
                    Goods key = iterator.next();
                    int x = bonus.get(key);
                    // System.out.println(key);
                    //System.out.println(x);
                    while (x > 0) {
                        pushItem(key);
                        x--;
                    }
                }
            }
        }
//          for(Goods good:merchantStand.keySet()) {
//              int x = merchantStand.get(good);
//              System.out.println(good.getId() + " " + x);
//          }
        bag.clear();
    }

    public ArrayList<Goods> getBag() {
        return bag;
    }
    public abstract void  setRoundsnum(int a);
    public abstract int  getRoundsnum();

    private void pushItem(final Goods card) {
        try {
            int numItems = merchantStand.get(card);
            merchantStand.put(card, numItems + 1);
        } catch (NullPointerException nullExc) {
            merchantStand.put(card, 1);
        }
    }

    public final void drawCards() {
        if(cardsinHand.size() != 0){
            cardsinHand.clear();
        }
        cardsinHand.addAll(GameEngine.shareCards(Constants.NR_MAX_CARDS));
//        for(int i =0; i < cardsinHand.size();i++)
//        System.out.println(cardsinHand.get(i).getId());
    }

    final int getMaxBag() {
        return Constants.NR_MAX_BAG;
    }

    public final void countItemsWithBonus(final int[][] bonusTable, final int posInPlayers) {
        Iterator<Map.Entry<Goods, Integer>>  it = merchantStand.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Goods, Integer> currItem = it.next();
            Goods currCardKey = currItem.getKey();
            if (currCardKey.getType() == GoodsType.Legal) {
                bonusTable[currCardKey.getId()][posInPlayers] += currItem.getValue();
            }
        }
    }

    public void setTotalcoins(int totalcoins) {
        this.totalcoins = totalcoins;
    }

    public final void BonusCoins(final int numCoins) {
        totalcoins += numCoins;
    }

    final int getTotalCoins() {
        return totalcoins;
    }

    final void updateTotalCoins(final int exchangedSum) {
        totalcoins += exchangedSum;
    }

    public int getTotalcoins() {
        return totalcoins;
    }

    final int Score() {
        score = totalcoins;
        Iterator<Map.Entry<Goods, Integer>> it = merchantStand.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Goods, Integer> currItem = it.next();
            score += currItem.getKey().getProfit() * currItem.getValue();
        }
       // System.out.println(score);
        return score;
    }

    final int getMaxCardType() {
        return Constants.MAX_CARDS_TYPE;
    }

    public abstract void Merchant();

    public boolean has_bribe() {
        if (bribe > 0)
            return true;
        else return false;
    }

    public int getBribe() {
        totalcoins = totalcoins - bribe;
        return bribe;
    }

    public void setBribe(int bribe) {
        this.bribe = bribe;
    }

    public abstract void Sheriff(LinkedList<Player> players);

    public abstract void updateLeader(MakeBord visitor,
                                      LinkedList<Map.Entry<Player, Integer>> leaderBoard, int ID);

    public Goods getDeclaredgood() {
        return declaredgood;
    }

    public void setDeclaredgood(Goods declaredgood) {
        this.declaredgood = declaredgood;
    }

    final void addItemtoBag(final Goods good) {
        this.getBag().add(good);
    }

    final void discardItem(final Goods good) {
        cardsinHand.remove(good);
    }

    public LinkedList<Goods> getCardsinHand() {
        return cardsinHand;
    }
}