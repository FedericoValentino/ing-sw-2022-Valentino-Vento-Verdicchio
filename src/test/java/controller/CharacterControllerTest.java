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

    public void setupTest()
    {
        controllerTest.AddPlayer(0, "jack", 8, "Franco");   //grey
        controllerTest.AddPlayer(1, "fede", 8, "Giulio");   //white
        controllerTest.Setup();
        controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).updateCoins(5);
        controllerTest.getGame().getCurrentTeams().get(1).getPlayers().get(0).updateCoins(4);
    }

    public void verifyDecks(CharacterCard testCard)
    {
        assertEquals(1, controllerTest.getGame().getCurrentCharacterDeck().getDeck().size());
        assertEquals(0, controllerTest.getGame().getCurrentActiveCharacterCard().size());
        assertEquals(testCard.getIdCard(), controllerTest.getGame().getCurrentCharacterDeck().getCard(0).getIdCard());
    }

    public void setDecks(CharacterCard testCard)
    {
        controllerTest.getGame().getCurrentCharacterDeck().getDeck().subList(0, 3).clear();
        controllerTest.getGame().getCurrentCharacterDeck().getDeck().add(testCard);
    }

    public int basicIslandSetup()
    {
        int island = (int) ((Math.random()*11));
        if(controllerTest.getGame().getCurrentIslands().getIslands().get(island).currentStudents.size() == 1)
            controllerTest.getGame().getCurrentIslands().getIslands().get(island).currentStudents.remove(0);


        controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).getSchool().updateProfessorsTable(0, true);   //green
        controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).getSchool().updateProfessorsTable(1, true);   //red
        controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).getSchool().updateProfessorsTable(2, true);   //yellow


        controllerTest.getGame().getCurrentTeams().get(1).getPlayers().get(0).getSchool().updateProfessorsTable(3, true);   //pink
        controllerTest.getGame().getCurrentTeams().get(1).getPlayers().get(0).getSchool().updateProfessorsTable(4, true);   //blue

        controllerTest.getGame().getCurrentTeams().get(0).updateProfessors();
        controllerTest.getGame().getCurrentTeams().get(1).updateProfessors();

        assertEquals(3, controllerTest.getGame().getCurrentTeams().get(0).getControlledProfessors().size());
        assertEquals(0, controllerTest.getGame().getCurrentTeams().get(0).getControlledProfessors().get(0).ordinal());
        assertEquals(1, controllerTest.getGame().getCurrentTeams().get(0).getControlledProfessors().get(1).ordinal());
        assertEquals(2, controllerTest.getGame().getCurrentTeams().get(0).getControlledProfessors().get(2).ordinal());

        assertEquals(2, controllerTest.getGame().getCurrentTeams().get(1).getControlledProfessors().size());
        assertEquals(3, controllerTest.getGame().getCurrentTeams().get(1).getControlledProfessors().get(0).ordinal());
        assertEquals(4, controllerTest.getGame().getCurrentTeams().get(1).getControlledProfessors().get(1).ordinal());

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

        return island;
    }

    public void checksAfterInfluenceCalculation(int winningTeam, int island)
    {
        if(winningTeam == 0)
        {
            assertEquals(ColTow.GREY, controllerTest.getGame().getCurrentIslands().getIslands().get(island).getOwnership());
            assertEquals(1, controllerTest.getGame().getCurrentIslands().getIslands().get(island).towerNumber);
            assertEquals(8, controllerTest.getGame().getCurrentTeams().get(1).getPlayers().get(0).getSchool().getTowerCount());
            assertEquals(0, controllerTest.getGame().getCurrentTeams().get(1).getControlledIslands());
            assertEquals(7, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).getSchool().getTowerCount());
            assertEquals(1, controllerTest.getGame().getCurrentTeams().get(0).getControlledIslands());
        }
        else if(winningTeam == 1)
        {
            assertEquals(ColTow.WHITE, controllerTest.getGame().getCurrentIslands().getIslands().get(island).getOwnership());
            assertEquals(1, controllerTest.getGame().getCurrentIslands().getIslands().get(island).towerNumber);
            assertEquals(7, controllerTest.getGame().getCurrentTeams().get(1).getPlayers().get(0).getSchool().getTowerCount());
            assertEquals(8, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).getSchool().getTowerCount());
            assertEquals(1, controllerTest.getGame().getCurrentTeams().get(1).getControlledIslands());
            assertEquals(0, controllerTest.getGame().getCurrentTeams().get(0).getControlledIslands());
        }
    }


    @Test
    public void testPickCard() {
        setupTest();
        assertEquals(6, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).getCoinAmount());
        assertEquals(5, controllerTest.getGame().getCurrentTeams().get(1).getPlayers().get(0).getCoinAmount());
        CharacterCard card = controllerTest.getGame().getCurrentCharacterDeck().getCard(0);
        int baseInitialCost = card.getBaseCost();

        //pick card

        controllerTest.getCharacterController().pickCard(controllerTest.getGame(), 0, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0));

        //assertions

        assertEquals(2, controllerTest.getGame().getCurrentCharacterDeck().getDeck().size());
        assertEquals(card.getIdCard(), controllerTest.getGame().getCurrentActiveCharacterCard().get(0).getIdCard());
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
        Priest testCard = new Priest();
        int island = (int) ((Math.random()*11));
        for (int i = 0; i < 4; i++)
            testCard.updateStudents(controllerTest.getGame().getCurrentPouch());

        Col color = testCard.getStudents().get(0).getColor();
        setDecks(testCard);

        controllerTest.getCharacterController().pickCard(controllerTest.getGame(), 0, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0));

        CharacterController.effect(testCard, controllerTest.getGame(), 0, island);

        verifyDecks(testCard);

        assertEquals(4, testCard.getStudents().size());

        assertEquals(color, controllerTest.getGame().getCurrentIslands().getIslands().get(island).currentStudents.get(controllerTest.getGame().getCurrentIslands().getIslands().get(island).currentStudents.size()-1).getColor());
        if(controllerTest.getGame().getCurrentIslands().getIslands().get(island).currentStudents.size() == 1)
            assertEquals(1, controllerTest.getGame().getCurrentIslands().getIslands().get(island).currentStudents.size());
        else
            assertEquals(2, controllerTest.getGame().getCurrentIslands().getIslands().get(island).currentStudents.size());
    }

    @Test
    //Princess
    public void testTestEffect()
    {
        setupTest();
        Princess testCard = new Princess();
        for (int i = 0; i < 4; i++)
            testCard.updateStudents(controllerTest.getGame().getCurrentPouch());
        setDecks(testCard);
        controllerTest.getCharacterController().pickCard(controllerTest.getGame(), 0, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0));

        Col color = testCard.getStudents().get(0).getColor();
        int index = color.ordinal();

        CharacterController.effect(testCard, controllerTest.getGame(), 0, "fede");

        verifyDecks(testCard);
        assertEquals(4, testCard.getStudents().size());

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
        Herald testCard = new Herald();
        setDecks(testCard);
        int island = basicIslandSetup();

        controllerTest.getCharacterController().pickCard(controllerTest.getGame(), 0, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0));

        CharacterController.effect(testCard, controllerTest.getGame(), island);

        verifyDecks(testCard);

        assertEquals(3, controllerTest.getGame().getCurrentIslands().getIslands().get(island).teamInfluence[0]);
        assertEquals(2, controllerTest.getGame().getCurrentIslands().getIslands().get(island).teamInfluence[1]);
        checksAfterInfluenceCalculation(0, island);
        assertFalse(controllerTest.getGame().getCurrentIslands().getIslands().get(island).motherNature);
    }

    @Test
    //Postman
    public void testTestEffect2()
    {
        setupTest();
        Postman testCard = new Postman();
        setDecks(testCard);

        controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).updateMaxMotherMovement(6);
        controllerTest.getGame().getCurrentTeams().get(1).getPlayers().get(0).updateMaxMotherMovement(2);

        controllerTest.getCharacterController().pickCard(controllerTest.getGame(), 0, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0));

        CharacterController.effect(testCard, controllerTest.getGame(), "jack");

        verifyDecks(testCard);

        assertEquals(8, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).getMaxMotherMovement());
        assertEquals(2, controllerTest.getGame().getCurrentTeams().get(1).getPlayers().get(0).getMaxMotherMovement());

    }

    @Test
    //GWeed
    public void testTestEffect3()
    {
        setupTest();
        GrandmaWeed testCard = new GrandmaWeed();
        setDecks(testCard);
        int island = (int) ((Math.random()*11));

        controllerTest.getCharacterController().pickCard(controllerTest.getGame(), 0, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0));

        CharacterController.effect(testCard, controllerTest.getGame(), island);

        verifyDecks(testCard);

        assertTrue(controllerTest.getGame().getCurrentIslands().getIslands().get(island).getNoEntry());
        assertEquals(3, testCard.getNoEntry());

    }

    @Test
    //Centaur
    public void testTestEffect4()
    {
        setupTest();
        Centaur testCard = new Centaur();
        setDecks(testCard);
        int island = basicIslandSetup();

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

        verifyDecks(testCard);

        assertEquals(3, controllerTest.getGame().getCurrentIslands().getIslands().get(island).teamInfluence[0]);
        assertEquals(4, controllerTest.getGame().getCurrentIslands().getIslands().get(island).teamInfluence[1]);
        checksAfterInfluenceCalculation(1, island);
    }

    @Test
    //THunter
    public void testTestEffect5()
    {
        setupTest();
        TruffleHunter testCard = new TruffleHunter();
        setDecks(testCard);
        int island = basicIslandSetup();

        Student s6 = new Student(Col.BLUE);
        Student s7 = new Student(Col.BLUE);
        controllerTest.getGame().getCurrentIslands().getIslands().get(island).currentStudents.add(s6);
        controllerTest.getGame().getCurrentIslands().getIslands().get(island).currentStudents.add(s7);

        if(!controllerTest.getGame().getCurrentIslands().getIslands().get(island).motherNature)
            controllerTest.getGame().getCurrentIslands().getIslands().get(island).updateMotherNature();

        controllerTest.getCharacterController().pickCard(controllerTest.getGame(), 0, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0));

        CharacterController.effect(testCard, controllerTest.getGame(), Col.BLUE, island);

        verifyDecks(testCard);

        checksAfterInfluenceCalculation(0, island);
        assertEquals(3, (int) controllerTest.getGame().getCurrentIslands().getIslands().get(island).currentStudents.stream().filter(Student -> Student.getColor() == Col.BLUE).count());

    }

    @Test
    //Knight
    public void testTestEffect6()
    {
        setupTest();
        Knight testCard = new Knight();
        setDecks(testCard);
        int island = basicIslandSetup();

        if(!controllerTest.getGame().getCurrentIslands().getIslands().get(island).motherNature)
            controllerTest.getGame().getCurrentIslands().getIslands().get(island).updateMotherNature();

        controllerTest.getCharacterController().pickCard(controllerTest.getGame(), 0, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0));

        CharacterController.effect(testCard, controllerTest.getGame(), island, 1);

        verifyDecks(testCard);

        checksAfterInfluenceCalculation(1, island);
        assertEquals(2, controllerTest.getGame().getCurrentIslands().getIslands().get(island).teamInfluence[1]);


    }

    @Test
    public void deckManagement()
    {
        setupTest();
        for(int i=0; i<3; i++)
            controllerTest.getCharacterController().pickCard(controllerTest.getGame(), 0, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0));
        CharacterCard card = controllerTest.getGame().getCurrentActiveCharacterCard().get(0);
        controllerTest.getCharacterController().deckManagement(controllerTest.getGame().getCurrentActiveCharacterCard().get(0), controllerTest.getGame());
        assertEquals(2, controllerTest.getGame().getCurrentActiveCharacterCard().size());
        assertEquals(1, controllerTest.getGame().getCurrentCharacterDeck().getDeck().size());
        for(int i=0; i<controllerTest.getGame().getCurrentActiveCharacterCard().size(); i++)
            assertNotEquals(card.getIdCard(), controllerTest.getGame().getCurrentActiveCharacterCard().get(i).getIdCard());
        assertEquals(card.getIdCard(), controllerTest.getGame().getCurrentCharacterDeck().getDeck().get(0).getIdCard());
    }

    @Test
    public void testGetPickedCard ()
    {
        setupTest();
        Knight testCard = new Knight();
        setDecks(testCard);

        controllerTest.getCharacterController().pickCard(controllerTest.getGame(), 0, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0));

        CharacterCard card = controllerTest.getCharacterController().getPickedCard();
        assertEquals(8, card.getIdCard());
    }
}