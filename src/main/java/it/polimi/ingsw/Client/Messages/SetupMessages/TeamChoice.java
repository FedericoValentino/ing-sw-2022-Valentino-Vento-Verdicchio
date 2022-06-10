package it.polimi.ingsw.Client.Messages.SetupMessages;


public class TeamChoice extends StandardSetupMessage
{
    private int team;

    public TeamChoice(int team)
    {
        this.team = team;
        super.type = SETUPMESSAGETYPE.TEAM_CHOICE;
    }

    public int getTeam() {
        return team;
    }
}