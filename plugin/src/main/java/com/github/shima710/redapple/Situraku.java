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
        for(int i=0;i<RedApple.quaGamePlayer;i++){//playerBox内でfor
            if(RedApple.playerBox[i][1].equals(player.getName())){//playerBox内で名前が一致する箱を見つけたら
                if(Integer.parseInt(RedApple.playerBox[i][2])<=-5){//-5以下なら
                    return true;
                }
            }
        }
        return false;
    }

    public static void situraku(Player player){
        for(Player p: Bukkit.getOnlinePlayers()){
            p.sendMessage(RedApple.separateBar);
            p.sendMessage(""+ChatColor.DARK_RED+ChatColor.BOLD+player.getName()+ChatColor.WHITE+" は負債が"+ChatColor.DARK_RED+ChatColor.BOLD+" 5 "+ChatColor.WHITE+"億円を超えたため失楽園行きとなります");
            p.sendMessage("救済希望者は \"/k <救済額>\" コマンドで負債を肩代わりすることができます");
            p.sendMessage("救済希望者がいない場合は代表一名が \"/s\" コマンドで対象者を失楽園へ送ってください");
            p.sendMessage(RedApple.separateBar);
        }
        RedApple.siturakuChoice = true;
        RedApple.siturakuWho = player;
    }

    public static void kyuusai(Player player, Player player2, Integer num){//するひとされるひときんがく
        RedApple.siturakuChoice = false;
        RedApple.siturakuWho = null;
        for(Player p: Bukkit.getOnlinePlayers()){
            p.sendMessage(RedApple.separateBar);
            p.sendMessage(""+ChatColor.DARK_RED+ChatColor.BOLD+player2.getName()+ChatColor.WHITE+" は "+ChatColor.DARK_BLUE+ChatColor.BOLD+player.getName()+ChatColor.WHITE+"によって救済されました");
            p.sendMessage("救済額は "+ChatColor.DARK_RED+ChatColor.BOLD+num+" 億円です");
            p.sendMessage(RedApple.separateBar);
        }
        for(int i=0;i<26;i++){//playerBox内でfor
            if(RedApple.playerBox[i][1].equals(player.getName())){//playerBox内で名前が一致する箱を見つけたら
                int nm = Integer.parseInt(RedApple.playerBox[i][2]);//nm=now money をplayerBox[任意位置][所持金]
                RedApple.playerBox[i][2] = String.valueOf(nm - num);//所持金更新
            }
            else if(RedApple.playerBox[i][1].equals(player2.getName())){//playerBox内で名前が一致する箱を見つけたら
                int nm = Integer.parseInt(RedApple.playerBox[i][2]);//nm=now money をplayerBox[任意位置][所持金]
                RedApple.playerBox[i][2] = String.valueOf(nm + num);//所持金更新
            }
        }
        SystemMain.refreshSidebar();
        Vote.phaseVote(RedApple.phase);
    }

    public static void goSituraku(Player player){
        for(Player p: Bukkit.getOnlinePlayers()){
            p.sendMessage(RedApple.separateBar);
            p.sendMessage(""+ChatColor.DARK_RED+ChatColor.BOLD+player.getName()+ChatColor.WHITE+" の失楽園行きが確定しました");
            p.sendMessage("ゲームを続行します");
            p.sendMessage(RedApple.separateBar);
        }
        player.setGameMode(GameMode.SPECTATOR);
        RedApple.situraku.add(player);
        Vote.phaseVote(RedApple.phase);
    }
}
