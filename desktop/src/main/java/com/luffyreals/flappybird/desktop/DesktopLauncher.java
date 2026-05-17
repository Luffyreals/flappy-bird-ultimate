package com.luffyreals.flappybird.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.luffyreals.flappybird.FlappyBirdGame;

public class DesktopLauncher {
    public static void main (String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setForegroundFPS(60);
        config.setTitle("Flappy Bird");
        config.setWindowedMode(1280, 720);
        config.setResizable(true);
        new Lwjgl3Application(new FlappyBirdGame(), config);
    }
}
