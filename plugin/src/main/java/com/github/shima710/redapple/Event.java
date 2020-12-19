package com.github.shima710.redapple;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class Event implements Listener {
    public static RedApple plugin;
    public Event(RedApple instance) { plugin = instance; }



    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        if(RedApple.gameStatus) {
            Player player = e.getPlayer();
            if (!RedApple.gamePlayer.contains(player)) {
                player.sendMessage(ChatColor.GREEN + ">GAME " + ChatColor.WHITE + "ゲーム中のため観戦モードに移行しました");
                player.setGameMode(GameMode.SPECTATOR);
            }
        }
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent e){
        if(RedApple.gameStatus) {
            Player player = e.getPlayer();
            Block block = e.getClickedBlock();
            assert block != null;
            if(block.getType().equals(Material.DIAMOND_ORE)){
                Vote.openVoteBox(player);
            }
        }
    }

    @EventHandler
    public void foodChange(FoodLevelChangeEvent e){
        if(RedApple.gameStatus) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void healthChange(EntityDamageEvent e){
        if(RedApple.gameStatus) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void invCLick(InventoryClickEvent e) {
        if(RedApple.gameStatus){
            if(!e.getAction().equals(InventoryAction.NOTHING)){
                ItemStack item = e.getCurrentItem();
                Player player = (Player) e.getWhoClicked();
                assert item != null;
                if(item.equals(RedApple.redApple)){
                    e.setCancelled(true);
                    player.closeInventory();
                    Vote.vote("red",player);
                }
                else if(item.equals(RedApple.silverApple)){
                    e.setCancelled(true);
                    player.closeInventory();
                    Vote.vote("silver",player);
                }
                else if(item.equals(RedApple.goldApple)){
                    e.setCancelled(true);
                    player.closeInventory();
                    Vote.vote("gold",player);
                }
            }
        }
    }

}
