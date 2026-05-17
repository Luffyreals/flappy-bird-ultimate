package com.luffyreals.flappybird.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.luffyreals.flappybird.FlappyBirdGame;
import com.luffyreals.flappybird.sprites.Bird;
import com.luffyreals.flappybird.sprites.Pipe;
import com.luffyreals.flappybird.sprites.PipeHandler;
import com.luffyreals.flappybird.tools.AssetLoader;

public class PlayScreen implements Screen {
    private FlappyBirdGame game;
    private OrthographicCamera camera;
    private Viewport viewport;

    private Bird bird;
    private PipeHandler pipeHandler;

    public enum State { PLAYING, PAUSED, GAME_OVER }
    private State currentState;

    private int score;
    private BitmapFont font;
    private Texture dimOverlay;

    private Vector3 touchPoint;
    private Rectangle btnResume, btnSetting, btnMenu;
    private Rectangle btnRestartGameOver, btnMenuGameOver;

    public PlayScreen(FlappyBirdGame game) {
        this.game = game;
        camera = new OrthographicCamera();
        viewport = new FitViewport(FlappyBirdGame.V_WIDTH, FlappyBirdGame.V_HEIGHT, camera);
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);

        touchPoint = new Vector3();
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(0, 0, 0, 0.6f);
        pixmap.fill();
        dimOverlay = new Texture(pixmap);
        pixmap.dispose();

        float centerX = FlappyBirdGame.V_WIDTH / 2f - 200f;
        btnResume  = new Rectangle(centerX, 600, 400, 80);
        btnSetting = new Rectangle(centerX, 480, 400, 80);
        btnMenu    = new Rectangle(centerX, 360, 400, 80);

        btnRestartGameOver = new Rectangle(centerX, FlappyBirdGame.V_HEIGHT / 2f - 80, 400, 80);
        btnMenuGameOver    = new Rectangle(centerX, FlappyBirdGame.V_HEIGHT / 2f - 200, 400, 80);

