package Client.Messages.SetupMessages;

public class ReadyStatus extends StandardSetupMessage{
    public ReadyStatus()
    {
        super.type = SETUPMESSAGETYPE.READINESS;
    }
}
