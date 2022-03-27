package model;

import model.boards.token.ColTow;

public class CurrentTurnState
{
    private int turn;
    private int actions;
    private boolean gameEnded;
    private ColTow WinningTeam;
    public boolean actionPhase;

    public CurrentTurnState()
    {
        this.turn = 0;
        this.actions = 0;
        this.gameEnded = false;
        this.WinningTeam = null;
        this.actionPhase = false;
    }

    public void UpdateTurn()
    {
        turn++;
    }
    public void UpdateActions()
    {
        actions++;
    }
    public void updateWinner(ColTow t)
    {
        gameEnded = true;
        WinningTeam = t;
    }
}
