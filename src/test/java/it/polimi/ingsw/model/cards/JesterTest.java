package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.TestUtilities;
import it.polimi.ingsw.controller.MainController;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.boards.token.enumerations.CharacterName;
import it.polimi.ingsw.model.boards.token.enumerations.Col;
import it.polimi.ingsw.model.boards.token.Student;
import it.polimi.ingsw.model.cards.characters.Jester;
import org.junit.Test;

import static org.junit.Assert.*;
import java.util.ArrayList;

public class JesterTest{

    /**
     * After saving the color of the first student on the Jester card and emptying the GREY player's entrance, we refill
     * the entrance with one student of each color, in order.
     * After this, we select the first student on the card and the last student in the entrance for the swap. and call
     * the Jester effect.
     * Since arraylists add elements to the tail, we check the tails of the card and of the entrance to see if the students have been swapped correctly
     */
    @Test
    public void testEffect()
    {
        MainController controllerTest = new MainController(2, true);

        TestUtilities.setupTestFor2(controllerTest);
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

        TestUtilities.setupTestFor2(controllerTest);
        Jester testCard = new Jester();

        for(int i = 0; i < 6; i++)
            testCard.updateStudents(controllerTest.getGame().getCurrentPouch());

        Col firstStudent = testCard.getStudents().get(0).getColor();
        Student extractedStudent = testCard.getStudent(0);

        assertEquals(firstStudent, extractedStudent.getColor());
        assertEquals(5, testCard.getStudents().size());
    }
}