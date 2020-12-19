package com.github.shima710.redapple;

import jdk.nashorn.internal.ir.Block;
import jdk.nashorn.internal.objects.NativeFloat32Array;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class RedApple extends JavaPlugin{
    public static RedApple plugin = null;
    public static String version = RedApple.class.getPackage().getImplementationVersion();

    BukkitTask task = null;
    public static final String alp[] = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
    public static final String separateBar = "===================================================";
    public static String playerBox[][] = new String[26][];
    public static Boolean gameStatus = false;
    public static Boolean siturakuChoise = false;
    public static Boolean exiSituraku = false;
    public static Boolean preparing = false;
    public static Boolean voting = false;
    public static Boolean finAllVote = false;
    public static Boolean ending = false;
    public static Boolean opening = false;
    public static Player siturakuWho;
    public static List<Player> gamePlayer = new ArrayList<>();
    public static List<Player> voted = new ArrayList<>();
    public static List<Player> voteRed = new ArrayList<>();
    public static List<Player> voteSilver = new ArrayList<>();
    public static List<Player> voteGold = new ArrayList<>();
    public static List<Player> situraku = new ArrayList<>();
    public static int phase = 1;
    public static int maxPhase;
    public static int voteTime;
    public static int prepareTime;
    public static int yetVote;
    public static int quaGamePlayer;
    public static int quaVoted;
    public static int quaVoteRed;
    public static int quaVoteSilver;
    public static int quaVoteGold;
    public static final ItemStack redApple = new ItemStack(Material.APPLE,1) ;
    public static final ItemStack silverApple = new ItemStack(Material.IRON_INGOT,1) ;
    public static final ItemStack goldApple = new ItemStack(Material.GOLD_INGOT,1) ;
    public static Objective objective;


    public static void makePlayerBox(){
        for(int i=0; i<26; i++){
            playerBox[i] = new String[3];
            playerBox[i][0] = RedApple.alp[i];
            playerBox[i][2] = "0";
        }
    }


    @Override
    public void onEnable() {
        plugin = this;
        Commands command = new Commands();
        getServer().getPluginManager().registerEvents(new Vote(this),this);
        getServer().getPluginManager().registerEvents(new SystemMain(this), this);
        getServer().getPluginManager().registerEvents(new Event(this), this);
        getServer().getPluginManager().registerEvents(new Situraku(this), this);
        getServer().getPluginManager().registerEvents(new Config(this), this);
        Objects.requireNonNull(getCommand("start")).setExecutor(command);
        Objects.requireNonNull(getCommand("k")).setExecutor(command);
        Objects.requireNonNull(getCommand("s")).setExecutor(command);
        Objects.requireNonNull(getCommand("ra-help")).setExecutor(command);
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        assert manager != null;
        Scoreboard board = manager.getMainScoreboard();
        objective = board.registerNewObjective("test", "dummy","所持金");


        saveDefaultConfig();
        SystemMain.resetGame();


        getLogger().info("Hello! Current version is v" + version);
    }

    @Override
    public void onDisable() {
        getLogger().info("Goodbye!");
    }
}
