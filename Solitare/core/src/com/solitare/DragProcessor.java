package com.solitare;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.utils.Timer;

public class DragProcessor implements InputProcessor {
    private final SolitaireGame gameScreen;

    private boolean isClick;

    DragProcessor(SolitaireGame gameScreen) {
        this.gameScreen = gameScreen;
        this.isClick = false;


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

    private void startClickTimer() {
        //Start isClick timer
        isClick = true;
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                isClick = false;
            }
        }, 0.2f);
    }
}
