package it.polimi.ingsw.Client.Messages.SetupMessages;

public class ReadyStatus extends StandardSetupMessage{
    /**
     * Class Constructor, this message is used to tell the server the player is ready to start the game
     */
    public ReadyStatus()
    {
        super.type = SETUPMESSAGETYPE.READINESS;
    }
}
