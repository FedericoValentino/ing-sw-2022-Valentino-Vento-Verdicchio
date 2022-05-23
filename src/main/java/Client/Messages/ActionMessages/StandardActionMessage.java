package Client.Messages.ActionMessages;

import Client.Messages.Message;

public abstract class StandardActionMessage implements Message
{
    public ACTIONMESSAGETYPE type;

    public ACTIONMESSAGETYPE getType() {
        return type;
    }
}
