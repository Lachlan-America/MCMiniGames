package MCMiniGames.deathSwap.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import MCMiniGames.deathSwap.DeathSwapGame;

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
				DeathSwapGame.getGameSettings().initialiseCountdownTimer();
				DeathSwapGame.getGameManager().initialiseCurrentPlayers();
				DeathSwapGame.getGameSettings().setGameState(true);
				return true;
			}
		} else if(args[0].toLowerCase().equals("stop")) {
			
			if(!DeathSwapGame.getGameSettings().getGameState()) {
				sender.sendMessage(ChatColor.RED + "Death swap was never started!");
			} else {
				sender.sendMessage(ChatColor.RED + "Death swap has been stopped!");
				DeathSwapGame.getGameSettings().setGameState(false);
			}
			return true;
		}
		
		return false;
	}
}
