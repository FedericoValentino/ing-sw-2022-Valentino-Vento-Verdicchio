package it.polimi.ingsw.Server.Answers.SetupAnswers;

/**
 * This Message is used to tell the clients the available team slots
 */
public class AvailableTeams extends StandardSetupAnswer
{
    private int[] availableTeams;

    /**
     * Class Constructor.
     */
    public AvailableTeams(int[] teams)
    {
        this.availableTeams = teams;
        super.type = SETUPANSWERTYPE.TEAMS;
    }

    public int[] getAvailableTeams() {
        return availableTeams;
    }
}
