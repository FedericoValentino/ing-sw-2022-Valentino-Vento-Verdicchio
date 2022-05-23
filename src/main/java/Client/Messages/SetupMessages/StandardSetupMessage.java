package Client.Messages.SetupMessages;

import Client.Messages.Message;


public abstract class StandardSetupMessage implements Message
{
    public SETUPMESSAGETYPE type;

    public SETUPMESSAGETYPE getType(){return type;}
}
