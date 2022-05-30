package model;

import model.boards.token.ColTow;
import model.boards.token.GamePhase;

import java.util.HashMap;

public class CurrentTurnState
{
    private HashMap<String, Integer> turnOrder;
    private int turn;
    private int moves;
    private boolean gameEnded;
    private ColTow WinningTeam;
    private GamePhase gamePhase;
    private boolean lastTurn;

    /** Class constructor. Uses a HashMap to model in which order the players have to play for that turn */
    public CurrentTurnState()
    {
        this.turnOrder = new HashMap<>();
        this.turn = 1;
        this.moves = 0;
        this.gameEnded = false;
        this.WinningTeam = null;
        this.gamePhase = GamePhase.SETUP;
    }

    /** Iterates the Turn of the game */
    public void UpdateTurn()
    {
        turn++;
    }

    /** Iterates the actions that have been taken in a single turn */
    public void UpdateMoves()
    {
        moves++;
    }

    public void ResetMoves(){ moves = 0;}

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
    }

    public void updateGamePhase(GamePhase newPhase)
    {
        gamePhase = newPhase;
    }

    public void setLastTurn(){lastTurn = true;}

    public GamePhase getGamePhase() {
        return gamePhase;
    }
    public ColTow getWinningTeam() {
        return WinningTeam;
    }
    public boolean getIsGameEnded() {
        return gameEnded;
    }
    public int getMoves(){return moves;}
    public int getTurn() {
        return turn;
    }
    public boolean getLastTurn(){ return  lastTurn; }

    public HashMap<String, Integer> getTurnOrder() {
        return turnOrder;
    }
}
