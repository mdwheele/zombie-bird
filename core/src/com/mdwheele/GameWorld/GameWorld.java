package com.mdwheele.GameWorld;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.mdwheele.GameObjects.Bird;
import com.mdwheele.GameObjects.ScrollHandler;
import com.mdwheele.Helpers.AssetLoader;

public class GameWorld
{
    public enum GameState {
        READY, RUNNING, GAMEOVER
    }

    private Bird bird;
    private ScrollHandler scroller;
    private Rectangle ground;
    private int score = 0;
    private int midPointY;

    private GameState currentState;

    public GameWorld(int midPointY)
    {
        this.midPointY = midPointY;
        bird = new Bird(33, midPointY - 5, 17, 12);
        scroller = new ScrollHandler(this, midPointY + 66);
        ground = new Rectangle(0, midPointY + 66, 136, 11);
        currentState = GameState.READY;
    }

    public void update(float delta)
    {
        switch (currentState) {
            case READY:
                updateReady(delta);
                break;

            case RUNNING:
                updateRunning(delta);
                break;

            default:
                break;
        }

    }

    private void updateReady(float delta)
    {

    }

    private void updateRunning(float delta)
    {
        if (delta > .15f) {
            delta = .15f;
        }

        bird.update(delta);
        scroller.update(delta);

        if (scroller.collides(bird) && bird.isAlive()) {
            scroller.stop();
            bird.die();
            AssetLoader.dead.play();
        }

        if (Intersector.overlaps(bird.getBoundingCircle(), ground)) {
            scroller.stop();
            bird.die();
            bird.decelerate();
            currentState = GameState.GAMEOVER;
        }
    }

    public boolean isReady() { return currentState == GameState.READY; }
    public boolean isRunning() { return currentState == GameState.RUNNING; }
    public boolean isGameOver() { return currentState == GameState.GAMEOVER; }

    public void start()
    {
        currentState = GameState.RUNNING;
    }

    public Bird getBird()
    {
        return bird;
    }
    public ScrollHandler getScroller() { return scroller; }

    public int getScore()
    {
        return score;
    }

    public void addScore(int increment)
    {
        score += increment;
    }

    public void restart() {
        currentState = GameState.READY;
        score = 0;
        bird.onRestart(midPointY - 5);
        scroller.onRestart();
        currentState = GameState.READY;
    }
}
