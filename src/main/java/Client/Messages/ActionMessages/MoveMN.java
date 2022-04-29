package Client.Messages.ActionMessages;

public class MoveMN implements StandardActionMessage
{
    private int amount;

    public MoveMN(int amount)
    {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }
}
