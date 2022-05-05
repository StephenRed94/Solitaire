package com.solitare.model;

public class ActiveCard {
    private BaseCard card;
    private String type;

    public ActiveCard(BaseCard card, String type) {
        this.card = card;
        this.type = type;
    }

    public BaseCard getCard() {
        return card;
    }

    public void setCard(BaseCard card) {
        this.card = card;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
