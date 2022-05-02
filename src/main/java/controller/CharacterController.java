package controller;

import model.CurrentGameState;
import model.Player;
import model.Team;
import model.boards.token.Col;
import model.boards.token.ColTow;
import model.boards.token.Student;
import model.cards.*;

public class CharacterController
{
    private CharacterCard pickedCard;


    /** Takes the selected card from the CharDeck and puts it in the ActiveDeck;
     handles the economy related to this action
     * @param game  an instance of the game
     * @param player   the player interacting with the card
     * @param position  the position of the chosen card in the deck
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


    /** Checks if the desired card can be picked, by comparing its ID with the cards in the CharacterDeck
     * @param game  an instance of the game
     * @param characterID  the ID of the desired card
     * @param player  the player responsible for the action
     * @return ture if the card is present, false if not
     */
    public static boolean isPickable(CurrentGameState game, int characterID, Player player)
    {
        for(int i=0; i<game.getCurrentCharacterDeck().getDeck().size(); i++)
            if(game.getCurrentCharacterDeck().getDeck().get(i).getIdCard() == characterID)
                return true;
        return false;
    }


    /** Checks if the effect of the desired card can be activated, by comparing the ID of the card with
     the cards into the CurrentActiveCharacterCard list
     * @param game  an instance of the game
     * @param characterID  the ID of the desired card
     * @return true if the card is present, false if not
     */
    public boolean isEffectPlayable(CurrentGameState game, int characterID)
    {
        for(int i=0; i<game.getCurrentActiveCharacterCard().size(); i++)
            if(game.getCurrentActiveCharacterCard().get(i).getIdCard() == characterID)
                return true;
        return false;
    }

    public int getCardByID(CurrentGameState game, int characterID)
    {
        for(int i=0; i<game.getCurrentActiveCharacterCard().size(); i++)
            if(game.getCurrentActiveCharacterCard().get(i).getIdCard() == characterID)
                return i;
        return -1;
    }

    /** Finds the card that has been used in the ActiveCharDeck, removes it from there,
     and places it, with updated values, in the CharacterDeck.
     * @param card  reference to the used card
     * @param game  an instance of the game
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


    /** Takes a Student from the Priest card residing at the desired position; places it on the
     chosen island. Then, refills the Priest card with another student from the pouch.
     * @param card  the chosen card
     * @param game  an instance of the game
     * @param studentPosition  the position of the chosen student onto the Priest card
     * @param chosenIsland  the island on which the student must be placed
     */
    public static void effect(Priest card, CurrentGameState game, int studentPosition, int chosenIsland, String currentPlayer, Col color)
    {
        game.getCurrentIslands().placeToken(card.getStudent(studentPosition), chosenIsland);
        card.updateStudents(game.getCurrentPouch());
        deckManagement(card, game);
    }


    /** Takes a student from the card at the desired position, saves its color; then finds the active player
     and obtains its school, placing the student in the dining room (updating the dining room structure using the student's color)
     * @param card  the chosen card
     * @param game  an instance of the game
     * @param studentPosition  the position of the chosen student onto the Princess card
     * @param currentPlayer  the name of the player who plays the effect
     */
    public static void effect(Princess card, CurrentGameState game, int studentPosition, int chosenIsland, String currentPlayer, Col color)
    {
        color = card.getStudent(studentPosition).getColor();
        MainController.findPlayerByName(game, currentPlayer).getSchool().placeInDiningRoom(color);
        card.updateStudents(game.getCurrentPouch());
        deckManagement(card, game);
    }


    /** Resolves the influence calculation on the island as if MN has ended there her movement
     * @param card  the chosen card
     * @param game  an instance of the game
     * @param chosenIsland  the island on which the influence calculation must occur
     */
    public static void effect(Herald card, CurrentGameState game, int studentPosition, int chosenIsland, String currentPlayer, Col color)
    {
        if(!game.getCurrentIslands().getIslands().get(chosenIsland).getMotherNature())
            game.getCurrentIslands().getIslands().get(chosenIsland).updateMotherNature();
        ActionController.solveEverything(game, chosenIsland);
        game.getCurrentIslands().getIslands().get(chosenIsland).updateMotherNature();
        deckManagement(card, game);
    }


    /** Adds 2 to the active players' maximum mother nature movement field
     * @param card  the chosen card
     * @param game  an instance of the game
     * @param currentPlayer  the name of the player who plays the effect
     */
    public static void effect(Postman card, CurrentGameState game, int studentPosition, int chosenIsland, String currentPlayer, Col color)
    {
        MainController.findPlayerByName(game, currentPlayer).updateMaxMotherMovement(2);
        deckManagement(card, game);
    }


