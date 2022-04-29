package Client.Messages.ActionMessages;

public class DrawFromPouch implements StandardActionMessage
{
    private int cloudIndex;

    public DrawFromPouch(int cloudIndex)
    {
        this.cloudIndex = cloudIndex;
    }

    public int getCloudIndex()
    {
        return cloudIndex;
    }
}
