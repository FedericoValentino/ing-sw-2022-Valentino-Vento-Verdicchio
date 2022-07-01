package it.polimi.ingsw.Server.Answers.SetupAnswers;

/**
 * Ping message, useful to the client to understand if the server is alive
 */
public class Ping extends StandardSetupAnswer{

    public Ping()
    {
        super.type = SETUPANSWERTYPE.PING;
    }
}
