package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.CurrentGameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Team;
import it.polimi.ingsw.model.boards.School;
import it.polimi.ingsw.model.boards.token.CharacterName;
import it.polimi.ingsw.model.boards.token.Col;
import it.polimi.ingsw.model.boards.token.Student;
//y
import java.io.Serializable;
import java.util.ArrayList;

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


    @Override
    public void effect(CurrentGameState game, ArrayList<Integer> studentPosition, ArrayList<Integer> chosenIsland, String currentPlayer, Col color) {
        ArrayList<Student> toBag = new ArrayList<>();
        for (Team t : game.getCurrentTeams()) {
            for (Player p : t.getPlayers()) {
                for (int i = 0; i < 3; i++) {
                    if (p.getSchool().getDiningRoom()[color.ordinal()] != 0) {
                        toBag.add(new Student(p.getSchool().removeFromDiningRoom(color.ordinal())));
                    }
                }
                EffectsUtilities.adjustCheckpoints(p.getSchool());
            }
        }
        game.getCurrentPouch().refillBag(toBag);
    }



}


