package Client.LightView;

import com.fasterxml.jackson.annotation.JsonProperty;
import model.boards.token.Student;

import java.util.ArrayList;

public class LightCloud
{
    private ArrayList<Student> student;

    public LightCloud(@JsonProperty("students") ArrayList<Student> st,
                      @JsonProperty("empty") boolean empty)
    {
        student= new ArrayList<>();
        for(int i=0;i<st.size();i++)
        {
            this.student.add(i, st.get(i));
        }
    }

    public ArrayList<Student> getStudent() {
        return student;
    }
}
