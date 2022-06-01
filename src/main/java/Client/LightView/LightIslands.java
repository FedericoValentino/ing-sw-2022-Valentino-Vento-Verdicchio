package Client.LightView;

import Observer.Observable;
import com.fasterxml.jackson.annotation.JsonProperty;
import model.boards.Island;

import java.util.ArrayList;

public class LightIslands extends Observable
{
    private ArrayList<Island> islands;

    public LightIslands(@JsonProperty("islands") ArrayList<Island> islands,
                        @JsonProperty("totalGroups") int totalGroups)
    {
        this.islands = islands;
    }

    public void updateIslands(LightIslands light)
    {
        if(light.equals(this))
        {
            return;
        }
        else
        {
            this.islands = light.getIslands();
            notifyLight(this);
        }
    }


    public ArrayList<Island> getIslands() {
        return islands;
    }
}
