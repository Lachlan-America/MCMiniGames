package com.madcreeper11.MCMiniGames.deathSwap.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.madcreeper11.MCMiniGames.PluginMain;

public class DeathSwapGameCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(args.length <= 0) return false;
			if (args[0].equalsIgnoreCase("start")) {
				if (sender instanceof Player) {
					Player player = (Player) sender;
					// Command logic for players
					if(Bukkit.getOnlinePlayers().size() < 2) {
					player.sendMessage(ChatColor.DARK_RED + "Game needs 2 players minimum!");
					return true;
				} else {
					// This condition starts the game
					Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + "DeathSwap has been started!");
					PluginMain.getGameManager().switchGame(PluginMain.getInstance().getDeathSwap());
					return true;
				}
			}
		} else if(args[0].equalsIgnoreCase("stop")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				if(PluginMain.getGameManager().getCurrentGame() == null) {
					player.sendMessage(ChatColor.DARK_RED + "No game is currently running!");
					return true;
				} else {
					PluginMain.getGameManager().stopGame();
					return true;
				}
			}
		}
		return false;
	}
}