    /** Sets the noEntry field on the desired island to true; decrements the noEntry field on the card by 1
     * @param card  the chosen card
     * @param game  an instance of the game
     * @param chosenIsland  the island on which the NoEntry tile must be placed
     */
    public static void effect(GrandmaWeed card, CurrentGameState game, int studentPosition, int chosenIsland, String currentPlayer, Col color)
    {
        game.getCurrentIslands().getIslands().get(chosenIsland).updateNoEntry();
        card.updateNoEntry(-1);
        deckManagement(card, game);
    }


    /** Removes the towers from the desired island before calculating the influence
     * @param card  the chosen card
     * @param game  an instance of the game
     * @param chosenIsland  the island on which the influence calculation must occur
     */
    public static void effect(Centaur card, CurrentGameState game, int studentPosition, int chosenIsland, String currentPlayer, Col color)
    {
        game.getCurrentIslands().getIslands().get(chosenIsland).towerNumber = 0;
        ActionController.solveEverything(game, chosenIsland);
        deckManagement(card, game);
    }


    /** Ignores a color of student in the influence calculation
     * @param card  the chosen card
     * @param game  an instance of the game
     * @param color  the color of student not to take into consideration during the influence calculation
     * @param chosenIsland  the island on which the influence calculation must occur
     */
    public static void effect(TruffleHunter card, CurrentGameState game, int studentPosition, int chosenIsland, String currentPlayer, Col color)
    {
        /*Uses this for cycle to remove the students of the selected color from the island: uses a
        counter to save how many students were removed  */
        int cont = 0;
        for(int i=0; i<game.getCurrentIslands().getIslands().get(chosenIsland).currentStudents.size(); i++)
        {
            if(game.getCurrentIslands().getIslands().get(chosenIsland).currentStudents.get(i).getColor() == color)
            {
                game.getCurrentIslands().getIslands().get(chosenIsland).currentStudents.remove(i);
                cont++;
            }
        }

        ActionController.solveEverything(game, chosenIsland);

        //After the influence calculations, it adds to the island as many students of the selected color as the number of the counter
        for(int i=0; i<cont; i++)
        {
            game.getCurrentIslands().getIslands().get(chosenIsland).currentStudents.add(new Student(color));
        }
        deckManagement(card, game);
    }


    /** Adds 2 extra influence to the Active team during the influence calculation. Since the standard method
     solveEverything updates the teamInfluence internally, it is needed to manually update the teams influence,
     add 2 extra influence to the desired team, calculate ownership, update towers and calling the idManagement.
     In the end, the boosted influence is set to its previous value.
     * @param card  the chosen card
     * @param game  an instance of the game
     * @param chosenIsland  the island on which the influence calculation must occur
     * @param currentPlayer  the player requesting the effect to be played
     */
    public static void effect(Knight card, CurrentGameState game, int studentPosition, int chosenIsland, String currentPlayer, Col color)
    {
        ColTow previousOwner = game.getCurrentIslands().getIslands().get(chosenIsland).getOwnership();                                                                                                           //chiama l'altro metodo (overloading) per aumentare di 2
        game.getCurrentIslands().getIslands().get(chosenIsland).updateTeamInfluence(game.getCurrentTeams());
        game.getCurrentIslands().getIslands().get(chosenIsland).updateTeamInfluence(2, MainController.getPlayerColor(game, currentPlayer).ordinal());
        game.getCurrentIslands().getIslands().get(chosenIsland).calculateOwnership();
        ColTow currentOwner = game.getCurrentIslands().getIslands().get(chosenIsland).getOwnership();
        if(previousOwner != currentOwner)
        {
            for(Team t: game.getCurrentTeams())
            {
                for(Player p: t.getPlayers())
                {
                    if(p.isTowerOwner() && t.getColor() == previousOwner)
                    {
                        p.getSchool().updateTowerCount(game.getCurrentIslands().getIslands().get(chosenIsland).getTowerNumber());
                        t.updateControlledIslands(-1);
                    }
                    if(p.isTowerOwner() && t.getColor() == currentOwner)
                    {
                        p.getSchool().updateTowerCount(-(game.getCurrentIslands().getIslands().get(chosenIsland).getTowerNumber()));
                        t.updateControlledIslands(1);
                    }
                }
            }
        }
        game.getCurrentIslands().idManagement();
        game.getCurrentIslands().getIslands().get(chosenIsland).updateTeamInfluence(-2, MainController.getPlayerColor(game, currentPlayer).ordinal());
        deckManagement(card, game);
    }


    public CharacterCard getPickedCard()
    {
        return pickedCard;
    }
}
