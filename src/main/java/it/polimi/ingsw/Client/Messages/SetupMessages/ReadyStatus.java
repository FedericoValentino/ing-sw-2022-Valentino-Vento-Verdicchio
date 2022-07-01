package it.polimi.ingsw.Client.Messages.SetupMessages;

/**
 * Message used to communicate to the server when a player becomes ready
 */
public class ReadyStatus extends StandardSetupMessage{
    /**
     * Class Constructor, this message is used to tell the server the player is ready to start the game
     */
    public ReadyStatus()
    {
        super.type = SETUPMESSAGETYPE.READINESS;
    }
}
