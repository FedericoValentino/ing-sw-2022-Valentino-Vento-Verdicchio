package controller;

import model.CurrentGameState;

public class PlanningController
{


    public void drawStudentForClouds(CurrentGameState game, int position)                                   //mode è un intero che ci dice il numero di team
    {                                                                                                       //se ci sono due team (2 o 4 giocatori), si prendono 3
        for(int i=0; i<=game.getCurrentTeams().size(); i++)                                                 //studenti, se ci sono 3 team, 4, e si mettono sulla
        {                                                                                                   //nuvola selezionata
            game.getCurrentClouds()[position].placeToken(game.getCurrentPouch().extractStudent());
        }
    }

    public void drawAssistantCard(CurrentGameState game, String currentPlayer, int cardPosition)                    //Cerca il player nel giusto team, gioca choose assistant card
    {
        MainController.findPlayerByName(game, currentPlayer).chooseAssistantCard(cardPosition);
    }
}
