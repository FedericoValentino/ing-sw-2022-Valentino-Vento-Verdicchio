package it.polimi.ingsw.model;
import it.polimi.ingsw.model.boards.token.enumerations.ColTow;
import it.polimi.ingsw.model.boards.token.enumerations.GamePhase;
import org.junit.Test;

import static org.junit.Assert.*;

public class CurrentTurnStateTest {
    CurrentGameState cg1 = new CurrentGameState(2, true);
    CurrentTurnState curTurnObj = new CurrentTurnState(cg1);
    ColTow cW=ColTow.GREY;


    @Test
    public void TestGetTurn() {

        curTurnObj.getTurn();
        cW=curTurnObj.getWinningTeam();
    }


    @Test
    public void TestUpdateTurn() {
        int t = curTurnObj.getTurn();
        curTurnObj.updateTurn();
        t++;
        assertEquals(t, curTurnObj.getTurn());
    }


    @Test
    public void TestUpdateANDGetWinner() {
        //da vedere se effettivamente il costruttore di Team non prende nulla


        assertNull(curTurnObj.getWinningTeam());
        curTurnObj.updateWinner(cW);
        assertEquals(curTurnObj.getWinningTeam(), cW);
    }

    @Test
    public void TestUpdateGamePhase()
    {
        curTurnObj.updateGamePhase(GamePhase.PLANNING);
        assertEquals(GamePhase.PLANNING, curTurnObj.getGamePhase());
    }

    @Test
    public void setLastTurn()
    {
        curTurnObj.setLastTurn();
        assertTrue(curTurnObj.getLastTurn());
    }
}
