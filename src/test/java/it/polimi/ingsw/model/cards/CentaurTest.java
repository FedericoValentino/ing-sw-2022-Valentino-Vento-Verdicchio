package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.TestUtilities;
import it.polimi.ingsw.controller.CharacterControllerTest;
import it.polimi.ingsw.controller.MainController;
import it.polimi.ingsw.model.boards.token.CharacterName;
import it.polimi.ingsw.model.boards.token.Col;
import it.polimi.ingsw.model.boards.token.Student;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class CentaurTest {
    Centaur c=new Centaur();


    @Test
    public void testGetUses()
    {
        assertEquals(c.getUses(),0);
    }
    @Test
    public void testUpdateCost()
    {

        assertEquals(c.getBaseCost(),3);
        assertEquals(c.currentCost,3);
        c.updateCost();
        assertEquals(c.getCurrentCost(),(c.getUses()+c.getBaseCost()));
    }

    @Test
    public void checkIdCard()
    {
        assertEquals(c.getCharacterName(), CharacterName.CENTAUR);
    }

    @Test
    /** Centaur effect test */
    public void testTestEffect4()
    {
        MainController controllerTest = new MainController(2, true);

        TestUtilities.setupTestFor2(controllerTest);
        Centaur testCard = new Centaur();

        EffectTestsUtility.setDecks(testCard, controllerTest.getGame());
        EffectTestsUtility.verifyDecks(testCard, controllerTest.getGame());
        int island = EffectTestsUtility.basicIslandSetup(controllerTest.getGame());

        /*Creates a situation in which the chosen island is already controlled by the GREY team;
        it adds a tower to the island and decrements the tower count in the GREY player's school.
        It updates the ownership on the island and the controlledIslands of the GREY team.
        Sets MotherNature to true, in order to simulate the situation in which the effect of this card is played */
        if(!controllerTest.getGame().getCurrentIslands().getIslands().get(island).getMotherNature())
            controllerTest.getGame().getCurrentIslands().getIslands().get(island).updateMotherNature();
        controllerTest.getGame().getCurrentIslands().getIslands().get(island).updateTeamInfluence(controllerTest.getGame().getCurrentTeams());
        controllerTest.getGame().getCurrentIslands().getIslands().get(island).calculateOwnership();
        controllerTest.getGame().getCurrentIslands().getIslands().get(island).setTowerNumber(1);
        controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).getSchool().updateTowerCount(-1);
        controllerTest.getGame().getCurrentTeams().get(0).updateControlledIslands(1);

        /*Creates two more students of the colours corresponding to the controlled professors of the WHITE team;
        considering the presence of the tower, we have a situation on parity on the island.
        With the activation of the effect, the tower should be eliminated, and so the WHITE team should win*/
        Student s6 = new Student(Col.BLUE);
        Student s7 = new Student(Col.PINK);
        controllerTest.getGame().getCurrentIslands().getIslands().get(island).getCurrentStudents().add(s6);
        controllerTest.getGame().getCurrentIslands().getIslands().get(island).getCurrentStudents().add(s7);
        ArrayList<Integer> chosenIsland = new ArrayList<>();
        chosenIsland.add(island);

        controllerTest.getCharacterController().pickCard(controllerTest.getGame(), CharacterName.CENTAUR, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0));
        testCard.effect(controllerTest.getGame(), null, chosenIsland, null, null);




        //Checks if the influence calculation has been done correctly and if the WHITE team won as expected
        assertEquals(3, controllerTest.getGame().getCurrentIslands().getIslands().get(island).getTeamInfluence()[0]);
        assertEquals(4, controllerTest.getGame().getCurrentIslands().getIslands().get(island).getTeamInfluence()[1]);
        EffectTestsUtility.checksAfterInfluenceCalculation(controllerTest.getGame(), 1, island);
    }
}
