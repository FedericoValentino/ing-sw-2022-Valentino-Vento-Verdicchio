package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.TestUtilities;
import it.polimi.ingsw.controller.MainController;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.boards.token.enumerations.CharacterName;
import it.polimi.ingsw.model.boards.token.enumerations.Col;
import it.polimi.ingsw.model.cards.characters.Thief;
import junit.framework.TestCase;

public class ThiefTest extends TestCase {

    /**
     * We set up the two players' dining rooms this way:
     * - The white player will get three students for each color
     * - The Grey player will get four students of the first three colors, and then one student for each of the last colors
     * After saving the pouch size, The effect is played on the color Green: we check if WHITE's dining room has been
     * depleted of greens, and if GREY's dining room still has one green student.
     * Then, we check that exactly six students have been added to the pouch.
     *
     */
    public void testEffect()
    {
        MainController controllerTest = new MainController(2, true);

        TestUtilities.setupTestFor2(controllerTest);
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

