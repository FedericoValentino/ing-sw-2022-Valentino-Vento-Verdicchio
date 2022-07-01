package it.polimi.ingsw.Client.Messages.ActionMessages;

import it.polimi.ingsw.Client.Messages.Message;

/**
 * Abstract class from which the action messages inherit Type attribute and getter method
 */
public abstract class StandardActionMessage implements Message
{
    public ACTIONMESSAGETYPE type;

    public ACTIONMESSAGETYPE getType() {
        return type;
    }
}
