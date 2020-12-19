package com.github.shima710.redapple;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class Timer extends BukkitRunnable{
    private final RedApple plg;
    private int count;

    /**
     * コンストラクタ
     * @param plg_ プラグインメインクラスのインスタンス
     * @param count_ 表示する値
     */
    public Timer(RedApple plg_, int count_)
    {
        plg = plg_;
        count = count_;
    }

    /**
     * 非同期処理実行メソッド
     */
    public void run()
    {
        count--;
        for(Player p: Bukkit.getOnlinePlayers()) {
            p.sendExperienceChange(0, count);
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
                Vote.endVote();
                RedApple.opening = false;
            }
        }
        else if (count >= 0) {
            new Timer(plg, count).runTaskLater(plg, 20);
        }
    }
}
