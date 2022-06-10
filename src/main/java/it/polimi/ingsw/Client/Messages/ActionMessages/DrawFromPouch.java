package it.polimi.ingsw.Client.Messages.ActionMessages;

public class DrawFromPouch extends StandardActionMessage
{
    private int cloudIndex;

    public DrawFromPouch(int cloudIndex)
    {
        this.cloudIndex = cloudIndex;
        super.type = ACTIONMESSAGETYPE.CLOUD_CHOICE;
    }

    public int getCloudIndex()
    {
        return cloudIndex;
    }
}
