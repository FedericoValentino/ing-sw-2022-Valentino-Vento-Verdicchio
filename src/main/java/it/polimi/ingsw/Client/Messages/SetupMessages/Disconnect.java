package it.polimi.ingsw.Client.Messages.SetupMessages;

/**
 * Message type that concerns with the client disconnection
 */
public class Disconnect extends StandardSetupMessage
{
    public Disconnect()
    {
        super.type = SETUPMESSAGETYPE.DISCONNECTION;
    }
}
