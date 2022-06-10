package it.polimi.ingsw.model;

import it.polimi.ingsw.model.CurrentGameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Team;
import it.polimi.ingsw.model.boards.token.ColTow;
import it.polimi.ingsw.model.boards.token.Wizard;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class TeamTest {
    CurrentGameState dummy;
    Team t=new Team(ColTow.GREY, dummy);
    Player p=new Player("giaco",ColTow.GREY,3, Wizard.SENSEI,false, dummy);
    Player p2=new Player("giaco",ColTow.GREY,3,Wizard.LORD,false, dummy);
    @Test
    public void testAddPlayer()
    {

        assertEquals(t.getPlayers().size(),0);
        t.addPlayer(p);
        assertEquals(t.getPlayers().get(0),p);
    }
    //molte linee di codice testate in island

    @Test
    public void testGetColor() {
        Assert.assertTrue(this.t.getColor() instanceof ColTow);
    }
}
