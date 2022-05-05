package com.solitare.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class TableauPile {
    private ArrayList<BaseCard> backTableauCards;
    private ArrayList<BaseCard> frontTableauCards;
    private String tableauType;
    private Sprite tableauSprite;
    private float positionX;
    private float positionY;

    public static final String TABLEAU_CARD_TYPE = "TableauCard";
    public static final int TABLEAU_PILE_ROWS = 7;
    public static final int TABLEAU_PILE_ROWS_X = 200;
    public static final int TABLEAU_PILE_ROWS_Y = 500;
    public static final int TABLEAU_PILE_ROWS_X_GAP = 120;
    public static final int TABLEAU_PILE_ROWS_Y_GAP = 25;

    public TableauPile(ArrayList<BaseCard> cards) {
        int cardSize = cards.size();
        positionX = TABLEAU_PILE_ROWS_X + TABLEAU_PILE_ROWS_X_GAP * (cardSize - 1);
        positionY = TABLEAU_PILE_ROWS_Y;
        Texture texture = new Texture("blank_card_spot.png");
        tableauSprite = new Sprite(texture);
        tableauSprite.setPosition(TABLEAU_PILE_ROWS_X + TABLEAU_PILE_ROWS_X_GAP * (cardSize - 1), TABLEAU_PILE_ROWS_Y);

        BaseCard frontCard = cards.remove(cardSize - 1);
        backTableauCards = cards;
        frontTableauCards = new ArrayList<BaseCard>();
        frontCard.setIsFrontSide(true);
        frontTableauCards.add(frontCard);
        tableauType = TABLEAU_CARD_TYPE + cardSize;
    }

    public String getTableauType() {
        return tableauType;
    }

    public void setTableauType(String tableauType) {
        this.tableauType = tableauType;
    }

    public void stackCard(BaseCard card) {
        if (card != null) {
            frontTableauCards.add(card);
        }
    }

    public void stackMultiCards(ArrayList<BaseCard> cards) {
        if (cards != null) {
            frontTableauCards.addAll(cards);
        }
    }

    public int getSize() {
        return backTableauCards.size() + frontTableauCards.size();
    }

    public void drewTableau(SpriteBatch batch) {
        tableauSprite.draw(batch);

        for (int i = 0; i < backTableauCards.size(); i++) {
            Sprite cardSprite = backTableauCards.get(i).getSprite();
            cardSprite.setPosition(positionX, positionY - TABLEAU_PILE_ROWS_Y_GAP * i);
            cardSprite.draw(batch);
        }

        for (int j = 0; j < frontTableauCards.size(); j++) {
            Sprite cardSprite = frontTableauCards.get(j).getSprite();
            cardSprite.setPosition(positionX, positionY - TABLEAU_PILE_ROWS_Y_GAP * (j + backTableauCards.size()));
            cardSprite.draw(batch);
        }
    }

    public BaseCard removeCard() {
        BaseCard removedCard = null;
        if (frontTableauCards.size() > 0) {
            removedCard = frontTableauCards.remove(frontTableauCards.size() - 1);
            if (frontTableauCards.size() == 0) {
                if (backTableauCards.size() > 0) {
                    BaseCard lastBackCard = backTableauCards.remove(backTableauCards.size() - 1);
                    lastBackCard.setIsFrontSide(true);
                    frontTableauCards.add(lastBackCard);
                }
            }
        }
        return removedCard;
    }

    public BaseCard flipUndo() {
        BaseCard lastBackCard = null;
        if (frontTableauCards.size() > 0) {
            lastBackCard = frontTableauCards.get(frontTableauCards.size() - 1);

            return lastBackCard;
        }
        return null;
    }

    public ArrayList<BaseCard> removeMultiCards() {
        ArrayList<BaseCard> removedFrontCards = new ArrayList<BaseCard>();
        if (frontTableauCards.size() > 0) {
            removedFrontCards.addAll(frontTableauCards);
            frontTableauCards = new ArrayList<BaseCard>();
        }
        if (backTableauCards.size() > 0) {
            BaseCard lastBackCard = backTableauCards.remove(backTableauCards.size() - 1);
            lastBackCard.setIsFrontSide(true);
            frontTableauCards.add(lastBackCard);
        }
        return removedFrontCards;
    }

    public ArrayList<PileableCard> getPileableCard(ArrayList<BaseCard> cards) {
        ArrayList<PileableCard> pileableCards = new ArrayList<PileableCard>();
        if (frontTableauCards.size() > 0) {
            BaseCard lastCard = frontTableauCards.get(frontTableauCards.size() - 1);
            String lastCardSuit = lastCard.getSuit();
            ArrayList<String> piledCardSuits = getPiledCardSuit(lastCardSuit);
            int piledCardIndex = lastCard.getIndex() - 1;
            BaseCard piledCard1 = BaseCard.findCardBySuitIndex(piledCardSuits.get(0), piledCardIndex, cards);
            BaseCard piledCard2 = BaseCard.findCardBySuitIndex(piledCardSuits.get(1), piledCardIndex, cards);
            pileableCards.add(new PileableCard(piledCard1, tableauType));
            pileableCards.add(new PileableCard(piledCard2, tableauType));
        } else {
            BaseCard spadeKCard = BaseCard.findCardBySuitIndex(BaseCard.CARD_SUIT_SPADE, 13, cards);
            BaseCard heartKCard = BaseCard.findCardBySuitIndex(BaseCard.CARD_SUIT_HEART, 13, cards);
            BaseCard clubKCard = BaseCard.findCardBySuitIndex(BaseCard.CARD_SUIT_CLUB, 13, cards);
            BaseCard diamondKCard = BaseCard.findCardBySuitIndex(BaseCard.CARD_SUIT_DIAMOND, 13, cards);
            pileableCards.add(new PileableCard(spadeKCard, tableauType));
            pileableCards.add(new PileableCard(heartKCard, tableauType));
            pileableCards.add(new PileableCard(clubKCard, tableauType));
            pileableCards.add(new PileableCard(diamondKCard, tableauType));
        }
        return pileableCards;
    }

    private ArrayList<String> getPiledCardSuit(String suit) {
        ArrayList<String> suits = new ArrayList<String>();
        if (suit == BaseCard.CARD_SUIT_SPADE || suit == BaseCard.CARD_SUIT_CLUB) {
            suits.add(BaseCard.CARD_SUIT_HEART);
            suits.add(BaseCard.CARD_SUIT_DIAMOND);
        } else if (suit == BaseCard.CARD_SUIT_HEART || suit == BaseCard.CARD_SUIT_DIAMOND) {
            suits.add(BaseCard.CARD_SUIT_SPADE);
            suits.add(BaseCard.CARD_SUIT_CLUB);
        }
        return suits;
    }

    public MoveableCard getMoveableCard() {
        BaseCard movedCard = null;
        MoveableCard moveableCard = null;
        if (frontTableauCards.size() > 0) {
            movedCard = frontTableauCards.get(frontTableauCards.size() - 1);
            moveableCard = new MoveableCard(movedCard, tableauType);
        }
        return moveableCard;
    }

    public MoveableCard getMultiMoveableCard() {
        BaseCard movedCard = null;
        MoveableCard moveableCard = null;
        if (frontTableauCards.size() > 1) {
            movedCard = frontTableauCards.get(0);
            if (movedCard.getIndex() != 13 || backTableauCards.size() > 0) {
                moveableCard = new MoveableCard(movedCard, tableauType);
            }
        }
        return moveableCard;
    }
}
