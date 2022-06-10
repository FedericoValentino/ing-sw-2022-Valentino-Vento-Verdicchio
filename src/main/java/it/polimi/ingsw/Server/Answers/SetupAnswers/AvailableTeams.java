package it.polimi.ingsw.Server.Answers.SetupAnswers;


public class AvailableTeams extends StandardSetupAnswer
{
    private int[] availableTeams;

    public AvailableTeams(int[] teams)
    {
        this.availableTeams = teams;
        super.type = SETUPANSWERTYPE.TEAMS;
    }

    public int[] getAvailableTeams() {
        return availableTeams;
    }
}
