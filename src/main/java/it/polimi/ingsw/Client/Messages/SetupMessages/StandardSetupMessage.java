package it.polimi.ingsw.Client.Messages.SetupMessages;

import it.polimi.ingsw.Client.Messages.Message;

/**
 * Abstract class from which all setup messages inherit Type and relative getter
 */
public abstract class StandardSetupMessage implements Message
{
    public SETUPMESSAGETYPE type;

    public SETUPMESSAGETYPE getType(){return type;}
}
