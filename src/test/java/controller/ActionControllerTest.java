package controller;
import model.boards.Island;
import model.boards.token.Col;
import model.boards.token.Student;
import model.boards.token.Wizard;
import model.cards.*;
import org.junit.Test;

import java.util.Random;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
public class ActionControllerTest
{
    MainController controllerTest = new MainController(2, true);

    public void setupTest()
    {
        controllerTest.AddPlayer(0, "jack", 8, Wizard.LORD );
        controllerTest.AddPlayer(1, "fede", 8, Wizard.DRUID);
        controllerTest.Setup();
        controllerTest.updateTurnState();
        controllerTest.determineNextPlayer();
    }


    @Test
    public void testPlaceStudentToIsland()
    {
        Student s;
        setupTest();
        s = MainController.findPlayerByName(controllerTest.getGame(), controllerTest.getCurrentPlayer()).getSchool().getEntrance().get(0);
        controllerTest.getActionController().placeStudentToIsland(0, 0, controllerTest.getGame(), controllerTest.getCurrentPlayer());
        assert(controllerTest.getGame().getCurrentIslands().getIslands().get(0).getCurrentStudents().contains(s));
    }

    @Test
    public void testPlaceStudentToDiningRoom()
    {
        Student s;
        setupTest();
        s = MainController.findPlayerByName(controllerTest.getGame(), controllerTest.getCurrentPlayer()).getSchool().getEntrance().get(0);
        int x = MainController.findPlayerByName(controllerTest.getGame(), controllerTest.getCurrentPlayer()).getSchool().getDiningRoom()[s.getColor().ordinal()];
        controllerTest.getActionController().placeStudentToDiningRoom(0, controllerTest.getGame(), controllerTest.getCurrentPlayer());
        assertEquals(x + 1, MainController.findPlayerByName(controllerTest.getGame(), controllerTest.getCurrentPlayer()).getSchool().getDiningRoom()[s.getColor().ordinal()]);
    }

    @Test
    public void testMoveMN()
    {
        Random r = new Random();
        setupTest();
        controllerTest.getPlanningController().drawAssistantCard(controllerTest.getGame(), controllerTest.getCurrentPlayer(), 7);
        int movement = r.nextInt(MainController.findPlayerByName(controllerTest.getGame(), controllerTest.getCurrentPlayer()).getMaxMotherMovement() - 1) + 1;
        if(movement == 0)
        {
            movement++;
        }
        int previousPosition = controllerTest.getGame().getCurrentMotherNature().getPosition();
        controllerTest.getActionController().MoveMN(movement, controllerTest.getGame());
        assert(false == controllerTest.getGame().getCurrentIslands().getIslands().get(previousPosition).getMotherNature());
        assert(true == controllerTest.getGame().getCurrentIslands().getIslands().get(controllerTest.getGame().getCurrentMotherNature().getPosition()).getMotherNature());
    }

    @Test
    public void testSolveEverything()
    {
        setupTest();
        controllerTest.getGame().getCurrentIslands().getIslands().get(0).getCurrentStudents().removeAll(controllerTest.getGame().getCurrentIslands().getIslands().get(0).getCurrentStudents());
        for(int i = 0; i < 4; i++)
        {
            MainController.findPlayerByName(controllerTest.getGame(), controllerTest.getCurrentPlayer()).getSchool().placeToken(new Student(Col.PINK));

        }
        //piazzo due studenti rosa nella dining room del current player
        for(int i = 0; i < 2; i++)
        {
            controllerTest.getActionController().placeStudentToDiningRoom(
                    MainController.findPlayerByName(controllerTest.getGame(), controllerTest.getCurrentPlayer()).getSchool().getEntrance().size()-1,
                    controllerTest.getGame(),
                    controllerTest.getCurrentPlayer()
            );
        }
        //piazzo due studenti rosa sull'isola 0
        for(int i = 0; i < 2; i++)
        {
            controllerTest.getActionController().placeStudentToIsland(
                    MainController.findPlayerByName(controllerTest.getGame(), controllerTest.getCurrentPlayer()).getSchool().getEntrance().size()-1,
                    0,
                    controllerTest.getGame(),
                    controllerTest.getCurrentPlayer()
            );
        }
        assertEquals(true, MainController.findPlayerByName(controllerTest.getGame(), controllerTest.getCurrentPlayer()).getSchool().getProfessorTable()[Col.PINK.ordinal()]);
        controllerTest.getGame().getCurrentIslands().getIslands().get(0).motherNature = true;
        ActionController.solveEverything(controllerTest.getGame(), 0);
        assertEquals(MainController.getPlayerColor(controllerTest.getGame(), controllerTest.getCurrentPlayer()),  controllerTest.getGame().getCurrentIslands().getIslands().get(0).getOwnership());
        controllerTest.determineNextPlayer();

        //Cambio l'ownership sull'isola 0
        for(int i = 0; i < 6; i++)
        {
            MainController.findPlayerByName(controllerTest.getGame(), controllerTest.getCurrentPlayer()).getSchool().placeToken(new Student(Col.RED));
        }

        //piazzo due studenti rossi nella dining room del current player
        for(int i = 0; i < 2; i++)
        {
            controllerTest.getActionController().placeStudentToDiningRoom(
                    MainController.findPlayerByName(controllerTest.getGame(), controllerTest.getCurrentPlayer()).getSchool().getEntrance().size()-1,
                    controllerTest.getGame(),
                    controllerTest.getCurrentPlayer()
            );
        }

        //piazzo 4 studenti rossi sull'isola 0
        for(int i = 0; i < 4; i++)
        {
            controllerTest.getActionController().placeStudentToIsland(
                    MainController.findPlayerByName(controllerTest.getGame(), controllerTest.getCurrentPlayer()).getSchool().getEntrance().size()-1,
                    0,
                    controllerTest.getGame(),
                    controllerTest.getCurrentPlayer()
            );
        }
        assertEquals(true, MainController.findPlayerByName(controllerTest.getGame(), controllerTest.getCurrentPlayer()).getSchool().getProfessorTable()[Col.RED.ordinal()]);
        controllerTest.getGame().getCurrentIslands().getIslands().get(0).motherNature = true;
        ActionController.solveEverything(controllerTest.getGame(), 0);
        assertEquals(MainController.getPlayerColor(controllerTest.getGame(), controllerTest.getCurrentPlayer()),  controllerTest.getGame().getCurrentIslands().getIslands().get(0).getOwnership());
    /*fgetc(stdin);*/
    }

