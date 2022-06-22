package it.polimi.ingsw.Client.Messages.ActionMessages;

public class ChooseCloud extends StandardActionMessage
{
    private int cloudIndex;

    /**
     * Class constructor, this message is used to tell the server from which cloud we want to refill our entrance
     * @param cloudIndex
     */
    public ChooseCloud(int cloudIndex)
    {
        this.cloudIndex = cloudIndex;
        super.type = ACTIONMESSAGETYPE.ENTRANCE_REFILL;
    }

    public int getCloudIndex()
    {
        return cloudIndex;
    }
}
