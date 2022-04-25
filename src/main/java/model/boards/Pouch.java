

package model.boards;

import model.boards.token.Col;
import model.boards.token.Student;

import java.util.ArrayList;
import java.util.Collections;

public class Pouch
{
    private ArrayList<Student> content;
    private boolean setup;

    /*
    It creates two bags of students, and then puts them together; the gameBag contains
    the students to utilize during the game phase, while the setupBag is to be used during the
    Setup of the match. Initially the boolean setup will be set as true, and it will be
    updated through the relative method by the controller during the setup phase
     */
    public Pouch()
    {
        this.setup = true;
        this.content = new ArrayList<>();
        ArrayList<Student> setupBag = new ArrayList<>();
        ArrayList<Student> gameBag = new ArrayList<>();

        //Fills the gameBag with 120 students, 24 of each color, and then shuffles it
        for(int i = 0; i < 5; i++)
        {
            for(int j = 0; j < 24; j++)
            {
                gameBag.add(new Student(Col.values()[i]));
            }
        }
        Collections.shuffle(gameBag);

        //Fills the setupBag with 2 students of each color to use in the setup phase, then shuffles it
        for(int i = 0; i < 5; i++)
        {
            for(int j = 0; j < 2; j++)
            {
                setupBag.add(new Student(Col.values()[i]));
            }
        }
        Collections.shuffle(setupBag);

        //Content represents the main bag: gameBag at the head, setupBag at the tail
        this.content.addAll(gameBag);
        this.content.addAll(setupBag);
    }

    /*
    Removes a student from the pouch and returns it. It functions differently
    in setupPhase and in gamePhase
    */
   public Student extractStudent()
   {
       int index;

       /*If we are in the setup phase, the pouch will take students from the setupBag,
       so taking them from the tail of the content */
       if(getSetup())
        {
            index = 120;
         }

       //If we are not in the setup phase, the pouch will take students from the head
       else
         {
           index = 0;
         }
       Student student = content.get(index);
       content.remove(index);
       return student;
   }

   //Checks if pouch is empty, useful for certain end game shenanigans
   public boolean checkEmpty()
   {
       return content.isEmpty();
   }

   //Sets the setup value to False when we're out of the setup phase
   public void updateSetup(boolean b)
   {
       this.setup = b;
   }

   public boolean getSetup()
    {return this.setup;}

   public ArrayList<Student> getContent()
   {return content;}


}
