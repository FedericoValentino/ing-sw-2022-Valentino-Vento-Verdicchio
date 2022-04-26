package Client.SetupMessages;

public class GameMode implements StandardSetupMessage
{
    private int maxPlayers;
    private boolean expertGame;

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public boolean isExpertGame() {
        return expertGame;
    }
}
