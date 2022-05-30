package Client.LightView;

import com.fasterxml.jackson.annotation.JsonProperty;
import model.boards.token.Student;

import java.util.ArrayList;

public class LightCloud
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

    public ArrayList<Student> getStudents() {
        return students;
    }
}
