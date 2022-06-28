package it.polimi.ingsw.model;

//URGENT
//TODO game testing
//TODO test school checkpoints when using minstrel and thief
//TODO professor assigning doesn't work
//TODO hints on turn end
//TODO charactercard notify and green sign GUI
//TODO minstrel fix

//A bit less urgent
//TODO Documentation!!

//Less less urgent
//TODO aesthetic of GUI in general


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
     Class Constructor, instantiates a new CurrentGameState, taking as parameters only the number of players playing and
     whether we're playing an expertGame or not
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
           this.currentCharacterDeck = new CharacterDeck(this);
           getCurrentPouch().updateSetup(false);
           currentCharacterDeck.SetupCards(getCurrentPouch());
           getCurrentPouch().updateSetup(true);
       }
       else
       {
           this.bankBalance = 0;
       }
    }


    /** Method updateTurnState takes the AssistantCard value that every player has played and puts it in an HashMap and
     then sorts it from the lowest to the highest
     */
    public void updateTurnState()
    {
        HashMap<String, Integer> map = new HashMap<>();
        HashMap<String, Integer> finalmap = new HashMap<>();
        for(Team t: currentTeams)
        {
            for(Player p: t.getPlayers())
            {
                map.put(p.getName(), p.getValue());
            }
        }
        finalmap = map.entrySet().stream()
                                 .sorted(Map.Entry.comparingByValue())
                                 .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        currentTurnState.updateTurn(finalmap);
    }


    /** Method updateBankBalance updates the BankBalance everytime a player p gains a coin from its DiningRoom or pays
     for a Character Card
     * @param p  the player to check
     * @param gain  the amount of coins gained or lost by the player
     */
    public void updateBankBalance(Player p, int gain)
    {
        int coinsToLose = 0;
        if(gain == 0)
        {
            coinsToLose = p.gainCoin();
        }
        else
        {
            p.updateCoins(-gain);
            coinsToLose = -(gain-1);
        }
        if(coinsToLose <= bankBalance)
            bankBalance -= coinsToLose;
        else
            bankBalance = 0;
    }


    /** Method giveProfessors assigns professors to the player with the most student in their diningRoom  */
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
                if(integer == max)
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
                    if(player.getName().equals(currentTurnState.getCurrentPlayer()))
                    {
                        player.getSchool().updateProfessorsTable(color.ordinal(), true);
                    }
                    else
                    {
                        player.getSchool().updateProfessorsTable(color.ordinal(), false);
                    }
                }
            }
            for(Player loser: losers)
                loser.getSchool().updateProfessorsTable(color.ordinal(), false);
        }
    }


    /** Method solveEverything is responsible for the influence calculation on the desired island, and
     handles the eventual exchange of towers between players' schools and the island
     * @param pos  the island on which the influence calculation has to be called
     */
    public void solveEverything(int pos)
    {
        ColTow previousOwner = currentIslands.getIslands().get(pos).getOwnership();
        currentIslands.getIslands().get(pos).updateTeamInfluence(getCurrentTeams());
        currentIslands.getIslands().get(pos).calculateOwnership();
        ColTow currentOwner = currentIslands.getIslands().get(pos).getOwnership();
        if(previousOwner != currentOwner)
        {
            for(Team t: getCurrentTeams())
            {
                for(Player p: t.getPlayers())
                {
                    if(p.isTowerOwner() && t.getColor() == previousOwner)
                    {
                        p.getSchool().updateTowerCount(getCurrentIslands().getIslands().get(pos).getTowerNumber());
                        t.updateControlledIslands(-1);
                    }
                    if(p.isTowerOwner() && t.getColor() == currentOwner)
                    {
                        p.getSchool().updateTowerCount(-(getCurrentIslands().getIslands().get(pos).getTowerNumber()));
                        t.updateControlledIslands(1);
                    }
                }
            }
        }
        currentIslands.idManagement();
    }

    public String modelToJson()
    {
        String json=null;
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