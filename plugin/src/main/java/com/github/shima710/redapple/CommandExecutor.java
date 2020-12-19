package com.github.shima710.redapple;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandExecutor implements org.bukkit.command.CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        if (cmd.getName().equalsIgnoreCase("start")) {
            SystemMain.gameStart();
            return true;
        }
        else if (cmd.getName().equalsIgnoreCase("k")) {
            if (sender instanceof Player) {
                // プレイヤーが実行
                Player player = (Player) sender;
                if (args.length != 0) {
                    // 引数がある

                }
                else{
                    sender.sendMessage(ChatColor.RED + ">ERROR" + ChatColor.WHITE + "数値を正しく入力してください");
                }
            }
            else {
                sender.sendMessage(ChatColor.RED + ">ERROR" + ChatColor.WHITE + "コンソールからは実行できません");
            }
            return true;
        }
        else if (cmd.getName().equalsIgnoreCase("s")) {
            return true;
        }
        return false;
    }
}