package it.polimi.ingsw.controller.checksandbalances;

import it.polimi.ingsw.controller.MainController;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Team;
import it.polimi.ingsw.model.boards.token.enumerations.CharacterName;
import it.polimi.ingsw.model.cards.*;
import it.polimi.ingsw.model.CurrentGameState;
import it.polimi.ingsw.model.boards.token.enumerations.GamePhase;
import it.polimi.ingsw.model.cards.assistants.AssistantCard;

import java.util.ArrayList;

public final class Checks {


    /** Checks whether the given gamePhase is the current gamePhase
     * @param game an instance of the game
     * @param currentGamePhase the given gamePhase
     * @return true if the game is in the given gamePhase
     */
    public static boolean isGamePhase(CurrentGameState game, GamePhase currentGamePhase)
    {
        return currentGamePhase == game.getCurrentTurnState().getGamePhase();
    }


    /** Compares the given player with the currentPlayer
     * @param nickname  the given player's nickname
     * @param currentPlayer the current player's nickname
     * @return true if the nicknames are the same
     */
    public static boolean isCurrentPlayer(String nickname, String currentPlayer)
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
    public static boolean isDestinationAvailable(CurrentGameState game, String currentPlayer, int entrancePosition, boolean toIsland, int islandId)
    {
        boolean validEntrance = entrancePosition >= 0 && entrancePosition < MainController.findPlayerByName(game, currentPlayer).getSchool().getEntrance().size();
        if(!toIsland)
            return validEntrance;
        else
        {
            if(validEntrance)
            {
                return islandId < game.getCurrentIslands().getIslands().size() && islandId >= 0;
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
    public static boolean isAcceptableMovementAmount(CurrentGameState game, String currentPlayer, int amount)
    {
        return amount > 0 && amount <= MainController.findPlayerByName(game, currentPlayer).getMaxMotherMovement();
    }


    /** Checks if the selected cloud exists and if it's not empty
     * @param game an instance of the game
     * @param cloudIndex the chosen cloud
     * @return true if the cloudIndex refers to an existing cloud an if said cloud is not empty
     */
    public static boolean isCloudAvailable(CurrentGameState game, int cloudIndex)
    {
        return cloudIndex >= 0 && cloudIndex < game.getCurrentClouds().length && !game.getCurrentClouds()[cloudIndex].isEmpty();
    }

    public static boolean isCloudFillable(CurrentGameState game, int cloudIndex)
    {
        return cloudIndex >= 0 && cloudIndex < game.getCurrentClouds().length && game.getCurrentClouds()[cloudIndex].isEmpty();
    }


    /** Checks if the pouch can be used to extract students
     * @param game an instance of the game
     * @return true if the pouch is not empty
     */
    public static boolean isPouchAvailable(CurrentGameState game)
    {
        return !game.getCurrentPouch().checkEmpty();
    }


    /** Checks whether the given cardIndex is referring to an existing assistant card
     * @param game an instance of the game
     * @param currentPlayer the current player's nickname
     * @param cardIndex the position of the chosen card into the deck
     * @return ture if the player's deck is not empty and if the index is within an acceptable range
     */
    public static boolean isAssistantValid(CurrentGameState game, String currentPlayer, int cardIndex)
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
    public static boolean isAssistantAlreadyPlayed(CurrentGameState game, String currentPlayer, int cardIndex)
    {
        Player player = MainController.findPlayerByName(game, currentPlayer);
        for (Team team : game.getCurrentTeams())
        {
            for (Player otherPlayer: team.getPlayers())
            {
                if (!player.getName().equals(otherPlayer.getName()) && otherPlayer.getCurrentAssistantCard() != null)
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
    public static boolean canCardStillBePlayed(CurrentGameState game, String currentPlayer, int cardIndex)
    {
        Player player = MainController.findPlayerByName(game, currentPlayer);
        ArrayList<AssistantCard> currentlyPlayedCards = new ArrayList<>();
        for(Team team: game.getCurrentTeams())
            for(Player p: team.getPlayers())
                if(!p.getName().equals(player.getName()) && p.getCurrentAssistantCard() != null)
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
        for (int j : hitCounter)
            if (j != 0)
                counter += 1;
        return counter == hitCounter.length;
    }



    /** Checks if the current turn is expiring
     * @param game an instance of the game
     * @return true if the current player is indeed the last player of that turn
     */
    public static boolean isLastPlayer(CurrentGameState game)
    {
        return game.getCurrentTurnState().getTurnOrder().size() == 0;
    }

    public static boolean isLastTurn(CurrentGameState game){
        return game.getCurrentTurnState().getLastTurn();
    }


    /** Returns true if there are any influence related character cards present in the Active Characters Deck
     * @param game  an instance of hte game
     * @return whether influence based card are currently active
     */
    public static boolean checkForInfluenceCharacter(CurrentGameState game)
    {
        for(CharacterCard card: game.getCurrentActiveCharacterCard())
        {
            CharacterName cardName = card.getCharacterName();
            if(cardName.equals(CharacterName.CENTAUR) || cardName.equals(CharacterName.TRUFFLE_HUNTER) || cardName.equals(CharacterName.KNIGHT))
                return true;
        }
        return false;
    }

    public static void checkWinnerForTowers(CurrentGameState game)
    {
        for(Team t: game.getCurrentTeams())
        {
            for(Player p: t.getPlayers())
            {
                if(p.getSchool().getTowerCount() == 0 && p.isTowerOwner())
                {
                    game.getCurrentTurnState().updateWinner(t.getColor());
                }
            }
        }
    }

    public static void checkWinnerForIsland(CurrentGameState game)
    {
        if(game.getCurrentIslands().getTotalGroups() <= 3)
        {
            game.getCurrentTurnState().updateWinner(game.getCurrentIslands().getMaxCol(game.getCurrentTeams()));
        }
    }

    public static boolean checkLastTurnDueToAssistants(CurrentGameState game, String currentPlayer)
    {
        Player player = MainController.findPlayerByName(game, currentPlayer);
        return player.getAssistantDeck().checkEmpty();
    }

    public static boolean isThereAWinner(CurrentGameState game)
    {
        return game.getCurrentTurnState().getWinningTeam() != null;
    }

}
