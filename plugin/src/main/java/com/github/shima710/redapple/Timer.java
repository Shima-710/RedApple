package com.github.shima710.redapple;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Timer extends BukkitRunnable{
    private final RedApple plg;
    private int count;
    private final Boolean expBar;

    /**
     * コンストラクタ
     * @param plg_ プラグインメインクラスのインスタンス
     * @param count_ 表示する値
     */
    public Timer(RedApple plg_, int count_, Boolean expBar_)
    {
        plg = plg_;
        count = count_;
        expBar = expBar_;
    }

    /**
     * 非同期処理実行メソッド
     */
    public void run()
    {

        if(expBar){
            for(Player p: Bukkit.getOnlinePlayers()) {
            p.sendExperienceChange(0, count);
            }
        }
        if(RedApple.finAllVote){
            RedApple.finAllVote = false;
            RedApple.ending = true;
            for(Player p: Bukkit.getOnlinePlayers()) {
                p.sendExperienceChange(0, 0);
            }
            this.cancel();
            new Timer(plg,3, false).runTaskTimer(plg, 10,20);
        }
        if (count == 0){
            if(RedApple.preparing){
                Vote.phaseVote(1);
                RedApple.preparing = false;
            }
            else if(RedApple.voting){
                Vote.timeUpVote();
                RedApple.voting = false;
            }
            else if(RedApple.ending){
                Vote.openVote();
                RedApple.ending = false;
            }
            else if(RedApple.opening){
                Vote.phaseVote(RedApple.phase);
                RedApple.opening = false;
            }
            this.cancel();
        }
        count--;
    }
}
