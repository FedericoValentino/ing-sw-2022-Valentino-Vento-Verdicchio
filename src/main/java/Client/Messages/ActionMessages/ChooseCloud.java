package Client.Messages.ActionMessages;

public class ChooseCloud extends StandardActionMessage
{
    private int cloudIndex;

    public ChooseCloud(int cloudIndex)
    {
        this.cloudIndex = cloudIndex;
        super.type = ACTIONMESSAGETYPE.CLOUD_CHOICE;
    }

    public int getCloudIndex()
    {
        return cloudIndex;
    }
}
