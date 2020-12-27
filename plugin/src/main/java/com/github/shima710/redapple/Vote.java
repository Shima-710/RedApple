package com.github.shima710.redapple;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

public class Vote implements Listener {
    public static RedApple plugin;
    public Vote(RedApple instance) { plugin = instance; }

    public static void phaseVote(int n){
        for(Player p :RedApple.gamePlayer){
            if(Situraku.checkSituraku(p)){
                RedApple.exiSituraku = true;
                Situraku.situraku(p);
                break;
            }
            else{
                RedApple.exiSituraku = false;
            }
        }
        if(!RedApple.exiSituraku){
            if(RedApple.phase!=RedApple.maxPhase+1){
                resetVote();
                for(Player pl:Bukkit.getOnlinePlayers()){
                    pl.sendTitle("Phase" + n,"投票",40,60,40);
                }
                Bukkit.broadcastMessage(RedApple.separateBar);
                Bukkit.broadcastMessage("投票の時間です");
                Bukkit.broadcastMessage("投票部屋でダイヤモンド鉱石を右クリックし、投票するリンゴを選んでください");
                Bukkit.broadcastMessage("制限時間は "+ChatColor.DARK_RED+RedApple.voteTime+ChatColor.WHITE+" 秒です");
                Bukkit.broadcastMessage(RedApple.separateBar);
                RedApple.phase += 1;
                RedApple.voting = true;
                new Timer(plugin,RedApple.voteTime,true).runTaskTimer(plugin, 10,20);
            }
            else{
                SystemMain.gameEnd();
            }
        }
    }

    public static void openVoteBox(Player player){
        Inventory voteBox = Bukkit.createInventory(player, 27, "投票箱");
        voteBox.setItem(11,SystemMain.namedItem(RedApple.goldApple,"金のりんごに投票する"));
        voteBox.setItem(13,SystemMain.namedItem(RedApple.silverApple,"銀のりんごに投票する"));
        voteBox.setItem(15,SystemMain.namedItem(RedApple.redApple,"真実の赤りんごに投票する"));

        player.openInventory(voteBox);
    }

    public static void vote(String color, Player player){
        if(checkVotable(player)){
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
            RedApple.yetVote = RedApple.quaGamePlayer - RedApple.quaVoted - RedApple.situraku.size();
            if(RedApple.yetVote==0){
                RedApple.voting = false;
                RedApple.finAllVote = true;
                Bukkit.broadcastMessage(RedApple.separateBar);
                Bukkit.broadcastMessage("投票が終了しました");
                Bukkit.broadcastMessage("開票しています...");
                Bukkit.broadcastMessage(RedApple.separateBar);
                // ここから先はTimerのif(finAllVote)へ飛ぶ
            }
            else{
                Bukkit.broadcastMessage("残り "+ChatColor.DARK_RED+RedApple.yetVote+ChatColor.WHITE+" 人です");
            }
        }

    }

    public static void timeUpVote(){
        Bukkit.broadcastMessage(RedApple.separateBar);
        Bukkit.broadcastMessage("制限時間を迎えたので投票を"+ChatColor.DARK_RED+ChatColor.BOLD+" 強制終了 "+ChatColor.WHITE+"します");
        Bukkit.broadcastMessage("開票しています...");
        Bukkit.broadcastMessage(RedApple.separateBar);

        RedApple.ending = true;
        new Timer(plugin,3,false).runTaskTimer(plugin, 10,20);
    }



