package com.solitare;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.TimeUtils;
import com.solitare.model.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.badlogic.gdx.Gdx.graphics;

public class SolitaireGame implements Screen, InputProcessor {
    DisplaySetup game;
    private ShapeRenderer shapeRenderer;
    SpriteBatch batch;
    TextureAtlas textureAtlas;
    TextureAtlas textureAtlas2;
    Boolean undoClicked = false;
    int rule = 0;
    long startTime, currentTime, timer;
    private final ArrayList<CardHandler> stock;
    private final ArrayList<CardHandler> spades;
    private final ArrayList<CardHandler> clubs;
    private final ArrayList<CardHandler> diamonds;
    private final ArrayList<CardHandler> hearts;
    private final List<List<CardHandler>> CardRows;
    private String[] locationArray;
    private ArrayList<MoveableCard> prevCard;
    private ArrayList<PileableCard> prevCardLocation;
    int activateStock = 0;
    String saveType;
    int tt = 0;
    int start = 0;
    int score = 0;
    int undoTimer = 0;
    int undoTurnCounter = 0;
    int SolitiareScoreTracking;
    private long startMillis;
    private TextureAtlas Images;
    Sprite sprite, sprite2, sprite3;
    private boolean isClick;
    TextureRegion textureRegion1, textureRegion2, textureRegion3, textureRegion4, textureRegion5, textureRegion6, textureRegion7, textureRegion8, textureRegion9, textureRegion10, textureRegion11, textureRegion12, textureRegion13, textureRegion14, textureRegion15,
            textureRegion16, textureRegion17, textureRegion18, textureRegion19, textureRegion20, textureRegion21, textureRegion22, textureRegion23, textureRegion24, textureRegion25, textureRegion26, textureRegion27, textureRegion28, textureRegion29, textureRegion30, textureRegion31, textureRegion32, textureRegion33, textureRegion34, textureRegion35, textureRegion36, textureRegion37, textureRegion38, textureRegion39, textureRegion40, textureRegion41, textureRegion42, textureRegion43, textureRegion44, textureRegion45, textureRegion46, textureRegion47, textureRegion48, textureRegion49, textureRegion50, textureRegion51, textureRegion52, backcard;

    private float posX, posY;
    private Sprite newgame, easymode, hardmode, wingame, undoButton, bgColor, logo;
    private boolean isEasyMode;
    private boolean isAutomatedTesting;
    private BitmapFont font;
    private BitmapFont Score;

    private BitmapFont ruleFont;
    int counter = 0;
    // Base card logic
    BaseCard saveMovedCard = null;
    private ArrayList<BaseCard> fullBaseCards;
    boolean MultiTrack;
    private ArrayList<BaseCard> shuffledCards;
    private Foundation foundationHeart;
    private Foundation foundationDiamond;
    private Foundation foundationClub;
    private Foundation foundationSpade;
    private StockPile stockPile;
    private TableauPile talonPileRow1;
    private TableauPile talonPileRow2;
    private TableauPile talonPileRow3;
    private TableauPile talonPileRow4;
    private TableauPile talonPileRow5;
    private TableauPile talonPileRow6;
    private TableauPile talonPileRow7;
    private ArrayList<TableauPile> talonPileRows;
    private float[] backgroundColor = {0.3f, 0.1f, 0.3f, 0.003f};
    CardHandler card;

    public SolitaireGame(DisplaySetup game) {
        this.game = game;
        isEasyMode = true;
        //FullDeck will be used to keep track of the deck.
        stock = new ArrayList<CardHandler>(52);

        CardRows = new ArrayList<List<CardHandler>>();
        for (int i = 0; i < 7; i++) {
            CardRows.add(new ArrayList<CardHandler>());
        }


        //When These 4 ArrayLists have 13 cards in them each
        //the game will end and the user will be declared a winner.
        spades = new ArrayList<CardHandler>();
        clubs = new ArrayList<CardHandler>();
        diamonds = new ArrayList<CardHandler>();
        hearts = new ArrayList<CardHandler>();

    }

