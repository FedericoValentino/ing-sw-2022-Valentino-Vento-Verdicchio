

package model;

import model.boards.Cloud;
import model.boards.Islands;
import model.boards.Pouch;
import model.boards.token.Col;
import model.boards.token.ColTow;
import model.boards.token.MotherNature;
import model.cards.CharacterCard;
import model.cards.CharacterDeck;
import Observer.Observable;

import java.util.*;
import java.util.stream.Collectors;


public class CurrentGameState extends Observable {
    private CharacterDeck currentCharacterDeck;
    private Pouch currentPouch;
    private Islands currentIslands;
    private ArrayList<Team> currentTeams;
    private Cloud[] currentClouds;
    private MotherNature currentMotherNature;
    private CurrentTurnState currentTurnState;
    private ArrayList<CharacterCard> currentActiveCharacterCard;
    //private ArrayList<Student> currentExtractedStudents;
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
       this.currentPouch = new Pouch();
       this.currentMotherNature = new MotherNature(this);
       this.currentIslands = new Islands(this);
       this.currentTeams = new ArrayList<>();
       if(playerNum == 2 || playerNum == 4)
       {
           for(int i = 0; i < 2; i++)
           {
               currentTeams.add(new Team(ColTow.values()[i], this));
           }
       }
       else if(playerNum == 3)
       {
           for(int i = 0; i < 3; i++)
           {
               currentTeams.add(new Team(ColTow.values()[i],this));
           }
       }
       this.currentClouds = new Cloud[playerNum];
       for(int i = 0; i < playerNum; i++)
       {
           currentClouds[i] = new Cloud(this);
       }
       this.currentTurnState = new CurrentTurnState();
       this.currentActiveCharacterCard = new ArrayList<>();
       //this.currentExtractedStudents = new ArrayList<>();
       if(expertGame)
       {
           this.bankBalance = 20 - playerNum;
           this.currentCharacterDeck = new CharacterDeck();
           getCurrentPouch().updateSetup(false);
           currentCharacterDeck.SetupCards(getCurrentPouch());
           getCurrentPouch().updateSetup(true);
       }
       else
           this.bankBalance = 0;
       this.expertGame = expertGame;
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
                map.put(p.getNome(), p.getValue());
            }
        }
        finalmap = map.entrySet().stream()
                                 .sorted(Map.Entry.comparingByValue())
                                 .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        currentTurnState.updateTurn(finalmap);
    }


    /** Method checkWinner checks if a winner is found and updates the CurrentTurnState */
    public void checkWinner()
    {
        for(Team t: currentTeams)
        {
            for(Player p: t.getPlayers())
            {
                if(p.getSchool().getTowerCount() == 0 && p.isTowerOwner())
                {
                    currentTurnState.updateWinner(t.getColor());
                }
                if(p.getAssistantDeck().checkEmpty())
                {
                    currentTurnState.lastTurn = true;
                }
            }
        }
        if(currentIslands.getTotalGroups() <= 3)
        {
            currentTurnState.updateWinner(currentIslands.getMaxCol(currentTeams));
        }
        else if (currentPouch.checkEmpty())
        {
            currentTurnState.lastTurn = true;
        }
        notify(modelToJson());
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
        notify(modelToJson());
    }


    /** Method giveProfessors assigns professors to the player with the most student in their diningRoom  */
    public void giveProfessors()
    {
        //Scrolls through each students' color
        for(Col c: Col.values())
        {
            //Saves the max number of colors we find
            int max = 0;
            //saves the player that has the highest number of students of that color in his Dining Room
            Player maxPlayer = null;

            //Finds the maxPlayer
            for (Team t : currentTeams)
            {
                for (Player p : t.getPlayers())
                {
                    if(p.getSchool().getDiningRoom()[c.ordinal()] > max)
                    {
                        max = p.getSchool().getDiningRoom()[c.ordinal()];
                        maxPlayer = p;
                    }
                }
            }

            //Assigns control of the correct professor to the maxPlayer
            for (Team t : currentTeams)
            {
                for (Player p : t.getPlayers())
                {
                    if(p.equals(maxPlayer))
                    {
                        p.school.updateProfessorsTable(c.ordinal(), true);
                    }
                    else
                    {
                        p.school.updateProfessorsTable(c.ordinal(), false);
                    }
                }
            }

        }
        notify(modelToJson());
    }

    public String modelToJson()
    {
       return "ciao";
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
}