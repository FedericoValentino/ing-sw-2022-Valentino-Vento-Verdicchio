package it.polimi.ingsw.Client.Messages.ActionMessages;

/**
 * Message type referencing the end turn action
 */
public class EndTurn extends StandardActionMessage
{
    /**
     * Class constructor, this message is used to tell the server we want to end our actionPhase
     */
    public EndTurn() {
        super.type = ACTIONMESSAGETYPE.TURN_END;
    }
}
