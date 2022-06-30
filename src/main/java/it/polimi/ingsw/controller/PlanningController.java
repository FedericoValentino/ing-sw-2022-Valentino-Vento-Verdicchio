package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.checksandbalances.Checks;
import it.polimi.ingsw.model.CurrentGameState;

/**
 * This controller is tasked of hosting the methods used to manage the game in the Planning Phase
 */
public class PlanningController
{
    /**
     * It draws students from the pouch and places them on the desired cloud: through the
     * size of the list of teams, it fills the cloud with 3 students in case of matches with 2 or 4 player,
     * and with 4 students in matches with 3 players
     * @param game  an instance of the game
     * @param position  the index identifying the chosen cloud in the clouds arraylist
     */
    public void drawStudentForClouds(CurrentGameState game, int position)
    {
        for(int i=0; i<=game.getCurrentTeams().size() && Checks.isPouchAvailable(game); i++)
        {
            game.getCurrentClouds()[position].placeToken(game.getCurrentPouch().extractStudent());
        }
        //TODO readd ! for later
        if(Checks.isPouchAvailable(game))
        {
            game.getCurrentTurnState().setLastTurn();
        }
        game.getCurrentTurnState().updatePlanningMoves();
    }

    /**
     * It finds the currentPlayer by its name, and it plays the desired AssistantCard, identified in
     * its deck by cardPosition
     * @param game  an instance of the game
     * @param currentPlayer  the player choosing the card
     * @param cardPosition  the index identifying the correct card in the deck
     */
    public void drawAssistantCard(CurrentGameState game, String currentPlayer, int cardPosition)
    {
        MainController.findPlayerByName(game, currentPlayer).chooseAssistantCard(cardPosition);
    }
}
