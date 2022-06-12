package it.polimi.ingsw.Client.LightView;

import it.polimi.ingsw.model.cards.CharacterCard;
import it.polimi.ingsw.model.cards.GrandmaHerbs;
import it.polimi.ingsw.model.cards.Priest;
import it.polimi.ingsw.model.cards.Princess;


import java.util.Arrays;

public class LightCharacterFactory 
{
    public LightCharacterCard characterCreator(CharacterCard card)
    {
        String[] description = new String[7];
        Arrays.fill(description, "");
        switch (card.getCharacterName())
        {
            case HERALD:
                description[0] += "The herald lets you ignore any rules of decency by letting you start the influence calculation ";
                description[1] += "on an island of your choice, even if Mother Nature hasn't ended her movement there. That's right, ";
                description[2] += "you won't need to wait for anyone to make your dreams of conquest come true!";
                description[3] += "Use this effect in combination with Mother Nature to try and conquer more than one island ";
                description[4] += "in just one turn!";
                return new LightCharacterCard(card.getCharacterName(),
                        card.getBaseCost(), 
                        card.getUses(), 
                        card.getCurrentCost(), 
                        -1, 
                        null, 
                        description, 
                        LightCharacterType.INTEGER_1);
                
            case KNIGHT:
                description[0] += "To arms! This is one of the most offensive character you can even dream to summon. Use the overwhelming strength ";
                description[1] += "and might of this companion to gain an important advantage in conquering an island: while this card is active ";
                description[2] += "you and your team gain 2 more influence points to aid in the crusade against the enemy!";
                description[3] += "Use this card to aid in the conquest of the most well defended outposts!";
                return new LightCharacterCard(card.getCharacterName(),
                        card.getBaseCost(), 
                        card.getUses(), 
                        card.getCurrentCost(), 
                        -1, 
                        null, 
                        description, 
                        LightCharacterType.NONE);
                
            case PRINCESS:
                description[0] += "The Princess-warrior of the lands has host of valorous and combat ready knights, always at her service. They loyalty is ";
                description[1] += "without question; but, like everything, it has a price. For as small as two coins, you can convince one of these ";
                description[2] += "skilled warriors to sit for the whole game at your dining room, patiently waiting for your - we are sure - genius ";
                description[3] += "strategies to unfold.";
                description[4] += "Use this card to grant you a significant advantage in the professors - control race!";
                return new LightCharacterCard(card.getCharacterName(),
                        card.getBaseCost(),
                        card.getUses(),
                        card.getCurrentCost(),
                        -1,
                        ((Princess) card).getStudents(),
                        description,
                        LightCharacterType.INTEGER_1);
                
            case PRIEST:
                description[0] += "The priest lives with his apprentices in a secluded convent, located amongst the hills of an unspecified island.";
                description[1] += "For a small fee, he will instruct one of his students to do your bidding for the rest of the game. You could order him to make ";
                description[2] += "some delicious coffee; or you could send him to an island of your choice (which actually is what you should make him do regardless).";
                description[3] += "Use this card to help you gain some critical influence on contested islands.";
                return new LightCharacterCard(card.getCharacterName(),
                        card.getBaseCost(),
                        card.getUses(),
                        card.getCurrentCost(),
                        -1,
                        ((Priest) card).getStudents(),
                        description,
                        LightCharacterType.INTEGER_2);
                
            case CENTAUR:
                description[0] += "Centaur is a card of the \"influence calculation\" type.";
                description[1] += "While this card is active, towers on the island on which mother nature lands do not count ";
                description[2] += "towards influence calculation, as if the island wasn't owned by anyone in the first place!";
                description[3] += "Use this card to help you conquer an island owned by your enemies!";
                return new LightCharacterCard(card.getCharacterName(),
                        card.getBaseCost(),
                        card.getUses(),
                        card.getCurrentCost(),
                        -1,
                        null,
                        description,
                        LightCharacterType.NONE);
                
            case POSTMAN:
                description[0] += "The Postman is quite the industrious chap. Thanks to the nature of his job, he developed excellent orienteering ";
                description[1] += "skills, skills that he uses to navigate efficiently around the cluttered Eryantis world.";
                description[2] += "If you choose him as your companion, he will teach you some of his tricks for the current turn, ";
                description[3] += "adding 2 extra movements to the maximum movements mother nature can perform!";
                description[4] += "Use this card to reach very distant islands.";
                return new LightCharacterCard(card.getCharacterName(),
                        card.getBaseCost(),
                        card.getUses(),
                        card.getCurrentCost(),
                        -1,
                        null,
                        description,
                        LightCharacterType.NONE);
                
                
            case GRANDMA_HERBS:
                description[0] += "Grandma Herbs has the power to place up to four No Entry tile onto an island of your choice (one per island).";
                description[1] += "You may ask yourself \"But why would I do that?\". Well, if Mother Nature ends her movement on ";
                description[2] += "said island, there won't be any influence calculation happening. It's the power of the magical herbs!";
                description[3] += "Use this ability to prevent your islands from being captured by your foes, but be advised: once ";
                description[4] += "Mother Nature ends her movement on an island sporting a No Entry tile, said No Entry will be consumed, and thus ";
                description[5] += "it will return on the Grandma Herbs card.";
                return new LightCharacterCard(card.getCharacterName(),
                        card.getBaseCost(),
                        card.getUses(),
                        card.getCurrentCost(),
                        ((GrandmaHerbs) card).getNoEntry(),
                        null,
                        description,
                        LightCharacterType.INTEGER_1);
                
                
            case TRUFFLE_HUNTER:
                description[0] += "This peculiar individual, as the name might imply, is a mushroom enthusiast, his \"hunting\" sessions consisting ";
                description[1] += "of slow paced walks in the woods, looking under every fern and heap of grass to find another example to add to his prized possessions.";
                description[2] += "What on earth could this man offer to you? Well, for a significant cost, he will bring \"hunting\" with him all the students of ";
                description[3] += "the selected color that he will find on the island on which mother nature has ended her movement, ensuring that they do not count ";
                description[4] += "towards the influence calculation.";
                description[5] += "Use this character to help you mitigate your incompetence in gaining control of professors, or just to demonstrate that ";
                description[6] += "you too, like the Knight, can conquer a well defended island, albeit with a little help.";
                return new LightCharacterCard(card.getCharacterName(),
                        card.getBaseCost(),
                        card.getUses(),
                        card.getCurrentCost(),
                        -1,
                        null,
                        description,
                        LightCharacterType.COLOR);
        }
        return null;
    }
}
