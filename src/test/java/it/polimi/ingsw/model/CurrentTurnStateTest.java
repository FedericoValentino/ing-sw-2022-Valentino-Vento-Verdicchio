package it.polimi.ingsw.model;
import it.polimi.ingsw.model.CurrentGameState;
import it.polimi.ingsw.model.CurrentTurnState;
import it.polimi.ingsw.model.boards.token.ColTow;
import it.polimi.ingsw.model.boards.token.GamePhase;
import org.junit.Test;

import static org.junit.Assert.*;

public class CurrentTurnStateTest {
    CurrentGameState cg1 = new CurrentGameState(2, true);
    CurrentTurnState curTurnObj = new CurrentTurnState(cg1);
    ColTow cW=ColTow.GREY;


    @Test
    public void TestGetTurn() {

        curTurnObj.getTurn();
        //assertNull(currentTurnStateObj.getIsGameEnded());
        //curTurnObj.getIsActionPhase();
        cW=curTurnObj.getWinningTeam();
    }

    @Test
    public void assertFalse() {
        if (curTurnObj.getIsGameEnded() == false)
        { //System.out.println("Ok, falso");
        }
        else
        {//System.out.println("Errore, condizione vera");
        }
    }

    @Test
    public void TestUpdateTurn() {
        int t = curTurnObj.getTurn();
        curTurnObj.UpdateTurn();
        t++;
        assertTrue(t == curTurnObj.getTurn());
    }


    @Test
    public void TestUpdateANDGetWinner() {
        //da vedere se effettivamente il costruttore di Team non prende nulla


        assertNull(curTurnObj.getWinningTeam());
        curTurnObj.updateWinner(cW);
        assertTrue(curTurnObj.getWinningTeam().equals(cW));
    }

    @Test
    public void TestUpdateGamePhase()
    {
        curTurnObj.updateGamePhase(GamePhase.PLANNING);
        assertEquals(GamePhase.PLANNING, curTurnObj.getGamePhase());
    }
}
