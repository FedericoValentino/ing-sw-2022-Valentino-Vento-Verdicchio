package it.polimi.ingsw.model.boards;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.model.boards.token.Student;

import java.util.ArrayList;

/**
 * Class modeling the cloud objects. It contains a cloud identifier and a list of students
 */
public class Cloud extends Board
{
  private int cloudNumber;
  private ArrayList<Student> students;

  /**
   * Class constructor 1
   */
  public Cloud(int identifier)
  {
    cloudNumber = identifier;
    students = new ArrayList<>();
  }


  /**
   * Adds the selected student to the cloud
   * @param student student to place on the cloud
   */
  public void placeToken(Student student)
  {
    students.add(student);
  }

  /** Remove the last Student of the cloud
   * @param student  the student to remove
   */
  public void removeToken(Student student)
  {
    if(!students.isEmpty())
      students.remove(student);
  }

  /**
   * Empties the cloud and returns a list containing the removed students
   * @return a list of the removed students
   */
  public ArrayList<Student> EmptyCloud()
  {
    ArrayList<Student> current = new ArrayList<>(students);
    students.clear();
    return current;
  }


  public ArrayList<Student> getStudents()
  {
    return students;
  }

  public int getCloudID()
  {
    return cloudNumber;
  }
}
