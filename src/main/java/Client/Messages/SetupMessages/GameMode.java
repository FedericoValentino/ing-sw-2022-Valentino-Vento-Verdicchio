package Client.Messages.SetupMessages;

public class GameMode extends StandardSetupMessage
{
    private int maxPlayers;
    private boolean expertGame;

    public GameMode()
    {
        super.type = SETUPMESSAGETYPE.MODE;
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
