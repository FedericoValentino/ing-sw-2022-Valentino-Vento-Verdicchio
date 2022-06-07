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
        int temp = 0;
        for(int i = 0; i < light.getIslands().size(); i++)
        {
            islands.get(i).updateIsland(light.getIslands().get(i));
            temp = i;
        }
        for(int i = 0; i < islands.size() - temp - 1; i++)
        {
            islands.remove(islands.size()-1);
        }
        System.out.println("Updated LightIslands");
        notifyLight(this);
    }


    public ArrayList<LightIsland> getIslands() {
        return islands;
    }
}
