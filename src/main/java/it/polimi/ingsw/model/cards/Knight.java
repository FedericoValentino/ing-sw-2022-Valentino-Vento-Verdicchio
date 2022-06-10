package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.controller.MainController;
import it.polimi.ingsw.model.CurrentGameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Team;
import it.polimi.ingsw.model.boards.token.CharacterName;
import it.polimi.ingsw.model.boards.token.Col;
import it.polimi.ingsw.model.boards.token.ColTow;

import java.io.Serializable;
import java.util.Arrays;

public class Knight extends CharacterCard implements Serializable {

    /** Class constructor */
    public Knight()
    {
        super();
        super.baseCost = 2;
        super.currentCost = super.baseCost;
        super.name = CharacterName.KNIGHT;
    }



    /** Adds 2 extra influence to the Active team during the influence calculation. Since the standard method
     solveEverything updates the teamInfluence internally, it is needed to manually update the teams influence,
     add 2 extra influence to the desired team, calculate ownership, update towers and calling the idManagement.
     In the end, the boosted influence is set to its previous value.
     * @param game  an instance of the game
     * @param chosenIsland  the island on which the influence calculation must occur
     * @param currentPlayer  the player requesting the effect to be played
     */
    @Override
    public void effect(CurrentGameState game, int studentPosition, int chosenIsland, String currentPlayer, Col color)
    {
        ColTow previousOwner = game.getCurrentIslands().getIslands().get(chosenIsland).getOwnership();                                                                                                           //chiama l'altro metodo (overloading) per aumentare di 2
        game.getCurrentIslands().getIslands().get(chosenIsland).updateTeamInfluence(game.getCurrentTeams());
        game.getCurrentIslands().getIslands().get(chosenIsland).updateTeamInfluence(2, MainController.getPlayerColor(game, currentPlayer).ordinal());
        game.getCurrentIslands().getIslands().get(chosenIsland).calculateOwnership();
        ColTow currentOwner = game.getCurrentIslands().getIslands().get(chosenIsland).getOwnership();
        if(previousOwner != currentOwner)
        {
            for(Team t: game.getCurrentTeams())
            {
                for(Player p: t.getPlayers())
                {
                    if(p.isTowerOwner() && t.getColor() == previousOwner)
                    {
                        p.getSchool().updateTowerCount(game.getCurrentIslands().getIslands().get(chosenIsland).getTowerNumber());
                        t.updateControlledIslands(-1);
                    }
                    if(p.isTowerOwner() && t.getColor() == currentOwner)
                    {
                        p.getSchool().updateTowerCount(-(game.getCurrentIslands().getIslands().get(chosenIsland).getTowerNumber()));
                        t.updateControlledIslands(1);
                    }
                }
            }
        }
        game.getCurrentIslands().idManagement();
        game.getCurrentIslands().getIslands().get(chosenIsland).updateTeamInfluence(-2, MainController.getPlayerColor(game, currentPlayer).ordinal());
    }

    @Override
    public String[] description() {
        String[] knightDescription = new String[7];
        Arrays.fill(knightDescription, "");
        knightDescription[0] += "To arms! This is one of the most offensive character you can even dream to summon. Use the overwhelming strength ";
        knightDescription[1] += "and might of this companion to gain an important advantage in conquering an island: while this card is active ";
        knightDescription[2] += "you and your team gain 2 more influence points to aid in the crusade against the enemy!";
        knightDescription[3] += "Use this card to aid in the conquest of the most well defended outposts!";
        return knightDescription;
    }

}
