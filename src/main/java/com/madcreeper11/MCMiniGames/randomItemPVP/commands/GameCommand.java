package MCMiniGames.randomItemPVP.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import MCMiniGames.randomItemPVP.RandomItemPVPGame;

public class GameCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (args.length > 0) {
			if (args[0].toLowerCase().equals("start")) {
				Bukkit.broadcastMessage(ChatColor.AQUA + "Random Item PVP Starting!");
				RandomItemPVPGame.getGameSettings().setGameState(true);
				return true;
			}
			if (args[0].toLowerCase().equals("stop")) {
				Bukkit.broadcastMessage(ChatColor.AQUA + "Random Item PVP Stoppped!");
				RandomItemPVPGame.getGameSettings().setGameState(false);
				return true;
			}
		}
		return false;
	}

}
