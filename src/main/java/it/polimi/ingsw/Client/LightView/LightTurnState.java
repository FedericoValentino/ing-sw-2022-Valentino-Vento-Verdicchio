package it.polimi.ingsw.Client.LightView;

import it.polimi.ingsw.Observer.Observable;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.model.boards.token.enumerations.ColTow;
import it.polimi.ingsw.model.boards.token.enumerations.GamePhase;

import java.util.HashMap;

public class LightTurnState extends Observable
{
    private HashMap<String, Integer> turnOrder;
    private int turn;
    private int planningMoves;
    private int actionMoves;
    private GamePhase gamePhase;
    private boolean lastTurn;
    private String currentPlayer;


    public LightTurnState(@JsonProperty("turnOrder") HashMap<String, Integer> turnOrder,
                          @JsonProperty("turn") int turn,
                          @JsonProperty("planningMoves") int planningMoves,
                          @JsonProperty("actionMoves") int actionMoves,
                          @JsonProperty("isGameEnded")boolean gameEnded,
                          @JsonProperty("winningTeam") ColTow winningTeam,
                          @JsonProperty("gamePhase") GamePhase gamePhase,
                          @JsonProperty("lastTurn") boolean lastTurn,
                          @JsonProperty("currentPlayer") String currentPlayer)
    {
        this.currentPlayer = currentPlayer;
        this.turnOrder = turnOrder;
        this.turn = turn;
        this.actionMoves = actionMoves;
        this.planningMoves = planningMoves;
        this.gamePhase = gamePhase;
        this.lastTurn = lastTurn;
    }


    public void updateTurn(LightTurnState light)
    {
        this.currentPlayer = light.getCurrentPlayer();
        this.turnOrder = light.getTurnOrder();
        this.turn = light.getTurn();
        this.actionMoves = light.getActionMoves();
        this.planningMoves = light.getPlanningMoves();
        this.gamePhase = light.getGamePhase();
        this.lastTurn = light.isLastTurn();
        notifyLight(this);
    }

    public HashMap<String, Integer> getTurnOrder() {
        return turnOrder;
    }

    public int getTurn() {
        return turn;
    }

    public int getActionMoves() {
        return actionMoves;
    }

    public int getPlanningMoves() { return  planningMoves; }

    public GamePhase getGamePhase() {
        return gamePhase;
    }

    public boolean isLastTurn() {
        return lastTurn;
    }

    public String getCurrentPlayer()
    {
        return currentPlayer;
    }
}
