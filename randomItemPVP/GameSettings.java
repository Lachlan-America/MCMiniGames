package com.madcreeper11.MCMiniGames.randomItemPVP;

public class GameSettings {

	private boolean gameRunning;
	private int countdownTime;
	
	public GameSettings() {
		
		this.gameRunning = false;
		this.countdownTime = 30;
	}
	
	public boolean getGameState() {
		return gameRunning;
	}

	public void setGameState(boolean setRunning) {
		gameRunning = setRunning;
	}

	public int getCountdownTime() {
		return countdownTime;
	}

	public void setCountdownTime(int countdownTime) {
		this.countdownTime = countdownTime;
	}
}
