package com.solitare;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;

import static com.badlogic.gdx.Gdx.graphics;


class TitleScreen implements Screen, InputProcessor {
    private final DisplaySetup game;
    private boolean newGamePlus;
    private Float time;
    int test = 0;
    private Sprite logo;
    private Sprite button;
    int i = 0;

    TitleScreen(DisplaySetup game, boolean newGamePlus, Float time) {
        this.game = game;
        this.newGamePlus = newGamePlus;
        this.time = time;
    }

    @Override
    public void show() {

        Texture texture = new Texture("logo.png");
        Texture texture2 = new Texture("button.png");
        logo = new Sprite(texture);
        button = new Sprite(texture2);
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        game.getBatch().setProjectionMatrix(game.getCamera().combined);
        game.getBatch().begin();
        logo.draw(game.getBatch());
        button.draw(game.getBatch());
        button.setPosition(420, 100);
        logo.setPosition(graphics.getWidth() / 2 - logo.getWidth() / 2, graphics.getHeight() / 2 - logo.getHeight() / 2 + 100);
        game.getBatch().end();
        if (Gdx.input.isTouched()) {
            {
                game.setScreen(new SolitaireGame(game));
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        game.getViewport().update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {


        if (button != Input.Buttons.LEFT || pointer > 0) {
            return false;
        }

        Vector3 vec = new Vector3(screenX, screenY, 0);

        vec = game.getCamera().unproject(vec, game.getViewport().getScreenX(), game.getViewport().getScreenY(), game.getViewport().getScreenWidth(), game.getViewport().getScreenHeight());


        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

}