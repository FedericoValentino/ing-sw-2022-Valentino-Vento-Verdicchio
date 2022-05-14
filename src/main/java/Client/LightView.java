package Client;

import Server.Answers.ActionAnswers.ViewMessage;
import model.Team;
import model.boards.Cloud;
import model.boards.Islands;
import model.boards.token.MotherNature;
import model.cards.CharacterDeck;

import java.util.ArrayList;

public class LightView
{
    private CharacterDeck CharacterDeckView;
    private Islands IslandsView;
    private ArrayList<Team> TeamsView;
    private MotherNature MNView;
    private Cloud[] CloudsView;
    public void parse(ViewMessage view)
    {
        String json = view.getJsonView().substring(1, view.getJsonView().length()-1);
        System.out.println(json);

    }
}
