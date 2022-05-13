package model.boards.token;
//serve java.utils.random per randomizzare la posizione
import model.CurrentGameState;

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
        //da implementare lock su idPos solo quando lo aggiorno
        //lock(idPosition)

        if(maxIdIsland<0 || maxIdIsland>11)
        {
            //System.out.println("MOTHER NATURE: Error, invalid maxIdIsland" +
              //      " : (you've insert a maxIdIsland >11 or <0)");
        }
        else if(maxIdIsland<idPosition)
        {
            // System.out.println("MOTHER NATURE: Error: maxIdIsland "+maxIdIsland+" < idPosition"+
               //     idPosition);
        }
        else if(maxIdIsland>=0 && maxIdIsland<=11 && maxIdIsland>=idPosition)
        {
            int temp=0;
            temp=idPosition+value;
            if(temp>maxIdIsland)
            {
                temp-=(maxIdIsland+1);
            }
            idPosition=temp;
        }
    }

    public int getPosition() {
        return idPosition;
    }
}
