package model;

import model.boards.token.ColTow;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CurrentGameStateTest {
    CurrentGameState cg1=new CurrentGameState(1,false);
    CurrentGameState cg2=new CurrentGameState(1,true);

    @Test
    public void testUpdateBankBalance()
    {
        Player p1=new Player("ci", ColTow.WHITE,8,"ca",false);
        Player p2=new Player("asd", ColTow.BLACK,8,"caadsds",false);

        int i=p1.getCoinAmount();
        assertEquals(cg1.getBankBalance(),0);
        cg1.updateBankBalance(p1);
        assertEquals(cg1.getBankBalance(),0);
    }
}
