package com.tema1.helpers;




import com.tema1.main.GameInput;
import com.tema1.goods.Goods;

import java.util.*;

import com.tema1.goods.GoodsFactory;
import  com.tema1.goods.LegalGoods;
import  com.tema1.players.Player;
import com.tema1.players.Make_My_Character;

public abstract class GameEngine {
    private static HashSet<Goods> Legal_cards_in_game;
    private static int rounds;
    private static LinkedList<Player> my_players;
    private static  LinkedList<Goods> games_cards;



    public static LinkedList<Player> getMy_players() {
        return my_players;
    }

    public static void setMy_players(LinkedList<Player> my_players) {
        GameEngine.my_players = my_players;
    }


    public static void take_data(GameInput input) {
        my_players = new LinkedList<>();
        rounds = input.getRounds();
        Legal_cards_in_game = new HashSet<>();
        games_cards = new LinkedList<>();
//        System.out.println(GoodsFactory.getInstance().getGoodsById(6));
        List<String> playernames = input.getPlayerNames();
        makePackage(input.getAssetIds(), Legal_cards_in_game);

        for (String player : playernames) {
            my_players.add(Make_My_Character.getPlayer(player));
        }
        for(int i = 0; i < my_players.size(); i++){
            my_players.get(i).setId(i);
        }
//        for(int i = 0; i <players.size();i++)
//        System.out.println(pl);
    }
   static void makePackage(final List<Integer> assetsIds , final HashSet<Goods>cardtable) {
       Iterator<Integer> it = assetsIds.iterator();
        while(it.hasNext()) {
            Integer it2 = it.next();
            games_cards.add(GoodsFactory.getInstance().getGoodsById((it2)));
            for(int j = 0 ; j < 10; j++)
                cardtable.add(GoodsFactory.getInstance().getGoodsById((j)));

        }
    }
    static public void push_back(Goods card){
            games_cards.add(card);
        }
      static  public LinkedList<Goods> shareCards(final int nrCardstoDraw){
            LinkedList<Goods> drawnCards = new LinkedList<>();
            int i = 0;
            while(i < nrCardstoDraw){
                try {
                    drawnCards.add(games_cards.remove());
                }
                catch (Exception e) {
                    System.out.println("Exception: " + e);
                }
                i++;
            }
            return drawnCards;
        }
    public static void play() {
        int numMaxTurns = my_players.size() * 5;
        int roundsTurns = my_players.size() * rounds;
        if (roundsTurns > numMaxTurns) {
            roundsTurns = numMaxTurns;
        }
        int i = 0;
        while(i < roundsTurns){
            if( i % my_players.size() == 0)
                for(Player play : my_players){
                    int number = play.getRoundsnum() + 1;
                    play.setRoundsnum(number);
                }
            Player Sheriff = my_players.get(i % my_players.size());
            Iterator<Player> it =  my_players.iterator();
            while (it.hasNext()) {
                Player Player =it.next();
                if (Player != Sheriff) {
                    Player.drawCards();
                    Player.Merchant();
                    //System.out.println(currPlayer.getCardsinHand());
                }
            }
            Sheriff.Sheriff(my_players);
            i++;
        }
       bonus_king_and_queen();
    }
    public static void bonus_king_and_queen() {
        int len = my_players.size();
        int[][] bonustable = new int[10][len];
        int posInPlayers = 0;
        // fiecare jucator isi noteaza numarul de obiecte in bonusTable
        for (Player currPlayer : my_players) {
            currPlayer.countItemsWithBonus(bonustable, posInPlayers++);
        }
//        for(int i = 0 ; i < 10; i++) {
//            for (int j = 0; j < len; j++) {
//                System.out.println(bonustable[i][j]);
//            }
//            System.out.println();
//        }
//        //System.out.println("carti");
        // System.out.println();}
//        for(Goods good : Legal_cards_in_game){
//            for( Goods g :  )
//        }


//        for(Goods goods: cardtable){
//            System.out.println(goods.getId());
//        }
        Iterator<Goods> iterator = Legal_cards_in_game.iterator();
        while (iterator.hasNext()) {
            int kingBonus = 0, queenBonus = 0,ij = 0;
            Goods currCard = iterator.next();
            int currItemId = currCard.getId();
            while(ij <len) {
                if (bonustable[currItemId][ij] >= kingBonus) {
                    queenBonus = kingBonus;
                    kingBonus = bonustable[currItemId][ij];
                } else if (bonustable[currItemId][ij] > queenBonus
                        && bonustable[currItemId][ij] < kingBonus) {
                    queenBonus = bonustable[currItemId][ij];
                }
                ij++;
            }
            int ID = 0;
            int ID2 = 0;
            for(int i = 0; i < len;i++){

                if (bonustable[currItemId][i] == kingBonus && kingBonus != 0 && ID == 0) {
                    my_players.get(i).BonusCoins(((LegalGoods) currCard).getKingBonus());
                    ID =  1;
//                    System.out.println(i);
//                    System.out.println(((LegalGoods)currCard).getKingBonus());
                } else if (bonustable[currItemId][i] == queenBonus && queenBonus != 0 && ID2 == 0) {
//                    System.out.println(i);
                    ID2 = 1;
                    my_players.get(i).BonusCoins(((LegalGoods) currCard).getQueenBonus());
//                    System.out.println(((LegalGoods)currCard).getQueenBonus());
                }
            }
        }
        }
    public static int getRounds() {
        return rounds;
    }

    public static void setRounds(int rounds) {
        GameEngine.rounds = rounds;
    }
}
