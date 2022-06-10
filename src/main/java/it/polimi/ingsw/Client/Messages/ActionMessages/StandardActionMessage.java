package it.polimi.ingsw.Client.Messages.ActionMessages;

import it.polimi.ingsw.Client.Messages.Message;

public abstract class StandardActionMessage implements Message
{
    public ACTIONMESSAGETYPE type;

    public ACTIONMESSAGETYPE getType() {
        return type;
    }
}
