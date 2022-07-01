package it.polimi.ingsw.Client.Messages.ActionMessages;

/**
 * Message type concerning the action of moving mother nature
 */
public class MoveMN extends StandardActionMessage
{
    private int amount;

    /**
     * Class constructor, this message is used to tell the server how much we want to move MN
     */
    public MoveMN(int amount)
    {
        this.amount = amount;
        super.type = ACTIONMESSAGETYPE.MN_MOVE;
    }

    public int getAmount() {
        return amount;
    }
}
