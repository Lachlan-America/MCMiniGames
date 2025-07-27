package com.madcreeper11.MCMiniGames.huntersVsSpeedrunners.commands;

import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.madcreeper11.MCMiniGames.huntersVsSpeedrunners.HuntersVsSpeedrunnersGameType;

public class TeamsCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		String speedrunnerNames = "";
		String hunterNames = "";
		
		Set<UUID> speedrunnerUUIDs = HuntersVsSpeedrunnersGameType.getGameManager().getTeamHandler().getSpeedrunners().keySet();
		Set<UUID> hunterUUIDs = HuntersVsSpeedrunnersGameType.getGameManager().getTeamHandler().getHunters().keySet();
		
		for(UUID playerUUID : speedrunnerUUIDs) {
			speedrunnerNames += Bukkit.getOfflinePlayer(playerUUID).getName() + ", ";
		}
		for(UUID playerUUID : hunterUUIDs) {
			hunterNames += Bukkit.getOfflinePlayer(playerUUID).getName() + ", ";
		}
		
		// Remove end comma
		if(speedrunnerNames.length() > 0) {
			speedrunnerNames = speedrunnerNames.substring(0, speedrunnerNames.length()-2) + ".";
		}
		if(hunterNames.length() > 0) {
			hunterNames = hunterNames.substring(0, hunterNames.length()-2) + ".";
		}
		
		sender.sendMessage(String.format(ChatColor.GREEN + "Speedrunners: %s\n" + ChatColor.RED + "Hunters: %s", speedrunnerNames, hunterNames));
		return true;
	}

}
