package Client.LightView;

import com.fasterxml.jackson.annotation.JsonProperty;
import model.boards.Island;

import java.util.ArrayList;

public class LightIslands
{
    private ArrayList<Island> islands;

    public LightIslands(@JsonProperty("islands") ArrayList<Island> islands,
                        @JsonProperty("totalGroups") int totalGroups)
    {
        this.islands = islands;
    }

    public ArrayList<Island> getIslands() {
        return islands;
    }
}
