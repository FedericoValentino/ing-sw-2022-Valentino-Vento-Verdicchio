package controller;

import model.CurrentGameState;
import model.Player;
import model.Team;
import model.boards.token.GamePhase;
import model.cards.*;

import java.util.ArrayList;

public class Checks {


    /** Checks whether the given gamePhase is the current gamePhase
     * @param game an instance of the game
     * @param currentGamePhase the given gamePhase
     * @return true if the game is in the given gamePhase
     */
    public boolean isGamePhase(CurrentGameState game, GamePhase currentGamePhase)
    {
        return currentGamePhase == game.getCurrentTurnState().getGamePhase();
    }


    /** Compares the given player with the currentPlayer
     * @param nickname  the given player's nickname
     * @param currentPlayer the current player's nickname
     * @return true if the nicknames are the same
     */
    public boolean isCurrentPlayer(String nickname, String currentPlayer)
    {
        return nickname.equals(currentPlayer);
    }


    /** Checks the validity of the chosen entrancePosition and islandID
     * @param game an instance of the game
     * @param currentPlayer the current player's nickname
     * @param entrancePosition chosen entrance position
     * @param toIsland indicates whether the student is being moved to an island or to the entrance
     * @param islandId chosen island
     * @return true if the entrancePosition refers to an existing position and, in case of moving to an island, if the islandID corresponds to an existing island
     */
    public boolean isDestinationAvailable(CurrentGameState game, String currentPlayer, int entrancePosition, boolean toIsland, int islandId)
    {
        boolean validEntrance = true;
        if (entrancePosition < 0 || entrancePosition >= MainController.findPlayerByName(game, currentPlayer).getSchool().getEntrance().size())
            validEntrance = false;
        if(!toIsland)
            return validEntrance;
        else
        {
            if(validEntrance)
            {
                if (islandId >= game.getCurrentIslands().getIslands().size() || islandId < 0)
                    return false;
                return true;
            }
            else
                return false;
        }
    }


    /** Checks if the Mother Nature Movement value chosen by the player is within an acceptable range
     * @param game an instance of the game
     * @param currentPlayer the current player's name
     * @param amount the chosen movement value
     * @return true if the chosen movement value sits between 1 and MaxMotherMovement
     */
    public boolean isAcceptableMovementAmount(CurrentGameState game, String currentPlayer, int amount)
    {
        return amount > 0 && amount <= MainController.findPlayerByName(game, currentPlayer).getMaxMotherMovement();
    }


    /** Checks if the selected cloud exists and if it's not empty
     * @param game an instance of the game
     * @param cloudIndex the chosen cloud
     * @return true if the cloudIndex refers to an existing cloud an if said cloud is not empty
     */
    public boolean isCloudAvailable(CurrentGameState game, int cloudIndex)
    {
        return cloudIndex >= 0 && cloudIndex < game.getCurrentClouds().length && !game.getCurrentClouds()[cloudIndex].isEmpty();
    }

    public boolean isCloudFillable(CurrentGameState game, int cloudIndex)
    {
        return cloudIndex >= 0 && cloudIndex < game.getCurrentClouds().length && game.getCurrentClouds()[cloudIndex].isEmpty();
    }


    /** Checks if the pouch can be used to extract students
     * @param game an instance of the game
     * @return true if the pouch is not empty
     */
    public boolean isPouchAvailable(CurrentGameState game)
    {
        return !game.getCurrentPouch().checkEmpty();
    }


    /** Checks whether the given cardIndex is referring to an existing assistant card
     * @param game an instance of the game
     * @param currentPlayer the current player's nickname
     * @param cardIndex the position of the chosen card into the deck
     * @return ture if the player's deck is not empty and if the index is within an acceptable range
     */
    public boolean isAssistantValid(CurrentGameState game, String currentPlayer, int cardIndex)
    {
        Player player = MainController.findPlayerByName(game, currentPlayer);
        return !player.getAssistantDeck().checkEmpty() && cardIndex >= 0 && cardIndex < player.getAssistantDeck().getDeck().size();
    }


