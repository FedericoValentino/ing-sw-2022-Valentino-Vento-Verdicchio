package it.polimi.ingsw.Client.PreemptiveChecks;

import it.polimi.ingsw.Client.LightView.LightTeams.LightPlayer;
import it.polimi.ingsw.Client.LightView.LightTeams.LightTeam;
import it.polimi.ingsw.Client.LightView.LightTurnState;
import it.polimi.ingsw.Client.LightView.LightUtilities.Utilities;
import it.polimi.ingsw.Client.LightView.LightView;
import it.polimi.ingsw.model.CurrentGameState;
import it.polimi.ingsw.model.boards.token.enumerations.CharacterName;
import it.polimi.ingsw.model.boards.token.enumerations.GamePhase;
import it.polimi.ingsw.model.cards.CharacterCard;
import it.polimi.ingsw.model.cards.assistants.AssistantCard;

import java.util.ArrayList;

public final class GeneralChecks {
    /**
     * Checks whether the given gamePhase is the current gamePhase
     *
     * @param state            an instance of the turn state
     * @param currentGamePhase the given gamePhase
     * @return true if the game is in the given gamePhase
     */
    public static boolean isGamePhase(LightTurnState state, GamePhase currentGamePhase) {
        return currentGamePhase == state.getGamePhase();
    }


    /**
     * Compares the given player with the currentPlayer
     *
     * @param nickname the given player's nickname
     * @param state    an instance of the turn state
     * @return true if the nicknames are the same
     */
    public static boolean isCurrentPlayer(LightTurnState state, String nickname) {
        return nickname.equals(state.getCurrentPlayer());
    }


    /**
     * Checks the validity of the chosen entrancePosition and islandID
     *
     * @param view             an instance of the view
     * @param currentPlayer    the current player's nickname
     * @param entrancePosition chosen entrance position
     * @param toIsland         indicates whether the student is being moved to an island or to the entrance
     * @param islandId         chosen island
     * @return true if the entrancePosition refers to an existing position and, in case of moving to an island, if the islandID corresponds to an existing island
     */
    public static boolean isDestinationAvailable(LightView view, String currentPlayer, int entrancePosition, boolean toIsland, int islandId) {
        boolean validEntrance = entrancePosition >= 0 && entrancePosition < Utilities.findPlayerByName(view, currentPlayer).getSchool().getEntrance().size();
        if (!toIsland)
            return validEntrance;
        else {
            if (validEntrance) {
                if (islandId >= view.getCurrentIslands().getIslands().size() || islandId < 0)
                    return false;
                return true;
            } else
                return false;
        }
    }


    /**
     * Checks if the Mother Nature Movement value chosen by the player is within an acceptable range
     *
     * @param view          an instance of the view
     * @param currentPlayer the current player's name
     * @param amount        the chosen movement value
     * @return true if the chosen movement value sits between 1 and MaxMotherMovement
     */
    public static boolean isAcceptableMovementAmount(LightView view, String currentPlayer, int amount) {
        return amount > 0 && amount <= Utilities.findPlayerByName(view, currentPlayer).getMaxMotherMovement();
    }


    /**
     * Checks if the selected cloud exists and if it's not empty
     *
     * @param view       an instance of the view
     * @param cloudIndex the chosen cloud
     * @return true if the cloudIndex refers to an existing cloud an if said cloud is not empty
     */
    public static boolean isCloudAvailable(LightView view, int cloudIndex) {
        return cloudIndex >= 0 && cloudIndex < view.getCurrentClouds().length && !view.getCurrentClouds()[cloudIndex].getStudents().isEmpty();
    }

    public static boolean isCloudFillable(LightView view, int cloudIndex) {
        return cloudIndex >= 0 && cloudIndex < view.getCurrentClouds().length && view.getCurrentClouds()[cloudIndex].getStudents().isEmpty();
    }


    /**
     * Checks whether the given cardIndex is referring to an existing assistant card
     *
     * @param view          an instance of the view
     * @param currentPlayer the current player's nickname
     * @param cardIndex     the position of the chosen card into the deck
     * @return ture if the player's deck is not empty and if the index is within an acceptable range
     */
    public static boolean isAssistantValid(LightView view, String currentPlayer, int cardIndex) {
        LightPlayer player = Utilities.findPlayerByName(view, currentPlayer);
        return !player.getLightAssistantDeck().getDeck().isEmpty() && cardIndex >= 0 && cardIndex < player.getLightAssistantDeck().getDeck().size();
    }


    /**
     * Checks if the selected assistant card has already been played by other players
     *
     * @param view          an instance of the view
     * @param currentPlayer the current player's nickname
     * @param cardIndex     the selected card
     * @return true if an equivalent assistant card is in other players' currentAssistantCard field
     */
    public static boolean isAssistantAlreadyPlayed(LightView view, String currentPlayer, int cardIndex) {
        LightPlayer player = Utilities.findPlayerByName(view, currentPlayer);
        for (LightTeam team : view.getCurrentTeams()) {
            for (LightPlayer otherPlayer : team.getPlayers()) {
                if (!player.getName().equals(otherPlayer.getName()) && otherPlayer.getCurrentAssistantCard() != null) {
                    if (player.getLightAssistantDeck().getDeck().get(cardIndex).getValue() == otherPlayer.getCurrentAssistantCard().getValue())
                        return true;
                }
            }
        }
        return false;
    }


    /**
     * Has to be called after isAssistantAlreadyPlayed (if true): checks the player's deck and verifies whether
     * the other cards in player's possession have been already played by others in the current turn. If this happens
     * to be true, then the chosen card can be played
     *
     * @param view          an instance of the view
     * @param currentPlayer the current player's nickname
     * @param cardIndex     the chosen card
     * @return true if all the other cards in the player's deck have already been played
     */
    public static boolean canCardStillBePlayed(LightView view, String currentPlayer, int cardIndex) {
        LightPlayer player = Utilities.findPlayerByName(view, currentPlayer);
        ArrayList<AssistantCard> currentlyPlayedCards = new ArrayList<>();
        for (LightTeam team : view.getCurrentTeams())
            for (LightPlayer p : team.getPlayers())
                if (!p.getName().equals(player.getName()) && p.getCurrentAssistantCard() != null)
                    currentlyPlayedCards.add(p.getCurrentAssistantCard());
        int[] hitCounter = new int[player.getLightAssistantDeck().getDeck().size() - 1];
        int index = 0;
        int counter = 0;
        for (AssistantCard examinedCard : player.getLightAssistantDeck().getDeck())
            if (examinedCard.getValue() != player.getLightAssistantDeck().getDeck().get(cardIndex).getValue())
                for (AssistantCard currentCard : currentlyPlayedCards)
                    if (examinedCard.getValue() == currentCard.getValue()) {
                        hitCounter[index] += 1;
                        index += 1;
                    }
        for (int j : hitCounter)
            if (j != 0)
                counter += 1;
        return counter == hitCounter.length;
    }

}



