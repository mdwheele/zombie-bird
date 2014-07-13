package com.mdwheele.GameObjects;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

public class Pipe extends Scrollable {

    private Random r;

    private Rectangle skullUp, skullDown, pipeUp, pipeDown;

    public static final int VERTICAL_GAP = 45;
    public static final int SKULL_WIDTH = 24;
    public static final int SKULL_HEIGHT = 11;

    private float groundY;
    private boolean isScored = false;

    public Pipe(float x, float y, int width, int height, float scrollSpeed, float groundY) {
        super(x, y, width, height, scrollSpeed);
        r = new Random();

        skullUp = new Rectangle();
        skullDown = new Rectangle();
        pipeUp = new Rectangle();
        pipeDown = new Rectangle();
        this.groundY = groundY;
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        pipeUp.set(position.x, position.y, width, height);
        pipeDown.set(position.x, position.y + height + VERTICAL_GAP, width,
                groundY - (position.y + height + VERTICAL_GAP));

        // This shift is equivalent to: (SKULL_WIDTH - width) / 2
        skullUp.set(position.x - (SKULL_WIDTH - width) / 2, position.y + height
                - SKULL_HEIGHT, SKULL_WIDTH, SKULL_HEIGHT);
        skullDown.set(position.x - (SKULL_WIDTH - width) / 2, pipeDown.y,
                SKULL_WIDTH, SKULL_HEIGHT);

    }

    @Override
    public void reset(float newX) {
        super.reset(newX);
        height = r.nextInt(90) + 15;
        isScored = false;
    }

    public boolean collides(Bird bird) {
        if (position.x < bird.getX() + bird.getWidth()) {
            return (Intersector.overlaps(bird.getBoundingCircle(), pipeUp)
                    || Intersector.overlaps(bird.getBoundingCircle(), pipeDown)
                    || Intersector.overlaps(bird.getBoundingCircle(), skullUp) || Intersector
                    .overlaps(bird.getBoundingCircle(), skullDown));
        }
        return false;
    }

    public void onRestart(float x, float scrollSpeed) {
        velocity.x = scrollSpeed;
        reset(x);
    }

    public Rectangle getPipeDown()
    {
        return pipeDown;
    }

    public Rectangle getPipeUp()
    {
        return pipeUp;
    }

    public Rectangle getSkullDown()
    {
        return skullDown;
    }

    public Rectangle getSkullUp()
    {
        return skullUp;
    }

    public boolean isScored() { return isScored; }

    public void setScored(boolean scored) { isScored = scored; }
}