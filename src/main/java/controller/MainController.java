package controller;

import model.CurrentGameState;
import model.Player;
import model.Team;
import model.boards.token.ColTow;
import model.boards.token.Wizard;

import java.util.ArrayList;
import java.util.Optional;

public class MainController
{
    private CurrentGameState game;
    private String currentPlayer;
    private ActionController actionController;
    private PlanningController planningController;
    private CharacterController characterController;
    private boolean expertGame;
    private ArrayList<Wizard> availableWizards;
    private int readyPlayers;
    private int players;


    public MainController(int n, boolean expert)
    {

        this.game = new CurrentGameState(n, expert);
        this.currentPlayer = null;
        this.actionController = new ActionController(currentPlayer);
        this.planningController = new PlanningController();
        this.characterController = new CharacterController();
        this.expertGame = expert;
        for(int i = 0; i < 4; i++)
        {
            this.availableWizards.add(Wizard.values()[i]);
        }
        this.readyPlayers = 0;
        this.players = n;
    }

    /*
     * Method AddPlayer adds a player to the specified team
     */
    public void AddPlayer(int team, String name, int towers, Wizard Wizard)
    {
        if(game.getCurrentTeams().get(team).getPlayers().size() == 0)
        {
            this.game.getCurrentTeams().get(team).addPlayer(new Player(name, ColTow.values()[team], towers, Wizard, this.expertGame));
        }
        else
        {
            this.game.getCurrentTeams().get(team).addPlayer(new Player(name, ColTow.values()[team], 0, Wizard, this.expertGame));
        }
    }

    /*
     * Method Setup handles the game setup phase: placing students in the islands and placing students in the players entrances
     */
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

    public boolean lastPlayer()
    {
        if(game.getCurrentTurnState().getTurnOrder().size() == 0)
        {
            return true;
        }
        return false;
    }

    /*
     * Method determineNextPlayer extracts the next player to play from the CurrentTurnState HashMap
     */
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

    /*
     * Method updateTurnState updates the HashMap with the new turn order
     */
    public void updateTurnState()
    {
        game.updateTurnState();
    }

    /*
     * Method findPlayerByName return the Player with the given name
     */
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
    /*
     * Method getPlayerColor returns given a playerName his team color
     */
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

    public void readyPlayer()
    {
        readyPlayers++;
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

    public ArrayList<Wizard> getAvailableWizards() {
        return availableWizards;
    }

    public int getReadyPlayers() {
        return readyPlayers;
    }

    public int getPlayers() {
        return players;
    }
}
