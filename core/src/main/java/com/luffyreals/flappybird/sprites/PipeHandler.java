package com.luffyreals.flappybird.sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public class PipeHandler {
    private static final int TUBE_SPACING = 450;
    private static final int TUBE_COUNT = 5;
    private static final float VELOCITY_X = -350f;

    private Array<Pipe> pipes;

    public PipeHandler(float startX) {
        pipes = new Array<Pipe>();
        for (int i = 1; i <= TUBE_COUNT; i++) {
            pipes.add(new Pipe(startX + (i * (TUBE_SPACING + Pipe.TUBE_WIDTH))));
        }
    }

    public void update(float delta) {
        for (Pipe pipe : pipes) {
            pipe.update(delta, VELOCITY_X);
            if (pipe.getPosTopPipe().x + Pipe.TUBE_WIDTH <= 0) {
                pipe.reposition(pipe.getPosTopPipe().x + ((Pipe.TUBE_WIDTH + TUBE_SPACING) * TUBE_COUNT));
            }
        }
    }

    public void draw(SpriteBatch batch) {
        for (Pipe pipe : pipes) {
            pipe.draw(batch);
        }
    }

    public Array<Pipe> getPipes() {
        return pipes;
    }
}