    private void startNewGame() {
        // Initialize full base cards
        undoTurnCounter = 0;
        score = 0;
        rule = 0;
        startTime = System.currentTimeMillis();
        fullBaseCards = BaseCard.initAllCards(textureAtlas);
        shuffledCards = (ArrayList) fullBaseCards.clone();
        Collections.shuffle(shuffledCards);
        isAutomatedTesting = false;

        // Initialize Foundation cards
        foundationHeart = new Foundation(BaseCard.CARD_SUIT_HEART);
        foundationDiamond = new Foundation(BaseCard.CARD_SUIT_DIAMOND);
        foundationClub = new Foundation(BaseCard.CARD_SUIT_CLUB);
        foundationSpade = new Foundation(BaseCard.CARD_SUIT_SPADE);

        // Initialize TalonPile cards
        talonPileRow1 = new TableauPile(getTalonRowCards(1, shuffledCards));
        talonPileRow2 = new TableauPile(getTalonRowCards(2, shuffledCards));
        talonPileRow3 = new TableauPile(getTalonRowCards(3, shuffledCards));
        talonPileRow4 = new TableauPile(getTalonRowCards(4, shuffledCards));
        talonPileRow5 = new TableauPile(getTalonRowCards(5, shuffledCards));
        talonPileRow6 = new TableauPile(getTalonRowCards(6, shuffledCards));
        talonPileRow7 = new TableauPile(getTalonRowCards(7, shuffledCards));
        talonPileRows = new ArrayList<TableauPile>();
        talonPileRows.add(talonPileRow1);
        talonPileRows.add(talonPileRow2);
        talonPileRows.add(talonPileRow3);
        talonPileRows.add(talonPileRow4);
        talonPileRows.add(talonPileRow5);
        talonPileRows.add(talonPileRow6);
        talonPileRows.add(talonPileRow7);

        // Initialize StockPile cards
        stockPile = new StockPile(shuffledCards);

        //Initialize UndoArrayList
        prevCard = new ArrayList<MoveableCard>();
        prevCardLocation = new ArrayList<PileableCard>();
        locationArray = new String[2];
    }

    private void switchMode() {
        isEasyMode = !isEasyMode;
    }

    private ArrayList<BaseCard> getTalonRowCards(int number, ArrayList<BaseCard> cards) {
        ArrayList<BaseCard> getTalonRowCards = new ArrayList<BaseCard>();
        if (number == 1) {
            getTalonRowCards.add(cards.remove(1));
        } else {
            for (int i = 0; i < number; i++) {
                getTalonRowCards.add(cards.remove(1));
            }
        }
        return getTalonRowCards;
    }

    private ArrayList<PileableCard> getFoundationPileableCards(ArrayList<BaseCard> cards) {
        ArrayList<PileableCard> pileableCards = new ArrayList<PileableCard>();
        PileableCard pileableCardFH = foundationHeart.getPileableCard(cards);
        PileableCard pileableCardFD = foundationDiamond.getPileableCard(cards);
        PileableCard pileableCardFC = foundationClub.getPileableCard(cards);
        PileableCard pileableCardFS = foundationSpade.getPileableCard(cards);
        if (pileableCardFH != null) {
            pileableCards.add(pileableCardFH);
        }
        if (pileableCardFD != null) {
            pileableCards.add(pileableCardFD);
        }
        if (pileableCardFC != null) {
            pileableCards.add(pileableCardFC);
        }
        if (pileableCardFS != null) {
            pileableCards.add(pileableCardFS);
        }
        return pileableCards;
    }

