package controller;

import model.CurrentGameState;
import model.Player;
import model.Team;
import model.boards.token.GamePhase;
import model.cards.AssistantCard;

public class Checks {
    public boolean isGamePhase(CurrentGameState game, GamePhase currentGamePhase) {
        return currentGamePhase == game.getCurrentTurnState().getGamePhase();
    }

    public boolean isCurrentPlayer(String nickname, String currentPlayer) {
        return nickname.equals(currentPlayer);
    }

    public boolean isDestinationAvailable(CurrentGameState game, String currentPlayer, int playerNumber, int entrancePosition, boolean toIsland, int islandId) {
        if (!toIsland) {
            if (playerNumber == 2 || playerNumber == 4) {
                if (MainController.findPlayerByName(game, currentPlayer).getSchool().getEntrance().size() == 7 || entrancePosition < 0 || entrancePosition > 6)
                    return false;
            } else if (playerNumber == 3) {
                if (MainController.findPlayerByName(game, currentPlayer).getSchool().getEntrance().size() == 9 || entrancePosition < 0 || entrancePosition > 8)
                    return false;
            }
        } else {
            if (game.getCurrentIslands().getIslands().size() <= islandId || islandId < 0)
                return false;
        }
        return true;
    }

    public boolean isAcceptableMovementAmount(CurrentGameState game, String currentPlayer, int amount) {
        return amount > 0 && amount <= MainController.findPlayerByName(game, currentPlayer).getMovementValue();
    }

    public boolean isCloudAvailable(CurrentGameState game, int cloudIndex) {
        return cloudIndex >= 0 && cloudIndex < game.getCurrentClouds().length && !game.getCurrentClouds()[cloudIndex].isEmpty();
    }

    public boolean isPouchAvailable(CurrentGameState game) {
        return game.getCurrentPouch().checkEmpty();
    }


    public boolean isAssistantAlreadyPlayed(CurrentGameState game, String currentPlayer, int cardIndex) {
        Player player = MainController.findPlayerByName(game, currentPlayer);
        int counter = 0;
        for (int i = 0; i < game.getCurrentTeams().size(); i++)
            for (int j = 0; j < game.getCurrentTeams().get(i).getPlayers().size(); j++) {
                if (!player.getNome().equals(game.getCurrentTeams().get(i).getPlayers().get(j).getNome())) {
                    if (player.getAssistantDeck().getDeck().get(cardIndex).equals(game.getCurrentTeams().get(i).getPlayers().get(j).getCurrentAssistantCard()))
                        counter++;
                }
            }
        return counter != 0;
    }

    public boolean canCardStillBePlayed(CurrentGameState game, String currentPlayer, int cardIndex) {
        Player player = MainController.findPlayerByName(game, currentPlayer);
        int[] cardCounter = new int[player.getAssistantDeck().getDeck().size() - 1];
        int counter = 0;
        for (int i = 0; i < player.getAssistantDeck().getDeck().size() - 1; i++)
            cardCounter[i] = 0;
        for (int k = 0; k < player.getAssistantDeck().getDeck().size(); k++)
        {
            if (!player.getAssistantDeck().getDeck().get(k).equals(player.getAssistantDeck().getDeck().get(cardIndex)))
                for (int i = 0; i < game.getCurrentTeams().size(); i++)
                    for (int j = 0; j < game.getCurrentTeams().get(i).getPlayers().size(); j++) {
                        if (!player.getNome().equals(game.getCurrentTeams().get(i).getPlayers().get(j).getNome())) {
                            if (player.getAssistantDeck().getDeck().get(k).equals(game.getCurrentTeams().get(i).getPlayers().get(j).getCurrentAssistantCard()))
                                cardCounter[k] = +1;
                        }
                    }
        }
        for (int i = 0; i < cardCounter.length; i++)
            if (cardCounter[i] != 0)
                counter += 1;
        return counter == cardCounter.length;
    }

}
