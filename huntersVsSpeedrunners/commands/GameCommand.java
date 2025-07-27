package com.madcreeper11.MCMiniGames.huntersVsSpeedrunners.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.madcreeper11.MCMiniGames.huntersVsSpeedrunners.HuntersVsSpeedrunnersGameType;

// The command that initiates the Hunters vs Speedrunners game type
public class GameCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	
		// Ensure the command is typed correctly
		if(args.length <= 0) return false;
		
		// /hvs start
		if(args[0].toLowerCase().equals("start")) {		
			if(HuntersVsSpeedrunnersGameType.getGameManager().getTeamHandler().getHunters().isEmpty() 
					|| HuntersVsSpeedrunnersGameType.getGameManager().getTeamHandler().getSpeedrunners().isEmpty()) {
				
				sender.sendMessage(ChatColor.DARK_RED + "Please ensure that players exist on hunter and speedrunner teams!");
				return true;
			}
			// Enable the game, set an initial countdown period to 30 seconds
			HuntersVsSpeedrunnersGameType.getGameSettings().setGameState(true);
			for(Player p : Bukkit.getOnlinePlayers()) {
				p.setHealth(20.0);
				p.setFoodLevel(20);
			}
			//HuntersVsSpeedrunnersGameType.getGameSettings().setCountdownTime(30);
			//HuntersVsSpeedrunnersGameType.getGameManager().startingSequence(HuntersVsSpeedrunnersGameType.getGameSettings().getCountdownTime());
			
			if(args.length > 1) {
				HuntersVsSpeedrunnersGameType.getGameSettings().setCountdownTime(30);
				HuntersVsSpeedrunnersGameType.getGameManager().startingSequence(HuntersVsSpeedrunnersGameType.getGameSettings().getCountdownTime());
			}
			else {
				Bukkit.getServer().broadcastMessage(ChatColor.GOLD + "Game Started!");
				HuntersVsSpeedrunnersGameType.getGameSettings().setCountdownTime(0);
			}
			return true;
		}
		
		if(args[0].toLowerCase().equals("stop")) {
			
			HuntersVsSpeedrunnersGameType.getGameSettings().setGameState(false);
			HuntersVsSpeedrunnersGameType.getGameManager().getTeamHandler().resetTeams();
			Bukkit.getServer().broadcastMessage(ChatColor.GOLD + "Game Ended!");
			
			return true;
		}	
		
		return false;
	}

}
