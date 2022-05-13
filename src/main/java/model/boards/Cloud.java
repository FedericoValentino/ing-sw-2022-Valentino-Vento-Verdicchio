package model.boards;
import model.CurrentGameState;
import model.boards.token.Student;

import java.util.ArrayList;

public class Cloud extends Board
{
  private ArrayList<Student> student;
  private CurrentGameState game;

  /** Class constructor 1*/
  public Cloud(CurrentGameState game)
  {
    student= new ArrayList<>();
    this.game = game;
  }

  /** Class constructor 2 */
  public Cloud(ArrayList<Student> st)
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
    game.notify(game.modelToJson());
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
    game.notify(game.modelToJson());
  }


  public ArrayList<Student> EmptyCloud()
  {
    ArrayList<Student> current = new ArrayList<>();
    current.addAll(student);
    student.removeAll(student);
    game.notify(game.modelToJson());
    return current;
  }

  /** Checks if there are no more students on the cloud
   * @return whether the cloud is empty or not
   */
  public boolean isEmpty(){
    //da verificare se non posso farlo con una funzione di libreria piuttosto che così
    if(student.size()==0)
      return true;
    else
      return false;
  }

  /** Return the student at the specified position
   * @param pos  position of the student on the cloud
   * @return the required student
   */
  public Student getStudent(int pos) {
    //dovrebbe funzionare ma non so se posso evitarlo usando qualche throw exception
    try {
      return student.get(pos);
    } catch (IndexOutOfBoundsException e) {
      //System.out.println("CLOUD : Null pointer exception perché stai restituendo un intero negativo");
    }
    return null;
  }

  public ArrayList<Student> getStudents()
  {
    return student;
  }
}
