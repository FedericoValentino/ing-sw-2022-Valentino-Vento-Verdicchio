package model.boards;

import static org.junit.Assert.*;
import org.junit.Test;
import model.boards.token.Col;
import model.boards.token.ColTow;
import model.boards.token.Student;
import org.junit.Test;

public class SchoolTest{
    School s=new School(ColTow.BLACK,6);
    School s2=new School(ColTow.GREY,6);
    Student ss1=new Student(Col.YELLOW);
    Student ss2=new Student(Col.GREEN);


    @Test
    public void testPlaceToken() {
        s.placeToken(ss1);
        assertEquals(s.getEntrance().get(0),ss1);
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
        s.updateCheckpoint(i);
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