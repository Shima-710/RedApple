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

import java.util.ArrayList;
import java.util.List;

public class SystemMain implements Listener {
    public static RedApple plugin;
    public SystemMain(RedApple instance) { plugin = instance; }

    public static void gameStart(){
        RedApple.gameStatus = true;
        for(Player p:Bukkit.getOnlinePlayers()){
            p.setHealth(20.0);
            p.setFoodLevel(20);
            if(p.getLocation().add(0,-0.1,0).getBlock().getType().equals(Material.GOLD_BLOCK)) {
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
        for(Player p:Bukkit.getOnlinePlayers()){
            p.sendMessage(RedApple.separateBar);
            p.sendMessage("参加者は");
            p.sendMessage(ChatColor.BOLD+String.valueOf(disp));
            p.sendMessage("計 "+ChatColor.DARK_RED+RedApple.quaGamePlayer+ChatColor.WHITE+" 人です");
            p.sendMessage(RedApple.separateBar);
            p.sendMessage(" "+ChatColor.DARK_RED+RedApple.prepareTime+ChatColor.WHITE+" 秒後に最初の投票が始まります");
            p.sendMessage(RedApple.separateBar);
        }
        RedApple.preparing = true;
        new Timer(plugin,RedApple.prepareTime+1).runTaskLater(plugin, 0);
    }

    public static void gameEnd(){
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
    }


    public static ItemStack namedItem(ItemStack item,String name){
        ItemMeta im = item.getItemMeta();
        assert im != null;
        im.setDisplayName(name);
        item.setItemMeta(im);
        return item;
    }
}
