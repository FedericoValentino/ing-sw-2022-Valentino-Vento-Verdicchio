package Client.Messages.SetupMessages;

public class Disconnect implements StandardSetupMessage
{
    private boolean disconnecting;

    public boolean isDisconnecting() {
        return disconnecting;
    }
}