    private ArrayList<PileableCard> getTalonPileableCards(ArrayList<BaseCard> cards) {
        ArrayList<PileableCard> pileableCards = new ArrayList<PileableCard>();
        ArrayList<PileableCard> pileableCardsRow1 = talonPileRow1.getPileableCard(cards);
        ArrayList<PileableCard> pileableCardsRow2 = talonPileRow2.getPileableCard(cards);
        ArrayList<PileableCard> pileableCardsRow3 = talonPileRow3.getPileableCard(cards);
        ArrayList<PileableCard> pileableCardsRow4 = talonPileRow4.getPileableCard(cards);
        ArrayList<PileableCard> pileableCardsRow5 = talonPileRow5.getPileableCard(cards);
        ArrayList<PileableCard> pileableCardsRow6 = talonPileRow6.getPileableCard(cards);
        ArrayList<PileableCard> pileableCardsRow7 = talonPileRow7.getPileableCard(cards);
        pileableCards.addAll(pileableCardsRow1);
        pileableCards.addAll(pileableCardsRow2);
        pileableCards.addAll(pileableCardsRow3);
        pileableCards.addAll(pileableCardsRow4);
        pileableCards.addAll(pileableCardsRow5);
        pileableCards.addAll(pileableCardsRow6);
        pileableCards.addAll(pileableCardsRow7);
        return pileableCards;
    }

    private boolean isWinner() {
        if (foundationHeart.checkFull()
                && foundationDiamond.checkFull()
                && foundationClub.checkFull()
                && foundationSpade.checkFull()
        ) {
            return true;
        }
        return false;
    }


    //Have to alter this and im done.
    private PileableCard checkCardMoveable(MoveableCard moveableCard, boolean isSingleCard) {
        // Trying to check whole pileable card
        ArrayList<PileableCard> wholePileableCardList = new ArrayList<PileableCard>();
        if (isSingleCard) {
            wholePileableCardList.addAll(getTalonPileableCards(fullBaseCards));
            wholePileableCardList.addAll(getFoundationPileableCards(fullBaseCards));
        } else {
            wholePileableCardList.addAll(getTalonPileableCards(fullBaseCards));
        }

        PileableCard finalPileableCard = null;
        BaseCard movedCard = moveableCard.getCard();
        if (wholePileableCardList.size() > 0) {
            for (PileableCard pileableCard : wholePileableCardList) {
                if (moveableCard != null && movedCard != null
                        && pileableCard != null && pileableCard.getCard() != null) {
                    if (movedCard.getId() == pileableCard.getCard().getId()
                            && movedCard.getSuit().equals(pileableCard.getCard().getSuit())) {
                        finalPileableCard = pileableCard;
                    }
                }
            }
        }
        //System.out.println(finalPileableCard.getCard().getIndex() + " finalPileableCard");
        return finalPileableCard;
    }


    private void PrevMove(MoveableCard moveableCard, PileableCard pileableCard, boolean isMultiMove) {

        String moveType = moveableCard.getType();
        String pileType = pileableCard.getType();
        System.out.println(moveType + " TESTM");
        System.out.println(pileType + " TESTP");
        BaseCard movedCard = null;
        ArrayList<BaseCard> movedMultiCards = null;


    }


