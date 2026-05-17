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

public class TutorialScreen implements Screen {
    private FlappyBirdGame game;
    private OrthographicCamera camera;
    private Viewport viewport;
    private BitmapFont font;
    private Vector3 touchPoint;

    private Texture dimOverlay;
    private Texture panelBackground;

    private Rectangle btnBack;

    public TutorialScreen(FlappyBirdGame game) {
        this.game = game;
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
        panelPix.setColor(0.1f, 0.1f, 0.15f, 0.8f);
        panelPix.fill();
        panelBackground = new Texture(panelPix);
        panelPix.dispose();

        btnBack = new Rectangle(FlappyBirdGame.V_WIDTH / 2f - 200, 200, 400, 80);
    }

    public void update() {
        if (Gdx.input.justTouched()) {
            camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
            if (btnBack.contains(touchPoint.x, touchPoint.y)) {
                game.setScreen(new MenuScreen(game));
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

        float panelWidth = 1200;
        float panelHeight = 800;
        float panelX = (FlappyBirdGame.V_WIDTH - panelWidth) / 2f;
        float panelY = 140;
        game.batch.draw(panelBackground, panelX, panelY, panelWidth, panelHeight);

        font.getData().setScale(5f);
        font.draw(game.batch, "HOW TO PLAY", 0, panelY + panelHeight - 80, FlappyBirdGame.V_WIDTH, Align.center, false);

        font.getData().setScale(3f);
        float textStartY = panelY + panelHeight - 250;
        float lineSpacing = 100f;

        font.draw(game.batch, "1. Press [SPACE] or [CLICK] to flap your wings.", 0, textStartY, FlappyBirdGame.V_WIDTH, Align.center, false);
        font.draw(game.batch, "2. Avoid the green pipes and the ground.", 0, textStartY - lineSpacing, FlappyBirdGame.V_WIDTH, Align.center, false);
        font.draw(game.batch, "3. Pass through pipes to earn points.", 0, textStartY - lineSpacing * 2, FlappyBirdGame.V_WIDTH, Align.center, false);
        font.draw(game.batch, "4. Press [ESC] during gameplay to Pause.", 0, textStartY - lineSpacing * 3, FlappyBirdGame.V_WIDTH, Align.center, false);

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