    public static void openVote(){
        RedApple.quaVoteRed = RedApple.voteRed.size();
        RedApple.quaVoteSilver = RedApple.voteSilver.size();
        RedApple.quaVoteGold = RedApple.voteGold.size();
        Bukkit.broadcastMessage(RedApple.separateBar);
        Bukkit.broadcastMessage(""+ChatColor.GOLD+ChatColor.BOLD+"[結果]");
        Bukkit.broadcastMessage("金のリンゴ ： "+ChatColor.DARK_RED+ChatColor.BOLD+RedApple.quaVoteGold+ChatColor.WHITE+" 個  ｜  銀のリンゴ ： "+ChatColor.DARK_RED+ChatColor.BOLD+RedApple.quaVoteSilver+ChatColor.WHITE+" 個  ｜  真実の赤リンゴ ： "+ChatColor.DARK_RED+ChatColor.BOLD+RedApple.quaVoteRed+ChatColor.WHITE+" 個");
        Bukkit.broadcastMessage(" ");

        if(RedApple.quaVoteRed==0){
            if(RedApple.quaVoteGold == RedApple.quaVoteSilver){
                Bukkit.broadcastMessage("引き分けです");
                Bukkit.broadcastMessage(RedApple.separateBar);
            }
            else if(RedApple.quaVoteGold == RedApple.quaVoted){
                Bukkit.broadcastMessage("全員が金のりんごへ投票しました");
                Bukkit.broadcastMessage("全員"+ChatColor.DARK_RED+ChatColor.BOLD+" 1 "+ChatColor.WHITE+"億円を失います");
                Bukkit.broadcastMessage(RedApple.separateBar);
                changeMoney(RedApple.voteSilver,1,false);
                changeMoney(RedApple.voteGold,1,false);
            }
            else if(RedApple.quaVoteSilver == RedApple.quaVoted){
                Bukkit.broadcastMessage("全員が銀のりんごへ投票しました");
                Bukkit.broadcastMessage("全員"+ChatColor.DARK_RED+ChatColor.BOLD+" 1 "+ChatColor.WHITE+"億円を失います");
                Bukkit.broadcastMessage(RedApple.separateBar);
                changeMoney(RedApple.voteSilver,1,false);
                changeMoney(RedApple.voteGold,1,false);
            }
            else if(RedApple.quaVoteSilver < RedApple.quaVoteGold){
                Bukkit.broadcastMessage("金のりんごがより多くの票を集めました");
                Bukkit.broadcastMessage("金のりんご投票者に"+ChatColor.DARK_RED+ChatColor.BOLD+" 1 "+ChatColor.WHITE+"億円が与えられます");
                Bukkit.broadcastMessage("銀のりんご投票者は"+ChatColor.DARK_RED+ChatColor.BOLD+" 1 "+ChatColor.WHITE+"億円を失います");
                Bukkit.broadcastMessage(RedApple.separateBar);
                changeMoney(RedApple.voteGold,1,true);
                changeMoney(RedApple.voteSilver,1,false);
            }
            else {
                Bukkit.broadcastMessage("銀のりんごがより多くの票を集めました");
                Bukkit.broadcastMessage("銀のりんご投票者に"+ChatColor.DARK_RED+ChatColor.BOLD+" 1 "+ChatColor.WHITE+"億円が与えられます");
                Bukkit.broadcastMessage("金のりんご投票者は"+ChatColor.DARK_RED+ChatColor.BOLD+" 1 "+ChatColor.WHITE+"億円を失います");
                Bukkit.broadcastMessage(RedApple.separateBar);
                changeMoney(RedApple.voteSilver,1,true);
                changeMoney(RedApple.voteGold,1,false);
            }
        }
        else{
            if(RedApple.quaVoteRed == 1){
                Bukkit.broadcastMessage("真実の赤りんごの得票数が1でした");
                Bukkit.broadcastMessage("金のりんご投票者に"+ChatColor.DARK_RED+ChatColor.BOLD+" 1 "+ChatColor.WHITE+"億円が与えられます");
                Bukkit.broadcastMessage("銀のりんご投票者に"+ChatColor.DARK_RED+ChatColor.BOLD+" 1 "+ChatColor.WHITE+"億円が与えられます");
                Bukkit.broadcastMessage("真実の赤りんご投票者は"+ChatColor.DARK_RED+ChatColor.BOLD+" 10 "+ChatColor.WHITE+"億円を失います");
                Bukkit.broadcastMessage(RedApple.separateBar);
                changeMoney(RedApple.voteGold,1,true);
                changeMoney(RedApple.voteSilver,1,true);
                changeMoney(RedApple.voteRed,10,false);
            }
            else if(RedApple.quaVoteGold == 1 && RedApple.quaVoteSilver == 0){
                Bukkit.broadcastMessage("一人のみが金のりんごへ，その他全員は真実の赤りんごへ投票しました");
                Bukkit.broadcastMessage("金のりんご投票者に"+ChatColor.DARK_RED+ChatColor.BOLD+" 2 "+ChatColor.WHITE+"億円が与えられます");
                Bukkit.broadcastMessage("真実の赤りんご投票者は"+ChatColor.DARK_RED+ChatColor.BOLD+" 1 "+ChatColor.WHITE+"億円を失います");
                Bukkit.broadcastMessage(RedApple.separateBar);
                changeMoney(RedApple.voteGold,2, true);
                changeMoney(RedApple.voteRed,1, false);
            }
            else if(RedApple.quaVoteSilver == 1 && RedApple.quaVoteGold == 0){
                Bukkit.broadcastMessage("一人のみが銀のりんごへ，その他全員は真実の赤りんごへ投票しました");
                Bukkit.broadcastMessage("銀のりんご投票者に"+ChatColor.DARK_RED+ChatColor.BOLD+" 2 "+ChatColor.WHITE+"億円が与えられます");
                Bukkit.broadcastMessage("真実の赤りんご投票者は"+ChatColor.DARK_RED+ChatColor.BOLD+" 1 "+ChatColor.WHITE+"億円を失います");
                Bukkit.broadcastMessage(RedApple.separateBar);
                changeMoney(RedApple.voteSilver,2, true);
                changeMoney(RedApple.voteRed,1, false);
            }
            else if(RedApple.quaVoteGold+RedApple.quaVoteSilver >= 2){
                Bukkit.broadcastMessage("銀のりんごの得票数が2以上でした");
                Bukkit.broadcastMessage("金のりんご投票者に"+ChatColor.DARK_RED+ChatColor.BOLD+" 1 "+ChatColor.WHITE+"億円が与えられます");
                Bukkit.broadcastMessage("銀のりんご投票者に"+ChatColor.DARK_RED+ChatColor.BOLD+" 1 "+ChatColor.WHITE+"億円が与えられます");
                Bukkit.broadcastMessage("真実の赤りんご投票者は"+ChatColor.DARK_RED+ChatColor.BOLD+" 1 "+ChatColor.WHITE+"億円を失います");
                Bukkit.broadcastMessage(RedApple.separateBar);
                changeMoney(RedApple.voteGold,1,true);
                changeMoney(RedApple.voteSilver,1, true);
                changeMoney(RedApple.voteRed,1,false);
            }
            else if(RedApple.quaVoteRed == RedApple.quaVoted){
                Bukkit.broadcastMessage("全員が真実の赤りんごへ投票しました");
                Bukkit.broadcastMessage("全員に"+ChatColor.DARK_RED+ChatColor.BOLD+" 1 "+ChatColor.WHITE+"億円が与えられます");
                Bukkit.broadcastMessage(RedApple.separateBar);
                changeMoney(RedApple.voteRed,1, true);
            }
            else{
                Bukkit.broadcastMessage(ChatColor.RED + ">ERROR " + ChatColor.WHITE + "Unexpected error occurred.Please Report this with server logs.");
                Bukkit.broadcastMessage(ChatColor.RED + ">ERROR " + ChatColor.WHITE + "予期せぬエラーが発生しました．サーバーログと一緒に開発者へ報告してください．");
                Bukkit.broadcastMessage(RedApple.separateBar);
            }
        }
        SystemMain.refreshSidebar();
        RedApple.opening = true;
        new Timer(plugin,3,false).runTaskTimer(plugin, 10,20);
    }

