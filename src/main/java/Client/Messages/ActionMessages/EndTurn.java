package Client.Messages.ActionMessages;

public class EndTurn extends StandardActionMessage
{
    public EndTurn() {
        super.type = ACTIONMESSAGETYPE.TURN_END;
    }
}
