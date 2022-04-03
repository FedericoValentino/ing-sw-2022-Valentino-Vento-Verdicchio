package controller;

import model.CurrentGameState;

public class PlanningController
{


    public void drawStudentForClouds(CurrentGameState game, int position, int mode)
    {
        for(int i=0; i<mode; i++)
        {
            game.getCurrentClouds()[position].placeToken(game.getCurrentPouch().extractStudent());
        }
    }

    public void drawAssistantCard(CurrentGameState game, String currentPlayer, int card)
    {
        int teamIndex = 0;
        int playerIndex = 0;
        for(int i=0; i<game.getCurrentTeams().size(); i++)
        {
            for(int j=0; j< game.getCurrentTeams().get(i).getPlayers().size(); j++)
            {
                if(currentPlayer.equals(game.getCurrentTeams().get(i).getPlayers().get(j).getNome()))
                    playerIndex = j;
                    teamIndex = i;
            }
        }
        game.getCurrentTeams().get(teamIndex).getPlayers().get(playerIndex).chooseAssistantCard(card);
    }
}
