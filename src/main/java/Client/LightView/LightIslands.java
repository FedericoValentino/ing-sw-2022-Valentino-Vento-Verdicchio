package Client.LightView;

import Observer.Observable;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class LightIslands extends Observable
{
    private ArrayList<LightIsland> islands;

    public LightIslands(@JsonProperty("islands") ArrayList<LightIsland> islands,
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
            System.out.println("Updated LightIslands");
            notifyLight(this);
        }
    }


    public ArrayList<LightIsland> getIslands() {
        return islands;
    }
}
