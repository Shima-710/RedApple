package com.github.shima710.redapple;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;

public class Config implements Listener {
    public static RedApple plugin;
    public Config(RedApple instance) { plugin = instance; }

    public static void load(){
        FileConfiguration config = RedApple.plugin.getConfig();
        RedApple.maxPhase = config.getInt("MaxPhase");
        RedApple.voteTime = config.getInt("VoteTime");
        RedApple.prepareTime = config.getInt("PrepareTime");
    }

}
