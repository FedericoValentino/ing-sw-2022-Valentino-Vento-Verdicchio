package model;

import model.boards.Cloud;
import model.boards.Islands;
import model.boards.Pouch;
import model.boards.token.MotherNature;
import model.boards.token.Student;
import model.cards.CharacterCard;
import model.cards.CharacterDeck;

import java.util.ArrayList;


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

    public CurrentGameState(int playerNum)
    {
       this.currentCharacterDeck = new CharacterDeck();
       this.currentPouch = new Pouch();
       this.currentIslands = new Islands();
       this.currentTeams = new ArrayList<>();
       //Aggiungere creazione di team in base al numero di players;
       this.currentClouds = new Cloud[playerNum];
       this.currentMotherNature = new MotherNature();
       this.currentTurnState = new CurrentTurnState();
       this.currentActiveCharacterCard = new ArrayList<>();
       this.currentExtractedStudents = new ArrayList<>();
       this.bankBalance = 20 - playerNum;

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

    public int getBankBalance(){return bankBalance;}
    public Islands getCurrentIslands(){return currentIslands;}
    public CharacterDeck getCurrentCharacterDeck(){return currentCharacterDeck;}
    public Pouch getCurrentPouch() {return currentPouch;}
}