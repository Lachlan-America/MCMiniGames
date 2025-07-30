package com.madcreeper11.MCMiniGames.util;

public class GameManager {
    private Minigame currentGame;

    public void switchGame(Minigame newGame) {
        if (currentGame != null) currentGame.stop();
        currentGame = newGame;
        currentGame.start();
    }

    public void stopGame() {
        if (currentGame != null) {
            currentGame.stop();
            currentGame = null;
        }
    }

    public Minigame getCurrentGame() {
        return currentGame;
    }
}