package it.polimi.ingsw.model.cards.characters;

import it.polimi.ingsw.model.CurrentGameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Team;
import it.polimi.ingsw.model.boards.token.enumerations.CharacterName;
import it.polimi.ingsw.model.boards.token.enumerations.Col;
import it.polimi.ingsw.model.boards.token.Student;
import it.polimi.ingsw.model.cards.CharacterCard;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Minstrel allows the player to exchange up to two student between their entrance and dining room
 */
public class Minstrel extends CharacterCard implements Serializable {

    /**
     * Class constructor
     */
    public Minstrel()
    {
        super();
        super.baseCost = 1;
        super.currentCost = super.baseCost;
        super.name = CharacterName.MINSTREL;
    }

    /**
     * It swaps the desired number of students between the entrance and the dining room of the current player. Runs
     * a check on the dining room checkpoints to readjust them, if necessary; same fot the professors
     * @param game an instance of the game, needed to operate at a high level of access
     * @param entranceStudentIndexes a list of the indexes of the entrance student to swap
     * @param colorOrdinals a list of color ordinals representing the dining room students to swap
     * @param currentPlayer the name of the currentPlayer, the one who played the card
     * @param color not used here
     */
    @Override
    public void effect(CurrentGameState game, ArrayList<Integer> entranceStudentIndexes, ArrayList<Integer> colorOrdinals, String currentPlayer, Col color)
    {
        Player player = EffectsUtilities.searchForCurrentPlayer(currentPlayer, game.getCurrentTeams());

        ArrayList<Student> toDining = new ArrayList<>();
        EffectsUtilities.swapStudents(toDining, entranceStudentIndexes, player.getSchool().getEntrance());

        for(Integer colorIndex: colorOrdinals)
        {
            Student student = new Student(player.getSchool().removeFromDiningRoom(colorIndex));
            player.getSchool().placeToken(student);
        }

        for(Student student : toDining)
            player.getSchool().placeInDiningRoom(student.getColor());

        EffectsUtilities.adjustCheckpoints(player.getSchool());
        game.giveProfessors(false);
        game.notify(game.modelToJson());
    }


}
