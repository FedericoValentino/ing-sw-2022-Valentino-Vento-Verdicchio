package it.polimi.ingsw.Client.Messages.ActionMessages;

public class MoveStudent extends StandardActionMessage
{
    private int entrancePos;
    private boolean toIsland;
    private int islandId;

    public MoveStudent(int entrancePos, boolean toIsland, int islandId)
    {
        this.entrancePos = entrancePos;
        this.toIsland = toIsland;
        this.islandId = islandId;
        super.type = ACTIONMESSAGETYPE.STUD_MOVE;
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
