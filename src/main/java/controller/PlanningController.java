package controller;

import model.CurrentGameState;

public class PlanningController
{
    /**
    It draws students from the pouch and places them on the desired cloud: through the
    CurrentTeams.size, it fills the cloud with 3 students in case of matches with 2 or 4 player,
    and with 4 students in matches with 3 players
     */
    public void drawStudentForClouds(CurrentGameState game, int position)
    {
        for(int i=0; i<=game.getCurrentTeams().size(); i++)
        {
            game.getCurrentClouds()[position].placeToken(game.getCurrentPouch().extractStudent());
        }
    }

    /**
    It finds the currentPlayer by its name and it plays the desired AssistantCard, identified in
    its deck by cardPosition
     */
    public void drawAssistantCard(CurrentGameState game, String currentPlayer, int cardPosition)
    {
        MainController.findPlayerByName(game, currentPlayer).chooseAssistantCard(cardPosition);
    }
}
