package com.github.shima710.redapple;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
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
                RedApple.quaGamePlayer++;
            }else{
                p.setGameMode(GameMode.SPECTATOR);
            }
        }
        RedApple.quaGamePlayer = RedApple.gamePlayer.size();
        List<String> disp = new ArrayList<String>();
        for(Player pl:RedApple.gamePlayer){
            disp.add(pl.getName());
        }
        Collections.shuffle(RedApple.gamePlayer);
        List<String> disp2 = new ArrayList<String>();
        for(Player pl:RedApple.gamePlayer){
            disp2.add(pl.getName());
        }
        for(int i=0; i<RedApple.quaGamePlayer; i++){
            RedApple.playerBox[i][1] = String.valueOf(disp2.get(i));
        }
        Bukkit.getLogger().info(""+ Arrays.deepToString(RedApple.playerBox));//TODO kesu
        for(Player p:Bukkit.getOnlinePlayers()){
            p.sendMessage(RedApple.separateBar);
            p.sendMessage("参加者は");
            p.sendMessage(ChatColor.BOLD+String.valueOf(disp));
            p.sendMessage("計 "+ChatColor.DARK_RED+RedApple.quaGamePlayer+ChatColor.WHITE+" 人です");
            p.sendMessage(RedApple.separateBar);
            p.sendMessage(" "+ChatColor.DARK_RED+RedApple.prepareTime+ChatColor.WHITE+" 秒後に最初の投票が始まります");
            p.sendMessage(RedApple.separateBar);
        }
        refreshSidebar();
        RedApple.preparing = true;
        new Timer(plugin,RedApple.prepareTime,true).runTaskTimer(plugin, 10,20);
    }

    public static void gameEnd(){
        for(Player p:Bukkit.getOnlinePlayers()){
            p.sendMessage(RedApple.separateBar);
            p.sendMessage("全ターンが終了しました");
            p.sendMessage("試合結果は次のようになりました");
            p.sendMessage("　");
        }
        for(int i=0; i<RedApple.quaGamePlayer; i++){
            for(Player pl:Bukkit.getOnlinePlayers()){
                pl.sendMessage(""+ChatColor.DARK_BLUE+ChatColor.BOLD+RedApple.playerBox[i][0]+ChatColor.WHITE+" - "+ChatColor.DARK_RED+ChatColor.BOLD+RedApple.playerBox[1][2]+ChatColor.WHITE+" 億円 - "+ChatColor.GOLD+RedApple.playerBox[i][1]);
            }
        }
        for(Player p:Bukkit.getOnlinePlayers()){
            p.sendMessage("　");
            p.sendMessage("以上でゲームを終了します");
            p.sendMessage(RedApple.separateBar);
        }
        resetGame();
    }

    public static void resetGame(){
        RedApple.gameStatus = false;
        RedApple.siturakuChoise = false;
        RedApple.gamePlayer = new ArrayList<Player>();
        RedApple.phase = 1;
        RedApple.quaGamePlayer = 0;
        Vote.resetVote();
        Config.load();
        RedApple.makePlayerBox();
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
        for(int i=0; i<RedApple.quaGamePlayer; i++){
            Score score = RedApple.objective.getScore(ChatColor.GREEN + RedApple.playerBox[i][0] +":");
            score.setScore(Integer.parseInt(RedApple.playerBox[i][2]));
        }

    }
}