    //To add undo all I have too do is alter this a bit
    private void moveAndStackCard(MoveableCard moveableCard, PileableCard pileableCard, boolean isMultiMove, boolean undo) {

        String moveType;
        String pileType;
        int num = 1;
        if (undo && !isMultiMove) {

            BaseCard cardToFlip = null;
            num = 0;
            try {
                saveMovedCard.setIsFrontSide(false);

            } catch (Exception ex) {
                rule = 1;
            }

            saveMovedCard = null;
            moveType = locationArray[1];
            pileType = locationArray[0];
            System.out.println(moveType);
            System.out.println(pileType);
            saveType = pileType;
            tt = 1;

        } else if (undo && isMultiMove) {
            rule = 1;
            moveType = null;
            pileType = null;
        } else {
            rule = 0;
            undoTurnCounter++;
            moveType = moveableCard.getType();
            pileType = pileableCard.getType();
            prevCard.add(moveableCard);
            prevCardLocation.add(pileableCard);
            locationArray[0] = moveType;
            locationArray[1] = pileType;
            System.out.println(moveType);
            System.out.println(pileType);
            counter++;
            undoTimer = 1;
        }

        BaseCard movedCard = null;
        ArrayList<BaseCard> movedMultiCards = null;


        // remove moveable card base on type


        //Add PileableCard to MoveableCard instead from prev save


        switch (moveType) {
            case StockPile.STOCK_CARD_TYPE:
                movedCard = stockPile.removeCard();


                break;

            case Foundation.FOUNDATION_CARD_TYPE + BaseCard.CARD_SUIT_HEART:
                score -= 5;

                movedCard = foundationHeart.removeCard();

                if (num == 1) {
                    saveMovedCard = foundationHeart.removeCard();
                }
                if (tt == 1 && !undo) {
                    tt = 0;
                    saveMovedCard.setIsFrontSide(true);
                }
                break;

            case Foundation.FOUNDATION_CARD_TYPE + BaseCard.CARD_SUIT_DIAMOND:
                score -= 5;
                movedCard = foundationDiamond.removeCard();
                if (num == 1) {
                    saveMovedCard = movedCard;
                }
                if (tt == 1 && !undo) {
                    tt = 0;
                    saveMovedCard.setIsFrontSide(true);
                }
                break;

            case Foundation.FOUNDATION_CARD_TYPE + BaseCard.CARD_SUIT_CLUB:
                score -= 5;
                movedCard = foundationClub.removeCard();
                if (num == 1) {
                    saveMovedCard = movedCard;
                }
                if (tt == 1 && !undo) {
                    tt = 0;
                    saveMovedCard.setIsFrontSide(true);
                }
                break;

            case Foundation.FOUNDATION_CARD_TYPE + BaseCard.CARD_SUIT_SPADE:
                score -= 5;

                movedCard = foundationSpade.removeCard();
                if (num == 1) {
                    saveMovedCard = movedCard;
                }
                if (tt == 1 && !undo) {
                    tt = 0;
                    saveMovedCard.setIsFrontSide(true);
                }
                break;

            case TableauPile.TABLEAU_CARD_TYPE + 1:
                if (!isMultiMove) {

                    movedCard = talonPileRow1.removeCard();
                    if (num == 1) {
                        saveMovedCard = talonPileRow1.flipUndo();
                    }
                    if (tt == 1 && !undo) {
                        tt = 0;
                        saveMovedCard.setIsFrontSide(true);
                    }

                } else {

                    movedMultiCards = talonPileRow1.removeMultiCards();
                }
                break;

            case TableauPile.TABLEAU_CARD_TYPE + 2:
                if (!isMultiMove) {

                    movedCard = talonPileRow2.removeCard();
                    if (num == 1) {
                        saveMovedCard = talonPileRow2.flipUndo();
                    }
                    if (tt == 1 && !undo) {
                        tt = 0;
                        saveMovedCard.setIsFrontSide(true);
                    }
                } else {

                    movedMultiCards = talonPileRow2.removeMultiCards();
                }
                break;

            case TableauPile.TABLEAU_CARD_TYPE + 3:
                if (!isMultiMove) {
                    movedCard = talonPileRow3.removeCard();
                    if (num == 1) {
                        saveMovedCard = talonPileRow3.flipUndo();
                    }
                    if (tt == 1 && !undo) {
                        tt = 0;
                        saveMovedCard.setIsFrontSide(true);
                    }
                } else {
                    movedMultiCards = talonPileRow3.removeMultiCards();
                }
                break;

            case TableauPile.TABLEAU_CARD_TYPE + 4:
                if (!isMultiMove) {
                    movedCard = talonPileRow4.removeCard();
                    if (num == 1) {
                        saveMovedCard = talonPileRow4.flipUndo();
                    }
                    if (tt == 1 && !undo) {
                        tt = 0;
                        saveMovedCard.setIsFrontSide(true);
                    }
                } else {
                    movedMultiCards = talonPileRow4.removeMultiCards();
                }
                break;

            case TableauPile.TABLEAU_CARD_TYPE + 5:
                if (!isMultiMove) {
                    movedCard = talonPileRow5.removeCard();
                    if (num == 1) {
                        saveMovedCard = talonPileRow5.flipUndo();
                    }
                    if (tt == 1 && !undo) {
                        tt = 0;
                        saveMovedCard.setIsFrontSide(true);
                    }
                } else {
                    movedMultiCards = talonPileRow5.removeMultiCards();
                }
                break;

            case TableauPile.TABLEAU_CARD_TYPE + 6:
                if (!isMultiMove) {
                    movedCard = talonPileRow6.removeCard();
                    if (num == 1) {
                        saveMovedCard = talonPileRow6.flipUndo();
                    }
                    if (tt == 1 && !undo) {
                        tt = 0;
                        saveMovedCard.setIsFrontSide(true);
                    }

                } else {
                    movedMultiCards = talonPileRow6.removeMultiCards();
                }
                break;

            case TableauPile.TABLEAU_CARD_TYPE + 7:
                if (!isMultiMove) {

                    movedCard = talonPileRow7.removeCard();
                    if (num == 1) {
                        saveMovedCard = talonPileRow7.flipUndo();
                    }
                    if (tt == 1 && !undo) {
                        tt = 0;
                        saveMovedCard.setIsFrontSide(true);
                    }
                    //this is the card i need to flip back
                    //	saveMovedCard = talonPileRow7.removeCard();

                } else {
                    movedMultiCards = talonPileRow7.removeMultiCards();
                }
                break;

            default:
                break;
        }

        // stack card base on type


        switch (pileType) {
            case Foundation.FOUNDATION_CARD_TYPE + BaseCard.CARD_SUIT_HEART:
                score += 5;
                foundationHeart.stackCard(movedCard);
                System.out.println(moveableCard.getCard().getIndex() + " " + pileableCard.getCard().getIndex());

                break;

            case Foundation.FOUNDATION_CARD_TYPE + BaseCard.CARD_SUIT_DIAMOND:
                score += 5;
                foundationDiamond.stackCard(movedCard);
                System.out.println(moveableCard.getCard().getIndex() + " " + pileableCard.getCard().getIndex());
                break;

            case Foundation.FOUNDATION_CARD_TYPE + BaseCard.CARD_SUIT_CLUB:
                score += 5;
                foundationClub.stackCard(movedCard);
                System.out.println(moveableCard.getCard().getIndex() + " " + pileableCard.getCard().getIndex());
                break;

            case Foundation.FOUNDATION_CARD_TYPE + BaseCard.CARD_SUIT_SPADE:
                score += 5;
                foundationSpade.stackCard(movedCard);
                System.out.println(moveableCard.getCard().getIndex() + " " + pileableCard.getCard().getIndex());
                break;

            case TableauPile.TABLEAU_CARD_TYPE + 1:
                if (!isMultiMove) {
                    talonPileRow1.stackCard(movedCard);
                    System.out.println(moveableCard.getCard().getIndex() + " " + pileableCard.getCard().getIndex());
                } else {
                    talonPileRow1.stackMultiCards(movedMultiCards);
                }
                break;

            case TableauPile.TABLEAU_CARD_TYPE + 2:
                if (!isMultiMove) {
                    talonPileRow2.stackCard(movedCard);

                    System.out.println(moveableCard.getCard().getIndex() + " " + pileableCard.getCard().getIndex());
                } else {
                    talonPileRow2.stackMultiCards(movedMultiCards);
                }
                break;

            case TableauPile.TABLEAU_CARD_TYPE + 3:
                if (!isMultiMove) {
                    talonPileRow3.stackCard(movedCard);
                    System.out.println(moveableCard.getCard().getIndex() + " " + pileableCard.getCard().getIndex());
                } else {
                    talonPileRow3.stackMultiCards(movedMultiCards);
                }
                break;

            case TableauPile.TABLEAU_CARD_TYPE + 4:
                if (!isMultiMove) {
                    talonPileRow4.stackCard(movedCard);
                    System.out.println(moveableCard.getCard().getIndex() + " " + pileableCard.getCard().getIndex());
                } else {
                    talonPileRow4.stackMultiCards(movedMultiCards);
                }
                break;

            case TableauPile.TABLEAU_CARD_TYPE + 5:
                if (!isMultiMove) {
                    talonPileRow5.stackCard(movedCard);
                    System.out.println(moveableCard.getCard().getIndex() + " " + pileableCard.getCard().getIndex());
                } else {
                    talonPileRow5.stackMultiCards(movedMultiCards);
                }
                break;

            case TableauPile.TABLEAU_CARD_TYPE + 6:
                if (!isMultiMove) {
                    talonPileRow6.stackCard(movedCard);
                    System.out.println(moveableCard.getCard().getIndex() + " " + pileableCard.getCard().getIndex());
                } else {
                    talonPileRow6.stackMultiCards(movedMultiCards);
                }
                break;

            case TableauPile.TABLEAU_CARD_TYPE + 7:
                if (!isMultiMove) {
                    talonPileRow7.stackCard(movedCard);
                    System.out.println(moveableCard.getCard().getIndex() + " " + pileableCard.getCard().getIndex());
                } else {
                    talonPileRow7.stackMultiCards(movedMultiCards);
                }
                break;

            default:
                break;
        }

    }

