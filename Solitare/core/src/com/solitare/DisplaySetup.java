package com.solitare;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class DisplaySetup extends Game {
    static final int BACK = 1;

    private OrthographicCamera camera;
    private FitViewport viewport;
    private SpriteBatch batch;
    private BitmapFont font48;
    private BitmapFont font16;

    @Override
    public void create() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1280, 720);
        viewport = new FitViewport(1280, 720, camera);

        //Setup fonts


        setScreen(new TitleScreen(this, false, null));

    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    OrthographicCamera getCamera() {
        return camera;
    }

    SpriteBatch getBatch() {
        return batch;
    }

    FitViewport getViewport() {
        return viewport;
    }

    BitmapFont getFont48() {
        return font48;
    }

    BitmapFont getFont16() {
        return font16;
    }
}
