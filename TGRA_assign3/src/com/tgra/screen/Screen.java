package com.tgra.screen;

import com.badlogic.gdx.Game;

public abstract class Screen {
    Game game;

    public Screen (Game game) {
            this.game = game;
    }

    public abstract void update (float deltaTime);

    public abstract void present (float deltaTime);

    public abstract void pause ();

    public abstract void resume ();

    public abstract void dispose ();
}
