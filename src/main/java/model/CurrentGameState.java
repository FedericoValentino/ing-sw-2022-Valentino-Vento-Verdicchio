package model;

import model.boards.Cloud;
import model.boards.Islands;
import model.boards.Pouch;
import model.boards.token.Col;
import model.boards.token.MotherNature;
import model.boards.token.Student;
import model.cards.CharacterCard;
import model.cards.CharacterDeck;


import java.util.*;


public class CurrentGameState {
    private CharacterDeck currentCharacterDeck;
    private Pouch currentPouch;
    private Islands currentIslands;
    private ArrayList<Team> currentTeams;
    private Cloud[] currentClouds;
    private MotherNature currentMotherNature;
    private CurrentTurnState currentTurnState;
    private ArrayList<CharacterCard> currentActiveCharacterCard;
    private ArrayList<Student> currentExtractedStudents;
    private int bankBalance;
    private boolean expertGame;

    public CurrentGameState(int playerNum, boolean expertGame)
    {
       this.currentCharacterDeck = new CharacterDeck();
       this.currentPouch = new Pouch();
       this.currentIslands = new Islands();
       this.currentTeams = new ArrayList<>();
       this.currentClouds = new Cloud[playerNum];
       this.currentMotherNature = new MotherNature();
       this.currentTurnState = new CurrentTurnState();
       this.currentActiveCharacterCard = new ArrayList<>();
       this.currentExtractedStudents = new ArrayList<>();
       if(expertGame)
           this.bankBalance = 20 - playerNum;
       else
           this.bankBalance = 0;
       this.expertGame = expertGame;

    }


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
        List<Map.Entry<String,Integer>> list = new LinkedList<>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2)
            {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        for (Map.Entry<String, Integer> aa : list) {
            finalmap.put(aa.getKey(), aa.getValue());
        }
        currentTurnState.updateTurn(finalmap);
    }


    public void insertExtractedStudent(Student s)
    {
        currentExtractedStudents.add(s);
    }

    public void checkWinner()
    {
        for(Team t: currentTeams)
        {
            for(Player p: t.getPlayers())
            {
                //da modificare perchè nella modalità a 4 giocatori 2 player non hanno torri
                if(p.getPlayerSchool().getTowerCount() == 0)
                {
                    currentTurnState.updateWinner(t.getColor());
                }
                if(p.getAssistantDeck().checkEmpty())
                {
                    currentTurnState.lastTurn = true;
                }
            }
        }
        if(currentIslands.getTotalGroups() == 3)
        {
            currentTurnState.updateWinner(currentIslands.getMaxCol());
        }
        else if (currentPouch.checkEmpty())
        {
            currentTurnState.lastTurn = true;
        }
    }

    public void updateBankBalance(Player p)
    {
        int coinsToLose = p.gainCoin();
        if(coinsToLose <= bankBalance)
        {
            bankBalance -= coinsToLose;

        }

    }

    public void giveProfessors()
    {
        for(Col c: Col.values())                                        //scorro tutti i colori di studenti
        {
            int max = 0;                                                //mi salvo il numero di studenti massimo che ho trovato
            Player maxPlayer = null;                                    //mi salvo il player con quel numero di studenti massimo
            for (Team t : currentTeams)                                 //trovo il player con il colore massimo
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
            for (Team t : currentTeams)                                  //setto il controllo del professore al maxPlayer
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
    }

    public int getBankBalance(){return bankBalance;}
    public Islands getCurrentIslands(){return currentIslands;}
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
}