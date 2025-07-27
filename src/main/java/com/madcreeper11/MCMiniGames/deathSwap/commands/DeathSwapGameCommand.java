package com.madcreeper11.MCMiniGames.deathSwap.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.madcreeper11.MCMiniGames.PluginMain;
import com.madcreeper11.MCMiniGames.deathSwap.DeathSwap;

public class DeathSwapGameCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	
		if(args.length <= 0) return false;
		if(args[0].toLowerCase().equals("start")) {
			
			if(Bukkit.getOnlinePlayers().size() < 2) {
				sender.sendMessage(ChatColor.DARK_RED + "Game needs 2 players minimum!");
				return true;
			} else {
				// This condition starts the game
				Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + "DeathSwap has been started!");
				PluginMain.getGameManager().switchGame(new DeathSwap());
				return true;
			}
		} else if(args[0].toLowerCase().equals("stop")) {
			
			if(PluginMain.getGameManager().getCurrentGame() == null) {
				sender.sendMessage(ChatColor.DARK_RED + "No game is currently running!");
				return true;
			}
			PluginMain.getGameManager().stopGame();
			return true;
		}
		
		return false;
	}
}
