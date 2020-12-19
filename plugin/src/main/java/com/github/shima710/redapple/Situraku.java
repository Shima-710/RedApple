package com.github.shima710.redapple;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class Situraku implements Listener {
    public static RedApple plugin;
    public Situraku(RedApple instance) { plugin = instance; }

    public static boolean checkSituraku(Player player){
        for(int i=0;i<26;i++){//playerBox内でfor
            if(RedApple.playerBox[i][1].equals(player.getName())){//playerBox内で名前が一致する箱を見つけたら
                if(Integer.parseInt(RedApple.playerBox[i][2])<=-10){//-10以下なら
                    return true;
                }
            }
        }
        return false;
    }

    public static void situraku(Player player){

    }

    public static void kyuusai(Player player, Player player2, Integer num){

    }
}
