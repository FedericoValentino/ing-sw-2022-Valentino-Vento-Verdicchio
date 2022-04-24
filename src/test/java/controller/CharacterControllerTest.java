package controller;

import controller.MainController;
import model.CurrentGameState;
import model.boards.token.Col;
import model.boards.token.ColTow;
import model.boards.token.MotherNature;
import model.boards.token.Student;
import model.cards.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class CharacterControllerTest {

    MainController controllerTest = new MainController(2, true);

    public void setupTest() {
        controllerTest.AddPlayer(0, "jack", 8, "Franco");   //grey
        controllerTest.AddPlayer(1, "fede", 8, "Giulio");   //white
        controllerTest.Setup();
    }


    @Test
    public void testPickCard() {
        setupTest();
        controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).updateCoins(5);
        controllerTest.getGame().getCurrentTeams().get(1).getPlayers().get(0).updateCoins(4);
        assertEquals(6, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).getCoinAmount());
        assertEquals(5, controllerTest.getGame().getCurrentTeams().get(1).getPlayers().get(0).getCoinAmount());
        CharacterCard card1 = controllerTest.getGame().getCurrentCharacterDeck().getCard(0);
        CharacterCard card2 = controllerTest.getGame().getCurrentCharacterDeck().getCard(1);
        CharacterCard card3 = controllerTest.getGame().getCurrentCharacterDeck().getCard(2);
        int baseInitialCost = card1.getBaseCost();
        System.out.println(baseInitialCost);

        //pick card

        controllerTest.getCharacterController().pickCard(controllerTest.getGame(), 0, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0));

        //assertions

        assertEquals(2, controllerTest.getGame().getCurrentCharacterDeck().getDeck().size());
        assertEquals(card1.getIdCard(), controllerTest.getGame().getCurrentActiveCharacterCard().get(0).getIdCard());
        assertEquals(1, controllerTest.getGame().getCurrentActiveCharacterCard().size());
        assertEquals(1, controllerTest.getGame().getCurrentActiveCharacterCard().get(0).getUses());
        assertEquals(baseInitialCost + 1, controllerTest.getGame().getCurrentActiveCharacterCard().get(0).getCurrentCost());
        assertEquals(6 - baseInitialCost, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).getCoinAmount());
        assertEquals((baseInitialCost - 1) + 18, controllerTest.getGame().getBankBalance());
    }

    @Test
    //Priest
    public void testEffect()
    {
        setupTest();
        controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).updateCoins(5);
        controllerTest.getGame().getCurrentTeams().get(1).getPlayers().get(0).updateCoins(4);
        Priest testCard = new Priest();
        int size = controllerTest.getGame().getCurrentCharacterDeck().getDeck().size();
        int island = (int) ((Math.random()*11));
        System.out.println(island);
        for (int i = 0; i < 4; i++)
            testCard.updateStudents(controllerTest.getGame().getCurrentPouch());
        if (size > 0) {
            controllerTest.getGame().getCurrentCharacterDeck().getDeck().subList(0, size).clear();
        }

        Col color = testCard.getStudents().get(0).getColor();
        controllerTest.getGame().getCurrentCharacterDeck().getDeck().add(testCard);
        controllerTest.getCharacterController().pickCard(controllerTest.getGame(), 0, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0));
        System.out.println(controllerTest.getGame().getCurrentIslands().getIslands().get(island).currentStudents.size());

        CharacterController.effect(testCard, controllerTest.getGame(), 0, island);

        assertEquals(1, controllerTest.getGame().getCurrentCharacterDeck().getDeck().size());
        assertEquals(4, testCard.getStudents().size());
        assertEquals(0, controllerTest.getGame().getCurrentActiveCharacterCard().size());
        assertEquals(testCard.getIdCard(), controllerTest.getGame().getCurrentCharacterDeck().getCard(0).getIdCard());

        assertEquals(color, controllerTest.getGame().getCurrentIslands().getIslands().get(island).currentStudents.get(controllerTest.getGame().getCurrentIslands().getIslands().get(island).currentStudents.size()-1).getColor());
        if(controllerTest.getGame().getCurrentIslands().getIslands().get(island).currentStudents.size() == 1)
            assertEquals(1, controllerTest.getGame().getCurrentIslands().getIslands().get(island).currentStudents.size());
        else
            assertEquals(2, controllerTest.getGame().getCurrentIslands().getIslands().get(island).currentStudents.size());
        System.out.println(controllerTest.getGame().getCurrentIslands().getIslands().get(island).currentStudents.size());
    }

    @Test
    //Princess
    public void testTestEffect()
    {
        setupTest();
        controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).updateCoins(5);
        controllerTest.getGame().getCurrentTeams().get(1).getPlayers().get(0).updateCoins(4);
        Princess testCard = new Princess();
        for (int i = 0; i < 4; i++)
            testCard.updateStudents(controllerTest.getGame().getCurrentPouch());
        controllerTest.getGame().getCurrentCharacterDeck().getDeck().subList(0, 3).clear();
        controllerTest.getGame().getCurrentCharacterDeck().getDeck().add(testCard);
        controllerTest.getCharacterController().pickCard(controllerTest.getGame(), 0, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0));

        Col color = testCard.getStudents().get(0).getColor();
        int index = color.ordinal();

        CharacterController.effect(testCard, controllerTest.getGame(), 0, "fede");

        assertEquals(1, controllerTest.getGame().getCurrentCharacterDeck().getDeck().size());
        assertEquals(4, testCard.getStudents().size());
        assertEquals(0, controllerTest.getGame().getCurrentActiveCharacterCard().size());
        assertEquals(testCard.getIdCard(), controllerTest.getGame().getCurrentCharacterDeck().getCard(0).getIdCard());

        for(int i=0; i<5; i++)
            assertEquals(0,controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).getSchool().getDiningRoom()[i]);
        for(int i =0; i<5; i++)
            if(i!=index)
                assertEquals(0,controllerTest.getGame().getCurrentTeams().get(1).getPlayers().get(0).getSchool().getDiningRoom()[i]);
            else
                assertEquals(1,controllerTest.getGame().getCurrentTeams().get(1).getPlayers().get(0).getSchool().getDiningRoom()[i]);
    }

    @Test
    //Herald
    public void testTestEffect1()
    {
        setupTest();
        controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).updateCoins(5);
        controllerTest.getGame().getCurrentTeams().get(1).getPlayers().get(0).updateCoins(4);
        Herald testCard = new Herald();
        controllerTest.getGame().getCurrentCharacterDeck().getDeck().subList(0, 3).clear();
        controllerTest.getGame().getCurrentCharacterDeck().getDeck().add(testCard);

        int island = (int) ((Math.random()*11));
        if(controllerTest.getGame().getCurrentIslands().getIslands().get(island).currentStudents.size() == 1)
            controllerTest.getGame().getCurrentIslands().getIslands().get(island).currentStudents.remove(0);


        controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).getSchool().updateProfessorsTable(0, true);   //green
        controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).getSchool().updateProfessorsTable(2, true);   //yellow

        controllerTest.getGame().getCurrentTeams().get(1).getPlayers().get(0).getSchool().updateProfessorsTable(1, true);   //red
        controllerTest.getGame().getCurrentTeams().get(1).getPlayers().get(0).getSchool().updateProfessorsTable(3, true);   //pink
        controllerTest.getGame().getCurrentTeams().get(1).getPlayers().get(0).getSchool().updateProfessorsTable(4, true);   //blue

        controllerTest.getGame().getCurrentTeams().get(0).updateProfessors();
        controllerTest.getGame().getCurrentTeams().get(1).updateProfessors();

        assertEquals(2, controllerTest.getGame().getCurrentTeams().get(0).getControlledProfessors().size());
        assertEquals(0, controllerTest.getGame().getCurrentTeams().get(0).getControlledProfessors().get(0).ordinal());
        assertEquals(2, controllerTest.getGame().getCurrentTeams().get(0).getControlledProfessors().get(1).ordinal());

        assertEquals(3, controllerTest.getGame().getCurrentTeams().get(1).getControlledProfessors().size());
        assertEquals(1, controllerTest.getGame().getCurrentTeams().get(1).getControlledProfessors().get(0).ordinal());
        assertEquals(3, controllerTest.getGame().getCurrentTeams().get(1).getControlledProfessors().get(1).ordinal());
        assertEquals(4, controllerTest.getGame().getCurrentTeams().get(1).getControlledProfessors().get(2).ordinal());

        Student s1 = new Student(Col.GREEN);
        Student s2 = new Student(Col.RED);
        Student s3 = new Student(Col.YELLOW);
        Student s4 = new Student(Col.PINK);
        Student s5 = new Student(Col.BLUE);

        controllerTest.getGame().getCurrentIslands().getIslands().get(island).currentStudents.add(s1);
        controllerTest.getGame().getCurrentIslands().getIslands().get(island).currentStudents.add(s2);
        controllerTest.getGame().getCurrentIslands().getIslands().get(island).currentStudents.add(s3);
        controllerTest.getGame().getCurrentIslands().getIslands().get(island).currentStudents.add(s4);
        controllerTest.getGame().getCurrentIslands().getIslands().get(island).currentStudents.add(s5);

        controllerTest.getCharacterController().pickCard(controllerTest.getGame(), 0, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0));

        CharacterController.effect(testCard, controllerTest.getGame(), island);

        assertEquals(1, controllerTest.getGame().getCurrentCharacterDeck().getDeck().size());
        assertEquals(0, controllerTest.getGame().getCurrentActiveCharacterCard().size());
        assertEquals(testCard.getIdCard(), controllerTest.getGame().getCurrentCharacterDeck().getCard(0).getIdCard());

        assertEquals(2, controllerTest.getGame().getCurrentIslands().getIslands().get(island).teamInfluence[0]);
        assertEquals(3, controllerTest.getGame().getCurrentIslands().getIslands().get(island).teamInfluence[1]);
        assertEquals(ColTow.WHITE, controllerTest.getGame().getCurrentIslands().getIslands().get(island).getOwnership());
        assertEquals(1, controllerTest.getGame().getCurrentIslands().getIslands().get(island).towerNumber);
        assertEquals(7, controllerTest.getGame().getCurrentTeams().get(1).getPlayers().get(0).getSchool().getTowerCount());
        assertEquals(1, controllerTest.getGame().getCurrentTeams().get(1).getControlledIslands());
        assertEquals(8, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).getSchool().getTowerCount());
        assertEquals(0, controllerTest.getGame().getCurrentTeams().get(0).getControlledIslands());
        assertFalse(controllerTest.getGame().getCurrentIslands().getIslands().get(island).motherNature);
    }

    @Test
    public void testTestEffect2()
    {
        setupTest();
        controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).updateCoins(5);
        controllerTest.getGame().getCurrentTeams().get(1).getPlayers().get(0).updateCoins(4);
        Postman testCard = new Postman();
        controllerTest.getGame().getCurrentCharacterDeck().getDeck().subList(0, 3).clear();
        controllerTest.getGame().getCurrentCharacterDeck().getDeck().add(testCard);

        controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).updateMaxMotherMovement(6);
        controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).updateMaxMotherMovement(2);

        controllerTest.getCharacterController().pickCard(controllerTest.getGame(), 0, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0));

        CharacterController.effect(testCard, controllerTest.getGame(), "jack");


        assertEquals(1, controllerTest.getGame().getCurrentCharacterDeck().getDeck().size());
        assertEquals(0, controllerTest.getGame().getCurrentActiveCharacterCard().size());
        assertEquals(testCard.getIdCard(), controllerTest.getGame().getCurrentCharacterDeck().getCard(0).getIdCard());

        assertEquals(8, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).getMaxMotherMovement());
        assertEquals(2, controllerTest.getGame().getCurrentTeams().get(1).getPlayers().get(0).getMaxMotherMovement());

    }

    @Test
    public void testTestEffect3()
    {
        setupTest();
        controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).updateCoins(5);
        controllerTest.getGame().getCurrentTeams().get(1).getPlayers().get(0).updateCoins(4);
        GrandmaWeed testCard = new GrandmaWeed();
        controllerTest.getGame().getCurrentCharacterDeck().getDeck().subList(0, 3).clear();
        controllerTest.getGame().getCurrentCharacterDeck().getDeck().add(testCard);
        int island = (int) ((Math.random()*11));

        controllerTest.getCharacterController().pickCard(controllerTest.getGame(), 0, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0));

        CharacterController.effect(testCard, controllerTest.getGame(), island);

        assertEquals(1, controllerTest.getGame().getCurrentCharacterDeck().getDeck().size());
        assertEquals(0, controllerTest.getGame().getCurrentActiveCharacterCard().size());
        assertEquals(testCard.getIdCard(), controllerTest.getGame().getCurrentCharacterDeck().getCard(0).getIdCard());

        assertTrue(controllerTest.getGame().getCurrentIslands().getIslands().get(island).getNoEntry());
        assertEquals(3, testCard.getNoEntry());

    }

    @Test
    public void testTestEffect4()
    {
        setupTest();
        controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).updateCoins(5);
        controllerTest.getGame().getCurrentTeams().get(1).getPlayers().get(0).updateCoins(4);
        Centaur testCard = new Centaur();
        controllerTest.getGame().getCurrentCharacterDeck().getDeck().subList(0, 3).clear();
        controllerTest.getGame().getCurrentCharacterDeck().getDeck().add(testCard);
        int island = (int) ((Math.random()*11));

        if(controllerTest.getGame().getCurrentIslands().getIslands().get(island).currentStudents.size() == 1)
            controllerTest.getGame().getCurrentIslands().getIslands().get(island).currentStudents.remove(0);

        Student s1 = new Student(Col.GREEN);
        Student s2 = new Student(Col.RED);
        Student s3 = new Student(Col.YELLOW);
        Student s4 = new Student(Col.PINK);
        Student s5 = new Student(Col.BLUE);

        controllerTest.getGame().getCurrentIslands().getIslands().get(island).currentStudents.add(s1);
        controllerTest.getGame().getCurrentIslands().getIslands().get(island).currentStudents.add(s2);
        controllerTest.getGame().getCurrentIslands().getIslands().get(island).currentStudents.add(s3);
        controllerTest.getGame().getCurrentIslands().getIslands().get(island).currentStudents.add(s4);
        controllerTest.getGame().getCurrentIslands().getIslands().get(island).currentStudents.add(s5);

        controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).getSchool().updateProfessorsTable(0, true);   //green
        controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).getSchool().updateProfessorsTable(1, true);   //red
        controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).getSchool().updateProfessorsTable(2, true);   //yellow

        controllerTest.getGame().getCurrentTeams().get(1).getPlayers().get(0).getSchool().updateProfessorsTable(3, true);   //pink
        controllerTest.getGame().getCurrentTeams().get(1).getPlayers().get(0).getSchool().updateProfessorsTable(4, true);   //blue

        controllerTest.getGame().getCurrentTeams().get(0).updateProfessors();
        controllerTest.getGame().getCurrentTeams().get(1).updateProfessors();

        if(!controllerTest.getGame().getCurrentIslands().getIslands().get(island).motherNature)
            controllerTest.getGame().getCurrentIslands().getIslands().get(island).updateMotherNature();
        controllerTest.getGame().getCurrentIslands().getIslands().get(island).updateTeamInfluence(controllerTest.getGame().getCurrentTeams());
        controllerTest.getGame().getCurrentIslands().getIslands().get(island).calculateOwnership();
        controllerTest.getGame().getCurrentIslands().getIslands().get(island).towerNumber = 1;
        controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).getSchool().updateTowerCount(-1);
        controllerTest.getGame().getCurrentTeams().get(0).updateControlledIslands(1);

        Student s6 = new Student(Col.BLUE);
        Student s7 = new Student(Col.PINK);
        controllerTest.getGame().getCurrentIslands().getIslands().get(island).currentStudents.add(s6);
        controllerTest.getGame().getCurrentIslands().getIslands().get(island).currentStudents.add(s7);

        controllerTest.getCharacterController().pickCard(controllerTest.getGame(), 0, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0));

        CharacterController.effect(testCard, controllerTest.getGame(), island);

        assertEquals(1, controllerTest.getGame().getCurrentCharacterDeck().getDeck().size());
        assertEquals(0, controllerTest.getGame().getCurrentActiveCharacterCard().size());
        assertEquals(testCard.getIdCard(), controllerTest.getGame().getCurrentCharacterDeck().getCard(0).getIdCard());

        assertEquals(3, controllerTest.getGame().getCurrentIslands().getIslands().get(island).teamInfluence[0]);
        assertEquals(4, controllerTest.getGame().getCurrentIslands().getIslands().get(island).teamInfluence[1]);
        assertEquals(ColTow.WHITE, controllerTest.getGame().getCurrentIslands().getIslands().get(island).getOwnership());
        assertEquals(1, controllerTest.getGame().getCurrentIslands().getIslands().get(island).towerNumber);
        assertEquals(7, controllerTest.getGame().getCurrentTeams().get(1).getPlayers().get(0).getSchool().getTowerCount());
        assertEquals(1, controllerTest.getGame().getCurrentTeams().get(1).getControlledIslands());
        assertEquals(8, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).getSchool().getTowerCount());
        assertEquals(0, controllerTest.getGame().getCurrentTeams().get(0).getControlledIslands());
        assertEquals(1, controllerTest.getGame().getCurrentTeams().get(1).getControlledIslands());
    }

    @Test
    public void testTestEffect5()
    {

    }

    @Test
    public void testTestEffect6() {
    }

    @Test
    public void deckManagement()
    {
        setupTest();
        controllerTest.getCharacterController().pickCard(controllerTest.getGame(), 0, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0));
        controllerTest.getCharacterController().pickCard(controllerTest.getGame(), 0, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0));
        controllerTest.getCharacterController().pickCard(controllerTest.getGame(), 0, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0));
        System.out.println(controllerTest.getGame().getCurrentCharacterDeck().getDeck().size());
        System.out.println(controllerTest.getGame().getCurrentActiveCharacterCard().size());
        CharacterCard card = controllerTest.getGame().getCurrentActiveCharacterCard().get(0);
        controllerTest.getCharacterController().deckManagement(controllerTest.getGame().getCurrentActiveCharacterCard().get(0), controllerTest.getGame());
        assertEquals(2, controllerTest.getGame().getCurrentActiveCharacterCard().size());
        assertEquals(1, controllerTest.getGame().getCurrentCharacterDeck().getDeck().size());
        for(int i=0; i<controllerTest.getGame().getCurrentActiveCharacterCard().size(); i++)
            assertNotEquals(card.getIdCard(), controllerTest.getGame().getCurrentActiveCharacterCard().get(i));
        assertEquals(card.getIdCard(), controllerTest.getGame().getCurrentCharacterDeck().getDeck().get(0).getIdCard());
    }

    @Test
    public void testGetPickedCard ()
    {

    }
}