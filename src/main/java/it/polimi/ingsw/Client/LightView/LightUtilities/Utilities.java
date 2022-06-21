package it.polimi.ingsw.Client.LightView.LightUtilities;

import it.polimi.ingsw.Client.LightView.LightTeams.LightPlayer;
import it.polimi.ingsw.Client.LightView.LightTeams.LightTeam;
import it.polimi.ingsw.Client.LightView.LightView;
import it.polimi.ingsw.model.CurrentGameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Team;

public final class Utilities
{

    /** Method findPlayerByName returns the Player with the given name
     * @param view  an instance of the view
     * @param nickname  the name of the LightPlayer object to seek
     * @return p, the correct player object
     */
    public static LightPlayer findPlayerByName(LightView view, String nickname)
    {


            for(LightTeam team: view.getCurrentTeams())
            {
                for(LightPlayer player: team.getPlayers())
                {
                    if(player.getName().equals(nickname))
                        return player;
                }
            }
            return null;
    }
}
