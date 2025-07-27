package com.madcreeper11.MCMiniGames.deathSwap;

import java.util.Random;

public class DeathSwapGameSettings {

	private Random random;
	private boolean gameRunning;
	// Seconds
	private int minSwapTime;
	private int maxSwapTime;
	private int warningTime;
	private int countdownTime;
	
	public DeathSwapGameSettings() {
		
		this.random = new Random();
		this.gameRunning = false;
		// Min = 2min, Max = 5min, Warning = 10sec
		this.minSwapTime = 3*60;
		this.maxSwapTime = 5*60;
		this.warningTime = 15;
	}
	
	public void loadSettings() {
	}
	public void saveSettings() {
	}
	
	public boolean getGameState() {
		return gameRunning;
	}
	public void setGameState(boolean setRunning) {
		gameRunning = setRunning;
	}	
	
	public int getMaxSwapTime() {
		return maxSwapTime;
	}
	public void setMaxSwapTime(int maxCountdownTime) {
		this.maxSwapTime = maxCountdownTime;
	}

	public int getMinSwapTime() {
		return minSwapTime;
	}
	public void setMinSwapTime(int minCountdownTime) {
		this.minSwapTime = minCountdownTime;
	}

	public int getWarningTime() {
		return warningTime;
	}
	public void setWarningTime(int warningTime) {
		this.warningTime = warningTime;
	}

	public void initialiseCountdownTimer() {
		countdownTime = minSwapTime + (int) (random.nextDouble() * (maxSwapTime - minSwapTime));	
	}
	// Time in sec
	public int getCountdownTime() {
		return countdownTime;
	}
	public void setCountdownTime(int countdownTime) {
		this.countdownTime = countdownTime;
	}
}

