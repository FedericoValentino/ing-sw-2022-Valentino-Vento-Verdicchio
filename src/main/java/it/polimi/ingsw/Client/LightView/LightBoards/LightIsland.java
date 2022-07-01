package it.polimi.ingsw.Client.LightView.LightBoards;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.model.boards.token.enumerations.ColTow;
import it.polimi.ingsw.model.boards.token.Student;

import java.util.ArrayList;

public class LightIsland {
    private int islandId;
    private boolean motherNature;
    private boolean Group;
    private ArrayList<Student> currentStudents;
    private ColTow ownership;
    private int towerNumber;
    private int[] teamInfluence;
    private boolean noEntry;


    /** Json class constructor */
    public LightIsland(@JsonProperty("islandId") int islandId,
                  @JsonProperty("motherNature")boolean motherNature,
                  @JsonProperty("group")boolean group,
                  @JsonProperty("currentStudents") ArrayList<Student> currentStudents,
                  @JsonProperty("ownership")ColTow ownership,
                  @JsonProperty("towerNumber")int towerNumber,
                  @JsonProperty("teamInfluence")int[] teamInfluence,
                  @JsonProperty("noEntry")boolean noEntry)
    {
        this.islandId = islandId;
        this.motherNature = motherNature;
        this.Group = group;
        this.currentStudents = currentStudents;
        this.ownership = ownership;
        this.towerNumber = towerNumber;
        this.teamInfluence = teamInfluence;
        this.noEntry = noEntry;
    }


    public int getIslandId() {
        return islandId;
    }

    public boolean isMotherNature() {
        return motherNature;
    }

    public boolean isGroup() {
        return Group;
    }

    public ArrayList<Student> getCurrentStudents() {
        return currentStudents;
    }

    public ColTow getOwnership() {
        return ownership;
    }

    public int getTowerNumber() {
        return towerNumber;
    }

    public int[] getTeamInfluence() {
        return teamInfluence;
    }

    public boolean isNoEntry() {
        return noEntry;
    }
}
