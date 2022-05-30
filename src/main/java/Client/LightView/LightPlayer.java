package Client.LightView;

import com.fasterxml.jackson.annotation.JsonProperty;
import model.boards.School;
import model.cards.AssistantCard;
import model.cards.AssistantDeck;

public class LightPlayer
{
    private String nome;
    private int coinAmount;
    private AssistantCard currentAssistantCard;
    private AssistantCard lastPlayedCard;
    private AssistantDeck assistantDeck;
    public LightSchool school;
    private int MaxMotherMovement;
    private int movementValue;
    private int value;
    private boolean TowerOwner;

    public LightPlayer(@JsonProperty("nome") String nome,
                  @JsonProperty("coinAmount")int coinAmount,
                  @JsonProperty("currentAssistantCard")AssistantCard currentAssistantCard,
                  @JsonProperty("lastPlayedCard")AssistantCard lastPlayedCard,
                  @JsonProperty("assistantDeck")AssistantDeck assistantDeck,
                  @JsonProperty("school")LightSchool school,
                  @JsonProperty("maxMotherMovement")int maxMotherMovement,
                  @JsonProperty("movementValue")int movementValue,
                  @JsonProperty("value")int value,
                  @JsonProperty("towerOwner")boolean towerOwner)
    {
        this.nome = nome;
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


    public String getNome() {
        return nome;
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

    public AssistantDeck getAssistantDeck() {
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
