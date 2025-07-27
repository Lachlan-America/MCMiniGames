package MCMiniGames.huntersVsSpeedrunners.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import MCMiniGames.huntersVsSpeedrunners.HuntersVsSpeedrunnersGameType;
import MCMiniGames.huntersVsSpeedrunners.handlers.TeamHandler;

public class TeamAssignCommand implements CommandExecutor {

	// /team [speedrunner/hunter] [set/remove] {User1, User2...}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length < 3) {
			sender.sendMessage(ChatColor.RED + "Usage: /team [speedrunner/hunter] [add/remove] {user1, user2...}");
			return true;
		}
		// For one player:
		if(args.length < 4) {
			if(Bukkit.getPlayer(args[2].toLowerCase()) == null) {
				sender.sendMessage(ChatColor.RED + "That player is currently not online!");
				return true;
			}
			UUID playerUUID = Bukkit.getPlayer(args[2].toLowerCase()).getUniqueId();
			assignPlayerToTeam(args, playerUUID, sender, args[2].toLowerCase());
			return true;
		}
		// For more than one in the list
		String[] names = new String[args.length - 2];
		for (int i = 2; i <= args.length - 1; i++) {
			names[i - 2] = args[i].replaceAll(",", "");
		}

		// String[] names = args[].replaceAll("\\s","").split(",");
		
		for (String name : names) {
			if(Bukkit.getPlayer(name) == null) {
				continue;
			}
			UUID playerUUID = Bukkit.getPlayer(name.toLowerCase()).getUniqueId();
			assignPlayerToTeam(args, playerUUID, sender, name);
		}
		
		return true;
	}

	private void assignPlayerToTeam(String[] args, UUID playerUUID, CommandSender sender, String name) {
		
		TeamHandler teamHandler = HuntersVsSpeedrunnersGameType.getGameManager().getTeamHandler();
		
		if (args[0].toLowerCase().equals("speedrunner")) {
			if (args[1].toLowerCase().equals("add")) {
				teamHandler.createSpeedrunner(playerUUID);
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6"+name+" &7was successfully added to team &aSpeedrunner&7!"));
			} else if (args[1].toLowerCase().equals("remove")) {
				teamHandler.removeSpeedrunner(playerUUID);
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6"+name+" &7was successfully removed from team &aSpeedrunner&7!"));
			}
		}
		else if (args[0].toLowerCase().equals("hunter")) {
			if (args[1].toLowerCase().equals("add")) {
				teamHandler.createHunter(playerUUID);
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6"+name+" &7was successfully added to team &cHunter&7!"));
			} else if (args[1].toLowerCase().equals("remove")) {
				teamHandler.removeHunter(playerUUID);
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6"+name+" &7was successfully removed from team &cHunter&7!"));
			}
		}
	}
	
}
