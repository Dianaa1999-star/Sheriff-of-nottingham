package com.tema1.main;

import com.tema1.goods.Goods;
import  com.tema1.helpers.GameEngine;

import java.util.ArrayList;
import java.util.List;
import fileio.FileSystem;
import com.tema1.helpers.GetResult;
import com.tema1.players.Player;

import java.util.*;
public  final class Main{
    private static final class GameInputLoader {
        // DO NOT MODIFY
        private final String mInputPath;
        private final String mOutputPath;

        GameInputLoader(final String inputPath, final String outputPath) {
            mInputPath = inputPath;
            mOutputPath = outputPath;
        }

        public GameInput load() {
            List<Integer> assetsIds = new ArrayList<>();
            List<String> playerOrder = new ArrayList<>();
            int rounds = 0;
            int noPlayers = 0;
            int noGoods = 0;

            try {
                FileSystem fs = new FileSystem(mInputPath, mOutputPath);

                rounds = fs.nextInt();
                noPlayers = fs.nextInt();

                for (int i = 0; i < noPlayers; ++i)
                    playerOrder.add(fs.nextWord());
                noGoods = fs.nextInt();
                for (int i = 0; i < noGoods; ++i)
                    assetsIds.add(fs.nextInt());

                fs.close();

            } catch (Exception e1) {
                e1.printStackTrace();
            }

            return new GameInput(rounds, assetsIds, playerOrder);
        }
    }
    public static void main(final String[] args) {
        GameInputLoader gameInputLoader = new GameInputLoader(args[0],args[1]);
        GameInput gameInput = gameInputLoader.load();
        // citeste datele din fisier
        GameEngine.take_data(gameInput);
        GameEngine.play();

        LinkedList<Map.Entry<Player, Integer>> leaderBoard = GetResult.getResults();
        LinkedList<Player> my_players = new LinkedList<>();
        my_players= GameEngine.getMy_players();
        for (Map.Entry<Player, Integer> pair : leaderBoard) {
            System.out.println( pair.getKey().getId() + " " + pair.getKey().name() + " "+ pair.getValue());


            // TODO: implement homework logic
        }
//        for(Player player:GameEngine.getMy_players()){
//            System.out.println("jucator");
//            System.out.println(player.getId());
//            for(Goods item : player.getMerchstand().keySet()){
//               System.out.println(item.getId() + " " + player.getMerchstand().get(item));
//
//            }
//        }
    }
}