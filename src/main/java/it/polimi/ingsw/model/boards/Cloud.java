package it.polimi.ingsw.model.boards;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.model.boards.token.Student;

import java.util.ArrayList;

public class Cloud extends Board
{
  private int cloudNumber;
  private ArrayList<Student> student;

  /** Class constructor 1*/
  public Cloud(int identifier)
  {
    cloudNumber = identifier;
    student= new ArrayList<>();
  }

  /** Class constructor 2 */
  public Cloud(@JsonProperty("students") ArrayList<Student> st,
               @JsonProperty("empty") boolean empty,
               @JsonProperty("CloudID") int id)
  {
      student= new ArrayList<>();
      for(int i=0;i<st.size();i++)
      {
        this.student.add(i, st.get(i));
      }
  }

  /** Adds the selected student to the cloud
   * @param s  student to place on the cloud
   */
  public void placeToken(Student s)
  {
    student.add(s);
  }

  /** remove the last Student of the cloud
   * @param s  the student to remove
   */
  public void removeToken(Student s)
  {
    if(!student.isEmpty())
    {
      student.remove(s);
    }
    else
    {
      // System.out.println("Error,the student vector is empty");
    }
  }


  public ArrayList<Student> EmptyCloud()
  {
    ArrayList<Student> current = new ArrayList<>();
    current.addAll(student);
    student.clear();
    return current;
  }

  /** Checks if there are no more students on the cloud
   * @return whether the cloud is empty or not
   */
  public boolean isEmpty(){
    //da verificare se non posso farlo con una funzione di libreria piuttosto che così
    return student.size() == 0;
  }

  /** Return the student at the specified position
   * @param pos  position of the student on the cloud
   * @return the required student
   */
  public Student getStudent(int pos) {
    try {
      return student.get(pos);
    } catch (IndexOutOfBoundsException e) {
      System.out.println("IN cloud index out of bound line 85");
    }
    return null;
  }

  public ArrayList<Student> getStudents()
  {
    return student;
  }

  public int getCloudID()
  {
    return cloudNumber;
  }
}
