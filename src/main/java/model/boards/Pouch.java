

package model.boards;

import model.boards.token.Col;
import model.boards.token.Student;

import java.util.ArrayList;
import java.util.Collections;

public class Pouch
{
    private ArrayList<Student> content;
    private boolean setup;

    public Pouch()                                                      //Pouch inizialmente riempie 120 studenti, 24 di ogni colore, e li mischia
    {
        this.setup = true;                                              //Pouch inizializza il bool setup a true perchè viene creato solo una volta all'inizio, proprio nel setup
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
        for(int i = 0; i < 5; i++)                                         //Pouch adesso inserisce ordinatamente due studenti di ogni colore (i 10 rimasti) in coda
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

   public Student extractStudent()                                              //La funzione si splitta in 2 parti
   {
       int index;
       if(getSetup())                                                      //se setup == true siamo in fase di setup: l'indice viene scelto
        {                                                                   //randomicamente fra il 120esimo elemento e l'ultimo, per disporre i primi
            int max_index = content.size() - 1;                             //studenti sulle isole
            index = (int) (Math.random() * (max_index - 120) + 120);
         }
       else                                                                 //se setup == false siamo in fase normale, dunque si va a togliere elementi
         {                                                                  //dalla testa della collezione (index == 0), già mischiati nel costruttore
           index = 0;
         }
       Student student = content.get(index);
       content.remove(index);
       return student;
   }

   public boolean checkEmpty()                                  //controlla se il pouch è vuoto, anche qui utile per controlli fine game
   {
       return content.isEmpty();
   }

   public void updateSetup(boolean b)
   {
       this.setup = b;
   }

   public boolean getSetup()
    {return this.setup;}

   public ArrayList<Student> getContent()
   {return content;}


}
