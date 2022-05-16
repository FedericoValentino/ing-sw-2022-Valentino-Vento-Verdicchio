package controller;

import model.CurrentGameState;
import model.Player;
import model.Team;
import model.boards.token.ColTow;
import model.boards.token.GamePhase;
import model.boards.token.Wizard;
import model.cards.CharacterCard;
import model.cards.CharacterDeck;
import model.cards.Priest;
import model.cards.Princess;

import java.util.ArrayList;
import java.util.Optional;

public class MainController
{
    private CurrentGameState game;
    private String currentPlayer;
    private ActionController actionController;
    private PlanningController planningController;
    private CharacterController characterController;
    private Checks checks;
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
        this.checks = new Checks();
        this.expertGame = expert;
        this.availableWizards = new ArrayList<>();
        for(int i = 0; i < 4; i++)
        {
            this.availableWizards.add(Wizard.values()[i]);
        }
        this.readyPlayers = 0;
        this.players = n;
    }


    /** Method AddPlayer adds a player to the specified team
     * @param team  the team that will contain the added player
     * @param name  the player's name
     * @param towers  the number of towers assigned to the player
     * @param Wizard  the wizard chosen by the player
     */
    public void AddPlayer(int team, String name, int towers, Wizard Wizard)
    {
        if(game.getCurrentTeams().get(team).getPlayers().size() == 0)
        {
            this.game.getCurrentTeams().get(team).addPlayer(new Player(name, ColTow.values()[team], towers, Wizard, this.expertGame, game));
        }
        else
        {
            this.game.getCurrentTeams().get(team).addPlayer(new Player(name, ColTow.values()[team], 0, Wizard, this.expertGame, game));
        }
    }

    /**
     Method Setup handles the game setup phase: placing students in the islands and placing students in the players entrances
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


    /**
     Method determineNextPlayer extracts the next player to play from the CurrentTurnState HashMap
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


    /**
     Method updateTurnState updates the HashMap with the new turn order
     */
    public void updateTurnState()
    {
        game.updateTurnState();
    }

    /** Method findPlayerByName returns the Player with the given name
     * @param game  an instance of the game
     * @param player  the name of the player object to seek
     * @return p, the correct player object
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

    /** Method getPlayerColor returns the team color given a playerName
     * @param game  an instance of the game
     * @param player  the player belonging to the team that defines its color
     * @return the color of the team to which the player is assigned
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


    public void updateGamePhase(GamePhase phase)
    {
        if(game.getCurrentTurnState().getGamePhase() == GamePhase.ACTION && phase.equals(GamePhase.PLANNING))
        {
            for(Team t : game.getCurrentTeams())
            {
                for(Player p: t.getPlayers())
                {
                    p.Discard();
                }
            }
        }
        game.getCurrentTurnState().updateGamePhase(phase);
    }


    public void resetReady()
    {
        readyPlayers = 0;
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

    public Checks getChecks(){return checks;}

    public boolean isExpertGame() {
        return expertGame;
    }

    public synchronized ArrayList<Wizard> getAvailableWizards() {
        return availableWizards;
    }

    public int getReadyPlayers() {
        return readyPlayers;
    }

    public int getPlayers() {
        return players;
    }
}
