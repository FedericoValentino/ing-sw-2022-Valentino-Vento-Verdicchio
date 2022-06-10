package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.boards.token.CharacterName;

public class CharacterCreator
{
    public static CharacterCard getCharacter(CharacterName type)
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
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
        return  card;
    }
}
