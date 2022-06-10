package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.CurrentGameState;
import it.polimi.ingsw.model.boards.token.Col;
import it.polimi.ingsw.model.boards.token.ColTow;
import it.polimi.ingsw.model.boards.token.Student;
import it.polimi.ingsw.model.cards.CharacterCard;

import static org.junit.Assert.assertEquals;

public class EffectTestsUtility
{

    /** Checks if the decks have been manipulated correctly
     after the effect call.
     * @param testCard  the character card whose functionalities are being tested
     */
    public static void verifyDecks(CharacterCard testCard, CurrentGameState game)
    {
        assertEquals(1, game.getCurrentCharacterDeck().getDeck().size());
        assertEquals(0, game.getCurrentActiveCharacterCard().size());
        assertEquals(testCard.getCharacterName(), game.getCurrentCharacterDeck().getCard(0).getCharacterName());
    }

    /** Specializes the setup by removing all character cards
     currently in the deck and adding the character card whose
     effect is the object of the test.
     * @param testCard  the character card whose functionalities are being tested
     */
    public static void setDecks(CharacterCard testCard, CurrentGameState game)
    {
        game.getCurrentCharacterDeck().getDeck().clear();
        game.getCurrentCharacterDeck().getDeck().add(testCard);
    }

    /** Prepares a basic island configuration that can be eventually further specialized.
     The island to modify is chosen by generating a random number from 0 to 11.
     To standardize the process of testing, the island in question has its student removed, if present.
     * @return the position to the island prepared to be a test environment
     */
    public static int basicIslandSetup(CurrentGameState game)
    {
        int island = (int) ((Math.random()*11));
        if(game.getCurrentIslands().getIslands().get(island).currentStudents.size() == 1)
            game.getCurrentIslands().getIslands().get(island).currentStudents.remove(0);

        //Grants the control of 3 professors to the first team, 2 to the other

        game.getCurrentTeams().get(0).getPlayers().get(0).getSchool().updateProfessorsTable(0, true);   //green
        game.getCurrentTeams().get(0).getPlayers().get(0).getSchool().updateProfessorsTable(1, true);   //red
        game.getCurrentTeams().get(0).getPlayers().get(0).getSchool().updateProfessorsTable(2, true);   //yellow


        game.getCurrentTeams().get(1).getPlayers().get(0).getSchool().updateProfessorsTable(3, true);   //pink
        game.getCurrentTeams().get(1).getPlayers().get(0).getSchool().updateProfessorsTable(4, true);   //blue

        game.getCurrentTeams().get(0).updateProfessors();
        game.getCurrentTeams().get(1).updateProfessors();

        //Checks if the  assignment of professors has been done correctly

        assertEquals(3, game.getCurrentTeams().get(0).getControlledProfessors().size());
        assertEquals(0, game.getCurrentTeams().get(0).getControlledProfessors().get(0).ordinal());
        assertEquals(1, game.getCurrentTeams().get(0).getControlledProfessors().get(1).ordinal());
        assertEquals(2, game.getCurrentTeams().get(0).getControlledProfessors().get(2).ordinal());

        assertEquals(2, game.getCurrentTeams().get(1).getControlledProfessors().size());
        assertEquals(3, game.getCurrentTeams().get(1).getControlledProfessors().get(0).ordinal());
        assertEquals(4, game.getCurrentTeams().get(1).getControlledProfessors().get(1).ordinal());

        //Creates one student for each colour; after it places them on the chosen island

        Student s1 = new Student(Col.GREEN);
        Student s2 = new Student(Col.RED);
        Student s3 = new Student(Col.YELLOW);
        Student s4 = new Student(Col.PINK);
        Student s5 = new Student(Col.BLUE);

        game.getCurrentIslands().getIslands().get(island).currentStudents.add(s1);
        game.getCurrentIslands().getIslands().get(island).currentStudents.add(s2);
        game.getCurrentIslands().getIslands().get(island).currentStudents.add(s3);
        game.getCurrentIslands().getIslands().get(island).currentStudents.add(s4);
        game.getCurrentIslands().getIslands().get(island).currentStudents.add(s5);

        //returns a reference to the island itself to be further used

        return island;
    }

    /** Number of standardized checks to assess if the SolveEverything function did
     the calculation of influence and the subsequent actions properly.
     Two different outcomes are explored.
     * @param winningTeam  the index of the team that, according to the card effect, should win the island after the influence calculation
     * @param island  index of the prepared island
     */
    public static void checksAfterInfluenceCalculation(CurrentGameState game, int winningTeam, int island)
    {
        if(winningTeam == 0)
        {
            assertEquals(ColTow.GREY, game.getCurrentIslands().getIslands().get(island).getOwnership());
            assertEquals(1, game.getCurrentIslands().getIslands().get(island).towerNumber);
            assertEquals(8, game.getCurrentTeams().get(1).getPlayers().get(0).getSchool().getTowerCount());
            assertEquals(0, game.getCurrentTeams().get(1).getControlledIslands());
            assertEquals(7, game.getCurrentTeams().get(0).getPlayers().get(0).getSchool().getTowerCount());
            assertEquals(1, game.getCurrentTeams().get(0).getControlledIslands());
        }
        else if(winningTeam == 1)
        {
            assertEquals(ColTow.WHITE, game.getCurrentIslands().getIslands().get(island).getOwnership());
            assertEquals(1, game.getCurrentIslands().getIslands().get(island).towerNumber);
            assertEquals(7, game.getCurrentTeams().get(1).getPlayers().get(0).getSchool().getTowerCount());
            assertEquals(8, game.getCurrentTeams().get(0).getPlayers().get(0).getSchool().getTowerCount());
            assertEquals(1, game.getCurrentTeams().get(1).getControlledIslands());
            assertEquals(0, game.getCurrentTeams().get(0).getControlledIslands());
        }
    }

}
