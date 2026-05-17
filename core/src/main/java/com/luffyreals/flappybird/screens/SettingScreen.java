package com.luffyreals.flappybird.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.luffyreals.flappybird.FlappyBirdGame;
import com.luffyreals.flappybird.tools.AssetLoader;

public class SettingScreen implements Screen {
    private FlappyBirdGame game;
    private Screen previousScreen;
    private OrthographicCamera camera;
    private Viewport viewport;
    private BitmapFont font;
    private Vector3 touchPoint;

    private Texture dimOverlay;
    private Texture panelBackground;

    private Rectangle btnBgmMinus, btnBgmPlus;
    private Rectangle btnSfxMinus, btnSfxPlus;
    private Rectangle btnBack;

    private float bgmVolume;
    private float sfxVolume;
    private Preferences prefs;

    public SettingScreen(FlappyBirdGame game, Screen previousScreen) {
        this.game = game;
        this.previousScreen = previousScreen;

        camera = new OrthographicCamera();
        viewport = new FitViewport(FlappyBirdGame.V_WIDTH, FlappyBirdGame.V_HEIGHT, camera);
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);

        touchPoint = new Vector3();
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        Pixmap dimPix = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        dimPix.setColor(0, 0, 0, 0.6f);
        dimPix.fill();
        dimOverlay = new Texture(dimPix);
        dimPix.dispose();

        Pixmap panelPix = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        panelPix.setColor(0.12f, 0.12f, 0.18f, 0.85f);
        panelPix.fill();
        panelBackground = new Texture(panelPix);
        panelPix.dispose();

        prefs = Gdx.app.getPreferences("FlappyBirdPrefs");
        bgmVolume = prefs.getFloat("bgmVolume", 0.3f);
        sfxVolume = prefs.getFloat("sfxVolume", 0.5f);

        float pWidth = 1200;
        float pX = (FlappyBirdGame.V_WIDTH - pWidth) / 2f;

        btnBgmMinus = new Rectangle(pX + 700, 640, 80, 80);
        btnBgmPlus  = new Rectangle(pX + 1000, 640, 80, 80);

        btnSfxMinus = new Rectangle(pX + 700, 490, 80, 80);
        btnSfxPlus  = new Rectangle(pX + 1000, 490, 80, 80);

        btnBack = new Rectangle(FlappyBirdGame.V_WIDTH / 2f - 200, 200, 400, 80);
    }

    public void update() {
        if (Gdx.input.justTouched()) {
            camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

            if (btnBgmMinus.contains(touchPoint.x, touchPoint.y)) {
                bgmVolume = Math.max(0f, bgmVolume - 0.1f);
                AssetLoader.bgm.setVolume(bgmVolume);
            } else if (btnBgmPlus.contains(touchPoint.x, touchPoint.y)) {
                bgmVolume = Math.min(1f, bgmVolume + 0.1f);
                AssetLoader.bgm.setVolume(bgmVolume);
            }

            if (btnSfxMinus.contains(touchPoint.x, touchPoint.y)) {
                sfxVolume = Math.max(0f, sfxVolume - 0.1f);
                AssetLoader.jumpSound.play(sfxVolume);
            } else if (btnSfxPlus.contains(touchPoint.x, touchPoint.y)) {
                sfxVolume = Math.min(1f, sfxVolume + 0.1f);
                AssetLoader.jumpSound.play(sfxVolume);
            }

            if (btnBack.contains(touchPoint.x, touchPoint.y)) {
                prefs.putFloat("bgmVolume", bgmVolume);
                prefs.putFloat("sfxVolume", sfxVolume);
                prefs.flush();
                game.setScreen(previousScreen);
            }
        }
    }

    @Override
    public void render(float delta) {
        update();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();

        game.batch.draw(AssetLoader.background, 0, 0, FlappyBirdGame.V_WIDTH, FlappyBirdGame.V_HEIGHT);
        game.batch.draw(dimOverlay, 0, 0, FlappyBirdGame.V_WIDTH, FlappyBirdGame.V_HEIGHT);

        float pWidth = 1200, pHeight = 800;
        float pX = (FlappyBirdGame.V_WIDTH - pWidth) / 2f;
        float pY = 140;
        game.batch.draw(panelBackground, pX, pY, pWidth, pHeight);

        font.getData().setScale(5f);
        font.draw(game.batch, "SETTINGS", 0, pY + pHeight - 60, FlappyBirdGame.V_WIDTH, Align.center, false);

        font.getData().setScale(3f);
        float labelX = pX + 80;

        font.draw(game.batch, "BACKGROUND MUSIC:", labelX, btnBgmMinus.y + 55);
        font.draw(game.batch, "-", btnBgmMinus.x, btnBgmMinus.y + 55, btnBgmMinus.width, Align.center, false);
        font.draw(game.batch, Math.round(bgmVolume * 100) + "%", btnBgmMinus.x + 80, btnBgmMinus.y + 55, 200, Align.center, false);
        font.draw(game.batch, "+", btnBgmPlus.x, btnBgmPlus.y + 55, btnBgmPlus.width, Align.center, false);

        font.draw(game.batch, "SOUND EFFECTS (SFX):", labelX, btnSfxMinus.y + 55);
        font.draw(game.batch, "-", btnSfxMinus.x, btnSfxMinus.y + 55, btnSfxMinus.width, Align.center, false);
        font.draw(game.batch, Math.round(sfxVolume * 100) + "%", btnSfxMinus.x + 80, btnSfxMinus.y + 55, 200, Align.center, false);
        font.draw(game.batch, "+", btnSfxPlus.x, btnSfxPlus.y + 55, btnSfxPlus.width, Align.center, false);
        font.getData().setScale(3.5f);
        font.draw(game.batch, "BACK TO MENU", btnBack.x, btnBack.y + 55, btnBack.width, Align.center, false);

        game.batch.end();
    }

    @Override public void resize(int width, int height) { viewport.update(width, height, true); }
    @Override public void show() {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {
        font.dispose();
        dimOverlay.dispose();
        panelBackground.dispose();
    }
}
