package model;

import model.boards.token.ColTow;

public class CurrentTurnState
{
    //HashMap che indica l'ordine di gioco <String> <int> | nome giocatore -> turno
    private int turn;
    private int actions;
    private boolean gameEnded;
    private ColTow WinningTeam;
    public boolean actionPhase;
    public boolean lastTurn;

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

    public boolean getIsActionPhase() {
        return actionPhase;
    }
    public ColTow getWinningTeam() {
        return WinningTeam;
    }
    public boolean getIsGameEnded() {
        return gameEnded;
    }
    public int getActions() {
        return actions;
    }
    public int getTurn() {
        return turn;
    }
}
