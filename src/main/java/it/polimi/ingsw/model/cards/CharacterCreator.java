package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.boards.token.enumerations.CharacterName;
import it.polimi.ingsw.model.cards.characters.*;

/**
 * This class contains the Factory method used by the Factory Pattern in CharacterDeck
 */
public class CharacterCreator
{
    /**
     * Given a type of characterCard, the method dynamically chooses which one to create, and returns it
     * @param type the name of the character card
     * @return the character card just created
     */
    public static CharacterCard getCharacter(CharacterName type, int deckIndex)
    {
        CharacterCard card;
        switch(type)
        {
            case CENTAUR:
                card = new Centaur();
                break;
            case GRANDMA_HERBS:
                card = new GrandmaHerbs();
                break;
            case HERALD:
                card = new Herald();
                break;
            case KNIGHT:
                card = new Knight();
                break;
            case POSTMAN:
                card = new Postman();
                break;
            case PRIEST:
                card = new Priest();
                break;
            case PRINCESS:
                card = new Princess();
                break;
            case TRUFFLE_HUNTER:
                card = new TruffleHunter();
                break;
            case JESTER:
                card = new Jester();
                break;
            case MINSTREL:
                card = new Minstrel();
                break;
            case THIEF:
                card = new Thief();
                break;
            case COOK:
                card = new Cook();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
        card.setDeckIndex(deckIndex);
        return  card;
    }
}
