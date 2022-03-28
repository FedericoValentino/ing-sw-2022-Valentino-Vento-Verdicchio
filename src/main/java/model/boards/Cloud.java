package model.boards;
import model.boards.token.Student;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

public class Cloud extends Board
{
  private ArrayList<Student> student;

  public Cloud(){}
  public Cloud(ArrayList<Student> st)
  {
    for(int i=0;i<st.size();i++)
    {
      this.student.set(i, st.get(i));
    }
  }

  //add Student to the cloud queue
  public void placeToken(Student s){
    student.add(s);
  }

  //remove the last Student insert
  public void removeToken(Student s)
  {
    if(!student.isEmpty())
    {
      student.remove(s);
    }
    else
    {
      System.out.println("Error,the student vector is empty");
    }
  }

  public boolean isEmpty(){
    //da verificare se non posso farlo con una funzione di libreria piuttosto che così
    if(student.size()==0)
      return true;
    else
      return false;
  }

  public Student getStudent(int pos) {
    //dovrebbe funzionare ma non so se posso evitarlo usando qualche throw exception
    try {
      return student.get(pos);
    } catch (NullPointerException e) {
      System.out.println("Null pointer exception perché stai restituendo un intero negativo");
    }
    return null;
  }
}
