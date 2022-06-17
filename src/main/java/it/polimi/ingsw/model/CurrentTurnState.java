package it.polimi.ingsw.model;

import it.polimi.ingsw.model.boards.token.ColTow;
import it.polimi.ingsw.model.boards.token.GamePhase;

import java.util.HashMap;

public class CurrentTurnState
{
    private String currentPlayer;
    private HashMap<String, Integer> turnOrder;
    private int turn;
    private int planningMoves;
    private int actionMoves;
    private boolean gameEnded;
    private ColTow winningTeam;
    private GamePhase gamePhase;
    private boolean lastTurn;
    private CurrentGameState game;

    /** Class constructor. Uses a HashMap to model in which order the players have to play for that turn */
    public CurrentTurnState(CurrentGameState game)
    {
        this.turnOrder = new HashMap<>();
        this.turn = 1;
        this.actionMoves = 0;
        this.planningMoves = 0;
        this.gameEnded = false;
        this.winningTeam = null;
        this.gamePhase = GamePhase.SETUP;
        this.game = game;
    }

    /** Iterates the Turn of the game */
    public void updateTurn()
    {
        turn++;
    }

    /** Iterates the actions that have been taken in a single turn */
    public void updateActionMoves()
    {
        actionMoves++;
        if(gamePhase == GamePhase.PLANNING || gamePhase == GamePhase.ACTION)
            game.notify(game.modelToJson());
    }

    public void updatePlanningMoves()
    {
        planningMoves++;
        if(gamePhase == GamePhase.PLANNING || gamePhase == GamePhase.ACTION)
            game.notify(game.modelToJson());
    }

    public void resetMoves()
    {
        actionMoves = 0;
        planningMoves = 0;
        if(gamePhase == GamePhase.PLANNING || gamePhase == GamePhase.ACTION)
            game.notify(game.modelToJson());
    }

    /** Signals that the game is ended and defines the winning team
     * @param t  the team that has won the game
     */
    public void updateWinner(ColTow t)
    {
        gameEnded = true;
        winningTeam = t;
    }

    /** Updates the turn order with an updated HashMap
     * @param map  the updated HashMap into which the players' turn order is stored
     */
    public void updateTurn(HashMap<String, Integer> map)
    {
        this.turnOrder = map;
    }

    public void updateGamePhase(GamePhase newPhase)
    {
        gamePhase = newPhase;

    }

    public String getCurrentPlayer(){
        return currentPlayer;
    }
    public GamePhase getGamePhase() {
        return gamePhase;
    }
    public ColTow getWinningTeam() {
        return winningTeam;
    }
    public boolean getIsGameEnded() {
        return gameEnded;
    }
    public int getActionMoves(){return actionMoves;}
    public int getPlanningMoves(){return planningMoves;}
    public int getTurn() {
        return turn;
    }
    public boolean getLastTurn(){ return  lastTurn; }
    public HashMap<String, Integer> getTurnOrder() {
        return turnOrder;
    }

    public void setLastTurn(){lastTurn = true;}
    public void setCurrentPlayer(String player){
        this.currentPlayer = player;
    }
}
