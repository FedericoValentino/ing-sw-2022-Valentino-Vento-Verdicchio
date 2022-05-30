package Client.LightView;

import Observer.Observable;
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


public class LightView extends Observable
{
    private LightActiveDeck currentActiveCharacterCard;
    private LightCharDeck currentCharacterDeck;
    private LightIslands currentIslands;
    private ArrayList<LightTeam> currentTeams;
    private LightMotherNature currentMotherNature;
    private LightCloud[] currentClouds;
    private LightTurnState currentTurnState;
    private int bankBalance;

    private InfoDispenser informations = new InfoDispenser();

    public LightView()
    {
    }

    public void parse(ViewMessage view) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = view.getJsonView();
        LightView lv;
        lv = objectMapper.readValue(json, LightView.class);
        lv.currentCharacterDeck = new LightCharDeck(view.getCurrentCharacterDeck().getDeck());
        lv.currentActiveCharacterCard = new LightActiveDeck(view.getCurrentActiveCharacterCard());
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
        this.currentTurnState = newView.currentTurnState;
        notifyLight(this);
    }



    public void setCurrentIslands(LightIslands currentIslands) {
        this.currentIslands = currentIslands;
    }

    public void setCurrentTeams(ArrayList<LightTeam> currentTeams) {
        this.currentTeams = currentTeams;
    }

    public void setCurrentMotherNature(LightMotherNature currentMotherNature) {
        this.currentMotherNature = currentMotherNature;
    }

    public void setCurrentClouds(LightCloud[] currentClouds) {
        this.currentClouds = currentClouds;
    }

    public void setBankBalance(int bankBalance) {
        this.bankBalance = bankBalance;
    }


    public LightActiveDeck getCurrentActiveCharacterCard() {
        return currentActiveCharacterCard;
    }

    public LightCharDeck getCurrentCharacterDeck() {
        return currentCharacterDeck;
    }

    public LightIslands getCurrentIslands() {
        return currentIslands;
    }

    public ArrayList<LightTeam> getCurrentTeams() {
        return currentTeams;
    }

    public LightMotherNature getCurrentMotherNature() {
        return currentMotherNature;
    }

    public LightCloud[] getCurrentClouds() {
        return currentClouds;
    }

    public LightTurnState getCurrentTurnState()
    {
        return currentTurnState;
    }

    public InfoDispenser getInformations(){
        return informations;
    }

    public int getBankBalance() {
        return bankBalance;
    }
}
