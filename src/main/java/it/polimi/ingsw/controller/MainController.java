package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.CurrentGameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Team;
import it.polimi.ingsw.model.boards.token.enumerations.ColTow;
import it.polimi.ingsw.model.boards.token.enumerations.GamePhase;
import it.polimi.ingsw.model.boards.token.enumerations.Wizard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

/**
 * The main class of the controller. It instantiates the other controllers and the currentGameState (and everything along
 * with that). Additionally, holds different methods useful to manage some standard actions done during the game, and other
 * general methods that can aid the other controller classes in their job.
 * The main philosophy behind all the controllers is to use the simple logic methods already present in the model to form more
 * complex instructions that require a higher level of access. The Controller rarely modifies the model by direct action, but
 * always through using the methods already defined in the model, in such a fashion that permits the correct flow of the game.
 */
public class MainController
{
    private CurrentGameState game;
    private String currentPlayer;
    private ActionController actionController;
    private PlanningController planningController;
    private CharacterController characterController;
    private boolean expertGame;
    private ArrayList<Wizard> availableWizards;
    private int[] availableTeams;
    private int readyPlayers;
    private int players;


    /**
     * Class constructor. It instantiates the other controllers, the currentGameState, and other structure useful during
     * the setup phase (such as availableTeams and availableWizards)
     * @param playerNumber number of players in the game
     * @param expert player choice about the expert mode
     */
    public MainController(int playerNumber, boolean expert)
    {

        this.game = new CurrentGameState(playerNumber, expert);
        this.currentPlayer = null;
        this.actionController = new ActionController(null);
        this.planningController = new PlanningController();
        this.characterController = new CharacterController();
        this.expertGame = expert;
        this.availableWizards = new ArrayList<>();
        this.availableWizards.addAll(Arrays.asList(Wizard.values()).subList(0, 4));
        this.availableTeams = new int[3];
        if(playerNumber == 2)
        {
            availableTeams[0] = 1;
            availableTeams[1] = 1;
        }
        else if(playerNumber == 3)
        {
            availableTeams[0] = 1;
            availableTeams[1] = 1;
            availableTeams[2] = 1;
        }
        else if(playerNumber == 4)
        {
            availableTeams[0] = 2;
            availableTeams[1] = 2;
        }
        this.readyPlayers = 0;
        this.players = playerNumber;
    }


    /**
     * Method addPlayer adds a player to the specified team
     * @param team  the team that will contain the added player
     * @param name  the player's name
     * @param towers  the number of towers assigned to the player
     * @param wizard  the wizard chosen by the player
     */
    public void addPlayer(int team, String name, int towers, Wizard wizard)
    {
        if(game.getCurrentTeams().get(team).getPlayers().size() == 0)
        {
            if(players == 3)
            {
                this.game.getCurrentTeams().get(team).addPlayer(new Player(name, ColTow.values()[team], 6, wizard, this.expertGame));
            }
            else
            {
                this.game.getCurrentTeams().get(team).addPlayer(new Player(name, ColTow.values()[team], towers, wizard, this.expertGame));
            }
        }
        else
        {
            this.game.getCurrentTeams().get(team).addPlayer(new Player(name, ColTow.values()[team], 0, wizard, this.expertGame));
        }
    }

    /**
     * Method setup handles the game setup phase: placing students in the islands and placing students in the players entrances
     */
    public void setup()
    {
        int MotherPosition = game.getCurrentMotherNature().getPosition();
        game.getCurrentIslands().getIslands().get(MotherPosition).updateMotherNature();
        for(int i = MotherPosition+1; i < 12 + MotherPosition; i++)
        {
            if(i != MotherPosition + 6)
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

        int studentsToGive = 7;
        if(players == 3)
        {
            studentsToGive = 9;
        }
        for(Team team: game.getCurrentTeams())
        {
            for(Player player: team.getPlayers())
            {
                for(int i = 0; i < studentsToGive; i++)
                {
                    player.getSchool().placeToken(game.getCurrentPouch().extractStudent());
                }
            }
        }
        game.notify(game.modelToJson());
    }


    /**
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
        game.getCurrentTurnState().setCurrentPlayer(currentPlayer);
        CharacterController.deckManagement(game);
        game.getCurrentTurnState().resetMoves();
    }


    /**
     * Method updateTurnState updates the HashMap with the new turn order
     */
    public void updateTurnState()
    {
        game.updateTurnState();
    }

    /**
     * Method findPlayerByName returns the Player with the given name
     * @param game  an instance of the game
     * @param playerName  the name of the player object to seek
     * @return player, the correct player object
     */
    public static Player findPlayerByName(CurrentGameState game, String playerName)
    {
        for(Team team: game.getCurrentTeams())
        {
            for(Player player: team.getPlayers())
            {
                if(player.getName().equals(playerName))
                {
                    return player;
                }
            }
        }
        return null;
    }

    /**
     * Method getPlayerColor returns the team color given a playerName
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
                if(p.getName().equals(player))
                {
                    return t.getColor();
                }
            }
        }
        return null;
    }


    /**
     * Changes the Game Phase in currentTurnState to the specified one
     * @param phase the phase we want to transition into
     */
    public void updateGamePhase(GamePhase phase)
    {
        if(game.getCurrentTurnState().getGamePhase() == GamePhase.ACTION && phase.equals(GamePhase.PLANNING))
        {
            for(Team t : game.getCurrentTeams())
            {
                for(Player p: t.getPlayers())
                {
                    p.discard();
                }
            }
        }
        game.getCurrentTurnState().updateGamePhase(phase);
    }

    /**
     * Upon reaching certain conditions (the depletion of assistant cards for example), it sets the lastTurn boolean
     * in currentTurnState to true, prompting the last turn of the game
     */
    public void lastTurn()
    {
        game.getCurrentTurnState().setLastTurn();
    }

    /**
     * Updates the winning team by checking which team controls most islands
     */
    public void selectWinner()
    {
        game.getCurrentTurnState().updateWinner(game.getCurrentIslands().getMaxCol(game.getCurrentTeams()));
    }

    /**
     * Updates the available slots in the specified teams upon player entry in that team
     * @param teamChoice the team to update
     */
    public void removeSlotFromTeam(int teamChoice)
    {
        availableTeams[teamChoice]--;
    }

    /**
     * reset ready players to the initial value
     */
    public void resetReady()
    {
        readyPlayers = 0;
    }

    /**
     * Updates the ready players value upon command of player readiness
     */
    public void readyPlayer()
    {
        readyPlayers++;
    }

    /**
     * Method emergencyUpdatePlanning updates the planning moves to skip the cloud selection part when the pouch is empty
     */
    //TODO add to tests
    public void emergencyUpdatePlanning()
    {
        game.getCurrentTurnState().updatePlanningMoves();
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

    public synchronized ArrayList<Wizard> getAvailableWizards() {
        return availableWizards;
    }

    public int getReadyPlayers() {
        return readyPlayers;
    }

    public int getPlayers() {
        return players;
    }

    public synchronized int[] getAvailableTeams() {
        return availableTeams;
    }


}
