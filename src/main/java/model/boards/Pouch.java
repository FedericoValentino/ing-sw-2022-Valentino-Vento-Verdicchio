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
        this.content = new ArrayList<Student>();
        for (int i=0; i<24; i++)
        {this.content.add(new Student(Col.BLUE));}
        for (int i=0; i<24; i++)
        {this.content.add(new Student(Col.RED));}
        for (int i=0; i<24; i++)
        {this.content.add(new Student(Col.YELLOW));}
        for (int i=0; i<24; i++)
        {this.content.add(new Student(Col.GREEN));}
        for (int i=0; i<24; i++)
        {this.content.add(new Student(Col.PINK));}
        Collections.shuffle((this.content));
        for (int i=0; i<2; i++)                                        //Pouch adesso inserisce ordinatamente due studenti di ogni colore (i 10 rimasti) in coda
        {this.content.add(new Student(Col.BLUE));}            //Questo per la fase di setup
        for (int i=0; i<2; i++)
        {this.content.add(new Student(Col.RED));}
        for (int i=0; i<2; i++)
        {this.content.add(new Student(Col.YELLOW));}
        for (int i=0; i<2; i++)
        {this.content.add(new Student(Col.GREEN));}
        for (int i=0; i<2; i++)
        {this.content.add(new Student(Col.PINK));}

    }

   public Student extractStudent()                                              //La funzione si splitta in 2 parti
   {
       int index;
       if(getSetup())                                                       //se setup == true siamo in fase di setup: l'indice viene scelto
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


   public boolean getSetup()
    {return this.setup;}

   public ArrayList<Student> getContent()
   {return content;}


}
