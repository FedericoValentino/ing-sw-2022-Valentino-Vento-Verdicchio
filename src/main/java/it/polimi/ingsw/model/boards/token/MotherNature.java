package it.polimi.ingsw.model.boards.token;
//serve java.utils.random per randomizzare la posizione
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.model.CurrentGameState;

import java.util.Random;

public class MotherNature
{
    private int idPosition;
    private CurrentGameState game;


    /** Class constructor. The initial position of Mother Nature is randomized */
    public MotherNature(CurrentGameState game){
        //this.id_position
        this.game = game;
        Random r= new Random();
        this.idPosition=r.nextInt(12);
    }

    /** Moves Mother Nature clockwise given the movement value and the ID of the last island of the Islands structure
     * @param value  movement value
     * @param maxIdIsland  highest ID of all the islands
     */
    public void move(int value,int maxIdIsland)
    {
        int temp = idPosition + value;
        if(temp > maxIdIsland)
        {
            temp = temp - (maxIdIsland+1);
        }
        idPosition = temp;
    }

    public void updatePositionAfterMerge(int position)
    {
        idPosition = position;
    }

    public int getPosition() {
        return idPosition;
    }
}
