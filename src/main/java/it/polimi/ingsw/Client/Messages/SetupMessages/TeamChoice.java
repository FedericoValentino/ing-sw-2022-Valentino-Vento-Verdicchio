package it.polimi.ingsw.Client.Messages.SetupMessages;


public class TeamChoice extends StandardSetupMessage
{
    private int team;

    /**
     * Class Constructor, this message is used to tell the server which team the player wants to join
     * @param team
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