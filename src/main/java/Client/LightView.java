package Client;

import Server.Answers.ActionAnswers.ViewMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Team;
import model.boards.Cloud;
import model.boards.Islands;
import model.boards.token.MotherNature;
import model.cards.CharacterCard;
import model.cards.CharacterDeck;

import java.util.ArrayList;


public class LightView
{
    private ArrayList<CharacterCard> currentActiveCharacterCard;
    private CharacterDeck currentCharacterDeck;
    private Islands currentIslands;
    private ArrayList<Team> currentTeams;
    private MotherNature currentMotherNature;
    private Cloud[] currentClouds;
    private int bankBalance;

    public LightView()
    {

    }

    public void parse(ViewMessage view) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = view.getJsonView();
        LightView lv;
        lv = objectMapper.readValue(json, LightView.class);
        lv.currentCharacterDeck = view.getCurrentCharacterDeck();
        lv.currentActiveCharacterCard = view.getCurrentActiveCharacterCard();
        updateLightView(lv);
    }

    public void updateLightView(LightView newView)
    {
        this.currentIslands = newView.currentIslands;
        this.currentTeams = newView.currentTeams;
        this.currentMotherNature = newView.currentMotherNature;
        this.currentClouds = newView.currentClouds;
        this.currentCharacterDeck = newView.currentCharacterDeck;
        this.currentActiveCharacterCard = newView.currentActiveCharacterCard;
    }



    public void setCurrentIslands(Islands currentIslands) {
        this.currentIslands = currentIslands;
    }

    public void setCurrentTeams(ArrayList<Team> currentTeams) {
        this.currentTeams = currentTeams;
    }

    public void setCurrentMotherNature(MotherNature currentMotherNature) {
        this.currentMotherNature = currentMotherNature;
    }

    public void setCurrentClouds(Cloud[] currentClouds) {
        this.currentClouds = currentClouds;
    }

    public void setBankBalance(int bankBalance) {
        this.bankBalance = bankBalance;
    }


    public ArrayList<CharacterCard> getCurrentActiveCharacterCard() {
        return currentActiveCharacterCard;
    }

    public CharacterDeck getCurrentCharacterDeck() {
        return currentCharacterDeck;
    }

    public Islands getCurrentIslands() {
        return currentIslands;
    }

    public ArrayList<Team> getCurrentTeams() {
        return currentTeams;
    }

    public MotherNature getCurrentMotherNature() {
        return currentMotherNature;
    }

    public Cloud[] getCurrentClouds() {
        return currentClouds;
    }

    public int getBankBalance() {
        return bankBalance;
    }
}
