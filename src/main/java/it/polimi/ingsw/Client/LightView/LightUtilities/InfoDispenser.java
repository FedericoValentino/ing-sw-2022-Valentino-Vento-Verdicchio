package it.polimi.ingsw.Client.LightView.LightUtilities;

import it.polimi.ingsw.Client.LightView.LightTeams.LightPlayer;
import it.polimi.ingsw.Client.LightView.LightTeams.LightTeam;
import it.polimi.ingsw.Client.LightView.LightTurnState;
import it.polimi.ingsw.model.boards.token.enumerations.GamePhase;

import java.util.ArrayList;

public class InfoDispenser {


    /** A factory method that uses the client's own information on the game to dynamically generate hints to display to the player
     * @param state the actual state of the game
     * @param teams the list of teams
     * @return the dynamically generated hint
     */
    public InternalMessage informationCreator(LightTurnState state, ArrayList<LightTeam> teams) {
        int players = 0;
        for (LightTeam team : teams) {
            for (LightPlayer player : team.getPlayers()) {
                players++;
            }
        }
        if (state.getGamePhase().equals(GamePhase.PLANNING)) {
            switch (state.getPlanningMoves()) {
                case 0:
                    return new InternalMessage("Choose which cloud to refill!");
                case 1:
                    return new InternalMessage("Choose an assistant card to play!");
                default:
                    throw new IllegalStateException("Unexpected value: " + state.getPlanningMoves());
            }
        } else if (state.getGamePhase().equals(GamePhase.ACTION)) {
            boolean threePlayerGame = players == 3;
            if (state.getActionMoves() == 0) {
                if (threePlayerGame)
                    return new InternalMessage("Move four students from your entrance");
                else
                    return new InternalMessage("Move three students from your entrance");
            } else if (state.getActionMoves() == 3) {
                if (!threePlayerGame)
                    return new InternalMessage("Move Mother Nature");
            } else if (state.getActionMoves() == 4) {
                if (threePlayerGame)
                    return new InternalMessage("Move Mother Nature");
                else
                    return new InternalMessage("Choose an island to refill your entrance");
            } else if (state.getActionMoves() == 5) {
                if (threePlayerGame)
                    return new InternalMessage("Choose an island to refill your entrance");
                else
                    return new InternalMessage("End your turn");
            }
        }
        return new InternalMessage("");
    }
}
