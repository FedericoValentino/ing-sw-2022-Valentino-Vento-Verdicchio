package model;

import com.fasterxml.jackson.annotation.JsonProperty;
import model.boards.token.ColTow;
import model.boards.token.GamePhase;

import java.util.HashMap;

public class CurrentTurnState
{
    private String currentPlayer;
    private HashMap<String, Integer> turnOrder;
    private int turn;
    private int planningMoves;
    private int actionMoves;
    private boolean gameEnded;
    private ColTow WinningTeam;
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
        this.WinningTeam = null;
        this.gamePhase = GamePhase.SETUP;
        this.game = game;
    }

    /** Iterates the Turn of the game */
    public void UpdateTurn()
    {
        turn++;
    }

    /** Iterates the actions that have been taken in a single turn */
    public void UpdateActionMoves()
    {
        actionMoves++;
        if(gamePhase == GamePhase.PLANNING || gamePhase == GamePhase.ACTION)
            game.notify(game.modelToJson());
    }

    public void UpdatePlanningMoves()
    {
        planningMoves++;
        if(gamePhase == GamePhase.PLANNING || gamePhase == GamePhase.ACTION)
            game.notify(game.modelToJson());
    }

    public void ResetMoves()
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
        WinningTeam = t;
    }

    /** Updates the turn order with an updated HashMap
     * @param map  the updated HashMap into which the players' turn order is stored
     */
    public void updateTurn(HashMap<String, Integer> map)
    {
        this.turnOrder = map;
        ResetMoves();
    }

    public void updateGamePhase(GamePhase newPhase)
    {
        gamePhase = newPhase;

    }

    public void setLastTurn(){lastTurn = true;}

    public void setCurrentPlayer(String player){
        this.currentPlayer = player;
    }

    public String getCurrentPlayer(){
        return currentPlayer;
    }
    public GamePhase getGamePhase() {
        return gamePhase;
    }
    public ColTow getWinningTeam() {
        return WinningTeam;
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
}
