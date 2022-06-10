package it.polimi.ingsw.Client.Messages.SetupMessages;

import it.polimi.ingsw.Client.Messages.Message;


public abstract class StandardSetupMessage implements Message
{
    public SETUPMESSAGETYPE type;

    public SETUPMESSAGETYPE getType(){return type;}
}
