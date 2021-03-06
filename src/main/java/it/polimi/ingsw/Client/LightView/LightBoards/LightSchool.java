package it.polimi.ingsw.Client.LightView.LightBoards;
//
import it.polimi.ingsw.Observer.Observable;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.model.boards.token.enumerations.ColTow;
import it.polimi.ingsw.model.boards.token.Student;

import java.util.ArrayList;

public class LightSchool extends Observable
{
    private String owner;
    private final ColTow color;
    private ArrayList<Student> entrance;
    private int[] diningRoom;
    private boolean[] professorTable;
    private int towerCount;

    public LightSchool(@JsonProperty("color")ColTow color,
                  @JsonProperty("entrance")ArrayList<Student> entrance,
                  @JsonProperty("diningRoom")int[] diningRoom,
                  @JsonProperty("roomCheckpoints")int[] roomCheckpoints,
                  @JsonProperty("professorTable")boolean[] professorTable,
                  @JsonProperty("towerCount")int towerCount)
    {
        this.color = color;
        this.entrance = entrance;
        this.diningRoom = diningRoom;
        this.professorTable = professorTable;
        this.towerCount = towerCount;
    }


    public void updateSchool(LightSchool light)
    {
        this.entrance = light.getEntrance();
        this.diningRoom = light.getDiningRoom();
        this.professorTable = light.getProfessorTable();
        this.towerCount = light.getTowerCount();
        notifyLight(this);
    }

    public void updateName(String name)
    {
        this.owner = name;
    }

    public String getOwner(){
        return owner;
    }

    public ColTow getColor() {
        return color;
    }

    public ArrayList<Student> getEntrance() {
        return entrance;
    }

    public int[] getDiningRoom() {
        return diningRoom;
    }

    public boolean[] getProfessorTable() {
        return professorTable;
    }

    public int getTowerCount() {
        return towerCount;
    }
}
