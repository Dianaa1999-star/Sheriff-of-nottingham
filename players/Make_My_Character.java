package com.tema1.players;


public abstract class Make_My_Character {
    public static Player getPlayer(final String playername) {
     switch(playername){
         case "basic":
             return new My_Basic();
         case "greedy":
             return new My_Greedy();
         case "bribed":
             return new My_Bribed();
             default:
                 return null;
     }
    }
}