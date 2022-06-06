package controller;

import model.CurrentGameState;
import model.Player;
import model.Team;
import model.boards.token.Student;


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
     * @param entrancepos  the index identifying the position of the player's student into the school entrance
     * @param islandId  the id of the island onto which the student must be placed
     * @param game  an instance of the game
     * @param name  the player's name, needed to access his school
     */
    public void placeStudentToIsland(int entrancepos, int islandId, CurrentGameState game, String name)
    {
        Student s = MainController.findPlayerByName(game, name).getSchool().extractStudent(entrancepos);
        game.getCurrentIslands().getIslands().get(islandId).addStudent(s);
        this.movableStudents--;
        game.getCurrentTurnState().UpdateActionMoves();
    }

    /** Method placeStudentToDiningRoom places the selected student from the entrance to the dining room, and checks
     whether this action has granted the player the control of a professor
     * @param entrancepos  the index identifying the position of the player's student into the school entrance
     * @param game  an instance of the game
     * @param name  the player's name, needed to access his school
     */
    public void placeStudentToDiningRoom(int entrancepos, CurrentGameState game, String name)
    {

        Player p = MainController.findPlayerByName(game, name);
        Student s;

        s = p.getSchool().extractStudent(entrancepos);
        p.getSchool().placeInDiningRoom(s.getColor());
        game.updateBankBalance(p, 0);

        game.giveProfessors();
        for(Team t: game.getCurrentTeams())
        {
            t.updateProfessors();
        }
        this.movableStudents--;
        game.getCurrentTurnState().UpdateActionMoves();
    }

    public void drawFromClouds(int cloudIndex, CurrentGameState game, String name)
    {
        MainController.findPlayerByName(game, name).getSchool().placeToken(game.getCurrentClouds()[cloudIndex].EmptyCloud());
        game.getCurrentTurnState().UpdateActionMoves();
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
        if(Checks.checkForInfluenceCharacter(game, currentPlayer))
        {
            game.solveEverything(game.getCurrentMotherNature().getPosition());
            Checks.checkWinnerForTowers(game);
            Checks.checkWinnerForIsland(game);
        }
        game.getCurrentTurnState().UpdateActionMoves();
    }



    public void setCurrentPlayer(String name)
    {
        currentPlayer = name;
    }

    public int getMovableStudents() {
        return movableStudents;
    }
}
