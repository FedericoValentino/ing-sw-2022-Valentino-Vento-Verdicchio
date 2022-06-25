package it.polimi.ingsw.model.cards;


import it.polimi.ingsw.TestUtilities;
import it.polimi.ingsw.controller.MainController;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.boards.token.enumerations.CharacterName;
import it.polimi.ingsw.model.boards.token.enumerations.Col;
import it.polimi.ingsw.model.cards.characters.Cook;
import org.junit.Test;
import static org.junit.Assert.*;

public class CookTest {

    @Test
    public void testEffect()
    {
        MainController controllerTest = new MainController(2, true);

        TestUtilities.setupTestFor2(controllerTest);
        Cook testCard = new Cook();

        EffectTestsUtility.setDecks(testCard, controllerTest.getGame());
        EffectTestsUtility.verifyDecks(testCard, controllerTest.getGame());

        Player jack = controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0);
        Player fede = controllerTest.getGame().getCurrentTeams().get(1).getPlayers().get(0);

        controllerTest.getGame().getCurrentTurnState().setCurrentPlayer("fede");

        jack.getSchool().placeInDiningRoom(Col.GREEN);
        for(int i = 0; i < 2; i++)
            jack.getSchool().placeInDiningRoom(Col.BLUE);

        fede.getSchool().placeInDiningRoom(Col.GREEN);
        fede.getSchool().placeInDiningRoom(Col.BLUE);

        controllerTest.getGame().giveProfessors(false);

        controllerTest.getGame().getCurrentTeams().get(0).updateProfessors();
        controllerTest.getGame().getCurrentTeams().get(1).updateProfessors();

        assertEquals(Col.BLUE, controllerTest.getGame().getCurrentTeams().get(0).getControlledProfessors().get(0));

        controllerTest.getCharacterController().pickCard(controllerTest.getGame(), CharacterName.COOK, fede);
        testCard.effect(controllerTest.getGame(), null, null, "fede", null);

        assertTrue(fede.getSchool().getProfessorTable()[0]);
        assertFalse(fede.getSchool().getProfessorTable()[4]);
        assertTrue(jack.getSchool().getProfessorTable()[4]);
        assertFalse(jack.getSchool().getProfessorTable()[0]);

        fede.getSchool().placeInDiningRoom(Col.BLUE);
        testCard.effect(controllerTest.getGame(), null, null, "fede", null);

        assertTrue(fede.getSchool().getProfessorTable()[0]);
        assertTrue(fede.getSchool().getProfessorTable()[4]);
        assertFalse(jack.getSchool().getProfessorTable()[4]);
        assertFalse(jack.getSchool().getProfessorTable()[0]);

        controllerTest.getGame().getCurrentTeams().get(0).updateProfessors();
        controllerTest.getGame().getCurrentTeams().get(1).updateProfessors();

        assert(controllerTest.getGame().getCurrentTeams().get(1).getControlledProfessors().contains(Col.GREEN)
        && controllerTest.getGame().getCurrentTeams().get(1).getControlledProfessors().contains(Col.BLUE));

        assertFalse(controllerTest.getGame().getCurrentTeams().get(0).getControlledProfessors().contains(Col.GREEN)
                && controllerTest.getGame().getCurrentTeams().get(0).getControlledProfessors().contains(Col.BLUE));
    }
}