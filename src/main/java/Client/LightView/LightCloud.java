package Client.LightView;

import Observer.Observable;
import com.fasterxml.jackson.annotation.JsonProperty;
import model.boards.token.Student;

import java.util.ArrayList;

public class LightCloud extends Observable
{
    private ArrayList<Student> students;

    public LightCloud(@JsonProperty("students") ArrayList<Student> st,
                      @JsonProperty("empty") boolean empty)
    {
        students= new ArrayList<>();
        for(int i=0;i<st.size();i++)
        {
            this.students.add(i, st.get(i));
        }
    }

    public void updateCloud(LightCloud light)
    {
        if(light.equals(this))
        {
            return;
        }
        else
        {
            this.students = light.getStudents();
            notifyLight(this);
        }
    }

    public ArrayList<Student> getStudents() {
        return students;
    }
}
