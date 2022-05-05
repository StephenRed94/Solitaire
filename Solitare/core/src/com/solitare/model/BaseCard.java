package com.solitare.model;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;

public class BaseCard {
    private int id;
    private String suit;
    private int index;
    private boolean isFrontSide;
    private TextureAtlas textureAtlas;
    private Sprite sprite;

    public static int currentCardId = 0;
    public static final int CARD_SUIT_SIZE = 13;
    public static final String CARD_SUIT_SPADE = "spade";
    public static final String CARD_SUIT_HEART = "heart";
    public static final String CARD_SUIT_CLUB = "club";
    public static final String CARD_SUIT_DIAMOND = "diamond";
    public static final String CARD_BACK = "back";
    public static final int CARD_WIDTH = 88;
    public static final int CARD_HEIGHT = 124;
    public static final int CARD_GAP = 2;

    public BaseCard(int id, String suit, int index, boolean isFrontSide, TextureAtlas textureAtlas) {
        this.id = id;
        this.suit = suit;
        this.index = index;
        this.isFrontSide = isFrontSide;
        this.textureAtlas = textureAtlas;
        TextureRegion textureRegion = textureAtlas.findRegion(suit, index);
        if (!isFrontSide) {
            textureRegion = textureAtlas.findRegion("back", 2);
        }
        this.sprite = new Sprite(textureRegion);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TextureAtlas getTextureAtlas() {
        return textureAtlas;
    }

    public void setTextureAtlas(TextureAtlas textureAtlas) {
        this.textureAtlas = textureAtlas;
    }

    public boolean getIsFrontSide() {
        return isFrontSide;
    }

    public void setIsFrontSide(boolean isFrontSide) {
        this.isFrontSide = isFrontSide;
        TextureRegion textureRegion;
        if (isFrontSide) {
            textureRegion = this.textureAtlas.findRegion(suit, index);
        } else {
            textureRegion = this.textureAtlas.findRegion("back", 2);
        }
        this.sprite = new Sprite(textureRegion);
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public String getSuit() {
        return suit;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }

    public static ArrayList<BaseCard> initAllCards(TextureAtlas textureAtlas) {
        ArrayList<BaseCard> cards = new ArrayList<BaseCard>();
        // init back card
        BaseCard backCard = new BaseCard(currentCardId, CARD_BACK, 1, false, textureAtlas);
        currentCardId++;
        // init 4 suit cards
        ArrayList<BaseCard> spadeCards = initCardsBySuit(CARD_SUIT_SPADE, textureAtlas);
        ArrayList<BaseCard> heartCards = initCardsBySuit(CARD_SUIT_HEART, textureAtlas);
        ArrayList<BaseCard> clubCards = initCardsBySuit(CARD_SUIT_CLUB, textureAtlas);
        ArrayList<BaseCard> diamondCards = initCardsBySuit(CARD_SUIT_DIAMOND, textureAtlas);
        cards.addAll(spadeCards);
        cards.addAll(heartCards);
        cards.addAll(clubCards);
        cards.addAll(diamondCards);
        return cards;
    }

    private static ArrayList<BaseCard> initCardsBySuit(String suit, TextureAtlas textureAtlas) {
        ArrayList<BaseCard> cards = new ArrayList<BaseCard>();
        for (int i = 0; i < CARD_SUIT_SIZE; i++) {
            BaseCard newCard = new BaseCard(currentCardId, suit, (i + 1), false, textureAtlas);
            currentCardId++;
            cards.add(newCard);
        }
        return cards;
    }

    public static BaseCard findCardBySuitIndex(String suit, int index, ArrayList<BaseCard> cards) {
        BaseCard foundCard = null;
        for (BaseCard card : cards) {
            if (card.getSuit() == suit && card.getIndex() == index) {
                foundCard = card;
            }
        }
        return foundCard;
    }
}
