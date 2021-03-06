package it.polimi.ingsw.Client.LightView.LightBoards;

import it.polimi.ingsw.Observer.Observable;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.model.boards.token.Student;

import java.util.ArrayList;

public class LightCloud extends Observable
{
    public int cloudID = 0;
    private ArrayList<Student> students;

    public LightCloud(@JsonProperty("students") ArrayList<Student> st,
                      @JsonProperty("empty") boolean empty,
                      @JsonProperty("CloudID") int id)
    {
        cloudID = id;
        students= new ArrayList<>();
        for(int i=0;i<st.size();i++)
        {
            this.students.add(i, st.get(i));
        }
    }

    public void updateCloud(LightCloud light)
    {
        this.students = light.getStudents();
        notifyLight(this);
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public int getCloudID()
    {
        return cloudID;
    }

}
