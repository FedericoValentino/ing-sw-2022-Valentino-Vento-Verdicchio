package it.polimi.ingsw.model;

//URGENT
//TODO game testing (especially 3 and ESPECIALLY 4 players)
//TODO somehow islands (sometimes, hardly replicable) do not update. Very bad issue   !!could be fixed!!ù
//TODO hints on turn end (3 Players)    !!could be fixed!!
//TODO hints bug sometimes upon character usage     !!could be fixed!!
//TODO test school checkpoints when using minstrel
//TODO Wrong inputs still causing the CLI to crash
//TODO Testing of Knight, Centaur, GHerbs
//TODO Documentation!!


//A bit less urgent
//TODO Remove updater notices
//TODO GUI needs to visualize bank balance in propaganda
//TODO Look at code repetition in MainBoard for characters (Jester - Princess - Priest)
//TODO Look at the jSon constructor in Assistants and Student and double constructor in cloud

//Less less urgent
//TODO aesthetic of GUI in general
//TODO CLI school information appears not aligned


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.polimi.ingsw.model.boards.Cloud;
import it.polimi.ingsw.model.boards.Pouch;
import it.polimi.ingsw.model.cards.CharacterCard;
import it.polimi.ingsw.model.cards.CharacterDeck;
import it.polimi.ingsw.model.boards.Islands;
import it.polimi.ingsw.model.boards.token.enumerations.Col;
import it.polimi.ingsw.model.boards.token.enumerations.ColTow;
import it.polimi.ingsw.model.boards.token.MotherNature;
import it.polimi.ingsw.Observer.Observable;

import java.util.*;
import java.util.stream.Collectors;

/**
 * CurrentGameState is one of the two classes responsible to represent, as the name suggests, the current state of the game:
 * it does this by creating and sometimes manipulating game objects.
 * In addition, it is the highest class in the model hierarchy, having the possibility to access almost every other class,
 * some directly via reference, some through other high level classes.
 * It creates the game objects through its constructor and has methods requiring a higher level of access in order to change
 * the state of some game objects.
 */
@JsonSerialize(using = CgSerializer.class)
public class CurrentGameState extends Observable {
    private CharacterDeck currentCharacterDeck;
    private Pouch currentPouch;
    private Islands currentIslands;
    private ArrayList<Team> currentTeams;
    private Cloud[] currentClouds;
    private MotherNature currentMotherNature;
    private CurrentTurnState currentTurnState;
    private ArrayList<CharacterCard> currentActiveCharacterCard;
    private int bankBalance;
    private boolean expertGame;


    /**
     * Class Constructor, instantiates a new CurrentGameState, taking as parameters only the number of players playing and
     * whether we're playing an expertGame or not
     * @param playerNum  the number of players
     * @param expertGame  the game mode
     */
    public CurrentGameState(int playerNum, boolean expertGame)
    {
       this.expertGame = expertGame;
       this.currentPouch = new Pouch();
       this.currentMotherNature = new MotherNature();
       this.currentIslands = new Islands(this);
       this.currentTeams = new ArrayList<>();
       if(playerNum == 2 || playerNum == 4)
       {
           for(int i = 0; i < 2; i++)
           {
               currentTeams.add(new Team(ColTow.values()[i]));
           }
       }
       else if(playerNum == 3)
       {
           for(int i = 0; i < 3; i++)
           {
               currentTeams.add(new Team(ColTow.values()[i]));
           }
       }
       this.currentClouds = new Cloud[playerNum];
       for(int i = 0; i < playerNum; i++)
       {
           currentClouds[i] = new Cloud(i);
       }
       this.currentTurnState = new CurrentTurnState(this);

       if(expertGame)
       {
           this.currentActiveCharacterCard = new ArrayList<>();
           this.bankBalance = 20 - playerNum;
           this.currentCharacterDeck = new CharacterDeck();
           getCurrentPouch().updateSetup(false);
           currentCharacterDeck.SetupCards(getCurrentPouch());
           getCurrentPouch().updateSetup(true);
       }
       else
       {
           this.bankBalance = 0;
       }
    }


