package com.luffyreals.flappybird.sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.luffyreals.flappybird.tools.AssetLoader;

public class Bird {
    private static final float GRAVITY = -18f;
    private static final float JUMP_VELOCITY = 550f;

    private Vector2 position;
    private Vector2 velocity;
    private Circle boundingCircle;

    private int width;
    private int height;

    public Bird(float x, float y) {
        position = new Vector2(x, y);
        velocity = new Vector2(0, 0);

        this.width = 90;
        this.height = 65;

        boundingCircle = new Circle(x + width / 2f, y + height / 2f, 20);
    }

    public void update(float delta) {
        velocity.add(0, GRAVITY);

        position.add(0, velocity.y * delta);

        if (position.y < 0) {
            position.y = 0;
            velocity.y = 0;
        }

        if (position.y > 1080 - height) {
            position.y = 1080 - height;
            velocity.y = 0;
        }

        boundingCircle.setPosition(position.x + width / 2f, position.y + height / 2f);
    }

    public void jump() {
        velocity.y = JUMP_VELOCITY;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(AssetLoader.bird, position.x, position.y, width, height);
    }

    public Circle getBoundingCircle() {
        return boundingCircle;
    }

    public Vector2 getPosition() {
        return position;
    }
}
