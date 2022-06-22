package it.polimi.ingsw.Client.Messages.ActionMessages;

public class MoveStudent extends StandardActionMessage
{
    private int entrancePos;
    private boolean toIsland;
    private int islandId;

    /**
     * Class Constructor, this message is used to tell the server we want to move a student to the dining room or to an island
     * @param entrancePos the student position in the entrance
     * @param toIsland if we want to move it to an island or not
     * @param islandId the island position
     */
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
