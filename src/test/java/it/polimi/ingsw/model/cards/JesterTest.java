package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.controller.CharacterControllerTest;
import it.polimi.ingsw.controller.MainController;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.boards.token.CharacterName;
import it.polimi.ingsw.model.boards.token.Col;
import it.polimi.ingsw.model.boards.token.Student;
import org.junit.Test;

import static org.junit.Assert.*;
import java.util.ArrayList;

public class JesterTest{

    @Test
    public void testEffect()
    {
        MainController controllerTest = new MainController(2, true);

        CharacterControllerTest.setupTest(controllerTest);
        Jester testCard = new Jester();

        for(int i = 0; i < 6; i++)
            testCard.updateStudents(controllerTest.getGame().getCurrentPouch());

        Col firstStudent = testCard.getStudents().get(0).getColor();

        EffectTestsUtility.setDecks(testCard, controllerTest.getGame());
        EffectTestsUtility.verifyDecks(testCard, controllerTest.getGame());

        Player jack = controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0);

        controllerTest.getGame().getCurrentTurnState().setCurrentPlayer("jack");

        jack.getSchool().getEntrance().clear();

        for(int i = 0; i < 5; i++)
            jack.getSchool().getEntrance().add(new Student(Col.values()[i]));

        ArrayList<Integer> cardIndexes = new ArrayList<>();
        cardIndexes.add(0);

        ArrayList<Integer> entranceIndexes = new ArrayList<>();
        entranceIndexes.add(4);

        controllerTest.getCharacterController().pickCard(controllerTest.getGame(), CharacterName.JESTER, jack);
        testCard.effect(controllerTest.getGame(), cardIndexes, entranceIndexes, "jack", null);

        assertEquals(firstStudent, jack.getSchool().getEntrance().get(4).getColor());
        assertEquals(Col.BLUE, testCard.getStudents().get(5).getColor());

    }

    @Test
    public void testGetStudent()
    {
        MainController controllerTest = new MainController(2, true);

        CharacterControllerTest.setupTest(controllerTest);
        Jester testCard = new Jester();

        for(int i = 0; i < 6; i++)
            testCard.updateStudents(controllerTest.getGame().getCurrentPouch());

        Col firstStudent = testCard.getStudents().get(0).getColor();
        Student extractedStudent = testCard.getStudent(0);

        assertEquals(firstStudent, extractedStudent.getColor());
        assertEquals(5, testCard.getStudents().size());
    }
}