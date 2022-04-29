package Client.Messages;

import Client.Messages.ActionMessages.StandardActionMessage;
import Client.Messages.SetupMessages.StandardSetupMessage;

import java.io.Serializable;

public class SerializedMessage implements Serializable
{
    private StandardSetupMessage command;
    private StandardActionMessage action;

    public SerializedMessage(StandardActionMessage m)
    {
        this.command = null;
        this.action = m;
    }

    public SerializedMessage(StandardSetupMessage m)
    {
        this.command = m;
        this.action = null;
    }

    public StandardSetupMessage getCommand() {
        return command;
    }

    public StandardActionMessage getAction() {
        return action;
    }
}
