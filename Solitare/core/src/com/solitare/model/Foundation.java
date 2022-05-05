package com.solitare.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class Foundation {
    private ArrayList<BaseCard> foundationCards;
    private String suit;
    private String foundationType;
    private Sprite foundationSprite;
    private float foundationY;

    public static final String FOUNDATION_CARD_TYPE = "FoundationCard";
    public static final int FOUNDATION_X = 1050;
    public static final int FOUNDATION_HEART_Y = 500;
    public static final int FOUNDATION_DIAMOND_Y = 350;
    public static final int FOUNDATION_CLUB_Y = 200;
    public static final int FOUNDATION_SPADE_Y = 50;

    public Foundation(String suit) {
        Texture texture = new Texture("blank_card_spot.png");
        foundationSprite = new Sprite(texture);
        switch (suit) {
            case BaseCard.CARD_SUIT_HEART:
                foundationY = FOUNDATION_HEART_Y;
                break;

            case BaseCard.CARD_SUIT_DIAMOND:
                foundationY = FOUNDATION_DIAMOND_Y;
                break;

            case BaseCard.CARD_SUIT_CLUB:
                foundationY = FOUNDATION_CLUB_Y;
                break;

            case BaseCard.CARD_SUIT_SPADE:
                foundationY = FOUNDATION_SPADE_Y;
                break;

            default:
                foundationY = FOUNDATION_HEART_Y;
                break;
        }
        foundationSprite.setPosition(FOUNDATION_X, foundationY);

        this.foundationCards = new ArrayList<BaseCard>();
        this.suit = suit;
        this.foundationType = FOUNDATION_CARD_TYPE + suit;
    }

    public ArrayList<BaseCard> getFoundationCards() {
        return foundationCards;
    }

    public void setFoundationCards(ArrayList<BaseCard> foundationCards) {
        this.foundationCards = foundationCards;
    }

    public String getFoundationType() {
        return foundationType;
    }

    public void setFoundationType(String foundationType) {
        this.foundationType = foundationType;
    }

    public String getSuit() {
        return suit;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }

    public boolean checkFull() {
        boolean isFull = false;
        if (foundationCards.size() == 13) {
            isFull = true;
        }
        return isFull;
    }

    public int getSize() {
        return foundationCards.size();
    }

    public void drewFoundation(SpriteBatch batch) {
        foundationSprite.draw(batch);
        for (BaseCard baseCard : foundationCards) {
            Sprite cardSprite = baseCard.getSprite();
            cardSprite.setPosition(FOUNDATION_X, foundationY);
            cardSprite.draw(batch);
        }
    }

    public void stackCard(BaseCard card) {
        if (card != null) {
            foundationCards.add(card);
        }
    }

    public BaseCard removeCard() {
        BaseCard removedCard = null;
        if (foundationCards.size() > 0) {
            removedCard = foundationCards.remove(foundationCards.size() - 1);
        }
        return removedCard;
    }


    public PileableCard getPileableCard(ArrayList<BaseCard> cards) {
        BaseCard piledCard = null;
        PileableCard pileableCard = null;
        if (foundationCards.size() > 0 && foundationCards.size() < 13) {
            BaseCard lastCard = foundationCards.get(foundationCards.size() - 1);
            int piledCardIndex = lastCard.getIndex() + 1;
            piledCard = BaseCard.findCardBySuitIndex(suit, piledCardIndex, cards);
            pileableCard = new PileableCard(piledCard, foundationType);
        } else if (foundationCards.size() == 0) {
            piledCard = BaseCard.findCardBySuitIndex(suit, 1, cards);
            pileableCard = new PileableCard(piledCard, foundationType);
        }
        return pileableCard;
    }

    public MoveableCard getMoveableCard() {
        BaseCard movedCard = null;
        MoveableCard moveableCard = null;
        if (foundationCards.size() > 0) {
            movedCard = foundationCards.get(foundationCards.size() - 1);
            moveableCard = new MoveableCard(movedCard, foundationType);
        }
        return moveableCard;
    }
}
