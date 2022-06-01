package Client.LightView;

import Observer.Observable;
import com.fasterxml.jackson.annotation.JsonProperty;
import model.Player;
import model.boards.token.Col;
import model.boards.token.ColTow;

import java.util.ArrayList;

public class LightTeam extends Observable
{
    private final ColTow color;
    private ArrayList<LightPlayer> players;
    private int controlledIslands;
    private ArrayList<Col> controlledProfessors;

    /** Json class constructor */
    public LightTeam(@JsonProperty("color") ColTow color,
                @JsonProperty("players") ArrayList<LightPlayer> players,
                @JsonProperty("controlledIslands") int controlledIslands,
                @JsonProperty("controlledProfessors") ArrayList<Col> controlledProfessors)
    {
        this.color = color;
        this.players = players;
        this.controlledIslands = controlledIslands;
        this.controlledProfessors = controlledProfessors;
    }

    public void updateTeam(LightTeam light)
    {
        if(light.equals(this))
        {
            return;
        }
        else
        {
            this.players = light.getPlayers();
            this.controlledIslands = light.getControlledIslands();
            this.controlledProfessors = light.getControlledProfessors();
            notifyLight(this);
        }
    }

    public ColTow getColor() {
        return color;
    }

    public ArrayList<LightPlayer> getPlayers() {
        return players;
    }

    public int getControlledIslands() {
        return controlledIslands;
    }

    public ArrayList<Col> getControlledProfessors() {
        return controlledProfessors;
    }
}
