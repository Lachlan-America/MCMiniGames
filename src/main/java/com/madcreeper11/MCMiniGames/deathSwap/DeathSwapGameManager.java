package com.madcreeper11.MCMiniGames.deathSwap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.madcreeper11.MCMiniGames.PluginMain;

public class DeathSwapGameManager implements Listener {
	
	private int timeRemaining;
	private List<Player> currentPlayers;
	
	public DeathSwapGameManager() {
    	Bukkit.getPluginManager().registerEvents(this, PluginMain.getInstance());
    	currentPlayers = new ArrayList<Player>();
    	
		Update();
	}

	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		if(currentPlayers.contains(player)) {
			// They are no longer a part of the game
			currentPlayers.remove(player);
		}
		
		if(currentPlayers.size() == 1) { 
			DeathSwapGame.getGameSettings().setGameState(false);
			Bukkit.broadcastMessage(ChatColor.GOLD + currentPlayers.get(0).getName() + " is the winner!");
			currentPlayers.remove(0);
			return;
		}
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		Player player = (Player) event.getEntity();
		if(currentPlayers.contains(player)) {
			// They are no longer a part of the game
			currentPlayers.remove(player);
		}
		
		if(currentPlayers.size() == 1) { 
			DeathSwapGame.getGameSettings().setGameState(false);
			Bukkit.broadcastMessage(ChatColor.GOLD + currentPlayers.get(0).getName() + " is the winner!");
			currentPlayers.remove(0);
			return;
		}
		
	}
	
	public void initialiseCurrentPlayers() {
		Collection<? extends Player> onlinePlayers = Bukkit.getOnlinePlayers();
		for(Player player : onlinePlayers) {
			currentPlayers.add(player);
		}
	}
	
	private void swapPlayerLocations() {
		
		// If no players exist, game should be stopped
		if(currentPlayers == null || currentPlayers.size() == 1) { 
			DeathSwapGame.getGameSettings().setGameState(false);
			return;
		}
		
		List<Location> locationList = new ArrayList<Location>();
		
		Collections.shuffle(currentPlayers);
		for(Player player : currentPlayers) {
			locationList.add(player.getLocation());
		}
		
		Collections.rotate(locationList, 1);
		int i = 0;
		for(Player player : currentPlayers) {
			player.teleport(locationList.get(i));
			i++;
		}
	}
	
	public void Update() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(PluginMain.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				if (!DeathSwapGame.getGameSettings().getGameState())
					return;
				
				timeRemaining = DeathSwapGame.getGameSettings().getCountdownTime();
				//Bukkit.broadcastMessage(timeRemaining + "");
				
				if(timeRemaining <= 0) {
					Bukkit.broadcastMessage(ChatColor.RED + "" + ChatColor.BOLD + "Swapping!");
					swapPlayerLocations();
					DeathSwapGame.getGameSettings().initialiseCountdownTimer();
					return;
				}
				
				if(timeRemaining <= DeathSwapGame.getGameSettings().getWarningTime()) {
					if(timeRemaining == 1) {
						Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',"&c&lSwapping in " + timeRemaining + " second!"));
					} else {
						Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',"&c&lSwapping in " + timeRemaining + " seconds!"));
					}
				}
				
				timeRemaining--;	
				DeathSwapGame.getGameSettings().setCountdownTime(timeRemaining);
			}

		}, 0, 20L);
	}
}