    @Test
    public void TestDrawFromClouds()
    {
        setupTest();
        controllerTest.getPlanningController().drawStudentForClouds(controllerTest.getGame(), 0);
        int x = MainController.findPlayerByName(controllerTest.getGame(), controllerTest.getCurrentPlayer()).getSchool().getEntrance().size();
        controllerTest.getActionController().drawFromClouds(0, controllerTest.getGame(), controllerTest.getCurrentPlayer());
        assert(controllerTest.getGame().getCurrentClouds()[0].getStudents().isEmpty());
        assert(x + 3 == MainController.findPlayerByName(controllerTest.getGame(), controllerTest.getCurrentPlayer()).getSchool().getEntrance().size());
    }

    @Test
    public void TestIsCloudEmpty()
    {
        setupTest();
        assert(controllerTest.getActionController().isCloudEmpty(0, controllerTest.getGame()));
        controllerTest.getPlanningController().drawStudentForClouds(controllerTest.getGame(), 0);
        assert(!controllerTest.getActionController().isCloudEmpty(0, controllerTest.getGame()));
    }

    @Test
    public void testPossibleMNmove()
    {
        setupTest();
        controllerTest.getPlanningController().drawAssistantCard(controllerTest.getGame(), controllerTest.getCurrentPlayer(), 7);
        assert(controllerTest.getActionController().possibleMNmove(4, controllerTest.getGame()));
        assert(!controllerTest.getActionController().possibleMNmove(6, controllerTest.getGame()));
    }

    @Test
    public void checkForCharacters()
    {
        setupTest();

        CharacterCard knight = new Knight();
        CharacterCard truffleHunter = new TruffleHunter();
        CharacterCard centaur = new Centaur();

        EffectTestsUtility.setDecks(knight, controllerTest.getGame());
        EffectTestsUtility.verifyDecks(knight, controllerTest.getGame());

        controllerTest.getGame().getCurrentCharacterDeck().getDeck().add(truffleHunter);
        controllerTest.getGame().getCurrentCharacterDeck().getDeck().add(centaur);

        controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).updateCoins(10);

        for(int i=0; i<3; i++)
            controllerTest.getCharacterController().pickCard(controllerTest.getGame(), 0, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0));

        assertEquals(3, controllerTest.getGame().getCurrentActiveCharacterCard().size());
        assertEquals(0, controllerTest.getGame().getCurrentCharacterDeck().getDeck().size());

        controllerTest.getActionController().MoveMN(3, controllerTest.getGame());
        controllerTest.getActionController().MoveMN(3, controllerTest.getGame());
        controllerTest.getActionController().MoveMN(3, controllerTest.getGame());

        assertEquals(0, controllerTest.getGame().getCurrentActiveCharacterCard().size());
        assertEquals(3, controllerTest.getGame().getCurrentCharacterDeck().getDeck().size());

        Herald herald = new Herald();

        EffectTestsUtility.setDecks(herald, controllerTest.getGame());
        EffectTestsUtility.verifyDecks(herald, controllerTest.getGame());

        controllerTest.getCharacterController().pickCard(controllerTest.getGame(), 0, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0));
        controllerTest.getActionController().MoveMN(3, controllerTest.getGame());

        assertEquals(1, controllerTest.getGame().getCurrentActiveCharacterCard().size());
        assertEquals(0, controllerTest.getGame().getCurrentCharacterDeck().getDeck().size());
    }



}
