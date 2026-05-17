package com.luffyreals.flappybird.screens;

import com.badlogic.gdx.Gdx;
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

public class MenuScreen implements Screen {
    private FlappyBirdGame game;
    private OrthographicCamera camera;
    private Viewport viewport;
    private BitmapFont font;
    private Vector3 touchPoint;

    private Texture dimOverlay;

    private Rectangle btnStart, btnTutorial, btnAchievement, btnSetting, btnExit;
    private final float BTN_WIDTH = 400f;
    private final float BTN_HEIGHT = 80f;
    private final float START_Y = 600f;
    private final float SPACING = 120f;

    public MenuScreen(FlappyBirdGame game) {
        this.game = game;
        camera = new OrthographicCamera();
        viewport = new FitViewport(FlappyBirdGame.V_WIDTH, FlappyBirdGame.V_HEIGHT, camera);
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);

        touchPoint = new Vector3();
        font = new BitmapFont();
        font.setColor(Color.WHITE);

        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(0, 0, 0, 0.4f);
        pixmap.fill();
        dimOverlay = new Texture(pixmap);
        pixmap.dispose();

        float centerX = FlappyBirdGame.V_WIDTH / 2f - BTN_WIDTH / 2f;
        btnStart       = new Rectangle(centerX, START_Y, BTN_WIDTH, BTN_HEIGHT);
        btnTutorial    = new Rectangle(centerX, START_Y - SPACING, BTN_WIDTH, BTN_HEIGHT);
        btnAchievement = new Rectangle(centerX, START_Y - SPACING * 2, BTN_WIDTH, BTN_HEIGHT);
        btnSetting     = new Rectangle(centerX, START_Y - SPACING * 3, BTN_WIDTH, BTN_HEIGHT);
        btnExit        = new Rectangle(centerX, START_Y - SPACING * 4, BTN_WIDTH, BTN_HEIGHT);
    }

    public void update() {
        if (Gdx.input.justTouched()) {
            camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

            if (btnStart.contains(touchPoint.x, touchPoint.y)) {
                game.setScreen(new PlayScreen(game));
            } else if (btnTutorial.contains(touchPoint.x, touchPoint.y)) {
                game.setScreen(new TutorialScreen(game));
            } else if (btnAchievement.contains(touchPoint.x, touchPoint.y)) {
                game.setScreen(new AchievementScreen(game));
            } else if (btnSetting.contains(touchPoint.x, touchPoint.y)) {
                game.setScreen(new SettingScreen(game, this));
            } else if (btnExit.contains(touchPoint.x, touchPoint.y)) {
                Gdx.app.exit(); // Thoát game
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
        font.getData().setScale(6f);
        font.draw(game.batch, "FLAPPY BIRD ULTIMATE", 0, 900, FlappyBirdGame.V_WIDTH, Align.center, false);
        font.getData().setScale(3f);
        drawButtonText("START GAME", btnStart);
        drawButtonText("TUTORIAL", btnTutorial);
        drawButtonText("ACHIEVEMENT", btnAchievement);
        drawButtonText("SETTING", btnSetting);
        drawButtonText("EXIT GAME", btnExit);

        game.batch.end();
    }

    private void drawButtonText(String text, Rectangle bounds) {
        font.draw(game.batch, text, bounds.x, bounds.y + bounds.height / 2 + 15, bounds.width, Align.center, false);
    }

    @Override public void resize(int width, int height) { viewport.update(width, height, true); }
    @Override public void show() {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {
        font.dispose();
        dimOverlay.dispose();
    }
}
