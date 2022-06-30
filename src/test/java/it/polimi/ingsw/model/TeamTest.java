package it.polimi.ingsw.model;

import it.polimi.ingsw.model.boards.token.enumerations.ColTow;
import it.polimi.ingsw.model.boards.token.enumerations.Wizard;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class TeamTest {
    Team t=new Team(ColTow.GREY);
    Player p=new Player("giaco",ColTow.GREY,3, Wizard.SENSEI,false);
    /**
     * This method tests if the get and add player works properly (test the 0 and 1 player in the team)
     */
    @Test
    public void testAddPlayer()
    {
        assertEquals(t.getPlayers().size(),0);
        t.addPlayer(p);
        assertEquals(t.getPlayers().get(0),p);
    }

    @Test
    public void testGetColor() {
        Assert.assertTrue(this.t.getColor() instanceof ColTow);
    }
}
