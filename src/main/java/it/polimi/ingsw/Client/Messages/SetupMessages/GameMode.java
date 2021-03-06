package it.polimi.ingsw.Client.Messages.SetupMessages;

/**
 * Message used to communicate to the server number of players and whether they want to play in normal or expert mode
 */
public class GameMode extends StandardSetupMessage
{
    private int maxPlayers;
    private boolean expertGame;

    /**
     * Class Constructor, this message is used to tell the server the desired game mode
     */
    public GameMode()
    {
        super.type = SETUPMESSAGETYPE.MODE;
        this.maxPlayers = 99;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public boolean isExpertGame() {
        return expertGame;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public void setExpertGame(boolean expertGame) {
        this.expertGame = expertGame;
    }
}
