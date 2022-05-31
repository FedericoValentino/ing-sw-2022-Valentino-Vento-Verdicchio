package Client.LightView;

import com.fasterxml.jackson.annotation.JsonProperty;
import model.boards.token.ColTow;
import model.boards.token.GamePhase;

import java.util.HashMap;

public class LightTurnState
{
    private HashMap<String, Integer> turnOrder;
    private int turn;
    private int planningMoves;
    private int actionMoves;
    private GamePhase gamePhase;
    private boolean lastTurn;
    private String CurrentPlayer;


    public LightTurnState(@JsonProperty("turnOrder") HashMap<String, Integer> turnOrder,
                          @JsonProperty("turn") int turn,
                          @JsonProperty("planningMoves") int planningMoves,
                          @JsonProperty("actionMoves") int actionMoves,
                          @JsonProperty("isGameEnded")boolean gameEnded,
                          @JsonProperty("winningTeam") ColTow winningTeam,
                          @JsonProperty("gamePhase") GamePhase gamePhase,
                          @JsonProperty("lastTurn") boolean lastTurn,
                          @JsonProperty("CurrentPlayer") String CurrentPlayer)
    {
        this.CurrentPlayer = CurrentPlayer;
        this.turnOrder = turnOrder;
        this.turn = turn;
        this.actionMoves = actionMoves;
        this.planningMoves = planningMoves;
        this.gamePhase = gamePhase;
        this.lastTurn = lastTurn;
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
        return CurrentPlayer;
    }
}
