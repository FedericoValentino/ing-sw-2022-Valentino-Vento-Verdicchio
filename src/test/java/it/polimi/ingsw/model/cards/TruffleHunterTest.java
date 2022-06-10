package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.controller.CharacterControllerTest;
import it.polimi.ingsw.controller.MainController;
import it.polimi.ingsw.model.boards.token.CharacterName;
import it.polimi.ingsw.model.boards.token.Col;
import it.polimi.ingsw.model.boards.token.Student;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TruffleHunterTest {
    TruffleHunter t=new TruffleHunter();


    @Test
    public void testGetUses()
    {
        assertEquals(t.getUses(),0);
    }
    @Test
    public void testUpdateCost()
    {

        assertEquals(t.getBaseCost(),3);
        assertEquals(t.currentCost,t.getBaseCost());
        t.updateCost();
        assertEquals(t.getCurrentCost(),(t.getUses()+t.getBaseCost()));
    }

    @Test
    public void checkIdCard()
    {
        assertEquals(t.getCharacterName(), CharacterName.TRUFFLE_HUNTER);
    }

    @Test
    public void testSetChooseColor()
    {
        t.setChosenColor(Col.YELLOW);
        assertEquals(Col.YELLOW,t.getChosenColor());
    }

    @Test
    /** Truffle Hunter effect test */
    public void testTestEffect5()
    {
        MainController controllerTest = new MainController(2, true);

        CharacterControllerTest.setupTest(controllerTest);
        TruffleHunter testCard = new TruffleHunter();

        EffectTestsUtility.setDecks(testCard, controllerTest.getGame());
        EffectTestsUtility.verifyDecks(testCard, controllerTest.getGame());

        int island = EffectTestsUtility.basicIslandSetup(controllerTest.getGame());

        /*Adds two students of a colour controlled by WHITE on the island: White has the higher influence now,
        but with the activation of the effect, the colour BLUE will not count towards the influence calculation,
        and GREY should secure the victory   */
        Student s6 = new Student(Col.BLUE);
        Student s7 = new Student(Col.BLUE);
        controllerTest.getGame().getCurrentIslands().getIslands().get(island).currentStudents.add(s6);
        controllerTest.getGame().getCurrentIslands().getIslands().get(island).currentStudents.add(s7);

        if(!controllerTest.getGame().getCurrentIslands().getIslands().get(island).motherNature)
            controllerTest.getGame().getCurrentIslands().getIslands().get(island).updateMotherNature();

        controllerTest.getCharacterController().pickCard(controllerTest.getGame(), CharacterName.TRUFFLE_HUNTER, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0));
        testCard.effect(controllerTest.getGame(), 0, island, null, Col.BLUE);





        //Checks if GREY has won the island and if the eliminated students have been re-added on it
        EffectTestsUtility.checksAfterInfluenceCalculation(controllerTest.getGame(), 0, island);
        assertEquals(3, (int) controllerTest.getGame().getCurrentIslands().getIslands().get(island).currentStudents.stream().filter(Student -> Student.getColor() == Col.BLUE).count());

    }
}
