package com.github.shima710.rai;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class Rai extends JavaPlugin{

    // RAIフォルダ内のredapple.skの文字列・ファイル・パス
    public static String strRaiScript = "./plugins/RAI/redapple.sk";
    public static File fileRaiScript = new File(strRaiScript);
    public static Path pathRaiScript = Paths.get(strRaiScript);

    // Skriptフォルダ内のredapple.skの文字列・ファイル・パス
    public static String strSkriptScript = "./plugins/Skript/scripts/redapple.sk";
    public static File fileSkriptScript = new File(strSkriptScript);
    public static Path pathSkriptScript = Paths.get(strSkriptScript);

    @Override
    public void onEnable() {
        getLogger().info("Hello!! Enabling Complete! v2.0.0");

        // インストール状態をチェック
        if (checkInstallStatus()){
            Bukkit.getLogger().info("[RAI] RedApple is now available! Enjoy game!");
        } else{
            Bukkit.getLogger().warning("[RAI] RedApple is not installed!");
            Bukkit.getLogger().info("[RAI] Processing auto install...");
            // オートインストールを開始。スレッドスリープは特に意味ない。なんとなく。
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                install();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onDisable() {
        getLogger().info("GoodBye!");
    }

    public static boolean checkInstallStatus(){
        // インストール状態=Skript/scripts内にskファイルが存するかどうかをbooleanでリターン
        return fileSkriptScript.exists();
    }

    public static boolean checkDownloadStatus(){
        // ダウンロード状態=RAI内にskファイルが存するかどうかをbooleanでリターン
        return fileRaiScript.exists();
    }


    public static void install() throws IOException {
        // インストールされていれば当メソッドを抜ける
        if (checkInstallStatus()){
            Bukkit.getLogger().info("[RAI] RedApple is already installed!");
            return;
        }
        //ダウンロードされていなければ当メソッドを抜ける
        if (!checkDownloadStatus()){
            Bukkit.getLogger().warning("[RAI] plugins/RAI/redapple.sk is not found!");
            Bukkit.getLogger().warning("[RAI] Please download again. -> https://shimashima.studio.design/project/redapple");
            return;
        }
        // 上記二つをスルーしてここに来る
        Files.copy(pathRaiScript, pathSkriptScript);
        Bukkit.getLogger().info("[RAI] Installed Successfully!");
        Bukkit.getLogger().info("[RAI] Server will be reloaded.");
        Bukkit.reload();
    }

    public static void update() throws IOException {
        // まずSkript内のskを削除
        Files.deleteIfExists(pathSkriptScript);
        // レッツインストール
        install();
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
        // check
        if(cmd.getName().equalsIgnoreCase("check")){
            if (sender instanceof Player) {
                if (checkInstallStatus()){
                    sender.sendMessage(ChatColor.GREEN + ">RAI [INFO] " + ChatColor.WHITE + "RedApple is already installed!");
                } else{
                    sender.sendMessage(ChatColor.RED + ">RAI [ERROR] " + ChatColor.WHITE + "RedApple is not installed!");
                }
            }
            else {
                if (checkInstallStatus()){
                    Bukkit.getLogger().info("[RAI] RedApple is already installed!");
                } else{
                    Bukkit.getLogger().warning("[RAI] RedApple is not installed!");
                }
            }
            return true;
        }
        // install
        else if(cmd.getName().equalsIgnoreCase("install")){
            if (sender instanceof Player) {
                sender.sendMessage(ChatColor.RED + ">RAI [ERROR] " + ChatColor.WHITE + "Do this command on console!");
                return false;
            } else{
                try {
                    install();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return true;
            }
        }
        // update
        else if(cmd.getName().equalsIgnoreCase("update")){
            if (sender instanceof Player) {
                sender.sendMessage(ChatColor.RED + ">RAI [ERROR] " + ChatColor.WHITE + "Do this command on console!");
                return false;
            } else{
                try {
                    update();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return true;
            }
        }
        return false;
    }

}
