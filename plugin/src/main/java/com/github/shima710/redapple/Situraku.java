package com.github.shima710.redapple;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class Situraku implements Listener {
    public static RedApple plugin;
    public Situraku(RedApple instance) { plugin = instance; }

    public static boolean checkSituraku(Player player){
        for(Player p:RedApple.gamePlayer){
            if(Integer.parseInt(RedApple.playerMapBox.get(p).get(1)) <= -5){//playerでgetしてlistのindex=1が-5以下なら
                if(!RedApple.situraku.contains(p)) {//失楽園にいないければ
                    return true;
                }
            }
        }
        return false;
    }

    public static void situraku(Player player){
        Bukkit.broadcastMessage(RedApple.separateBar);
        Bukkit.broadcastMessage(""+ChatColor.DARK_RED+ChatColor.BOLD+player.getName()+ChatColor.WHITE+" は負債が"+ChatColor.DARK_RED+ChatColor.BOLD+" 5 "+ChatColor.WHITE+"億円を超えたため失楽園行きとなります");
        Bukkit.broadcastMessage("救済希望者は \"/k <救済額>\" コマンドで負債を肩代わりすることができます");
        Bukkit.broadcastMessage("救済希望者がいない場合は代表一名が \"/s\" コマンドで対象者を失楽園へ送ってください");
        Bukkit.broadcastMessage(RedApple.separateBar);
        RedApple.siturakuChoice = true;
        RedApple.siturakuWho = player;
    }

    public static void kyuusai(Player player, Player player2, Integer num){//するひとされるひときんがく
        RedApple.siturakuChoice = false;
        RedApple.siturakuWho = null;
        Bukkit.broadcastMessage(RedApple.separateBar);
        Bukkit.broadcastMessage(""+ChatColor.DARK_RED+ChatColor.BOLD+player2.getName()+ChatColor.WHITE+" は "+ChatColor.DARK_BLUE+ChatColor.BOLD+player.getName()+ChatColor.WHITE+"によって救済されました");
        Bukkit.broadcastMessage("救済額は "+ChatColor.DARK_RED+ChatColor.BOLD+num+" 億円です");
        Bukkit.broadcastMessage(RedApple.separateBar);

        int nmp1 = Integer.parseInt(RedApple.playerMapBox.get(player).get(1));//player1の現在の所持金をnmp1に
        RedApple.playerMapBox.get(player).set(1,String.valueOf(nmp1 - num));//numを引いてset

        int nmp2 = Integer.parseInt(RedApple.playerMapBox.get(player2).get(1));
        RedApple.playerMapBox.get(player2).set(1,String.valueOf(nmp2 + num));

        SystemMain.refreshSidebar();
        Vote.phaseVote(RedApple.phase);
    }

    public static void goSituraku(Player player){
        Bukkit.broadcastMessage(RedApple.separateBar);
        Bukkit.broadcastMessage(""+ChatColor.DARK_RED+ChatColor.BOLD+player.getName()+ChatColor.WHITE+" の失楽園行きが確定しました");
        Bukkit.broadcastMessage("ゲームを続行します");
        Bukkit.broadcastMessage(RedApple.separateBar);
        player.setGameMode(GameMode.SPECTATOR);
        RedApple.situraku.add(player);
        Vote.phaseVote(RedApple.phase);
    }
}
