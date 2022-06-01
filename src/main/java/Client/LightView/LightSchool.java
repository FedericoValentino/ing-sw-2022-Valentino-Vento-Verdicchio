package Client.LightView;

import Observer.Observable;
import com.fasterxml.jackson.annotation.JsonProperty;
import model.boards.token.ColTow;
import model.boards.token.Student;

import java.util.ArrayList;

public class LightSchool extends Observable
{
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
        if(light.equals(this))
        {
            return;
        }
        else
        {
            this.entrance = light.getEntrance();
            this.diningRoom = light.getDiningRoom();
            this.professorTable = light.getProfessorTable();
            this.towerCount = light.getTowerCount();
            notifyLight(this);
        }
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
