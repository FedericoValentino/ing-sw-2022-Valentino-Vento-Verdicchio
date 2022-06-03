package Client.LightView;

import Observer.Observable;
import Server.Answers.ActionAnswers.ViewMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;


public class LightView extends Observable
{
    private Boolean firstUpdate = true;
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

        if(!firstUpdate)
        {
            currentIslands.updateIslands(newView.currentIslands);
            for(int i = 0; i < currentTeams.size(); i++)
            {
                currentTeams.get(i).updateTeam(newView.currentTeams.get(i));
            }
            for(int i = 0; i < currentTeams.size(); i++)
            {
                currentClouds[i].updateCloud(newView.currentClouds[i]);
            }
            currentMotherNature.updateMother(newView.currentMotherNature);
            currentCharacterDeck.updateCharDeck(newView.currentCharacterDeck);
            currentActiveCharacterCard.updateActive(newView.currentActiveCharacterCard);
        }
        else
        {
            this.currentIslands = newView.currentIslands;
            this.currentTeams = newView.currentTeams;
            this.currentClouds = newView.currentClouds;
            this.currentMotherNature = newView.currentMotherNature;
            this.currentCharacterDeck = newView.currentCharacterDeck;
            this.currentActiveCharacterCard = newView.currentActiveCharacterCard;
        }
//
        this.currentTurnState = newView.currentTurnState;
        addNameToSchools();
        if(firstUpdate)
        {
            notifyLight(this);
            firstUpdate = false;
        }
    }

    public void addNameToSchools()
    {
        for(LightTeam t: currentTeams)
            for(LightPlayer p: t.getPlayers())
            {
                p.getSchool().updateName(p.getNome());
            }
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
