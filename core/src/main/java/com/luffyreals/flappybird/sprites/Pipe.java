package com.luffyreals.flappybird.sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.luffyreals.flappybird.tools.AssetLoader;
import java.util.Random;

public class Pipe {
    public static final int TUBE_WIDTH = 130;

    private static final int FLUCTUATION = 450;
    private static final int TUBE_GAP = 320;
    private static final int LOWEST_OPENING = 200;
    private static final float HITBOX_OFFSET = 15f;

    private Vector2 posTopPipe, posBotPipe;
    private Rectangle boundsTop, boundsBot; // Hình chữ nhật để xét va chạm
    private Random rand;
    private boolean scored;

    public Pipe(float x) {
        rand = new Random();
        this.scored = false;
        posTopPipe = new Vector2(x, rand.nextInt(FLUCTUATION) + TUBE_GAP + LOWEST_OPENING);
        posBotPipe = new Vector2(x, posTopPipe.y - TUBE_GAP - AssetLoader.pipe.getHeight());

        boundsTop = new Rectangle(posTopPipe.x + HITBOX_OFFSET, posTopPipe.y, TUBE_WIDTH - HITBOX_OFFSET * 2, AssetLoader.pipe.getHeight());
        boundsBot = new Rectangle(posBotPipe.x + HITBOX_OFFSET, posBotPipe.y, TUBE_WIDTH - HITBOX_OFFSET * 2, AssetLoader.pipe.getHeight());
    }

    public void update(float delta, float velocityX) {
        posTopPipe.x += velocityX * delta;
        posBotPipe.x += velocityX * delta;

        boundsTop.setPosition(posTopPipe.x + HITBOX_OFFSET, posTopPipe.y);
        boundsBot.setPosition(posBotPipe.x + HITBOX_OFFSET, posBotPipe.y);
    }

    public void reposition(float x) {
        this.scored = false;
        posTopPipe.set(x, rand.nextInt(FLUCTUATION) + TUBE_GAP + LOWEST_OPENING);
        posBotPipe.set(x, posTopPipe.y - TUBE_GAP - AssetLoader.pipe.getHeight());

        boundsTop.setPosition(posTopPipe.x + HITBOX_OFFSET, posTopPipe.y);
        boundsBot.setPosition(posBotPipe.x + HITBOX_OFFSET, posBotPipe.y);
    }

    public void draw(SpriteBatch batch) {
        batch.draw(AssetLoader.pipe, posTopPipe.x, posTopPipe.y, TUBE_WIDTH, AssetLoader.pipe.getHeight(),
            0, 0, AssetLoader.pipe.getWidth(), AssetLoader.pipe.getHeight(), false, true);

        batch.draw(AssetLoader.pipe, posBotPipe.x, posBotPipe.y, TUBE_WIDTH, AssetLoader.pipe.getHeight());
    }

    public Vector2 getPosTopPipe() { return posTopPipe; }
    public Rectangle getBoundsTop() { return boundsTop; }
    public Rectangle getBoundsBot() { return boundsBot; }
    public boolean isScored() { return scored; }
    public void setScored(boolean b) { this.scored = b; }
}