    private void continueTest() {
        isAutomatedTesting = true;
    }

    private void pauseTest() {
        isAutomatedTesting = false;
    }

    private void checkTalonPileRow() {
        for (TableauPile talonPileRow : talonPileRows) {
            MoveableCard moveableCard = talonPileRow.getMoveableCard();
            MoveableCard multiMoveableCard = talonPileRow.getMultiMoveableCard();
            if (moveableCard != null) {
                PileableCard pileableCard = checkCardMoveable(moveableCard, true);
                if (pileableCard != null) {
                    moveAndStackCard(moveableCard, pileableCard, false, false);
                } else if (multiMoveableCard != null) {
                    PileableCard multiPileableCard = checkCardMoveable(multiMoveableCard, false);
                    if (multiPileableCard != null) {
                        moveAndStackCard(multiMoveableCard, multiPileableCard, true, false);
                    }
                }
            }
        }

        stockPile.moveCardFromStock(isEasyMode);
        MoveableCard stockMoveableCard = stockPile.getMoveableCard();
        if (stockMoveableCard != null) {
            PileableCard stockPileableCard = checkCardMoveable(stockMoveableCard, true);
            if (stockPileableCard != null) {
                moveAndStackCard(stockMoveableCard, stockPileableCard, false, false);
            }
        }
    }


