package Client.Messages.ActionMessages;

public class ChooseCloud implements StandardActionMessage
{
    private int cloudIndex;

    public ChooseCloud(int cloudIndex)
    {
        this.cloudIndex = cloudIndex;
    }

    public int getCloudIndex()
    {
        return cloudIndex;
    }
}
