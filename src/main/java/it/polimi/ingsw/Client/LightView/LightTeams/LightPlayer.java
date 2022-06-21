package it.polimi.ingsw.Client.LightView.LightTeams;
////
import it.polimi.ingsw.Client.LightView.LightBoards.LightSchool;
import it.polimi.ingsw.Client.LightView.LightCards.LightAssistantDeck;
import it.polimi.ingsw.Observer.Observable;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.model.cards.assistants.AssistantCard;

public class LightPlayer extends Observable
{
    private String name;
    private int coinAmount;
    private AssistantCard currentAssistantCard;
    private AssistantCard lastPlayedCard;
    private LightAssistantDeck assistantDeck;
    private LightSchool school;
    private int MaxMotherMovement;
    private int movementValue;
    private int value;
    private boolean TowerOwner;

    public LightPlayer(@JsonProperty("name") String nome,
                  @JsonProperty("coinAmount")int coinAmount,
                  @JsonProperty("currentAssistantCard")AssistantCard currentAssistantCard,
                  @JsonProperty("lastPlayedCard")AssistantCard lastPlayedCard,
                  @JsonProperty("assistantDeck")LightAssistantDeck assistantDeck,
                  @JsonProperty("school")LightSchool school,
                  @JsonProperty("maxMotherMovement")int maxMotherMovement,
                  @JsonProperty("movementValue")int movementValue,
                  @JsonProperty("value")int value,
                  @JsonProperty("towerOwner")boolean towerOwner)
    {
        this.name = nome;
        this.coinAmount = coinAmount;
        this.currentAssistantCard = currentAssistantCard;
        this.lastPlayedCard = lastPlayedCard;
        this.assistantDeck = assistantDeck;
        this.school = school;
        MaxMotherMovement = maxMotherMovement;
        this.movementValue = movementValue;
        this.value = value;
        TowerOwner = towerOwner;
    }



    public void updatePlayer(LightPlayer light)
    {
        this.name = light.getName();
        this.coinAmount = light.getCoinAmount();
        this.currentAssistantCard = light.getCurrentAssistantCard();
        this.lastPlayedCard = light.getLastPlayedCard();
        this.assistantDeck = light.getLightAssistantDeck();
        school.updateSchool(light.getSchool());
        this.MaxMotherMovement = light.getMaxMotherMovement();
        this.movementValue = light.getMovementValue();
        this.value = light.getValue();
        this.TowerOwner = light.isTowerOwner();
        System.out.println("Updated Lightplayer");
        notifyLight(this);
    }


    public String getName() {
        return name;
    }

    public int getCoinAmount() {
        return coinAmount;
    }

    public AssistantCard getCurrentAssistantCard() {
        return currentAssistantCard;
    }

    public AssistantCard getLastPlayedCard() {
        return lastPlayedCard;
    }

    public LightAssistantDeck getLightAssistantDeck() {
        return assistantDeck;
    }

    public LightSchool getSchool() {
        return school;
    }

    public int getMaxMotherMovement() {
        return MaxMotherMovement;
    }

    public int getMovementValue() {
        return movementValue;
    }

    public int getValue() {
        return value;
    }

    public boolean isTowerOwner() {
        return TowerOwner;
    }
}
