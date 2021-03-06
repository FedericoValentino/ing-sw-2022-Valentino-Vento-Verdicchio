package it.polimi.ingsw.Client.LightView.LightUtilities;

import it.polimi.ingsw.Client.LightView.LightBoards.LightSchool;
import it.polimi.ingsw.Client.LightView.LightTeams.LightPlayer;
import it.polimi.ingsw.Client.LightView.LightTeams.LightTeam;
import it.polimi.ingsw.Client.LightView.LightView;

/**
 * Class containing some methods useful to the whole LightView package
 */
public final class Utilities
{

    /**
     * Method findPlayerByName returns the Player with the given name
     * @param view  an instance of the view
     * @param nickname  the name of the LightPlayer object to seek
     * @return player, the correct player object
     */
    public static LightPlayer findPlayerByName(LightView view, String nickname)
    {

            for(LightTeam team: view.getCurrentTeams())

                for(LightPlayer player: team.getPlayers())

                    if(player.getName().equals(nickname))

                        return player;

            return null;
    }


    /**
     * Given a player's name, identifies and return the player's team
     * @param view an instance of the view
     * @param name the player's name
     * @return team, the correct team
     */
    public static LightTeam getPlayerTeam(LightView view, String name)
    {

        for (LightTeam team : view.getCurrentTeams())

            for (LightPlayer player : team.getPlayers())

                if (player.getName().equals(name))

                    return team;

        return null;
    }

    /**
     * Given the view and a specific name, returns the corresponding player's school
     * @param view an instance of the view
     * @param username the nickname of the player we seek
     * @return the correct school object
     */
    public static LightSchool getSchoolByName(LightView view, String username)
    {
        for(LightTeam t: view.getCurrentTeams())
        {
            for(LightPlayer p: t.getPlayers())
            {
                if(p.getName().equals(username))
                {
                    return p.getSchool();
                }
            }
        }
        return null;
    }
}

