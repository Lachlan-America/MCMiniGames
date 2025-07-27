package com.madcreeper11.MCMiniGames.huntersVsSpeedrunners.tasks;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.madcreeper11.MCMiniGames.huntersVsSpeedrunners.HuntersVsSpeedrunnersGameType;

public class TimerTask extends BukkitRunnable {

	private int timer;

	public TimerTask(int time) {
		if (time <= 0) {
			throw new IllegalArgumentException("Counter must be greater than 0!");
		} else {
			this.timer = time;
		}
	}

	@Override
	public void run() {

		if (!HuntersVsSpeedrunnersGameType.getGameSettings().getGameState())
			return;

		if (timer <= 0) {
			for (Player player : Bukkit.getOnlinePlayers()) {
				player.sendTitle(ChatColor.RED + "The Hunters have been released!", null, 5, 40, 5);
				player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_AMBIENT, 1f, 1f);
			}
			this.cancel();
		}
		
		for (Player player : Bukkit.getOnlinePlayers()) {

			// Player#sendTitle(@Nullable String title, @Nullable String subtitle, int
			// fadeIn, int stay, int fadeOut)
			player.sendTitle(ChatColor.YELLOW + String.valueOf(timer), null, 0, 10, 5);
			player.playSound(player.getLocation(), Sound.BLOCK_LEVER_CLICK, 1f, 1f);
		}
		
		timer--;
		HuntersVsSpeedrunnersGameType.getGameSettings().setCountdownTime(timer);
	}

}