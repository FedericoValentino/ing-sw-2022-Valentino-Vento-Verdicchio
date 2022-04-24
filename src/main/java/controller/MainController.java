package controller;

import model.CurrentGameState;
import model.Player;
import model.Team;
import model.boards.token.ColTow;

import java.util.Optional;

public class MainController
{
    private CurrentGameState game;
    private String currentPlayer;
    private ActionController actionController;
    private PlanningController planningController;
    private CharacterController characterController;
    private boolean expertGame;


    public MainController(int n, boolean expert)
    {
        this.game = new CurrentGameState(n, expert);
        this.currentPlayer = null;
        this.actionController = new ActionController(currentPlayer);
        this.planningController = new PlanningController();
        this.characterController = new CharacterController();
        this.expertGame = expert;
    }

    public void AddPlayer(int team, String name, int towers, String Wizard)
    {
        this.game.getCurrentTeams().get(team).addPlayer(new Player(name, ColTow.values()[team], towers, Wizard, this.expertGame));
    }

    public void Setup()
    {
        int MNpos = game.getCurrentMotherNature().getPosition();
        game.getCurrentIslands().getIslands().get(MNpos).updateMotherNature();
        for(int i = MNpos+1; i < 12 + MNpos; i++)
        {
            if(i != MNpos + 6)
            {
                if(i >= 12)
                {
                    game.getCurrentIslands().getIslands().get(i - 12).addStudent(game.getCurrentPouch().extractStudent());
                }
                else
                {
                    game.getCurrentIslands().getIslands().get(i).addStudent(game.getCurrentPouch().extractStudent());
                }

            }
        }
        game.getCurrentPouch().updateSetup(false);
        for(Team t: game.getCurrentTeams())
        {
            for(Player p: t.getPlayers())
            {
                for(int i = 0; i < 7; i++)
                {
                    p.getSchool().placeToken(game.getCurrentPouch().extractStudent());
                }
            }
        }

    }

    public void determineNextPlayer()
    {
        Optional<String> nextPlayer = game.getCurrentTurnState().getTurnOrder().keySet().stream().findFirst();
        if(nextPlayer.isPresent())
        {
            currentPlayer = nextPlayer.get();
            actionController.setCurrentPlayer(currentPlayer);
        }
        game.getCurrentTurnState().getTurnOrder().remove(currentPlayer);
        actionController.setCurrentPlayer(currentPlayer);
    }


    public void updateTurnState()
    {
        game.updateTurnState();
    }

    public static Player findPlayerByName(CurrentGameState game, String player)
    {
        for(Team t: game.getCurrentTeams())
        {
            for(Player p: t.getPlayers())
            {
                if(p.getNome().equals(player))
                {
                    return p;
                }
            }
        }
        return null;
    }

    public static ColTow getPlayerColor(CurrentGameState game, String player)
    {
        for(Team t: game.getCurrentTeams())
        {
            for(Player p: t.getPlayers())
            {
                if(p.getNome().equals(player))
                {
                    return t.getColor();
                }
            }
        }
        return null;
    }

    public CurrentGameState getGame()
    {
        return game;
    }
    public CharacterController getCharacterController() {return characterController;}

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public ActionController getActionController() {
        return actionController;
    }

    public PlanningController getPlanningController() {
        return planningController;
    }

    public boolean isExpertGame() {
        return expertGame;
    }
}
