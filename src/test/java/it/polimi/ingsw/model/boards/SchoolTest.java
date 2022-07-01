package it.polimi.ingsw.model.boards;

import static org.junit.Assert.*;

import org.junit.Test;
import it.polimi.ingsw.model.boards.token.enumerations.Col;
import it.polimi.ingsw.model.boards.token.enumerations.ColTow;
import it.polimi.ingsw.model.boards.token.Student;

import java.util.ArrayList;

public class SchoolTest
{
    School schoolBlack =new School(ColTow.BLACK,6);
    Student studYellow =new Student(Col.YELLOW);
    Student studGreen =new Student(Col.GREEN);


    @Test
    public void testPlaceToken() {
        schoolBlack.placeToken(studYellow);
        assertEquals(schoolBlack.getEntrance().get(0), studYellow);
    }

    /**
     * This method tests the adds of 2 students to the entrance
     */
    @Test
    public void testPlaceToken2()
    {
        School st = new School(ColTow.BLACK,6);
        st.getEntrance().removeAll(st.getEntrance());
        ArrayList<Student> studentArrayList = new ArrayList<>();
        studentArrayList.add(studYellow);
        studentArrayList.add(studGreen);
        st.placeToken(studentArrayList);
        assertEquals(st.getEntrance().get(0), studYellow);
        assertEquals(st.getEntrance().get(1), studGreen);
    }


    /**
     * This method test also the catch branch
     */
    @Test
    public void testExtractStudent() {
        testPlaceToken();
        assertEquals(schoolBlack.extractStudent(0), studYellow);

        assertNull(schoolBlack.extractStudent(2));
    }

    /**
     * This method is testing that in the yellow column there is only 1 students (because 1 is the entrance and
     * not in dining). Then it also tests that  in the green column there is 1 student
     */
    @Test
    public void testPlaceInDiningRoom() {
        testPlaceToken();

        schoolBlack.placeInDiningRoom(Col.YELLOW);
        assertEquals(schoolBlack.getDiningRoom()[2],1);

        schoolBlack.placeToken(studGreen);
        schoolBlack.placeInDiningRoom(Col.GREEN);
        assertEquals(schoolBlack.getDiningRoom()[0],1);
    }

    @Test
    public void testUpdateCheckpoint() {
        int i=0;
        schoolBlack.updateCheckpoint(i, true);
        assertEquals(schoolBlack.getRoomCheckpoints()[i],5);
    }

    @Test
    public void testUpdateTowerCount() {
        int tt= schoolBlack.getTowerCount();
        schoolBlack.updateTowerCount(2);
        assertEquals(schoolBlack.getTowerCount(),tt+2);
    }


    @Test
    public void testGetProfessorTable() {
        schoolBlack.updateProfessorsTable(0,true);
        assertEquals(schoolBlack.getProfessorTable()[0], true);
        schoolBlack.updateProfessorsTable(0, false);
        assertEquals(schoolBlack.getProfessorTable()[0], false);
    }

    @Test
    public void testGetColor() {
        assertEquals(schoolBlack.getColor(),ColTow.BLACK);
    }
}