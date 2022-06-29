package it.polimi.ingsw.model.boards.token;


import java.util.Random;

/**
 * Mother nature has associated a position that corresponds to the islandId of the island containing mother nature
 */
public class MotherNature
{
    private int idPosition;


    /**
     * Class constructor. The initial position of Mother Nature is randomized
     */
    public MotherNature(){
        Random r= new Random();
        this.idPosition=r.nextInt(12);
    }

    /**
     * Moves Mother Nature clockwise given the movement value and the ID of the last island of the Islands structure
     * @param value  movement value
     * @param maxIdIsland  highest ID of all the islands
     */
    public void move(int value, int maxIdIsland)
    {
        int temp = idPosition + value;
        if(temp > maxIdIsland)
        {
            temp = temp - (maxIdIsland+1);
        }
        idPosition = temp;
    }

    /**
     * After an island merge, this method resets the mother nature position to an updated value, coherent with the
     * change if islandIds
     * @param position the "new" Mother Nature position
     */
    public void updatePositionAfterMerge(int position)
    {
        idPosition = position;
    }

    public int getPosition() {
        return idPosition;
    }
}
