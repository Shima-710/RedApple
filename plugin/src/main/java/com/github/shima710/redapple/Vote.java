package com.github.shima710.redapple;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Vote implements Listener {
    public static RedApple plugin;
    public Vote(RedApple instance) { plugin = instance; }

    public static void phaseVote(int n){
        for(Player p :RedApple.gamePlayer){
            if(Situraku.checkSituraku(p)){
                break;
            }
        }
        resetVote();
        for(Player pl:Bukkit.getOnlinePlayers()){
            pl.sendTitle("Phase" + n,"投票",40,60,40);
            pl.sendMessage(RedApple.separateBar);
            pl.sendMessage("投票の時間です");
            pl.sendMessage("投票部屋でダイヤモンド鉱石を右クリックし、投票するリンゴを選んでください");
            pl.sendMessage("制限時間は "+ChatColor.DARK_RED+RedApple.voteTime+ChatColor.WHITE+" 秒です");
            pl.sendMessage(RedApple.separateBar);
        }
        RedApple.phase += 1;
        RedApple.voting = true;
        new Timer(plugin,RedApple.voteTime+1,true).runTaskLater(plugin, 0);
    }

    public static void openVoteBox(Player player){
        Inventory voteBox = Bukkit.createInventory(player, 27, "投票箱");
        voteBox.setItem(11,SystemMain.namedItem(RedApple.goldApple,"金のりんごに投票する"));
        voteBox.setItem(13,SystemMain.namedItem(RedApple.silverApple,"銀のりんごに投票する"));
        voteBox.setItem(15,SystemMain.namedItem(RedApple.redApple,"真実の赤りんごに投票する"));

        player.openInventory(voteBox);
    }

    public static void vote(String color, Player player){
        if(checkVotable(player)){//FIXME なぜか投票済みだよ
            RedApple.quaVoted++;
            switch (color) {
                case "red":
                    RedApple.voteRed.add(player);
                    player.sendMessage(ChatColor.GREEN + ">GAME " + ChatColor.WHITE + "真実の赤りんごへ投票しました");
                    break;
                case "silver":
                    RedApple.voteSilver.add(player);
                    player.sendMessage(ChatColor.GREEN + ">GAME " + ChatColor.WHITE + "銀のりんごへ投票しました");
                    break;
                case "gold":
                    RedApple.voteGold.add(player);
                    player.sendMessage(ChatColor.GREEN + ">GAME " + ChatColor.WHITE + "金のりんごへ投票しました");
                    break;
            }
            RedApple.yetVote = RedApple.quaGamePlayer - RedApple.quaVoted;
            if(RedApple.yetVote==0){
                RedApple.voting = false;
                RedApple.finAllVote = true;
                for(Player p:Bukkit.getOnlinePlayers()){
                    p.sendMessage(RedApple.separateBar);
                    p.sendMessage("投票が終了しました");
                    p.sendMessage("開票しています...");
                    p.sendMessage(RedApple.separateBar);
                }
                RedApple.ending = true;
                new Timer(plugin,6, false).runTaskLater(plugin, 0);
            }
            else{
                for(Player p:Bukkit.getOnlinePlayers()){
                    p.sendMessage("残り "+ChatColor.DARK_RED+RedApple.yetVote+ChatColor.WHITE+" 人です");
                }
            }
        }

    }

    public static void timeUpVote(){
        for(Player p:Bukkit.getOnlinePlayers()){
            p.sendMessage(RedApple.separateBar);
            p.sendMessage("制限時間を迎えたので投票を"+ChatColor.DARK_RED+ChatColor.BOLD+" 強制終了 "+ChatColor.WHITE+"します");
            p.sendMessage("開票しています...");
            p.sendMessage(RedApple.separateBar);
        }
        RedApple.ending = true;
        new Timer(plugin,6,false).runTaskLater(plugin, 0);
    }


    public static void endVote(){
        if(RedApple.phase==RedApple.maxPhase){
            SystemMain.gameEnd();
        }
        else{
            phaseVote(RedApple.phase);
        }
    }

    public static void openVote(){
        RedApple.quaVoteRed = RedApple.voteRed.size();
        RedApple.quaVoteSilver = RedApple.voteSilver.size();
        RedApple.quaVoteGold = RedApple.voteGold.size();
        for(Player p:Bukkit.getOnlinePlayers()){
            p.sendMessage(RedApple.separateBar);
            p.sendMessage(""+ChatColor.GOLD+ChatColor.BOLD+"[結果]");
            p.sendMessage("金のリンゴ ： "+ChatColor.DARK_RED+ChatColor.BOLD+RedApple.quaVoteGold+ChatColor.WHITE+" 個  ｜  銀のリンゴ ： "+ChatColor.DARK_RED+ChatColor.BOLD+RedApple.quaVoteSilver+ChatColor.WHITE+" 個  ｜  真実の赤リンゴ ： "+ChatColor.DARK_RED+ChatColor.BOLD+RedApple.quaVoteRed+ChatColor.WHITE+" 個");
            p.sendMessage(" ");
        }


        RedApple.opening = true;
        new Timer(plugin,6,false).runTaskLater(plugin, 0);
    }

    public static Boolean checkVotable(Player player){
        if(RedApple.gameStatus) { //ゲーム中
            if (RedApple.gamePlayer.contains(player)) { //参加者
                if (!RedApple.voted.contains(player)) { // 未投票
                    if (RedApple.voting) { // 投票可能時間
                        return true;
                    }
                    else {
                        player.sendMessage(ChatColor.RED + ">ERROR " + ChatColor.WHITE + "現在投票可能時間ではありません");
                        return false;
                    }
                }
                else {
                    player.sendMessage(ChatColor.RED + ">ERROR " + ChatColor.WHITE + "既に投票済みです");
                    return false;
                }
            }
            else {
                player.sendMessage(ChatColor.RED + ">ERROR " + ChatColor.WHITE + "あなたは参加者ではありません");
                return false;
            }
        }
        else {
            return false;
        }
    }

    public static void resetVote(){
        RedApple.voted = new ArrayList<>();
        RedApple.voteRed = new ArrayList<>();
        RedApple.voteSilver = new ArrayList<>();
        RedApple.voteGold = new ArrayList<>();
        RedApple.situraku = new ArrayList<>();
        RedApple.voting = false;
        RedApple.finAllVote = false;
        RedApple.quaVoted = 0;
        RedApple.quaVoteRed = 0;
        RedApple.quaVoteSilver = 0;
        RedApple.quaVoteGold = 0;
        RedApple.yetVote = 0;
    }
}
