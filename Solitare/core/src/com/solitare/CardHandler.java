package com.solitare;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class CardHandler {
    private final Sprite backOfCard;
    private final Sprite frontOfCard;
    private final CardTypeHandler cardType;
    private final int rank;
    private boolean displayFront;

    Sprite getBack() {
        return backOfCard;
    }

    int getRank() {
        return rank;
    }

    CardTypeHandler getSuit() {
        return cardType;
    }

    boolean isDisplayFront() {
        return displayFront;
    }

    CardHandler(Sprite backOfCard, Sprite frontOfCard, CardTypeHandler cardType, int rank) {
        this.backOfCard = backOfCard;
        this.frontOfCard = frontOfCard;
        this.cardType = cardType;
        this.rank = rank;
        this.displayFront = false;
    }

    void setDisplayFront() {
        displayFront = !displayFront;
    }

    void draw(Batch batch, float x, float y) {
        if (displayFront) {
            frontOfCard.setPosition(x, y);
            frontOfCard.draw(batch);
        } else {
            backOfCard.setPosition(x, y);
            backOfCard.draw(batch);
        }
    }

    Vector2 getPosition() {
        if (displayFront) {
            return new Vector2(frontOfCard.getX(), frontOfCard.getY());
        } else {
            return new Vector2(backOfCard.getX(), backOfCard.getY());
        }
    }

    void toggleFaceUp() {
        displayFront = !displayFront;
    }
}