    public static void changeMoney(List<Player> list, int money,Boolean add){
        for(Player p:RedApple.gamePlayer){//ゲームプレイヤー全員でfor
            if(list.contains(p)){//そいつが指定のリストにいれば
                int nm = Integer.parseInt(RedApple.playerMapBox.get(p).get(1));//playerの現在の所持金をnmに
                if(add) {
                    RedApple.playerMapBox.get(p).set(1, String.valueOf(nm + money));//足し引きしてset
                } else {
                    RedApple.playerMapBox.get(p).set(1, String.valueOf(nm - money));
                }
            }
        }
    }

    public static Boolean checkVotable(Player player){
        if(RedApple.gameStatus) { //ゲーム中
            if (RedApple.gamePlayer.contains(player)) { //参加者
                if(!RedApple.situraku.contains(player)){ //失楽園
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
                else{
                    player.sendMessage(ChatColor.RED + ">ERROR " + ChatColor.WHITE + "あなたは現在失楽園にいます");
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
        RedApple.voted.clear();
        RedApple.voteRed.clear();
        RedApple.voteSilver.clear();
        RedApple.voteGold.clear();
        RedApple.voting = false;
        RedApple.finAllVote = false;
        RedApple.quaVoted = 0;
        RedApple.quaVoteRed = 0;
        RedApple.quaVoteSilver = 0;
        RedApple.quaVoteGold = 0;
        RedApple.yetVote = 0;
    }
}
