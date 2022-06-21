package it.polimi.ingsw.model.boards;

import static org.junit.Assert.*;

import it.polimi.ingsw.model.CurrentGameState;
import org.junit.Test;
import it.polimi.ingsw.model.boards.token.enumerations.Col;
import it.polimi.ingsw.model.boards.token.enumerations.ColTow;
import it.polimi.ingsw.model.boards.token.Student;

import java.util.ArrayList;

public class SchoolTest
{
    CurrentGameState game = new CurrentGameState(2, true);
    School s=new School(ColTow.BLACK,6, game);
    School s2=new School(ColTow.GREY,6, game);
    Student ss1=new Student(Col.YELLOW);
    Student ss2=new Student(Col.GREEN);


    @Test
    public void testPlaceToken() {
        s.placeToken(ss1);
        assertEquals(s.getEntrance().get(0),ss1);
    }

    @Test
    public void testPlaceToken2()
    {
        School st = new School(ColTow.BLACK,6, game);
        st.getEntrance().removeAll(st.getEntrance());
        ArrayList<Student> ss = new ArrayList<>();
        ss.add(ss1);
        ss.add(ss2);
        st.placeToken(ss);
        assertEquals(st.getEntrance().get(0), ss1);
        assertEquals(st.getEntrance().get(1), ss2);
    }


    @Test
    public void testExtractStudent() {
        testPlaceToken();
        assertEquals(s.extractStudent(0),ss1);
        //I use it to test the catch branch
        s.extractStudent(2);
    }

    @Test
    public void testPlaceInDiningRoom() {
        testPlaceToken();
        s.placeInDiningRoom(Col.YELLOW);
        assertEquals(s.getDiningRoom()[2],1);
        s.placeToken(ss2);
        s.placeInDiningRoom(Col.GREEN);
        assertEquals(s.getDiningRoom()[0],1);
    }

    @Test
    public void testUpdateCheckpoint() {
        int i=0;
        s.updateCheckpoint(i, true);
        //udating the position
        assertEquals(s.getRoomCheckpoints()[i],5);
    }

    @Test
    public void testUpdateTowerCount() {
        int tt=s.getTowerCount();
        s.updateTowerCount(2);
        assertEquals(s.getTowerCount(),tt+2);
    }


    @Test
    public void testGetProfessorTable() {
        //da capire che valori hanno, perchè adesso sono a null
        s.updateProfessorsTable(0,true);
        assertEquals(s.getProfessorTable()[0], true);
    }

    @Test
    public void testGetColor() {
        assertEquals(s.getColor(),ColTow.BLACK);
    }
}