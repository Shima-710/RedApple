package com.github.shima710.redapple;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        if (cmd.getName().equalsIgnoreCase("start")) {
            SystemMain.gameStart();
            return true;
        }
        else if (cmd.getName().equalsIgnoreCase("k")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (args.length == 1) {
                    String str = args[0];
                    boolean isNumeric = true;
                    for (int i = 0; i < str.length(); i++) {
                        if (!Character.isDigit(str.charAt(i))) {
                            isNumeric = false;
                        }
                    }
                    if(isNumeric){
                        int gaku = Integer.parseInt(str);
                        if(RedApple.siturakuChoise){
                            Situraku.kyuusai(player,RedApple.siturakuWho,gaku);
                            return true;
                        }
                        else{
                            sender.sendMessage(ChatColor.RED + ">ERROR" + ChatColor.WHITE + "救済対象者がいません");
                            return false;
                        }
                    }
                    else {
                        sender.sendMessage(ChatColor.RED + ">ERROR" + ChatColor.WHITE + "数値を正しく入力してください");
                        return false;
                    }
                }
                else{
                    sender.sendMessage(ChatColor.RED + ">ERROR" + ChatColor.WHITE + "数値を正しく入力してください");
                    return false;
                }
            }
            else {
                sender.sendMessage(ChatColor.RED + ">ERROR" + ChatColor.WHITE + "コンソールからは実行できません");
                return false;
            }
        }
        else if (cmd.getName().equalsIgnoreCase("s")) {
            if(RedApple.siturakuChoise){
                Situraku.goSituraku(RedApple.siturakuWho);
                return true;
            }
            else{
                sender.sendMessage(ChatColor.RED + ">ERROR" + ChatColor.WHITE + "対象者がいません");
                return false;
            }
        }
        return false;
    }
}