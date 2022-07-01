package it.polimi.ingsw.Client.LightView;

import it.polimi.ingsw.Client.LightView.LightBoards.LightCloud;
import it.polimi.ingsw.Client.LightView.LightBoards.LightIslands;
import it.polimi.ingsw.Client.LightView.LightCards.characters.LightActiveDeck;
import it.polimi.ingsw.Client.LightView.LightCards.characters.LightCharDeck;
import it.polimi.ingsw.Client.LightView.LightTeams.LightPlayer;
import it.polimi.ingsw.Client.LightView.LightTeams.LightTeam;
import it.polimi.ingsw.Client.LightView.LightToken.LightMotherNature;
import it.polimi.ingsw.Observer.Observable;
import it.polimi.ingsw.Server.Answers.ActionAnswers.ViewMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

/**
 * Class LightView contains mock objects used to mimic those of the model. Each object contains the attributes needed for
 * its representation, and the associated getters. For this reason, only the classes with some peculiarities will have a
 * documentation attached (it's not very informative documenting getters, and most of the classes are 1:1 reproductions of those
 * inside the model).
 * Moreover, each object (aside from some objects that are contained within other objects), and the lightView itself,
 * extends the observable class, and have the update method to synchronize themselves with the latest view.
 * LightView contains its update method, a method to parse the JSON file containing the updated view, and some service methods useful
 * in lots of classes that retain an instance of the view.
 * The seemingly unused setters are required by JSON protocol, even though they seem useless
 */
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

    /**
     * Class constructor
     */
    public LightView()
    {
    }

    /**
     * The method parses the ViewMessage containing the JSON serialization of the game model
     * @param view the viewMessage containing the JSON
     */
    public void parse(ViewMessage view) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = view.getJsonView();
        LightView lv;
        lv = objectMapper.readValue(json, LightView.class);
        if(view.isExpertMode())
        {
            lv.currentCharacterDeck = new LightCharDeck(view.getCurrentCharacterDeck().getDeck());
            lv.currentActiveCharacterCard = new LightActiveDeck(view.getCurrentActiveCharacterCard());
        }
        updateLightView(lv);
    }

    /**
     * Method updateLightView updates the client view and its objects, after the deserialization of the model JSON
     * @param newView the up-to-date view
     */
    public void updateLightView(LightView newView)
    {

        if(!firstUpdate)
        {
            currentIslands.updateIslands(newView.currentIslands);
            for(int i = 0; i < currentTeams.size(); i++)
            {
                currentTeams.get(i).updateTeam(newView.currentTeams.get(i));
            }
            for(int i = 0; i < currentClouds.length; i++)
            {
                currentClouds[i].updateCloud(newView.currentClouds[i]);
            }
            currentMotherNature.updateMother(newView.currentMotherNature);
            if(newView.currentCharacterDeck != null && newView.currentActiveCharacterCard !=null)
            {
                currentCharacterDeck.updateCharDeck(newView.currentCharacterDeck);
                currentActiveCharacterCard.updateActive(newView.currentActiveCharacterCard);
            }
            currentTurnState.updateTurn(newView.getCurrentTurnState());
        }
        else
        {
            this.currentIslands = newView.currentIslands;
            this.currentTeams = newView.currentTeams;
            this.currentClouds = newView.currentClouds;
            this.currentMotherNature = newView.currentMotherNature;
            if(newView.currentCharacterDeck != null && newView.currentActiveCharacterCard !=null)
            {
                this.currentCharacterDeck = newView.currentCharacterDeck;
                this.currentActiveCharacterCard = newView.currentActiveCharacterCard;
            }
            this.currentTurnState = newView.currentTurnState;
        }
        this.bankBalance = newView.getBankBalance();


        addNameToSchools();
        if(firstUpdate)
        {
            notifyLight(this);
            firstUpdate = false;
        }
    }

    /**
     * Method addNameToSchools sets the school owner for easier localization of the player school
     */
    public void addNameToSchools()
    {
        for(LightTeam t: currentTeams)
            for(LightPlayer p: t.getPlayers())
            {
                p.getSchool().updateName(p.getName());
            }
    }

    /** Method findPlayerByName returns the Player with the given name
     * @param currentTeams  the teams to search the player name in
     * @param playerName  the name of the player object to seek
     * @return p, the correct player object
     */
    public LightPlayer findPlayerByName(ArrayList<LightTeam> currentTeams, String playerName)
    {
        for(LightTeam team: currentTeams)
        {
            for(LightPlayer player: team.getPlayers())
            {
                if(player.getName().equals(playerName))
                {
                    return player;
                }
            }
        }
        return null;
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

    public int getBankBalance() {
        return bankBalance;
    }
}
