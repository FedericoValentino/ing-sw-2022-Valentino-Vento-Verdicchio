package model.boards;

import model.boards.token.Student;

public class Pouch
{

    private Student[] content;

    public Pouch(Student[] content)
    {
        for(int i=0;i<content.length;i++)
            this.content[i]=content[i];
    }

    /*
    public Student extractStudent()
    {
    }
    public boolean checkEmpty();
    {
    }
     */

}
