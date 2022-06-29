package it.polimi.ingsw.model;

import it.polimi.ingsw.model.boards.token.enumerations.ColTow;
import it.polimi.ingsw.model.boards.token.enumerations.GamePhase;

import java.util.HashMap;

/**
 * The second class responsible for holding information representing the current state of the game. Quite differently to
 * CurrentGameState, it holds all the information relative to the turn, game phases, players turn order, winning teams
 * and others.
 */
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

    /**
     * Class constructor. Uses a HashMap to model in which order the players have to play for that turn
     */
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

    /**
     * Iterates the Turn of the game
     */
    public void updateTurn()
    {
        turn++;
    }

    /**
     * Iterates the actions that have been taken in a single Action Phase. Since it is called at the end of every move,
     * it notifies the view to trigger a LightView update
     */
    public void updateActionMoves()
    {
        actionMoves++;
        if(gamePhase == GamePhase.PLANNING || gamePhase == GamePhase.ACTION)
            game.notify(game.modelToJson());
    }

    /**
     * Iterates the actions that have been taken in a single Planning Phase. Since it is called at the end of every move,
     * it notifies the view to trigger a LightView update
     */
    public void updatePlanningMoves()
    {
        planningMoves++;
        if(gamePhase == GamePhase.PLANNING || gamePhase == GamePhase.ACTION)
            game.notify(game.modelToJson());
    }

    /**
     * At the end of a player's Action Phase, it resets all parameters to the original values. For similar reasons as above,
     * it notifies the view
     */
    public void resetMoves()
    {
        actionMoves = 0;
        planningMoves = 0;
        if(gamePhase == GamePhase.PLANNING || gamePhase == GamePhase.ACTION)
            game.notify(game.modelToJson());
    }

    /**
     * Informs that the game is ended and proclaims a team as winner
     * @param team  the team that has won the game
     */
    public void updateWinner(ColTow team)
    {
        gameEnded = true;
        winningTeam = team;
    }

    /**
     * Updates the turn order with an updated HashMap
     * @param map  the updated HashMap into which the players' turn order is stored
     */
    public void updateTurn(HashMap<String, Integer> map)
    {
        this.turnOrder = map;
    }

    /**
     * Updates the Game Phase with a given one
     * @param newPhase the Phase the game is transitioning into
     */
    public void updateGamePhase(GamePhase newPhase)
    {
        gamePhase = newPhase;

    }

    /**
     * Sets the current turn as last turn of the game: it is triggered when the pouch empties or when a player ends his
     * assistant cards
     */
    public void setLastTurn(){lastTurn = true;}

    /**
     * After each player's turn, updates the currentPlayer field accordingly
     * @param player the player that has to become the currentPlayer
     */
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


}
