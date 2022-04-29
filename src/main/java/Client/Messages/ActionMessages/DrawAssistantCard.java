package Client.Messages.ActionMessages;

public class DrawAssistantCard implements StandardActionMessage
{
    public int cardIndex;

    public DrawAssistantCard(int cardIndex)
    {
        this.cardIndex = cardIndex;
    }

    public int getCardIndex()
    {
        return cardIndex;
    }
}
