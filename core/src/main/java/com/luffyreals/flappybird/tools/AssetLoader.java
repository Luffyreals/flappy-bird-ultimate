package com.luffyreals.flappybird.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class AssetLoader {
    public static Texture background;
    public static Texture bird;
    public static Texture pipe;

    public static Music bgm;
    public static Sound jumpSound;
    public static Sound scoreSound;
    public static Sound gameOverSound;

    public static void load() {
        background = new Texture(Gdx.files.internal("background.jpg"));
        bird = new Texture(Gdx.files.internal("bird.png"));
        pipe = new Texture(Gdx.files.internal("pipe.png"));


        bgm = Gdx.audio.newMusic(Gdx.files.internal("bgm.mp3"));
        jumpSound = Gdx.audio.newSound(Gdx.files.internal("jump.wav"));
        scoreSound = Gdx.audio.newSound(Gdx.files.internal("score.wav"));
        gameOverSound = Gdx.audio.newSound(Gdx.files.internal("gameover.wav"));

        bgm.setLooping(true);
        bgm.setVolume(0.3f);
    }

    public static void dispose() {
        if (background != null) background.dispose();
        if (bird != null) bird.dispose();
        if (pipe != null) pipe.dispose();

        if (bgm != null) bgm.dispose();
        if (jumpSound != null) jumpSound.dispose();
        if (scoreSound != null) scoreSound.dispose();
        if (gameOverSound != null) gameOverSound.dispose();
    }
}
