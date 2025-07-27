package com.madcreeper11.MCMiniGames.deathSwap;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.checkerframework.checker.units.qual.min;

import com.madcreeper11.MCMiniGames.Minigame;
import com.madcreeper11.MCMiniGames.PluginMain;
import com.madcreeper11.MCMiniGames.deathSwap.commands.DeathSwapGameCommand;

public class DeathSwap implements Minigame {
	private BukkitRunnable gameTask;
	
	// Time in seconds
	private int minSwapTime;
	private int maxSwapTime;
	private int warningTime;
	private int countdownTime;
	private Random random;
	
	@Override
	public void init() {
		minSwapTime = 3 * 60; // 3 minutes
		maxSwapTime = 5 * 60; // 5 minutes
		warningTime = 15; // 15 seconds
		random = new Random();
		countdownTime = minSwapTime + random.nextInt(maxSwapTime - minSwapTime + 1);

        PluginMain.getInstance().getCommand("ds").setExecutor(new DeathSwapGameCommand());
	}

    @Override
    public void start() {
        gameTask = new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.broadcastMessage("§c[DeathSwap] §7Swapping in " + countdownTime + " seconds...");
                countdownTime--;

                if (countdownTime <= 0) {
                    // Perform the swap
                    Bukkit.broadcastMessage("§c[DeathSwap] §7Swapping players now!");
                    swapPlayers();
                    countdownTime = minSwapTime + random.nextInt(maxSwapTime - minSwapTime + 1);
                }
            }
        };
        gameTask.runTaskTimer(PluginMain.getInstance(), 0L, 20L);
    }

    @Override
    public void stop() {
        if (gameTask != null) gameTask.cancel();
    }

    @Override
    public String getName() {
        return "DeathSwap";
    }
}
