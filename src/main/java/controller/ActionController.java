package controller;

import model.CurrentGameState;
import model.Player;
import model.Team;
import model.boards.token.ColTow;
import model.boards.token.Student;
import model.cards.Centaur;
import model.cards.CharacterCard;
import model.cards.Knight;
import model.cards.TruffleHunter;
import controller.CharacterController.*;

public class ActionController
{
    private String currentPlayer;

    public ActionController(String Player)
    {
        this.currentPlayer = Player;
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

        game.giveProfessors();
        for(Team t: game.getCurrentTeams())
        {
            t.updateProfessors();
        }
    }

    public void drawFromClouds(int cloudIndex, CurrentGameState game, String name)
    {
        MainController.findPlayerByName(game, name).getSchool().placeToken(game.getCurrentClouds()[cloudIndex].EmptyCloud());
    }


    public boolean isCloudEmpty(int cloudIndex, CurrentGameState game)
    {
        if(game.getCurrentClouds()[cloudIndex].isEmpty())
        {
            return true;
        }
        return false;
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
        if(!checkForCharacter(game))
        {
            solveEverything(game, game.getCurrentMotherNature().getPosition());
        }


    }

    /** Returns true if there are any influence related character cards present in the Active Characters Deck
     * @param game  an instance of hte game
     * @return whether influence based card are currently active
     */
    private boolean checkForCharacter(CurrentGameState game)
    {
        for(CharacterCard c: game.getCurrentActiveCharacterCard())
        {
            if(c instanceof Knight)
            {
                CharacterController.effect((Knight)c, game, game.getCurrentMotherNature().getPosition(), currentPlayer);
            }
            else if(c instanceof TruffleHunter)
            {
                CharacterController.effect((TruffleHunter) c, game, game.getCurrentMotherNature().getPosition(), ((TruffleHunter) c).getChosenColor());
            }
            else if(c instanceof Centaur)
            {
                CharacterController.effect((Centaur) c, game, game.getCurrentMotherNature().getPosition());
            }
        }
        return false;
    }

    /** Method solveEverything is responsible for the influence calculation on the desired island, and
     handles the eventual exchange of towers between players' schools and the island
     * @param game  an instance of the game
     * @param pos  the island on which the influence calculation has to be called
     */
    public static void solveEverything(CurrentGameState game, int pos)
    {
        ColTow previousOwner = game.getCurrentIslands().getIslands().get(pos).getOwnership();
        game.getCurrentIslands().getIslands().get(pos).updateTeamInfluence(game.getCurrentTeams());
        game.getCurrentIslands().getIslands().get(pos).calculateOwnership();
        ColTow currentOwner = game.getCurrentIslands().getIslands().get(pos).getOwnership();
        if(previousOwner != currentOwner)
        {
            for(Team t: game.getCurrentTeams())
            {
                for(Player p: t.getPlayers())
                {
                    if(p.isTowerOwner() && t.getColor() == previousOwner)
                    {
                        p.getSchool().updateTowerCount(game.getCurrentIslands().getIslands().get(pos).getTowerNumber());
                        t.updateControlledIslands(-1);
                    }
                    if(p.isTowerOwner() && t.getColor() == currentOwner)
                    {
                        p.getSchool().updateTowerCount(-(game.getCurrentIslands().getIslands().get(pos).getTowerNumber()));
                        t.updateControlledIslands(1);
                    }
                }
            }
        }
        game.getCurrentIslands().idManagement();
    }

    public boolean possibleMNmove(int amount, CurrentGameState game)
    {
        if(amount <= MainController.findPlayerByName(game, currentPlayer).getMaxMotherMovement())
        {
            return true;
        }
        return false;
    }

    public void setCurrentPlayer(String name)
    {
        currentPlayer = name;
    }
}
