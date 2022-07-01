package it.polimi.ingsw.Server.Answers.SetupAnswers;

/**
 * This Message is used to tell the client the game is starting
 */
public class GameStarting extends StandardSetupAnswer
{
    /**
     * Class Constructor.
     */
    public GameStarting()
    {
        super.type = SETUPANSWERTYPE.GAME_START;
    }
}
