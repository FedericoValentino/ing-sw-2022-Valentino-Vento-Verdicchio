package Client.Messages.ActionMessages;

public class MoveStudent implements StandardActionMessage
{
    private int entrancePos;
    private boolean toIsland;
    private int islandId;

    public MoveStudent(int entrancePos, boolean toIsland, int islandId)
    {
        this.entrancePos = entrancePos;
        this.toIsland = toIsland;
        this.islandId = islandId;
    }

    public int getEntrancePos()
    {
        return entrancePos;
    }

    public boolean isToIsland()
    {
        return toIsland;
    }

    public int getIslandId()
    {
        return islandId;
    }
}
