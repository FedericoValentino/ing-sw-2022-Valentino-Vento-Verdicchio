package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.controller.CharacterControllerTest;
import it.polimi.ingsw.controller.MainController;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.boards.token.CharacterName;
import it.polimi.ingsw.model.boards.token.Col;
import junit.framework.TestCase;

public class ThiefTest extends TestCase {

    public void testEffect()
    {
        MainController controllerTest = new MainController(2, true);

        CharacterControllerTest.setupTest(controllerTest);
        Thief testCard = new Thief();

        EffectTestsUtility.setDecks(testCard, controllerTest.getGame());
        EffectTestsUtility.verifyDecks(testCard, controllerTest.getGame());

        Player fede = controllerTest.getGame().getCurrentTeams().get(1).getPlayers().get(0);
        Player jack = controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0);

        controllerTest.getGame().getCurrentTurnState().setCurrentPlayer("fede");

        for(int i = 0; i < 5; i++)
            for(int j = 0; j < 3; j++)
                fede.getSchool().placeInDiningRoom(Col.values()[i]);

        for(int i = 0; i < 3; i++)
            for(int j = 0; j < 4; j++)
                jack.getSchool().placeInDiningRoom(Col.values()[i]);

        jack.getSchool().placeInDiningRoom(Col.PINK);
        jack.getSchool().placeInDiningRoom(Col.BLUE);

        int initialPouchSize = controllerTest.getGame().getCurrentPouch().getContent().size();

        controllerTest.getCharacterController().pickCard(controllerTest.getGame(), CharacterName.THIEF, fede);
        testCard.effect(controllerTest.getGame(), null, null, "fede", Col.GREEN);

        assertEquals(0, fede.getSchool().getDiningRoom()[0]);
        assertEquals(1, jack.getSchool().getDiningRoom()[0]);
        assertEquals(initialPouchSize + 6, controllerTest.getGame().getCurrentPouch().getContent().size());

    }
}

