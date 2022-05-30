package Client.LightView;

import com.fasterxml.jackson.annotation.JsonProperty;
import model.boards.token.ColTow;
import model.boards.token.GamePhase;

import java.util.HashMap;

public class LightTurnState
{
    private HashMap<String, Integer> turnOrder;
    private int turn;
    private int moves;
    private GamePhase gamePhase;
    private boolean lastTurn;


    public LightTurnState(@JsonProperty("turnOrder") HashMap<String, Integer> turnOrder,
                          @JsonProperty("turn") int turn,
                          @JsonProperty("moves")int moves,
                          @JsonProperty("gameEnded")boolean gameEnded,
                          @JsonProperty("winningTeam") ColTow winningTeam,
                          @JsonProperty("GamePhase") GamePhase gamePhase,
                          @JsonProperty("lastTurn") boolean lastTurn)
    {
        this.turnOrder = turnOrder;
        this.turn = turn;
        this.moves = moves;
        this.gamePhase = gamePhase;
        this.lastTurn = lastTurn;
    }

    public HashMap<String, Integer> getTurnOrder() {
        return turnOrder;
    }

    public int getTurn() {
        return turn;
    }

    public int getMoves() {
        return moves;
    }

    public GamePhase getGamePhase() {
        return gamePhase;
    }

    public boolean isLastTurn() {
        return lastTurn;
    }
}
