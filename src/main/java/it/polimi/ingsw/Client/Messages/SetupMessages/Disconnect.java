package it.polimi.ingsw.Client.Messages.SetupMessages;

public class Disconnect extends StandardSetupMessage
{
    private boolean disconnecting;

    public Disconnect()
    {
        super.type = SETUPMESSAGETYPE.DISCONNECTION;
    }

    public boolean isDisconnecting() {
        return disconnecting;
    }
}
