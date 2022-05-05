package com.solitare.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.Collections;

public class StockPile {
    private ArrayList<BaseCard> stockCards;
    private ArrayList<BaseCard> stockTalonCards;
    private Sprite stockSprite;
    private Sprite stockTalonSprite;

    public static final int STOCK_PILE_SIZE = 24;
    public static final String STOCK_CARD_TYPE = "StockCard";
    public static final int STOCK_PILE_X = 50;
    public static final int STOCK_PILE_Y = 500;
    public static final int STOCK_PILE_GAP = 3;
    public static final int STOCK_TALON_PILE_X = 50;
    public static final int STOCK_TALON_PILE_Y = 250;
    public static final int STOCK_TALON_PILE_GAP = 25;

    public StockPile(ArrayList<BaseCard> cards) {
        Texture texture = new Texture("blank_card_spot.png");
        stockSprite = new Sprite(texture);
        stockTalonSprite = new Sprite(texture);
        stockSprite.setPosition(STOCK_PILE_X, STOCK_PILE_Y);
        stockTalonSprite.setPosition(STOCK_TALON_PILE_X, STOCK_TALON_PILE_Y);

        stockCards = cards;
        stockTalonCards = new ArrayList<BaseCard>();
    }

    public Sprite getStockSprite() {
        return stockSprite;
    }

    public void setStockSprite(Sprite stockSprite) {
        this.stockSprite = stockSprite;
    }

    public int getSize() {
        return stockCards.size() + stockTalonCards.size();
    }

    public void drewStock(SpriteBatch batch) {
        stockSprite.draw(batch);
        stockTalonSprite.draw(batch);

        for (int i = 0; i < stockCards.size(); i++) {
            Sprite cardSprite = stockCards.get(i).getSprite();
            cardSprite.setPosition(STOCK_PILE_X, STOCK_PILE_Y - STOCK_PILE_GAP * i);
            cardSprite.draw(batch);
        }
        int stockTalonSize = stockTalonCards.size();
        if (stockTalonSize >= 3) {
            for (int j = 0; j < stockTalonSize; j++) {
                Sprite cardSprite = stockTalonCards.get(j).getSprite();
                if (stockTalonSize - j <= 3) {
                    cardSprite.setPosition(STOCK_TALON_PILE_X, STOCK_TALON_PILE_Y - STOCK_PILE_GAP * j - STOCK_TALON_PILE_GAP * (3 - stockTalonSize + j));
                } else {
                    cardSprite.setPosition(STOCK_TALON_PILE_X, STOCK_TALON_PILE_Y - STOCK_PILE_GAP * j);
                }
                cardSprite.draw(batch);
            }
        } else {
            for (int k = 0; k < stockTalonSize; k++) {
                Sprite cardSprite = stockTalonCards.get(k).getSprite();
                cardSprite.setPosition(STOCK_TALON_PILE_X, STOCK_TALON_PILE_Y - STOCK_TALON_PILE_GAP * k);
                cardSprite.draw(batch);
            }
        }

    }

    public void moveCardFromStock(boolean isEasyMode) {
        int stockCardsSize = stockCards.size();
        if (isEasyMode) {
            if (stockCardsSize > 0) {
                BaseCard movedCard = stockCards.remove(stockCards.size() - 1);
                movedCard.setIsFrontSide(true);
                stockTalonCards.add(movedCard);
            } else {
                restock();
            }
        } else {
            if (stockCardsSize > 3) {
                for (int i = 0; i < 3; i++) {
                    BaseCard movedCard = stockCards.remove(stockCards.size() - 1);
                    movedCard.setIsFrontSide(true);
                    stockTalonCards.add(movedCard);
                }
            } else if (stockCardsSize > 0) {
                for (int j = 0; j < stockCardsSize; j++) {
                    BaseCard movedCard = stockCards.remove(stockCards.size() - 1);
                    movedCard.setIsFrontSide(true);
                    stockTalonCards.add(movedCard);
                }
            } else {
                restock();
            }
        }
    }

    public void restock() {
        Collections.reverse(stockTalonCards);
        stockCards.addAll(stockTalonCards);
        for (BaseCard card : stockCards) {
            card.setIsFrontSide(false);
        }
        stockTalonCards = new ArrayList<BaseCard>();
    }

    public BaseCard removeCard() {
        BaseCard removedCard = null;
        int stockTalonCardsize = stockTalonCards.size();
        if (stockTalonCardsize > 0) {
            removedCard = stockTalonCards.remove(stockTalonCardsize - 1);
        }
        return removedCard;
    }

    public MoveableCard getMoveableCard() {
        BaseCard movedCard = null;
        MoveableCard moveableCard = null;
        if (stockTalonCards.size() > 0) {
            movedCard = stockTalonCards.get(stockTalonCards.size() - 1);
            moveableCard = new MoveableCard(movedCard, STOCK_CARD_TYPE);
        }
        return moveableCard;
    }
}
