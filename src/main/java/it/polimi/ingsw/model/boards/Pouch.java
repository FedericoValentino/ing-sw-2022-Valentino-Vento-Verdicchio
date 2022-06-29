

package it.polimi.ingsw.model.boards;

import it.polimi.ingsw.model.boards.token.enumerations.Col;
import it.polimi.ingsw.model.boards.token.Student;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Pouch contains all the students in the game (at least at the beginning of the setup phase). It attributes and methods
 * to vary its behaviour based upon the game phase and, of course, methods to manipulate the list of student it instantiates
 */
public class Pouch
{
    private ArrayList<Student> content;
    private boolean setup;

    /**
     * Class constructor. It creates two bags of students, and then puts them together; the Game Bag contains
     * the students to utilize during the game phase, while the setup Bag is to be used during the setup of the match.
     * Initially the boolean setup will be set as true, and it will be updated through the relative method by the
     * controller during the setup phase.
     * The setup bag will need to have exactly two students of each color in order to prepare the islands, according to the rules.
     */
    public Pouch()
    {
        this.setup = true;
        this.content = new ArrayList<>();
        ArrayList<Student> setupBag = new ArrayList<>();
        ArrayList<Student> gameBag = new ArrayList<>();

        for(int i = 0; i < 5; i++)
        {
            for(int j = 0; j < 24; j++)
            {
                gameBag.add(new Student(Col.values()[i]));
            }
        }
        Collections.shuffle(gameBag);

        for(int i = 0; i < 5; i++)
        {
            for(int j = 0; j < 2; j++)
            {
                setupBag.add(new Student(Col.values()[i]));
            }
        }
        Collections.shuffle(setupBag);

        this.content.addAll(gameBag);
        this.content.addAll(setupBag);
    }


    /**
     * Removes a student from the pouch and returns it. It functions differently
     * in setupPhase and in gamePhase:
     * if we are in setup, it extracts from the tail, so from the end of the setupBag
     * if we are not in setup, the setup bag has been consumed, and the extractions will be done from the head
     * @return the student extracted from the pouch
     */
   public Student extractStudent()
   {
       int index;

       if(getSetup())
        {
            index = content.size() - 10;
         }

       else
         {
           index = 0;
         }
       Student student = content.get(index);
       content.remove(index);
       return student;
   }

    /**
     * Refills the pouch with the given list of students
     * @param students a list of students
     */
   public void refillBag(ArrayList<Student> students)
   {
       content.addAll(students);
       Collections.shuffle(content);
   }

    /**
     * Sets the setup value to False when we're out of the setup phase
     * @param bool  the boolean that tells us whether to set the setup field to true or false
     */
   public void updateSetup(boolean bool)
   {
       this.setup = bool;
   }

   public boolean getSetup()
    {return this.setup;}

   public ArrayList<Student> getContent()
   {return content;}


}
