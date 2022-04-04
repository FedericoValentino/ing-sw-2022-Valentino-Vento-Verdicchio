package model;

import model.boards.token.ColTow;
import org.junit.Test;
import static org.junit.Assert.*;

public class TeamTest {
    Team t=new Team(ColTow.GREY);
    Player p=new Player("giaco",ColTow.GREY,3,"mago2",false);
    Player p2=new Player("giaco",ColTow.GREY,3,"mago2",false);
    @Test
    public void testAddPlayer()
    {

        assertEquals(t.getPlayers().size(),0);
        t.addPlayer(p);
        assertEquals(t.getPlayers().get(0),p);
    }
    //molte linee di codice testate in island

    @Test
    public void testGetColor()
    {
        assertTrue(t.getColor() instanceof ColTow);
    }
}
