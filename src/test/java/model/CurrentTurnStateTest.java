package model;
import model.boards.token.Col;
import model.boards.token.ColTow;
import model.boards.token.GamePhase;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class CurrentTurnStateTest {
    CurrentTurnState curTurnObj = new CurrentTurnState();
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
