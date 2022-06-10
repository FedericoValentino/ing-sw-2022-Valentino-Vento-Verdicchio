package it.polimi.ingsw.Client.Messages.ActionMessages;

public class DrawAssistantCard extends StandardActionMessage
{
    public int cardIndex;

    public DrawAssistantCard(int cardIndex)
    {
        this.cardIndex = cardIndex;
        super.type = ACTIONMESSAGETYPE.DRAW_CHOICE;
    }

    public int getCardIndex()
    {
        return cardIndex;
    }
}