    @Override
    public void show() {
        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        startMillis = TimeUtils.millis();
        Texture LogoTexture = new Texture("logo_transparent.png");
        logo = new Sprite(LogoTexture);
        textureAtlas = new TextureAtlas(Gdx.files.internal("pack.atlas"));
        Images = new TextureAtlas("pack.atlas");
        Texture newgameTexture = new Texture("newgame.png");
        newgame = new Sprite(newgameTexture);
        Texture hardmodeTexture = new Texture("Hard.png");
        hardmode = new Sprite(hardmodeTexture);
        Texture easymodeTexture = new Texture("easy.png");
        easymode = new Sprite(easymodeTexture);
        Texture wingameTexture = new Texture("wingame.png");
        wingame = new Sprite(wingameTexture);
        Texture bgcolorTexture = new Texture("bgcolor.png");
        bgColor = new Sprite(bgcolorTexture);
        Texture undoTexture = new Texture("undo.png");


        undoButton = new Sprite(undoTexture);

        font = new BitmapFont();
        Score = new BitmapFont();
        ruleFont = new BitmapFont();

//		batch.setProjectionMatrix(camera.combined);

        startNewGame();

        Gdx.input.setInputProcessor(this);
    }


    @Override
    public void render(float delta) {
        //Screen Color
        Gdx.gl.glClearColor(backgroundColor[0], backgroundColor[1], backgroundColor[2], backgroundColor[3]);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        currentTime = System.currentTimeMillis();
        timer = (currentTime - startTime) / 1000;
        long hr = timer / 3600;
        long rem = timer % 3600;
        long mn = rem / 60;
        long sec = rem % 60;
        String hrStr = (hr < 10 ? "0" : "") + hr;
        String mnStr = (mn < 10 ? "0" : "") + mn;
        String secStr = (sec < 10 ? "0" : "") + sec;
        String showTime = hrStr + ":" + mnStr + ":" + secStr;


        font.draw(batch, "Time: " + showTime, 820, 660);
        Score.draw(batch, "Score: " + score, 720, 660);

        if (rule == 1) {
            ruleFont.draw(batch, "You cannot use undo, please move again", 320, 660);
        } else {
            ruleFont.draw(batch, "", 320, 660);
        }

        if (isWinner()) {
            wingame.setPosition(420, 250);
            wingame.draw(batch);
        }

        if (isAutomatedTesting) {
            long time = System.currentTimeMillis();
            while (System.currentTimeMillis() < time + 100) {
            }
            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run() {
                    checkTalonPileRow();
                }
            });
            System.out.println("Automated testing...");
        }

        newgame.setPosition(620, 50);
        newgame.draw(batch);
        bgColor.setPosition(50, 50);
        bgColor.draw(batch);
        logo.draw(batch);
        logo.setPosition(graphics.getWidth() / 2 - logo.getWidth() / 2 - 100, graphics.getHeight() / 2 - logo.getHeight() / 2 - 50);


        undoButton.setPosition(435, 50);
        undoButton.draw(batch);

        if (isEasyMode) {
            easymode.setPosition(870, 50);
            easymode.draw(batch);
        } else {
            hardmode.setPosition(870, 50);
            hardmode.draw(batch);
        }


        talonPileRow1.drewTableau(batch);
        talonPileRow2.drewTableau(batch);
        talonPileRow3.drewTableau(batch);
        talonPileRow4.drewTableau(batch);
        talonPileRow5.drewTableau(batch);
        talonPileRow6.drewTableau(batch);
        talonPileRow7.drewTableau(batch);

        stockPile.drewStock(batch);

        foundationHeart.drewFoundation(batch);
        foundationDiamond.drewFoundation(batch);
        foundationClub.drewFoundation(batch);
        foundationSpade.drewFoundation(batch);

        batch.end();
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        textureAtlas.dispose();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    //------------------------ InputProcessor Interface --------------------------------
    @Override
    public boolean keyDown(int keycode) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            System.out.println("One time move");
            checkTalonPileRow();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            System.out.println("Continue automated test");
            continueTest();
            checkTalonPileRow();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            System.out.println("Pause automated test");
            pauseTest();
            checkTalonPileRow();
        }

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
        System.out.println("===touchDown");
        System.out.print("screenX: ");
        System.out.println(screenX);
        System.out.print("screenY: ");
        System.out.println(screenY);
        String PrevLocation;

        if (button == Buttons.LEFT) {
            MoveableCard moveableCard = null;
            MoveableCard multiMoveableCard = null;
            if (screenX > 50 && screenX < 138 && screenY > 96 && screenY < 290) {
                System.out.println("clicked stock pile");

                stockPile.moveCardFromStock(isEasyMode);
            }

            if (screenX > 50 && screenX < 138 && screenY > 348 && screenY < 590) {
                System.out.println("clicked stock talon");
                moveableCard = stockPile.getMoveableCard();
            }

            if (screenX > 200 && screenX < 288 && screenY > 96 && screenY < 590) {
                System.out.println("clicked tableau row1");
                moveableCard = talonPileRow1.getMoveableCard();
                multiMoveableCard = talonPileRow1.getMultiMoveableCard();
            }

            if (screenX > 320 && screenX < 408 && screenY > 96 && screenY < 590) {
                System.out.println("clicked tableau row2");
                moveableCard = talonPileRow2.getMoveableCard();
                multiMoveableCard = talonPileRow2.getMultiMoveableCard();
            }

            if (screenX > 440 && screenX < 528 && screenY > 96 && screenY < 590) {
                System.out.println("clicked tableau row3");
                moveableCard = talonPileRow3.getMoveableCard();
                multiMoveableCard = talonPileRow3.getMultiMoveableCard();
            }

            if (screenX > 560 && screenX < 648 && screenY > 96 && screenY < 590) {
                System.out.println("clicked tableau row4");
                moveableCard = talonPileRow4.getMoveableCard();
                multiMoveableCard = talonPileRow4.getMultiMoveableCard();
            }

            if (screenX > 680 && screenX < 768 && screenY > 96 && screenY < 590) {
                System.out.println("clicked tableau row5");
                moveableCard = talonPileRow5.getMoveableCard();
                multiMoveableCard = talonPileRow5.getMultiMoveableCard();
            }

            if (screenX > 800 && screenX < 888 && screenY > 96 && screenY < 590) {
                System.out.println("clicked tableau row6");
                moveableCard = talonPileRow6.getMoveableCard();
                multiMoveableCard = talonPileRow6.getMultiMoveableCard();
            }

            if (screenX > 920 && screenX < 1008 && screenY > 96 && screenY < 590) {
                System.out.println("clicked tableau row7");
                moveableCard = talonPileRow7.getMoveableCard();
                multiMoveableCard = talonPileRow7.getMultiMoveableCard();
            }

            if (screenX > 1050 && screenX < 1138 && screenY > 96 && screenY < 220) {
                System.out.println("clicked foundation heart");
                moveableCard = foundationHeart.getMoveableCard();
            }

            if (screenX > 1050 && screenX < 1138 && screenY > 246 && screenY < 370) {
                System.out.println("clicked foundation diamond");
                moveableCard = foundationDiamond.getMoveableCard();
            }

            if (screenX > 1050 && screenX < 1138 && screenY > 396 && screenY < 520) {
                System.out.println("clicked foundation club");
                moveableCard = foundationClub.getMoveableCard();
            }

            if (screenX > 1050 && screenX < 1138 && screenY > 546 && screenY < 670) {
                System.out.println("clicked foundation spade");
                moveableCard = foundationSpade.getMoveableCard();
            }

            if (screenX > 435 && screenX < 585 && screenY > 610 && screenY < 670) {
                System.out.println("Undo");
                undoClicked = true;

            }


            if (undoClicked && undoTimer > 0 && undoTurnCounter >= 5) {
                undoTurnCounter = 0;
                undoTimer--;
                moveableCard = prevCard.get(0);
                PileableCard pileableCard = prevCardLocation.get(0);
                System.out.println(moveableCard.getCard().getIndex() + " MOVE");
                System.out.println(pileableCard.getCard().getIndex() + " PILE");
                moveAndStackCard(moveableCard, pileableCard, false, true);

            } else if (undoClicked && undoTimer == 0) {

                System.out.println("Must move card again before using undo");
            } else if (undoTurnCounter < 5) {
                int temp = 5 - undoTurnCounter;

                System.out.println("Must make " + temp + " turns to use undo function");

            }


//card originally clicked
            if (moveableCard != null && undoClicked == false) {
//Checks if the card can move
                PileableCard pileableCard = checkCardMoveable(moveableCard, true);
                //This will store

                //System.out.println(prevCard.get(0).getCard().getIndex());
                if (pileableCard != null) {
                    moveAndStackCard(moveableCard, pileableCard, false, false);

                } else if (multiMoveableCard != null) {

                    PileableCard multiPileableCard = checkCardMoveable(multiMoveableCard, false);
                    if (multiPileableCard != null) {

                        moveAndStackCard(multiMoveableCard, multiPileableCard, true, false);
                    }
                }
            }

            if (screenX > 620 && screenX < 840 && screenY > 610 && screenY < 670) {
                System.out.println("clicked new game");
                startNewGame();
            }

            if (screenX > 870 && screenX < 1010 && screenY > 610 && screenY < 670) {
                System.out.println("switch mode");
                switchMode();
            }
            if (screenX > 50 && screenX < 138 && screenY > 637 && screenY < 670) {
                if (screenX < 94) {
                    // change color to option 1 (green)
                    backgroundColor[0] = 0.3f;
                    backgroundColor[1] = 0.7f;
                    backgroundColor[2] = 0.3f;
                } else if (screenX > 94) {
                    // change color to option 2 (blue)
                    backgroundColor[0] = 0.2f;
                    backgroundColor[1] = 0.1f;
                    backgroundColor[2] = 0.6f;
                }
            }

        }
        undoClicked = false;
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        System.out.println("===touchUp");
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        System.out.println("===touchDragged");
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        System.out.println("===scrolled");
        return false;
    }

}