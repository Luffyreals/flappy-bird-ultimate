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

public class AchievementScreen implements Screen {
    private FlappyBirdGame game;
    private OrthographicCamera camera;
    private Viewport viewport;
    private BitmapFont font;
    private Vector3 touchPoint;

    private Texture dimOverlay;
    private Texture panelBackground;

    private Rectangle btnBack;

    private int bestScore;
    private int totalGames;
    private int totalScore;
    private int totalFlaps;
    private float avgScore;
    private String medal;

    public AchievementScreen(FlappyBirdGame game) {
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
        panelPix.setColor(0.11f, 0.11f, 0.16f, 0.85f);
        panelPix.fill();
        panelBackground = new Texture(panelPix);
        panelPix.dispose();

        Preferences prefs = Gdx.app.getPreferences("FlappyBirdPrefs");
        bestScore  = prefs.getInteger("highScore", 0);
        totalGames = prefs.getInteger("totalGames", 0);
        totalScore = prefs.getInteger("totalScore", 0);
        totalFlaps = prefs.getInteger("totalFlaps", 0);

        avgScore = totalGames > 0 ? (float) totalScore / totalGames : 0f;

        if (bestScore >= 40) medal = "PLATINUM";
        else if (bestScore >= 30) medal = "GOLD";
        else if (bestScore >= 20) medal = "SILVER";
        else if (bestScore >= 10) medal = "BRONZE";
        else medal = "NONE";

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

        float pWidth = 1200;
        float pHeight = 800;
        float pX = (FlappyBirdGame.V_WIDTH - pWidth) / 2f;
        float pY = 140;
        game.batch.draw(panelBackground, pX, pY, pWidth, pHeight);

        font.getData().setScale(5f);
        font.draw(game.batch, "ACHIEVEMENTS", 0, pY + pHeight - 60, FlappyBirdGame.V_WIDTH, Align.center, false);

        font.getData().setScale(3f);
        float labelX = pX + 80;
        float valueX = pX + pWidth - 380;
        float startY = pY + pHeight - 180;
        float spacingY = 75f; // Tinh chỉnh giãn cách hàng cho cân đối

        font.draw(game.batch, "PERSONAL BEST:", labelX, startY);
        font.draw(game.batch, String.valueOf(bestScore), valueX, startY, 300, Align.right, false);

        font.draw(game.batch, "TOTAL GAMES PLAYED:", labelX, startY - spacingY);
        font.draw(game.batch, String.valueOf(totalGames), valueX, startY - spacingY, 300, Align.right, false);

        font.draw(game.batch, "TOTAL POINTS ACQUIRED:", labelX, startY - spacingY * 2);
        font.draw(game.batch, String.valueOf(totalScore), valueX, startY - spacingY * 2, 300, Align.right, false);

        font.draw(game.batch, "TOTAL FLAPS (SPACE/CLICK):", labelX, startY - spacingY * 3);
        font.draw(game.batch, String.valueOf(totalFlaps), valueX, startY - spacingY * 3, 300, Align.right, false);

        font.draw(game.batch, "AVERAGE SCORE / GAME:", labelX, startY - spacingY * 4);
        font.draw(game.batch, String.format("%.2f", avgScore), valueX, startY - spacingY * 4, 300, Align.right, false);

        font.draw(game.batch, "CURRENT MEDAL:", labelX, startY - spacingY * 5);

        if (medal.equals("PLATINUM")) font.setColor(Color.CYAN);
        else if (medal.equals("GOLD")) font.setColor(Color.GOLD);
        else if (medal.equals("SILVER")) font.setColor(Color.LIGHT_GRAY);
        else if (medal.equals("BRONZE")) font.setColor(Color.ORANGE);
        else font.setColor(Color.GRAY);

        font.draw(game.batch, medal, valueX, startY - spacingY * 5, 300, Align.right, false);
        font.setColor(Color.WHITE);

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