        initGame();
    }

    private void initGame() {
        bird = new Bird(200, 540);
        pipeHandler = new PipeHandler(800);
        score = 0;
        currentState = State.PLAYING;

        com.badlogic.gdx.Preferences prefs = Gdx.app.getPreferences("FlappyBirdPrefs");
        int currentGames = prefs.getInteger("totalGames", 0);
        prefs.putInteger("totalGames", currentGames + 1);
        prefs.flush();
    }

    public void update(float delta) {
        camera.update();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) && currentState != State.GAME_OVER) {
            if (currentState == State.PLAYING) {
                currentState = State.PAUSED;
            } else if (currentState == State.PAUSED) {
                currentState = State.PLAYING;
            }
        }

        if (currentState == State.PLAYING) {
            if (Gdx.input.justTouched() || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                bird.jump();

                float currentSfxVol = Gdx.app.getPreferences("FlappyBirdPrefs").getFloat("sfxVolume", 0.5f);
                AssetLoader.jumpSound.play(currentSfxVol);

                com.badlogic.gdx.Preferences scorePrefs = Gdx.app.getPreferences("FlappyBirdPrefs");
                int currentFlaps = scorePrefs.getInteger("totalFlaps", 0);
                scorePrefs.putInteger("totalFlaps", currentFlaps + 1);
                scorePrefs.flush();
            }

            bird.update(delta);
            pipeHandler.update(delta);
            checkCollisionsAndScore();

            if (bird.getPosition().y <= 0) {
                triggerGameOver();
            }
        }
        else if (currentState == State.PAUSED) {
            if (Gdx.input.justTouched()) {
                camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

                if (btnResume.contains(touchPoint.x, touchPoint.y)) {
                    currentState = State.PLAYING;
                }
                else if (btnSetting.contains(touchPoint.x, touchPoint.y)) {
                    game.setScreen(new SettingScreen(game, this));
                }
                else if (btnMenu.contains(touchPoint.x, touchPoint.y)) {
                    game.setScreen(new MenuScreen(game));
                }
            }
        }
        else if (currentState == State.GAME_OVER) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.R)) initGame();
            else if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) game.setScreen(new MenuScreen(game));

            if (Gdx.input.justTouched()) {
                camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
                if (btnRestartGameOver.contains(touchPoint.x, touchPoint.y)) initGame();
                else if (btnMenuGameOver.contains(touchPoint.x, touchPoint.y)) game.setScreen(new MenuScreen(game));
            }
        }
    }

    private void checkCollisionsAndScore() {
        for (Pipe pipe : pipeHandler.getPipes()) {
            if (Intersector.overlaps(bird.getBoundingCircle(), pipe.getBoundsTop()) ||
                Intersector.overlaps(bird.getBoundingCircle(), pipe.getBoundsBot())) {
                triggerGameOver();
            }

            if (bird.getPosition().x > pipe.getPosTopPipe().x + Pipe.TUBE_WIDTH && !pipe.isScored()) {
                score++;
                pipe.setScored(true);

                float currentSfxVol = Gdx.app.getPreferences("FlappyBirdPrefs").getFloat("sfxVolume", 0.5f);
                AssetLoader.scoreSound.play(currentSfxVol);
            }
        }
    }

    private void triggerGameOver() {
        if (currentState != State.GAME_OVER) {
            currentState = State.GAME_OVER;

            float currentSfxVol = Gdx.app.getPreferences("FlappyBirdPrefs").getFloat("sfxVolume", 0.5f);
            AssetLoader.gameOverSound.play(currentSfxVol);

            com.badlogic.gdx.Preferences gamePrefs = Gdx.app.getPreferences("FlappyBirdPrefs");
            int currentHighScore = gamePrefs.getInteger("highScore", 0);
            if (score > currentHighScore) gamePrefs.putInteger("highScore", score);
            int currentTotalScore = gamePrefs.getInteger("totalScore", 0);
            gamePrefs.putInteger("totalScore", currentTotalScore + score);
            gamePrefs.flush();
        }
    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();

        game.batch.draw(AssetLoader.background, 0, 0, FlappyBirdGame.V_WIDTH, FlappyBirdGame.V_HEIGHT);
        pipeHandler.draw(game.batch);
        bird.draw(game.batch);

        if (currentState == State.PAUSED || currentState == State.GAME_OVER) {
            game.batch.draw(dimOverlay, 0, 0, FlappyBirdGame.V_WIDTH, FlappyBirdGame.V_HEIGHT);
        }

        font.getData().setScale(5f);
        font.draw(game.batch, "SCORE: " + score, 0, FlappyBirdGame.V_HEIGHT - 100, FlappyBirdGame.V_WIDTH, Align.center, false);

        if (currentState == State.PAUSED) {
            font.getData().setScale(6f);
            font.draw(game.batch, "PAUSED", 0, 850, FlappyBirdGame.V_WIDTH, Align.center, false);

            font.getData().setScale(3f);
            font.draw(game.batch, "RESUME", btnResume.x, btnResume.y + 55, btnResume.width, Align.center, false);
            font.draw(game.batch, "SETTING", btnSetting.x, btnSetting.y + 55, btnSetting.width, Align.center, false);
            font.draw(game.batch, "MAIN MENU", btnMenu.x, btnMenu.y + 55, btnMenu.width, Align.center, false);
        }

        if (currentState == State.GAME_OVER) {
            font.getData().setScale(6f);
            font.draw(game.batch, "GAME OVER", 0, FlappyBirdGame.V_HEIGHT / 2f + 150, FlappyBirdGame.V_WIDTH, Align.center, false);

            font.getData().setScale(3f);
            font.draw(game.batch, "RESTART (R)", btnRestartGameOver.x, btnRestartGameOver.y + 55, btnRestartGameOver.width, Align.center, false);
            font.draw(game.batch, "MAIN MENU (ESC)", btnMenuGameOver.x, btnMenuGameOver.y + 55, btnMenuGameOver.width, Align.center, false);
        }

        game.batch.end();
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
