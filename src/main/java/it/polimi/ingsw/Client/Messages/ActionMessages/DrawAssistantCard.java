package it.polimi.ingsw.Client.Messages.ActionMessages;

/**
 * Message type used to identify the player's assistant choice move
 */
public class DrawAssistantCard extends StandardActionMessage
{
    public int cardIndex;

    /**
     * Class Constructor, this message is used to draw an assistant card from our hand
     */
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
