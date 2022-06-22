package it.polimi.ingsw.Server.Answers.SetupAnswers;

public class GameStarting extends StandardSetupAnswer
{
    /**
     * Class Constructor, this message is used to tell the client the game is starting
     */
    public GameStarting()
    {
        super.type = SETUPANSWERTYPE.GAME_START;
    }
}
