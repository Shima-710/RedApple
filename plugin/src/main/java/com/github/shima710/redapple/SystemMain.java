package com.github.shima710.redapple;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scoreboard.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SystemMain implements Listener {
    public static RedApple plugin;
    public SystemMain(RedApple instance) { plugin = instance; }

    public static void gameStart(){
        RedApple.gameStatus = true;
        for(Player p:Bukkit.getOnlinePlayers()){
            p.setHealth(20.0);
            p.setFoodLevel(20);
            if(p.getGameMode().equals(GameMode.ADVENTURE) || p.getGameMode().equals(GameMode.SURVIVAL)) {
                p.setGameMode(GameMode.ADVENTURE);
                RedApple.gamePlayer.add(p);
            }else{
                p.setGameMode(GameMode.SPECTATOR);
            }
        }
        RedApple.quaGamePlayer = RedApple.gamePlayer.size();

        List<String> disp = new ArrayList<>();
        for(Player pl:RedApple.gamePlayer){
            disp.add(pl.getName());
        }
        Bukkit.getLogger().info("シャッフル前" + disp);//TODO kesu

        Collections.shuffle(RedApple.gamePlayer);
        for(int i=0; i<RedApple.quaGamePlayer; i++){
            RedApple.playerMapBox.put(RedApple.gamePlayer.get(i),new ArrayList<>());
            RedApple.playerMapBox.get(RedApple.gamePlayer.get(i)).add(0,RedApple.alp[i]);
            RedApple.playerMapBox.get(RedApple.gamePlayer.get(i)).add(1,"0");
        }

        Bukkit.getLogger().info("playerMapBox"+ RedApple.playerMapBox);//TODO kesu
        Bukkit.broadcastMessage(RedApple.separateBar);
        Bukkit.broadcastMessage("参加者は");
        Bukkit.broadcastMessage(ChatColor.BOLD+String.valueOf(disp));
        Bukkit.broadcastMessage("計 "+ChatColor.DARK_RED+RedApple.quaGamePlayer+ChatColor.WHITE+" 人です");
        Bukkit.broadcastMessage(RedApple.separateBar);
        Bukkit.broadcastMessage(" "+ChatColor.DARK_RED+RedApple.prepareTime+ChatColor.WHITE+" 秒後に最初の投票が始まります");
        Bukkit.broadcastMessage(RedApple.separateBar);
        refreshSidebar();
        RedApple.preparing = true;
        new Timer(plugin,RedApple.prepareTime,true).runTaskTimer(plugin, 10,20);
    }

    public static void gameEnd(){
        Bukkit.broadcastMessage(RedApple.separateBar);
        Bukkit.broadcastMessage("全ターンが終了しました");
        Bukkit.broadcastMessage("結果は次のようになりました");
        Bukkit.broadcastMessage(" ");
        for(Player p:RedApple.gamePlayer){
            Bukkit.broadcastMessage("  "+ChatColor.GREEN+ChatColor.BOLD+RedApple.playerMapBox.get(p).get(0)+ChatColor.WHITE+" - "+ChatColor.DARK_RED+ChatColor.BOLD+RedApple.playerMapBox.get(p).get(1)+ChatColor.WHITE+" 億円 - "+ChatColor.GOLD+p.getName());
        }
        Bukkit.broadcastMessage(" ");
        Bukkit.broadcastMessage("以上でゲームを終了します");
        Bukkit.broadcastMessage(RedApple.separateBar);
        resetGame();
    }

    public static void resetGame(){
        RedApple.gameStatus = false;
        RedApple.siturakuChoice = false;
        RedApple.situraku.clear();
        RedApple.gamePlayer.clear();
        RedApple.phase = 1;
        RedApple.quaGamePlayer = 0;
        RedApple.playerMapBox.clear();
        Vote.resetVote();
        Config.load();
    }


    public static ItemStack namedItem(ItemStack item,String name){
        ItemMeta im = item.getItemMeta();
        assert im != null;
        im.setDisplayName(name);
        item.setItemMeta(im);
        return item;
    }

    public static void refreshSidebar(){
        RedApple.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        for(Player p:RedApple.gamePlayer){
            Score score = RedApple.objective.getScore(ChatColor.GREEN + RedApple.playerMapBox.get(p).get(0) +" :");
            score.setScore(Integer.parseInt(RedApple.playerMapBox.get(p).get(1)));
        }

        /* これでもいけるのか。これにするならgamePlayerいらなくなるね
        for(Player p:RedApple.playerMapBox.keySet()){
            Score score = RedApple.objective.getScore(ChatColor.GREEN + RedApple.playerMapBox.get(p).get(0) +" :");
            score.setScore(Integer.parseInt(RedApple.playerMapBox.get(p).get(1)));
        }
        */

    }
}
