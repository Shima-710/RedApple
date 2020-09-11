package com.github.shima170.redapple;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public final class Redapple extends JavaPlugin {

    public static void main(String[] args){
        // Bukkit Plugin においてmainメソッドっていらないんですか？
    }

    @Override
    public void onEnable() {
        getLogger().info("Hello!! Enabling Complete!");

        /*
        // 初回ならばconfig
        if (!checkConfigExist()) {
            try {
                genConfig();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        */

        // ステータスチェック
        if (checkInstallStatus()){
            Bukkit.getLogger().info("[RAI] RedApple is now available! Enjoy game!");
        } else{
            Bukkit.getLogger().warning("[RAI] RedApple is not installed!");
            Bukkit.getLogger().info("[RAI] Processing auto install...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                installHoC();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDisable() {
        getLogger().info("RAI is Disabled!");
        getLogger().info("Good Bye!!");
    }

    public static boolean checkInstallStatus(){
        // インストールステータスをチェックしてboolean型で返すよ
        String pathPluginsSkript = "./plugins/Skript/scripts/redapple.sk";
        File filePluginsSkript = new File(pathPluginsSkript);
        return filePluginsSkript.exists();
    }

    /*
    public static boolean checkConfigExist(){
        // configファイルがあるかをチェックしてboolean型で返すよ
        String pathConfig = "./plugins/RAI/config.yml";
        File fileConfig = new File(pathConfig);
        return fileConfig.exists();
    }

    public static void genConfig() throws IOException {
        // configファイルを生成するよ
        Properties settings = new Properties();
        settings.setProperty("country", "USA");
        settings.setProperty("lang", "English");

        FileOutputStream out = null;
        try {
            out = new FileOutputStream("foo.properties");
            settings.store(out, "Foo Properties");
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
    */

    public static void installHoC() throws IOException {
        // インストール状態を確認 されてたら抜けるよ
        if (checkInstallStatus()){
            Bukkit.getLogger().info("[RAI] RedApple is already installed!");
            return;
        }
        // 下準備
        String pathWorldSkript = "./plugins/RAI/redapple.sk";
        File fileWorldSkript = new File(pathWorldSkript);
        Path pathsWorldSkript = Paths.get(pathWorldSkript);
        String pathPluginsSkript = "./plugins/Skript/scripts/redapple.sk";
        Path pathsPluginsSkript = Paths.get(pathPluginsSkript);
        boolean fileExistsWorldSkript = fileWorldSkript.exists();
        // そもファイルあるよな？
        if (fileExistsWorldSkript){
            // コピーするぜ
            Files.copy(pathsWorldSkript, pathsPluginsSkript);
            Bukkit.getLogger().info("[RAI] Installed Successfully! Server will be reloaded.");
            Bukkit.reload();
        }
        // いやファイルないんだが
        else{
            Bukkit.getLogger().warning("[RAI] plugins/RAI/redapple.sk is not found! Please download again.");
        }
    }

    public static void updateHoC() throws IOException {
        String pathPluginsSkript = "./plugins/Skript/scripts/redapple.sk";
        Path pathsPluginsSkript = Paths.get(pathPluginsSkript);
        Files.deleteIfExists(pathsPluginsSkript);
        installHoC();
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
        // hoc-check
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
        // hoc-install
        else if(cmd.getName().equalsIgnoreCase("install")){
            if (sender instanceof Player) {
                sender.sendMessage(ChatColor.RED + ">RAI [ERROR] " + ChatColor.WHITE + "Do this command on console!");
                return false;
            } else{
                try {
                    installHoC();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return true;
            }
        }
        // hoc-update
        else if(cmd.getName().equalsIgnoreCase("update")){
            if (sender instanceof Player) {
                sender.sendMessage(ChatColor.RED + ">RAI [ERROR] " + ChatColor.WHITE + "Do this command on console!");
                return false;
            } else{
                try {
                    updateHoC();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return true;
            }
        }
        return false;
    }

}
