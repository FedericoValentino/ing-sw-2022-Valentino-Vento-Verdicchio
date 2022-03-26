package model;

import model.boards.Islands;
import model.boards.Pouch;


public class CurrentGameState {
    private Islands currentIslands;
    private Character currentCharacterDeck;
    private Pouch currentPouch;
    private Team[] currentTeams;
    private int turn;
    private int actions;
    private boolean gameEnded;
    private Team winningTeam;
    private boolean actionPhase;
    private int bankBalance;

    public CurrentGameState(){
        //va implementato tutto o solo in parte?
    }

    /*
    public Team getTeam(int idTeamIns)
    {

    }
 */
    public int getTurn(){return turn;}
    public boolean getGameEnded(){return gameEnded;}
    public Team getWinningTeam(){return winningTeam;}
    public boolean getActionPhase(){return  actionPhase;}
    public int getBankBalance(){return bankBalance;}
    public Islands getCurrentIslands(){return currentIslands;}
    public Character getCurrentCharacterDeck(){return currentCharacterDeck;}
    public Pouch getCurrentPouch() {return currentPouch;}
}