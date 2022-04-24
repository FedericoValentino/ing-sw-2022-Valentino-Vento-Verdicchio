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

    public void placeStudentToIsland(int entrancepos, int islandId, CurrentGameState game, String name)
    {
        Student s = null;
        for(Team t : game.getCurrentTeams())
        {
            for(Player p: t.getPlayers())
            {
                if(p.getNome().equals(name))
                {
                    s = p.getSchool().extractStudent(entrancepos);
                }
            }
        }
        game.getCurrentIslands().getIslands().get(islandId).addStudent(s);
    }

    public void placeStudentToDiningRoom(int entrancepos, CurrentGameState game, String name)
    {
        Student s;
        for(Team t : game.getCurrentTeams())
        {
            for(Player p: t.getPlayers())
            {
                if(p.getNome().equals(name))
                {
                    s = p.getSchool().extractStudent(entrancepos);
                    p.getSchool().placeInDiningRoom(s.getColor());
                }
            }
        }
        game.giveProfessors();
        for(Team t: game.getCurrentTeams())
        {
            t.updateProfessors();
        }
    }

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

    /*returns true if there are any influence cards*/
    private boolean checkForCharacter(CurrentGameState game)
    {
        for(CharacterCard c: game.getCurrentActiveCharacterCard())
        {
            if(c instanceof Knight)
            {
                CharacterController.effect((Knight)c, game, game.getCurrentMotherNature().getPosition(), MainController.getPlayerColor(game, currentPlayer).ordinal());
            }
            else if(c instanceof TruffleHunter)
            {
                CharacterController.effect((TruffleHunter) c, game, ((TruffleHunter) c).getChosenColor(), game.getCurrentMotherNature().getPosition());
            }
            else if(c instanceof Centaur)
            {
                CharacterController.effect((Centaur) c, game, game.getCurrentMotherNature().getPosition());
            }
        }
        return false;
    }

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

    public void setCurrentPlayer(String name)
    {
        currentPlayer = name;
    }
}
