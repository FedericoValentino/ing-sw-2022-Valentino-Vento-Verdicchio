package model;

import model.boards.token.ColTow;
import model.boards.token.Wizard;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class TeamTest {
    Team t=new Team(ColTow.GREY);
    Player p=new Player("giaco",ColTow.GREY,3, Wizard.SENSEI,false);
    Player p2=new Player("giaco",ColTow.GREY,3,Wizard.LORD,false);
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
