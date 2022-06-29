package it.polimi.ingsw.Client.LightView.LightTeams;
//
import it.polimi.ingsw.Observer.Observable;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.model.boards.token.enumerations.Col;
import it.polimi.ingsw.model.boards.token.enumerations.ColTow;

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
        for(int i = 0; i < players.size(); i++)
        {
            players.get(i).updatePlayer(light.getPlayers().get(i));
        }
        this.controlledIslands = light.getControlledIslands();
        this.controlledProfessors = light.getControlledProfessors();
        notifyLight(this);
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
