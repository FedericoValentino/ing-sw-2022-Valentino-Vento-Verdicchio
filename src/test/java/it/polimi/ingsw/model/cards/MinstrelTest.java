package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.TestUtilities;
import it.polimi.ingsw.controller.MainController;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.boards.token.enumerations.CharacterName;
import it.polimi.ingsw.model.boards.token.enumerations.Col;
import it.polimi.ingsw.model.boards.token.Student;
import it.polimi.ingsw.model.cards.characters.Minstrel;
import junit.framework.TestCase;

import java.util.ArrayList;

public class MinstrelTest extends TestCase {

    /**
     * We clear the WHITE player's entrance and refill it with 5 students of each color, in order; then we place a Green and
     * a Blue students in his dining room.
     * We then proceed to call the Minstrel effects, choosing the Red and Yellow students from the entrance and the Green and Blue students
     * from the dining room.
     * We ensure that now only one Red and one Yellow students will be present in the dining room, and that at the tail of
     * the entrance we will have a Green and a Blue student
     */
    public void testEffect()
    {
        MainController controllerTest = new MainController(2, true);

        TestUtilities.setupTestFor2(controllerTest);
        Minstrel testCard = new Minstrel();

        EffectTestsUtility.setDecks(testCard, controllerTest.getGame());
        EffectTestsUtility.verifyDecks(testCard, controllerTest.getGame());

        Player fede = controllerTest.getGame().getCurrentTeams().get(1).getPlayers().get(0);

        controllerTest.getGame().getCurrentTurnState().setCurrentPlayer("fede");

        fede.getSchool().getEntrance().clear();

        for(int i = 0; i < 5; i++)
            fede.getSchool().getEntrance().add(new Student(Col.values()[i]));

        fede.getSchool().placeInDiningRoom(Col.GREEN);
        fede.getSchool().placeInDiningRoom(Col.BLUE);

        ArrayList<Integer> entranceIndexes = new ArrayList<>();
        entranceIndexes.add(1);
        entranceIndexes.add(2);

        ArrayList<Integer> colorOrdinals = new ArrayList<>();
        colorOrdinals.add(0);
        colorOrdinals.add(4);

        controllerTest.getCharacterController().pickCard(controllerTest.getGame(), CharacterName.MINSTREL, fede);
        testCard.effect(controllerTest.getGame(), entranceIndexes, colorOrdinals, "fede", null);

        assertEquals(0, fede.getSchool().getDiningRoom()[0]);
        assertEquals(1, fede.getSchool().getDiningRoom()[1]);
        assertEquals(1, fede.getSchool().getDiningRoom()[2]);
        assertEquals(0, fede.getSchool().getDiningRoom()[3]);
        assertEquals(0, fede.getSchool().getDiningRoom()[4]);

        assertEquals(Col.GREEN, fede.getSchool().getEntrance().get(3).getColor());
        assertEquals(Col.BLUE, fede.getSchool().getEntrance().get(4).getColor());
    }
}