    /** Checks if the selected assistant card has already been played by other players
     * @param game an instance of the game
     * @param currentPlayer the current player's nickname
     * @param cardIndex the selected card
     * @return true if an equivalent assistant card is in other players' currentAssistantCard field
     */
    public boolean isAssistantAlreadyPlayed(CurrentGameState game, String currentPlayer, int cardIndex)
    {
        Player player = MainController.findPlayerByName(game, currentPlayer);
        for (Team team : game.getCurrentTeams())
        {
            for (Player otherPlayer: team.getPlayers())
            {
                if (!player.getNome().equals(otherPlayer.getNome()) && otherPlayer.getCurrentAssistantCard() != null)
                {
                    if (player.getAssistantDeck().getDeck().get(cardIndex).getValue() == otherPlayer.getCurrentAssistantCard().getValue())
                        return true;
                }
            }
        }
        return false;
    }



    /** Has to be called after isAssistantAlreadyPlayed (if true): checks the player's deck and verifies whether
     the other cards in player's possession have been already played by others in the current turn. If this happens
     to be true, then the chosen card can be played
     * @param game an instance of the game
     * @param currentPlayer the current player's nickname
     * @param cardIndex the chosen card
     * @return true if all the other cards in the player's deck have already been played
     */
    public boolean canCardStillBePlayed(CurrentGameState game, String currentPlayer, int cardIndex)
    {
        Player player = MainController.findPlayerByName(game, currentPlayer);
        ArrayList<AssistantCard> currentlyPlayedCards = new ArrayList<>();
        for(Team team: game.getCurrentTeams())
            for(Player p: team.getPlayers())
                if(!p.getNome().equals(player.getNome()) && p.getCurrentAssistantCard() != null)
                    currentlyPlayedCards.add(p.getCurrentAssistantCard());
        int[] hitCounter = new int[player.getAssistantDeck().getDeck().size()-1];
        int index = 0;
        int counter = 0;
        for(AssistantCard examinedCard: player.getAssistantDeck().getDeck())
            if(examinedCard.getValue() != player.getAssistantDeck().getDeck().get(cardIndex).getValue())
                for(AssistantCard currentCard: currentlyPlayedCards)
                    if(examinedCard.getValue() == currentCard.getValue())
                    {
                        hitCounter[index] += 1;
                        index +=1;
                    }
        for (int i = 0; i < hitCounter.length; i++)
            if (hitCounter[i] != 0)
                counter += 1;
        return counter == hitCounter.length;
    }



    /** Checks if the current turn is expiring
     * @param game an instance of the game
     * @return true if the current player is indeed the last player of that turn
     */
    public boolean isLastPlayer(CurrentGameState game)
    {
        return game.getCurrentTurnState().getTurnOrder().size() == 0;
    }


    /** Returns true if there are any influence related character cards present in the Active Characters Deck
     * @param game  an instance of hte game
     * @return whether influence based card are currently active
     */
    protected boolean checkForInfluenceCharacter(CurrentGameState game, String currentPlayer)
    {
        for(CharacterCard c: game.getCurrentActiveCharacterCard())
        {
            if(c instanceof Knight)
            {
                c.effect(game, 0, game.getCurrentMotherNature().getPosition(), currentPlayer, null);
                CharacterController.deckManagement(game);
                return  true;
            }
            else if(c instanceof TruffleHunter)
            {
                c.effect(game, 0, game.getCurrentMotherNature().getPosition(), null, ((TruffleHunter) c).getChosenColor());
                CharacterController.deckManagement(game);
                return  true;
            }
            else if(c instanceof Centaur)
            {
                c.effect(game, 0, game.getCurrentMotherNature().getPosition(), null, null);
                CharacterController.deckManagement(game);
                return  true;
            }
        }
        return false;
    }

}
