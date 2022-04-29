package controller;

import controller.MainController;
import model.CurrentGameState;
import model.boards.token.*;
import model.cards.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class CharacterControllerTest {

    MainController controllerTest = new MainController(2, true);

    /** Creates two teams of one player each.
     Runs the standard Setup procedure.
     Assigns a sufficient number of coins to the players.
     */
    public void setupTest()
    {
        controllerTest.AddPlayer(0, "jack", 8, Wizard.LORD );
        controllerTest.AddPlayer(1, "fede", 8, Wizard.DRUID);
        controllerTest.Setup();
        controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).updateCoins(5);
        controllerTest.getGame().getCurrentTeams().get(1).getPlayers().get(0).updateCoins(4);
    }

    /** Checks if the decks have been manipulated correctly
     after the effect call.
     * @param testCard  the character card whose functionalities are being tested
     */
    public void verifyDecks(CharacterCard testCard)
    {
        assertEquals(1, controllerTest.getGame().getCurrentCharacterDeck().getDeck().size());
        assertEquals(0, controllerTest.getGame().getCurrentActiveCharacterCard().size());
        assertEquals(testCard.getIdCard(), controllerTest.getGame().getCurrentCharacterDeck().getCard(0).getIdCard());
    }

    /** Specializes the setup by removing all character cards
     currently in the deck and adding the character card whose
     effect is the object of the test.
     * @param testCard  the character card whose functionalities are being tested
     */
    public void setDecks(CharacterCard testCard)
    {
        controllerTest.getGame().getCurrentCharacterDeck().getDeck().subList(0, 3).clear();
        controllerTest.getGame().getCurrentCharacterDeck().getDeck().add(testCard);
    }

    /** Prepares a basic island configuration that can be eventually further specialized.
     The island to modify is chosen by generating a random number from 0 to 11.
     To standardize the process of testing, the island in question has its student removed, if present.
     * @return the position to the island prepared to be a test environment
     */
    public int basicIslandSetup()
    {
        int island = (int) ((Math.random()*11));
        if(controllerTest.getGame().getCurrentIslands().getIslands().get(island).currentStudents.size() == 1)
            controllerTest.getGame().getCurrentIslands().getIslands().get(island).currentStudents.remove(0);

        //Grants the control of 3 professors to the first team, 2 to the other

        controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).getSchool().updateProfessorsTable(0, true);   //green
        controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).getSchool().updateProfessorsTable(1, true);   //red
        controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).getSchool().updateProfessorsTable(2, true);   //yellow


        controllerTest.getGame().getCurrentTeams().get(1).getPlayers().get(0).getSchool().updateProfessorsTable(3, true);   //pink
        controllerTest.getGame().getCurrentTeams().get(1).getPlayers().get(0).getSchool().updateProfessorsTable(4, true);   //blue

        controllerTest.getGame().getCurrentTeams().get(0).updateProfessors();
        controllerTest.getGame().getCurrentTeams().get(1).updateProfessors();

        //Checks if the  assignment of professors has been done correctly

        assertEquals(3, controllerTest.getGame().getCurrentTeams().get(0).getControlledProfessors().size());
        assertEquals(0, controllerTest.getGame().getCurrentTeams().get(0).getControlledProfessors().get(0).ordinal());
        assertEquals(1, controllerTest.getGame().getCurrentTeams().get(0).getControlledProfessors().get(1).ordinal());
        assertEquals(2, controllerTest.getGame().getCurrentTeams().get(0).getControlledProfessors().get(2).ordinal());

        assertEquals(2, controllerTest.getGame().getCurrentTeams().get(1).getControlledProfessors().size());
        assertEquals(3, controllerTest.getGame().getCurrentTeams().get(1).getControlledProfessors().get(0).ordinal());
        assertEquals(4, controllerTest.getGame().getCurrentTeams().get(1).getControlledProfessors().get(1).ordinal());

        //Creates one student for each colour; after it places them on the chosen island

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

        //returns a reference to the island itself to be further used

        return island;
    }

    /** Number of standardized checks to assess if the SolveEverything function did
     the calculation of influence and the subsequent actions properly.
     Two different outcomes are explored.
     * @param winningTeam  the index of the team that, according to the card effect, should win the island after the influence calculation
     * @param island  index of the prepared island
     */
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
    /** Checks if the function pickCard, in charge of moving a card from the CharDeck
     to the ActiveCharDeck and modifying the economy of the game, is working properly.
     */
    public void testPickCard() {

        //Does a basic setup and checks if the desired number of coins has been correctly assigned
        setupTest();
        assertEquals(6, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).getCoinAmount());
        assertEquals(5, controllerTest.getGame().getCurrentTeams().get(1).getPlayers().get(0).getCoinAmount());

        //Saves the reference to the card and its initial cost
        CharacterCard card = controllerTest.getGame().getCurrentCharacterDeck().getCard(0);
        int baseInitialCost = card.getBaseCost();

        //picks the card
        controllerTest.getCharacterController().pickCard(controllerTest.getGame(), 0, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0));

        //Initially it checks if the decks have been manipulated correctly
        assertEquals(2, controllerTest.getGame().getCurrentCharacterDeck().getDeck().size());
        assertEquals(card.getIdCard(), controllerTest.getGame().getCurrentActiveCharacterCard().get(0).getIdCard());
        assertEquals(1, controllerTest.getGame().getCurrentActiveCharacterCard().size());

        //Then it checks if the card values and the economy have been updated correctly
        assertEquals(1, controllerTest.getGame().getCurrentActiveCharacterCard().get(0).getUses());
        assertEquals(baseInitialCost + 1, controllerTest.getGame().getCurrentActiveCharacterCard().get(0).getCurrentCost());
        assertEquals(6 - baseInitialCost, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).getCoinAmount());
        assertEquals((baseInitialCost - 1) + 18, controllerTest.getGame().getBankBalance());
    }

    /*
    From now on the effects of the various characters are tested.
    Comments will be provided to explain steps that do not use the
    standard procedures defined in the fist part of this test class.
    The various effects are explained in the CharacterController class.
     */

    @Test
    /** Priest effect test */
    public void testEffect()
    {
        setupTest();
        Priest testCard = new Priest();
        setDecks(testCard);
        int island = (int) ((Math.random()*11));

        /*fills the Priest card with 4 students chosen randomly from the pouch,
        then saves the colour of the first one in the arraylist for testing purposes*/
        for (int i = 0; i < 4; i++)
            testCard.updateStudents(controllerTest.getGame().getCurrentPouch());
        Col color = testCard.getStudents().get(0).getColor();

        controllerTest.getCharacterController().pickCard(controllerTest.getGame(), 0, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0));
        CharacterController.effect(testCard, controllerTest.getGame(), 0, island);
        verifyDecks(testCard);

        //Checks if a new student has been added to the card after the effect
        assertEquals(4, testCard.getStudents().size());

        /*First of all, it verifies that the last student added to the island is of the same colour of that
        taken from the card.
        Then, it checks if the correct number of students are on the island at the end of the effect  */
        assertEquals(color, controllerTest.getGame().getCurrentIslands().getIslands().get(island).currentStudents.get(controllerTest.getGame().getCurrentIslands().getIslands().get(island).currentStudents.size()-1).getColor());
        if(controllerTest.getGame().getCurrentIslands().getIslands().get(island).currentStudents.size() == 1)
            assertEquals(1, controllerTest.getGame().getCurrentIslands().getIslands().get(island).currentStudents.size());
        else
            assertEquals(2, controllerTest.getGame().getCurrentIslands().getIslands().get(island).currentStudents.size());
    }

    @Test
    /** Princess effect test */
    public void testTestEffect()
    {
        setupTest();
        Princess testCard = new Princess();
        setDecks(testCard);

        /*fills the Princess card with 4 students chosen randomly from the pouch,
        then saves the colour of the first one in the arraylist and the ordinal
        of the color of the aforementioned student for testing purposes*/
        for (int i = 0; i < 4; i++)
            testCard.updateStudents(controllerTest.getGame().getCurrentPouch());
        Col color = testCard.getStudents().get(0).getColor();
        int index = color.ordinal();

        controllerTest.getCharacterController().pickCard(controllerTest.getGame(), 0, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0));
        CharacterController.effect(testCard, controllerTest.getGame(), 0, "fede");
        verifyDecks(testCard);

        //Checks if a new student has been added to the card after the effect
        assertEquals(4, testCard.getStudents().size());

        /*First, it checks that the dining room of the school of the player that didn't call the effect remained untouched.
        Secondly, it assures that the dining room of the active player has been manipulated correctly (the int at the index
        corresponding with the ordinal of the selected colour has been updated: the rest remains untouched).  */
        for(int i=0; i<5; i++)
            assertEquals(0,controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).getSchool().getDiningRoom()[i]);
        for(int i =0; i<5; i++)
            if(i!=index)
                assertEquals(0,controllerTest.getGame().getCurrentTeams().get(1).getPlayers().get(0).getSchool().getDiningRoom()[i]);
            else
                assertEquals(1,controllerTest.getGame().getCurrentTeams().get(1).getPlayers().get(0).getSchool().getDiningRoom()[i]);
    }

    @Test
    /** Herald effect test */
    public void testTestEffect1()
    {
        setupTest();
        Herald testCard = new Herald();
        setDecks(testCard);
        int island = basicIslandSetup();

        controllerTest.getCharacterController().pickCard(controllerTest.getGame(), 0, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0));
        CharacterController.effect(testCard, controllerTest.getGame(), island);
        verifyDecks(testCard);

        /*After the influence calculation, the first team should control the selected island: fisrt it checks if
        the teamInfluence structure in the island has the correct values, then it checks that the winning team
        is chosen correctly. In the end it ensures that motherNature has been set at false on the island   */
        assertEquals(3, controllerTest.getGame().getCurrentIslands().getIslands().get(island).teamInfluence[0]);
        assertEquals(2, controllerTest.getGame().getCurrentIslands().getIslands().get(island).teamInfluence[1]);
        checksAfterInfluenceCalculation(0, island);
        assertFalse(controllerTest.getGame().getCurrentIslands().getIslands().get(island).motherNature);
    }

    @Test
    /** Postman effect test */
    public void testTestEffect2()
    {
        setupTest();
        Postman testCard = new Postman();
        setDecks(testCard);

        //It gives starting values to maxMovementMovement to both players
        controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).updateMaxMotherMovement(6);
        controllerTest.getGame().getCurrentTeams().get(1).getPlayers().get(0).updateMaxMotherMovement(2);

        controllerTest.getCharacterController().pickCard(controllerTest.getGame(), 0, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0));
        CharacterController.effect(testCard, controllerTest.getGame(), "jack");
        verifyDecks(testCard);

        //Checks if the player who has called the effect has had its maxMotherMovement increased by 2
        assertEquals(8, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).getMaxMotherMovement());
        assertEquals(2, controllerTest.getGame().getCurrentTeams().get(1).getPlayers().get(0).getMaxMotherMovement());

    }

    @Test
    /** Grandma Weed effect test */
    public void testTestEffect3()
    {
        setupTest();
        GrandmaWeed testCard = new GrandmaWeed();
        setDecks(testCard);
        int island = (int) ((Math.random()*11));

        controllerTest.getCharacterController().pickCard(controllerTest.getGame(), 0, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0));
        CharacterController.effect(testCard, controllerTest.getGame(), island);
        verifyDecks(testCard);

        /*It checks if the noEntry has been placed on the chosen island and if the counter on
        GWeed has been updated accordingly */
        assertTrue(controllerTest.getGame().getCurrentIslands().getIslands().get(island).getNoEntry());
        assertEquals(3, testCard.getNoEntry());

    }

    @Test
    /** Centaur effect test */
    public void testTestEffect4()
    {
        setupTest();
        Centaur testCard = new Centaur();
        setDecks(testCard);
        int island = basicIslandSetup();

        /*Creates a situation in which the chosen island is already controlled by the GREY team;
        it adds a tower to the island and decrements the tower count in the GREY player's school.
        It updates the ownership on the island and the controlledIslands of the GREY team.
        Sets MotherNature to true, in order to simulate the situation in which the effect of this card is played */
        if(!controllerTest.getGame().getCurrentIslands().getIslands().get(island).motherNature)
            controllerTest.getGame().getCurrentIslands().getIslands().get(island).updateMotherNature();
        controllerTest.getGame().getCurrentIslands().getIslands().get(island).updateTeamInfluence(controllerTest.getGame().getCurrentTeams());
        controllerTest.getGame().getCurrentIslands().getIslands().get(island).calculateOwnership();
        controllerTest.getGame().getCurrentIslands().getIslands().get(island).towerNumber = 1;
        controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0).getSchool().updateTowerCount(-1);
        controllerTest.getGame().getCurrentTeams().get(0).updateControlledIslands(1);

        /*Creates two more students of the colours corresponding to the controlled professors of the WHITE team;
        considering the presence of the tower, we have a situation on parity on the island.
        With the activation of the effect, the tower should be eliminated, and so the WHITE team should win*/
        Student s6 = new Student(Col.BLUE);
        Student s7 = new Student(Col.PINK);
        controllerTest.getGame().getCurrentIslands().getIslands().get(island).currentStudents.add(s6);
        controllerTest.getGame().getCurrentIslands().getIslands().get(island).currentStudents.add(s7);

        controllerTest.getCharacterController().pickCard(controllerTest.getGame(), 0, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0));
        CharacterController.effect(testCard, controllerTest.getGame(), island);
        verifyDecks(testCard);

        //Checks if the influence calculation has been done correctly and if the WHITE team won as expected
        assertEquals(3, controllerTest.getGame().getCurrentIslands().getIslands().get(island).teamInfluence[0]);
        assertEquals(4, controllerTest.getGame().getCurrentIslands().getIslands().get(island).teamInfluence[1]);
        checksAfterInfluenceCalculation(1, island);
    }

    @Test
    /** Truffle Hunter effect test */
    public void testTestEffect5()
    {
        setupTest();
        TruffleHunter testCard = new TruffleHunter();
        setDecks(testCard);
        int island = basicIslandSetup();

        /*Adds two students of a colour controlled by WHITE on the island: White has the higher influence now,
        but with the activation of the effect, the colour BLUE will not count towards the influence calculation,
        and GREY should secure the victory   */
        Student s6 = new Student(Col.BLUE);
        Student s7 = new Student(Col.BLUE);
        controllerTest.getGame().getCurrentIslands().getIslands().get(island).currentStudents.add(s6);
        controllerTest.getGame().getCurrentIslands().getIslands().get(island).currentStudents.add(s7);

        if(!controllerTest.getGame().getCurrentIslands().getIslands().get(island).motherNature)
            controllerTest.getGame().getCurrentIslands().getIslands().get(island).updateMotherNature();

        controllerTest.getCharacterController().pickCard(controllerTest.getGame(), 0, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0));
        CharacterController.effect(testCard, controllerTest.getGame(), Col.BLUE, island);
        verifyDecks(testCard);

        //Checks if GREY has won the island and if the eliminated students have been re-added on it
        checksAfterInfluenceCalculation(0, island);
        assertEquals(3, (int) controllerTest.getGame().getCurrentIslands().getIslands().get(island).currentStudents.stream().filter(Student -> Student.getColor() == Col.BLUE).count());

    }

    @Test
    /** Knight effect test */
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

        /*Checks if the winning team is the WHITE team, after the effect has boosted its influence on the island
        (without the effect, the GREY team would have won).
         Checks if the WHITE influence has been re-updated accordingly at the end of the influence calculation*/
        checksAfterInfluenceCalculation(1, island);
        assertEquals(2, controllerTest.getGame().getCurrentIslands().getIslands().get(island).teamInfluence[1]);

    }

    @Test
    public void deckManagement()
    {
        setupTest();

        //Picks all three cards in the CharDeck
        for(int i=0; i<3; i++)
            controllerTest.getCharacterController().pickCard(controllerTest.getGame(), 0, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0));

        CharacterCard card = controllerTest.getGame().getCurrentActiveCharacterCard().get(0);
        controllerTest.getCharacterController().deckManagement(controllerTest.getGame().getCurrentActiveCharacterCard().get(0), controllerTest.getGame());

        //Checks if the sizes of the decks have been handled correctly (2 cards in the Active, on in the Char)
        assertEquals(2, controllerTest.getGame().getCurrentActiveCharacterCard().size());
        assertEquals(1, controllerTest.getGame().getCurrentCharacterDeck().getDeck().size());

        //Checks if the correct Card has been acted upon by comparing the IDs of the cards in the Active and in the CharDeck.
        for(int i=0; i<controllerTest.getGame().getCurrentActiveCharacterCard().size(); i++)
            assertNotEquals(card.getIdCard(), controllerTest.getGame().getCurrentActiveCharacterCard().get(i).getIdCard());
        assertEquals(card.getIdCard(), controllerTest.getGame().getCurrentCharacterDeck().getDeck().get(0).getIdCard());
    }

    @Test
    public void testGetPickedCard ()
    {
        setupTest();

        //Creates a dummy card
        Knight testCard = new Knight();
        setDecks(testCard);

        controllerTest.getCharacterController().pickCard(controllerTest.getGame(), 0, controllerTest.getGame().getCurrentTeams().get(0).getPlayers().get(0));
        CharacterCard card = controllerTest.getCharacterController().getPickedCard();

        //Verifies if the method returns the right card by comparing the correct ID with the dummy's ID
        assertEquals(8, card.getIdCard());
    }
}