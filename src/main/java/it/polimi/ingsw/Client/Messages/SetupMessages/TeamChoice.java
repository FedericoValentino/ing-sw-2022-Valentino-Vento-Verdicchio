package it.polimi.ingsw.Client.Messages.SetupMessages;

/**
 * Message used to tell the Server which team the player wants to enter
 */
public class TeamChoice extends StandardSetupMessage
{
    private int team;

    /**
     * Class Constructor, sets up the desired team
     */
    public TeamChoice(int team)
    {
        this.team = team;
        super.type = SETUPMESSAGETYPE.TEAM_CHOICE;
    }

    public int getTeam() {
        return team;
    }
}