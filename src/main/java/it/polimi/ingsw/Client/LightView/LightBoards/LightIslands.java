package it.polimi.ingsw.Client.LightView.LightBoards;

import it.polimi.ingsw.Observer.Observable;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class LightIslands extends Observable
{
    private ArrayList<LightIsland> islands;
    private final Object setupLock = new Object();

    public LightIslands(@JsonProperty("islands") ArrayList<LightIsland> islands,
                        @JsonProperty("totalGroups") int totalGroups)
    {
        this.islands = islands;
    }

    public void updateIslands(LightIslands light)
    {
        synchronized (setupLock)
        {
            islands.clear();
            islands.addAll(light.getIslands());
            notifyLight(this);
        }
    }


    public ArrayList<LightIsland> getIslands() {
        return islands;
    }
    public Object getLock()
    {
        return setupLock;
    }
}
