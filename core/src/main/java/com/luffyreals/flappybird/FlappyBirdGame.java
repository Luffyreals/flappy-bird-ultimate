package com.luffyreals.flappybird;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.luffyreals.flappybird.screens.MenuScreen;
import com.luffyreals.flappybird.screens.PlayScreen;
import com.luffyreals.flappybird.tools.AssetLoader;

public class FlappyBirdGame extends Game {
    public static final int V_WIDTH = 1920;
    public static final int V_HEIGHT = 1080;

    public SpriteBatch batch;

    @Override
    public void create() {
        batch = new SpriteBatch();
        AssetLoader.load();
        com.badlogic.gdx.Preferences prefs = com.badlogic.gdx.Gdx.app.getPreferences("FlappyBirdPrefs");
        float savedBgmVol = prefs.getFloat("bgmVolume", 0.3f);
        if (AssetLoader.bgm != null) {
            AssetLoader.bgm.setVolume(savedBgmVol);
            if (!AssetLoader.bgm.isPlaying()) {
                AssetLoader.bgm.play();
            }
        }
        this.setScreen(new MenuScreen(this));
    }

    @Override
    public void render() {
        if (com.badlogic.gdx.Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.F11)) {
            if (com.badlogic.gdx.Gdx.graphics.isFullscreen()) {
                com.badlogic.gdx.Gdx.graphics.setWindowedMode(V_WIDTH, V_HEIGHT);
            }
            else {
                com.badlogic.gdx.Gdx.graphics.setFullscreenMode(com.badlogic.gdx.Gdx.graphics.getDisplayMode());
            }
        }
        super.render();
    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
        AssetLoader.dispose();
    }
}
