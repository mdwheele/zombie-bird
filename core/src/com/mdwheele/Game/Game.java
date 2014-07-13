package com.mdwheele.Game;

import com.mdwheele.Helpers.AssetLoader;
import com.mdwheele.Screens.GameScreen;

public class Game extends com.badlogic.gdx.Game
{

    @Override
    public void create()
    {
        AssetLoader.load();
        setScreen(new GameScreen());
    }

    @Override
    public void dispose()
    {
        AssetLoader.dispose();
    }

}
