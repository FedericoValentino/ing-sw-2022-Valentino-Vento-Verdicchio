package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.checksandbalances.Checks;
import it.polimi.ingsw.model.boards.token.Student;
import it.polimi.ingsw.model.CurrentGameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Team;
import it.polimi.ingsw.model.cards.characters.Centaur;
import it.polimi.ingsw.model.cards.CharacterCard;
import it.polimi.ingsw.model.cards.characters.Knight;
import it.polimi.ingsw.model.cards.characters.TruffleHunter;

import java.util.ArrayList;


public class ActionController
{
    private String currentPlayer;
    private int movableStudents;

    public ActionController(String Player, int playerNumber)
    {
        this.currentPlayer = Player;
        if(playerNumber == 2 || playerNumber == 4)
            this.movableStudents = 3;
        else if(playerNumber == 3)
            this.movableStudents = 4;
    }

    /** Method placeStudentToIsland places a student from the currentPlayer's school to a specified island
     * @param entrancePos  the index identifying the position of the player's student into the school entrance
     * @param islandId  the id of the island onto which the student must be placed
     * @param game  an instance of the game
     * @param name  the player's name, needed to access his school
     */
    public void placeStudentToIsland(int entrancePos, int islandId, CurrentGameState game, String name)
    {
        Student s = MainController.findPlayerByName(game, name).getSchool().extractStudent(entrancePos);
        game.getCurrentIslands().getIslands().get(islandId).addStudent(s);
        this.movableStudents--;
        game.getCurrentTurnState().updateActionMoves();
    }

    /** Method placeStudentToDiningRoom places the selected student from the entrance to the dining room, and checks
     whether this action has granted the player the control of a professor
     * @param entrancePos  the index identifying the position of the player's student into the school entrance
     * @param game  an instance of the game
     * @param name  the player's name, needed to access his school
     */
    public void placeStudentToDiningRoom(int entrancePos, CurrentGameState game, String name)
    {

        Player p = MainController.findPlayerByName(game, name);
        Student s;

        s = p.getSchool().extractStudent(entrancePos);
        p.getSchool().placeInDiningRoom(s.getColor());
        game.updateBankBalance(p, 0);

        game.giveProfessors(false);
        for(Team t: game.getCurrentTeams())
        {
            t.updateProfessors();
        }
        this.movableStudents--;
        game.getCurrentTurnState().updateActionMoves();
    }

    public void drawFromClouds(int cloudIndex, CurrentGameState game, String name)
    {
        MainController.findPlayerByName(game, name).getSchool().placeToken(game.getCurrentClouds()[cloudIndex].EmptyCloud());
        game.getCurrentTurnState().updateActionMoves();
    }


    /** Method moveMN moves mother nature for the specified amount
     * @param amount  amount of spaces mother nature has to move through
     * @param game  an instance of the game
     */
    public void MoveMN(int amount, CurrentGameState game)
    {
        game.getCurrentIslands().getIslands().get(game.getCurrentMotherNature().getPosition()).updateMotherNature();
        game.getCurrentMotherNature().move(amount, game.getCurrentIslands().getTotalGroups()-1);
        game.getCurrentIslands().getIslands().get(game.getCurrentMotherNature().getPosition()).updateMotherNature();
        if(!Checks.checkForInfluenceCharacter(game))
        {
            game.solveEverything(game.getCurrentMotherNature().getPosition());
            Checks.checkWinnerForTowers(game);
            Checks.checkWinnerForIsland(game);
        }
        else
        {
            CharacterCard card = game.getCurrentActiveCharacterCard().get(0);
            ArrayList<Integer> position = new ArrayList<>();
            position.add(game.getCurrentMotherNature().getPosition());
            if(card instanceof Knight)
            {
                card.effect(game, null, position, currentPlayer, null);
                CharacterController.deckManagement(game);
            }
            else if(card instanceof TruffleHunter)
            {
                card.effect(game, null, position, null, ((TruffleHunter) card).getChosenColor());
                CharacterController.deckManagement(game);
            }
            else if(card instanceof Centaur)
            {
                card.effect(game, null, position, null, null);
                CharacterController.deckManagement(game);
            }
        }
        game.getCurrentTurnState().updateActionMoves();
    }



    public void setCurrentPlayer(String name)
    {
        currentPlayer = name;
    }

    public int getMovableStudents() {
        return movableStudents;
    }
}
