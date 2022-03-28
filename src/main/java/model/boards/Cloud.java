package model.boards;
import model.boards.token.Student;

public class Cloud extends Board
{
  public Student[] student;

  public Cloud(Student[] st)
  {
    for(int i=0;i<st.length;i++)
    {
      this.student[i]=st[i];
    }
  }

  public void placeToken(Student s, int pos){}
  public void removeToken(Student s,int pos) {  }
  public boolean isEmpty(){
    //da verificare se non posso farlo con una funzione di libreria piuttosto che così
    if(student.length==0)
      return true;
    else
      return false;
  }

  /*public Student getStudent(int pos) {
    //dovrebbe funzionare ma non so se posso evitarlo usando qualche throw exception
    if (pos < 0) {
      System.out.println("parametro minore di 0");
    } else {
      try {
        return student[pos];
      } catch (NullPointerException e) {
        System.out.println("Null pointer exception in gestione pos studente");
      }
    }
    return ;//da capire cosa ritornare in caso di errore
  }*/
}
