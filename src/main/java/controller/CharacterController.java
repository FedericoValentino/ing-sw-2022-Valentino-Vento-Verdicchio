package controller;

import model.CurrentGameState;
import model.Player;
import model.Team;
import model.boards.token.Col;
import model.boards.token.ColTow;
import model.boards.token.Student;
import model.cards.*;


import java.util.ArrayList;

public class CharacterController
{
    private CharacterCard pickedCard;

    /*
    Takes the selected card from the CharDeck and puts it in the ActiveDeck;
    handles the economy related to this action
     */
    public void pickCard(CurrentGameState game, int position, Player player)
    {
        //drawCard updates the cost and the uses of the selected card
        pickedCard = game.getCurrentCharacterDeck().drawCard(position);
        game.getCurrentActiveCharacterCard().add(pickedCard);

        /*Updates player balance and bank balance using the currentCost-1 of the card,
        which is how much the card cost when it was played  */
        int gain = pickedCard.getCurrentCost() - 1;
        game.updateBankBalance(player, gain);
    }

    /*
    Finds the card that has been used in the ActiveCharDeck, removes it from there,
    and places it, with updated values, in the CharacterDeck.
     */
    public static void deckManagement(CharacterCard card, CurrentGameState game)
    {
        for(int i=0; i<game.getCurrentActiveCharacterCard().size(); i++)
        {
            if(card.getIdCard() == game.getCurrentActiveCharacterCard().get(i).getIdCard())
                game.getCurrentActiveCharacterCard().remove(i);
        }
        game.getCurrentCharacterDeck().getDeck().add(card);
    }

    //From here on we have the Characters' effects: every one of them calls deckManagement at the end

    /*
    Takes a Student from the Priest card residing at the desired position; places it on the
    chosen island. Then, refills the Priest card with another student from the pouch.
     */
    public static void effect(Priest card, CurrentGameState game, int studentPosition, int chosenIsland)
    {
        game.getCurrentIslands().placeToken(card.getStudent(studentPosition), chosenIsland);
        card.updateStudents(game.getCurrentPouch());
        deckManagement(card, game);
    }

    /*
    Takes a student from the card at the desired position, saves its color; then finds the active player
    and obtains its school, placing the student in the dining room (updating the dining room structure using the student's color)
     */
    public static void effect(Princess card, CurrentGameState game, int studentPosition, String currentPlayer)
    {
        Col color = card.getStudent(studentPosition).getColor();
        MainController.findPlayerByName(game, currentPlayer).getSchool().placeInDiningRoom(color);
        card.updateStudents(game.getCurrentPouch());
        deckManagement(card, game);
    }

    //Resolves the influence calculation on the island as if MN has ended there her movement
    public static void effect(Herald card, CurrentGameState game, int island)
    {
        if(game.getCurrentIslands().getIslands().get(island).getMotherNature() == false)
            game.getCurrentIslands().getIslands().get(island).updateMotherNature();
        ActionController.solveEverything(game, island);
        game.getCurrentIslands().getIslands().get(island).updateMotherNature();
        deckManagement(card, game);
    }

    //Finds the active player using its name, increases its maxMotherMovement by 2
    public static void effect(Postman card, CurrentGameState game, String currentPlayer)
    {
        MainController.findPlayerByName(game, currentPlayer).updateMaxMotherMovement(2);
        deckManagement(card, game);
    }

    //Sets the noEntry field on the desired island to true; decrements the noEntry field on the card by 1
    public static void effect(GrandmaWeed card, CurrentGameState game, int island)
    {
        game.getCurrentIslands().getIslands().get(island).updateNoEntry();
        card.updateNoEntry(-1);
        deckManagement(card, game);
    }

    //Removes the towers from the desired island before calculating the influence
    public static void effect(Centaur card, CurrentGameState game, int island)
    {
        game.getCurrentIslands().getIslands().get(island).towerNumber = 0;
        ActionController.solveEverything(game, island);
        deckManagement(card, game);
    }

    //Ignores a color of student in the influence calculation
    public static void effect(TruffleHunter card, CurrentGameState game, Col color, int island)
    {
        /*Uses this for cycle to remove the students of the selected color from the island: uses a
        counter to save how many students were removed  */
        int cont = 0;
        for(int i=0; i<game.getCurrentIslands().getIslands().get(island).currentStudents.size(); i++)
        {
            if(game.getCurrentIslands().getIslands().get(island).currentStudents.get(i).getColor() == color)
            {
                game.getCurrentIslands().getIslands().get(island).currentStudents.remove(i);
                cont++;
            }
        }

        ActionController.solveEverything(game, island);

        //After the influence calculations, it adds to the island as many students of the selected color as the number of the counter
        for(int i=0; i<cont; i++)
        {
            game.getCurrentIslands().getIslands().get(island).currentStudents.add(new Student(color));
        }
        deckManagement(card, game);
    }

    /*
    Adds 2 extra influence to the Active team during the influence calculation. Since the standard method
    solveEverything updates the teamInfluence internally, it is needed to manually update the teams influence,
    add 2 extra influence to the desired team, calculate ownership, update towers and calling the idManagement.
    In the end, the boosted influence is set to its previous value.
     */
    public static void effect(Knight card, CurrentGameState game, int island, int team)
    {
        ColTow previousOwner = game.getCurrentIslands().getIslands().get(island).getOwnership();                                                                                                           //chiama l'altro metodo (overloading) per aumentare di 2
        game.getCurrentIslands().getIslands().get(island).updateTeamInfluence(game.getCurrentTeams());
        game.getCurrentIslands().getIslands().get(island).updateTeamInfluence(2, team);
        game.getCurrentIslands().getIslands().get(island).calculateOwnership();
        ColTow currentOwner = game.getCurrentIslands().getIslands().get(island).getOwnership();
        if(previousOwner != currentOwner)
        {
            for(Team t: game.getCurrentTeams())
            {
                for(Player p: t.getPlayers())
                {
                    if(p.isTowerOwner() && t.getColor() == previousOwner)
                    {
                        p.getSchool().updateTowerCount(game.getCurrentIslands().getIslands().get(island).getTowerNumber());
                        t.updateControlledIslands(-1);
                    }
                    if(p.isTowerOwner() && t.getColor() == currentOwner)
                    {
                        p.getSchool().updateTowerCount(-(game.getCurrentIslands().getIslands().get(island).getTowerNumber()));
                        t.updateControlledIslands(1);
                    }
                }
            }
        }
        game.getCurrentIslands().idManagement();
        game.getCurrentIslands().getIslands().get(island).updateTeamInfluence(-2, team);
        deckManagement(card, game);
    }


    public CharacterCard getPickedCard()
    {
        return pickedCard;
    }
}