    /**
     * Method updateTurnState takes the AssistantCard value that every player has played and puts it in an HashMap; it
     * then sorts it from the lowest to the highest
     */
    public void updateTurnState()
    {
        HashMap<String, Integer> map = new HashMap<>();
        HashMap<String, Integer> finalMap;
        for(Team team: currentTeams)
        {
            for(Player player: team.getPlayers())
            {
                map.put(player.getName(), player.getValue());
            }
        }
        finalMap = map.entrySet().stream()
                                 .sorted(Map.Entry.comparingByValue())
                                 .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (element1, element2) -> element1, LinkedHashMap::new));

        currentTurnState.updateTurn(finalMap);
    }


    /**
     * Method updateBankBalance updates the BankBalance everytime a player gains a coin from its DiningRoom or pays
     * for a Character Card
     * @param player  the player to check
     * @param gain  the amount of coins gained or lost by the player
     */
    public void updateBankBalance(Player player, int gain)
    {
        int coinsToLose;
        if(gain == 0)
        {
            coinsToLose = player.gainCoin();
        }
        else
        {
            player.updateCoins(-gain);
            coinsToLose = -(gain-1);
        }
        if(coinsToLose <= bankBalance)
            bankBalance -= coinsToLose;
        else
            bankBalance = 0;
    }


    /**
     * Method giveProfessors assigns professors to the player with the most student in their diningRoom. It accounts for
     * draw cases, and it differentiates whether the Cook card is active, using a simple boolean in input
     * @param cook a boolean that tells the method if cook is active. If it is active, in case of a draw, the method will
     *             give the contested professor to the player who activated the card, contrary to the normal flow of the method
     */
    public void giveProfessors(boolean cook)
    {
        for(Col color: Col.values())
        {
            HashMap<Player, Integer> comparator = new HashMap<>();
            for(Team team: currentTeams)
            {
                for(Player player: team.getPlayers())
                {
                    comparator.put(player, player.getSchool().getDiningRoom()[color.ordinal()]);
                }
            }
            int max = Collections.max(comparator.values());
            ArrayList<Player> playersMax = new ArrayList<>();
            ArrayList<Player> losers = new ArrayList<>();
            comparator.forEach((player, integer) -> {
                if(integer == max && integer != 0)
                    playersMax.add(player);
                else
                    losers.add(player);
            });

            if(playersMax.size() == 1)
            {
                playersMax.get(0).getSchool().updateProfessorsTable(color.ordinal(), true);
            }
            else if(cook)
            {
                for(Player player : playersMax)
                {
                    player.getSchool().updateProfessorsTable(color.ordinal(), player.getName().equals(currentTurnState.getCurrentPlayer()));
                }
            }
            for(Player loser: losers)
                loser.getSchool().updateProfessorsTable(color.ordinal(), false);
        }
    }


    /**
     * Method solveEverything is responsible for the influence calculation on the desired island, and
     * handles the eventual exchange of towers between players' schools and the island
     * @param islandPosition  the island on which the influence calculation has to be called
     */
    public void solveEverything(int islandPosition)
    {
        ColTow previousOwner = currentIslands.getIslands().get(islandPosition).getOwnership();
        currentIslands.getIslands().get(islandPosition).updateTeamInfluence(getCurrentTeams());
        currentIslands.getIslands().get(islandPosition).calculateOwnership();
        ColTow currentOwner = currentIslands.getIslands().get(islandPosition).getOwnership();
        if(previousOwner != currentOwner)
        {
            for(Team t: getCurrentTeams())
            {
                for(Player p: t.getPlayers())
                {
                    if(p.isTowerOwner() && t.getColor() == previousOwner)
                    {
                        p.getSchool().updateTowerCount(getCurrentIslands().getIslands().get(islandPosition).getTowerNumber());
                        t.updateControlledIslands(-1);
                    }
                    if(p.isTowerOwner() && t.getColor() == currentOwner)
                    {
                        p.getSchool().updateTowerCount(-(getCurrentIslands().getIslands().get(islandPosition).getTowerNumber()));
                        t.updateControlledIslands(1);
                    }
                }
            }
        }
        currentIslands.idManagement();
    }

    /**
     * Method used by the notifies to compile the json file with the currentGameState information
     * @return a string representing the json file
     */
    public String modelToJson()
    {
        String json = null;
        try {
            json = new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }

    public int getBankBalance(){return bankBalance;}
    public Islands getCurrentIslands(){return currentIslands;}
    public Cloud[] getCurrentClouds(){return currentClouds;}
    public CharacterDeck getCurrentCharacterDeck(){return currentCharacterDeck;}
    public Pouch getCurrentPouch() {return currentPouch;}
    public ArrayList<CharacterCard> getCurrentActiveCharacterCard()
    {
        return currentActiveCharacterCard;
    }
    public ArrayList<Team> getCurrentTeams()
    {
      return currentTeams;
    }
    public CurrentTurnState getCurrentTurnState()
    {
        return currentTurnState;
    }
    public MotherNature getCurrentMotherNature() {
        return currentMotherNature;
    }
    public boolean getExpertMode(){
        return expertGame;
    }
}