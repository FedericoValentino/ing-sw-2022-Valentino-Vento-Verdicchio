package Client.Messages.ActionMessages;

public class MoveMN extends StandardActionMessage
{
    private int amount;

    public MoveMN(int amount)
    {
        this.amount = amount;
        super.type = ACTIONMESSAGETYPE.MN_MOVE;
    }

    public int getAmount() {
        return amount;
    }
}
