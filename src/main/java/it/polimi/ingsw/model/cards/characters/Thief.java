package it.polimi.ingsw.model.cards.characters;

import it.polimi.ingsw.model.CurrentGameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Team;
import it.polimi.ingsw.model.boards.token.enumerations.CharacterName;
import it.polimi.ingsw.model.boards.token.enumerations.Col;
import it.polimi.ingsw.model.boards.token.Student;
import it.polimi.ingsw.model.cards.CharacterCard;
//y
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Thief allows the player to steal up to three students of the selected color from every player's dining room
 */
public class Thief extends CharacterCard implements Serializable {

    /**
     * Class constructor
     */
    public Thief() {
        super();
        super.baseCost = 3;
        super.currentCost = super.baseCost;
        super.name = CharacterName.THIEF;
    }

    /**
     * Runs through each player's dining room and removes a maximum of three students per selected color. After this
     * it recalculates the checkpoints, readjusting them, and runs a check on the professors
     * @param game an instance of the game, needed to operate at a high level of access
     * @param firstChoice not used here
     * @param secondChoice not used here
     * @param currentPlayer not used here
     * @param color the selected color
     */
    @Override
    public void effect(CurrentGameState game, ArrayList<Integer> firstChoice, ArrayList<Integer> secondChoice, String currentPlayer, Col color) {
        ArrayList<Student> toBag = new ArrayList<>();
        for (Team team : game.getCurrentTeams()) {
            for (Player player : team.getPlayers()) {
                for (int i = 0; i < 3; i++) {
                    if (player.getSchool().getDiningRoom()[color.ordinal()] != 0) {
                        toBag.add(new Student(player.getSchool().removeFromDiningRoom(color.ordinal())));
                    }
                }
                EffectsUtilities.adjustCheckpoints(player.getSchool());
            }
        }
        game.getCurrentPouch().refillBag(toBag);
        game.giveProfessors(false);
        game.notify(game.modelToJson());
    }



}


