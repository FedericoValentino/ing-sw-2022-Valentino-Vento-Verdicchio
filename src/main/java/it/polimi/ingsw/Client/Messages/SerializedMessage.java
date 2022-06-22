package it.polimi.ingsw.Client.Messages;

import it.polimi.ingsw.Client.Messages.ActionMessages.StandardActionMessage;
import it.polimi.ingsw.Client.Messages.SetupMessages.StandardSetupMessage;

import java.io.Serializable;

public class SerializedMessage implements Serializable
{
    private static final long serialVersionUID = 7526472295622776147L;
    private StandardSetupMessage command;
    private StandardActionMessage action;

    /**
     * Constructor for a game message
     * @param m
     */
    public SerializedMessage(StandardActionMessage m)
    {
        this.command = null;
        this.action = m;
    }

    /**
     * Constructor for a setup message
     * @param m
     */
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
